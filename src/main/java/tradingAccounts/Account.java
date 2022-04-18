package tradingAccounts;

/**
 * @author NITHEESH S
 * Account class has the implementation of all the methods
 * to do a transaction by a user
 * including implementation of UserTransactions interface
 */
import java.util.*;

public class Account implements UserTransactions{
	float mainBalance;
	String accountName;
	LinkedHashMap<String, Coin> coins = new LinkedHashMap<String, Coin>();
	static Scanner sc = new Scanner(System.in);
	
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
		System.out.println("Enter the coin you want to buy in full name: \nExample: bitcoin, ethereum, cardano, etc");
		String coinName = sc.next();
		coinName = coinName.toLowerCase();
		float price = RealTimePriceUsingAPI.getPriceOfCoin(coinName);
		if(price == 0) {
			System.out.println("Entered coin name is not Correct or not present!");
			return;
		}
		System.out.println("Current price of the coin: " + price);
		System.out.println("Enter the Quantity you want to buy: ");
		float quantity = sc.nextFloat();
		float buyCost = price * quantity;
		if(mainBalance < buyCost) {
			System.out.println("You don't have the required Balance to Buy this coin\nKindy Deposit amount to buy");
		}
		else {
			Set<String> allCoinsName = coins.keySet();
			if(allCoinsName.contains(coinName)) {
				Coin coin = coins.get(coinName);
				coin.setQuantity(coin.getQuantity() + quantity);
				float averagePrice = (buyCost + (coin.getAverageBuyPrice() * coin.getQuantity())) / (quantity + coin.getQuantity());
				coin.setAverageBuyPrice(averagePrice);
			}
			else {
				Coin coin = new Coin("CoinName");
				coins.put(coinName, coin);
				coin.setQuantity(quantity);
				float averagePrice = price;
				coin.setAverageBuyPrice(averagePrice);
			}
			mainBalance -= buyCost;
			System.out.println("Coin Bought Successfully");
		}
	}

	/**
	 * Called when Sells a coin
	 * When user sells a coin, it deletes/update its value in their corresponding account
	 */
	public void sell() {
		Set<String> allCoinsName = coins.keySet();
		System.out.println("Enter the coin you want to sell: ");
		String coinName = sc.next();
		coinName = coinName.toLowerCase();
		if(allCoinsName.contains(coinName)) {
			Coin coin = coins.get(coinName);
			System.out.println("You have " + coin.getQuantity() + " Quantity of " + coinName + " with Average Buy price of " + coin.getAverageBuyPrice() +  " to Sell");
			float price = RealTimePriceUsingAPI.getPriceOfCoin(coinName);
			System.out.println("Current price of the coin: " + price);
			System.out.println("Enter the Quantity of coin you want to sell: ");
			float quantity = sc.nextFloat();
			if(quantity > coin.getQuantity() ) {
				System.out.println("You don't have enough Quantity of this coin to sell");
				return;
			}
			System.out.println("Coin Sold Successfully");
			coin.setQuantity(coin.getQuantity() -  quantity);
			float sellCost = price * quantity;
			mainBalance += sellCost;
		}
		else {
			System.out.println("You don't have enough Quantity of " + coinName + " to Sell");
			return;
		}
	}

	/**
	 * Called when user deposit amount
	 * asks the user to enter the amount he wants to deposit
	 */
	public void depositAmount() {
		System.out.println("Enter the Amount you want to Deposit: ");
		mainBalance += sc.nextFloat();
		System.out.println("Amount Successfully Deposited!\nMain Balance: " + mainBalance);
	}

	/**
	 * Called when user withdraws amount
	 * asks the user to enter the amount he wants to withdraw
	 */
	public void withdrawAmount() {
		System.out.println("Main Balance : "+ mainBalance);
		System.out.println("Enter the Amount you want to Withdraw: ");
		float amount = sc.nextFloat();
		if(mainBalance < amount) {
			System.out.println("No sufficient amount to Withdraw");
			return;
		}
		else {
			mainBalance -= amount;
			System.out.println("Amount Successfully Withdrawn!\nMain Balance: " + mainBalance);
		}
	}
	
	/**
	 * Called to Display all coins that the user has in his portfolio
	 */
	public void displayCoins() {
		Set<String> allCoinNames = coins.keySet();
		if(allCoinNames.isEmpty()) {
			System.out.println("No Coins is present!");
			return;
		}
		else {
			for(String coinName: allCoinNames) {
				System.out.println("coin Name: " + coinName + ", Quantity: " + coins.get(coinName).getQuantity() + ", Average Price: " + coins.get(coinName).getAverageBuyPrice());
			}
		}
	}
	
	/**
	 * Called when user go to their account to buy/sell coin
	 * It coins all the options to do a transaction
	 */
	public  void accessPortfolio() {
		int choice = 0;
		while(true) {
			System.out.println("----------------------------------------------------------------------------");
			System.out.println("Current Portfolio : " + accountName + "\nMain Balance : " + mainBalance + "\n");
			System.out.println("Enter 1 to VIEW all the coins and coin balance");
			System.out.println("Enter 2 to BUY a coin");
			System.out.println("Enter 3 to SELL a coin");
			System.out.println("Enter 4 to Deposit Amount into Main Balance");
			System.out.println("Enter 5 to Withdraw Amount for Main Balance");
			System.out.println("Enter 0 to Get out of this Account and Goto Main Menu to view all the Accounts");
			choice = sc.nextInt();
			switch(choice) {
				case 1:
					displayCoins();
					break;
				case 2:
					buy();
					break;
				case 3:
					sell();
					break;
				case 4:
					depositAmount();
					break;
				case 5:
					withdrawAmount();
					break;
				case 0:
					System.out.println("Exiting current Account and Going to Main Menu........");
					return;
				default:
					System.out.println("Enter a valid choice!");
			}
		}
	}
}
