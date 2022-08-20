package Model;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Prescription {
    private String Datetime;
    private int prescriptionID;
    private HashMap<Integer, Medicine> medicineHashMap;
    private String prescriptionName;
    private String reason;
    private String medicineID;
    private int doctorID;
    private int residentID;
    private ArrayList<Medicine> medicineArrayList;
    private Medicine medicine;

    public Prescription(int prescriptionID, String prescriptionName, String datetime,
                        String reason, String medicineID, int doctorID, int residentID) {
        this.Datetime = datetime;
        this.prescriptionID = prescriptionID;
        this.prescriptionName = prescriptionName;
        this.reason = reason;
        this.medicineID = medicineID;
        this.doctorID = doctorID;
        this.residentID = residentID;
        medicineArrayList = new ArrayList<>();
        initiationMedicine();

    }


    public void initiationMedicine() {//put all the data into the medicine Arraylist
        try {
            Connection connectionDB = SqlLink.getConnection("CareHomeDB");
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery("select * from MEDICINE");
            while (queryResult.next()) {
                String medicineIDFromDataBase = queryResult.getString("MEDICINEID");
                String medicineNameFromDatabase = queryResult.getString("MEDICINENAME");
                String medicinedosageFromDatabase = queryResult.getString("MEDICINEDOSAGE");
                int prescriptionIDFromDatabase = queryResult.getInt("PRESCRIPTIONID");
                medicine = new Medicine(medicineIDFromDataBase, medicineNameFromDatabase, medicinedosageFromDatabase,
                        (prescriptionIDFromDatabase + ""));
                medicineArrayList.add(medicine);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public ArrayList<String> getMedicineName() {//search all the medicine name in the arraylist
        ArrayList<String> medcineName = new ArrayList<>();
        for (int i = 0; i < medicineArrayList.size(); i++) {
            if (medicineArrayList.get(i).getMedicineDosage() == null && medicineArrayList.get(i).getPresciptionID().equals("0")) {
                medcineName.add(medicineArrayList.get(i).getMedicineName());
            }
        }


        return medcineName;
    }

    public String fineID(String medicineName) {//search all the medicine ID in the arraylist
        String medicineIDFrom = null;
        for (int i = 0; i < medicineArrayList.size(); i++) {
            if (medicineName.equals(medicineArrayList.get(i).getMedicineName().toString())) {
                medicineIDFrom = medicineArrayList.get(i).getMedicineID();
            }
        }
        return medicineIDFrom;
    }

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }

    public int getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public HashMap<Integer, Medicine> getMedicineHashMap() {
        return medicineHashMap;
    }

    public void setMedicineHashMap(HashMap<Integer, Medicine> medicineHashMap) {
        this.medicineHashMap = medicineHashMap;
    }

    public String getPrescriptionName() {
        return prescriptionName;
    }

    public void setPrescriptionName(String prescriptionName) {
        this.prescriptionName = prescriptionName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getResidentID() {
        return residentID;
    }

    public void setResidentID(int residentID) {
        this.residentID = residentID;
    }

    public ArrayList<Medicine> getMedicineArrayList() {
        return medicineArrayList;
    }

    public void setMedicineArrayList(ArrayList<Medicine> medicineArrayList) {
        this.medicineArrayList = medicineArrayList;
    }
}
