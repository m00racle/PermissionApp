package com.mooracle.permissionapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                // shift the permission check into this method for more memory efficiency.
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                    //if the android version is lower than Marshmallow:
                    makeCall();
                }else {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        // set the message
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                Manifest.permission.CALL_PHONE)){
                            new AlertDialog.Builder(MainActivity.this).setTitle("Call Permission Needed")
                                    .setMessage("Permission to Call is needed for the app to work, Will you grant one?")
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ActivityCompat.requestPermissions(MainActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE},
                                                    PERMISSIONS_REQUEST_CODE);
                                        }
                                    })
                                    .setNegativeButton("Nope, Sorry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(MainActivity.this,
                                                    "Sorry, the app cannot operate with no call permission :(",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }).show();
                        }else{
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_CODE);
                        }
                    }else{makeCall();}
                }
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
         startActivity(intent);

    }

    // override method to handle the request permission results from makeCall method permission check:
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // check if the requestCode is not null and same as PERMISSION_REQUEST_CODE
        if (requestCode == PERMISSIONS_REQUEST_CODE){
            if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED){ //permission granted
                makeCall();
            }else { //permission denied
                Toast.makeText(MainActivity.this,
                        "Sorry, the app cannot operate with no call permission :(",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
