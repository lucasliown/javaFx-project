package Model;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static Model.CareHome.loginID;


public class Nurse extends Staff {
    private int nurseID;
    private String nurseName;
    private String password;
    private HashMap<Integer, Nurse> nurseMap;
    private HashMap<Integer, Ward> wardMap;
    private ArrayList<Shift> nurseShift;
    private ArrayList<String> log;
    private Shift shift;
    private CareHome careHome;
    private Room room;
    private HashMap<Integer, Resident> residentMap;
    private HashMap<Integer, Bed> bedMap;
    private Doctor doctor;
    private ArrayList<Shift> doctorShift;

    public Nurse(int nurseID, String nurseName, String password) {
        super(nurseID, nurseName, password);
        this.nurseID = nurseID;
        this.nurseName = nurseName;
        this.password = password;
        nurseShift = new ArrayList<>();
        log = new ArrayList<String>();
        initiationNurseShift();
        room = new Room(0);
        bedMap = new HashMap<>();
        doctorShift=new ArrayList<>();
    }


    public void initiationNurseShift() {//put all the data into the HashMap

        try {

            Connection connectionDB = SqlLink.getConnection("CareHomeDB");
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery("select * from NURSESHIFT");
            while (queryResult.next()) {
                String shiftIDFromDataBase = queryResult.getString("SHIFTID");
                String shiftNameFromDatabase = queryResult.getString("SHIFTNAME");
                int staffIDFromDatabase = queryResult.getInt("STAFFID");
                shift = new Shift(shiftIDFromDataBase, shiftNameFromDatabase, staffIDFromDatabase);
                nurseShift.add(shift);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public String findShiftID(String nameList) {//use the shift name to find the shift id
        String shiftID = null;
        for (int i = 0; i < nurseShift.size(); i++) {
            if (nameList.equals(nurseShift.get(i).getShiftName())) {
                shiftID = nurseShift.get(i).getShiftID();
                return shiftID;
            }

        }
        return null;
    }

    public ArrayList<String> findShiftByStaffID(String StaffID) {//use shift id to find the shiftName
        ArrayList<String> nameDispaly = new ArrayList<>();
        int StaffIDInt = Integer.parseInt(StaffID);
        for (int i = 0; i < nurseShift.size(); i++) {
            if (StaffIDInt == nurseShift.get(i).getStaffID()) {
                nameDispaly.add(nurseShift.get(i).getShiftName());
            }

        }
        return nameDispaly;
    }


    public String addShift(String nurseID, String nameList) throws exceedWorkHourException, SQLException, ClassNotFoundException {
        doctor=new Doctor(0,null,null);
        String result = null;
        careHome = new CareHome();
        careHome.initiationCareHome();
        nurseMap = careHome.getNurseMap();
        doctorShift=doctor.getDoctorShift();
        int nurseIDInt = Integer.parseInt(nurseID);
        for (int i = 0; i < nurseShift.size(); i++) {
            if (nurseIDInt == nurseShift.get(i).getStaffID()) {//if the nurse have another shift in a day you can not add more shift
                if (nameList.substring(0, 3).equals(nurseShift.get(i).getShiftName().substring(0, 3))) {
                    throw new exceedWorkHourException();
                }

            }
        }
        for (int i = 0; i <  doctorShift.size(); i++) {//you can not add the doctor shift into nurse
            if ( doctorShift.get(i).getShiftName().equals(nameList)){
                return "we can not add doctor shift to the nurse";
            }
        }

        String ID = findShiftID(nameList);
        nurseShift.add(new Shift(ID, nameList, nurseIDInt));
        Connection connectionDB = SqlLink.getConnection("CareHomeDB");
        String sql = "insert into NURSESHIFT (SHIFTID,SHIFTNAME,STAFFID)values(?,?,?)";//link to nurseshift and add the shift
        PreparedStatement pst = connectionDB.prepareStatement(sql);
        pst.setString(1, ID);
        pst.setString(2, nameList);
        pst.setInt(3, nurseIDInt);
        pst.executeUpdate();
        Date date = new Date();
        String logDetail = "log detail: " + "manager ID: " + loginID + " add Nurse shift " + " time: " + date + " Nurse ID: " + nurseIDInt +
                " Shift Detail: " + nameList;
        String sql2 = "insert into LOG (LOGDETAIL)values(?)";//link the log
        PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
        pst2.setString(1, logDetail);
        pst2.executeUpdate();
        result = "add shift successfully";

        return result;

    }


    public String deleteShift(String nurseID, String nurseName) throws exceedWorkHourException, SQLException, ClassNotFoundException {
        int nurseIDInt = Integer.parseInt(nurseID);
        String result = null;
        for (int i = 0; i < nurseShift.size(); i++) {
            if (nurseIDInt == nurseShift.get(i).getStaffID()) {
                if (nurseName.equals(nurseShift.get(i).getShiftName())) {
                    nurseShift.remove(i);//remove the shift in the map
                    Connection connectionDB = SqlLink.getConnection("CareHomeDB");
                    String sql = "delete from NURSESHIFT where STAFFID=? and SHIFTNAME=?";//link to the database
                    PreparedStatement pst = connectionDB.prepareStatement(sql);
                    pst.setInt(1, nurseIDInt);
                    pst.setString(2, nurseName);
                    pst.executeUpdate();
                    Date date=new Date();
                    String logDetail = "log detail: " + "manager ID: " + loginID + " delete Nurse shift " + " time: " + date + " Nurse ID: " + nurseIDInt +
                            " Shift Detail: " + nurseName;
                    String sql2 = "insert into LOG (LOGDETAIL)values(?)";//link to the log
                    PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
                    pst2.setString(1, logDetail);
                    pst2.executeUpdate();
                    result = "you delete shift:\n" + nurseName;
                    return result;
                }

            }

        }
        result = "The Shift not exist";
        return result;
    }


    public void administerMedicine(String logDetail, int residentID) throws SQLException, ClassNotFoundException {//link to the database and record the administer
        Connection connectionDB = SqlLink.getConnection("CareHomeDB");
        String sql = "insert into LOG (LOGDETAIL,RESIDENTID)values(?,?)";
        PreparedStatement pst = connectionDB.prepareStatement(sql);
        pst.setString(1, logDetail);
        pst.setInt(2, residentID);
        pst.executeUpdate();
    }


    public int getNurseID() {
        return nurseID;
    }

    public void setNurseID(int nurseID) {
        this.nurseID = nurseID;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<Integer, Nurse> getNurseMap() {
        return nurseMap;
    }

    public void setNurseMap(HashMap<Integer, Nurse> nurseMap) {
        this.nurseMap = nurseMap;
    }

    public HashMap<Integer, Ward> getWardMap() {
        return wardMap;
    }

    public void setWardMap(HashMap<Integer, Ward> wardMap) {
        this.wardMap = wardMap;
    }


    public String changeResidentToBed(int moveID, int residentID, String bedStatus, int bedIDInt) throws SQLException, ClassNotFoundException {
        careHome = new CareHome();
        careHome.initiationResident();
        residentMap = careHome.getResidentMap();
        room.initiationBed();
        bedMap = room.getBedMap();
        if (bedMap.get(bedIDInt).getBedState().equals("empty") && bedMap.get(bedIDInt).getBedID() ==
                bedIDInt) {
            if (bedStatus.equals("female")) {
                bedMap.get(moveID).setBedState("empty");
                bedMap.get(moveID).setResidentID(0);
                bedMap.get(bedIDInt).setBedState("female");
                bedMap.get(bedIDInt).setResidentID(residentID);
                residentMap.get(residentID).setBedID(bedIDInt);
                Connection connectionDB = SqlLink.getConnection("CareHomeDB");
                String sql = "update BED set  BEDSTATUS='empty', RESIDENTID=null where BEDID=?";
                PreparedStatement pst = connectionDB.prepareStatement(sql);
                pst.setInt(1, moveID);
                pst.executeUpdate();
                String sql1 = "update BED set  BEDSTATUS=?, RESIDENTID=? where BEDID=?";
                PreparedStatement pst1 = connectionDB.prepareStatement(sql1);
                pst1.setString(1, "female");
                pst1.setInt(2, residentID);
                pst1.setInt(3, bedIDInt);
                pst1.executeUpdate();
                String sql2 = "update RESIDENT set  BEDID=? where RESIDENTID=?";
                PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
                pst2.setInt(1, bedIDInt);
                pst2.setInt(2, residentID);
                pst2.executeUpdate();
                Date date=new Date();
                String logDetail = "log detail: " + " Nurse ID: " + loginID +" patient ID "+residentID+ " move patient "+
                       " from bed ID :"+moveID + " to "+" bed ID: "+bedIDInt+" time: " + date;
                String sql4 = "insert into LOG (LOGDETAIL,RESIDENTID)values(?,?)";
                PreparedStatement pst4 = connectionDB.prepareStatement(sql4);
                pst4.setString(1, logDetail);
                pst4.setInt(2,residentID);
                pst4.executeUpdate();


            } else if (bedStatus.equals("male")) {
                bedMap.get(moveID).setBedState("empty");
                bedMap.get(moveID).setResidentID(0);
                bedMap.get(bedIDInt).setBedState("male");
                bedMap.get(bedIDInt).setResidentID(residentID);
                residentMap.get(residentID).setBedID(bedIDInt);
                Connection connectionDB = SqlLink.getConnection("CareHomeDB");
                String sql = "update BED set  BEDSTATUS='empty', RESIDENTID=null where BEDID=?";
                PreparedStatement pst = connectionDB.prepareStatement(sql);
                pst.setInt(1, moveID);
                pst.executeUpdate();
                String sql1 = "update BED set  BEDSTATUS=?, RESIDENTID=? where BEDID=?";
                PreparedStatement pst1 = connectionDB.prepareStatement(sql1);
                pst1.setString(1, "male");
                pst1.setInt(2, residentID);
                pst1.setInt(3, bedIDInt);
                pst1.executeUpdate();
                String sql2 = "update RESIDENT set  BEDID=? where RESIDENTID=?";
                PreparedStatement pst2 = connectionDB.prepareStatement(sql2);
                pst2.setInt(1, bedIDInt);
                pst2.setInt(2, residentID);
                pst2.executeUpdate();
                Date date=new Date();
                String logDetail = "log detail: " + " Nurse ID: " + loginID +" patient ID "+residentID+ " move patient "+
                        " from bed ID :"+moveID + " to "+" bed ID: "+bedIDInt+" time: " + date;
                String sql4 = "insert into LOG (LOGDETAIL,RESIDENTID)values(?,?)";
                PreparedStatement pst4 = connectionDB.prepareStatement(sql4);
                pst4.setString(1, logDetail);
                pst4.setInt(2,residentID);
                pst4.executeUpdate();
            }

        }
        return null;
    }


    public ArrayList<String> getLog() {
        return log;
    }

    public void setLog(ArrayList<String> log) {
        this.log = log;
    }

    public ArrayList<Shift> getNurseShift() {
        return nurseShift;
    }

    public void setNurseShift(ArrayList<Shift> nurseShift) {
        this.nurseShift = nurseShift;
    }
}
