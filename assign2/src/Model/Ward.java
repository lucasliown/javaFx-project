package Model;

import java.util.HashMap;

public class Ward {
    private int wardID;
    private HashMap<Integer,Room> roomMap;

    public Ward(int wardID) {
        this.wardID = wardID;
        roomMap = new HashMap<>();
    }

    public int getWardID() {
        return wardID;
    }

    public void setWardID(int wardID) {
        this.wardID = wardID;
    }

    public HashMap<Integer, Room> getRoomMap() {
        return roomMap;
    }

    public void setRoomMap(HashMap<Integer, Room> roomMap) {
        this.roomMap = roomMap;
    }

}
