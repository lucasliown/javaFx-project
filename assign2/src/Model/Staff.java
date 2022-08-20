package Model;

public abstract class Staff {
    private String name;
    private int staffID;
    private String password;
    private Shift shift;

    public  Staff(int staffID, String name,String password) {
        this.name = name;
        this.staffID = staffID;
        this.password=password;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }
}
