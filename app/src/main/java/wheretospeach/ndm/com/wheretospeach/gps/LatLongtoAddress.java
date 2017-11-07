package wheretospeach.ndm.com.wheretospeach.gps;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import wheretospeach.ndm.com.wheretospeach.general.WebUrl;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncResult;


//Not in use
public class LatLongtoAddress extends AsyncTask<String, Void, String>
{
	String mResult;
	private static final String PLACES_API_BASE = "http://maps.googleapis.com/maps/api/geocode/json";
	Context ctx;

	public LatLongtoAddress(Context ctx)
	{
		this.ctx=ctx;

	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub

		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();

		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE);
			sb.append("?latlng=" + params[0]+","+params[1]);
			sb.append("&sensor=false");   //nearbysearch  //regions
			sb.append("&_=1302084865163");

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}

		}
		catch (Exception e) {
			// TODO: handle exception

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}



		return jsonResults.toString();
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		//Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
		try {


			JSONObject jsonObj = new JSONObject(result);
			JSONArray predsJsonArray = jsonObj.getJSONArray("results");
			jsonObj = new JSONObject(predsJsonArray.getJSONObject(0).toString());
			// Extract the Place descriptions from the results
			String address = jsonObj.getString("formatted_address");
			Toast.makeText(ctx, address, Toast.LENGTH_LONG).show();


			new SendAddress().callservice(ctx,address);
		} catch (Exception e) {

		}
	}



}





