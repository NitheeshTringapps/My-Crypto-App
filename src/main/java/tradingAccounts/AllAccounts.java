package tradingAccounts;

/**
 * @author NITHEESH S
 * AllAccounts class allows to create multiple accounts, manages
 * them and to get into to a required account
 */

import java.util.*;

public class AllAccounts {
	LinkedHashMap<String, Account> allAccount = new LinkedHashMap<String, Account>();
	Scanner sc = new Scanner(System.in);
	
	/**
	 * creates a new account
	 */
	public void createAccount() {
		System.out.println("Enter your new Account name: ");
		String accountName = sc.next();
		allAccount.put(accountName, new Account(accountName));
	}
	
	/**
	 * Shows all the accounts present
	 */
	public void viewAllAccount() {
		Set<String> allPortfolioNames = allAccount.keySet();
		if(allPortfolioNames.isEmpty()) {
			System.out.println("No Account is present!");
			return;
		}
		System.out.println("All Account's are:");
		for(String accountName: allPortfolioNames) {
			System.out.println(accountName);
		}
	}
	
	/**
	 * Goto (Login) to a required account
	 */
	public void visitAccount() {
		System.out.println("Enter name of the Account you wish to go and Buy/Sell coins: ");
		String accountName = sc.next();
		Set<String> allAccountNames = allAccount.keySet();
		if(allAccountNames.contains(accountName)) {
			allAccount.get(accountName).accessPortfolio();
		}
		else {
			System.out.println("Entered Account Name is not present!");
		}
	}
	
	/**
	 * Delete a required account
	 */
	public void deleteAccount() {
		System.out.println("Enter name of the Account you wish to delete");
		String accountName = sc.next();
		Set<String> allAccountNames = allAccount.keySet();
		if(allAccountNames.contains(accountName)) {
			allAccount.remove(accountName);
			System.out.println(accountName + "\'s Account is removed Successfully!");
		}
		else {
			System.out.println("Entered Account Name is not present!");
		}
	}
	
	/**
	 * Starts the operation of this class by getting input from the user
	 */
	public void start(){
		int choice = 0;
		
		while(true) {
			System.out.println("================================================================================");
			System.out.println("Enter 1 to create a new Account");
			System.out.println("Enter 2 to view all the Accounts");
			System.out.println("Enter 3 to GoTo an Account to buy/sell coins");
			System.out.println("Enter 4 to delete an Account");
			choice = sc.nextInt();
			switch(choice) {
				case 1:
					createAccount();
					break;
				case 2:
					viewAllAccount();
					break;
				case 3:
					visitAccount();
					break;
				case 4:
					deleteAccount();
					break;
				default:
					System.out.println("Enter a valid choice!");
			}
		}
	}
}
