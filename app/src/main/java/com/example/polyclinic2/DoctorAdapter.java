package com.example.polyclinic2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DocViewHolder>{
    private List<Doctor> docList;
    private OnItemClickListener listener;
    private Context context;
    private Bundle bundle;

    public interface OnItemClickListener {
        void onItemClick(Doctor doctor);
    }

    public DoctorAdapter(List<Doctor> docList, Context context, OnItemClickListener listener, Bundle bundle) {

        this.docList = docList;
        this.listener = listener;
        this.context = context;
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public DoctorAdapter.DocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new DoctorAdapter.DocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.DocViewHolder holder, int position) {
        Doctor doctor = docList.get(position);
        holder.fioDoc.setText(doctor.getFioDoc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString(Constant.DOCTOR_ID, doctor.getId());
                DoctorProfileFragment doctorProfileFragment = new DoctorProfileFragment();
                doctorProfileFragment.setArguments(b);
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_doc_list, doctorProfileFragment).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount(){
        return docList.size();
    }

    public static class DocViewHolder extends RecyclerView.ViewHolder {
        TextView fioDoc;

        public DocViewHolder(@NonNull View itemView) {
            super(itemView);
            fioDoc = itemView.findViewById(R.id.fio_list);
        }
    }
}
