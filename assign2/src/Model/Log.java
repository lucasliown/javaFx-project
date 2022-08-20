package Model;

public class Log {
    private String logDetail;
    private int residentID;

    public Log( String logDetail, int residentID) {
        this.logDetail = logDetail;
        this.residentID = residentID;
    }


    public String getLogDetail() {
        return logDetail;
    }

    public void setLogDetail(String logDetail) {
        this.logDetail = logDetail;
    }

    public int getResidentID() {
        return residentID;
    }

    public void setResidentID(int residentID) {
        this.residentID = residentID;
    }
}
