package game.view.gfx;

import game.controller.input.SimpleKeyboard;
import game.entity.types.Level;
import game.entity.types.Player;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * Dette er en viktig del av grafikkmotoren.
 * Den er lerretet som ting blir malt på.
 * 
 * Siden to lerret er equals hvis og bare hvis de er samme lerret, er det ikke overkjørt hverken hashCode eller equals
 * 
 * @author Haakon Løtveit (haakon.lotveit@student.uib.no)
 *
 */
public class Lerret extends Canvas {

	/**
	 * autogenerated 
	 */
	private static final long serialVersionUID = -8579638927397401101L;
	
	protected final int TILESIZE;
	protected Level brett;
	protected Player spiller;
	protected Dimension størrelseIPiksler;
	protected PaintingThread painter;
	
	/**
	 * Dette skaper et nytt lerret å tegne på. Du trenger det til å kunne vise grafikk.
	 * @param level brettet som skal brukes.
	 * @param player spilleren som skal brukes.
	 * 	Standardimplementasjonen av Player, SimplePlayer har en getInstance() metode som gjør at du ikke trenger å hive den inn her.
	 *	Men dersom du hadde gjort det, kunne du ikke lengre byttet ut SimplePlayer med en ny og bedre implementasjon.
	 *	Kanskje du vil lagre replays eller noe, og derfor vil lagre tastetrykk? Det kan ikke SimplePlayer, men kanskje en annen implementasjon kan.
	 * 	Siden vi nå krever at en instans av Player skal gis, kan du derfor endre dette veldig enkelt.
	 * @param keyboard tastaturlytteren som skal brukes. SimpleKeyboard er ikke en singleton, siden en kan tenkes at en vil ha flere tastaturlyttere, som kan kobles til flere steder.
	 */
	public Lerret(Level level, Player player, SimpleKeyboard keyboard){		
		TILESIZE = level.tilesize();		
		brett = level;
		this.størrelseIPiksler = calculateSize();
		spiller = player;
		
		setSizes();
		
		this.addKeyListener(keyboard);
		this.setFocusable(true);
		this.requestFocusInWindow();
		painter = new PaintingThread(this);
		
	}
	
	public void setLevel(Level level){
		this.brett = level;
		this.størrelseIPiksler = calculateSize();
		this.setSizes();
	}
	
	public void tick(){
		brett.tick();
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
//		RENDER HERE
		g.fillRect(0, 0, størrelseIPiksler.width, størrelseIPiksler.height);
		
		/*
		 * Hvis du er her i koden er det sannsynlig at du lurer på hvordan renderingen fungerer.
		 * Sånn halvveis forenklet, ber vi først brettet male seg selv (og lerretet anser monstre og slikt på brettet som en del av brettet) til lerretet.
		 * Deretter, oppå brettet, ber vi spilleren male seg selv. Derfor ser det ut som om spilleren er oppå brettet.
		 * Sånn rekursivt, så TileLevel først male alle tiles fra (0,0) til (n_x-1, n_y-1) så male andre ting (nudge nudge, wink wink), og så returnere.
		 * 
		 * Siden vi ikke vet noe om hvordan brettet ser ut innvendig, eller hvordan ting skal gjøres der, så gir vi bare brettet malerpenselen, og ber den gjøre tingen sin.
		 * Da kan vi senere bytte utnew Dimension(level.tileColumns() * TILESIZE, level.tileRows() * TILESIZE); hele brett-implementasjonen med noe nytt og bedre (når jeg skrev motoren gjorde jeg det, ca. 3 ganger) uten at noe annet må endre enn at det står new TileLevel2000 istedenfor new TileLevel et sted.
		 * Det gjør at designet blir veldig smidig, og du kan rote rundt mer effektivt.
		 */
		
		if(null != brett){
			brett.render(g);
		}
		
		if(null != spiller){
			spiller.render(g);
		}
		
//		END RENDER
		g.dispose();
		bs.show();
		
	}
	
	private Dimension calculateSize(){
		return new Dimension(this.brett.tileColumns() * TILESIZE, this.brett.tileRows() * TILESIZE);
	}
	
	private void setSizes(){
		this.setPreferredSize(størrelseIPiksler);
		this.setMinimumSize(størrelseIPiksler);
		this.setMaximumSize(størrelseIPiksler);
	}
}