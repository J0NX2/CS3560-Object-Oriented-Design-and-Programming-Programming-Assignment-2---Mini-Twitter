package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import visitor.GroupTotalSysEntryVisitor;
import visitor.MessageTotalSysEntryVisitor;
import visitor.PositiveSysEntryVisitor;
import visitor.UserTotalSysEntryVisitor;

import javax.swing.JTree;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class AdminControlPanel extends JFrame {
	private static final AdminControlPanel instance = new AdminControlPanel();
	private JPanel contentPane;
	private JTextField userId;
	private JTextField groupId;
	
	Group group = new Group();
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode(group.getRoot());
	private DefaultTreeModel model = new DefaultTreeModel(root);
	
	private ArrayList<User> userList;
	private ArrayList<Group> groupList;
	public ArrayList<String> userIDList;
	public ArrayList<String> groupIDList;
	private String selectedUser;
	private HashMap<String, UserView> userViews = new HashMap<String, UserView>();
	
	private AdminControlPanel() {
		setTitle("Mini Twitter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTree jtree = new JTree(model);
		jtree.setBounds(12, 13, 297, 467);
		contentPane.add(jtree);
		jtree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtree.getLastSelectedPathComponent();
			}
		});
		
		
		userId = new JTextField();
		userId.setBounds(321, 30, 213, 62);
		contentPane.add(userId);
		userId.setColumns(10);
		
		groupId = new JTextField();
		groupId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		groupId.setColumns(10);
		groupId.setBounds(321, 119, 213, 62);
		contentPane.add(groupId);
		
		userIDList = new ArrayList();
		groupIDList = new ArrayList();
		userList = new ArrayList();
		groupList = new ArrayList();
		userIDList.add("Root");
		groupList.add(new Group("Root"));
		
		Group entries = new Group();
		
		JButton btnNewButton = new JButton("Add User");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(userId.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "User ID cannot be empty", "Add User Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
		            if (!userIDList.contains(userId.getText())) {
		                if (jtree.getSelectionPath() == null) {
		                    User user = new User(userId.getText());
		                    DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user, false);
		                    userList.add(user);
		                    userIDList.add(userId.getText());
		                    userViews.put(user.getDisplayName(), new UserView(user, userIDList, userList, userViews));
		                    root.add(userNode);
		                } else {
		                    DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) jtree.getSelectionPath().getLastPathComponent();
		                    if (selectedElement == root) {
		                        User user = new User(userId.getText());
		                        DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user, false);
		                        userList.add(user);
		                        userIDList.add(userId.getText());
		                        userViews.put(user.getDisplayName(), new UserView(user, userIDList, userList, userViews));
		                        root.add(userNode);
		                    } else if (selectedElement.getUserObject() instanceof Group) {
		                        User user = new User(userId.getText());
		                        DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user, false);
		                        userList.add(user);
		                        userIDList.add(userId.getText());
		                        userViews.put(user.getDisplayName(), new UserView(user, userIDList, userList, userViews));
		                        selectedElement.add(userNode);
		                    } else if (selectedElement.getUserObject() instanceof User) {
		                        User user = new User(userId.getText());
		                        DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user, false);
		                        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedElement.getParent();
		                        userList.add(user);
		                        userIDList.add(userId.getText());
		                        userViews.put(user.getDisplayName(), new UserView(user, userIDList, userList, userViews));
		                        parentNode.add(userNode);
		                    }
		                }
		            } else {
		                JOptionPane.showMessageDialog(null, "This user already exists.", "Add User Error", JOptionPane.INFORMATION_MESSAGE);
		            }
		        }
				
				jtree.updateUI();
				expandTree(jtree, 0, jtree.getRowCount());
				userId.setText("");
			}
		});
		btnNewButton.setBounds(557, 30, 213, 62);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add Group");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(groupId.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Group ID cannot be empty", "Add Group Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
		            if(!groupIDList.contains(groupId.getText())) {
		                if(jtree.getSelectionPath() == null) {
		                    group = new Group(groupId.getText());
		                    DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
		                    groupList.add(group);
		                    groupIDList.add(groupId.getText());
		                    root.add(groupNode);
		                } 
		                else {
		                    DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) jtree.getSelectionPath().getLastPathComponent();
		                    if(selectedElement == root) {
		                        group = new Group(groupId.getText());
		                        DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
		                        groupList.add(group);
		                        groupIDList.add(groupId.getText());
		                        root.add(groupNode);
		                    } 
		                else if(groupIDList.contains(selectedElement.getUserObject().toString())) {
		                        group = new Group(groupId.getText());
		                        DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
		                        groupList.add(group);
		                        groupIDList.add(groupId.getText());
		                        selectedElement.add(groupNode);
		                    }
		                }
		            } 
		            else {
		                JOptionPane.showMessageDialog(null, "This group already exists.", "Add Group Error", JOptionPane.INFORMATION_MESSAGE);
		            }
		        }
				
				jtree.updateUI();
				expandTree(jtree, 0, jtree.getRowCount());
				groupId.setText("");
			}
		});
		btnNewButton_1.setBounds(557, 119, 213, 62);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Open User View");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jtree.getSelectionPath() == null) {
		            JOptionPane.showMessageDialog(null, "Please select a user to view.", "User View Error", JOptionPane.INFORMATION_MESSAGE);
		        } else {
		            DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) jtree.getSelectionPath().getLastPathComponent();
		            if (selectedElement.getUserObject() instanceof Group || selectedElement.getUserObject().toString().equals("Root")) {
		                JOptionPane.showMessageDialog(null, "Please select a user to view.", "User View Error", JOptionPane.INFORMATION_MESSAGE);
		            } else if (selectedElement.getUserObject() instanceof User) {
		                selectedUser = selectedElement.getUserObject().toString();
		                User user = new User(selectedUser);
		                UserView userView = userViews.get(selectedUser);
		                userView.setVisible(true);
		                userView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		            }
		        }
			}
		});
		btnNewButton_2.setBounds(321, 222, 449, 62);
		contentPane.add(btnNewButton_2);
		
		MessageTotalSysEntryVisitor v = new MessageTotalSysEntryVisitor();
		JButton btnNewButton_3 = new JButton("Show Message Total");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageTotalSysEntryVisitor visitor = new MessageTotalSysEntryVisitor();
				int totalTweets = 0; 
				for(User user : userList) {
					totalTweets += user.accept(visitor);
				}
				JOptionPane.showMessageDialog(null, "There are a total of " + totalTweets + " message(s).", "Show Message Total", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton_3.setBounds(321, 418, 213, 62);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Show User Total");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserTotalSysEntryVisitor visitor = new UserTotalSysEntryVisitor();
				int totalUsers = 0; 
				for(User user : userList) {
					totalUsers += user.accept(visitor);
				}
				JOptionPane.showMessageDialog(null, "There are a total of " + totalUsers + " user(s).", "Show User Total", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton_4.setBounds(321, 343, 213, 62);
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Show Group Total");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GroupTotalSysEntryVisitor visitor = new GroupTotalSysEntryVisitor();
				int totalGroups = -1; // account for Root group
				for(Group group : groupList) {
					totalGroups += group.accept(visitor);
				}
				JOptionPane.showMessageDialog(null, "There are a total of " + totalGroups + " group(s).", "Show Group Total", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton_5.setBounds(557, 343, 213, 62);
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Show Positive Percentage");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PositiveSysEntryVisitor posVisitor = new PositiveSysEntryVisitor();
				MessageTotalSysEntryVisitor msgVisitor = new MessageTotalSysEntryVisitor();
				double positiveWords = 0;
				double totalTweets = 0;
				for(User user : userList) {
					positiveWords += user.accept(posVisitor);
					totalTweets += user.accept(msgVisitor);
				}
				double percentage = (positiveWords / totalTweets) * 100;
				JOptionPane.showMessageDialog(null, "The percentage of positive tweets is " + percentage + "%", "Show Positive Percentage", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton_6.setBounds(557, 418, 213, 62);
		contentPane.add(btnNewButton_6);
		
		JLabel lblNewLabel = new JLabel("User Id");
		lblNewLabel.setBounds(321, 13, 56, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Group Id");
		lblNewLabel_1.setBounds(321, 103, 56, 16);
		contentPane.add(lblNewLabel_1);
	}
	
	private void expandTree(JTree tree, int index, int rowCount) {
        for(int i = index; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if(tree.getRowCount() != rowCount) {
            expandTree(tree, rowCount, tree.getRowCount());
        }
    }
	
	public static AdminControlPanel getInstance() {
		return instance;
	}
}
