package test.testing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import test.testing.R;

public class Register extends AppCompatActivity {

    public static final int SELECT_UDICE_REQUEST_CODE = 1008;

    private Button btnFindUdice;
    private Button submit;
    private EditText mobileEt;
    private EditText udiceEt;
    private EditText nameEt;
    private EditText emailEt;
    private EditText designationEt;
    private EditText schoolTypeEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnFindUdice = findViewById(R.id.find_udice);
        submit = findViewById(R.id.rsubmit);

        mobileEt = findViewById(R.id.et_number);
        udiceEt = findViewById(R.id.et_udice);
        nameEt = findViewById(R.id.et_name);
        emailEt = findViewById(R.id.et_email);

        designationEt = findViewById(R.id.et_designation);
        schoolTypeEt = findViewById(R.id.et_school_type);

        btnFindUdice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectUdiceIntent = new Intent(Register.this, MainActivity.class);
                startActivityForResult(selectUdiceIntent, SELECT_UDICE_REQUEST_CODE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Home.class);
                startActivity(intent);
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


//    {
//        "MobileNo": "9711911482",
//            "SchoolType" : "High",
//            "UserType" : "Individual",
//            "UserName" : "Gunwant",
//            "Designation" : "Professor",
//            "EmailID" : "mail@test.com",
//            "JSONData": "",
//            "CreatedBy": "9999998996",
//            "UdiceCode" :"18301307801",
//            "DUdiceCode":"DI18150500803"
//    }
}
