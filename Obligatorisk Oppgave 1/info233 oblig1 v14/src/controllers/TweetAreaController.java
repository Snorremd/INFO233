/**
 * 
 */
package controllers;

import io.TwitterStream;

import java.util.List;

import javax.swing.JTextArea;

import views.AppView;

/**
 * @author snorre
 *
 */
public class TweetAreaController implements TweetConsumer {
	
	// App controller reference in order to fetch components by name
	AppController appController;
	TwitterStream stream;
	
	public TweetAreaController(AppController appController) {
		this.appController = appController;
		stream = new TwitterStream(this);
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

	/* (non-Javadoc)
	 * @see controllers.TweetConsumer#startTwitterStream()
	 */
	public void startTwitterStream() {
		// TODO Auto-generated method stub
		System.out.println("Execute stream!");
		stream.execute();

	}

	/* (non-Javadoc)
	 * @see controllers.TweetConsumer#stopTwitterStream()
	 */
	public void stopTwitterStream() {
		// TODO Auto-generated method stub
		stream.cancel(true);

	}

}
