package test.testing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import test.testing.R;
import test.testing.pojo.request.SchoolDataBody;
import test.testing.pojo.response.SchoolDataResponse;
import test.testing.rest.ApiService;
import test.testing.rest.ResponseCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUCID, etDistrict, etVillage, etblock;
    private ApiService apiService;
    private SchoolDataBody schoolDataBody;
    private Button submitButton;
    private ProgressBar progressBar;
    AutoCompleteTextView suggestion_box;
    Spinner items;
    ArrayList list = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        apiService = new ApiService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUCID = findViewById(R.id.udice_code);
        etblock = findViewById(R.id.block_code);
        etDistrict = findViewById(R.id.district_code);
        etVillage = findViewById(R.id.village_code);
        submitButton = findViewById(R.id.btn_submit);
        progressBar = findViewById(R.id.progress_bar);

        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        submitButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        schoolDataBody = new SchoolDataBody();
        schoolDataBody.setuDiceCode(etUCID.getText().toString().trim());
        schoolDataBody.setDistrictCode(etDistrict.getText().toString().trim());
        schoolDataBody.setVillageCode(etVillage.getText().toString().trim());
        schoolDataBody.setBlockCode(etblock.getText().toString().trim());

        apiService.getSchoolData(schoolDataBody, new ResponseCallback<List<SchoolDataResponse>>() {
            @Override
            public void success(List<SchoolDataResponse> schoolDataResponses) {
                submitButton.setEnabled(true);
                progressBar.setVisibility(View.GONE);

                if (schoolDataResponses != null) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putParcelableArrayListExtra("list", new ArrayList<Parcelable>(schoolDataResponses));
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "null response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(List<SchoolDataResponse> schoolDataResponses) {
                submitButton.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error response", Toast.LENGTH_SHORT).show();
            }
        });

        if(v.getId() == R.id.btn_submit)
        {
            Intent i = new Intent(MainActivity.this, Register.class);
            startActivity(i);
        }
    }
}

