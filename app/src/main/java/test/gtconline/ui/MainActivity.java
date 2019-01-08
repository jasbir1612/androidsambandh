package test.gtconline.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import test.gtconline.ErrorHandler.AppCrashHandler;
import test.gtconline.R;
import test.gtconline.pojo.response.BlockDataResponse;
import test.gtconline.pojo.response.DistrictDataResponse;
import test.gtconline.pojo.response.SchoolDataResponse;
import test.gtconline.pojo.response.VillageDataResponse;
import test.gtconline.rest.ApiService;
import test.gtconline.rest.ResponseCallback;

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
    RequestQueue requestQueue;
    String baseurl = "http://sambandhhealthapi.uniso.in/api/Sambandh/GetUdiseCount?UdiseCode=";
    String finalUdice, url;
    String finalDudice;
    static int count =3;
    TextView txtCount;
    String udiseFinal=null;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(getApplicationContext()));

        txtCount = findViewById(R.id.txtucnt);

        requestQueue = Volley.newRequestQueue(this);
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
                if (position == 0) {
                    udiseFinal = doubleConverter(schoolDataList.get(position).getSchoolCode());
                    udiceEt.setText(doubleConverter(schoolDataList.get(position).getSchoolCode()));
                } else {
                    udiseFinal = doubleConverter(schoolDataList.get(position).getSchoolCode());
                    udiceEt.setText(doubleConverter(schoolDataList.get(position).getSchoolCode()));
                }
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
        if (v.getId() == R.id.btn_submit) {

            if (udiseFinal != null || udiceEt.getText().toString()!=null)
            {

                    finalUdice = udiceEt.getText().toString();
                finalDudice = dUdiceEt.getText().toString();
                url = baseurl + finalUdice;

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            Log.d("Volley", "jsonObject" + jsonObject.toString());
                            String str = jsonObject.getString("Cnt");
                            //                    count = Integer.parseInt(str);
                            int count2 = Integer.parseInt(str);
                            Log.d("Volley", "count: " + count2);

                            if (TextUtils.isEmpty(finalUdice)) {
                                Toast.makeText(MainActivity.this, "Problem in finding udice. Try again later.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (count2 < 3) {
                                final AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(MainActivity.this);
                                }
                                builder.setTitle("UDISE code")
                                        .setMessage("Selected UDISE code is: " +finalUdice)
                                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent resultIntent = new Intent();
                                                resultIntent.putExtra("udice_code", finalUdice);
                                                resultIntent.putExtra("dudice_code", finalDudice);
                                                setResult(Activity.RESULT_OK, resultIntent);
                                                finish();
                                            }
                                        })
                                        .setNegativeButton("Select Again", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();


                            } else {
                                Toast.makeText(MainActivity.this, "Maximum 3 users allowed on this UDISE code.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Volley", "catch error: " + e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        Log.d("Volley", error.toString());

                    }
                });
                //
                //        String temp = txtCount.getText().toString();
                //        int countTemp = Integer.parseInt(temp);
                //        Log.d("Volley", "count here: " +countTemp);

                requestQueue.add(jsonArrayRequest);
            }
            else{
                Toast.makeText(this, "Udise code not found", Toast.LENGTH_SHORT).show();
            }
        }
        
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
                Log.d("Resp", "School List" +schoolDataList);
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
        if(data==null) {
            Toast.makeText(MainActivity.this, "No School found", Toast.LENGTH_SHORT).show();
        }else {
            ArrayList<String> providerlist = new ArrayList<>();

            for (SchoolDataResponse response : data) {
                providerlist.add(response.getSchoolName());
            }

            Log.d("Resp2", "List: " + providerlist);
            if (providerlist.isEmpty()) {
                Toast.makeText(MainActivity.this, "No School found", Toast.LENGTH_SHORT).show();
                udiseFinal = null;
                udiceEt.setText("");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, providerlist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            schoolSpinner.setAdapter(adapter);

            submitButton.setEnabled(true);
            if (schoolDataList.size() > 0) {
//            udiseFinal = doubleConverter(schoolDataList.get(schoolSpinner.getSelectedItemPosition()).getSchoolCode());
                dUdiceEt.setText(schoolDataList.get(schoolSpinner.getSelectedItemPosition()).getDistSchoolCode());
            }

        }
    }

    private void showLoader(boolean value) {
        loadingProgress.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    private String doubleConverter(Double value) {
        String ans = BigDecimal.valueOf(value).toPlainString();
        return ans;
    }
//    public void getVolleyData()
//    {
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try{
//                    JSONObject jsonObject = response.getJSONObject(0);
//                    Log.d("Volley", "jsonObject"+jsonObject.toString());
//                    str = jsonObject.getString("Cnt");
//                    count = Integer.parseInt(str);
//                    Log.d("Volley", "count: " +count);
//
//                }catch (JSONException e)
//                {
//                    e.printStackTrace();
//                    Log.d("Volley", "catch error: "+e.toString());
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
//                Log.d("Volley", error.toString());
//
//            }
//        });
//
//        requestQueue.add(jsonArrayRequest);
//    }
}

