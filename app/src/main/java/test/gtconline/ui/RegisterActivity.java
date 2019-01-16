package test.gtconline.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

import test.gtconline.ErrorHandler.AppCrashHandler;
import test.gtconline.R;
import test.gtconline.pojo.request.RegisterBody;
import test.gtconline.pojo.response.RegisterResponse;
import test.gtconline.rest.ApiService;
import test.gtconline.rest.Database;
import test.gtconline.rest.ResponseCallback;

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

    private EditText activityDateEt;
    DatePickerDialog dialog;
    String dateST;
    int year, month, day;
    long back2 = 1000 * 30 * 60 * 60;
    long back = back2 * 24;
    private DatePickerDialog.OnDateSetListener date;

    private EditText pledgesEt;
    String pledge;

    private EditText schoolNameEt;

    private AppCompatSpinner designationSpinner, userTypeSpinner, schoolTypeSpinner;

    String[] designationArray, schoolTypeArray, userTypeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(getApplicationContext()));

        apiService = new ApiService();
        db = new Database(getApplicationContext());

        btnFindUdice = findViewById(R.id.find_udice);
        submit = findViewById(R.id.rsubmit);
        etDesignation = findViewById(R.id.et_designation);
        schoolNameEt = findViewById(R.id.et_schoolName);

        schoolNameEt.setEnabled(false);

        activityDateEt = findViewById(R.id.update);
        activityDateEt.setFocusable(false);

        pledgesEt=findViewById(R.id.upnumber);


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

        activityDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - back);
                dialog.show();
            }
        });
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int dyear, int dmonth, int ddayOfMonth) {
                dmonth = dmonth + 1;
                dateST = ddayOfMonth + "/" + dmonth + "/" + dyear;
                Log.d("date", dateST);
                if (ddayOfMonth < 10) {
                    dateST = "0" + ddayOfMonth + "/" + dmonth + "/" + dyear;
                }
                Log.d("testing", dateST);
                if (dmonth < 10)

                {
                    dateST = ddayOfMonth + "/" + "0" + dmonth + "/" + dyear;
                    if (ddayOfMonth < 10) {
                        dateST = "0" + ddayOfMonth + "/" + "0" + dmonth + "/" + dyear;
                    }
                }
                Log.d("testing", dateST);
                Toast.makeText(RegisterActivity.this, dateST, Toast.LENGTH_SHORT).show();
                activityDateEt.setText(dateST);
            }
        };

        dialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_DeviceDefault, date, year, month, day);

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
                    schoolNameEt.setText(data.getStringExtra("schoolName"));
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
        pledge = pledgesEt.getText().toString().trim();

        if (TextUtils.isEmpty(pledge))
        {  //if(Integer.parseInt(pledge) <= 0) {
            Toast.makeText(RegisterActivity.this, "Please enter no of students.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(dateST) ) {
            Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
        } else {
            RegisterBody jsonBody = new RegisterBody(mobile, schoolType, userType, userName, designation, email, jsonData, createdBy, udiceCode, dudiceCode);
            apiService.register(jsonBody, new ResponseCallback<List<RegisterResponse>>() {
                @Override
                public void success(List<RegisterResponse> registerResponses) {
//                Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
//                db.setMobile(registerResponses.get(0).getMobileNo());
//                db.setAppId(registerResponses.get(0).getAppID());
//                Intent intent = new Intent(RegisterAtivity.this, HomeActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();


                    Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    // db.clear();
                    Intent intent = new Intent(RegisterActivity.this, UploadActivity.class);
                    //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("date", dateST);

                    intent.putExtra("pledge",pledge);
                    startActivity(intent);
                    //    finish();
                }

                @Override
                public void failure(List<RegisterResponse> registerResponses) {
                    Toast.makeText(RegisterActivity.this, "Please fill all compulsory(*) boxes or try again later.", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
