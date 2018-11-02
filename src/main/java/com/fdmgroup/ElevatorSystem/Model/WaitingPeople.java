package com.fdmgroup.ElevatorSystem.Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.fdmgroup.ElevatorSystem.Controller.ElevatorController;

public class WaitingPeople {
	private Map<Elevator, Queue<Person>> waitingPeople;
	private List<Elevator> elevators;

	public List<Elevator> getElevators() {
		return elevators;
	}

	public void setElevators(List<Elevator> elevators) {
		this.elevators = elevators;
	}

	public Map<Elevator, Queue<Person>> getWaitingPeople() {
		return waitingPeople;
	}

	public void setWaitingPeople(Map<Elevator, Queue<Person>> waitingPeople) {
		this.waitingPeople = waitingPeople;
	}

	public void addWaitingQueue(Elevator elevator, Queue<Person> list) {
		waitingPeople.put(elevator, list);
	}

	public WaitingPeople(List<Elevator> elevators) {
		super();
		waitingPeople = new HashMap<Elevator, Queue<Person>>();
		for (Elevator e : elevators) {
			waitingPeople.put(e, new LinkedList<Person>());
		}
		this.elevators = elevators;
	}

	public Elevator getEmptyElevator() {
		for (Elevator e : elevators) {
			if (e.getPeople().isEmpty()) {
				return e;
			}
		}
		return null;
	}

	/*
	 * public void addNewPassenger(Person person) { if(getEmptyElevator() != null) {
	 * waitingPeople.get(getEmptyElevator()).add(person); return; } Elevator select
	 * = null; int time = -1; for(Elevator e : elevators) { if(e.isUp() ==
	 * person.isGoingUp()) { if(select == null) { select = e; time = 1 +
	 * Elevator.getPickUpTime(e.getCurrentFloor(), person); }else
	 * if(person.isGoingUp()) { if(e.getCurrentFloor() < person.getStartFloor()) {
	 * if(time > Elevator.getPickUpTime(e.getCurrentFloor(), person)) { time =
	 * Elevator.getPickUpTime(e.getCurrentFloor(), person); select = e; } } }else {
	 * if(e.getCurrentFloor() > person.getStartFloor()) { if(time >
	 * Elevator.getPickUpTime(e.getCurrentFloor(), person)) { time =
	 * Elevator.getPickUpTime(e.getCurrentFloor(), person); select = e; } } } }else
	 * { if(select == null) { select = e; time = 1 + e.calculateTime(e.getPeople())
	 * + Elevator.getPickUpTime(e.getLastPassengerDestinationFloor(), person); }else
	 * { int timeOpposite = e.calculateTime(e.getPeople()) +
	 * Elevator.getPickUpTime(e.getLastPassengerDestinationFloor(), person); if(time
	 * > timeOpposite) { time = timeOpposite; select = e; } } } } if(select == null)
	 * { for(Elevator e : elevators) { if(select == null) { select = e; time = 1 +
	 * e.calculateTime(e.getPeople()) +
	 * Elevator.getPickUpTime(e.getLastPassengerDestinationFloor(), person); }else {
	 * if(time > e.calculateTime(e.getPeople()) +
	 * Elevator.getPickUpTime(e.getLastPassengerDestinationFloor(), person)) { time
	 * = e.calculateTime(e.getPeople()) +
	 * Elevator.getPickUpTime(e.getLastPassengerDestinationFloor(), person); select
	 * = e; } } } } waitingPeople.get(select).add(person); }
	 */

	private static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {

		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		@SuppressWarnings("unchecked")
		List<T> dest = (List<T>) in.readObject();
		return dest;
	}

	private static <T> Queue<T> deepCopy(Queue<T> src) throws IOException, ClassNotFoundException {

		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		@SuppressWarnings("unchecked")
		Queue<T> dest = (Queue<T>) in.readObject();
		return dest;
	}

