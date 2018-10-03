package test.testing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import test.testing.R;
import test.testing.pojo.request.SchoolDataBody;
import test.testing.pojo.response.BlockDataResponse;
import test.testing.pojo.response.DistrictDataResponse;
import test.testing.pojo.response.SchoolDataResponse;
import test.testing.pojo.response.VillageDataResponse;
import test.testing.rest.ApiService;
import test.testing.rest.ResponseCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatSpinner districtSpinner, villageSpinner, blockSpinner;
    private AppCompatEditText udiceEt;
    private ApiService apiService;
    private SchoolDataBody schoolDataBody;
    private Button submitButton;
    private ProgressBar progressBar;
    private ProgressBar loadingProgress;

    private ArrayList<DistrictDataResponse> districtDataList = new ArrayList<>();
    private ArrayList<BlockDataResponse> blockDataList = new ArrayList<>();
    private ArrayList<VillageDataResponse> villageDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = new ApiService();

        udiceEt = findViewById(R.id.udice_code);
        blockSpinner = findViewById(R.id.block_code);
        districtSpinner = findViewById(R.id.district_code);
        villageSpinner = findViewById(R.id.village_code);
        loadingProgress = findViewById(R.id.loading_progress);

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        udiceEt.setEnabled(false);
        blockSpinner.setEnabled(false);
        villageSpinner.setEnabled(false);

        submitButton = findViewById(R.id.btn_submit);
        progressBar = findViewById(R.id.progress_bar);

        submitButton.setOnClickListener(this);

        getDistrictData();
    }

    @Override
    public void onClick(View v) {

        submitButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        schoolDataBody = new SchoolDataBody();
//        schoolDataBody.setuDiceCode(etUCID.getText().toString().trim());
//        schoolDataBody.setDistrictCode(etDistrict.getText().toString().trim());
//        schoolDataBody.setVillageCode(etVillage.getText().toString().trim());
//        schoolDataBody.setBlockCode(etblock.getText().toString().trim());

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

        if (v.getId() == R.id.btn_submit) {
            Intent i = new Intent(MainActivity.this, Register.class);
            startActivity(i);
        }
    }

    private void getDistrictData() {
        showLoader(true);
        apiService.getDistrictData(18, new ResponseCallback<List<DistrictDataResponse>>() {
            @Override
            public void success(List<DistrictDataResponse> districtDataResponses) {
                districtDataList = new ArrayList<>(districtDataResponses);
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
                blockDataList = new ArrayList<>(blockDataResponse);
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
                villageDataList = new ArrayList<>(villageDataResponses);
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
    }

    private void showLoader(boolean value) {
        loadingProgress.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}

