package kravspesifikasjon;

import java.util.Collection;

public interface TwitterBrukerCollection extends Samling<TwitterBruker>{
		/**
		 * Returnerer en bruker med gitt ID. IDer er unike for hver bruker.
		 * Det skal derfor aldri være to brukere med samme ID.
		 * @param id den unike ID-en til brukeren.
		 * @return en TwitterBruker med gitt ID hvis en slik en finnes, ellers null.
		 */
		public TwitterBruker getBruker(String id);
		
		/**
		 * Sorterer etter antall meldinger brukerene har sendt.
		 * @param stigende Hvis sann, så skal samlingen være stigende, ellers skal den være synkende. (0,1,2,3 = stigende, 3,2,1,0 = synkende)
		 * @return en samling sortert etter antall meldinger
		 */
		public Collection<TwitterBruker> sortertEtterAntallMeldinger(boolean stigende);
		
		/**
		 * Sorterer etter antall followers brukeren har
		 * @param stigende Hvis sann, så skal samlingen være stigende, ellers skal den være synkende. (0,1,2,3 = stigende, 3,2,1,0 = synkende)
		 * @return en samling sortert etter antall followers
		 */
		public Collection<TwitterBruker> sortertEtterAntallFollowers(boolean stigende);
		
		/**
		 * Sorterer etter antall friends brukeren har.
		 * @param stigende Hvis sann, så skal samlingen være stigende, ellers skal den være synkende. (0,1,2,3 = stigende, 3,2,1,0 = synkende)
		 * @return en samling sortert etter antall friends.
		 */
		public Collection<TwitterBruker> sortertEtterAntallFriends(boolean stigende);
		
}