	private List<Map<Integer, Queue<Person>>> arrange(Queue<Person> people, int num) {

		List<Map<Integer, Queue<Person>>> result = new ArrayList<Map<Integer, Queue<Person>>>();

		if (people.size() == 1) {

			for (int i = 0; i < num; i++) {

				Map<Integer, Queue<Person>> onePossiblity = new HashMap<Integer, Queue<Person>>();

				for (int j = 0; j < num; j++) {

					onePossiblity.put(j, new LinkedList<Person>());
				}

				onePossiblity.get(i).add(people.peek());
				result.add(onePossiblity);
			}
			return result;
		} else {

			Person removed = people.poll();
			List<Person> removedPerson = new ArrayList<Person>();
			removedPerson.add(removed);
			List<Map<Integer, Queue<Person>>> previous = arrange(people, num);

			for (int i = 0; i < previous.size(); i++) {

				for (int j = 0; j < num; j++) {

					Map<Integer, Queue<Person>> onePossibility = new HashMap<Integer, Queue<Person>>();
					List<Map<Integer, Queue<Person>>> previous3 = arrange(people, num);
					try {
						previous3 = deepCopy(previous);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					onePossibility = previous3.get(i);
					onePossibility.get(j).add(removed);
					try {
						onePossibility.get(j - 1).remove(removed);
					} catch (NullPointerException e) {

					}
					result.add(onePossibility);
				}
			}
			return result;
		}
	}

	private List<Map<Elevator, Queue<Person>>> arrangeByElevator(List<Map<Integer, Queue<Person>>> arrangedByNum) {
		// TODO Auto-generated method stub

		List<Map<Elevator, Queue<Person>>> arrangedByElevator = new ArrayList<Map<Elevator, Queue<Person>>>();
		for (int i = 0; i < arrangedByNum.size(); i++) {

			int j = 0;
			Map<Elevator, Queue<Person>> onePossibility = new HashMap<Elevator, Queue<Person>>();
			for (Elevator e : elevators) {

				onePossibility.put(e, arrangedByNum.get(i).get(j));
				for (Person p : onePossibility.get(e)) {
					
					p.setCurrentElevator(e);
				}
				j++;
			}

			arrangedByElevator.add(onePossibility);
		}
		return arrangedByElevator;
	}

	public List<Map<Elevator, Queue<Person>>> arrangeNewPassengerIntoWaitingQueue(Person newPerson) {

		Queue<Person> toBeArranged = new LinkedList<Person>();
		for (Elevator e : elevators) {

			toBeArranged.addAll(waitingPeople.get(e));
		}
		toBeArranged.add(newPerson);
		List<Map<Integer, Queue<Person>>> arrangedByNum = arrange(toBeArranged, elevators.size());
		List<Map<Elevator, Queue<Person>>> arrangedByElevator = arrangeByElevator(arrangedByNum);

		return arrangedByElevator;

	}

	public List<Map<Elevator, Queue<Person>>> combineWaitingPeopleAndLoadedPeople(
			List<Map<Elevator, Queue<Person>>> arrangedByElevator) {

		List<Map<Elevator, Queue<Person>>> combined = new ArrayList<Map<Elevator, Queue<Person>>>();
		for (int i = 0; i < arrangedByElevator.size(); i++) {

			boolean skip = false;
			Map<Elevator, Queue<Person>> onePossibility = new HashMap<Elevator, Queue<Person>>();
			for (Elevator e : elevators) {

				try {
					onePossibility.put(e, deepCopy(e.getPeople()));
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// System.out.println(onePossibility);
				for (Person p : arrangedByElevator.get(i).get(e)) {

					if (p.isGoingUp() == e.isUp() && e.getCurrentFloor() > p.getStartFloor() && !e.isEmpty()) {

						skip = true;
						break;
					}
					onePossibility.get(e).add(p);
				}
				if (skip) {

					break;
				}
			}
			if (skip) {

				continue;
			}
			combined.add(onePossibility);
		}

		return combined;
	}

	public Map<Elevator, Queue<Person>> findBestArrangement(List<Map<Elevator, Queue<Person>>> allPossibility) {

		Map<Elevator, Queue<Person>> bestMap = allPossibility.get(0);
		int minTimeAmongAllPossibility = 2147483647;
		for (Map<Elevator, Queue<Person>> m : allPossibility) {

			int maxTimeAmongELevators = 0;
			for (Elevator e : elevators) {

				int runTime = e.calculateTime(m.get(e));
				// System.out.println("Each = " + runTime);
				if (runTime > maxTimeAmongELevators) {

					maxTimeAmongELevators = runTime;
				}
			}

			int time = maxTimeAmongELevators;
			if (time < minTimeAmongAllPossibility) {

				bestMap = m;
			}
		}

		return bestMap;
	}

	public void updateWaitingList(Map<Elevator, Queue<Person>> bestMap) {

		for (Elevator e : elevators) {

			Queue<Person> currentElevator = new LinkedList<Person>();
			waitingPeople.put(e, currentElevator);

			for (Person p : bestMap.get(e)) {

				if (p.getIsWaiting()) {

					currentElevator.add(p);
				}
			}
		}
	}

	public void addNewWaitingPassenger(Person person) {

		List<Map<Elevator, Queue<Person>>> arranged = arrangeNewPassengerIntoWaitingQueue(person);
		List<Map<Elevator, Queue<Person>>> combined = combineWaitingPeopleAndLoadedPeople(arranged);
		updateWaitingList(findBestArrangement(combined));
	}

	@Override
	public String toString() {
		String result = "";

		for (Elevator e : elevators) {
			Iterator<Person> iterator = waitingPeople.get(e).iterator();
			if (iterator.hasNext()) {
				Person p = iterator.next();
				result += "Person ";
			}
		}

		return result;
	}

}
