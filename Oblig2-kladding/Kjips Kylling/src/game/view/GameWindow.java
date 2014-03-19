package game.view;

import game.entity.types.Level;
import game.entity.types.Player;
import game.gfx.Lerret;
import game.gfx.LerretFactory;
import game.input.SimpleKeyboard;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameWindow extends JFrame {
	/**
	 * autogennet
	 */
	private static final long serialVersionUID = 4499966461488101017L;
	private static final String TITLE = "Kjip's Challenge";
	
	private LerretFactory lerretLeverandør;
	private Lerret lerret;
	
	public GameWindow(SimpleKeyboard keyboard, Player player){
		super(TITLE);
		lerretLeverandør = new LerretFactory().keyboard(keyboard).player(player);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		
	}
	
	public void loadLevel(Level level){
		if(null == lerret){
			this.lerret = lerretLeverandør.level(level).create();
			this.add(lerret);
			this.pack();
			this.setVisible(true);
		}
		else{
			lerret.setLevel(level);
		}
		
	}
		
	public boolean popupDeath(){
		Object[] options = {"Restart", "Exit"};
		int choice = JOptionPane.showOptionDialog(this, "You're dead.\nNow what do you do?", "Death and such",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, options, options[0]);
		
		System.out.printf("I think you chose %s%n", options[choice]);
		
		return 0==choice;
	}

	public void popupVictory() {
		JOptionPane.showMessageDialog(this, "DU HAR VUNNET", "SEIER!", JOptionPane.INFORMATION_MESSAGE);		
	}
	
	public void popupGameComplete() {
		JOptionPane.showMessageDialog(this, "Spill rundet", "Ferdig!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void popupGeneric(String title, String message){
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
