package com.fdmgroup.ElevatorSystem;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

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
		elevator = new Elevator(10);
		elevators.add(elevator);
		waitingPeople = new WaitingPeople(elevators);
//		elevator.setWaitingPeople(waitingPeople);
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
		assertEquals(person, elevator.deliverPassager());
		assertEquals(3, elevator.getPeople().peek().getStartFloor());
	}
	
	@Test
	public void test_devilverPassenger_no_person_return_null() {
		// Arrange
		
		// Act
		elevator.deliverPassager();
		// Assert
		assertEquals(null, elevator.deliverPassager());
	}
	
	@Test
	public void test_isEmpty_0_person_return_true() {
		// Arrange
		
		// Act
		
		// Assert
		assertEquals(true, elevator.isEmpty());
	}
	
	@Test
	public void test_isEmpty_1_person_return_false() {
		// Arrange
		Person person = new Person(2, 4);
		// Act
		elevator.pickPassenger(person);
		// Assert
		assertEquals(false, elevator.isEmpty());
	}
	
	@Test
	public void test_goUp_once_from0To1() {
		// Arrange
		
		// Act
		elevator.goUp();
		// Assert
		assertEquals(1, elevator.getCurrentFloor());
	}
	
	@Test
	public void test_goDown_once_from1To0() {
		// Arrange
		
		// Act
		elevator.goUp();
		elevator.goDown();
		// Assert
		assertEquals(1, elevator.getCurrentFloor());
	}
	
	@Test
	public void test_peopleOutOfEevator_1person() {
		// Arrange
		Person person = new Person(1, 4);
		// Act
		Elevator e = mock(Elevator.class);
		e.pickPassenger(person);
		// Assert
		
	}
	
	
}
