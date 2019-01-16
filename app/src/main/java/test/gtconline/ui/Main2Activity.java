package test.gtconline.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import test.gtconline.BuildConfig;
import test.gtconline.ErrorHandler.AppCrashHandler;
import test.gtconline.R;
import test.gtconline.pojo.response.AppVersionResponse;
import test.gtconline.rest.ApiService;
import test.gtconline.rest.ResponseCallback;

public class Main2Activity extends AppCompatActivity {

    Button bDownload,bUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(getApplicationContext()));

        bDownload=findViewById(R.id.download2);
        bUpload=findViewById(R.id.upload2);

        bDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this,DownloadActivty2.class);
                startActivity(intent);
            }
        });


        bUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this,UploadActivty2.class);
                startActivity(intent);
            }
        });


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

     /*   ApiService apiService=new ApiService();
        apiService.getLatestAppVersion(new ResponseCallback<AppVersionResponse>() {
            @Override
            public void success(AppVersionResponse appVersionResponse) {
                if(Integer.parseInt(appVersionResponse.getStableVersionCode())>BuildConfig.VERSION_CODE)
                {
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
*/

    }
}
