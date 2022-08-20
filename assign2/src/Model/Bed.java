package Model;

public class Bed {
       private int bedID;
       private Resident resident;
       private String bedState;
       private int residentID;
    public Bed(int bedID,String bedState,int residentID) {
        this.bedID = bedID;
        this.bedState= bedState;
        this.residentID=residentID;
    }

    public int getBedID() {
        return bedID;
    }

    public void setBedID(int bedID) {
        this.bedID = bedID;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public String getBedState() {
        return bedState;
    }

    public void setBedState(String bedState) {
        this.bedState = bedState;
    }

    public int getResidentID() {
        return residentID;
    }

    public void setResidentID(int residentID) {
        this.residentID = residentID;
    }
}
