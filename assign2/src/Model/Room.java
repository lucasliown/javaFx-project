package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


public class Room {
    private int roomID;
    private HashMap<Integer, Bed> bedMap;


    public Room(int roomID) {
        this.roomID = roomID;


//        if (roomID == 1) {
//            bedMap.put(1, new Bed(1,BedState.empty));
//        } else if (roomID == 2) {
//            bedMap.put(2, new Bed(2,BedState.empty));
//            bedMap.put(3, new Bed(3,BedState.empty));
//        } else if (roomID == 3) {
//            bedMap.put(4, new Bed(4,BedState.empty));
//            bedMap.put(5, new Bed(5,BedState.empty));
//            bedMap.put(6, new Bed(6,BedState.empty));
//            bedMap.put(7, new Bed(7,BedState.empty));
//        } else if (roomID == 4) {
//            bedMap.put(8, new Bed(8,BedState.empty));
//            bedMap.put(9, new Bed(9,BedState.empty));
//            bedMap.put(10, new Bed(10,BedState.empty));
//            bedMap.put(11, new Bed(11,BedState.empty));
//        } else if (roomID == 5) {
//            bedMap.put(12, new Bed(12,BedState.empty));
//            bedMap.put(13, new Bed(13,BedState.empty));
//            bedMap.put(14, new Bed(14,BedState.empty));
//            bedMap.put(15, new Bed(15,BedState.empty));
//        } else if (roomID == 6) {
//            bedMap.put(16, new Bed(16,BedState.empty));
//            bedMap.put(17, new Bed(17,BedState.empty));
//            bedMap.put(18, new Bed(18,BedState.empty));
//            bedMap.put(19, new Bed(19,BedState.empty));
//
//        } else if (roomID == 7) {
//            bedMap.put(20, new Bed(20,BedState.empty));
//        } else if (roomID == 8) {
//            bedMap.put(21, new Bed(21,BedState.empty));
//            bedMap.put(22, new Bed(22,BedState.empty));
//        } else if (roomID == 9) {
//            bedMap.put(23, new Bed(23,BedState.empty));
//            bedMap.put(24, new Bed(24,BedState.empty));
//            bedMap.put(25, new Bed(25,BedState.empty));
//            bedMap.put(26, new Bed(26,BedState.empty));
//        } else if (roomID == 10) {
//            bedMap.put(27, new Bed(27,BedState.empty));
//            bedMap.put(28, new Bed(28,BedState.empty));
//            bedMap.put(29, new Bed(29,BedState.empty));
//            bedMap.put(30, new Bed(30,BedState.empty));
//        } else if (roomID == 11) {
//            bedMap.put(31, new Bed(31,BedState.empty));
//            bedMap.put(32, new Bed(32,BedState.empty));
//            bedMap.put(33, new Bed(33,BedState.empty));
//            bedMap.put(34, new Bed(34,BedState.empty));
//        } else if (roomID == 12) {
//            bedMap.put(35, new Bed(35,BedState.empty));
//            bedMap.put(36, new Bed(36,BedState.empty));
//            bedMap.put(37, new Bed(37,BedState.empty));
//            bedMap.put(38, new Bed(38,BedState.empty));
//        }
//

    }

    public void initiationBed() throws SQLException, ClassNotFoundException {
        bedMap = new HashMap<>();
        Connection connectionDB = SqlLink.getConnection("CareHomeDB");
        Statement statement = connectionDB.createStatement();
        ResultSet queryResult = statement.executeQuery("select * from BED");
        while (queryResult.next()) {
            int BedIDFromDataBase = queryResult.getInt("BEDID");
            String bedStatusFromDatabase = queryResult.getString("BEDSTATUS");
            int residentIDFromDtabase = queryResult.getInt("RESIDENTID");
            bedMap.put(BedIDFromDataBase, new Bed(BedIDFromDataBase, bedStatusFromDatabase, residentIDFromDtabase));

        }
    }


    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public HashMap<Integer, Bed> getBedMap() {
        return bedMap;
    }

    public void setBedMap(HashMap<Integer, Bed> bedMap) {
        this.bedMap = bedMap;
    }
}