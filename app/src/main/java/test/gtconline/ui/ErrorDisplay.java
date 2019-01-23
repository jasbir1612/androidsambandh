package test.gtconline.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import test.gtconline.R;
import test.gtconline.pojo.request.ErrorMessage;
import test.gtconline.rest.ApiService;
import test.gtconline.rest.ResponseCallback;

public class ErrorDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_display);


                TextView exception_text = (TextView) findViewById(R.id.exception_txt);
                Button btnBack = (Button) findViewById(R.id.btn_back);
           //     exception_text.setText(getIntent().getExtras().getString("error"));

                exception_text.setText("There is some issue.Please try again later.");

                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

               ErrorMessage error= (ErrorMessage) getIntent().getExtras().get("error");
               try {
                   sendErrorReport(error);
               }
               catch(Exception e) {

               }
            }


           public void sendErrorReport(ErrorMessage errorMessage)
           {
               ApiService apiService=new ApiService();
               apiService.uploadErrorMessage(errorMessage, new ResponseCallback<List<ErrorMessage>>() {
                   @Override
                   public void success(List<ErrorMessage> errorMessages) {

                   }

                   @Override
                   public void failure(List<ErrorMessage> errorMessages) {

                   }
               });

           }

            public void intentData() {

                Log.d("CDA", "onBackPressed Called");
                Intent setIntent = new Intent(ErrorDisplay.this, Main2Activity.class);
                setIntent.addCategory(Intent.CATEGORY_HOME);
                setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setIntent);
    }
}
