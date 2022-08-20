package Model;

import Controller.LoginController;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class CareHome {
    private HashMap<Integer, Ward> wardMap;
    private Resident resident;
    private Prescription prescription;
    private HashMap<Integer, Nurse> nurseMap;
    private HashMap<Integer, Doctor> doctorMap;
    private ArrayList<Log> log;
    private LoginController loginController;
    private Connection connectionDB;
    private HashMap<Integer, Manager> managerMap;
    private HashMap<Integer,Resident> residentMap;
    public static int loginID;
    public static String loginName;
    private ArrayList<String> logDetail;
    private ArrayList<String> patientDetail;

    public CareHome() {
        resident = new Resident(0, null, null, null,0,0);
        nurseMap = new HashMap<>();
        doctorMap = new HashMap<>();
        managerMap = new HashMap<>();
        wardMap = new HashMap<>();
        log = new ArrayList<Log>();
        loginController = new LoginController();
        residentMap =new HashMap<>();
        logDetail=new ArrayList<>();
        patientDetail=new ArrayList<>();
    }


    public void initiationCareHome() {//put the all the doctor nurse manager data into the memory
        try {

            connectionDB = SqlLink.getConnection("CareHomeDB");
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery("select * from DOCTOR");
            while (queryResult.next()) {
                int doctorIDFromDataBase = queryResult.getInt("DOCTORID");
                String doctorNameFromDatabase = queryResult.getString("USERNAME");
                String doctorPasswordFromDatabase = queryResult.getString("PASSWORD");
                doctorMap.put(doctorIDFromDataBase, new Doctor(doctorIDFromDataBase, doctorNameFromDatabase, doctorPasswordFromDatabase));

            }
            ResultSet queryResult2 = statement.executeQuery("select * from NURSE");
            while (queryResult2.next()) {
                int nurseIDFromDataBase = queryResult2.getInt("NURSEID");
                String nurseNameFromDatabase = queryResult2.getString("USERNAME");
                String nursePasswordFromDatabase = queryResult2.getString("PASSWORD");
                nurseMap.put(nurseIDFromDataBase, new Nurse(nurseIDFromDataBase, nurseNameFromDatabase, nursePasswordFromDatabase));

            }
            ResultSet queryResult3 = statement.executeQuery("select * from MANAGER");
            while (queryResult3.next()) {
                int managerIDFromDataBase = queryResult3.getInt("MANAGERID");
                String managerNameFromDatabase = queryResult3.getString("USERNAME");
                String managerPasswordFromDatabase = queryResult3.getString("PASSWORD");
                managerMap.put(managerIDFromDataBase, new Manager(managerIDFromDataBase, managerNameFromDatabase, managerPasswordFromDatabase));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void initiationResident() throws SQLException, ClassNotFoundException {//put all the resident data into the map
        connectionDB = SqlLink.getConnection("CareHomeDB");
        Statement statement = connectionDB.createStatement();
        ResultSet queryResult = statement.executeQuery("select * from RESIDENT");
        while (queryResult.next()) {
            int residentIDFromDataBase = queryResult.getInt("RESIDENTID");
            String residentNameFromDatabase = queryResult.getString("RESIDENTNAME");
            String admittedFromDatabase = queryResult.getString("ADMITTED");
            String dischargedFromDatabase=queryResult.getString("DISCHARGED");
            int prescriptionIDFromDatabase=queryResult.getInt("PRESCRIPTION");
            int bedIDFromDatabase=queryResult.getInt("BEDID");
            residentMap.put(residentIDFromDataBase, new Resident(residentIDFromDataBase, residentNameFromDatabase,
                    admittedFromDatabase,dischargedFromDatabase,prescriptionIDFromDatabase,bedIDFromDatabase));


        }
    }


    public int validateLogin(String usernameTextField, String passwordTextField) {//for check the user can login or not
        initiationCareHome();
        for (Nurse nurse : nurseMap.values()) {
            if (nurse.getNurseName().equals(usernameTextField) && nurse.getPassword().equals(passwordTextField)) {
                loginID= nurse.getNurseID();//record the log id
                loginName=usernameTextField;//record the log name
                return 1;
            }
        }
        for (Doctor doctor : doctorMap.values()) {
            if (doctor.getDoctorName().equals(usernameTextField) && doctor.getPassword().equals(passwordTextField)) {
                loginID= doctor.getDoctorID();//record the log id
                loginName=usernameTextField;//record the log name
                return 2;
            }
        }
        for (Manager manager : managerMap.values()) {
            if (manager.getManagerName().equals(usernameTextField) && manager.getManagerPassword().equals(passwordTextField)) {
                loginID= manager.getManageID();//record the log id
                loginName=usernameTextField;//record the log name
                return 3;
            }
        }


        return 4;
    }

    public int validateSearchSaff(String addID, String addName) {//for search the staff detail for modify
        int addIDInt = Integer.parseInt(addID);
        for (Nurse nurse : nurseMap.values()) {
            if (nurse.getNurseID() == addIDInt || nurse.getNurseName().equals(addName)) {//if name and password is equal
                return 1;
            }
        }
        for (Doctor doctor : doctorMap.values()) {
            if (doctor.getDoctorID() == addIDInt || doctor.getDoctorName().equals(addName)) {//if name and password is equal
                return 2;
            }
        }
        for (Manager manager : managerMap.values()) {
            if (manager.getManageID() == addIDInt || manager.getManagerName().equals(addName)) {//if name and password is equal
                return 3;
            }
        }


        return 4;
    }

    public void initialLog() throws SQLException, ClassNotFoundException {//for put all the log data into the memory
        connectionDB = SqlLink.getConnection("CareHomeDB");
        Statement statement = connectionDB.createStatement();
        ResultSet queryResult = statement.executeQuery("select * from LOG");
        while (queryResult.next()) {
            String logDetailFromDatabase = queryResult.getString("LOGDETAIL");
            int residentIDFromDatabase = queryResult.getInt("RESIDENTID");
            log.add(new Log(logDetailFromDatabase, residentIDFromDatabase));

        }
    }


    public void writeLog() throws FileNotFoundException {//write to the log into the file
        PrintWriter px = new PrintWriter(new FileOutputStream("log.txt", false));
        String content = "";
        for (int i = 0; i < logDetail.size(); i++) {
            if (logDetail.get(i) != null) {

                content += logDetail.get(i) + "\n";
            }
        }
        px.print(content);
        px.flush();
        px.close();
    }
    public void writePatientLog(int residentID) throws FileNotFoundException {//write to the patient detail into the file
        PrintWriter px = new PrintWriter(new FileOutputStream("patientID"+residentID+"log.txt", false));
        String content = "";
        for (int i = 0; i < patientDetail.size(); i++) {
            if (patientDetail.get(i) != null) {

                content += patientDetail.get(i) + "\n";
            }
        }
        px.print(content);
        px.flush();
        px.close();
    }


    public void writeTheLogDetail() throws SQLException, ClassNotFoundException, FileNotFoundException {//put the log detail into array list
        initialLog();
        for(int i=0;i<log.size();i++){
            logDetail.add(log.get(i).getLogDetail());
        }
        writeLog();
        logDetail.clear();
        log.clear();
    }

   public void writeThePatientLog(int residentID) throws SQLException, ClassNotFoundException, FileNotFoundException {//put the patient detail into array list
       initialLog();
       for(int i=0;i<log.size();i++){
           if(log.get(i).getResidentID()==residentID){
               patientDetail.add(log.get(i).getLogDetail());
           }
       }
       writePatientLog(residentID);
       patientDetail.clear();
       log.clear();
   }


    public HashMap<Integer, Ward> getWardMap() {
        return wardMap;
    }

    public void setWardMap(HashMap<Integer, Ward> wardMap) {
        this.wardMap = wardMap;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public HashMap<Integer, Nurse> getNurseMap() {
        return nurseMap;
    }

    public void setNurseMap(HashMap<Integer, Nurse> nurseMap) {
        this.nurseMap = nurseMap;
    }

    public HashMap<Integer, Doctor> getDoctorMap() {
        return doctorMap;
    }

    public HashMap<Integer, Doctor> setDoctorMap(HashMap<Integer, Doctor> doctorMap) {
        doctorMap = doctorMap;
        return doctorMap;
    }

    public HashMap<Integer, Resident> getResidentMap() {
        return residentMap;
    }

    public void setResidentMap(HashMap<Integer, Resident> residentMap) {
        this.residentMap = residentMap;
    }

    public ArrayList<Log> getLog() {
        return log;
    }

    public void setLog(ArrayList<Log> log) {
        this.log = log;
    }
}


