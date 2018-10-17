package test.testing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import test.testing.R;
import test.testing.rest.Database;

public class HomeActivity extends AppCompatActivity {

    Button btnUpload, btnDownload;
    String email = "jasbir1612@gmail.com";
    String password = "geniejasbir";
    String TAG = "Home";
    private FirebaseAuth mAuth;
    private Button logoutbtn;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new Database(getApplicationContext());

        btnUpload = findViewById(R.id.upload);
        btnDownload = findViewById(R.id.download);
        logoutbtn = findViewById(R.id.logout_btn);
        mAuth = FirebaseAuth.getInstance();

//        Toast.makeText(this, "email" +mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, UploadActivity.class);
                startActivity(i);
                loginUser(email, password);
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, DownloadActivity.class);
                startActivity(i);
                loginUser(email, password);
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.clear();
                Intent intent = new Intent(HomeActivity.this, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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
                            Toast.makeText(HomeActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                        }

                        if (!task.isSuccessful()) {
//                            txtStatus.setText("Authentication failed!");
                        }
                    }
                });
    }

}
