package com.fdmgroup.ElevatorSystem;

import java.util.ArrayList;
import java.util.List;

public class WaitingPeople {
	private List<Object> waitingPeople;

	public List<Object> getWaitingPeople() {
		return waitingPeople;
	}

	public void setWaitingPeople(List<Object> waitingPeople) {
		this.waitingPeople = waitingPeople;
	}
	
	public void addWaitingQueue(List<Person> list) {
		waitingPeople.add(list);
	}

	public WaitingPeople() {
		super();
		waitingPeople = new ArrayList<Object>();
	}
	
	
}