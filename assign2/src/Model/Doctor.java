package Model;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import static Model.CareHome.loginID;


public class Doctor extends Staff {
    private Prescription prescription;
    private Resident resident;
    private int doctorID;
    private String doctorName;
    private String password;
    private HashMap<Integer, Ward> wardMap;
    private HashMap<Integer, Doctor> doctorMap;
    private ArrayList<String> log;
    private HashMap<Integer, Resident> residentMap;
    private Room room;
    private HashMap<Integer, Bed> bedMap;
    private CareHome careHome;
    private Shift shift;
    private ArrayList<Shift> doctorShift;
    private ArrayList<Prescription> prescriptionArrayList;
    private ArrayList<Medicine> medicineArrayList;
    private ArrayList<String> medicineName;
    private ArrayList<String> medicineID;
    private ArrayList<String> medicineDosage;
    private ArrayList<String> medicineDetailArray;
    private ArrayList<Shift> nurseShift;
    private Nurse nurse;


    public Doctor(int doctorID, String doctorName, String password) {
        super(doctorID, doctorName, password);
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.password = password;
        log = new ArrayList<String>();
        residentMap = new HashMap<>();
        doctorShift = new ArrayList<>();
        prescription = new Prescription(0, null, null, null,
                null, 0, 0);
        prescriptionArrayList = new ArrayList<>();
        initiationDoctorShift();
        initiationprescription();
        medicineArrayList = prescription.getMedicineArrayList();
        nurseShift=new ArrayList<>();
    }


