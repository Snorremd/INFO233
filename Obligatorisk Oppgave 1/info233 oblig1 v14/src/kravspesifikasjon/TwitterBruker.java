package kravspesifikasjon;

import java.io.Serializable;

public interface TwitterBruker extends Serializable{
	/**
	 * Navnet på brukeren som vises på tweetene.
	 * @return skjermnavnet til brukeren.
	 */
	public String getNavn();
	/**
	 * Alle brukere har en unik ID
	 * @return den unike ID-en til brukeren.
	 */
	public String getID();
	
	/**
	 * totalt antall meldinger som brukeren har sendt.
	 * @return et heltall som representerer alle meldingene denne brukeren har sendt.
	 */
	public int numTweets();

	/**
	 * Antall tegn er alle tegn i alle meldinger denne brukeren har sendt.
	 * @return totalt antall tegn denne brukeren har postet.
	 */
	public int numCharacters();
	
	/**
	 * Det gjennomsnittslige antall tegn i en melding. (Husk på heltallsdivisjon vs flyttallsdivisjon)
	 * @return gjennomsnittslig antall tegn i en melding fra denne brukeren.
	 */
	public double getAverageNumCharacters();
	/**
	 * Henter antall followers
	 * @return antall followers denne brukeren har.
	 */
	public int numFollowers();
	
	/**
	 * Henter ut antall friends.
	 * @return antall folk er registrert som "friend" med denne brukeren.
	 */
	public int numFriends();
}
