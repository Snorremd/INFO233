/**
 * 
 */
package demoer;

import io.TwitterStream;

import java.util.ArrayList;

import exceptions.TwitterConnectionException;

/**
 * @author snorre
 *
 */
public class TweetStroem {

	/**
	 * Denne main-metoden er ment som et eksempel på hvordan
	 * man kan bruke TwitterStream-klassen.
	 * @param args
	 */
	public static void main(String[] args) {
		TwitterStream stream = TwitterStream.instance();
		stream.connect();
		
		while(true) {
			// Sov tjue sekunder for å la køen til twitter-strømmen fylles litt opp
			try {
				Thread.sleep(20000);	
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			// Hent ut alle twitter-meldinger i køen og skriv ut.
			ArrayList<String> tweets = new ArrayList<>();
			try {
				tweets.addAll(stream.getTweets(-1));
			} catch (TwitterConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(tweets);
		}
	}

}
