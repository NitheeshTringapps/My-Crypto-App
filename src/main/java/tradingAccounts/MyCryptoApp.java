package tradingAccounts;

/**
 * 
 * @author NITHEESH S
 * My Cryto App is a trading application to buy and sell crypcurrency with real time market price
 * This App uses API Data form Coingecko to get realtime price of Cryptocurrency
 * MyCryptoApp class contains the main method to start the Application
 *
 */

/*
 * NOTE:
 * => This app in tradingAccounts runs only in console
 * => To run using Swing UI run MyCryptoApp class of tradingAccountsWithSwing package
 */
public class MyCryptoApp {
	public static void main(String[] args) {
		AllAccounts allAccounts = new AllAccounts();
		allAccounts.start();
	}
}
