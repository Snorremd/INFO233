/**
 * 
 */
package controllers;

import io.CourseReaderWriter;
import Exceptions.CourseFullException;
import Exceptions.StudentAlreadyRegisteredException;
import models.Course;
import models.Instructor;
import models.Student;

/**
 * @author snorre
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Instructor instructor = new Instructor("Bjornar Tessem", 12, "Department of Information and Media Sciences");
		Course course = new Course("Advanced programming", 1, instructor);
		try {
			course.addStudent(new Student("Bob", 0, 100, 400));
		} catch (CourseFullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StudentAlreadyRegisteredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CourseReaderWriter.writeCourse(course);
		
		Course courseCopied = CourseReaderWriter.readCourse();
		
		if(course.equals(courseCopied)) System.err.println("Great success!");

	}
	

}
