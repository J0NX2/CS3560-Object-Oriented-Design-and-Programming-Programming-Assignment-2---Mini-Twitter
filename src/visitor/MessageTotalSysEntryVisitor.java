package visitor;

import main.Group;
import main.User;

public class MessageTotalSysEntryVisitor implements SysEntryVisitor {
	private int numberOfTweets;
	
	@Override
	public int visitUser(User user) {
		setNumberOfTweets(user.getTweetCount());
		return numberOfTweets;
	}

	public void setNumberOfTweets(int tweets) {
		this.numberOfTweets = tweets;
	}
	
	@Override
	public int visitGroup(Group group) {
		// TODO Auto-generated method stub
		return 0;
	}

}
