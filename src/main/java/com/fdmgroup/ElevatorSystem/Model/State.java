package com.fdmgroup.ElevatorSystem.Model;

public enum State {
	
	SEEDPUP(1), SLOWDOWN(1), MOVE(1), SERVICE(2), STOP(0);
	
	private int time;
	
	private State(int time) {
		
		this.time = time;
	}
	
	public int getTime() {
		
		return time;
	}

}
