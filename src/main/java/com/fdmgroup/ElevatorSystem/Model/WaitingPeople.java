package com.fdmgroup.ElevatorSystem.Model;

import java.util.ArrayList;
import java.util.List;

public class WaitingPeople {
	private List<Person> waitingPeople;

	public List<Person> getWaitingPeople() {
		return waitingPeople;
	}
	
	public Object getWaitingPeople(int i) {
		return waitingPeople.get(i);
	}

	public void setWaitingPeople(List<Person> waitingPeople) {
		this.waitingPeople = waitingPeople;
	}

	public WaitingPeople() {
		super();
		waitingPeople = new ArrayList<Person>();
	}
	public int size() {
		return waitingPeople.size();
	}
	
}
