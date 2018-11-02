package com.fdmgroup.ElevatorSystem.Model;

import java.io.Serializable;

public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9044567465794346158L;
	private String name;
	private int startFloor;
	private int destinationFloor;
	private boolean isWaiting;
	private Elevator currentElevator;

	public Person(int startFloor, int destinationFloor) {

		this.startFloor = startFloor;
		this.destinationFloor = destinationFloor;
		isWaiting = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartFloor() {

		return startFloor;
	}

	public int getDestinationFloor() {

		return destinationFloor;
	}

	public boolean getIsWaiting() {

		return isWaiting;
	}

	public void setIsWaiting(boolean isWaiting) {
		this.isWaiting = isWaiting;
	}

	public boolean isGoingUp() {
		return destinationFloor - startFloor > 0;
	}

	public int getFloorsToGo() {
		if (startFloor - destinationFloor > 0) {
			return startFloor - destinationFloor;
		} else {
			return destinationFloor - startFloor;
		}
	}

	@Override
	public String toString() {

		String result = "";
		result += "Person: from floor " + this.getStartFloor() + " to floor " + this.getDestinationFloor();
		if (isWaiting) {
			result += ", is now waiting";
		} else {
			result += ", is now on board";
		}
		return result;
	}

	public Elevator getCurrentElevator() {
		return currentElevator;
	}

	public void setCurrentElevator(Elevator currentElevator) {
		this.currentElevator = currentElevator;
	}

}
