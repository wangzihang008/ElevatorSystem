package com.fdmgroup.ElevatorSystem.Model;

public class Person {
	
	private int startFloor;
	private int destinationFloor;
	private boolean isWaiting;
	
	public Person(int startFloor, int destinationFloor) {
		
		this.startFloor = startFloor;
		this.destinationFloor = destinationFloor;
		isWaiting = true;
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
	
	@Override
	public String toString() {
		
		String result = "";
		result += "Person: from floor " + this.getStartFloor() + " to floor " + this.getDestinationFloor();
		if (isWaiting) {
			result += ", is now waiting";
		}else {
			result += ", is now on board";
		}
		return result;
	}

}
