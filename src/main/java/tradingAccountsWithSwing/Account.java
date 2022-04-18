package tradingAccountsWithSwing;

/**
 * @author NITHEESH S
 * Account class has the implementation of all the methods
 * to do a transaction by a user
 * including implementation of UserTransactions interface
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

public class Account implements UserTransactions{
	float mainBalance;
	String accountName;
	LinkedHashMap<String, Coin> coins = new LinkedHashMap<String, Coin>();
	static Scanner sc = new Scanner(System.in);
	JFrame accessPortfolioFrame = new JFrame("Main Account");
	JFrame buyFrame;
	JFrame sellFrame;
	JFrame depositAmountFrame;
	JFrame withdrawAmountFrame;
	JFrame displayCoinsFrame;
	JTextField tf1, tf2;
	JLabel l1;
	String coinName;
	float price;
	
	/**
	 * This constructor sets the account name
	 * @param accountName name of the account
	 */
	Account(String accountName){
		this.accountName = accountName;
	}
	
	/**
	 * called when user Buys a coin
	 * When user buys a coin, it stores/update its value in their corresponding account
	 */
	public void buy() {
		//creating buyFrame
		buyFrame = new JFrame("Buy a Coin");
		JLabel l1=new JLabel("<html>Enter the coin you want to buy in full name: <br/>Example: bitcoin, ethereum, cardano, etc<html>");  
	    l1.setBounds(50,50, 350,60);
		
	    tf1=new JTextField(); 
		tf1.setBounds(150,150,95,30);
		
		JButton b1= new JButton("Search");
		b1.setBounds(150,200,95,30);
        
		//runs when user enters the coins and clicks search button
        b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				coinName = tf1.getText();
				coinName = coinName.toLowerCase();
				price = RealTimePriceUsingAPI.getPriceOfCoin(coinName);
				
				//shows a dialogbox if entered coin name is not correct or not present
				if(price == 0) {
					JOptionPane.showMessageDialog(accessPortfolioFrame,"Entered coin name is not Correct or not present!");
					buyFrame.setVisible(false);
					accessPortfolioFrame.setVisible(true);
					return;
				}
				
				//if entered coin is present, it creates a new frame to display current coin price 
				//and get the quantity of coin from the user
				final JFrame buyPriceFrame = new JFrame();
				JLabel l2=new JLabel("Current price of the coin: " + price);  
			    l2.setBounds(100,100, 300,30);
			    
			    JLabel l3=new JLabel("Enter the Quantity you want to buy: ");  
			    l3.setBounds(100,150, 300,30);
			    
			    tf2=new JTextField(); 
				tf2.setBounds(150,200,95,30);
				
				JButton b2= new JButton("Buy");
				b2.setBounds(150,250,95,30);
				
				//runs when user clicks Buy button
				b2.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						float quantity = Float.parseFloat(tf2.getText());
						float buyCost = price * quantity;
						
						//shows a dialog box if user doesn't havee required Balance to Buy this coin
						if(mainBalance < buyCost) {
							JOptionPane.showMessageDialog(accessPortfolioFrame,"You don't have the required Balance to Buy this coin\nKindy Deposit amount to buy");
							buyFrame.setVisible(false);
							buyPriceFrame.setVisible(false);
							accessPortfolioFrame.setVisible(true);
						}
						else {
							Set<String> allCoinsName = coins.keySet();
							//updates the coin quantity if user already has that coin
							if(allCoinsName.contains(coinName)) {
								Coin coin = coins.get(coinName);
								coin.setQuantity(coin.getQuantity() + quantity);
								float averagePrice = (buyCost + (coin.getAverageBuyPrice() * coin.getQuantity())) / (quantity + coin.getQuantity());
								coin.setAverageBuyPrice(averagePrice);
							}
							else {
								//creates a new coin if user doesn't has the coin
								Coin coin = new Coin("CoinName");
								coins.put(coinName, coin);
								coin.setQuantity(quantity);
								float averagePrice = price;
								coin.setAverageBuyPrice(averagePrice);
							}
							//decrementing cost from main balance
							mainBalance -= buyCost;
							JOptionPane.showMessageDialog(accessPortfolioFrame,"Coin Bought Successfully");
							buyPriceFrame.setVisible(false);
							accessPortfolioFrame.setVisible(true);
						}
					}
				});
				buyPriceFrame.add(l2);buyPriceFrame.add(l3);
				buyPriceFrame.add(tf2);
				buyPriceFrame.add(b2);
				buyPriceFrame.setSize(400,500);
				buyPriceFrame.setLayout(null);
				buyPriceFrame.setVisible(true);
				buyPriceFrame.setLocationRelativeTo(null);
				
				buyFrame.setVisible(false);
			}
		});
        buyFrame.add(l1);
        buyFrame.add(tf1);
        buyFrame.add(b1);
        buyFrame.setSize(400,500);
        buyFrame.setLayout(null);
        buyFrame.setVisible(true);
        buyFrame.setLocationRelativeTo(null); 
	}

	/**
	 * Called when Sells a coin
	 * When user sells a coin, it deletes/update its value in their corresponding account
	 */
	public void sell() {
		//creating sell Frame
		sellFrame = new JFrame("Sell a Coin");
		JLabel l1=new JLabel("Choose the coin you want to sell: ");  
	    l1.setBounds(100,100, 300,60);
		
	    //displaying all coins in combo box
	    final Set<String> allCoinsName = coins.keySet();
		Vector<String> vector = new Vector<String>(allCoinsName);
		final JComboBox<String> cb= new JComboBox<String>(vector);
		cb.setBounds(150,150,95,30);
	    
		JButton b1= new JButton("Ok");
		b1.setBounds(150,200,95,30);
		
		//runs if clicked OK button
		b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				coinName = cb.getItemAt(cb.getSelectedIndex());
				//shows a dialog box if No coin is present
				if(coinName == null) {
					JOptionPane.showMessageDialog(accessPortfolioFrame,"No coin is present");
					sellFrame.setVisible(false);
					accessPortfolioFrame.setVisible(true);
					return;
				}
				
				//creates a sellPriceFrame
				final JFrame sellPriceFrame = new JFrame();
				final Coin coin = coins.get(coinName);
				
				JLabel l1=new JLabel("<html>You have " + coin.getQuantity() + " Quantity of " + coinName + " with <br/>Average Buy price of " + coin.getAverageBuyPrice() +  " to Sell<html>");  
			    l1.setBounds(30,50, 350,30);
			    
			    //getting realtime price using Coingecko API
			    final float price = RealTimePriceUsingAPI.getPriceOfCoin(coinName);
			    JLabel l2=new JLabel("Current price of the coin: " + price);  
			    l2.setBounds(50,100, 300,30);
			    
			    JLabel l3=new JLabel("Enter the Quantity of coin you want to sell: ");
				l3.setBounds(30,150,350,30);
				
				tf1=new JTextField(); 
				tf1.setBounds(150,200,100,30);
				
				JButton b1= new JButton("Submit");
				b1.setBounds(150,250,100,30);
		        
				//runs if user clicked Submit button
		        b1.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						float quantity = Float.parseFloat(tf1.getText());
						//shows dialog box if user don't have enough Quantity of this coin to sell
						if(quantity > coin.getQuantity() ) {
							JOptionPane.showMessageDialog(accessPortfolioFrame,"You don't have enough Quantity of this coin to sell");
							sellPriceFrame.setVisible(false);
							accessPortfolioFrame.setVisible(true);
							return;
						}
						
						//runs if user have enough quantity to sell
						coin.setQuantity(coin.getQuantity() -  quantity);
						float sellCost = price * quantity;
						mainBalance += sellCost;
						
						JOptionPane.showMessageDialog(accessPortfolioFrame,"Coin Sold Successfully");
						sellPriceFrame.setVisible(false);
						accessPortfolioFrame.setVisible(true);
					}
				});
		        sellPriceFrame.add(l1);sellPriceFrame.add(l2);sellPriceFrame.add(l3);
		        sellPriceFrame.add(tf1);
		        sellPriceFrame.add(b1);
		        sellPriceFrame.setSize(400,500);
		        sellPriceFrame.setLayout(null);
				sellPriceFrame.setVisible(true);
				sellPriceFrame.setLocationRelativeTo(null); 
				sellFrame.setVisible(false);
			}
		});
		sellFrame.add(l1);
		sellFrame.add(cb);
		sellFrame.add(b1);
		sellFrame.setSize(400,500);
		sellFrame.setLayout(null);
		sellFrame.setVisible(true);
		sellFrame.setLocationRelativeTo(null);  
	}

	/**
	 * Called when user deposit amount
	 * asks the user to enter the amount he wants to deposit
	 */
	public void depositAmount() {
		//creating depositAmountFrame 
		depositAmountFrame = new JFrame("Deposit Amount in USD");
		JLabel l1=new JLabel("Enter the Amount you want to Deposit:");  
	    l1.setBounds(100,100, 300,30);
		
		tf1=new JTextField(); 
		tf1.setBounds(150,150,95,30);
		
		JButton b1= new JButton("Submit");
		b1.setBounds(150,200,95,30);
        
		//runs if clicked submit
        b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//incrementing mainBalance with deposit amount
				float amount = Float.parseFloat(tf1.getText());
				mainBalance += amount;
				JOptionPane.showMessageDialog(accessPortfolioFrame,"Amount Successfully Deposited!\nMain Balance: " + mainBalance); 
				depositAmountFrame.setVisible(false);
				accessPortfolioFrame.setVisible(true);
			}
		});
        
        depositAmountFrame.add(l1);
        depositAmountFrame.add(tf1);
        depositAmountFrame.add(b1);
        depositAmountFrame.setSize(400,500);
        depositAmountFrame.setLayout(null);
        depositAmountFrame.setVisible(true);
        depositAmountFrame.setLocationRelativeTo(null); 
	}

	/**
	 * Called when user withdraws amount
	 * asks the user to enter the amount he wants to withdraw
	 */
	public void withdrawAmount() {
		//Creating withdrawAmountFrame
		withdrawAmountFrame = new JFrame("Deposit Amount in USD");
		JLabel l1=new JLabel("Main Balance : "+ mainBalance);  
	    l1.setBounds(100,50, 300,30);
		
	    JLabel l2=new JLabel("Enter the Amount you want to Withdraw: ");  
	    l1.setBounds(100,100, 300,30);
	    
		tf1=new JTextField(); 
		tf1.setBounds(150,150,95,30);
		
		JButton b1= new JButton("Submit");
		b1.setBounds(150,200,95,30);
        
		//runs if clicked Submit button
        b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				float amount = Float.parseFloat(tf1.getText());
				if(mainBalance < amount) {
					JOptionPane.showMessageDialog(accessPortfolioFrame,"No sufficient amount to Withdraw");
				}
				else {
					//decrementing mainBalance
					mainBalance -= amount;
					JOptionPane.showMessageDialog(accessPortfolioFrame,"Amount Successfully Withdrawn!\nMain Balance: " + mainBalance);
				}
				withdrawAmountFrame.setVisible(false);
				accessPortfolioFrame.setVisible(true);
			}
		});
        
        withdrawAmountFrame.add(l2);
        withdrawAmountFrame.add(l1);
        withdrawAmountFrame.add(tf1);
        withdrawAmountFrame.add(b1);
        withdrawAmountFrame.setSize(400,500);
        withdrawAmountFrame.setLayout(null);
        withdrawAmountFrame.setVisible(true);
        withdrawAmountFrame.setLocationRelativeTo(null);
		
	}
	
	/**
	 * Called to Display all coins that the user has in his portfolio
	 */
	public void displayCoins() {
		//creating displayCoinsFrame
		displayCoinsFrame = new JFrame("View all the Coins");
		Set<String> allCoinNames = coins.keySet();
		//if no coins present it shows a dialog box
		if(allCoinNames.isEmpty()) {
			JOptionPane.showMessageDialog(accessPortfolioFrame,"No Coins is present!");  
			accessPortfolioFrame.setVisible(true);
			return;
		}
		
		Vector<String> vector = new Vector<String>();
        
		//displaying all coins using JList
		for(String coinName: allCoinNames) {
			vector.add("<html>coin Name: " + coinName + ", Quantity: " + coins.get(coinName).getQuantity() + ", <br/>Average Buy Price: " + coins.get(coinName).getAverageBuyPrice() +"<html>");
		}
		
		JList<String> jlist = new JList<String>(vector);
		jlist.setBounds(100, 100, 100, 30);
		
		JButton b1= new JButton("Back to Main Menu");
		b1.setBounds(100,350,200,30);
        
		//runs if clicked Back to Main Menu button
        b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				displayCoinsFrame.setVisible(false);
				accessPortfolioFrame.setVisible(true);
			}
		});
        
        
        displayCoinsFrame.add(b1);
        displayCoinsFrame.add(jlist);
		displayCoinsFrame.setSize(400,500);
		displayCoinsFrame.setVisible(true);
		displayCoinsFrame.setLocationRelativeTo(null);
	}
	
	/**
	 * Called when user go to their account to buy/sell coin
	 * It coins all the options to do a transaction
	 */
	public  void accessPortfolio() {
		//On clicking any button, it go to it's corresponding methods and performs  corresponding actions
		l1=new JLabel("Current Portfolio : " + accountName);  
	    l1.setBounds(150,50, 400,30);  
		
	    JButton b1=new JButton("VIEW all the coins and coin balance");  
	    b1.setBounds(100,100,300,30);
	    
	    b1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				accessPortfolioFrame.setVisible(false);
				displayCoins();
			}
		});
	    
	    JButton b2=new JButton("BUY a coin");  
	    b2.setBounds(100,150,300,30);
	    
	    b2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				accessPortfolioFrame.setVisible(false);
				buy();
			}
		});
	    
	    JButton b3=new JButton("SELL a coin");  
	    b3.setBounds(100,200,300,30);
	    
	    b3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				accessPortfolioFrame.setVisible(false);
				sell();
			}
		});
	    
	    JButton b4=new JButton("Deposit Amount into Main Balance");  
	    b4.setBounds(100,250,300,30);
	    
	    b4.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				accessPortfolioFrame.setVisible(false);
				depositAmount();
			}
		});
	    
	    JButton b5=new JButton("Withdraw Amount for Main Balance");  
	    b5.setBounds(100,300,300,30);
	    
	    b5.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				accessPortfolioFrame.setVisible(false);
				withdrawAmount();
			}
		});
	    
	    JButton b6=new JButton("Go To Main Menu");  
	    b6.setBounds(100,350,300,30);
	    
	    b6.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				accessPortfolioFrame.setVisible(false);
				AllAccounts.mainMenuFrame.setVisible(true);
				return;
			}
		});
	    
	    accessPortfolioFrame.add(l1);
	    accessPortfolioFrame.add(b1);accessPortfolioFrame.add(b2);accessPortfolioFrame.add(b3);accessPortfolioFrame.add(b4);accessPortfolioFrame.add(b5);accessPortfolioFrame.add(b6);
	    accessPortfolioFrame.setSize(500,500);
	    accessPortfolioFrame.setLayout(null);
	    accessPortfolioFrame.setVisible(true);
	    accessPortfolioFrame.setLocationRelativeTo(null);
	}
}
