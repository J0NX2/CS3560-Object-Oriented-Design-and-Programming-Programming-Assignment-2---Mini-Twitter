package visitor;

import main.Group;
import main.User;

public class UserTotalSysEntryVisitor implements SysEntryVisitor {
	private int numberOfUsers = 0;
	
	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	@Override
	public int visitGroup(Group group) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visitUser(User user) {
		setNumberOfUsers(user.getNumberOfUsers());
		return numberOfUsers;
	}
	
}
