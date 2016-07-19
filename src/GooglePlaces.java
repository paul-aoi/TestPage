

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GooglePlaces {
	
	public static ArrayList<String> getInfo(String placeID) throws ClientProtocolException, IOException, ParseException {

			String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeID
					+ "&key=AIzaSyDM0lmlS-ptLTR9KnDZSGUyijPQ5H1fsZs";

			HttpClient client = HttpClientBuilder.create().build();
			
			HttpGet request = new HttpGet(url);
			
			HttpResponse response = client.execute(request);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line.trim());

			}

			request.releaseConnection();

			JsonElement jelement = new JsonParser().parse(result.toString());
		
			JsonObject jobject = jelement.getAsJsonObject();
			
			jobject = jobject.getAsJsonObject("result");

			JsonElement name = jobject.get("name");
			JsonElement address = jobject.get("formatted_address");
			JsonElement phoneNumber = jobject.get("formatted_phone_number");
			JsonElement icon = jobject.get("icon");
			
			
			String shop_name = name.toString().replaceAll("\"", "");
			String shop_address = address.toString().replaceAll("\"", "");
			String phone = phoneNumber.toString().replaceAll("\"", "");
			String iconurl = ("<img src=\"" + icon.toString().replaceAll("\"", "") + "\">");
		
			
			
			ArrayList<String> info = new ArrayList<String>();
					
			
			info.add(shop_name);
			info.add(phone);
			info.add(shop_address);
			info.add(iconurl);

		return info;	
		}

	public static void main(String[] args) throws ClientProtocolException, IOException, ParseException {
		System.out.println(getInfo("ChIJtzwfLTItO4gRxwpKgcgFomE"));
	}
}