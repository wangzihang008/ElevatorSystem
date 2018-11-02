package com.fdmgroup.ElevatorSystem.Model;

import java.util.Iterator;
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
	private WaitingPeople waitingPeople;
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
		person.setIsWaiting(false);
	}
	
	public Person deliverPassenger() {
		return people.poll();
	}
	
	public boolean isFull() {
		return max == people.size();
	}
	
	public boolean isEmpty() {
		return people.size() < 1;
	}
	
	public Elevator() {}
	
	public Elevator(int max, WaitingPeople waitingPeople) {
		super();
		isUp = true;
		currentFloor = 0;
		people = new LinkedBlockingQueue<Person>();
		state = State.STOP;
		this.max = max;
		this.waitingPeople = waitingPeople;
	}
	
	public void goUp() {
		
		state = State.MOVE;
		isUp = true;
		currentFloor++;
	}
	
	public void goDown() {
		
		state = State.MOVE;
		isUp = false;
		currentFloor--;
	}
	
	public void service() {
		
		state = State.SERVICE;
	}
	
	public void stop() {
		
		state = State.STOP;
	}
	
	public void speedUp() {
		
		state = State.SPEEDUP;
	}
	
	public void slowDown() {
		
		state = State.SLOWDOWN;
	}
	
	public int getLastPassengerDestinationFloor() {
		Iterator<Person> iterator = people.iterator();
		if(iterator.hasNext()) {
			Person lastPerson = iterator.next();
			while(iterator.hasNext()) {
				lastPerson = iterator.next();
			}
			return lastPerson.getDestinationFloor();
		}
		return getCurrentFloor();
	}
	
	public static int getPickUpTime(int currentFloor, Person person) {
		int result = 0;
		
		if(currentFloor != person.getStartFloor()) {
			result = 2 + getNumFloorToGo(currentFloor, person);
		}
		
		return result;
	}
	
	public static int getNumFloorToGo(int currentFloor, Person person) {
		return currentFloor - person.getStartFloor() > 0 ? 
				currentFloor - person.getStartFloor() : person.getStartFloor() - currentFloor;
	}
	
	
	public int calculateTime(Queue<Person> people) {
		
		int time = 0;
		int atFloor = currentFloor;
		boolean direction = isUp;
		State currentState = state;
		int len = people.size();
		for (int i = 0; i < len; i++) {
					
			Person nextPersonToOut = people.poll();
			if (nextPersonToOut.getIsWaiting()) {
				
				if (((nextPersonToOut.getStartFloor() - atFloor) > 0) != direction) {
					
					direction = !direction;
					if (currentState == State.SPEEDUP) {
						
						time += 3; //need to speed up, slow down, change direction, and then speed up
					}else if (currentState == State.SLOWDOWN || currentState == State.MOVE) {
						
						time += 2; //need to slow down, change direction, and then speed up
					}else {
						
						time += 1; //stop or service, need to speed up
					}
				}else {
					
					if (currentState == State.SLOWDOWN) {
						
						time += 2; //need to slow down, and then speed up
					}else if (currentState == State.MOVE) {
						
						time += 0; //no need to speed up
					}else {
						
						time += 1; //speed up, stop or service, need to speed up
					}
				}
				time += Math.abs(nextPersonToOut.getStartFloor() - atFloor);//move
				atFloor = nextPersonToOut.getStartFloor();
				time += 1; //slow down
				time += 2; //service
				currentState = State.SERVICE;
			}
			if (((nextPersonToOut.getDestinationFloor() - atFloor) > 0) != direction) {
				
				direction = !direction;
				if (currentState == State.SPEEDUP) {
						
					time += 3; //need to speed up, slow down, change direction, and then speed up 
				}else if (currentState == State.SLOWDOWN || currentState == State.MOVE) {
						
					time += 2; //need to slow down, change direction, and then speed up
				}else {
					
					time += 1; //stop or service, need to speed up
				}
			}else {
				
				if (currentState == State.SLOWDOWN) {
					
					time += 2; //need to slow down, and then speed up
				}else if (currentState == State.MOVE) {
					
					time += 0; //no need to speed up
				}else {
					
					time += 1; //speed up, stop or service, need to speed up
				}
			}
			time += Math.abs(nextPersonToOut.getDestinationFloor() - atFloor); //move
			time += 1; //slow down
			time += 2; //service
			currentState = State.SERVICE;
			atFloor = nextPersonToOut.getDestinationFloor(); 
		}
		
		return time;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentFloor;
		result = prime * result + (isUp ? 1231 : 1237);
		result = prime * result + max;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((people == null) ? 0 : people.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((waitingPeople == null) ? 0 : waitingPeople.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Elevator other = (Elevator) obj;
		if (currentFloor != other.currentFloor)
			return false;
		if (isUp != other.isUp)
			return false;
		if (max != other.max)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (people == null) {
			if (other.people != null)
				return false;
		} else if (!people.equals(other.people))
			return false;
		if (state != other.state)
			return false;
		if (waitingPeople == null) {
			if (other.waitingPeople != null)
				return false;
		} else if (!waitingPeople.equals(other.waitingPeople))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String result = name + " is at Level " + currentFloor + ". ";
		result += "There are " + people.size() + " people in this elevator. ";
		if(state == State.MOVE) {
			if(isUp) {
				result += "It is going up. "; 
			}else {
				result += "It is going down. "; 
			}
		}else if(state == State.SERVICE) {
			result += "It is servicing. "; 
		}else if(state == State.STOP) {
			result += "It stops. "; 
		}else if(state == State.SLOWDOWN) {
			result += "It is slowing down. "; 
		}else if(state == State.SPEEDUP) {
			result += "It is speeding up. "; 
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
