package main;

import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

import visitor.SysEntryVisitor;

public class Group implements SysEntry {
	ArrayList<SysEntry> groupList = new ArrayList<SysEntry>();
	private static DefaultMutableTreeNode root;
	private List<User> userList = new ArrayList();
	private String displayName;
	private int numberOfGroups = 0;
	
	public Group() {
		root = new DefaultMutableTreeNode("Root");
		numberOfGroups++;
	}
	
	public Group(String groupName) {
		displayName = groupName;
		numberOfGroups++;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void addToGroup(SysEntry sys, int index) {
		if(groupList.size() <= index) {
			groupList.add(index, sys);
		}
		else {
			groupList.set(index, sys);
		}
		System.out.println(groupList.toString());
	}
	
	public SysEntry getEntry(int index) {
		return groupList.get(index);
	}
	
	public DefaultMutableTreeNode getRoot() {
		return root;
	}
	
	public int getNumberOfGroups() {
		return numberOfGroups;
	}
	
	@Override
	public int accept(SysEntryVisitor visitor) {
		return visitor.visitGroup(this);
	}
	
	@Override
	public String toString() {
		return getDisplayName();
	}
}
