package com.fdmgroup.ElevatorSystem.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

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
	
	public WaitingPeople getWaitingPeople() {
		return waitingPeople;
	}
	public void setWaitingPeople(WaitingPeople waitingPeople) {
		this.waitingPeople = waitingPeople;
	}
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
	
	public Person deliverPassager() {
		return people.poll();
	}
	
	public boolean isFull() {
		return max == people.size();
	}
	
	public boolean isEmpty() {
		return people.size() < 1;
	}
	
	public Elevator() {}
	
	public Elevator(int max) {
		super();
		isUp = true;
		currentFloor = 0;
		people = new PriorityQueue<Person>(new PeopleInElevatorComparator());
		state = State.STOP;
		this.max = max;
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
	
	public ArrayList<Person> peopleOutOfEevator(){
		ArrayList<Person> ps = new ArrayList<Person>();
		Iterator<Person> iterator = people.iterator();
		while(iterator.hasNext()) {
			Person p = iterator.next();
			if(p.getDestinationFloor() == currentFloor) {
				ps.add(p);
			}
		}
		if(ps.isEmpty()) {
			return null;
		}
		return ps;
	}
	

//	private ArrayList<Person> peopleIntoElevator(){
//		return WaitingPeople.peopleIntoElevator(this);
// 	}
	
	public void service() {
		if(peopleOutOfEevator() != null) {
			for(Person p : peopleOutOfEevator()) {
				people.remove(p);
			}
			if(waitingPeople.getWaitingPeopleForElevatorAviliable().size() > 0) {
				Person p = null;
				int num = -1;
				for(Person person: waitingPeople.getWaitingPeopleForElevatorAviliable()) {
					if(p == null) {
						p = person;
						num = calculateTime(getPeople()) + 
								Elevator.getPickUpTime(getLastPassengerDestinationFloor(), person);
					}else {
						if(num > calculateTime(getPeople()) + 
								Elevator.getPickUpTime(getLastPassengerDestinationFloor(), person)) {
							num = calculateTime(getPeople()) + 
									Elevator.getPickUpTime(getLastPassengerDestinationFloor(), person);
							p = person;
						}
					}
				}
				waitingPeople.addWaitingPersonIntoQueue(this, p);
				waitingPeople.notifyAllElevator();
			}
		}

		if(waitingPeople.peopleIntoElevator(this) != null) {
			for(Person p : waitingPeople.peopleIntoElevator(this)) {
				people.add(p);
				waitingPeople.removeWaitingPerson(this, p);
			}
		}
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
	
	public Person getFirstDeliverPerson() {
		if(people.isEmpty()) {
			return null;
		}
		return people.peek();
	}
	
	public static boolean isGoingUpToPickPerson(int currentFloor, Person person) {
		return currentFloor - person.getStartFloor() < 0;
	}
	
	public static boolean isGoingUpToDeliverPerson(int currentFloor, Person person) {
		return currentFloor - person.getDestinationFloor() < 0;
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
			result += "It stop. "; 
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
				
				Person waitingPerson = waitingPeople.getFirstWaitingPerson(this);
				Person deliverPerson = getFirstDeliverPerson();
				if(waitingPerson != null && deliverPerson != null) {
					if(getNumFloorToGo(currentFloor, waitingPerson) > getNumFloorToGo(currentFloor, deliverPerson)) {
						switch(state) {
							case STOP:
								speedUp();
								break;
							case SPEEDUP:
								if(isGoingUpToDeliverPerson(currentFloor, deliverPerson)) {
									goUp();
								}else {
									goDown();
								}
								break;
							case MOVE:
								if(currentFloor == deliverPerson.getDestinationFloor()) {
									slowDown();
								}else {
									if(isUp) {
										goUp();
									}else {
										goDown();
									}
								}
								break;
							case SLOWDOWN:
								service();
								break;
							case SERVICE:
								speedUp();
								break;
						}
					}else {
						switch(state) {
							case STOP:
								speedUp();
								break;
							case SPEEDUP:
								if(isGoingUpToPickPerson(currentFloor, waitingPerson)) {
									goUp();
								}else {
									goDown();
								}
								break;
							case MOVE:
								if(currentFloor == waitingPerson.getStartFloor()) {
									slowDown();
								}else {
									if(isUp) {
										goUp();
									}else {
										goDown();
									}
								}
								break;
							case SLOWDOWN:
								service();
								break;
							case SERVICE:
								speedUp();
								break;
						}
					}
				}else if(waitingPerson != null) {
					switch(state) {
						case STOP:
							speedUp();
							break;
						case SPEEDUP:
							if(isGoingUpToPickPerson(currentFloor, waitingPerson)) {
								goUp();
							}else {
								goDown();
							}
							break;
						case MOVE:
							if(currentFloor == waitingPerson.getStartFloor()) {
								slowDown();
							}else {
								if(isUp) {
									goUp();
								}else {
									goDown();
								}
							}
							break;
						case SLOWDOWN:
							service();
							break;
						case SERVICE:
							speedUp();
							break;
					}
				}else if(deliverPerson != null) {
					switch(state) {
						case STOP:
							speedUp();
							break;
						case SPEEDUP:
							if(isGoingUpToDeliverPerson(currentFloor, deliverPerson)) {
								goUp();
							}else {
								goDown();
							}
							break;
						case MOVE:
							if(currentFloor == deliverPerson.getDestinationFloor()) {
								slowDown();
							}else {
								if(isUp) {
									goUp();
								}else {
									goDown();
								}
							}
							break;
						case SLOWDOWN:
							service();
							break;
						case SERVICE:
							speedUp();
							break;
					}
				}else {
					if(currentFloor == 0) {
						stop();
					}else {
						stop();
					}
				}
				if(state == State.SERVICE) {
					Thread.sleep(2000);
				}else {
					Thread.sleep(1000);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
