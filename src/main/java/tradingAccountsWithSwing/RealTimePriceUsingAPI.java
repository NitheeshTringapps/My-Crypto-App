package tradingAccountsWithSwing;

/**
 * @author NITHEESH S
 * This class is used to Get RealTime Cryptocurrency's Price from Coingecko
 * using Apache HTTPComponents library.
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

import org.json.*;

public class RealTimePriceUsingAPI {
	
	/**
	 * Called to get RealTime price of a cryptocurrency
	 * @param coin name of the cryptocurrency for which real time price is needed
	 * @return price of the given cryptocurrency
	 */
	public static float getPriceOfCoin(String coin) {
		String uri = "https://api.coingecko.com/api/v3/simple/price?ids=" + coin + "&vs_currencies=usd"; //Request URL
		String result = "";
		try {
			result = makeAPICall(uri); //calling makeAPICall function to get JSON response in string format from the request URI
		} catch (IOException e) {
			System.out.println("Error: cannont access content - " + e.toString());
		} catch (URISyntaxException e) {
			System.out.println("Error: Invalid URL " + e.toString());
		}
		if(result.toString().equals("{}")) {
			return 0;
		}
		JSONObject json = new JSONObject(result);
		Object price = json.getJSONObject(coin).get("usd");
		float requiredPrice;
		if(price.getClass() == Integer.class) {
			requiredPrice = Integer.parseInt(price.toString());
		}
		else {
			BigDecimal x = (BigDecimal) json.getJSONObject(coin).get("usd");
			requiredPrice = x.floatValue();
		}
		return requiredPrice;
	}

	/**
	 * Called to send Request to Coingecko and recieve JSON Response in String Format
	 * @param uri URI of API service provider(Coingecko)
	 * @return JSON response in String format
	 */
	public static String makeAPICall(String uri)
			throws URISyntaxException, IOException {
		String response_content = "";
		URIBuilder query = new URIBuilder(uri); //creating URI
		CloseableHttpClient client = HttpClients.createDefault(); //creating client
		HttpGet request = new HttpGet(query.build()); //creating request with CURL
		request.setHeader(HttpHeaders.ACCEPT, "application/json"); //adding header to request CURL
		CloseableHttpResponse response = client.execute(request); //sending request and getting response

		try {
			HttpEntity entity = response.getEntity(); //Getting HTTP entity
			response_content = EntityUtils.toString(entity); //Parsing entity to String
			EntityUtils.consume(entity);
		} finally {
			response.close();
		}

		return response_content;
	}

}
