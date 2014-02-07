/**
 * Denne filen inneholder en "wrapper"-klasse for Twitters
 * strømme-API. Klassen lar en opprette en forbindelse til API-et
 * og hente ut twitter-meldinger fra en meldingskø.
 */
package io;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import utils.Config;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import exceptions.TwitterConnectionException;

/**
 * TwitterStream klassen pakker inn funksjonaliteten
 * til Hosebird-klienten til Twitter. Denne klienten
 * abonnerer på en Twitter-strøm, i dette tilfellet på
 * Tweets postet i Sør-Norge (filtrerer Twitter-strøm på
 * geo-lokasjon).
 * 
 * Klassen eksponerer en konstruktør uten parametere og ordner
 * all konfigurasjon av Twitter-strømmen på egenhånd. Klassen er
 * avhengig av at det ligger en twitterauth.properties fil med
 * korrekt autentiseringsdata i filen. Spør om hjelp hvis dere er
 * usikre på oppsettet.
 * 
 * For å starte klienten kan dere kalle på metoden connect() i
 * denne klassen. En disconnect-metode er også lagt til for å
 * "pause" klienten.
 * 
 * Bruk metoden getTweets(int number) for å hente ut n antall
 * tweets fra Twitter-meldings-køen, eller alle tweets.
 * 
 * @author Snorre Magnus Davøen
 *
 */
public class TwitterStream {
	
	// Holder på singleton-objektet
	private static TwitterStream stream;
	
	// Køer for strømmen
	private BlockingQueue<String> messageQueue;
	private BlockingQueue<Event> eventQueue;
	
	// Felter for klient-relaterte objekter
	private Client hosebirdClient;
	private Authentication hosebirdAuth;
	private ClientBuilder clientBuilder;
	
	/**
	 * Standard konstruktør for TwitterStream-klassen. Den tar ingen
	 * argumenter, og konfigurerer Hosebird-klienten på egenhånd. Merk
	 * at oppkobling mot Twitter-APIet krever at en twitterauth.properties
	 * fil ligger i config-mappen med riktige autentiseringsnøkler. Et eksempel
	 * på en slik fil ligger i mappen med navn twitterauth.properties.example
	 * 
	 * Konstruktøren oppretter ikke klienten, dette skjer først ved påkalling  av
	 * metoden connect();
	 */
	private TwitterStream() {

		this.messageQueue = new LinkedBlockingQueue<String>(1000);
		
		// Fetch authentication properties and store them in string variables
		Properties oauthCredentials = Config.getTwitterProperties();
		String consumerKey = oauthCredentials.getProperty("consumerKey");
		String consumerSecret = oauthCredentials.getProperty("consumerSecret");
		String token = oauthCredentials.getProperty("token");
		String secret = oauthCredentials.getProperty("secret");
		
		// Create new Authentication object for use with the hose bird client
		hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);
		
		// Create hosebird host (we want to connect to streaming api)
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		
		// Create an endpoint filter that filters the stream on a boxed geo position
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
		
		String southWestLat = "57.00";
		String southWestLong = "2.55";
		String northEastLat = "64.00";
		String northEastLong = "12.00";
		
		String location = ""
						+ southWestLong + "," 
						+ southWestLat  + ","
						+ northEastLong  + ","
						+ northEastLat;
						
		hosebirdEndpoint.addPostParameter("locations", location);
		
		
		// Build a hosebird client
		clientBuilder = new ClientBuilder()
			.name("Hosebird-Client-01")
			.hosts(hosebirdHosts)
			.authentication(hosebirdAuth)
			.endpoint(hosebirdEndpoint)
			.processor(new StringDelimitedProcessor(messageQueue))
			.eventMessageQueue(eventQueue);
	}
	
	/**
	 * Returner singleton-objektet for TwitterStream-klassen.
	 * Denne metoden er eneste måte å hente et objekt  på for
	 * å unngå at det opprettes mer enn ett TwitterStream-objekt.
	 * 
	 * @return det unike TwitterStream-objektet.
	 */
	public static TwitterStream instance() {
		
		if(stream == null) {
			stream = new TwitterStream();
		}
		return stream;
	}
	
	/**
	 * Denne main-metoden er ment som et eksempel på hvordan
	 * man kan bruke TwitterStream-klassen.
	 * @param args
	 */
	public static void main(String[] args) {
		TwitterStream stream = TwitterStream.instance();
		stream.connect();
		
		while(true) {
			try {
				Thread.sleep(20000);	
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			ArrayList<String> tweets = new ArrayList<>();
			tweets.addAll(stream.getAllTweets());
			System.out.println(tweets);
		}
	}
	
	/**
	 * Opprett en klient og koble til twitter-strømmen.
	 */
	public void connect() {
		hosebirdClient = clientBuilder.build();
		hosebirdClient.connect();
	}
	
	/**
	 * Koble fra twitter-strømmen og slett
	 * hosebird-klienten. Ny klient må opprettes
	 * for hver tilkobling.
	 */
	public void disconnect() {
		hosebirdClient.stop();
		hosebirdClient = null;
	}
	
	/**
	 * Hent en liste med n antall tweets fra køen til Twitter-strømmen.
	 * Hver tweet er representert i form av en ren JSON-streng.
	 * 
	 * Hvis n < 0 (i.e. -1) returneres alle tweets fra køen.
	 * Hvis n == 0 returneres 0 tweets.
	 * Hvis n > 0 returneres n tweets hvis n <= antall tweets i køen, ellers returneres antall tweets
	 * 
	 * @param number antallet tweets som skal hentes fra køen
	 * @return en liste med tweets (i form av JSON-strenger)
	 * @throws TwitterConnectionException
	 */
	public List<String> getTweets(int number) throws TwitterConnectionException {
		
		if(hosebirdClient.isDone()) throw new TwitterConnectionException();
		
		LinkedList<String> tweets;
		
		if(number > -1) {
			tweets = getNTweets(number);
		} else {
			tweets = getAllTweets();
		}
		
		return tweets;
	}
	
	/*
	 * Intern metode for å hente alle tweets i køen.
	 */
	private LinkedList<String> getAllTweets() {
		LinkedList<String> tweets = new LinkedList<>();
		while(messageQueue.peek() != null) {
			try {
				tweets.add(messageQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return tweets;
	}

	/*
	 * Intern metode for å hente n tweets fra køen.
	 */
	private LinkedList<String> getNTweets(int number) {
		
		LinkedList<String> tweets = new LinkedList<>();
		for (int i = 0; i < number; i++) {
			try {
				tweets.add(messageQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return tweets;
	}
}