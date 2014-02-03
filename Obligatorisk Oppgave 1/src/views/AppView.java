/**
 * 
 */
package views;

import javax.swing.JFrame;

/**
 * @author snorre
 *
 */
public class AppView extends JFrame {
	
	public AppView() {
		setTitle("Kvitter");
		getContentPane().add(new StreamPanel());
		pack();
		
	}
	
	

}
