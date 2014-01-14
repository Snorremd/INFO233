/**
 * 
 */
package models;

import java.io.Serializable;

import Exceptions.CourseFullException;
import Exceptions.StudentAlreadyRegisteredException;
import Exceptions.StudentNotFoundException;

/**
 * @author snorre
 *
 */
public class Course implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 681538967467921781L;
	private String name;
	private int registrationCode;
	private final int maxStudentNumber = 35;
	private Instructor instructor;
	private byte numberOfStudents;
	private Student[] students;
	
	/**
	 * Default constructor for Course.
	 * 
	 * @param name
	 * @param registrationCode
	 * @param instructor
	 */
	public Course(String name, int registrationCode, Instructor instructor) {
		this.name = name;
		this.registrationCode = registrationCode;
		this.instructor = instructor;
		this.students = new Student[maxStudentNumber];
		this.numberOfStudents = 0;
	}

	/**
	 * Searches for student in course. Returns the
	 * student object if found, else returns null.
	 * 
	 * @param id - the id number of the student
	 * @return the student object if found, or null
	 */
	public Student searchForStudent(int id) {
		for (int i = 0; i < students.length; i++) {
			Student student = students[i];
			if(student == null) {
				return null;
			} else if(student.getNumber() == id) {
				return student;
			}	
		}
		return null;
	}
	
	/**
	 * Add a student to the course given that the course is not full and
	 * that the student is not already a member of the course.
	 * 
	 * @param student
	 * @throws CourseFullException
	 * @throws StudentAlreadyRegisteredException
	 */
	public void addStudent(Student student) throws CourseFullException, StudentAlreadyRegisteredException {
		if(numberOfStudents > maxStudentNumber) {
			throw new CourseFullException();
		} else if(searchForStudent(student.getNumber()) != null) {
			throw new StudentAlreadyRegisteredException();
		} else {
			students[numberOfStudents] = student;
			numberOfStudents =+ 1;
		}
	}
	
	/**
	 * Removes a student from the course if the student is
	 * present in the student list.
	 * 
	 * @param student
	 * @throws StudentNotFoundException
	 */
	public void removeStudent(Student student) throws StudentNotFoundException {
		if(searchForStudent(student.getNumber()) != null) {
			Student[] newArray = new Student[maxStudentNumber];
			int indexCounter = 0;
			for (int i = 0; i < students.length; i++) {
				if(student.getNumber() != students[i].getNumber()) {
					newArray[indexCounter] = students[i];
					indexCounter++;
				}
			}
			students = newArray;
			numberOfStudents--;
		} else {
			throw new StudentNotFoundException();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + registrationCode;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Course)) {
			return false;
		}
		Course other = (Course) obj;
		if (registrationCode != other.registrationCode) {
			return false;
		}
		return true;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(int registrationCode) {
		this.registrationCode = registrationCode;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public byte getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(byte numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public Student[] getStudents() {
		return students;
	}

	public void setStudents(Student[] students) {
		this.students = students;
	}

	public int getMaxStudentNumber() {
		return maxStudentNumber;
	}
}
