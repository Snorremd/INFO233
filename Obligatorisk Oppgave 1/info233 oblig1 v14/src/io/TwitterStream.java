/**
 * This file contains a wrapper class for the Twitter Streaming api.
 * It implements a producer class (as in the producer consumer pattern)
 * wherein the 
 */
package io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.SwingWorker;

import utils.Config;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StreamingEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import controllers.TweetConsumer;
import exceptions.TwitterConnectionException;

/**
 * @author Snorre Davøen
 *
 */
public class TwitterStream {
	
	private BlockingQueue<String> messageQueue;
	private BlockingQueue<Event> eventQueue;
	
	private Client hosebirdClient;
	private Hosts hosebirdHosts;
	private StreamingEndpoint endpoint;
	private Authentication hosebirdAuth;
	private ClientBuilder clientBuilder;
	
	public TwitterStream() {

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
	
	public static void main(String[] args) {
		TwitterStream stream = new TwitterStream();
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
	
	public void connect() {
		hosebirdClient = clientBuilder.build();
		hosebirdClient.connect();
	}
	
	public void disconnect() {
		hosebirdClient.stop();
		hosebirdClient = null;
	}
	
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

//	@Override
//	public void run() {
//		// Build a new hosebird client and connect
//		hosebirdClient = clientBuilder.build();
//		hosebirdClient.connect();
//		
//		// While there are still more tweets in this stream
//		while(!hosebirdClient.isDone()) {
//			String tweet;
//			try {
//				// Consume tweet (if any left else catch interrupted exception)
//				tweet = messageQueue.take();
//				System.out.println("Publish tweet");
//				publish(tweet);
//			} catch (InterruptedException e) {
//				// No elements in message queue or interrupted by gui
//				System.out.println("Swing worker twitter stream interrupted");
//				hosebirdClient.stop();
//			}
//		}
//		return null;
//	}
	
//	@Override
//	protected void process(List<String> tweets) {
//		// Call the consumeTweets method in the consumer (controller)
//		System.out.println("Process tweets " + tweets);
//		consumer.consumeTweets(tweets);
//	}
}