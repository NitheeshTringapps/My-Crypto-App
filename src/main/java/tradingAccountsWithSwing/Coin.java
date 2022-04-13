package tradingAccountsWithSwing;

/**
 * 
 * @author NITHEESH S
 * Coins class contains all the properties of a cryptoCurrency
 * in a user's wallet like Name, Quantity, averageBuyPrice
 *
 */

public class Coin {
	private float Quantity;
	private float averageBuyPrice;
	private String CoinName;
	
	/**
	 * This constructor set the name of the cryptocurrency
	 * @param CoinName name of the cryptocurrency
	 */
	Coin(String CoinName){
		this.CoinName = CoinName;
	}
	
	/**
	 * gets the name of the cryptocurrency 
	 * @return the Name of the cryptocurrency
	 */
	public String getCoinName() {
		return CoinName;
	}

	/**
	 * Sets the Name of the cryptocurrency
	 * @param coinName name of the cryptocurrency
	 */
	public void setCoinName(String coinName) {
		CoinName = coinName;
	}

	/**
	 * gets the quantity of cryptocurrency present in account
	 * @return quantity of cryptocurrency present in account
	 */
	public float getQuantity() {
		return Quantity;
	}

	/**
	 * sets the quantity of cryptocurrency in account
	 * @param quantity quantity of a cryptocurrency
	 */
	public void setQuantity(float quantity) {
		Quantity = quantity;
	}

	/**
	 * get the Average Buy Price of the coin
	 * @return Average Buy Price of the coin
	 */
	public float getAverageBuyPrice() {
		return averageBuyPrice;
	}

	/**
	 * sets the Average Buy Price of the coin
	 * @param averageBuyPrice Average Buy Price of the coin
	 */
	public void setAverageBuyPrice(float averageBuyPrice) {
		this.averageBuyPrice = averageBuyPrice;
	}
}
