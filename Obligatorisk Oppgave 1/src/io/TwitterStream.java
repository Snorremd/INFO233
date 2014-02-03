/**
 * This file contains a wrapper class for the Twitter Streaming api.
 * It implements a producer class (as in the producer consumer pattern)
 * wherein the 
 */
package io;

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

/**
 * @author Snorre Davøen
 *
 */
public class TwitterStream extends SwingWorker<Void, String> {
	
	private BlockingQueue<String> messageQueue;
	private BlockingQueue<Event> eventQueue;
	
	private Client hosebirdClient;
	private Hosts hosebirdHosts;
	private StreamingEndpoint endpoint;
	private Authentication hosebirdAuth;
	private ClientBuilder clientBuilder;
	
	private TweetConsumer consumer;
	
	public TwitterStream(TweetConsumer consumer) {
		
		this.consumer = consumer;
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

	@Override
	protected Void doInBackground() throws Exception {
		// Build a new hosebird client and connect
		hosebirdClient = clientBuilder.build();
		hosebirdClient.connect();
		
		// While there are still more tweets in this stream
		while(!hosebirdClient.isDone()) {
			String tweet;
			try {
				// Consume tweet (if any left else catch interrupted exception)
				tweet = messageQueue.take();
				System.out.println("Publish tweet");
				publish(tweet);
			} catch (InterruptedException e) {
				// No elements in message queue or interrupted by gui
				System.out.println("Swing worker twitter stream interrupted");
				hosebirdClient.stop();
			}
		}
		return null;
	}
	
	@Override
	protected void process(List<String> tweets) {
		// Call the consumeTweets method in the consumer (controller)
		System.out.println("Process tweets " + tweets);
		consumer.consumeTweets(tweets);
	}
}