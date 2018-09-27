package test.testing.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.testing.R;
import test.testing.pojo.response.SchoolDataResponse;

public class SchoolDataAdapter extends RecyclerView.Adapter<SchoolDataAdapter.SchoolDataViewHolder> {

    ArrayList<SchoolDataResponse> dataList;

    public SchoolDataAdapter(List<SchoolDataResponse> data) {
        this.dataList = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public SchoolDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SchoolDataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.school_data_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolDataViewHolder holder, int position) {
        holder.set(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class SchoolDataViewHolder extends RecyclerView.ViewHolder {

        private SchoolDataResponse item;
        private TextView mainTv;

        public SchoolDataViewHolder(View itemView) {
            super(itemView);
            mainTv = itemView.findViewById(R.id.main_tv);
        }

        public void set(SchoolDataResponse data) {
            item = data;
            String temp = item.getRowid() + "\n" +
                    item.getDistrictName() + "\n" +
                    item.getBlockName() + "\n" +
                    item.getVillageName() + "\n" +
                    item.getSchoolName() + "\n" +
                    item.getSchoolCode() + "\n" +
                    item.getSchoolCategory() + "\n" +
                    item.getSchoolManagement() + "\n" +
                    item.getActivity();

            mainTv.setText(temp);
        }
    }
}
