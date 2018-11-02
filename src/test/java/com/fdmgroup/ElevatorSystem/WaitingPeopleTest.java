package com.fdmgroup.ElevatorSystem;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.fdmgroup.ElevatorSystem.Model.Elevator;
import com.fdmgroup.ElevatorSystem.Model.Person;
import com.fdmgroup.ElevatorSystem.Model.WaitingPeople;

public class WaitingPeopleTest {
	@Test
	public void test() {
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
        
        
        //System.out.println(wp.getSize());
        //System.out.println(wp.getQueue().size());
        
	
        assertEquals(5, wp.getSize());
	}
}
