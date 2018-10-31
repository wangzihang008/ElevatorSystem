package com.fdmgroup.ElevatorSystem.Model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Elevator implements Runnable{
	private String name;
	private boolean isUp;
	private int currentFloor;
	private Queue<Person> people;
	private State state;
	private int max;
	private Logger log = LogManager.getLogger(Elevator.class);
	
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isUp() {
		return isUp;
	}
	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}
	public int getCurrentFloor() {
		return currentFloor;
	}
	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}
	public Queue<Person> getPeople() {
		return people;
	}
	public void setPeople(Queue<Person> people) {
		this.people = people;
	}
	
	public void pickPassenger(Person person) {
		people.add(person);
	}
	
	public Person deliverPassager() {
		return people.poll();
	}
	
	public boolean isFull() {
		return max == people.size();
	}
	
	public Elevator(int max) {
		super();
		isUp = true;
		currentFloor = 1;
		people = new LinkedBlockingQueue<Person>();
		state = State.STOP;
		this.max = max;
	}

	@Override
	public String toString() {
		String result = name + " is at Level " + currentFloor + ". ";
		result += "There are " + people.size() + " in this elevator. ";
		if(isUp) {
			result += "It is going up. "; 
		}else {
			result += "It is going down. "; 
		}
		
		return result;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			
			
			// Print info to console and sleep for next second
			try {
				log.info(this.toString());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
