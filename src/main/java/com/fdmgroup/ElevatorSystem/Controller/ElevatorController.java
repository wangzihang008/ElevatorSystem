package com.fdmgroup.ElevatorSystem.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

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
		waitingPeople = new WaitingPeople(elevators);
		
		for(int i = 0; i < numElevator; i++) {
			Elevator e = new Elevator(elevatorLimit, waitingPeople);
			e.setName("Elevator" + (i + 1));
			Thread t = new Thread(e);
			elevators.add(e);
			elevatorThreads.add(t);
			
			t.start();
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
	
	// Create method to return all possible combinations of distributing waitingPeople to each elevator
    public ArrayList<HashMap<Elevator, Queue<Person>>> solve(int currentIndex, int emptyCount, WaitingPeople wp, int[] result, int[] counter) {
    	
        ArrayList<HashMap<Elevator, Queue<Person>>> mylist = new ArrayList<HashMap<Elevator, Queue<Person>>>();
    	
    	if (currentIndex < emptyCount - 1) {
        	return null;
        }

        if (currentIndex == -1) {
            for (int i = 0; i < result.length; i++) {
            	HashMap<Elevator, Queue<Person>> tmp = new HashMap<Elevator, Queue<Person>>();
            	Queue<Person> myqueue = new LinkedBlockingQueue<Person>();
            	// Logic to enumerate all combinations and separate to different maps
            	if (i == 0) {
            		while (! tmp.containsKey(wp.getElevators().get(result[i]))) {
            			tmp.put(wp.getElevators().get(result[i]), myqueue);
            		}
            		//System.out.println(tmp.keySet() + " " + tmp.values());
            		
            		tmp.get(wp.getElevators().get(result[i])).add((Person) wp.getQueue().get(i));
            		//System.out.println(tmp.keySet() + " " + tmp.values());
            		mylist.add(tmp);
            		//System.out.println(wp.getWaitingPeople(i) + " " + elevator.get(result[i]).getName());
            	} else {
            		while (! tmp.containsKey(wp.getElevators().get(result[i]))) {
            			tmp.put(wp.getElevators().get(result[i]), myqueue);
            		}
            		
            		tmp.get(wp.getElevators().get(result[i])).add((Person) wp.getQueue().get(i));
            		//System.out.println(tmp.keySet() + " " + tmp.values());
            	}
            }
           	return mylist;
        }
        
        for (int i = 0; i < wp.getElevators().size(); i++) {
			counter[i]++;
            result[currentIndex] = i;

            if (counter[i] == 1)
                solve(currentIndex - 1, emptyCount - 1, wp, result, counter);
            else
                solve(currentIndex - 1, emptyCount, wp, result, counter);
            
            counter[i]--;
        }
		return mylist;
		
    }
	
	public Map<Map<Elevator, Queue<Person>>, Integer> findBestArrangement(ArrayList<HashMap<Elevator, Queue<Person>>> mylist) {
		
		Map<Map<Elevator, Queue<Person>>, Integer> result = new HashMap<Map<Elevator, Queue<Person>>, Integer>();
		Map<Elevator, Queue<Person>> bestMap = mylist.get(0);
		int minTimeAmongAllPossibility = 2147483647;
		for (Map<Elevator, Queue<Person>> m : mylist) {
			
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
	
	
}
