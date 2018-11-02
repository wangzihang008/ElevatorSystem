package com.fdmgroup.ElevatorSystem.Model;

import java.util.Comparator;

public class PersonComparator implements Comparator<Person> {

	public int compare(Person p1, Person p2) {
		// TODO Auto-generated method stub

		if (p1.isGoingUp() == p2.isGoingUp()) {

			if (p1.isGoingUp()) {

				return p1.getDestinationFloor() - p2.getDestinationFloor();

			} else {
				return p2.getDestinationFloor() - p1.getDestinationFloor();
			}
		} else {

			if (!p1.getIsWaiting()) {

				return -1;
			} else {

				if (!p2.getIsWaiting()) {

					return -1;
				} else {

					if (p1.getCurrentElevator().isUp() == p1.isGoingUp()) {

						return -1;
					} else {

						return 1;
					}
				}

			}
		}
	}
}