    public void initiationprescription() {//put all the prescription data into a arraylist
        try {
            Connection connectionDB = SqlLink.getConnection("CareHomeDB");
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery("select * from PRESCRIPTION");
            while (queryResult.next()) {
                int pIDFromDataBase = queryResult.getInt("PRESCRIPTIONID");
                String pNameFromDatabase = queryResult.getString("PRESCRIPTIONNAME");
                String pDateTimeFromDatabase = queryResult.getString("DATETIME");
                String reasonFromDataBase = queryResult.getString("REASON");
                String medicineIDfromDatabase = queryResult.getString("MEDICINEID");
                int doctorIDFromDatabase = queryResult.getInt("DOCTORID");
                int residentIDFromDatabase = queryResult.getInt("RESIDENTID");
                prescription = new Prescription(pIDFromDataBase, pNameFromDatabase, pDateTimeFromDatabase,
                        reasonFromDataBase, medicineIDfromDatabase, doctorIDFromDatabase, residentIDFromDatabase);
                prescriptionArrayList.add(prescription);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public HashMap<Integer, String> checkBedStatus() throws SQLException, ClassNotFoundException {//check the bed detail in the bed
        HashMap<Integer, String> bedStatus = new HashMap<>();
        room = new Room(0);
        room.initiationBed();
        bedMap = room.getBedMap();
        for (Bed bed : bedMap.values()) {
            if (bed.getBedState().equals("female")) {
                bedStatus.put(bed.getBedID(), "female");
            } else if (bed.getBedState().equals("male")) {
                bedStatus.put(bed.getBedID(), "male");
            } else {
                bedStatus.put(bed.getBedID(), "empty");
            }


        }
        return bedStatus;
    }

    public String checkBedDetail(int bedID) throws SQLException, ClassNotFoundException {//for display all the detail in the bed
        String result = null;
        room = new Room(0);
        careHome = new CareHome();
        room.initiationBed();
        bedMap = room.getBedMap();//transfer bedMap in the Room
        careHome.initiationResident();
        residentMap = careHome.getResidentMap();//transfer residentMap in the careHome
        Bed bed = bedMap.get(bedID);
        for (Resident resident : residentMap.values()) {
            if (resident.getResidentID() == bed.getResidentID()) {
                result = "resident ID:" + resident.getResidentID() + "\n" + "resident name:" + resident.getName() + "\n" +
                        "admitted time:" + resident.getAdmitted() + "\n" + "discharged time:" + resident.getDischarged() + "\n" +
                        "Gender:" + bed.getBedState();//this string will display in the window
                break;
            } else {
                result = "This is empty bed";
            }

        }
        return result;
    }

    public void initiationDoctorShift() {//put all the shift data into Array list from the database

        try {

            Connection connectionDB = SqlLink.getConnection("CareHomeDB");
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery("select * from DOCTORSHIFT");
            while (queryResult.next()) {
                String shiftIDFromDataBase = queryResult.getString("SHIFTID");
                String shiftNameFromDatabase = queryResult.getString("SHIFTNAME");
                int staffIDFromDatabase = queryResult.getInt("STAFFID");
                shift = new Shift(shiftIDFromDataBase, shiftNameFromDatabase, staffIDFromDatabase);
                doctorShift.add(shift);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public String findShiftID(String nameList) {//for find the shift ID use shift name
        String shiftID = null;
        for (int i = 0; i < doctorShift.size(); i++) {
            if (nameList.equals(doctorShift.get(i).getShiftName())) {
                shiftID = doctorShift.get(i).getShiftID();
                return shiftID;
            }

        }
        return null;
    }

    public ArrayList<String> findShiftByStaffID(String StaffID) {//for find the shift name use staff id
        ArrayList<String> nameDispaly = new ArrayList<>();
        int StaffIDInt = Integer.parseInt(StaffID);
        for (int i = 0; i < doctorShift.size(); i++) {
            if (StaffIDInt == doctorShift.get(i).getStaffID()) {
                nameDispaly.add(doctorShift.get(i).getShiftName());
            }

        }
        return nameDispaly;
    }


    public String addShift(String doctorID, String nameList) throws exceedWorkHourException, SQLException, ClassNotFoundException {//add shift
        nurse=new Nurse(0,null,null);
        nurseShift=nurse.getNurseShift();
        String result = null;
        int doctorIDInt = Integer.parseInt(doctorID);
        for (int i = 0; i < doctorShift.size(); i++) {//check the doctor is exceed the work time or not
            if (doctorIDInt == doctorShift.get(i).getStaffID()) {
                if (nameList.substring(0, 3).equals(doctorShift.get(i).getShiftName().substring(0, 3))) {
                    throw new exceedWorkHourException();//throw the exception
                }

            }
        }
        for (int i = 0; i <  nurseShift.size(); i++) {//check the shift is doctor's shift  or not
            if ( nurseShift.get(i).getShiftName().equals(nameList)){
                return "we can not add nurse shift to the doctor";
            }


        }

        String ID = findShiftID(nameList);
        doctorShift.add(new Shift(ID, nameList, doctorIDInt));
        Connection connectionDB = SqlLink.getConnection("CareHomeDB");
        String sql = "insert into DOCTORSHIFT (SHIFTID,SHIFTNAME,STAFFID)values(?,?,?)";//link to the database doctorShift table
        PreparedStatement pst = connectionDB.prepareStatement(sql);
        pst.setString(1, ID);
        pst.setString(2, nameList);
        pst.setInt(3, doctorIDInt);
        pst.executeUpdate();
        Date date = new Date();
        String logDetail = "log detail: " + "manager ID: " + loginID + " add doctor shift " + " time: " + date + " Doctor ID: " + doctorIDInt +
                " Shift Detail: " + nameList;
        String sql2 = "insert into LOG (LOGDETAIL)values(?)";//link to the database log
        PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
        pst2.setString(1, logDetail);
        pst2.executeUpdate();
        result = "add shift successfully";

        return result;

    }

    public String deleteShift(String doctorID, String doctorName) throws exceedWorkHourException, SQLException, ClassNotFoundException {//delete shift
        int doctorIDInt = Integer.parseInt(doctorID);
        String result = null;
        for (int i = 0; i < doctorShift.size(); i++) {
            if (doctorIDInt == doctorShift.get(i).getStaffID()) {
                if (doctorName.equals(doctorShift.get(i).getShiftName())) {
                    doctorShift.remove(i);//remove the shift in the memory
                    Connection connectionDB = SqlLink.getConnection("CareHomeDB");//link to the database :doctorShift table
                    String sql = "delete from DOCTORSHIFT where STAFFID=? and SHIFTNAME=?";
                    PreparedStatement pst = connectionDB.prepareStatement(sql);
                    pst.setInt(1, doctorIDInt);
                    pst.setString(2, doctorName);
                    pst.executeUpdate();
                    Date date = new Date();
                    String logDetail = "log detail: " + "manager ID: " + loginID + " delete doctor shift " + " time: " + date + " Doctor ID: " + doctorIDInt +
                            " Shift Detail: " + doctorName;
                    String sql2 = "insert into LOG (LOGDETAIL)values(?)";//link to the database :log table
                    PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
                    pst2.setString(1, logDetail);
                    pst2.executeUpdate();
                    result = "you delete shift:\n" + doctorName;
                    return result;
                }

            }

        }
        result = "The Shift not exist";
        return result;
    }


    public String addPrescription(ArrayList<Medicine> medicineList, String prescriptionID,
                                  String prescriptionName, String reason,
                                  int loginID, int residentID) throws SQLException, ClassNotFoundException {
        careHome = new CareHome();
        careHome.initiationResident();
        residentMap = careHome.getResidentMap();
        String residentName = residentMap.get(residentID).getName();
        if (residentMap.get(residentID).getPrescriptionID() != 0) {//check the prescription is exist or not
            return "one resident can have one prescription";


        }
        int prescriptionIDInt = Integer.parseInt(prescriptionID);
        for (int i = 0; i < prescriptionArrayList.size(); i++) {
            if (prescriptionArrayList.get(i).getPrescriptionID() == prescriptionIDInt) {// check the prescription
                return "we can not have  same prescription ID";
            }
        }
        Date prescriptionTime = new Date();
        for (int i = 0; i < medicineList.size(); i++) {
            prescriptionArrayList.add(new Prescription(prescriptionIDInt, prescriptionName,//add the prescription in the memory
                    prescriptionTime.toString(), reason, medicineList.get(i).getMedicineID(), loginID, residentID));

        }
        residentMap.put(residentID, new Resident(residentID, residentMap.get(residentID).getName(),//add the resident in the memory
                residentMap.get(residentID).getAdmitted(), residentMap.get(residentID).getDischarged(), prescriptionIDInt,
                residentMap.get(residentID).getBedID()));
        for (int i = 0; i < medicineList.size(); i++) {//add new medicine in the medicine
            medicineArrayList.add(new Medicine(medicineList.get(i).getMedicineID(), medicineList.get(i).getMedicineName(),
                    medicineList.get(i).getMedicineDosage(), medicineList.get(i).getPresciptionID()));
        }
        Connection connectionDB = SqlLink.getConnection("CareHomeDB");//link to prescription table
        for (int i = 0; i < medicineList.size(); i++) {
            String sql1 = "insert into PRESCRIPTION (PRESCRIPTIONID,PRESCRIPTIONNAME,DATETIME,REASON,MEDICINEID,DOCTORID,RESIDENTID)values(?,?,?,?,?,?,?)";
            PreparedStatement pst1 = connectionDB.prepareStatement(sql1);
            pst1.setInt(1, prescriptionIDInt);
            pst1.setString(2, prescriptionName);
            pst1.setString(3, prescriptionTime.toString());
            pst1.setString(4, reason);
            pst1.setString(5, medicineList.get(i).getMedicineID());
            pst1.setInt(6, loginID);
            pst1.setInt(7, residentID);
            pst1.executeUpdate();
        }
        String sql = "update RESIDENT set PRESCRIPTION=? where RESIDENTID=?";//link to resident table and add the prescription id
        PreparedStatement pst = connectionDB.prepareStatement(sql);
        pst.setInt(1, prescriptionIDInt);
        pst.setInt(2, residentID);
        pst.executeUpdate();
        for (int i = 0; i < medicineList.size(); i++) {
            String sql1 = "insert into MEDICINE (MEDICINEID,MEDICINENAME,MEDICINEDOSAGE,PRESCRIPTIONID)values(?,?,?,?)";//link to medicine table
            PreparedStatement pst1 = connectionDB.prepareStatement(sql1);
            pst1.setString(1, medicineList.get(i).getMedicineID());
            pst1.setString(2, medicineList.get(i).getMedicineName());
            pst1.setString(3, medicineList.get(i).getMedicineDosage());
            pst1.setInt(4, prescriptionIDInt);
            pst1.executeUpdate();
        }
        medicineDetailArray = new ArrayList<>();
        medicineName = searchMedicneListForName(prescriptionIDInt);
        medicineID = searchMedicneListForID(prescriptionIDInt);
        medicineDosage = searchMedicneListForDosage(prescriptionIDInt);
        for (int i = 0; i < medicineName.size(); i++) {// put all the detail into a string
            String medicineDetail = "medicine ID: " + medicineID.get(i) + " medicine Name: " +
                    medicineName.get(i) + " medicine Dosage: " + medicineDosage.get(i);
            medicineDetailArray.add(medicineDetail);
        }
        String medicineForLog = String.join(" / ", medicineDetailArray);
        String logDetail = "log detail: " + "Doctor ID: " + loginID + " add a new prescription " + " patient ID: " +
                residentID + " " + medicineForLog + " time: " + prescriptionTime;
        String sql2 = "insert into LOG (LOGDETAIL,RESIDENTID)values(?,?)";// link to the log put all the string into log
        PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
        pst2.setString(1, logDetail);
        pst2.setInt(2, residentID);
        pst2.executeUpdate();
        medicineDetailArray.clear();
        medicineName.clear();
        medicineID.clear();
        medicineDosage.clear();
        return "you are successful add a new \nprescription to a patient";
    }


    public int searchByResidentID(int residentID) throws SQLException, ClassNotFoundException {//search the prescription id by resident id
        careHome = new CareHome();
        careHome.initiationResident();
        residentMap = careHome.getResidentMap();
        int prescriptionID = 0;
        for (Resident resident : residentMap.values())
            if (resident.getResidentID() == residentID) {
                prescriptionID = resident.getPrescriptionID();
            }
        return prescriptionID;
    }

    public String searchPrescription(int prescriptionID) {//this method is for display all the prescription detail
        String result = null;
        for (int i = 0; i < prescriptionArrayList.size(); i++) {
            if (prescriptionID == prescriptionArrayList.get(i).getPrescriptionID()) {//if the prescription id is the same
                result = "prescription ID: " + prescriptionArrayList.get(i).getPrescriptionID()
                        + "\nprescription Name: " + prescriptionArrayList.get(i).getPrescriptionName()
                        + "\nprescription DateTime: " + prescriptionArrayList.get(i).getDatetime() +
                        "\nprescription reason: " + prescriptionArrayList.get(i).getReason();

            }

        }

        return result;
    }


    public String ModifyMedicine(ArrayList<Medicine> medicineModify, int prescriptionID, int loginId) throws SQLException, ClassNotFoundException {
        careHome = new CareHome();
        careHome.initiationResident();
        residentMap = careHome.getResidentMap();
        Date prescriptionTime = new Date();
        String prescriptionName = null;
        String prescriptionReason = null;
        int doctorID = 0;
        int residentID = 0;
        String prescriptionIDString = String.valueOf(prescriptionID);
        for (int i = 0; i < prescriptionArrayList.size(); i++) {
            if (prescriptionArrayList.get(i).getPrescriptionID() == prescriptionID) {
                if (prescriptionArrayList.get(i).getDoctorID() != loginId) {//if the doctor id is the same you can modify the prescription
                    return "you do not have authority to modify \nother doctor's prescription";
                }
            }
        }
        for (int i = 0; i < prescriptionArrayList.size(); i++) {//get the all the prescription id in the arraylist
            if (prescriptionArrayList.get(i).getPrescriptionID() == prescriptionID) {
                prescriptionName = prescriptionArrayList.get(i).getPrescriptionName();
                prescriptionReason = prescriptionArrayList.get(i).getReason();
                doctorID = prescriptionArrayList.get(i).getDoctorID();
                residentID = prescriptionArrayList.get(i).getResidentID();
            }
        }
        if (medicineModify.isEmpty()) {//if the medicine list is empty it means doctor is delete the prescription
            prescriptionArrayList.removeIf(prescription -> prescription.getPrescriptionID() == prescriptionID);
            medicineArrayList.removeIf(medicine -> medicine.presciptionIDProperty().get().equals(prescriptionIDString));
            Connection connectionDB = SqlLink.getConnection("CareHomeDB");//delete the all the detail in the prescription
            String sql1 = "delete from PRESCRIPTION where PRESCRIPTIONID=?";
            PreparedStatement pst1 = connectionDB.prepareStatement(sql1);
            pst1.setInt(1, prescriptionID);
            pst1.executeUpdate();
            String sql3 = "delete from MEDICINE where PRESCRIPTIONID=?";//delete the all the detail in the medicine
            PreparedStatement pst3 = connectionDB.prepareStatement(sql3);
            pst3.setInt(1, prescriptionID);
            pst3.executeUpdate();
            String sql = "update RESIDENT set  PRESCRIPTION=null where RESIDENTID=?";//delete the all the detail in the resident
            PreparedStatement pst = connectionDB.prepareStatement(sql);
            pst.setInt(1, residentID);
            pst.executeUpdate();
            String logDetail = "log detail: " + "Doctor ID: " + loginID + " delete prescription " + " patient ID: " +
                    residentID + " time: " + prescriptionTime;
            String sql2 = "insert into LOG (LOGDETAIL,RESIDENTID)values(?,?)";//link to the log
            PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
            pst2.setString(1, logDetail);
            pst2.setInt(2, residentID);
            pst2.executeUpdate();
            return "prescription deleted";
        }

        prescriptionArrayList.removeIf(prescription -> prescription.getPrescriptionID() == prescriptionID);//delete the prescription first in the memory

        for (int i = 0; i < medicineModify.size(); i++) {
            prescriptionArrayList.add(new Prescription(prescriptionID, prescriptionName, prescriptionTime.toString(),
                    prescriptionReason, medicineModify.get(i).getMedicineID(), doctorID, residentID));//add the prescription in the memory
        }
        medicineArrayList.removeIf(medicine -> medicine.presciptionIDProperty().get().equals(prescriptionIDString));//delete the medicine first in the memory
        for (int i = 0; i < medicineModify.size(); i++) {
            medicineArrayList.add(new Medicine(medicineModify.get(i).getMedicineID(),
                    medicineModify.get(i).getMedicineName(), medicineModify.get(i).getMedicineDosage(),//add the medicine in the memory
                    prescriptionIDString));

        }
        Connection connectionDB = SqlLink.getConnection("CareHomeDB");
        String sql1 = "delete from PRESCRIPTION where PRESCRIPTIONID=?";//delete the prescription in the database
        PreparedStatement pst1 = connectionDB.prepareStatement(sql1);
        pst1.setInt(1, prescriptionID);
        pst1.executeUpdate();
        for (int i = 0; i < medicineModify.size(); i++) {//insert the prescription in the database
            String sql2 = "insert into PRESCRIPTION (PRESCRIPTIONID,PRESCRIPTIONNAME,DATETIME,REASON,MEDICINEID,DOCTORID,RESIDENTID)values(?,?,?,?,?,?,?)";
            PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
            pst2.setInt(1, prescriptionID);
            pst2.setString(2, prescriptionName);
            pst2.setString(3, prescriptionTime.toString());
            pst2.setString(4, prescriptionReason);
            pst2.setString(5, medicineModify.get(i).getMedicineID());
            pst2.setInt(6, doctorID);
            pst2.setInt(7, residentID);
            pst2.executeUpdate();
        }
        String sql3 = "delete from MEDICINE where PRESCRIPTIONID=?";//delete the medicine in the database
        PreparedStatement pst3 = connectionDB.prepareStatement(sql3);
        pst3.setInt(1, prescriptionID);
        pst3.executeUpdate();
        for (int i = 0; i < medicineModify.size(); i++) {
            String sql4 = "insert into MEDICINE (MEDICINEID,MEDICINENAME,MEDICINEDOSAGE,PRESCRIPTIONID)values(?,?,?,?)";//insert the medicine in the database
            PreparedStatement pst4 = connectionDB.prepareStatement(sql4);
            pst4.setString(1, medicineModify.get(i).getMedicineID());
            pst4.setString(2, medicineModify.get(i).getMedicineName());
            pst4.setString(3, medicineModify.get(i).getMedicineDosage());
            pst4.setInt(4, prescriptionID);
            pst4.executeUpdate();
        }
        medicineDetailArray = new ArrayList<>();
        medicineName = searchMedicneListForName(prescriptionID);
        medicineID = searchMedicneListForID(prescriptionID);
        medicineDosage = searchMedicneListForDosage(prescriptionID);
        for (int i = 0; i < medicineName.size(); i++) {//put all the medicine in a string
            String medicineDetail = "medicine ID: " + medicineID.get(i) + " medicine Name: " +
                    medicineName.get(i) + " medicine Dosage: " + medicineDosage.get(i);
            medicineDetailArray.add(medicineDetail);
        }
        String medicineForLog = String.join(" / ", medicineDetailArray);
        String logDetail = "log detail: " + "Doctor ID: " + loginID + " modify prescription " + " patient ID: " +
                residentID + " " + medicineForLog + " time: " + prescriptionTime;
        String sql2 = "insert into LOG (LOGDETAIL,RESIDENTID)values(?,?)";//link to the log
        PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
        pst2.setString(1, logDetail);
        pst2.setInt(2, residentID);
        pst2.executeUpdate();
        medicineDetailArray.clear();
        medicineName.clear();
        medicineID.clear();
        medicineDosage.clear();
        return "you are successful update \nprescription to a patient";
    }

    public ArrayList<Medicine> searchMedicneList(int prescriptionID) {// use prescription id to search the medicine list
        String prescriptionIDString = String.valueOf(prescriptionID);
        ArrayList<Medicine> list = new ArrayList<>();
        for (int i = 0; i < medicineArrayList.size(); i++) {
            if (medicineArrayList.get(i).getPresciptionID() != null) {
                if (prescriptionIDString.equals(medicineArrayList.get(i).getPresciptionID())) {
                    list.add(new Medicine(medicineArrayList.get(i).getMedicineID(), medicineArrayList.get(i).getMedicineName(),
                            medicineArrayList.get(i).getMedicineDosage(), prescriptionIDString));

                }
            }

        }
        return list;
    }

    public ArrayList<String> searchMedicneListForID(int prescriptionID) {// use prescription id to search the medicine list
        String prescriptionIDString = String.valueOf(prescriptionID);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < medicineArrayList.size(); i++) {
            if (medicineArrayList.get(i).getPresciptionID() != null) {
                if (prescriptionIDString.equals(medicineArrayList.get(i).getPresciptionID())) {
                    list.add(medicineArrayList.get(i).getMedicineID());

                }
            }

        }
        return list;
    }

    public ArrayList<String> searchMedicneListForName(int prescriptionID) {// use prescription id to search the medicine list
        String prescriptionIDString = String.valueOf(prescriptionID);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < medicineArrayList.size(); i++) {
            if (medicineArrayList.get(i).getPresciptionID() != null) {
                if (prescriptionIDString.equals(medicineArrayList.get(i).getPresciptionID())) {
                    list.add(medicineArrayList.get(i).getMedicineName());

                }
            }

        }
        return list;
    }


    public ArrayList<String> searchMedicneListForDosage(int prescriptionID) {// use prescription id to search the medicine list
        String prescriptionIDString = String.valueOf(prescriptionID);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < medicineArrayList.size(); i++) {
            if (medicineArrayList.get(i).getPresciptionID() != null) {
                if (prescriptionIDString.equals(medicineArrayList.get(i).getPresciptionID())) {
                    list.add(medicineArrayList.get(i).getMedicineDosage());

                }
            }

        }
        return list;
    }


    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<Integer, Ward> getWardMap() {
        return wardMap;
    }

    public void setWardMap(HashMap<Integer, Ward> wardMap) {
        this.wardMap = wardMap;
    }

    public HashMap<Integer, Doctor> getDoctorMap() {
        return doctorMap;
    }

    public void setDoctorMap(HashMap<Integer, Doctor> doctorMap) {
        this.doctorMap = doctorMap;
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public void setLog(ArrayList<String> log) {
        this.log = log;
    }

    public ArrayList<Shift> getDoctorShift() {
        return doctorShift;
    }

    public void setDoctorShift(ArrayList<Shift> doctorShift) {
        this.doctorShift = doctorShift;
    }
}
