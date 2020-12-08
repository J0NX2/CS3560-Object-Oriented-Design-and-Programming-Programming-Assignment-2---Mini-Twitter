package main;
import java.util.*;

import visitor.SysEntryVisitor;

import java.awt.List;

public class User extends Observer implements SysEntry {
	
	List userList = new List();
	private ArrayList<String> newsFeed;
	private ArrayList<User> followers;
	private ArrayList<String> following;
	private ArrayList<String> tweetList;
	private int tweetCount = 0;
	private int numberOfUsers = 0;
	private int positiveMessageCount;
	private String displayName;
	private String[] positiveWords = {"good", "great", "excellent"};
	
	public User(String user) {
		displayName = user;
		followers = new ArrayList();
		following = new ArrayList();
		tweetList = new ArrayList();
		newsFeed = new ArrayList();
		numberOfUsers++;
	}
	
	public void follow(String user) {
		following.add(user);
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void tweet(String tweet) {
		tweetList.add(tweet);
		updateNewsFeed(tweet);
		
		for(String word : positiveWords) {
            if (tweet.toLowerCase().contains(word)) {
            	positiveMessageCount++;
            }
        }
        tweetCount++;
	}
	
	public int getTweetCount() {
		return tweetCount;
	}
	
	public ArrayList getTweets() {
		return tweetList;
	}
	
	public ArrayList getFollowing() {
		return following;
	}
	
	public int getNumberOfUsers() {
		return numberOfUsers;
	}
	
	public int getPositiveMessageCount() {
		return positiveMessageCount;
	}
	
	public void attach(User user) {
		followers.add(user);
	}
	
	public ArrayList<String> getNewsFeed() {
		return newsFeed;
	}
	
	public ArrayList<User> getObserver() {
		return followers;
	}
	
	public void updateNewsFeed(String tweet) {
		newsFeed.add(tweet);
	}
	
    @Override
    public int accept(SysEntryVisitor visitor) {
    	return visitor.visitUser(this);
    }
    
	@Override
	public String toString() {
		return getDisplayName();
	}

	@Override
	public void update() {
		for(User follower: followers) {
			for(int i = 0; i < newsFeed.size(); i++) {
				follower.getNewsFeed().add(newsFeed.get(i));
			}
		}
	}
}
