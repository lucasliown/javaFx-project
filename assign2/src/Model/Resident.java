package Model;

public class Resident {
     private  Prescription prescription;
     private  String name;
     private  int residentID;
     private  String admitted;
     private  String discharged;
     private  int prescriptionID;
     private int bedID;


     public Resident(int residentID, String name, String admitted, String discharged,int prescriptionID,int bedID) {
          this.bedID=bedID;
          this.residentID = residentID;
          this.name = name;
          this.admitted =admitted;
          this.discharged=discharged;
          this.prescriptionID=prescriptionID;
     }

     public Prescription getPrescription() {
          return prescription;
     }

     public void setPrescription(Prescription prescription) {
          this.prescription = prescription;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public int getResidentID() {
          return residentID;
     }

     public void setResidentID(int residentID) {
          this.residentID = residentID;
     }

     public String getAdmitted() {
          return admitted;
     }

     public void setAdmitted(String admitted) {
          this.admitted = admitted;
     }

     public String getDischarged() {
          return discharged;
     }

     public void setDischarged(String discharged) {
          this.discharged = discharged;
     }

     public int getPrescriptionID() {
          return prescriptionID;
     }

     public void setPrescriptionID(int prescriptionID) {
          this.prescriptionID = prescriptionID;
     }

     public int getBedID() {
          return bedID;
     }

     public void setBedID(int bedID) {
          this.bedID = bedID;
     }

}
