package eu.appbucket.rothar.core.networking.task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskCommons {
	
	public JSONObject postToUrl(String url) throws IOException, JSONException {
		return this.postToUrl(url, null);
	}
	
	public JSONObject postToUrl(String url, Map<String, String> payload) throws IOException, JSONException {
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(doHttpPost(url, payload), "UTF-8")); 
	    StringBuilder responseStrBuilder = new StringBuilder();
	    String inputStr;
	    while ((inputStr = streamReader.readLine()) != null) {
	        responseStrBuilder.append(inputStr);
	    }
	    return new JSONObject(responseStrBuilder.toString());
	}

	private InputStream doHttpPost(String urlString, Map<String, String> payload) throws IOException, JSONException {		
		URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setReadTimeout(10000);
	    conn.setConnectTimeout(15000);	    
	    conn.setRequestMethod("POST");
	    conn.setDoInput(true);
	    conn.setRequestProperty("Accept", "application/json");
	    conn.setRequestProperty("Content-Type", "application/json");
		if(payload != null) {
			JSONObject jsonPayload = new JSONObject();
			for(String key: payload.keySet()) {
				jsonPayload.put(key, payload.get(key));	
			}
			BufferedWriter out =  new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			out.write(jsonPayload.toString());
			out.close();
		}	    
	    conn.connect();
	    return conn.getInputStream();
	}
}
