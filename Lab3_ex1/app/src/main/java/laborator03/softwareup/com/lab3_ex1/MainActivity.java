package laborator03.softwareup.com.lab3_ex1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // edit Text - where the page is added
        final EditText editText = (EditText) findViewById(R.id.editText);

        // text View - where the page is shown
        final TextView textView = (TextView) findViewById(R.id.textView);

        // define button
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>(){

                    @Override
                    protected String doInBackground(String... params) {

                        // open the connection
                        URL url = null;
                        try {
                            url = new URL(params[0]);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        //Log.d("AAA", params[0]);
                        HttpURLConnection connection = null;
                        try {
                            if (url != null)
                                connection = (HttpURLConnection) url.openConnection();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // ---------------------------
                        InputStream is = null;
                        try {
                            if (connection != null)
                                is = connection.getInputStream();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        try {
                            if (is != null)
                                while ((length = is.read(buffer)) != -1) {
                                    result.write(buffer, 0, length);
                                }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            return result.toString("UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        // if not ok, return null string
                        return "ERROR";
                    }

                    // executed in the main thd (UI thread)
                    @Override
                    protected void onPostExecute(String content) {
                        textView.setText(content);
                    }
                };

                task.execute(editText.getText().toString());
            }
        });
    }
}
