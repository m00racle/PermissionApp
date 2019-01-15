package com.mooracle.permissionapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 11;

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
    private void makeCall() {
        // make an intent to action call:
        Intent intent = new Intent(Intent.ACTION_CALL);

        // set the data attribute the tel: and number:
        intent.setData(Uri.parse("tel:" + "12345678"));

        // start the new activity using the intent: (promptly will invoke error due lack of permission)
        // Fix add permission check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //  Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSIONS_REQUEST_CODE);
        }else {startActivity(intent);}

    }

    // override method to handle the request permission results from makeCall method permission check:
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // check if the requestCode is not null and same as PERMISSION_REQUEST_CODE
        if (requestCode == PERMISSIONS_REQUEST_CODE){
            if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makeCall();
            }
        }
    }
}
