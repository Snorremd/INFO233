package kravspesifikasjon;

import java.util.Collection;

public interface TwitterMeldingCollection extends Samling<TwitterMelding> {
	
	/**
	 * Returner alle TwitterMeldingene med et bestemt ord i seg.
	 * @param word ordet som skal søkes etter.
	 * @return en samling med meldinger. Se {@link http://docs.oracle.com/javase/7/docs/api/java/util/Collection.html} for mer informasjon.
	 */
	Collection<TwitterMelding> getTweetsWith(String word);
	
	/**
	 * Slett denne meldingen fra listen hvis den eksisterer.
	 * @param tm meldingen
	 * @return true hvis den ble slettet, ellers false
	 */
	public boolean deleteTweet(TwitterMelding tm);
	
	/**
	 * Henter ut en TwitterMelding med en gitt ID
	 * @param id den unike IDstrengen til Tweeten.
	 * @return TwitterMeldingen dersom den eksisteren. Dersom den ikke eksisterer, returner null.
	 */
	public TwitterMelding getTweet(String id);
	
	/**
	 * Sletter TwitterMeldingen med oppgitt ID.
	 * @param id den unike id-strengen til alle TwitterMeldinger.
	 * @return true dersom den ble slettet, og ellers false
	 */
	public boolean deleteTweet(String id);
	
	/**
	 * Hent ut en samling meldinger, sortert på tid.
	 * Dersom to meldinger skulle vært publisrt på samme tid, er det uviktig hvilken av dem som skal ut først.
	 * @param nyesteFørst true dersom de nyeste meldingene skal ut først, false dersom de eldste skal ut først.
	 * @return en samling sortert etter kriteriene
	 */
	public Collection<TwitterMelding> sortertEtterTid(boolean nyesteFørst);
	
	/**
	 * Hent ut en samling meldinger, sortert på lengde.
	 * Med lengde menes antall tegn.
	 * Dersom to meldinger har samme lengde er det uviktig hvilken av dem som skal først i samlingen.
	 * @param lengsteFørst true dersom de lengste meldingene skal ut først, false dersom de korteste skal ut først.
	 * @return en samling sortert etter kriteriene
	 */
	public Collection<TwitterMelding> sortertEtterLengde(boolean lengsteFørst);
	
	/**
	 * Henter ut alle meldingene fra en bestemt bruker
	 * @param bruker brukeren vi er ute etter
	 * @return en ny TwitterMeldingCollection fylt med alle meldingene fra gitt bruker, og ingen andre. 
	 */
	public TwitterMeldingCollection meldingerFra(TwitterBruker bruker);
}
