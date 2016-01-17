package info.devexchanges.internet;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetImageTask extends AsyncTask<Void, Void, Bitmap> {

    private final String IMAGE_URL = "http://i.imgur.com/ur4Ye8R.jpg";
    private MainActivity activity;
    private ProgressDialog progressDialog;
    private HttpURLConnection connection;

    public GetImageTask(MainActivity activity) {
        this.activity = activity;
        progressDialog = ProgressDialog.show(activity, "Connecting...", "Downloading Image...", true);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            //get Image from URL by URLConnection
            URL url = new URL(IMAGE_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            //InputStream and decode to Bitmap
            InputStream input = connection.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(input);

            return bmp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            connection.disconnect();
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        progressDialog.dismiss(); //dismiss progress bar when task finished!
        if (bitmap == null) {
            Toast.makeText(activity, "Wrong Link!", Toast.LENGTH_SHORT).show();
        } else {
            //call back data to UI thread
            activity.getBackBitmap(bitmap);
        }
    }
}
