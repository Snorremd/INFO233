package kravspesifikasjon;

import java.io.Serializable;

public interface TwitterMelding extends Serializable{
	public String getText();
	public TwitterBruker getBruker();
}
