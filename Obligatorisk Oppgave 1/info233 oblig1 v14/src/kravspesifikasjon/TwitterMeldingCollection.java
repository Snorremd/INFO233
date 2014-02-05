package kravspesifikasjon;

import java.io.Serializable;
import java.util.Collection;

public interface TwitterMeldingCollection extends Serializable {
	/**
	 * Hvor stor er denne samlingen?
	 * @return et heltall som er større eller lik 0
	 */
	public int size();
	/**
	 * Setter inn en ny TwitterMelding i samlingen.
	 * Det er ikke definert hvor du setter inn meldingen.
	 * At dersom denne samlingen ikke har lov til å si at den er full.
	 * @return true dersom meldingen ble satt inn korrekt, false dersom noe gikk galt.
	 */
	public boolean insert(TwitterMelding tm);

	
	/**
	 * Setter inn en melding på angitt plass.
	 * Indexene begynner på 0, og høyeste tillatte index er size() -1
	 * Dersom plassen er opptatt, skal elementene flyttes oppover.
	 * (Til dømes, dersom du har (a,b,c,d), og noen vil sette inn e på plass 2, skal vi ende opp med (a,b,e,c,d))
	 * @param tm TwitterMeldingen som skal settes inn i samlingen.
	 * @param index
	 * @return
	 */
	public boolean insert(TwitterMelding tm, int index);
	
	/**
	 * Fjerner TwitterMeldingen på angitt plass.
	 * Plassene skal fylles igjen etter sletting.
	 * @param index positivt heltall, som angir hvor noe skal slettes. Dersom index er større eller lik size() skal ikke noe gjøres, og false returneres.
	 * @return true dersom en slettet, false ellers.
	 */
	public boolean remove(int index);
	public TwitterMelding get(int index);	
	Collection<TwitterMelding> getTweetsWith(String word);
	public boolean deleteTweet(TwitterMelding tm);
	public TwitterMelding getTweet(String id);
	public boolean deleteTweet(String id);
}
