package visitor;

import java.util.ArrayList;
import java.util.List;

import main.Group;
import main.User;

public class PositiveSysEntryVisitor implements SysEntryVisitor {
	private int positiveTweets = 0;
	
	@Override
	public int visitUser(User user) {
		positiveTweets = user.getPositiveMessageCount();
		return positiveTweets;
	}
	
	@Override
	public int visitGroup(Group group) {
		// TODO Auto-generated method stub
		return 0;
	}
}
