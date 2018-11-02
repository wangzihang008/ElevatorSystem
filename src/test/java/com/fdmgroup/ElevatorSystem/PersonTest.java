package com.fdmgroup.ElevatorSystem;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import com.fdmgroup.ElevatorSystem.Model.Person;

public class PersonTest {
	@Before
	public void setup() {
		
	}
	
	@Test
	public void test_isGoingUp_startFloor0_destinationFloor4_returnTrue() {
		// Arrange
		Person person = new Person(0, 4);
		// Act
		
		// Assert
		assertEquals(true, person.isGoingUp());
	}
	
	@Test
	public void test_isGoingUp_startFloor4_destinationFloor0_returnFalse() {
		// Arrange
		Person person = new Person(4, 0);
		// Act
		
		// Assert
		assertEquals(false, person.isGoingUp());
	}
	
	@Test
	public void test_getFloorsToGo_startFloor0_destinationFloor4_return4() {
		// Arrange
		Person person = new Person(0, 4);
		// Act
		
		// Assert
		assertEquals(4, person.getFloorsToGo());
	}
	
	@Test
	public void test_getFloorsToGo_startFloor3_destinationFloor0_return3() {
		// Arrange
		Person person = new Person(3, 0);
		// Act
		
		// Assert
		assertEquals(3, person.getFloorsToGo());
	}
}
