package models;

import java.io.Serializable;

public class Instructor implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 759842884393385981L;
	private String name;
	private int facultyIdNumber;
	private String department;
	
	/**
	 * Default constructor of Instructor class.
	 * 
	 * @param name
	 * @param facultyIdNumber
	 * @param department
	 */
	public Instructor(String name, int facultyIdNumber, String department) {
		this.name = name;
		this.facultyIdNumber = facultyIdNumber;
		this.department = department;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + facultyIdNumber;
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
		if (!(obj instanceof Instructor)) {
			return false;
		}
		Instructor other = (Instructor) obj;
		if (facultyIdNumber != other.facultyIdNumber) {
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

	public int getFacultyIdNumber() {
		return facultyIdNumber;
	}

	public void setFacultyIdNumber(int facultyIdNumber) {
		this.facultyIdNumber = facultyIdNumber;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
