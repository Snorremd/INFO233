/**
 * 
 */
package controllers;

import javax.swing.SwingUtilities;

import views.AppView;
import views.CourseView;

/**
 * @author snorre
 *
 */
public class AppController {
	
	public AppController() {
		
		AppView appView = new AppView();
		appView.getContentPane().add(new CourseView());
		appView.pack();
		appView.setVisible(true);
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppController app = new AppController();
            }
        });

	}

}
