package test.testing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import test.testing.pojo.request.SendSmsBody;
import test.testing.pojo.response.SmsDataResponse;
import test.testing.rest.ApiService;
import test.testing.rest.ResponseCallback;
import test.testing.ui.DetailActivity;
import test.testing.ui.Home;
import test.testing.ui.Register;

public class LoginScreen extends AppCompatActivity {

    EditText etNumber, etUDICE;
    Button btnFindUdice, btnRegister, btnLogin;
    ApiService apiService;
    private Button btnOtp;
    private SendSmsBody sendSmsBody;
    String etNumberField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        btnRegister = findViewById(R.id.register);
        btnLogin = findViewById(R.id.btnlogin);
        btnOtp= findViewById(R.id.btnGenerateOTP);
//        btnRegister.setEnabled(false);
        etNumber = findViewById(R.id.number);
        etNumberField= etNumber.getText().toString();

        if (etNumber.getText().toString().length()== 10) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            btnRegister.setEnabled(true);
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginScreen.this, Register.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginScreen.this, Home.class);
                startActivity(i);
            }
        });

        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("OnClick of ", " Generate OTP button");
                sendSmsBody = new SendSmsBody("abcd", "9818140752", " Your OTP verification code is , \n\n" + generateRandomNumber(), "1");



                apiService.getSmsData(sendSmsBody, new ResponseCallback<List<SmsDataResponse>>() {
                    @Override
                    public void success(List<SmsDataResponse> smsDataResponses) {
                        //submitButton.setEnabled(true);
                        //  progressBar.setVisibility(View.GONE);

                        Log.d("goesin", "success: "+smsDataResponses);
                        if (smsDataResponses != null) {
                            Intent intent = new Intent(LoginScreen.this, DetailActivity.class);
                            intent.putParcelableArrayListExtra("list", new ArrayList<Parcelable>(smsDataResponses));
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginScreen.this, "null response", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(List<SmsDataResponse> smsDataResponses) {

                        Log.d("goesin","failure"+smsDataResponses);
                        // submitButton.setEnabled(true);
                        //progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginScreen.this, "Error response", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        apiService = new ApiService();
    }


    int range = 9;  // to generate a single number with this range, by default its 0..9
    int length = 4; // by default length is 4


    public int generateRandomNumber() {
        int randomNumber;

        SecureRandom secureRandom = new SecureRandom();
        String s = "";
        for (int i = 0; i < length; i++) {
            int number = secureRandom.nextInt(range);
            if (number == 0 && i == 0) { // to prevent the Zero to be the first number as then it will reduce the length of generated pin to three or even more if the second or third number came as zeros
                i = -1;
                continue;
            }
            s = s + number;
        }

        randomNumber = Integer.parseInt(s);

        return randomNumber;
    }
}
