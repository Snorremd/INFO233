package kravspesifikasjon;

import java.util.Collection;

public interface TwitterBrukerCollection extends Samling<TwitterBruker>{
		public TwitterBruker getBruker(String id);
		
		public Collection<TwitterBruker> sortertEtterAntallMeldinger(boolean stigende);
		public Collection<TwitterBruker> sortertEtterAntallFollowers(boolean stigende);
		public Collection<TwitterBruker> sortertEtterAntallFriends(boolean stigende);
		
}
