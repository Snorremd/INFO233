/**
 * 
 */
package views;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.GridLayout;
import javax.swing.JButton;

/**
 * @author snorre
 *
 */
public class CourseView extends JPanel {
	private JTextField textField;
	public CourseView() {
		setLayout(new MigLayout("", "[][grow]", "[][][][][][grow][grow]"));
		
		JLabel lblStudent = new JLabel("Student:");
		add(lblStudent, "cell 0 0,alignx trailing");
		
		JLabel lblId = new JLabel("Undefined");
		add(lblId, "cell 1 0");
		
		JLabel lblName = new JLabel("Name:");
		add(lblName, "cell 0 1,alignx trailing");
		
		textField = new JTextField();
		add(textField, "cell 1 1,growx");
		textField.setColumns(10);
		
		JLabel lblCredits = new JLabel("Credits:");
		add(lblCredits, "cell 0 2,alignx trailing");
		
		JSpinner spinner = new JSpinner();
		add(spinner, "cell 1 2");
		
		JLabel lblGradePoints = new JLabel("Grade points:");
		add(lblGradePoints, "cell 0 3");
		
		JSpinner spinner_1 = new JSpinner();
		add(spinner_1, "cell 1 3");
		
		JLabel lblGpa = new JLabel("GPA:");
		add(lblGpa, "cell 0 4,alignx trailing");
		
		JLabel label = new JLabel("0");
		add(label, "cell 1 4");
		
		JPanel panel = new JPanel();
		add(panel, "cell 1 5,alignx center,growy");
		panel.setLayout(new MigLayout("", "[][]", "[]"));
		
		JButton btnSave = new JButton("Save");
		panel.add(btnSave, "cell 0 0");
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel, "cell 1 0");
		
		JPanel panel_1 = new JPanel();
		add(panel_1, "cell 1 6,alignx center");
		
		JButton btnEnrollInCourse = new JButton("Enroll in course");
		panel_1.add(btnEnrollInCourse);
	}

}
