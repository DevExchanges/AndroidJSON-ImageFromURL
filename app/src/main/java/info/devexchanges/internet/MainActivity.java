package info.devexchanges.internet;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private View btnGetJSON;
    private View btnGetImage;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetImage = findViewById(R.id.btn_img);
        btnGetJSON = findViewById(R.id.btn_json);
        imageView = (ImageView) findViewById(R.id.image);

        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetImageTask(MainActivity.this).execute();
            }
        });

        btnGetJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetJSONTask(MainActivity.this).execute();
            }
        });
    }

    public void getBackBitmap(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    public void parsingJSONResponse(String jsonString) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_weather);
        dialog.setTitle(getString(R.string.notice));
        dialog.setCancelable(true);

        TextView temperature = (TextView) dialog.findViewById(R.id.temperature);
        TextView humidity = (TextView) dialog.findViewById(R.id.humidity);
        TextView pressure = (TextView) dialog.findViewById(R.id.pressure);
        TextView location = (TextView) dialog.findViewById(R.id.location);
        TextView city = (TextView) dialog.findViewById(R.id.city);

        // parsing json and set the custom dialog components - text, image and button
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            //get location
            JSONObject locationObject = jsonObject.getJSONObject("coord");
            location.setText("[ " + locationObject.getString("lon") + ", " + locationObject.getString("lat") + " ]");

            //get temperature, humidity and pressure
            JSONObject tempObject = jsonObject.getJSONObject("main");
            temperature.setText(tempObject.getString("temp") + " Kelvin");
            humidity.setText(tempObject.getString("humidity") + "%");
            pressure.setText(tempObject.getString("pressure") + "Pa");

            //get city name
            city.setText(jsonObject.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //showing dialog
        dialog.show();
    }
}
