package com.fdmgroup.ElevatorSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Algorithm {

	Map<Elevator, WaitingPeople> map = new HashMap<Elevator, WaitingPeople>();
	List<Object> list = new ArrayList<Object>();
	public List<Object> combination(List<Elevator> e, WaitingPeople wp) {
		for (int i = 0; i < e.size(); i++) {
			for (int j = 0; j < wp.size(); j++) {
				map.put(e.get(i), (WaitingPeople) wp.getWaitingPeople(j));
			}
			list.add(map);
		}
		return list;
	}
	
	public void calculateTime() {
		
	}
}
