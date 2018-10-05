package test.testing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import test.testing.R;
import test.testing.pojo.request.RegisterBody;
import test.testing.pojo.response.RegisterResponse;
import test.testing.rest.ApiService;
import test.testing.rest.ResponseCallback;

public class RegisterActivity extends AppCompatActivity {

    public static final int SELECT_UDICE_REQUEST_CODE = 1008;
    private ApiService apiService;

    private Button btnFindUdice;
    private Button submit;
    private EditText mobileEt;
    private EditText udiceEt;
    private EditText nameEt;
    private EditText emailEt;
    private EditText designationEt;
    private EditText schoolTypeEt;
    private EditText userTypeEt;
    private EditText jsonDataEt;
    private EditText createdByEt;
    private EditText dudiceEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiService = new ApiService();

        btnFindUdice = findViewById(R.id.find_udice);
        submit = findViewById(R.id.rsubmit);

        mobileEt = findViewById(R.id.et_number);
        udiceEt = findViewById(R.id.et_udice);
        nameEt = findViewById(R.id.et_name);
        emailEt = findViewById(R.id.et_email);
        designationEt = findViewById(R.id.et_designation);
        schoolTypeEt = findViewById(R.id.et_school_type);
        userTypeEt = findViewById(R.id.et_user_type);
        jsonDataEt = findViewById(R.id.et_json_data);
        createdByEt = findViewById(R.id.et_created_by);
        dudiceEt = findViewById(R.id.et_dudice_code);

        btnFindUdice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectUdiceIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivityForResult(selectUdiceIntent, SELECT_UDICE_REQUEST_CODE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_UDICE_REQUEST_CODE) {
                if (data != null) {
                    String udiceCode = data.getStringExtra("udice_code");
                    udiceEt.setText(udiceCode);
                }
            }
        }
    }

    private void register() {
        String mobile = mobileEt.getText().toString().trim();
        String schoolType = schoolTypeEt.getText().toString().trim();
        String userType = userTypeEt.getText().toString().trim();
        String userName = nameEt.getText().toString().trim();
        String designation = designationEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String jsonData = jsonDataEt.getText().toString().trim();
        String createdBy = createdByEt.getText().toString().trim();
        String udiceCode = udiceEt.getText().toString().trim();
        String dudiceCode = dudiceEt.getText().toString().trim();

        RegisterBody jsonBody = new RegisterBody(mobile, schoolType, userType, userName, designation, email, jsonData, createdBy, udiceCode, dudiceCode);
        apiService.register(jsonBody, new ResponseCallback<List<RegisterResponse>>() {
            @Override
            public void success(List<RegisterResponse> registerResponses) {
                Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void failure(List<RegisterResponse> registerResponses) {
                Toast.makeText(RegisterActivity.this, "Problem while registering. Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
