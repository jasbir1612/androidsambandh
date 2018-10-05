package test.testing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import test.testing.R;
import test.testing.pojo.response.SendOtpResponse;
import test.testing.pojo.response.VerifyOtpResponse;
import test.testing.rest.ApiService;
import test.testing.rest.Database;
import test.testing.rest.ResponseCallback;

public class LoginActivity extends AppCompatActivity {

    private EditText etPhone, etOtp;
    private Button btnRegister, btnLogin, btnOtp;
    private ApiService apiService;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        apiService = new ApiService();
        db = new Database(getApplicationContext());

        btnRegister = findViewById(R.id.register_btn);
        btnLogin = findViewById(R.id.login_btn);
        btnOtp = findViewById(R.id.generate_otp_btn);
        etPhone = findViewById(R.id.et_number);
        etOtp = findViewById(R.id.et_otp);

        btnLogin.setEnabled(false);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = etPhone.getText().toString().trim();
                String otp = etOtp.getText().toString().trim();
                String appUid = db.getAppUid();

                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(LoginActivity.this, "Enter mobile number first", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(otp)) {
                    Toast.makeText(LoginActivity.this, "Enter mobile number first", Toast.LENGTH_SHORT).show();
                    return;
                }

                apiService.verifyOtp(mobile, otp, appUid, new ResponseCallback<List<VerifyOtpResponse>>() {
                    @Override
                    public void success(List<VerifyOtpResponse> verifyOtpResponses) {
                        Toast.makeText(LoginActivity.this, "Otp verified.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }

                    @Override
                    public void failure(List<VerifyOtpResponse> verifyOtpResponses) {
                        Toast.makeText(LoginActivity.this, "There was some problem right now. Try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneNumber = etPhone.getText().toString().trim();

                if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 7) {
                    Toast.makeText(LoginActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    btnRegister.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    //generate OTP function
                }

                apiService.sendOtp(phoneNumber, new ResponseCallback<List<SendOtpResponse>>() {
                    @Override
                    public void success(List<SendOtpResponse> sendOtpResponses) {
                        if (sendOtpResponses.get(0).getAppUID() != null) {
                            db.setAppUid(sendOtpResponses.get(0).getAppUID());
                            db.setMobile(sendOtpResponses.get(0).getMobile());
                        }
                        btnLogin.setEnabled(true);
                        Toast.makeText(LoginActivity.this, "Otp sent successfully to " + phoneNumber, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(List<SendOtpResponse> sendOtpResponses) {
                        Toast.makeText(LoginActivity.this, "Problem in sending OTP. Try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
