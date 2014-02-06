package kravspesifikasjon;

import java.io.Serializable;
import java.util.Calendar;

public interface TwitterMelding extends Serializable{
	public String getMeldingsTekst();
	public TwitterBruker getBruker();
	public int size();
	public Calendar dato();
	public String getID();
}
