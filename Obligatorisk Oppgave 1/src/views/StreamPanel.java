/**
 * 
 */
package views;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

/**
 * @author snorre
 *
 */
public class StreamPanel extends JPanel {
	
	private JTextArea tweetArea;
	
	public StreamPanel() {
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		tweetArea = new JTextArea();
		tweetArea.setEditable(false);
		scrollPane.setViewportView(tweetArea);
		tweetArea.setColumns(100);
		tweetArea.setRows(40);
		tweetArea.setName("tweetArea");
	}

	/**
	 * @return the tweetArea
	 */
	public JTextArea getTweetArea() {
		return tweetArea;
	}

}
