package com.example;

public class Patient {
 
	private final Integer id;
	private final String name;
	private final Integer age;
	private final String disease;
	private final String address;
	public Patient(Integer id, String name,Integer age,String disease,String address) {
        this.id = id;
        this.name = name; 
        this.age = age;
        this.disease = disease;
        this.address = address;
    }
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Integer getAge() {
		return age;
	}
	public String getDisease() {
		return disease;
	}
	public String getAddress() {
		return address;
	}
}
