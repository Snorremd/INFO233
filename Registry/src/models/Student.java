package models;

import java.io.Serializable;

public class Student implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -917555435834084607L;
	private String name;
	private int number;
	private int credits;
	private int gradePoints;
	
	/**
	 * Constructor for Student class
	 * 
	 * @param name
	 * @param number
	 * @param credits
	 * @param gradePoints
	 */
	public Student(String name, int number, int credits, int gradePoints) {
		this.name = name;
		this.number = number;
		this.credits = credits;
		this.gradePoints = gradePoints;
	}
	
	/**
	 * Calculates the grade point average (GPA) of the student
	 * i.e. the grade points divided by number of credits.
	 * 
	 * @return the grade point average of the student
	 */
	public double calcGradePointAverage() {
		return gradePoints / credits;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
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
		if (!(obj instanceof Student)) {
			return false;
		}
		Student other = (Student) obj;
		if (number != other.number) {
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public int getGradePoints() {
		return gradePoints;
	}
	public void setGradePoints(int gradePoints) {
		this.gradePoints = gradePoints;
	}
	
	

}
