package test.testing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import test.testing.R;
import test.testing.pojo.response.SendSmsResponse;
import test.testing.rest.ApiService;
import test.testing.rest.ResponseCallback;

public class LoginActivity extends AppCompatActivity {

    private EditText etPhone, etOtp;
    private Button btnRegister, btnLogin, btnOtp;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        apiService = new ApiService();

        btnRegister = findViewById(R.id.register_btn);
        btnLogin = findViewById(R.id.login_btn);
        btnOtp = findViewById(R.id.generate_otp_btn);
        etPhone = findViewById(R.id.et_number);
        etOtp = findViewById(R.id.et_otp);

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
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = etPhone.getText().toString().trim();

                if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 7) {
                    Toast.makeText(LoginActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
