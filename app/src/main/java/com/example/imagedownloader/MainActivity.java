package com.example.imagedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button btnDownload;
    EditText urLText;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urLText = (EditText) findViewById(R.id.URL);
        btnDownload = (Button)findViewById(R.id.Download);
        imageView = (ImageView) findViewById(R.id.image);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadTask task = new DownloadTask();
                task.execute(urLText.getText().toString());

            }
        });
    }
    /**
     * Download function that opens HTTP Connection to download the image in the url
     * */
    Bitmap download(String url){
        Bitmap result= null;
        URL urlObj = null;
        HttpURLConnection httpURLConnection;
        InputStream inputStream=null;
        try {
            urlObj = new URL(url);
            httpURLConnection = (HttpURLConnection) urlObj.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            result = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }

    class DownloadTask extends AsyncTask<String,Void, Bitmap>{
        /**
         * To do lengthy task that might cause  ANR */
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap result = null;
            result = download(strings[0]);
            return result;
        }

        /**
         * To edit my activity view once the results of the task arrive
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            Toast.makeText(MainActivity.this, "Downloaded Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}