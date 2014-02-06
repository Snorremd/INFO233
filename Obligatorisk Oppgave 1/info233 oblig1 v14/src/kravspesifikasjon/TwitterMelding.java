package kravspesifikasjon;

import java.io.Serializable;
import java.util.Calendar;

public interface TwitterMelding extends Serializable{
	/**
	 * Henter ut en meldingstekst.
	 * Merk at \u0000 ting skal gjøres om til korrekte tegn
	 * @return meldingsteksten
	 */
	public String getMeldingsTekst();
	/**
	 * Henter ut brukeren som sendte teksten.
	 * Merk at to meldinger som har samme forfatter skal returnere samme brukerobjektet, ikke bare et slik at bruker1.equals(bruker2) holder, men bruker1 == bruker2
	 * @return brukeren som skrev meldingen
	 */
	public TwitterBruker getBruker();
	/**
	 * teller opp antall tegn i meldingen.
	 * (For dere som har hacket i C, er dette et annet tall en antall bytes.)
	 * @return antall tegn i meldingen totalt. mellom 0-160
	 */
	public int size();
	
	/**
	 * Når meldingen ble sendt.
	 * Sjekk java sin Calendar dokumentasjon.
	 * @return et Calendar objekt som representerer når meldingen ble sendt.
	 */
	public Calendar dato();
	
	/**
	 * Alle meldinger har en unik ID som dere må ta vare på.
	 * @return Den unike ID-en.
	 */
	public String getID();
}
