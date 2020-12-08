package visitor;

import main.Group;
import main.User;

public class GroupTotalSysEntryVisitor implements SysEntryVisitor {

	private int numberOfGroups = 0;
	
	public void setNumberOfGroups(int numberOfGroups) {
		this.numberOfGroups = numberOfGroups;
	}
	
	@Override
	public int visitUser(User user) {
		return 0;
	}

	@Override
	public int visitGroup(Group group) {
		setNumberOfGroups(group.getNumberOfGroups());
		return numberOfGroups;
	}
}
