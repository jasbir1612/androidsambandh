package test.gtconline.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import test.gtconline.ErrorHandler.AppCrashHandler;
import test.gtconline.R;

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



    }
}
