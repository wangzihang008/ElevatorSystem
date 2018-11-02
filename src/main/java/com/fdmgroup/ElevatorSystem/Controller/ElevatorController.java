package com.fdmgroup.ElevatorSystem.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.swing.JOptionPane;

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
			e.setWaitingPeople(waitingPeople);
			Thread t = new Thread(e);
			elevators.add(e);
			elevatorThreads.add(t);
		}
		
		waitingPeople = new WaitingPeople(elevators);
		for(Elevator e : elevators) {
			e.setWaitingPeople(waitingPeople);
		}
	}
	
	public void addNewPassenger(Person person) {
		if(person.getStartFloor() > 0 && person.getDestinationFloor() <= totalFloor) {
			waitingPeople.addNewPassenger(person);
		}else {
			JOptionPane.showMessageDialog(
					null, 
					"Invalid Input", 
					"Information of New Passenger is Invalid! Please Input Again.", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

//	public void addNewPassengerIntoWaitingQueue(Person person) {
//		int min = 0;
//		Elevator elevator = elevators.get(0);
//		if(getEmptyElevator() != null) {
//			for(Elevator e : elevators) {
//				if(e.isEmpty()) {
//					if(e.getCurrentFloor() - person.getStartFloor() == 0) {
//						if(min == 0) {
//							min = State.SPEEDUP.getTime() + State.SLOWDOWN.getTime() +
//									State.SERVICE.getTime() + person.getFloorsToGo();
//						}else if(min > 0 && min > State.SPEEDUP.getTime() + State.SLOWDOWN.getTime() +
//								State.SERVICE.getTime() + person.getFloorsToGo()) {
//							min = State.SPEEDUP.getTime() + State.SLOWDOWN.getTime() +
//									State.SERVICE.getTime() + person.getFloorsToGo();
//						}
//					}
//				}else {
//					
//				}
//			}
//		}else {
//			
//		}
//	}
//	
	
	public Map<Map<Elevator, Queue<Person>>, Integer> findBestArrangement(List<Map<Elevator, Queue<Person>>> allPossibility) {
		
		Map<Map<Elevator, Queue<Person>>, Integer> result = new HashMap<Map<Elevator, Queue<Person>>, Integer>();
		Map<Elevator, Queue<Person>> bestMap = allPossibility.get(0);
		int minTimeAmongAllPossibility = 2147483647;
		for (Map<Elevator, Queue<Person>> m : allPossibility) {
			
			Set<Elevator> keySet = m.keySet();
			Iterator<Elevator> keyIter = keySet.iterator();
			int maxTimeAmongELevators = 0;
			while (keyIter.hasNext()) {
				
				Elevator elevatorToCalculate = keyIter.next();
				int runTime = elevatorToCalculate.calculateTime(m.get(elevatorToCalculate));
				if (runTime > maxTimeAmongELevators) {
					
					maxTimeAmongELevators = runTime;
				}
			}
			int time = maxTimeAmongELevators;
			if (time < minTimeAmongAllPossibility) {
				
				minTimeAmongAllPossibility = time;
				bestMap = m;
			}
		}
		result.put(bestMap, minTimeAmongAllPossibility);
		
		return result;
	}
	
	public void putPersonIntoElevators(Map<Elevator, Queue<Person>> bestMap) {
		
		for (Elevator e : elevators) {
			
			e.setPeople(bestMap.get(e));
		}
	}
	
	public void startThreads() {
		for(Thread t : elevatorThreads) {
			t.start();
		}
	}
}
