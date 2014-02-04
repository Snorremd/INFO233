/**
 * 
 */
package controllers;

import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import views.AppView;

/**
 * @author Snorre Magnus Davøen
 *
 */
public class AppController {

	private TweetConsumer tweetController;
	private AppView appView;
	private HashMap<String, Component> componentMap;

	public AppController() {

		appView = new AppView();
		createComponentMap();
		appView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				AppController controller = new AppController();
				controller.startStream();

			}
		});
	}

	private void startStream() {
		System.out.println("This object is: " + this);
		tweetController = new TweetAreaController(this);
		tweetController.startTwitterStream();
		appView.setVisible(true);
	}

	private void createComponentMap() {
		componentMap = new HashMap<String,Component>();
		Component[] components = appView.getContentPane().getComponents();
		for (int i=0; i < components.length; i++) {
			componentMap.put(components[i].getName(), components[i]);
		}
	}

	public Component getComponentByName(String name) {
		return getComponentByName(appView.getRootPane(), name);
	}

	public Component getComponentByName(Container root, String name) {
		for (Component c : root.getComponents()) {
			if (name.equals(c.getName())) { return c; } if (c instanceof Container) { Component result = getComponentByName((Container) c, name); if (result != null) { return result; } } } return null; } 



}
