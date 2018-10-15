package test.testing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import test.testing.R;
import test.testing.pojo.request.RegisterBody;
import test.testing.pojo.response.RegisterResponse;
import test.testing.rest.ApiService;
import test.testing.rest.Database;
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
    private EditText jsonDataEt;
    private EditText createdByEt;
    private EditText dudiceEt, etDesignation;
    private Database db;

    private AppCompatSpinner designationSpinner, userTypeSpinner, schoolTypeSpinner;

    String[] designationArray, schoolTypeArray, userTypeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiService = new ApiService();
        db = new Database(getApplicationContext());

        btnFindUdice = findViewById(R.id.find_udice);
        submit = findViewById(R.id.rsubmit);
        etDesignation = findViewById(R.id.et_designation);

        mobileEt = findViewById(R.id.et_number);
        mobileEt.setEnabled(false);
        mobileEt.setText(db.getMobile());

        udiceEt = findViewById(R.id.et_udice);
        nameEt = findViewById(R.id.et_name);
        emailEt = findViewById(R.id.et_email);
        jsonDataEt = findViewById(R.id.et_json_data);

        designationSpinner = findViewById(R.id.designation_spinner);
        designationSpinner.setVisibility(View.GONE);
        schoolTypeSpinner = findViewById(R.id.school_type_spinner);
        userTypeSpinner = findViewById(R.id.user_type_spinner);

        createdByEt = findViewById(R.id.et_created_by);
        createdByEt.setText(db.getMobile());

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

        designationArray = getResources().getStringArray(R.array.array_designation);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_register, designationArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        designationSpinner.setAdapter(adapter);


        userTypeArray = getResources().getStringArray(R.array.array_user_type);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item_register, userTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter2);

        schoolTypeArray = getResources().getStringArray(R.array.array_school_type);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.spinner_item_register, schoolTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolTypeSpinner.setAdapter(adapter3);
        userTypeSpinner.setVisibility(View.GONE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_UDICE_REQUEST_CODE) {
                if (data != null) {
                    String udiceCode = data.getStringExtra("udice_code");
                    String dudiceCode = data.getStringExtra("dudice_code");
                    udiceEt.setText(udiceCode);
                    dudiceEt.setText(dudiceCode);
                }
            }
        }
    }

    private void register() {
        String mobile = mobileEt.getText().toString().trim();
        String schoolType = schoolTypeArray[schoolTypeSpinner.getSelectedItemPosition()];
        String userType = userTypeArray[userTypeSpinner.getSelectedItemPosition()];
        String designation = etDesignation.getText().toString().trim();
        String userName = nameEt.getText().toString().trim();
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
                db.setMobile(registerResponses.get(0).getMobileNo());
                db.setAppId(registerResponses.get(0).getAppID());
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void failure(List<RegisterResponse> registerResponses) {
                Toast.makeText(RegisterActivity.this, "Problem while registering. Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
