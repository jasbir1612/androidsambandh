package test.testing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import test.testing.ui.Home;
import test.testing.ui.Register;

public class LoginScreen extends AppCompatActivity {

    EditText etNumber, etUDICE;
    Button btnFindUdice, btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        btnRegister = findViewById(R.id.register);
        btnLogin = findViewById(R.id.btnlogin);
//        btnRegister.setEnabled(false);
        etNumber = findViewById(R.id.number);
        if (etNumber.getText().toString().length() == 10) {
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

    }
}
