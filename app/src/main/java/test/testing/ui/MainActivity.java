package test.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import test.testing.R;
import test.testing.pojo.response.BlockDataResponse;
import test.testing.pojo.response.DistrictDataResponse;
import test.testing.pojo.response.SchoolDataResponse;
import test.testing.pojo.response.VillageDataResponse;
import test.testing.rest.ApiService;
import test.testing.rest.ResponseCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatSpinner districtSpinner, villageSpinner, blockSpinner, schoolSpinner;
    private AppCompatEditText udiceEt, dUdiceEt;
    private ApiService apiService;
    private Button submitButton;
    private ProgressBar loadingProgress;

    private ArrayList<DistrictDataResponse> districtDataList = new ArrayList<>();
    private ArrayList<BlockDataResponse> blockDataList = new ArrayList<>();
    private ArrayList<VillageDataResponse> villageDataList = new ArrayList<>();
    private ArrayList<SchoolDataResponse> schoolDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = new ApiService();

        udiceEt = findViewById(R.id.udice_code_et);
        dUdiceEt = findViewById(R.id.dudice_code_et);
        blockSpinner = findViewById(R.id.block_code);
        districtSpinner = findViewById(R.id.district_code);
        villageSpinner = findViewById(R.id.village_code);
        loadingProgress = findViewById(R.id.loading_progress);
        schoolSpinner = findViewById(R.id.school_code);

        submitButton = findViewById(R.id.btn_submit);
        submitButton.setEnabled(false);

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getBlockData(districtDataList.get(position).getDistrictCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        blockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getVillageData(blockDataList.get(position).getBlockCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        villageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long selectedDistrictCode = districtDataList.get(districtSpinner.getSelectedItemPosition()).getDistrictCode();
                long selectedblockCode = blockDataList.get(blockSpinner.getSelectedItemPosition()).getBlockCode();
                long selectedVillageCode = villageDataList.get(position).getVillageCode();

                getSchoolData(selectedDistrictCode, selectedblockCode, selectedVillageCode, "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                submitButton.setEnabled(true);
                udiceEt.setText(doubleConverter(schoolDataList.get(position).getSchoolCode()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        udiceEt.setEnabled(false);
        dUdiceEt.setEnabled(false);
        blockSpinner.setEnabled(false);
        villageSpinner.setEnabled(false);

        submitButton = findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(this);

        getDistrictData();
    }

    @Override
    public void onClick(View v) {
        String finalUdice = udiceEt.getText().toString();
        String finalDudice = dUdiceEt.getText().toString();

        if (TextUtils.isEmpty(finalUdice)) {
            Toast.makeText(this, "Problem in finding udice. Try again later.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("udice_code", finalUdice);
        resultIntent.putExtra("dudice_code", finalDudice);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void getSchoolData(long districtCode, long blockCode, long villageCode, String udiceCode) {
        apiService.getSchoolData(districtCode, blockCode, villageCode, "", new ResponseCallback<List<SchoolDataResponse>>() {
            @Override
            public void success(List<SchoolDataResponse> schoolDataResponses) {
                if (schoolDataResponses != null)
                    schoolDataList = new ArrayList<>(schoolDataResponses);
                else
                    schoolDataList = new ArrayList<>();
                fillSchoolData(schoolDataList);
                showLoader(false);
            }

            @Override
            public void failure(List<SchoolDataResponse> schoolDataResponses) {
                schoolDataList = new ArrayList<>();
                fillSchoolData(schoolDataList);
                showLoader(false);
            }
        });
    }

    private void getDistrictData() {
        showLoader(true);
        apiService.getDistrictData(18, new ResponseCallback<List<DistrictDataResponse>>() {
            @Override
            public void success(List<DistrictDataResponse> districtDataResponses) {
                if (districtDataResponses != null)
                    districtDataList = new ArrayList<>(districtDataResponses);
                else
                    districtDataList = new ArrayList<>();
                fillDistrictData(districtDataList);
                showLoader(false);
            }

            @Override
            public void failure(List<DistrictDataResponse> districtDataResponses) {
                districtDataList = new ArrayList<>();
                fillDistrictData(districtDataList);
                showLoader(false);
            }
        });
    }

    private void getBlockData(long code) {
        showLoader(true);
        apiService.getBlockData(code, new ResponseCallback<List<BlockDataResponse>>() {
            @Override
            public void success(List<BlockDataResponse> blockDataResponse) {
                if (blockDataResponse != null)
                    blockDataList = new ArrayList<>(blockDataResponse);
                else
                    blockDataList = new ArrayList<>();
                fillBlockData(blockDataList);
                showLoader(false);
            }

            @Override
            public void failure(List<BlockDataResponse> blockDataResponse) {
                blockDataList = new ArrayList<>();
                fillBlockData(blockDataList);
                showLoader(false);
            }
        });
    }

    private void getVillageData(long code) {
        showLoader(true);
        apiService.getVillageData(code, new ResponseCallback<List<VillageDataResponse>>() {
            @Override
            public void success(List<VillageDataResponse> villageDataResponses) {
                if (villageDataResponses != null)
                    villageDataList = new ArrayList<>(villageDataResponses);
                else
                    villageDataList = new ArrayList<>();
                filVillageData(villageDataList);
                showLoader(false);
            }

            @Override
            public void failure(List<VillageDataResponse> villageDataResponses) {
                villageDataList = new ArrayList<>(villageDataResponses);
                filVillageData(villageDataList);
                showLoader(false);
            }
        });
    }

    private void fillDistrictData(ArrayList<DistrictDataResponse> data) {
        ArrayList<String> providerlist = new ArrayList<>();
        for (DistrictDataResponse response : data) {
            providerlist.add(response.getDistrictName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, providerlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(adapter);
        blockSpinner.setEnabled(true);
        getBlockData(districtDataList.get(districtSpinner.getSelectedItemPosition()).getDistrictCode());
    }

    private void fillBlockData(ArrayList<BlockDataResponse> data) {
        ArrayList<String> providerlist = new ArrayList<>();
        for (BlockDataResponse response : data) {
            providerlist.add(response.getBlockName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, providerlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blockSpinner.setAdapter(adapter);
        villageSpinner.setEnabled(true);
        getVillageData(blockDataList.get(blockSpinner.getSelectedItemPosition()).getBlockCode());
    }

    private void filVillageData(ArrayList<VillageDataResponse> data) {
        ArrayList<String> providerlist = new ArrayList<>();
        for (VillageDataResponse response : data) {
            providerlist.add(response.getVillageName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, providerlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        villageSpinner.setAdapter(adapter);

        long selectedDistrictCode = districtDataList.get(districtSpinner.getSelectedItemPosition()).getDistrictCode();
        long selectedblockCode = blockDataList.get(blockSpinner.getSelectedItemPosition()).getBlockCode();
        long selectedVillageCode = villageDataList.get(villageSpinner.getSelectedItemPosition()).getVillageCode();
        getSchoolData(selectedDistrictCode, selectedblockCode, selectedVillageCode, "");
    }

    private void fillSchoolData(ArrayList<SchoolDataResponse> data) {
        ArrayList<String> providerlist = new ArrayList<>();
        for (SchoolDataResponse response : data) {
            providerlist.add(response.getSchoolName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, providerlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolSpinner.setAdapter(adapter);

        submitButton.setEnabled(true);
        if (schoolDataList.size() > 0) {
            udiceEt.setText(doubleConverter(schoolDataList.get(schoolSpinner.getSelectedItemPosition()).getSchoolCode()));
            dUdiceEt.setText(schoolDataList.get(schoolSpinner.getSelectedItemPosition()).getDistSchoolCode());
        }

    }

    private void showLoader(boolean value) {
        loadingProgress.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    private String doubleConverter(Double value) {
        String ans = BigDecimal.valueOf(value).toPlainString();
        return ans;
    }
}

