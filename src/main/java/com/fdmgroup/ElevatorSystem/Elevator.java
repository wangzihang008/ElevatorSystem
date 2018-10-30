package com.fdmgroup.ElevatorSystem;

import java.util.LinkedList;
import java.util.Queue;

public class Elevator implements Runnable{
	private String name;
	private boolean isUp;
	private int currentFloor;
	private Queue<Person> people;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isUp() {
		return isUp;
	}
	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}
	public int getCurrentFloor() {
		return currentFloor;
	}
	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}
	public Queue<Person> getPeople() {
		return people;
	}
	public void setPeople(Queue<Person> people) {
		this.people = people;
	}
	
	public Elevator() {
		super();
		isUp = true;
		currentFloor = 1;
		people = new LinkedList<Person>();
	}

	@Override
	public String toString() {
		String result = name + " is at Level " + currentFloor + ". ";
		result += "There are " + people.size() + " in this elevator. ";
		if(isUp) {
			result += "It is going up. "; 
		}else {
			result += "It is going down. "; 
		}
		
		return result;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
