package com.fdmgroup.ElevatorSystem.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaitingPeople {

	private Map<Elevator, Object> waitingPeople;

	public Map<Elevator, Object> getWaitingPeople() {
		return waitingPeople;
	}
	
	public Object getWaitingPeople(int i) {
		return waitingPeople.get(i);
	}


	public void setWaitingPeople(Map<Elevator, Object> waitingPeople) {
		this.waitingPeople = waitingPeople;
	}
	
	public void addWaitingQueue(Elevator elevator, List<Person> list) {
		waitingPeople.put(elevator, list);
	}

	public WaitingPeople() {
		super();
		waitingPeople = new HashMap<Elevator, Object>();
	}
	
}
