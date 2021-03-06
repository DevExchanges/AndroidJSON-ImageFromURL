package info.devexchanges.internet;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetJSONTask extends AsyncTask<Void, Void, String> {

    private HttpURLConnection urlConnection;
    private final String JSON_URL = "http://api.openweathermap.org/data/2.5/weather?q=hanoi,vn&appid=2de143494c0b295cca9337e1e96b00e0";
    private MainActivity activity;
    private ProgressDialog progressDialog;

    public GetJSONTask(MainActivity activity) {
        this.activity = activity;
        progressDialog = ProgressDialog.show(activity, "Connecting...", "Downloading JSON...", true);
    }
    @Override
    protected String doInBackground(Void... params) {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(JSON_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.dismiss(); //dismiss dialog
        activity.parsingJSONResponse(result); //call back data to UI thread
    }
}
