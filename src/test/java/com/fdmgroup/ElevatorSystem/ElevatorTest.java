package com.fdmgroup.ElevatorSystem;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.ElevatorSystem.Model.Elevator;
import com.fdmgroup.ElevatorSystem.Model.Person;
import com.fdmgroup.ElevatorSystem.Model.WaitingPeople;

public class ElevatorTest {
	private Elevator elevator;
	private WaitingPeople waitingPeople;
	
	@Before
	public void setup() {
		ArrayList<Elevator> elevators = new ArrayList<Elevator>();
		waitingPeople = new WaitingPeople(elevators);
		elevator = new Elevator(10, waitingPeople);
		
	}
	
	@Test
	public void test_pickPassenger_1_person_destinationFloorMustBeSame() {
		// Arrange
		Person person = new Person(1, 2);
		// Act
		elevator.pickPassenger(person);
		// Assert
		assertEquals(2, elevator.getPeople().poll().getDestinationFloor());
	}
	
	@Test
	public void test_pickPassenger_2_person_passengersCountAsInserted() {
		// Arrange
		Person person = new Person(1, 2);
		Person person2 = new Person(3, 5);
		// Act
		elevator.pickPassenger(person);
		elevator.pickPassenger(person2);
		// Assert
		assertEquals(2, elevator.getPeople().size());
	}
	
	@Test
	public void test_isFull_10_person_return_ture() {
		// Arrange
		Person [] people = new Person [10];
		for(int i = 0; i < people.length; i++) {
			Person p = new Person(i + 1, i + 2);
			people[i] = p;
		}
		// Act
		for(Person p : people) {
			elevator.pickPassenger(p);
		}
		// Assert
		assertEquals(true, elevator.isFull());
	}
	
	@Test
	public void test_isFull_1_person_return_false() {
		// Arrange
		Person person = new Person(1, 2);
		// Act
		elevator.pickPassenger(person);
		// Assert
		assertEquals(false, elevator.isFull());
	}
	
	@Test
	public void test_devilverPassenger_2_person_devilver_1_person_passengersCountAsInserted() {
		// Arrange
		Person person = new Person(1, 2);
		Person person2 = new Person(3, 5);
		// Act
		elevator.pickPassenger(person);
		elevator.pickPassenger(person2);
		
		// Assert
		assertEquals(person, elevator.deliverPassenger());
		assertEquals(3, elevator.getPeople().peek().getStartFloor());
	}
	
	@Test
	public void test_devilverPassenger_no_person_return_null() {
		// Arrange
		
		// Act
		elevator.deliverPassenger();
		// Assert
		assertEquals(null, elevator.deliverPassenger());
	}
}
