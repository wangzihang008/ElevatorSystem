package com.fdmgroup.ElevatorSystem.Controller;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.ElevatorSystem.Model.Elevator;
import com.fdmgroup.ElevatorSystem.Model.Person;
import com.fdmgroup.ElevatorSystem.Model.WaitingPeople;
import com.fdmgroup.ElevatorSystem.View.ElevatorView;

public class ElevatorController {
	private ElevatorView elevatorWiew;
	private WaitingPeople waitingPeople;
	private List<Elevator> elevators;
	private int totalFloor;
	
	public ElevatorController(int totalFloor, int numElevator, int elevatorLimit) {
		this.totalFloor = totalFloor;
		elevators = new ArrayList<Elevator>();
		waitingPeople = new WaitingPeople();
		elevatorWiew = new ElevatorView(this);
		
		for(int i = 0; i < numElevator; i++) {
			Elevator e = new Elevator(elevatorLimit);
			elevators.add(e);
		}
	}
	
	public void addNewPassengerIntoWaitingQueue(Person person) {
		
	}
}
