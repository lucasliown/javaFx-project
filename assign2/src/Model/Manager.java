package Model;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static Model.CareHome.loginID;

public class Manager {

    private int manageID;
    private String managerName;
    private String managerPassword;
    private HashMap<Integer, Ward> wardMap;
    private HashMap<Integer, Bed> bedMap;
    private HashMap<Integer, Doctor> doctorMap;
    private HashMap<Integer, Nurse> nurseMap;
    private ArrayList<String> log;
    private CareHome careHome;
    private HashMap<Integer, Resident> residentMap;
    private Room room;


    public Manager(int manageID, String managerName, String managerPassword) {
        log = new ArrayList<String>();
        this.managerName = managerName;
        this.managerPassword = managerPassword;
        this.manageID = manageID;
        residentMap = new HashMap<>();


    }

    public HashMap<Integer, String> checkBedStatus() throws SQLException, ClassNotFoundException {//search the bed detail
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


    public String addResidentToBed(int residentID, String residentName, String Gender, String addAdmissiontime, String discharged, int addToBed_Num) throws SQLException, ClassNotFoundException {
        room = new Room(0);
        careHome = new CareHome();
        room.initiationBed();
        bedMap = room.getBedMap();
        careHome.initiationResident();
        residentMap = careHome.getResidentMap();
        Bed bed = bedMap.get(addToBed_Num);
        for (Resident resident : residentMap.values()) {//if the already have a resident you can not add same id
            if (resident.getResidentID() == residentID) {
                return "resident can not have same ID";
            }


        }

        if (bed.getBedState().equals("empty")) {//if the bed is empty
            if (Gender.equals("female")) {
                bedMap.put(addToBed_Num, new Bed(addToBed_Num, Gender, residentID));//you can add the bed into the map
                Resident newResident = new Resident(residentID, residentName, addAdmissiontime, discharged, 0, addToBed_Num);
                residentMap.put(residentID, newResident);
                Connection connectionDB = SqlLink.getConnection("CareHomeDB");
                String sql = "update BED set BEDSTATUS=?, RESIDENTID=? where BEDID=?";//link to the bed table
                PreparedStatement pst = connectionDB.prepareStatement(sql);
                pst.setString(1, Gender);
                pst.setInt(2, residentID);
                pst.setInt(3, addToBed_Num);
                pst.executeUpdate();
                String sql1 = "insert into RESIDENT (RESIDENTID,RESIDENTNAME,ADMITTED,DISCHARGED,BEDID)values(?,?,?,?,?)";//link to the resident table
                PreparedStatement pst1 = connectionDB.prepareStatement(sql1);
                pst1.setInt(1, residentID);
                pst1.setString(2, residentName);
                pst1.setString(3, addAdmissiontime);
                pst1.setString(4, discharged);
                pst1.setInt(5, addToBed_Num);
                pst1.executeUpdate();

                Date recoring = new Date();
                String logDetail = "log detail: " + "managerID: " + loginID + " add resident to bed " + " time: " + recoring + " bed.No: " + addToBed_Num + " resident ID: " + residentID + " resident name: " + residentName +
                        " admitted time: " + addAdmissiontime + " " + " discharged time: " + discharged +
                        " Gender: " + "female";
                String sql2 = "insert into LOG (LOGDETAIL,RESIDENTID)values(?,?)";//link to the log and put the string in the log
                PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
                pst2.setString(1, logDetail);
                pst2.setInt(2, residentID);
                pst2.executeUpdate();
                return "you are add a female patient to a bed";
            } else if (Gender.equals("male")) {
                bedMap.put(addToBed_Num, new Bed(addToBed_Num, Gender, residentID));
                Resident newResident = new Resident(residentID, residentName, addAdmissiontime, discharged, 0, addToBed_Num);
                residentMap.put(residentID, newResident);
                Connection connectionDB = SqlLink.getConnection("CareHomeDB");
                String sql = "update BED set BEDSTATUS=?, RESIDENTID=? where BEDID=?";//link to the bed table and change the bed status
                PreparedStatement pst = connectionDB.prepareStatement(sql);
                pst.setString(1, Gender);
                pst.setInt(2, residentID);
                pst.setInt(3, addToBed_Num);
                pst.executeUpdate();
                String sql1 = "insert into RESIDENT (RESIDENTID,RESIDENTNAME,ADMITTED,DISCHARGED,BEDID)values(?,?,?,?,?)";//link to the resident table and change the bed status
                PreparedStatement pst1 = connectionDB.prepareStatement(sql1);
                pst1.setInt(1, residentID);
                pst1.setString(2, residentName);
                pst1.setString(3, addAdmissiontime);
                pst1.setString(4, discharged);
                pst1.setInt(5, addToBed_Num);
                pst1.executeUpdate();
                Date recoring = new Date();
                String logDetail = "log detail: " + "managerID: " + loginID + " add resident to bed " + " time: " + recoring + " bed.No: " + addToBed_Num + " resident ID: " + residentID + " resident name: " + residentName +
                        " admitted time: " + addAdmissiontime + " " + " discharged time: " + discharged +
                        " Gender: " + "male";
                String sql2 = "insert into LOG (LOGDETAIL,RESIDENTID)values(?,?)";//link to the log
                PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
                pst2.setString(1, logDetail);
                pst2.setInt(2, residentID);
                pst2.executeUpdate();

                return "you are add a male patient to a bed";
            }

        } else {
            if (bed.getBedState().equals("female")) {
                return "already have a female patient on the bed";
            } else if (bed.getBedState().equals("male")) {
                return "already have a male patient on the bed";
            }


        }


        return null;
    }


    public void addStaff(String addID, String addName, String addPassword, String accountType) throws SQLException, ClassNotFoundException {
        careHome = new CareHome();
        careHome.initiationCareHome();
        doctorMap = careHome.getDoctorMap();
        nurseMap = careHome.getNurseMap();
        int addIDInt = Integer.parseInt(addID);
        if (accountType.equals("Doctor")) {//if the account type is doctor
            Doctor newDoctor = new Doctor(addIDInt, addName, addPassword);
            doctorMap.put(addIDInt, newDoctor);
            Connection connectionDB = SqlLink.getConnection("CareHomeDB");//add the doctor to the database
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate("insert into DOCTOR (DOCTORID,USERNAME,PASSWORD)values(" +
                    addIDInt + ",'" + addName + "','" + addPassword + "');");

            Date recoring = new Date();
            String logDetail = "log detail: " + "manager ID: " + loginID + " add doctor staff " + " time: " + recoring + " Doctor ID: " + addID + " Doctor name: " + addName +
                    " Doctor password: " + addPassword;
            String sql2 = "insert into LOG (LOGDETAIL)values(?)";
            PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
            pst2.setString(1, logDetail);
            pst2.executeUpdate();

        } else if (accountType.equals("Nurse")) {//if the account type is nurse
            Nurse newNurse = new Nurse(addIDInt, addName, addPassword);
            nurseMap.put(addIDInt, newNurse);
            Connection connectionDB = SqlLink.getConnection("CareHomeDB");
            String sql = "insert into NURSE (NURSEID,USERNAME,PASSWORD)values(?,?,?)";//add the nurse to the database
            PreparedStatement pst = connectionDB.prepareStatement(sql);
            pst.setInt(1, addIDInt);
            pst.setString(2, addName);
            pst.setString(3, addPassword);
            pst.executeUpdate();
            Date recoring = new Date();
            String logDetail = "log detail: " + "manager ID: " + loginID + " add nurse staff " + " time: " + recoring + " Nurse ID: " + addID + " Nurse name: " + addName +
                    " Nurse password: " + addPassword;
            String sql2 = "insert into LOG (LOGDETAIL)values(?)";
            PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
            pst2.setString(1, logDetail);
            pst2.executeUpdate();

        }

    }


    public String modifyStaff(String changeID, String changeName, String changePassword, String modifyAccountType) throws SQLException, ClassNotFoundException {
        careHome = new CareHome();
        careHome.initiationCareHome();
        doctorMap = careHome.getDoctorMap();
        nurseMap = careHome.getNurseMap();
        int changeIDInt = Integer.parseInt(changeID);
        if (modifyAccountType.equals("Doctor")) {
            for (Doctor doctor : doctorMap.values()) {
                if (doctorMap.containsKey(changeIDInt) == false) {//if the doctor is not exist we can modify
                    String result = "we can't find the ID";
                    return result;
                }
                Doctor newDoctor = new Doctor(changeIDInt, changeName, changePassword);
                doctorMap.put(changeIDInt, newDoctor);//put the doctor detail in the memory
                Connection connectionDB = SqlLink.getConnection("CareHomeDB");
                String sql = "update DOCTOR set DOCTORID=?, USERNAME=? ,PASSWORD=? where DOCTORID=?";//link to the database to update the doctor
                PreparedStatement pst = connectionDB.prepareStatement(sql);
                pst.setInt(1, changeIDInt);
                pst.setString(2, changeName);
                pst.setString(3, changePassword);
                pst.setInt(4, changeIDInt);
                pst.executeUpdate();
                Date recoring = new Date();
                String logDetail = "log detail: " + "manager ID: " + loginID + " modify doctor staff " + " time: " + recoring + " Doctor ID: " + changeID + " Doctor name: " + changeName +
                        " Doctor password: " + changePassword;
                String sql2 = "insert into LOG (LOGDETAIL)values(?)";//link to the log and put the string into the database
                PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
                pst2.setString(1, logDetail);
                pst2.executeUpdate();
                String result = "modify successfully";
                return result;
            }


        } else if (modifyAccountType.equals("Nurse")) {
            for (Nurse nurse : nurseMap.values()) {
                if (nurseMap.containsKey(changeIDInt) == false) {//if the nurse is not exist we can modify
                    String result = "we can't find the ID";
                    return result;
                }
                Nurse newNurse = new Nurse(changeIDInt, changeName, changePassword);
                nurseMap.put(changeIDInt, newNurse);
                Connection connectionDB = SqlLink.getConnection("CareHomeDB");
                String sql = "update NURSE set NURSEID=?, USERNAME=?, PASSWORD=? where NURSEID=?";//link to the database to update the nurse
                PreparedStatement pst = connectionDB.prepareStatement(sql);
                pst.setInt(1, changeIDInt);
                pst.setString(2, changeName);
                pst.setString(3, changePassword);
                pst.setInt(4, changeIDInt);
                pst.executeUpdate();
                Date recoring = new Date();
                String logDetail = "log detail: " + "manager ID: " + loginID + " modify nurse staff " + " time: " + recoring + " Nurse ID: " + changeID + " Nurse name: " + changeName +
                        " Nurse password: " + changePassword;
                String sql2 = "insert into LOG (LOGDETAIL)values(?)";//link to the log and put the string into the database
                PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
                pst2.setString(1, logDetail);
                pst2.executeUpdate();
                String result = "modify successfully";
                return result;
            }


        }
        return null;
    }


    public String searchByID(String modifyAccountType, String ModifyID) {//search the staff id
        String noResult = "we can not find the result";
        int ModifyIDInt = Integer.parseInt(ModifyID);
        careHome = new CareHome();
        careHome.initiationCareHome();
        doctorMap = careHome.getDoctorMap();
        nurseMap = careHome.getNurseMap();
        if (modifyAccountType.equals("Nurse")) {//if the account type is nurse
            for (Nurse nurse : nurseMap.values()) {
                if (nurse.getNurseID() == ModifyIDInt) {
                    String result = "Nurse ID: " + nurse.getNurseID() + "\nNurse Name: " + nurse.getNurseName()
                            + "\nNurse Password: " + nurse.getPassword();
                    return result;
                }
            }
        } else if (modifyAccountType.equals("Doctor")) {//if the account type is doctor
            for (Doctor doctor : doctorMap.values())
                if (doctor.getDoctorID() == ModifyIDInt) {
                    String result = "Doctor ID: " + doctor.getDoctorID() + "\nDoctor Name: " + doctor.getDoctorName()
                            + "\nDoctor Password: " + doctor.getPassword();
                    return result;
                }


        }

        return noResult;
    }

    public String searchByIDForChangeID(String modifyAccountType, String ModifyID) {//search the staff id by id
        int ModifyIDInt = Integer.parseInt(ModifyID);
        careHome = new CareHome();
        careHome.initiationCareHome();
        doctorMap = careHome.getDoctorMap();
        nurseMap = careHome.getNurseMap();
        if (modifyAccountType.equals("Nurse")) {
            for (Nurse nurse : nurseMap.values()) {
                if (nurse.getNurseID() == ModifyIDInt) {
                    String result = String.valueOf(nurse.getNurseID());
                    return result;
                }
            }
        } else if (modifyAccountType.equals("Doctor")) {
            for (Doctor doctor : doctorMap.values())
                if (doctor.getDoctorID() == ModifyIDInt) {
                    String result = String.valueOf(doctor.getDoctorID());
                    return result;
                }


        }
        return "No result";
    }

    public String searchByIDForChangeName(String modifyAccountType, String ModifyID) {//search the staff id by id
        int ModifyIDInt = Integer.parseInt(ModifyID);
        careHome = new CareHome();
        careHome.initiationCareHome();
        doctorMap = careHome.getDoctorMap();
        nurseMap = careHome.getNurseMap();
        if (modifyAccountType.equals("Nurse")) {
            for (Nurse nurse : nurseMap.values()) {
                if (nurse.getNurseID() == ModifyIDInt) {
                    String result = String.valueOf(nurse.getNurseName());
                    return result;
                }
            }
        } else if (modifyAccountType.equals("Doctor")) {
            for (Doctor doctor : doctorMap.values())
                if (doctor.getDoctorID() == ModifyIDInt) {
                    String result = String.valueOf(doctor.getDoctorName());
                    return result;
                }


        }
        return "No result";
    }


    public String searchByIDForChangePassword(String modifyAccountType, String ModifyID) {//search the staff id by id
        int ModifyIDInt = Integer.parseInt(ModifyID);
        careHome = new CareHome();
        careHome.initiationCareHome();
        doctorMap = careHome.getDoctorMap();
        nurseMap = careHome.getNurseMap();
        if (modifyAccountType.equals("Nurse")) {
            for (Nurse nurse : nurseMap.values()) {
                if (nurse.getNurseID() == ModifyIDInt) {
                    String result = String.valueOf(nurse.getPassword());
                    return result;
                }
            }
        } else if (modifyAccountType.equals("Doctor")) {
            for (Doctor doctor : doctorMap.values())
                if (doctor.getDoctorID() == ModifyIDInt) {
                    String result = String.valueOf(doctor.getPassword());
                    return result;
                }


        }
        return "No result";
    }

    public ArrayList<String> outPutNurse(String modifyAccountType) {//find all the nurse id
        careHome = new CareHome();
        careHome.initiationCareHome();
        nurseMap = careHome.getNurseMap();
        ArrayList<String> outputNurse = new ArrayList<>();
        if (modifyAccountType.equals("Nurse")) {
            for (Nurse nurse : nurseMap.values()) {
                outputNurse.add(String.valueOf(nurse.getNurseID()));

            }
        }
        return outputNurse;
    }


    public ArrayList<String> outPutDoctor(String modifyAccountType) {//find all the doctor id
        careHome = new CareHome();
        careHome.initiationCareHome();
        doctorMap = careHome.getDoctorMap();
        ArrayList<String> outputDoctor = new ArrayList<>();
        if (modifyAccountType.equals("Doctor")) {
            for (Doctor doctor : doctorMap.values()) {
                outputDoctor.add(String.valueOf(doctor.getDoctorID()));

            }
        }
        return outputDoctor;
    }

    public String discharging(int bedIDInt,int residentID,int prescriptionID) throws SQLException, ClassNotFoundException {
        room = new Room(0);
        careHome = new CareHome();
        room.initiationBed();
        bedMap = room.getBedMap();
        careHome.initiationResident();
        residentMap = careHome.getResidentMap();
        Bed bed = bedMap.get(bedIDInt);
        if (bed.getBedState().equals("empty")){//if the bed is empty you can not discharge

            return "you can not discharging a patient that not exist";
        }else {
            Connection connectionDB = SqlLink.getConnection("CareHomeDB");
            String sql1 = "delete from PRESCRIPTION where PRESCRIPTIONID=?";
            PreparedStatement pst1 = connectionDB.prepareStatement(sql1);
            pst1.setInt(1, prescriptionID);
            pst1.executeUpdate();
            String sql3 = "delete from MEDICINE where PRESCRIPTIONID=?";
            PreparedStatement pst3 = connectionDB.prepareStatement(sql3);
            pst3.setInt(1, prescriptionID);
            pst3.executeUpdate();
            String sql4 = "delete from RESIDENT where RESIDENTID=?";
            PreparedStatement pst4 = connectionDB.prepareStatement(sql4);
            pst4.setInt(1, residentID);
            pst4.executeUpdate();
            String sql = "update BED set  BEDSTATUS='empty', RESIDENTID=null where BEDID=?";
            PreparedStatement pst = connectionDB.prepareStatement(sql);
            pst.setInt(1, bedIDInt);
            pst.executeUpdate();
            Date date=new Date();
            String logDetail = "log detail: " + " manager ID: " + loginID +" patient ID "+residentID+ " discharging "+
                    " time: " + date;
            String sql5 = "insert into LOG (LOGDETAIL,RESIDENTID)values(?,?)";
            PreparedStatement pst5 = connectionDB.prepareStatement(sql5);
            pst5.setString(1, logDetail);
            pst5.setInt(2,residentID);
            pst5.executeUpdate();

            return "you are discharging successful";
        }

    }


    public int getManageID() {
        return manageID;
    }

    public void setManageID(int manageID) {
        this.manageID = manageID;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public void setLog(ArrayList<String> log) {
        this.log = log;
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

    public HashMap<Integer, Nurse> getNurseMap() {
        return nurseMap;
    }

    public void setNurseMap(HashMap<Integer, Nurse> nurseMap) {
        this.nurseMap = nurseMap;
    }

    public HashMap<Integer, Resident> getResidentMap() {
        return residentMap;
    }

    public void setResidentMap(HashMap<Integer, Resident> residentMap) {
        this.residentMap = residentMap;
    }
}