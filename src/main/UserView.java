package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserView extends JFrame implements Observer{

	private JPanel contentPane;
	private JTextField userId;
	private JTextField tweetMessage;

	private String username;
	private User user;
	private ArrayList<User> userList;
    private HashMap<String, UserView> userViews;
    private ArrayList<String> userIDList;
    DefaultListModel<String> followingModel;
    DefaultListModel<String> newsFeedModel;
    private JList newsFeedList;
    private JLabel userUpdate;

	public UserView(User user, ArrayList<String> userIDList, ArrayList<User> userList, HashMap<String, UserView> userViews) {
		setTitle(user.getDisplayName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userId = new JTextField();
		userId.setBounds(12, 31, 325, 52);
		contentPane.add(userId);
		userId.setColumns(10);
		
		this.user = user;
        this.username = user.getDisplayName();
        this.userIDList = userIDList;
        this.userList = userList;
        this.userViews = userViews;
        this.setTitle(username + "'s User View");
        followingModel = new DefaultListModel<String>();
        newsFeedModel = new DefaultListModel<String>();
        
        JList followingList = new JList(user.getFollowing().toArray());
		followingList.setBounds(12, 113, 578, 106);
		contentPane.add(followingList);
		
		newsFeedList = new JList(user.getNewsFeed().toArray());
		newsFeedList.setBounds(12, 341, 578, 146);
		contentPane.add(newsFeedList);
	
		JLabel lblNewLabel = new JLabel("User ID");
		lblNewLabel.setBounds(12, 13, 56, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Follow User");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!userIDList.contains(userId.getText())) {
		            JOptionPane.showMessageDialog(null, "This user does not exist.", "Follow Error", JOptionPane.INFORMATION_MESSAGE);
		            userId.setText("");
		            return;
		        } 
				else if (user.getFollowing().contains(userId.getText())) {
		            JOptionPane.showMessageDialog(null, "You are already following this user.", "Follow Error", JOptionPane.INFORMATION_MESSAGE);
		        } 
				else if (user.getDisplayName().equals(userId.getText())) {
		            JOptionPane.showMessageDialog(null, "You cannot follow yourself!", "Follow Error", JOptionPane.INFORMATION_MESSAGE);
		        } 
				else {
		            followingModel.addElement("- " + userId.getText());
		            followingList.setModel(followingModel);
		            user.follow(userId.getText());
		            for(int i = 0; i < userList.size(); i++) {
		                if(userList.get(i).getDisplayName().equals(userId.getText())) {
		                    userList.get(i).attach(user);
		                }
		            }
		        }
		        userId.setText("");
		        followingList.updateUI();
		        newsFeedList.updateUI();
			}
		});
		btnNewButton.setBounds(349, 31, 241, 52);
		contentPane.add(btnNewButton);
		
		tweetMessage = new JTextField();
		tweetMessage.setColumns(10);
		tweetMessage.setBounds(12, 260, 429, 52);
		contentPane.add(tweetMessage);
		
		JButton btnNewButton_1 = new JButton("Post Tweet");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tweetMessage.getText().equals("")) {
		            JOptionPane.showMessageDialog(null, "Tweets cannot be blank!", "Tweet Error", JOptionPane.INFORMATION_MESSAGE);
		        } 
				else {
		            user.tweet(tweetMessage.getText());
		            newsFeedModel.addElement(user.getNewsFeed().get(user.getNewsFeed().size()-1));
		            newsFeedList.setModel(newsFeedModel);
		            tweetMessage.setText("");

		            // update newsFeed
		            List<User> ifollowers = user.getObserver();
		            int isize = ifollowers.size();
		            String msg = user.getNewsFeed().get(0);
		            for (int j = 0; j < isize; j++) {
		                User myuser = ifollowers.get(j);
		                UserView myUserView = userViews.get(myuser.getDisplayName());
		                myuser.update();
		                myUserView.newsFeedModel.insertElementAt(msg, 0);
		                myUserView.newsFeedList.setModel(newsFeedModel);
		                myUserView.tweetMessage.setText("");
		                myUserView.revalidate();
		                myUserView.repaint();
		            }
		            newsFeedList.updateUI();
		            revalidate();
		            repaint();
		        }
				userUpdate.setText("User last update at: " + Long.toString(user.getLastUpdateTime()));
			}
		});
		btnNewButton_1.setBounds(453, 260, 137, 52);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("Currently Following");
		lblNewLabel_1.setBounds(12, 96, 116, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Tweet Message");
		lblNewLabel_1_1.setBounds(12, 239, 116, 16);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("News Feed");
		lblNewLabel_1_2.setBounds(12, 325, 116, 16);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_2 = new JLabel("User created at: " + user.getCreationTime());
		lblNewLabel_2.setBounds(12, 517, 578, 16);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton_2 = new JButton("Last Updated User");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean userFound = false;
				for(User users : userList) {
					if(users.getLastUpdateTime() == user.getLastUpdateTime()) {
						String last = users.getDisplayName();
						JOptionPane.showMessageDialog(null, "User: " + last, "Last Updated User", JOptionPane.INFORMATION_MESSAGE);
						userFound = true;
						break;
					}
				}
				if(!userFound) {
					JOptionPane.showMessageDialog(null, "No updated user", "Last Updated User", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnNewButton_2.setBounds(431, 495, 159, 38);
		contentPane.add(btnNewButton_2);
		
		userUpdate = new JLabel("User last update at: ");
		userUpdate.setBounds(12, 500, 578, 16);
		contentPane.add(userUpdate);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
