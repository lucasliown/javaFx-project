package Model;

public class Shift {
    private int StaffID;
    private String shiftID;
    private String shiftName;
    private int time;

    public Shift(String shiftID, String shiftName, int StaffID) {
        this.shiftID = shiftID;
        this.shiftName = shiftName;
        this.StaffID = StaffID;
    }



    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int staffID) {
        StaffID = staffID;
    }

    public String getShiftID() {
        return shiftID;
    }

    public void setShiftID(String shiftID) {
        this.shiftID = shiftID;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }




}
