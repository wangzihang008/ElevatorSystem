package com.fdmgroup.ElevatorSystem.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class WaitingPeople {
	private Map<String, Queue<Person>> waitingPeople;
	private List<Elevator> elevators;
	private List<Person> waitingPeopleForElevatorAviliable;

	public List<Person> getWaitingPeopleForElevatorAviliable() {
		return waitingPeopleForElevatorAviliable;
	}

	public void setWaitingPeopleForElevatorAviliable(List<Person> waitingPeopleForElevatorAviliable) {
		this.waitingPeopleForElevatorAviliable = waitingPeopleForElevatorAviliable;
	}

	public List<Elevator> getElevators() {
		return elevators;
	}

	public void setElevators(List<Elevator> elevators) {
		this.elevators = elevators;
	}

	public Map<String, Queue<Person>> getWaitingPeople() {
		return waitingPeople;
	}

	public void setWaitingPeople(Map<String, Queue<Person>> waitingPeople) {
		this.waitingPeople = waitingPeople;
	}
	
	public void putWaitingQueue(Elevator elevator, Queue<Person> list) {
		waitingPeople.put(elevator.getName(), list);
		notifyAllElevator();
	}
	
	public void addWaitingPersonIntoQueue(Elevator elevator, Person person) {
		waitingPeople.get(elevator.getName()).add(person);
		notifyAllElevator();
	}

	public WaitingPeople(List<Elevator> elevators) {
		super();
		waitingPeople = new HashMap<String, Queue<Person>>();
		for(Elevator e : elevators) {
			waitingPeople.put(e.getName(), new PriorityQueue<Person>(new PeopleWaitingComparator()));
		}
		this.elevators = elevators;
		waitingPeopleForElevatorAviliable = new ArrayList<Person>();
		notifyAllElevator();
	}
	
	public boolean isAllElevatorFull() {
		for(Elevator e : elevators) {
			if(!e.isFull()) {
				return false;
			}
		}
		return true;
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
			waitingPeople.get(getEmptyElevator().getName()).add(person);
			notifyAllElevator();
			return;
		}
		if(isAllElevatorFull()) {
			waitingPeopleForElevatorAviliable.add(person);
			notifyAllElevator();
			return;
		}
		Elevator select = null;
		int time = -1;
		for(Elevator e : elevators) {
			if(!e.isFull()) {
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
		}
		if(select == null) {
			for(Elevator e : elevators) {
				if(!e.isFull()) {
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
		}
		waitingPeople.get(select.getName()).add(person);
		notifyAllElevator();
	}
	
	public void removeWaitingPerson(Elevator elevator, Person person) {
		waitingPeople.get(elevator.getName()).remove(person);
		notifyAllElevator();
	}
	
	public void notifyAllElevator() {
		for(Elevator e : elevators) {
			e.setWaitingPeople(this);
		}
	}

	public Person getFirstWaitingPerson(Elevator elevator) {
		if(waitingPeople.get(elevator.getName()).isEmpty()) {
			return null;
		}
		return waitingPeople.get(elevator.getName()).peek();
	}
	
	public ArrayList<Person> peopleIntoElevator(Elevator elevator){
		ArrayList<Person> ps = new ArrayList<Person>();
		Iterator iterator = waitingPeople.get(elevator.getName()).iterator();
		while(iterator.hasNext()) {
			Person p = (Person) iterator.next();
			if(p.getStartFloor() == elevator.getCurrentFloor()) {
				ps.add(p);
			}
		}
		if(ps.isEmpty()) {
			return null;
		}
		return ps;
 	}
	
	@Override
	public String toString() {
		String result = "";
		
		for(Elevator e : elevators) {
			Iterator<Person> iterator = waitingPeople.get(e.getName()).iterator();
			if(iterator.hasNext()) {
				Person p = iterator.next();
				result += "Person ";
			}
		}
		
		return result;
	}
	
	
}
