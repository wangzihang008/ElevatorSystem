package com.fdmgroup.ElevatorSystem;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.fdmgroup.ElevatorSystem.Model.Person;
import com.fdmgroup.ElevatorSystem.Model.WaitingPeople;

public class AlgorithmTest {

	@Test
	public void test() {
		Person p1 = new Person(1, 24);
		Person p2 = new Person(24, 1);
		ArrayList<Person> list = new ArrayList<Person>();
		list.add(p1);
		list.add(p2);
		WaitingPeople wp = new WaitingPeople();
		Algorithm al = new Algorithm();
		//al.solve(wp.size(), 2);
		
		assertEquals(2, wp.size());
	}

}
