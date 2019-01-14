package com.mooracle.permissionapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate the call button object from the activity main layout:
        Button callButon = findViewById(R.id.callButton);

        // set on click listeners for the call button and make it call a number:
        callButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });
    }

    // make a separate method to make a call and call this method from the on click listeners
    private void makeCall(){
        // make an intent to action call:
        Intent intent = new Intent(Intent.ACTION_CALL);

        // set the data attribute the tel: and number:
        intent.setData(Uri.parse("tel:" + "12345678"));

        // start the new activity using the intent: (promptly will invoke error due lack of permission)
        //TODO: Fix add permission check
        //startActivity(intent);
    }
}
