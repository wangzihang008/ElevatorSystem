package com.fdmgroup.ElevatorSystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.fdmgroup.ElevatorSystem.Model.Elevator;
import com.fdmgroup.ElevatorSystem.Model.Person;
import com.fdmgroup.ElevatorSystem.Model.WaitingPeople;

public class Algorithm {

	public static void main(String[] args) {
		Date time = new Date();
		long t1 = time.getTime();
        
		ArrayList<Elevator> elevators_flag = new ArrayList<Elevator>();
		WaitingPeople flag = new WaitingPeople(elevators_flag);
		Elevator e1 = new Elevator(10, flag);
        Elevator e2 = new Elevator(20, flag);
        Elevator e3 = new Elevator(15, flag);
        e1.setName("first");
        e2.setName("second");
        e3.setName("third");
        
        Person p1 = new Person(1,24);
        Person p2 = new Person(3,5);
        Person p3 = new Person(4,9);
        Person p4 = new Person(5, 16);
        e1.pickPassenger(p1);
        
        ArrayList<Elevator> elevators = new ArrayList<Elevator>();
        elevators.add(e1);
        elevators.add(e2);
        elevators.add(e3);
        WaitingPeople wp = new WaitingPeople(elevators);
        wp.addNewPassenger(p2);
        wp.addNewPassenger(p3);
        wp.addNewPassenger(p4);
        wp.setElevators(elevators);
        
        //System.out.println(wp.getWaitingPeople());
        
        int[] result = new int[wp.getSize()];
        int[] counter = new int[wp.getElevators().size()];
        
        ArrayList<HashMap<Elevator, Queue<Person>>> mylist = solve(wp.getSize() - 1, wp.getElevators().size(), wp, result, counter);
        System.out.println(mylist.size());
        long t2 = time.getTime();
        System.out.println(t1);
        System.out.println(t2);
    }
			
	// Create method to return all possible combinations of distributing waitingPeople to each elevator
    public static ArrayList<HashMap<Elevator, Queue<Person>>> solve(int currentIndex, int emptyCount, WaitingPeople wp, int[] result, int[] counter) {
    	
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
            		System.out.println(tmp.keySet() + " " + tmp.values());
            		mylist.add(tmp);
            		//System.out.println(wp.getWaitingPeople(i) + " " + elevator.get(result[i]).getName());
            	} else {
            		//tmp.add(wp.getWaitingPeople(i));
            		while (! tmp.containsKey(wp.getElevators().get(result[i]))) {
            			tmp.put(wp.getElevators().get(result[i]), myqueue);
            		}
            		
            		tmp.get(wp.getElevators().get(result[i])).add((Person) wp.getQueue().get(i));
            		System.out.println(tmp.keySet() + " " + tmp.values());
            	}
            }
            //System.out.println();
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
        //System.out.println(mylist.size());
		return mylist;
		
    }
}
