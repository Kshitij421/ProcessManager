package com.example.kbw.processmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.content.Intent;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
    public void startService(View view) {
        startService(new Intent(getBaseContext(), MyService.class));
    }


    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }

    public void PrintFile(View view){
        StringBuilder text = new StringBuilder();

        try {
            File root = new File(Environment.getExternalStorageDirectory(), "DCIM");
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, "log.txt");
            Toast.makeText(getApplicationContext(), file.toString(), Toast.LENGTH_SHORT).show();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();

        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        TextView text1=(TextView)findViewById(R.id.text);
        text1.setText(text.toString());
    }
}
