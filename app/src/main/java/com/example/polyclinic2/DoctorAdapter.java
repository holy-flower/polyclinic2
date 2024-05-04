package com.example.polyclinic2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DocViewHolder>{
    private List<Doctor> docList;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Doctor doctor);
    }

    public DoctorAdapter(List<Doctor> docList, Context context, OnItemClickListener listener) {

        this.docList = docList;
        this.listener = listener;
        this.context = context;
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
                Log.i("Pos", String.valueOf(position));
                Log.i("Id doc", String.valueOf(doctor.id));
                Log.i("Id c", String.valueOf(doctor.fioDoc));

                Intent intent = new Intent(context, DoctorProfileActivity.class);
                intent.putExtra(Constant.DOCTOR_ID, doctor.getId());
                context.startActivity(intent);

                listener.onItemClick(doctor);
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
