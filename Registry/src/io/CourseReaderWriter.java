/**
 * 
 */
package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import models.Course;

/**
 * @author snorre
 *
 */
public class CourseReaderWriter {

	/**
	 * Write a Course object to file.
	 * 
	 * @param course to write to file
	 */
	public static void writeCourse(Course course) {
		ObjectOutputStream oos = null;
		FileOutputStream fout;
		
		try{
			System.out.println("." + File.separator + "course.obj");
			fout = new FileOutputStream("." + File.separator + "course.obj", false);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(course);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(oos != null) oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Read a course object from file.
	 * 
	 * @return the course object or null
	 */
	public static Course readCourse() {
		ObjectInputStream objectInputStream = null;
		FileInputStream fileInputStream;
		Course course = null;
		try {
			fileInputStream = new FileInputStream(new File("." + File.separator + "course.obj"));
			objectInputStream = new ObjectInputStream(fileInputStream);
			course = (Course) objectInputStream.readObject();
		} catch(FileNotFoundException ex) {
			System.out.println("Could not locate course.obj file.");
		} catch(IOException ex) {
			System.out.println("Could not open course.obj file.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Wrong class loaded.");
		} finally{
			if(objectInputStream != null)
				try {
					objectInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return course;
	}
}
