package wheretospeach.ndm.com.wheretospeach.httpurlconnection;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ndm-PC on 1/1/2016.
 */
public class HttpUrlServerConnection
{
    JSONObject jsonobj=null;
    public HttpUrlServerConnection()
    {
	}

    public String setURLandData(String urlString,String JsonString)
    {
		//String response = "";
		StringBuffer response = new StringBuffer();

		try
		{
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");


			if(JsonString!=null)
			{
                OutputStream os = conn.getOutputStream();
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
				writer.write(JsonString);

				writer.flush();
				writer.close();
				os.close();
			}



			int responseCode=conn.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK)
			{
				String line;
				BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line=br.readLine()) != null)
				{

					//response+=line;
					response.append(line);

				}
			}
			else
			{
				response.toString();
			}
		}
		catch (Exception e)
		{
    			e.printStackTrace();
    	}
    		return response.toString();
    	}
}
