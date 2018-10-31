package com.fdmgroup.ElevatorSystem.Controller;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.ElevatorSystem.Model.Elevator;
import com.fdmgroup.ElevatorSystem.Model.Person;
import com.fdmgroup.ElevatorSystem.Model.State;
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
		elevatorWiew = new ElevatorView(this);
		
		for(int i = 0; i < numElevator; i++) {
			Elevator e = new Elevator(elevatorLimit);
			e.setName("Elevator" + (i + 1));
			Thread t = new Thread(e);
			elevators.add(e);
			elevatorThreads.add(t);
			
			t.start();
		}
		
		waitingPeople = new WaitingPeople(elevators);
	}
	
	public Elevator getEmptyElevator() {
		for(Elevator e : elevators) {
			if(e.getPeople().isEmpty()) {
				return e;
			}
		}
		return null;
	}
	
	public void addNewPassenger(Person person) {
		
	}

	public void addNewPassengerIntoWaitingQueue(Person person) {
		int min = 0;
		Elevator elevator = elevators.get(0);
		if(getEmptyElevator() != null) {
			for(Elevator e : elevators) {
				if(e.isEmpty()) {
					if(e.getCurrentFloor() - person.getStartFloor() == 0) {
						if(min == 0) {
							min = State.SPEEDUP.getTime() + State.SLOWDOWN.getTime() +
									State.SERVICE.getTime() + person.getFloorsToGo();
						}else if(min > 0 && min > State.SPEEDUP.getTime() + State.SLOWDOWN.getTime() +
								State.SERVICE.getTime() + person.getFloorsToGo()) {
							min = State.SPEEDUP.getTime() + State.SLOWDOWN.getTime() +
									State.SERVICE.getTime() + person.getFloorsToGo();
						}
					}
				}else {
					
				}
			}
		}else {
			
		}
	}
	
}
