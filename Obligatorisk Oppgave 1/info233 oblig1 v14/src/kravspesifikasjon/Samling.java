package kravspesifikasjon;

import java.io.Serializable;

public interface Samling<E> extends Serializable {
	/**
	 * Hvor stor er denne samlingen?
	 * @return et heltall som er større eller lik 0
	 */
	public int size();
	/**
	 * Setter inn en ny TwitterMelding i samlingen.
	 * Det er ikke definert hvor du setter inn meldingen.
	 * At dersom denne samlingen ikke har lov til å si at den er full.
	 * Dersom TwitterMeldingen allerede er i listen, skal du ikke sette den inn på nytt igjen, og returnere false;
	 * 
	 * @return true dersom meldingen ble satt inn korrekt, false dersom noe gikk galt.
	 */
	public boolean insert(E element);

	
	/**
	 * Setter inn en melding på angitt plass.
	 * Indexene begynner på 0, og høyeste tillatte index er size() -1
	 * Dersom plassen er opptatt, skal elementene flyttes oppover.
	 * Dersom index er større eller lik size(), eller mindre enn 0, skal du ikke gjøre noe, men returnere false; 
	 * (Til dømes, dersom du har (a,b,c,d), og noen vil sette inn e på plass 2, skal vi ende opp med (a,b,e,c,d))
	 * Dersom TwitterMeldingen allerede er i listen, skal du ikke sette den inn på nytt igjen, og returnere false;
	 * @param tm TwitterMeldingen som skal settes inn i samlingen.
	 * @param index indeksen du vil sette inn på
	 * @return true hvis tm ble satt inn, ellers false;
	 */
	public boolean insert(E element, int index);
	
	/**
	 * Fjerner TwitterMeldingen på angitt plass. (Indeksen begynner på 0)
	 * Plassene skal fylles igjen etter sletting.
	 * (Til dømes, dersom du har (a,b,e,c,d) og remove(2) blir kalt, skal du sitte igjen med (a,b,c,d)
	 * Dersom index er større eller lik size(), eller mindre enn 0, skal du ikke gjøre noe, men returnere false.
	 * @param index positivt heltall, som angir hvor noe skal slettes. Dersom index er større eller lik size() skal ikke noe gjøres, og false returneres.
	 * @return true dersom en slettet, false ellers.
	 */
	public boolean remove(int index);
	
	/**
	 * Henter ut twittermeldingen på angitt plass
	 * @param index plassen, skal være mellom 0 og size() - 1
	 * @return meldingen på plassen. Hvis og bare hvis index er mindre enn 0 eller større enn size() - 1, skal du returnere null;
	 */
	public E get(int index);	

}
