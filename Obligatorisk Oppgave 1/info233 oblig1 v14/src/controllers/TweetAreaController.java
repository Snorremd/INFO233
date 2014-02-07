/**
 * 
 */
package controllers;

import io.TwitterStream;

import java.util.List;

import javax.swing.JTextArea;

/**
 * @author snorre
 *
 */
public class TweetAreaController implements TweetConsumer {
	
	// App controller reference in order to fetch components by name
	AppController appController;
	TwitterStream stream;
	
	public TweetAreaController(AppController appController) {
	}

	/* (non-Javadoc)
	 * @see controllers.TweetConsumer#consumeTweets(java.util.List)
	 */
	public void consumeTweets(List<String> tweets) {
		System.out.println(appController);
		JTextArea tweetArea = (JTextArea) appController.getComponentByName("tweetArea");
		String existingTweets = tweetArea.getText();
		StringBuilder builder = new StringBuilder();
		for(String tweet : tweets) {
			builder.append(tweet);
			builder.append("\n");
		}
		builder.append(existingTweets);
		
		System.out.println("Add tweets to JTextArea");
		tweetArea.setText(builder.toString());
		
	}

	@Override
	public void startTwitterStream() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopTwitterStream() {
		// TODO Auto-generated method stub
		
	}
}
