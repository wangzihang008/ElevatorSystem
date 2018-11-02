package com.fdmgroup.ElevatorSystem;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.junit.Test;

import com.fdmgroup.ElevatorSystem.Controller.ElevatorController;
import com.fdmgroup.ElevatorSystem.Model.Elevator;
import com.fdmgroup.ElevatorSystem.Model.Person;
import com.fdmgroup.ElevatorSystem.Model.WaitingPeople;

public class ElevatorControllerTest {

	@Test
	public void test() {
		fail("not yet completed");
		ElevatorController ec = new ElevatorController(1, 2, 3);
		ArrayList<Elevator> elevators_flag = new ArrayList<Elevator>();
		WaitingPeople flag = new WaitingPeople(elevators_flag);
		Elevator e1 = new Elevator(10, flag);
        Elevator e2 = new Elevator(20, flag);
        Elevator e3 = new Elevator(15, flag);
        
        Person p1 = new Person(1,24);
        Person p2 = new Person(3,5);
        Person p3 = new Person(4,9);
        Person p4 = new Person(5, 16);
        Person p5 = new Person(5, 16);
        Person p6 = new Person(5, 16);
        e1.pickPassenger(p1);
        e2.pickPassenger(p2);
        //e3.pickPassenger(p3);
        
        ArrayList<Elevator> elevators = new ArrayList<Elevator>();
        elevators.add(e1);
        elevators.add(e2);
        elevators.add(e3);
        WaitingPeople wp = new WaitingPeople(elevators);
        wp.addNewPassenger(p2);
        wp.addNewPassenger(p3);
        wp.addNewPassenger(p4);
        wp.addNewPassenger(p5);
        wp.addNewPassenger(p6);
        wp.setElevators(elevators);
        
		ec.addNewPassenger(p1);
		int[] result = new int[wp.getSize()];
        int[] counter = new int[wp.getElevators().size()];
        
        ArrayList<HashMap<Elevator, Queue<Person>>> mylist = ec.solve(wp.getSize() - 1, wp.getElevators().size(), wp, result, counter);
		Map<Map<Elevator, Queue<Person>>, Integer> bestMap = ec.findBestArrangement(mylist);
		for (Map<Elevator, Queue<Person>> map: bestMap.keySet()) {
			ec.putPersonIntoElevators(map);
		};
	}

}
