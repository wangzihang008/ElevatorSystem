package com.fdmgroup.ElevatorSystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fdmgroup.ElevatorSystem.Model.Elevator;
import com.fdmgroup.ElevatorSystem.Model.Person;
import com.fdmgroup.ElevatorSystem.Model.WaitingPeople;

public class Algorithm {
/*
	public static void main(String[] args) {
		Date time = new Date();
		long t1 = time.getTime();
        
		Person p1 = new Person(1,24);
        Person p2 = new Person(3,5);
        Person p3 = new Person(4,9);
        Person p4 = new Person(5, 16);
        WaitingPeople wp = new WaitingPeople();
        wp.add(p1);
        wp.add(p2);
        wp.add(p3);
        wp.add(p4);
        wp.add(p1);
        
        Elevator e1 = new Elevator(2);
        Elevator e2 = new Elevator(4);
        Elevator e3 = new Elevator(6);
        e1.setName("first");
        e2.setName("second");
        ArrayList<Elevator> elevators = new ArrayList<Elevator>();
        elevators.add(e1);
        elevators.add(e2);
        elevators.add(e3);
        //HashMap<Elevator, WaitingPeople> result = new HashMap<Elevator, WaitingPeople>();
        int[] result = new int[wp.size()];
        int[] counter = new int[elevators.size()];
        
        ArrayList<HashMap<Elevator, WaitingPeople>> mylist = solve(wp.size() - 1, elevators.size(), elevators, wp, result, counter);
        System.out.println(mylist);
        long t2 = time.getTime();
        System.out.println(t1);
        System.out.println(t2);
    }
			
	// Create method to return all possible combinations of distributing waitingPeople to each elevator
    public static ArrayList<HashMap<Elevator, WaitingPeople>> solve(int currentIndex, int emptyCount, 
    		ArrayList<Elevator> elevator, WaitingPeople wp, int[] result, int[] counter) {
    	
        ArrayList<HashMap<Elevator, WaitingPeople>> mylist = new ArrayList<HashMap<Elevator, WaitingPeople>>();
    	
    	if (currentIndex < emptyCount - 1) {
        	return null;
        }

        if (currentIndex == -1) {
            for (int i = 0; i < result.length; i++) {
            	WaitingPeople tmp = new WaitingPeople();
            	HashMap<Elevator, WaitingPeople> map = new HashMap<>();
                //map.put(elevator.get(result[i]), wp.getWaitingPeople(i));
            	if (i == 0) {
            		while (! map.containsKey(elevator.get(result[i]))) {
            			map.put(elevator.get(result[i]), tmp);
            		}
            		System.out.println(map.keySet() + " " + map.values());
            		map.get(elevator.get(result[i])).add(wp.getWaitingPeople(i));
            		//map.put(elevator.get(result[i]),tmp);
            		//map.put(elevator.get(result[i]), wp.getWaitingPeople(i));
            		
            		System.out.println();
            		mylist.add(map);
            		//System.out.println(wp.getWaitingPeople(i) + " " + elevator.get(result[i]).getName());
            	} else {
            		//tmp.add(wp.getWaitingPeople(i));
            		while (! map.containsKey(elevator.get(result[i]))) {
            			map.put(elevator.get(result[i]), tmp);
            		}
            		System.out.println(map.keySet() + " " + map.values());
            		map.get(elevator.get(result[i])).add(wp.getWaitingPeople(i));
            		/*if (! map.containsKey(elevator.get(result[i]))) {
            			map.put(elevator.get(result[i]), null);
            			map.get(elevator.get(result[i])).add(wp.getWaitingPeople(i));
            		}
            		else{
            			map.get(elevator.get(result[i])).add(wp.getWaitingPeople(i));
            		}
            		//map.put(elevator.get(result[i]), wp.getWaitingPeople(i));
            	}
            }
           	return mylist;
        }
        
        for (int i = 0; i < elevator.size(); i++) {
			counter[i]++;
            result[currentIndex] = i;

            if (counter[i] == 1)
                solve(currentIndex - 1, emptyCount - 1, elevator, wp, result, counter);
            else
                solve(currentIndex - 1, emptyCount, elevator, wp, result, counter);
            
            counter[i]--;
        }
		return mylist;
		
    }
	*/
}
