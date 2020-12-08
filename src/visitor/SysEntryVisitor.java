package visitor;

import main.Group;
import main.User;

public interface SysEntryVisitor {
	public int visitUser(User user);
	public int visitGroup(Group group);
}
