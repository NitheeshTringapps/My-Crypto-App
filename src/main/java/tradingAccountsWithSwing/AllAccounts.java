package tradingAccountsWithSwing;

/**
 * @author NITHEESH S
 * AllAccounts class allows to create multiple accounts, manages
 * them and to get into to a required account
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

public class AllAccounts {
	static LinkedHashMap<String, Account> allAccount = new LinkedHashMap<String, Account>();
	static Scanner sc = new Scanner(System.in);
	public static JFrame mainMenuFrame = new JFrame("Main Menu");
	JFrame createAccountFrame;
	JFrame viewAllAccountFrame;
	JFrame visitAccountFrame;
	JFrame deleteAccountFrame;
	String accountName;
	JTextField tf1;
	
	/**
	 * creates a new account
	 * and stores in allAccount
	 */
	public void createAccount() {
		createAccountFrame = new JFrame("Create an Account");
		JLabel l1=new JLabel("Enter your new Account name:");  
	    	l1.setBounds(100,50, 300,30);
		
		tf1=new JTextField(); 
		tf1.setBounds(150,100,95,30);
		
		JButton b1= new JButton("Submit");
		b1.setBounds(150,150,95,30);
        
        	b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				accountName = tf1.getText();
				allAccount.put(accountName, new Account(accountName));
				createAccountFrame.setVisible(false);
				mainMenuFrame.setVisible(true);
			}
		});
        
		createAccountFrame.add(l1);
		createAccountFrame.add(tf1);
		createAccountFrame.add(b1);
		createAccountFrame.setSize(400,500);
		createAccountFrame.setLayout(null);
		createAccountFrame.setVisible(true);
		createAccountFrame.setLocationRelativeTo(null);   
	}
	
	/**
	 * Shows all the accounts present
	 */
	public void viewAllAccount() {
		viewAllAccountFrame = new JFrame("View all the Accounts");
		Set<String> allAccountsNames = allAccount.keySet();
		if(allAccountsNames.isEmpty()) {
			JOptionPane.showMessageDialog(mainMenuFrame,"No Account is present!");  
			mainMenuFrame.setVisible(true);
			return;
		}
		
		Vector<String> vector = new Vector<String>();
        
		for(String accountName: allAccountsNames) {
			vector.add(accountName);
		}
		
		JList<String> jlist = new JList<String>(vector);
		jlist.setBounds(100, 100, 100, 30);
		
		JButton b1= new JButton("Back to Main Menu");
		b1.setBounds(100,350,200,30);
        
        	b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				viewAllAccountFrame.setVisible(false);
				mainMenuFrame.setVisible(true);
			}
		});
        
		viewAllAccountFrame.add(b1);
		viewAllAccountFrame.add(jlist);
		viewAllAccountFrame.setSize(400,500);
		viewAllAccountFrame.setVisible(true);
		viewAllAccountFrame.setLocationRelativeTo(null);
	}
	
	/**
	 * Goto (Login) to a required account
	 * to buy/sell a cryptocurrency
	 */
	public void visitAccount() {
		visitAccountFrame = new JFrame("Goto an Account");
		
		JLabel l1=new JLabel("Select the Account you wish to go and Buy/Sell coins: ");  
	    	l1.setBounds(10,50, 600,30);
		JButton b1= new JButton("Open");
		b1.setBounds(150,250,95,30);
		
		final Set<String> allAccountNames = allAccount.keySet();
		Vector<String> vector = new Vector<String>(allAccountNames);
		final JComboBox<String> cb= new JComboBox<String>(vector);
		cb.setBounds(150,150,95,30);
		
		b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				accountName = cb.getItemAt(cb.getSelectedIndex());
				if(allAccountNames.contains(accountName)) {
					visitAccountFrame.setVisible(false);
					allAccount.get(accountName).accessPortfolio();
				}
				else {
					JOptionPane.showMessageDialog(mainMenuFrame,"Entered Account Name is not present!");
					mainMenuFrame.setVisible(true);
				}
				visitAccountFrame.setVisible(false);
			}
		});
		
		visitAccountFrame.add(cb);
		visitAccountFrame.add(l1);
		visitAccountFrame.add(b1);
		visitAccountFrame.setSize(400,500);
		visitAccountFrame.setLayout(null);
		visitAccountFrame.setVisible(true);
		visitAccountFrame.setLocationRelativeTo(null);   
	}
	
	/**
	 * Delete a required account
	 * from AllAccount
	 */
	public void deleteAccount() {
		deleteAccountFrame = new JFrame("Delete an Account");
		
		JLabel l1=new JLabel("Select the Account you wish to delete");  
	    	l1.setBounds(50,50, 600,30);
		JButton b1= new JButton("Delete");
		b1.setBounds(150,250,95,30);
		
		final Set<String> allAccountNames = allAccount.keySet();
		Vector<String> vector = new Vector<String>(allAccountNames);
		final JComboBox<String> cb= new JComboBox<String>(vector);
		cb.setBounds(150,150,95,30);
		
		b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				accountName = cb.getItemAt(cb.getSelectedIndex());
				if(allAccountNames.contains(accountName)) {
					allAccount.remove(accountName);
					JOptionPane.showMessageDialog(mainMenuFrame,accountName + "\'s Account is removed Successfully!");
					deleteAccountFrame.setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(mainMenuFrame,"Entered Account Name is not present!");
				}
				deleteAccountFrame.setVisible(false);
				mainMenuFrame.setVisible(true);
			}
		});
		
		deleteAccountFrame.add(cb);
		deleteAccountFrame.add(l1);
		deleteAccountFrame.add(b1);
		deleteAccountFrame.setSize(400,500);
		deleteAccountFrame.setLayout(null);
		deleteAccountFrame.setVisible(true);
		deleteAccountFrame.setLocationRelativeTo(null);
	}
	
	/**
	 * Starts the operation of this class
	 * Gets the input from the user to do various options
	 */
	public void start() {
		
	    JLabel l1=new JLabel("Welcome To Crypto Trading App");  
	    l1.setBounds(80,50, 300,30);  
	    
	    JButton b1=new JButton("Create a new Account");  
	    b1.setBounds(100,100,200,30);
	    
	    b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mainMenuFrame.setVisible(false);
				createAccount();
			}
		});
	    JButton b2=new JButton("View All Accounts");  
	    b2.setBounds(100,150,200,30);
	    
	    b2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mainMenuFrame.setVisible(false);
				viewAllAccount();
			}
		});
	    
	    JButton b3=new JButton("Goto an Account");  
	    b3.setBounds(100,200,200,30);
	    
	    b3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mainMenuFrame.setVisible(false);
				visitAccount();
			}
		});
	    
	    JButton b4=new JButton("Delete an Account");  
	    b4.setBounds(100,250,200,30);
	    
	    b4.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mainMenuFrame.setVisible(false);
				deleteAccount();
			}
		});
	    
	    mainMenuFrame.add(l1);
	    mainMenuFrame.add(b1); mainMenuFrame.add(b2); mainMenuFrame.add(b3); mainMenuFrame.add(b4);
	    mainMenuFrame.setSize(400,500);
	    mainMenuFrame.setLayout(null);
	    mainMenuFrame.setVisible(true);
	    mainMenuFrame.setLocationRelativeTo(null);   
	}
}
