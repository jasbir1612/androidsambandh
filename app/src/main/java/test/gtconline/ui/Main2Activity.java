package test.gtconline.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import test.gtconline.BuildConfig;
import test.gtconline.ErrorHandler.AppCrashHandler;
import test.gtconline.R;
import test.gtconline.pojo.response.AppVersionResponse;
import test.gtconline.rest.ApiService;
import test.gtconline.rest.ResponseCallback;

public class Main2Activity extends AppCompatActivity {

    Button bDownload,bUpload;
    String email = "satnam.sethi@siinnovative.in";
    String password = "Ishu@3206";
    String TAG = "Home";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(getApplicationContext()));

        bDownload=findViewById(R.id.download2);
        bUpload=findViewById(R.id.upload2);

        mAuth = FirebaseAuth.getInstance();

        bDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkConnectivity())
                {
                    Toast.makeText(Main2Activity.this, "Check your network connection", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginUser(email,password);
                    Intent intent = new Intent(Main2Activity.this, DownloadActivty.class);
                    startActivity(intent);
                }
            }
        });


        bUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!checkConnectivity())
                {
                    Toast.makeText(Main2Activity.this, "Check your network connection", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(Main2Activity.this, LoginActivty.class);
                    startActivity(intent);
                    loginUser(email,password);
                }
            }
        });








    }

    @Override
    protected void onResume() {
        super.onResume();

        ApiService apiService=new ApiService();
        apiService.getLatestAppVersion(new ResponseCallback<AppVersionResponse>() {
            @Override
            public void success(AppVersionResponse appVersionResponse) {
                if(Integer.parseInt(appVersionResponse.getStableVersionCode())>BuildConfig.VERSION_CODE)
                {
                    AlertDialog.Builder alertdialog=new AlertDialog.Builder(Main2Activity.this);
                    alertdialog.setTitle("Update");
                    alertdialog.setMessage("Please update to latest version of app to continue.");
                    alertdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    });

                    alertdialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    });

                    alertdialog.setCancelable(false);
                    alertdialog.show();
                    Toast.makeText(Main2Activity.this,"Please update to latest version",Toast.LENGTH_SHORT).show();


                }
                else if(Integer.parseInt(appVersionResponse.getLatestVersionCode())>BuildConfig.VERSION_CODE){
                    Toast.makeText(Main2Activity.this,"New version available",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void failure(AppVersionResponse appVersionResponse) {

            }
        });


    }

    public void loginUser(String email, String pass) {

        Log.e(TAG, "email" + email);
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "signIn: Success!");

                            // update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
//                                updateUI(user);
                        } else {
                            Log.e(TAG, "signIn: Fail!", task.getException());
                            Toast.makeText(Main2Activity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                        }

                        if (!task.isSuccessful()) {
//                            txtStatus.setText("Authentication failed!");
                        }
                    }
                });
    }

    public boolean checkConnectivity()
    {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();


    }

}
