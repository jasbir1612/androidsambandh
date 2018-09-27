package test.testing.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import test.testing.R;
import test.testing.adapter.SchoolDataAdapter;
import test.testing.pojo.response.SchoolDataResponse;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<SchoolDataResponse> dataList = new ArrayList<>();
    private RecyclerView mainRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent().getExtras() != null) {
            dataList = getIntent().getParcelableArrayListExtra("list");
        }

        mainRv = findViewById(R.id.main_rv);
        mainRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mainRv.setAdapter(new SchoolDataAdapter(dataList));
    }
}
