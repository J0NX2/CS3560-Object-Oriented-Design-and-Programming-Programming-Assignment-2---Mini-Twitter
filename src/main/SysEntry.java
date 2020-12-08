package main;

import java.util.Observable;

import visitor.SysEntryVisitor;

public interface SysEntry {
	
	public String getDisplayName();
	
	public int accept(SysEntryVisitor visitor);
	
	@Override String toString();
}
