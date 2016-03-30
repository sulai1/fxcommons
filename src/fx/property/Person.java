package fx.property;

import java.time.LocalDate;

public class Person {

	private int id;
	private String name;
	private LocalDate date;
	private String surname;

	public Person(int id,String name, String surname, LocalDate localDate) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.date = localDate;
	}

	@Override
	public String toString() {
		return String.format("%d, %s, %s, %s", id,name,surname, date.toString());
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the localDate
	 */
	public LocalDate getTimeStamp() {
		return date;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

}
