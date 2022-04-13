package tradingAccountsWithSwing;

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
 * => This app in tradingAccountWithSwing package runs in using Swing UI
 * => To run using only with console run MyCryptoApp class of tradingAccounts package
 */
public class MyCryptoApp {
	public static void main(String[] args) {
		AllAccounts allAccounts = new AllAccounts();
		allAccounts.start();
	}
}
