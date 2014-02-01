/**
 * 
 */
package views;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * @author snorre
 *
 */
public class AppView extends JFrame {
	public AppView() {
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
	
	

}
