package com.fdmgroup.ElevatorSystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.ElevatorSystem.Model.Elevator;
import com.fdmgroup.ElevatorSystem.Model.Person;
import com.fdmgroup.ElevatorSystem.Model.WaitingPeople;
import com.fdmgroup.ElevatorSystem.Model.State;

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
		assertEquals(0, elevator.getCurrentFloor());
	}
	
	@Test
	public void test_peopleOutOfEevator_1person() {
		// Arrange
		Person person = new Person(1, 4);
		// Act
		Elevator e = mock(Elevator.class);
		e.pickPassenger(person);
		e.service();
		// Assert
	}
	
	@Test
	public void test_service_pickPassengerFrom0() {
		// Arrange
		Person person = new Person(0, 4);
		// Act
		waitingPeople.addNewPassenger(person);
		elevator.service();
		// Assert
		assertEquals(1, elevator.getPeople().size());
	}
	
	@Test
	public void test_service_deliverPassengerFrom0() {
		// Arrange
		Person person = new Person(4, 0);
		// Act
		elevator.pickPassenger(person);
		elevator.service();
		// Assert
		assertEquals(0, elevator.getPeople().size());
	}
	
	@Test
	public void test_service_deliverPassengerFromWaitingPeopleWhoIsWaitingForElevatorAviliabe() {
		// Arrange
		for(int i = 0; i < 10; i++) {
			Person p = new Person(0, i + 1);
			elevator.pickPassenger(p);
		}
		
		Person person = new Person(1, 4);
		waitingPeople.addNewPassenger(person);
		
		// Act
		elevator.goUp();
//		System.out.println(elevator.peopleOutOfEevator().size());
//		System.out.println(waitingPeople.getWaitingPeopleForElevatorAviliable().size());
		elevator.service();
		// Assert
		assertEquals(1, elevator.getPeople().size());
	}
	
	@Test
	public void test_stop_stateShouldEqualsStop() {
		// Arrange
		
		// Act
		elevator.goUp();
		elevator.stop();
		// Assert
		assertEquals(State.STOP, elevator.getState());
	}
	
	@Test
	public void test_speedUp_stateShouldEqualsSpeedUp() {
		// Arrange
		
		// Act
		elevator.speedUp();
		// Assert
		assertEquals(State.SPEEDUP, elevator.getState());
	}
	
	@Test
	public void test_slowDown_stateShouldEqualsSlowDown() {
		// Arrange
		
		// Act
		elevator.slowDown();
		// Assert
		assertEquals(State.SLOWDOWN, elevator.getState());
	}
	
	@Test
	public void test_getLastPassengerDestinationFloor_lastPersonDestinationFloor5_return5() {
		// Arrange
		Person p1 = new Person(1, 4);
		Person p2 = new Person(2, 5);
		// Act
		elevator.pickPassenger(p1);
		elevator.pickPassenger(p2);
		// Assert
		assertEquals(5, elevator.getLastPassengerDestinationFloor());
	}
	
	@Test
	public void test_getPickUpTime_sameFloor_return0() {
		// Arrange
		Person p1 = new Person(0, 4);
		// Act
		
		// Assert
		assertEquals(0, Elevator.getPickUpTime(elevator.getCurrentFloor(), p1));
	}
	
	@Test
	public void test_getPickUpTime_differentFloor_elevatorAt0Floor_personAt3Floor_return3() {
		// Arrange
		Person p1 = new Person(3, 4);
		// Act
		
		// Assert
		assertEquals(5, Elevator.getPickUpTime(elevator.getCurrentFloor(), p1));
	}
	
	@Test
	public void test_getNumFloorToGo_sameFloor_return0() {
		// Arrange
		Person p1 = new Person(0, 4);
		// Act
		
		// Assert
		assertEquals(0, Elevator.getNumFloorToGo(elevator.getCurrentFloor(), p1));
	}
	
	@Test
	public void test_getNumFloorToGo_differentFloor_elevatorAt0_personAt3_return3() {
		// Arrange
		Person p1 = new Person(3, 4);
		// Act
		
		// Assert
		assertEquals(3, Elevator.getNumFloorToGo(elevator.getCurrentFloor(), p1));
	}
	
	@Test
	public void test_getNumFloorToGo_differentFloor_elevatorAt3_personAt1_return2() {
		// Arrange
		Person p1 = new Person(1, 4);
		// Act
		elevator.goUp();
		elevator.goUp();
		elevator.goUp();
		// Assert
		assertEquals(2, Elevator.getNumFloorToGo(elevator.getCurrentFloor(), p1));
	}
	
	@Test
	public void test_getFirstDeliverPerson_noPersonInElevator_returnNull() {
		// Arrange
		
		// Act
		
		// Assert
		assertNull(elevator.getFirstDeliverPerson());
	}
	
	@Test
	public void test_getFirstDeliverPerson_1PersonInElevator_returnPerson() {
		// Arrange
		Person p1 = new Person(0, 4);
		// Act
		elevator.pickPassenger(p1);
		// Assert
		assertEquals(p1, elevator.getFirstDeliverPerson());
	}
	
	@Test
	public void test_getFirstDeliverPerson_2PersonInElevator_returnP2Person() {
		// Arrange
		Person p1 = new Person(0, 4);
		Person p2 = new Person(0, 2);
		// Act
		elevator.pickPassenger(p1);
		elevator.pickPassenger(p2);
		// Assert
		assertEquals(p2, elevator.getFirstDeliverPerson());
	}
	
	@Test
	public void test_isGoingUpToPickPerson_elevatorAt0_personAt2_returnTrue() {
		// Arrange
		Person p1 = new Person(2, 4);
		// Act
		
		// Assert
		assertEquals(true, Elevator.isGoingUpToPickPerson(elevator.getCurrentFloor(), p1));
	}
	
	@Test
	public void test_isGoingUpToPickPerson_elevatorAt2_personAt0_returnFalse() {
		// Arrange
		Person p1 = new Person(2, 4);
		// Act
		elevator.goUp();
		elevator.goUp();
		// Assert
		assertEquals(false, Elevator.isGoingUpToPickPerson(elevator.getCurrentFloor(), p1));
	}
	
	@Test
	public void test_isGoingUpToDeliverPerson_elevatorAt0_personAt0Going4_returnTrue() {
		// Arrange
		Person p1 = new Person(0, 4);
		// Act
		elevator.pickPassenger(p1);
		// Assert
		assertEquals(true, Elevator.isGoingUpToDeliverPerson(elevator.getCurrentFloor(), p1));
	}
	
	@Test
	public void test_isGoingUpToDeliverPerson_elevatorAt2_personAt2Going0_returnFalse() {
		// Arrange
		Person p1 = new Person(2, 0);
		// Act
		elevator.goUp();
		elevator.goUp();
		elevator.pickPassenger(p1);
		// Assert
		assertEquals(false, Elevator.isGoingUpToDeliverPerson(elevator.getCurrentFloor(), p1));
	}
	
	@Test
	public void test_calculateTime_person1From0To8_person2From3To6_return() {
		// Arrange
		Person p1 = new Person(0, 8);
		Person p2 = new Person(3, 6);
		// Act
		elevator.pickPassenger(p1);
		elevator.goUp();
		elevator.goUp();
		elevator.goUp();
		elevator.service();
		elevator.pickPassenger(p2);
		// Assert
		assertEquals(13, elevator.calculateTime(elevator.getPeople()));
	}
	
	
}
