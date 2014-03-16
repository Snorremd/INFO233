package game.gfx;

import game.entity.types.Level;
import game.entity.types.Player;

/**
 * Dette er en klasse for å lett kunne lage objekter av Lerret-klassen.
 * <br>
 * Lerret er et litt søkt eksempel for denne typen funksjonalitet,
 * men jeg håper at dere ser poenget bak denne typen fabrikker.
 * <br>
 * Du setter variablene (og du kan kjede dem, så du kan sette flere på samme linje),
 * og så kan du be fabrikken om å spytte ut nye objekter når du trenger dem.
 * <br>
 * Den viser i tillegg hvordan du bør hashe ting, som blir nytt til denne obligen.
 * Husk at alle fornuftige klasser overkjøre equals, hashCode og toString. Ha det gøy!
 * <br>
 * Et eksempel på bruk kan være:
 * <br><code>
 * LerretFactory lf = new LerretFactory();
 * lf.title("Tittel").tilesize(64).horizontalTiles(5).verticalTiles(5).create();
 * lf.title("Tittel2").create();
 * </code><br>
 * Det ville lagd to lerret-objketer, begge like store, med to forskjellige titler.
 * 
 * @author Haakon Løtveit (haakon.lotveit@student.uib.no)
 *
 */
public class LerretFactory extends java.lang.Object implements java.lang.Cloneable{
	protected String title;
	protected Level level;
	protected Player player;
	
	/**
	 * En enkel konstruktør.
	 * Den setter tilesize, horizontalTiles og verticalTiles til 0,
	 * og setter title til "NOTITLE"
	 */
	public LerretFactory(){
		title = "NOTITLE";
	}
	
	
	/**
	 * Lar deg sette tittelen på vinduet. Det er ikke noen mulighet til å sette ikonet.
	 * @param title En streng som blir tittelen på vinduet når du ber om lerretet.
	 * @return denne fabrikken, slik at du kan kjede sammen kall.
	 */
	public LerretFactory title(String title){
		this.title = title;
		return this;
	}
	
	/**
	 * Lar deg sette Playerobjektet som skal styre brettet.
	 * @param player et spillerobjekt som skal styre en spiller rundt på et brett.
	 * @return denne fabrikken, slik at du kan kjede sammen kall.
	 */
	public LerretFactory player(Player player){
		this.player = player;
		return this;
	}
	
	/**
	 * Lar deg setttet Levelobjektet som skal være banen på spillet.
	 * @param level brettet som skal lastes inn.
	 * @return denne fabrikken, slik at du kan kjede sammen kall.
	 */
	public LerretFactory level(Level level){
		this.level = level;
		return this;
	}
	
	/**
	 * Det er her det store skjer.
	 * Basert på hvilke verdier du har kalt, blir et nytt Lerret objekt returnert her.
	 * Dersom du kaller create() to ganger for du to lerret med de samme argumentene. Du får *ikke* det samme objektet to ganger, men to nye.
	 *  
	 * @return et nytt Lerret-objekt. Dersom du IKKE har satt en Player, et Level, eller tileSize, verticalTiles eller horizontalTiles til lovlige verdier (dvs tall over 0), returneres null
	 */
	public Lerret create(){
		if(null == player ||
		   null == level){
			return null;
		}
		return new Lerret(title,
				          level,
				          player);
	}
	
	@Override
	public int hashCode(){
		/* denne metoden er tatt rett ut av Effective Java, side 47. */
		
		int result = 17;
		result = 31 * result + player.hashCode(); 
		result = 31 * result + level.hashCode();
		result = 31 * result + title.hashCode();
		
		return result;
	}
	
	/*
	 * Jeg har sett en del bruk av getClass for å sammenligne.
	 * Det er feil ting å gjøre. Bruk instanceof istedenfor, siden den takler subklassing og slikt
	 * Det er en vane som sparer deg for en plutselige bugs du aldri så komme.
	 * 
	 * Hvis du *vet* at getClass er bedre en instanceof for deg i et gitt tilfelle, bruk getClass.
	 * Ellers, bruk instanceof.
	 * (Se Liskov Substitution Principle hvis du vil vite mer)
	 * 
	 * I tillegg, hashCode-metoden din må hashe på alle de samme feltene som equals bruker, og bare de samme. 
	 */
	@Override
	public boolean equals(Object obj){
		if(obj instanceof LerretFactory){
			LerretFactory lf = (LerretFactory) obj;
			return lf.title.equals(this.title) &&
				   lf.level.equals(this.level);
		}
		
		return false;
	}
	
	@Override
	public String toString(){
		return String.format("LerretFactory<title: %s, horizontalTiles: %d, verticalTiles %s, tilesize %d>",
							 title);
		}
	
	/*
	 * Cloning per cloneable er sært.
	 * Veldig sært. Cthulu var på utplassering hos Sun fra NAV når Cloneable ble implementert.
	 * Derfor, hvis alt virker, vær glad.
	 * Hvis ikke, vurder å lag en copy() metode eller lignende istedenfor.
	 * 
	 * Flere java-eksperter har gått ut å sagt at clone/cloneable er knekt hinsides alt håp.
	 * 
	 * En tommelfingerregel er at dersom du vurderer å bruke clone, lag en konstruktør istedenfor, av typen:
	 * public Foo(Foo toCopy)
	 * 
	 * Du kan jo sammenligne selv.
	 */
	@Override
	public LerretFactory clone(){
		try{			
			return (LerretFactory) super.clone();
		}
		catch(CloneNotSupportedException wtf){
			// Dette skal ikke kunne gå an å skje, da java.lang.Object SKAL implementere clone.
			// Men siden det kan være en feil i JVMen eller kompilatoren, er det greit å sjekke.			
			throw new AssertionError("java.lang.Object reports that it doesn't support cloning");
		}
	}
	/* Vanligvis har en konstruktørene sammen, dette er et unntak. */
	public LerretFactory(LerretFactory toClone){
		title = toClone.title;
		player = toClone.player;
		level = toClone.level;
	}
	
	/* 
	 * Merk at vi her bare kopierer rett over, men det vil IKKE være en god ide i det generelle tilfellet.
	 * Sett at vi har en klasse ArrayFoo som har et array i seg slik:
	 * class ArrayFoo{
	 *   private int[] magicNumbers;
	 *   
	 *   // Og så konstruktører, metoder, osv.
	 * }
	 * 
	 * La oss så late som om vi kopierte slik:
	 * 
	 * public ArrayFoo(ArrayFoo toCopy){
	 * 	 this.magicNumbers = toCopy.magicNumbers;
	 * }
	 * 
	 * Dersom en kopierer på den måten, og en verdi magicNumbers i en av Fooene endrer seg, vil
	 * *ALLE* som har blitt kopiert på den måten endre seg.
	 * Dette er en Dum Ting™. Du må derfor passe på å kopiere objekter defensivt.
	 * String og ints kan ikke endre seg, og kan derfor kopieres rett over.
	 * Arrays og objekter og slikt kan IKKE uten videre kopieres rett over. 
	 * (Gjett om clone gjør dette for deg automatisk?)
	 * 
	 * Se http://www.artima.com/intv/bloch13.html for mer om dette.
	 */	
	
}
