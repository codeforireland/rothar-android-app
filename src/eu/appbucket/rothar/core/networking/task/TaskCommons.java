package eu.appbucket.rothar.core.networking.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskCommons {
	
	public JSONObject getJsonFromUrl(String userRegistrationUrl) throws IOException, JSONException {
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(doHttpPost(userRegistrationUrl), "UTF-8")); 
	    StringBuilder responseStrBuilder = new StringBuilder();
	    String inputStr;
	    while ((inputStr = streamReader.readLine()) != null) {
	        responseStrBuilder.append(inputStr);
	    }
	    return new JSONObject(responseStrBuilder.toString());
	}

	private InputStream doHttpPost(String urlString) throws IOException {
	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setReadTimeout(10000);
	    conn.setConnectTimeout(15000);
	    conn.setRequestMethod("POST");
	    conn.setDoInput(true);
	    conn.connect();
	    return conn.getInputStream();
	}
}
