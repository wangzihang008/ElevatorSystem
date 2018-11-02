package com.fdmgroup.ElevatorSystem;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.ElevatorSystem.Model.Elevator;
import com.fdmgroup.ElevatorSystem.Model.Person;
import com.fdmgroup.ElevatorSystem.Model.WaitingPeople;

public class WaitingPeopleTest {
	private Elevator elevator;
	private Elevator elevator2;
	private WaitingPeople waitingPeople;
	
	@Before
	public void setup() {
		ArrayList<Elevator> elevators = new ArrayList<Elevator>();
		elevator = new Elevator(5);
		elevator2 = new Elevator(5);
		elevator.setName("1");
		elevator.setName("2");
		elevators.add(elevator);
		elevators.add(elevator2);
		waitingPeople = new WaitingPeople(elevators);
		elevator.setWaitingPeople(waitingPeople);
		elevator2.setWaitingPeople(waitingPeople);
//		elevator.setWaitingPeople(waitingPeople);
	}
	
	@Test
	public void test_addNewPassenger_whenAllElevatorIsFull_WaitingPeopleForElevatorAviliable_returnLengthAs1() {
		// Arrange
		for(int i = 0; i < 5; i++) {
			Person p = new Person(0, i);
			Person p2 = new Person(0, i + 1);
			elevator.pickPassenger(p);
			elevator2.pickPassenger(p2);
		}
		Person p = new Person(0, 6);
		// Act
		waitingPeople.addNewPassenger(p);
		// Assert
		assertEquals(1, waitingPeople.getWaitingPeopleForElevatorAviliable().size());
	}
	
	@Test
	public void test_addNewPassenger_whenElevatorIsNotAllFull_add1PassengerAfter3_return4() {
		// Arrange
		for(int i = 0; i < 3; i++) {
			Person p = new Person(0, i);
			waitingPeople.addNewPassenger(p);
		}
		Person p = new Person(0, 6);
		// Act
		waitingPeople.addNewPassenger(p);
		// Assert
		assertEquals(4, waitingPeople.getWaitingPeople().get(elevator.getName()).size());
	}
	
	@Test
	public void test_addNewPassenger_whenElevatorIsNotAllFull_add2Passengers_return1ForElevator2() {
		// Arrange
		
		Person p = new Person(0, 1);
		Person p2 = new Person(1, 2);
		
		// Act
		waitingPeople.addNewPassenger(p);
		elevator.pickPassenger(p);
		waitingPeople.removeWaitingPerson(elevator, p);
		waitingPeople.addNewPassenger(p2);
		// Assert
		assertEquals(0, waitingPeople.getWaitingPeople().get(elevator.getName()).size());
		assertEquals(1, waitingPeople.getWaitingPeople().get(elevator2.getName()).size());
	}
	
	@Test
	public void test_addNewPassenger_whenElevatorIsNotAllFull_add2PassengersAfter_return1ForEachElevator() {
		// Arrange
		
		Person p = new Person(0, 1);
		Person p2 = new Person(0, 2);
		Person p3 = new Person(1, 3);
		Person p4 = new Person(2, 4);
		// Act
		elevator.pickPassenger(p);
		elevator.pickPassenger(p2);
		waitingPeople.addNewPassenger(p3);
		waitingPeople.addNewPassenger(p4);
		// Assert
		assertEquals(0, waitingPeople.getWaitingPeople().get(elevator.getName()).size());
		assertEquals(2, waitingPeople.getWaitingPeople().get(elevator2.getName()).size());
	}
	
	@Test
	public void test_addWaitingPersonIntoQueue_returnThisPerson() {
		// Arrange
		Person p = new Person(0, 4);
		// Act
		waitingPeople.addWaitingPersonIntoQueue(elevator, p);
		// Assert
		assertEquals(p, waitingPeople.getWaitingPeople().get(elevator.getName()).peek());
	}
	
	@Test
	public void test_getEmptyElevator_returnElevator() {
		// Arrange
		
		// Act
		
		// Assert
		assertEquals(elevator, waitingPeople.getEmptyElevator());
	}
	
	@Test
	public void test_getEmptyElevator_noElevatorIsEmpty_returnNull() {
		// Arrange
		Person p = new Person(0, 3);
		Person p2 = new Person(0, 3);
		// Act
		elevator.pickPassenger(p);
		elevator2.pickPassenger(p2);
		// Assert
		assertEquals(null, waitingPeople.getEmptyElevator());
	}
	
	@Test
	public void test_removeWaitingPerson_noElevatorIsEmpty_returnNull() {
		// Arrange
		Person p = new Person(0, 3);
		// Act
		waitingPeople.addNewPassenger(p);
		waitingPeople.removeWaitingPerson(elevator, p);
		// Assert
		assertEquals(true, waitingPeople.getWaitingPeople().get(elevator.getName()).isEmpty());
	}
	
	@Test
	public void test_getFirstWaitingPerson_returnPerson() {
		// Arrange
		Person p = new Person(0, 3);
		// Act
		waitingPeople.addNewPassenger(p);
		// Assert
		assertEquals(p, waitingPeople.getFirstWaitingPerson(elevator));
	}
	
	@Test
	public void test_getFirstWaitingPerson_return2rdPerson() {
		// Arrange
		Person p = new Person(2, 3);
		Person p2 = new Person(0, 1);
		// Act
		waitingPeople.addNewPassenger(p);
		waitingPeople.addNewPassenger(p2);
		// Assert
		assertEquals(p2, waitingPeople.getFirstWaitingPerson(elevator));
	}
	
	@Test
	public void test_peopleIntoElevator_elevatorAt0_returnPersonAt0() {
		// Arrange
		Person p2 = new Person(0, 1);
		// Act
		waitingPeople.addNewPassenger(p2);
		// Assert
		assertEquals(1, waitingPeople.peopleIntoElevator(elevator).size());
	}
	
	@Test
	public void test_peopleIntoElevator_elevatorAt0_PersonAt1_returnNull() {
		// Arrange
		Person p2 = new Person(1, 2);
		// Act
		waitingPeople.addNewPassenger(p2);
		// Assert
		assertEquals(null, waitingPeople.peopleIntoElevator(elevator));
	}
}
