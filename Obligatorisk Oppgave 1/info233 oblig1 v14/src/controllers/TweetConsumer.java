package controllers;

import java.util.List;

public interface TweetConsumer {
	
	/**
	 * This method is called by the {@link TwitterStream} ({@link SwingWorker}) 
	 * object when it has processed one or more tweets.
	 * 
	 * @param tweets
	 */
	public void consumeTweets(List<String> tweets);
	
	
	/**
	 * Creates a new {@link TwitterStream} ({@link SwingWorker})
	 * object and run it as a background task.
	 */
	public void startTwitterStream();
	
	/**
	 * Stops the {@link TwitterStream} ({@link SwingWorker})
	 * object's task, but the object retained for future use.
	 */
	public void stopTwitterStream();
}
