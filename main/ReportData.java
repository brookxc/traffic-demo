package com.example.trafficapp;

public class ReportData {
    private int reportId;
    private String driverName,licensePlateNumber,driverAddress,type,description,reportDate,payment,vehicleModel,punishmentPrice;




    public ReportData(int reportId, String driverName, String licensePlateNumber, String driverAddress, String type, String description, String reportDate, String payment, String vehicleModel, String punishmentPrice){

        this.reportId = reportId;
        this.driverName = driverName;
        this.licensePlateNumber = licensePlateNumber;
        this.driverAddress = driverAddress;
        this.type = type;
        this.description = description;
        this.reportDate = reportDate;
        this.payment = payment;
        this.vehicleModel = vehicleModel;
        this.punishmentPrice = punishmentPrice;

    }

    public int getReportId() {
        return reportId;
    }
    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getPunishmentPrice() {
        return punishmentPrice;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
