package tradingAccountsWithSwing;

/**
 * 
 * @author NITHEESH S
 * This interface contains all the options that can be done by a user
 *
 */
public interface UserTransactions {
	
	/**
	 * called when user Buys a coin
	 */
	void buy();
	
	/**
	 * Called when Sells a coin
	 */
	void sell();
	
	/**
	 * Called when user deposit amount
	 */
	void depositAmount();
	
	/**
	 * Called when user withdraws amount
	 */
	void withdrawAmount();
	
	/**
	 * Called to Display all coins that the user has
	 */
	void displayCoins();
}