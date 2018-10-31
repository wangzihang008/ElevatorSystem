package com.fdmgroup.ElevatorSystem.Model;

public class Person {
	
	private int startFloor;
	private int destinationFloor;
	
	public Person(int startFloor, int destinationFloor) {
		
		this.startFloor = startFloor;
		this.destinationFloor = destinationFloor;
	}
	
	public int getStartFloor() {
		
		return startFloor;
	}
	
	public int getDestinationFloor() {
		
		return destinationFloor;
	}

}
