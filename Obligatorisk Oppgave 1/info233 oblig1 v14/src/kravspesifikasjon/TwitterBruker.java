package kravspesifikasjon;

import java.io.Serializable;

public interface TwitterBruker extends Serializable{
	public String getNavn();
	public String getID();
	public int numTweets();
	public void setTweets(int numTweets);	
	public int numCharacters();
	public double getAverageNumCharacters();
	public int numFollowers();
	public void setFollowers(int followers);
	public int numFriends();
	public void setFriends(int friends);
}
