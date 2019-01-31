package test.gtconline.ui;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import test.gtconline.ErrorHandler.AppCrashHandler;
import test.gtconline.R;
import test.gtconline.pojo.response.SendOtpResponse;
import test.gtconline.pojo.response.VerifyOtpResponse;
import test.gtconline.rest.ApiService;
import test.gtconline.rest.Database;
import test.gtconline.rest.ResponseCallback;

public class LoginActivty extends AppCompatActivity {



    Button register_btn,generateOtp_btn;
    EditText mobileEt,otpEt;
    TextView otpWarning;

    EditText selectStateUpload;

    String phoneNumber;

    Database db;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_activty2);

        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(getApplicationContext()));


        register_btn=findViewById(R.id.register_btn2);
        generateOtp_btn=findViewById(R.id.generate_otp_btn2);
        otpEt=findViewById(R.id.et_otp2);
        otpWarning=findViewById(R.id.warning);
        mobileEt=findViewById(R.id.et_number2);

        selectStateUpload=findViewById(R.id.selectStateUploadEt);
        selectStateUpload.setEnabled(false);

        register_btn.setEnabled(false);

        db = new Database(getApplicationContext());

  //   startActivity(new Intent(LoginActivty.this,RegisterActivity.class));

        apiService=new ApiService();

        generateOtp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = mobileEt.getText().toString().trim();

                if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 10) {
                    Toast.makeText(LoginActivty.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                //    btnRegister.setVisibility(View.VISIBLE);
                //    btnLogin.setVisibility(View.VISIBLE);

               //     Log.d("appuid",db.getAppUid().toString());

                    //generate OTP function
                }

                generateOtp_btn.setEnabled(false);
                generateOtp_btn.setBackgroundColor(ContextCompat.getColor(LoginActivty.this, R.color.gray_btn_color));
                otpWarning.setVisibility(View.VISIBLE);

                apiService.sendOtp(phoneNumber, new ResponseCallback<List<SendOtpResponse>>() {
                    @Override
                    public void success(List<SendOtpResponse> sendOtpResponses) {
                        if (sendOtpResponses.get(0).getAppUID() != null) {
                            SendOtpResponse sendOtpResponse = sendOtpResponses.get(0);
                            db.setAppUid(sendOtpResponse.getAppUID());
                            db.setMobile(sendOtpResponse.getMobile());
                            otpEt.requestFocus();
                        }

                        generateOtp_btn.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                generateOtp_btn.setEnabled(true);
                                generateOtp_btn.setBackgroundColor(ContextCompat.getColor(LoginActivty.this, R.color.login_btn_color));

                                otpWarning.setVisibility(View.GONE);

                            }
                        }, 20*60*1000);

                      //  btnLogin.setEnabled(true);
                      //  btnRegister.setEnabled(true);

                        register_btn.setBackgroundColor(ContextCompat.getColor(LoginActivty.this, R.color.login_btn_color));
                     //   btnRegister.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.register_btn_color));

                        register_btn.setEnabled(true);
                        Toast.makeText(LoginActivty.this, "Otp sent successfully to " + phoneNumber, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(List<SendOtpResponse> sendOtpResponses) {
                        generateOtp_btn.setEnabled(true);
                        generateOtp_btn.setBackgroundColor(ContextCompat.getColor(LoginActivty.this, R.color.login_btn_color));
                        Toast.makeText(LoginActivty.this, "Problem in sending OTP. Try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiService.verifyOtp(phoneNumber, otpEt.getText().toString().trim(), db.getAppUid(), new ResponseCallback<List<VerifyOtpResponse>>() {
                    @Override
                    public void success(List<VerifyOtpResponse> verifyOtpResponses) {
                        Toast.makeText(LoginActivty.this, "Otp verified.", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivty.this, RegisterActivity.class));
                    }

                    @Override
                    public void failure(List<VerifyOtpResponse> verifyOtpResponses) {
                        Toast.makeText(LoginActivty.this, "OTP Incorrect or expired. Please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }



}
