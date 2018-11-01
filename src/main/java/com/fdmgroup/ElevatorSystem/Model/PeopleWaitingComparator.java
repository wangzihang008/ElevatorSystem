package com.fdmgroup.ElevatorSystem.Model;

import java.util.Comparator;

public class PeopleWaitingComparator implements Comparator{

	public int compare(Object o1, Object o2) {
		Person p1 = (Person) o1;
		Person p2 = (Person) o2;
		if(p1.getStartFloor() == p2.getStartFloor()) {
			return 0;
		}else if(p1.getStartFloor() > p2.getStartFloor()) {
			return 1;
		}else {
			return -1;
		}
	}

}
