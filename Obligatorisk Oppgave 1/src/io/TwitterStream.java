/**
 * This file contains a wrapper class for the Twitter Streaming api.
 * It implements a producer class (as in the producer consumer pattern)
 * wherein the 
 */
package io;

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
import com.twitter.hbc.core.endpoint.StreamingEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

/**
 * @author Snorre Davøen
 *
 */
public class TwitterStream implements Runnable {
	
	private BlockingQueue<String> messageQueue;
	private BlockingQueue<Event> eventQueue;
	
	private Client hosebirdClient;
	private Hosts hosebirdHosts;
	private StreamingEndpoint endpoint;
	private Authentication hosebirdAuth;
	
	public TwitterStream(BlockingQueue<String> messageQueue) {
		this.messageQueue = messageQueue;
		Properties oauthCredentials = Config.getTwitterProperties();
		
		// Fetch each property and store in string variables
		String consumerKey = oauthCredentials.getProperty("consumerKey");
		String consumerSecret = oauthCredentials.getProperty("consumerSecret");
		String token = oauthCredentials.getProperty("token");
		String secret = oauthCredentials.getProperty("secret");
		
		// Create new Authentication object
		hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);
		
		// Create hosebird hosts
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		//StreamingEndpoint hosebirdEndpoint2 = new StatusesFilterEndpoint();
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
		hosebirdEndpoint.addPostParameter("locations", "5.10,60.23,5.50,60.46");
		
		// Build a hosebird client
		ClientBuilder clientBuilder = new ClientBuilder()
				.name("Hosebird-Client-01")
				.hosts(hosebirdHosts)
				.authentication(hosebirdAuth)
				.endpoint(hosebirdEndpoint)
				.processor(new StringDelimitedProcessor(messageQueue))
				.eventMessageQueue(eventQueue);
		
		hosebirdClient = clientBuilder.build();
	}
	
	public static void main(String[] args) {
		
		BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>(1000);
		TwitterStream stream = new TwitterStream(messageQueue);
		Thread streamThread = new Thread(stream);
		streamThread.run();
	}

	public void run() {
		hosebirdClient.connect();
		
		while (!hosebirdClient.isDone()) {
			  String msg;
			try {
				msg = messageQueue.take();
				System.out.println(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				hosebirdClient.stop();
			} catch(Exception e) {
				// Just in case
				hosebirdClient.stop();
			}
		}
		
	}
}