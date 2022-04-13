package com.example.trafficapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    Context ctx;
    LayoutInflater inflater;
    private List<ReportData> reportDatas;

    public Adapter(Context ctx, List<ReportData> reportDatas){
        this.ctx = ctx;
        this.reportDatas = reportDatas;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportData reportData = reportDatas.get(position);

        //bind the data
        holder.reportID.setText(String.valueOf(reportData.getReportId()));
        holder.driverName.setText(reportData.getDriverName());
        holder.licensePlateNumber.setText(reportData.getLicensePlateNumber());
        holder.type.setText(reportData.getType());
        holder.driverAddress.setText(reportData.getDriverAddress());
        holder.description.setText(reportData.getDescription());
        holder.reportDate.setText(reportData.getReportDate());
        holder.payment.setText(reportData.getPayment());
        holder.vehicleModel.setText(reportData.getVehicleModel());
        holder.punishmentPrice.setText(reportData.getPunishmentPrice());

    }

    @Override
    public int getItemCount() {
        return reportDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView reportID, driverName, licensePlateNumber, type, driverAddress, description, reportDate, payment, vehicleModel, punishmentPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reportID = itemView.findViewById(R.id.reportID);
            driverName = itemView.findViewById(R.id.driverName);
            licensePlateNumber = itemView.findViewById(R.id.licensePlateNumber);
            type = itemView.findViewById(R.id.type);
            driverAddress = itemView.findViewById(R.id.driverAddress);
            description = itemView.findViewById(R.id.description);
            reportDate = itemView.findViewById(R.id.reportDate);
            payment = itemView.findViewById(R.id.payment);
            vehicleModel = itemView.findViewById(R.id.vehicleModel);
            punishmentPrice = itemView.findViewById(R.id.punishmentPrice);


        }
    }
}
