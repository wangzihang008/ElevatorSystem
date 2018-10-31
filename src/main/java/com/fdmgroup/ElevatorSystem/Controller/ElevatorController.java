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
	private List<Thread> elevatorThreads;
	private List<Elevator> elevators;
	private int totalFloor;
	
	public ElevatorController(int totalFloor, int numElevator, int elevatorLimit) {
		this.totalFloor = totalFloor;
		elevators = new ArrayList<Elevator>();
		elevatorThreads = new ArrayList<Thread>();
		waitingPeople = new WaitingPeople();
		elevatorWiew = new ElevatorView(this);
		
		for(int i = 0; i < numElevator; i++) {
			Elevator e = new Elevator(elevatorLimit);
			e.setName("Elevator " + (i + 1));
			Thread t = new Thread(e);
			elevators.add(e);
			elevatorThreads.add(t);
			
			t.start();
		}
	}
	
	public void addNewPassengerIntoWaitingQueue(Person person) {
		
	}
}
