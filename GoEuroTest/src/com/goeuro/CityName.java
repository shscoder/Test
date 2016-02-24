package com.goeuro;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 */

/**
 * @author sourav
 *
 */
public class CityName {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		if(args[0]!=null && args[0].length()>0)
		{
			  try {

					URL url = new URL("http://api.goeuro.com/api/v2/position/suggest/en/"+args[0]);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Accept", "application/json");

					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : "
								+ conn.getResponseCode());
					}

					BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

					String output;
					System.out.println("Output from Server .... \n");
					StringBuilder sb=new StringBuilder();
					while ((output = br.readLine()) != null) {
						System.out.println(output);
						sb.append(output);
					}
					
					br.close();
					conn.disconnect();
					
					FileWriter fileWriter=new FileWriter("ouput.csv");
				    fileWriter.write("_id, name, type, latitude, longitude \n");
				    
					JSONArray jArray= new JSONArray(sb.toString());
					for (int i = 0; i < jArray.length(); i++)
					{
					    JSONObject jObj = jArray.getJSONObject(i);
					    JSONObject locationObj=jObj.getJSONObject("geo_position");
					    String line= jObj.getInt("_id")+","+jObj.getString("name")+","+jObj.getString("type")+","+locationObj.getDouble("latitude")+","+locationObj.getDouble("longitude") +"\n";
					    fileWriter.write(line);
					}
					fileWriter.close();
					
					
				  } catch (MalformedURLException e) {

					e.printStackTrace();

				  } catch (IOException e) {

					e.printStackTrace();

				  } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		else
		{
			System.out.println("Please enter a city name");
		}
	}

}
