package main;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
	private List<Observer> followers = new ArrayList<Observer>();
	private int state;
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
		notifyAllObservers();
	}
	
	public void attach(Observer follower) {
		followers.add(follower);
	}
	
	public void notifyAllObservers() {
		for(Observer follower : followers) {
			follower.update();
		}
	}
}
