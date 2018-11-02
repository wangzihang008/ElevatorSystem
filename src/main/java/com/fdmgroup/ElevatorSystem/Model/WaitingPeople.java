package com.fdmgroup.ElevatorSystem.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class WaitingPeople {
	private Map<Elevator, Queue<Person>> waitingPeople;
	private List<Elevator> elevators;

	public List<Elevator> getElevators() {
		return elevators;
	}

	public void setElevators(List<Elevator> elevators) {
		this.elevators = elevators;
	}

	public Map<Elevator, Queue<Person>> getWaitingPeople() {
		return waitingPeople;
	}

	public void setWaitingPeople(Map<Elevator, Queue<Person>> waitingPeople) {
		this.waitingPeople = waitingPeople;
	}
	
	public void addWaitingQueue(Elevator elevator, Queue<Person> list) {
		waitingPeople.put(elevator, list);
	}

	public WaitingPeople(List<Elevator> elevators) {
		super();
		waitingPeople = new HashMap<Elevator, Queue<Person>>();
		for(Elevator e : elevators) {
			waitingPeople.put(e, new LinkedBlockingQueue<Person>());
		}
		this.elevators = elevators;
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
		if(getEmptyElevator() != null) {
			waitingPeople.get(getEmptyElevator()).add(person);
			return;
		}
		Elevator select = null;
		int time = -1;
		for(Elevator e : elevators) {
			if(e.isUp() == person.isGoingUp()) {
				if(select == null) {
					select = e;
					time = 1 + Elevator.getPickUpTime(e.getCurrentFloor(), person);
				}else if(person.isGoingUp()) {
					if(e.getCurrentFloor() < person.getStartFloor()) {
						if(time > Elevator.getPickUpTime(e.getCurrentFloor(), person)) {
							time = Elevator.getPickUpTime(e.getCurrentFloor(), person);
							select = e;
						}
					}
				}else {
					if(e.getCurrentFloor() > person.getStartFloor()) {
						if(time > Elevator.getPickUpTime(e.getCurrentFloor(), person)) {
							time = Elevator.getPickUpTime(e.getCurrentFloor(), person);
							select = e;
						}
					}
				}
			}else {
				if(select == null) {
					select = e;
					time = 1 + e.calculateTime(e.getPeople()) + 
							Elevator.getPickUpTime(e.getLastPassengerDestinationFloor(), person);
				}else {
					int timeOpposite = e.calculateTime(e.getPeople()) + 
							Elevator.getPickUpTime(e.getLastPassengerDestinationFloor(), person);
					if(time > timeOpposite) {
						time = timeOpposite;
						select = e;
					}
				}
			}
		}
		if(select == null) {
			for(Elevator e : elevators) {
				if(select == null) {
					select = e;
					time = 1 + e.calculateTime(e.getPeople()) + 
							Elevator.getPickUpTime(e.getLastPassengerDestinationFloor(), person);
				}else {
					if(time > e.calculateTime(e.getPeople()) + 
							Elevator.getPickUpTime(e.getLastPassengerDestinationFloor(), person)) {
						time = e.calculateTime(e.getPeople()) + 
								Elevator.getPickUpTime(e.getLastPassengerDestinationFloor(), person);
						select = e;
					}
				}
			}
		}
		waitingPeople.get(select).add(person);
	}

	
	@Override
	public String toString() {
		String result = "";
		
		for(Elevator e : elevators) {
			Iterator<Person> iterator = waitingPeople.get(e).iterator();
			if(iterator.hasNext()) {
				Person p = iterator.next();
				result += "Person " + p.toString();
			}
		}
		
		return result;
	}
	
	// This method stores people currently in the waitingPeople map into a queue
	public List<Person> getQueue() {
		List<Person> myqueue = new ArrayList<Person>();
		for (Queue<Person> queue: waitingPeople.values()) {
		    Iterator<Person> iterator = queue.iterator();
		    while(iterator.hasNext()) {
		    	myqueue.add(iterator.next());
		    }
		}
		return myqueue;
	}
	
	public int getSize() {
		int size = 0;
		for (Queue<Person> queue: waitingPeople.values()) {
		    size += queue.size();
		}
		return size;
	}

}
