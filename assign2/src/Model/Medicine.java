package Model;

import javafx.beans.property.SimpleStringProperty;

public class Medicine {
    private SimpleStringProperty medicineID;
    private SimpleStringProperty medicineName;
    private SimpleStringProperty medicineDosage;
    private SimpleStringProperty presciptionID;

    public Medicine(String medicineID, String medicineName, String medicineDosage, String presciptionID) {
        this.medicineID = new SimpleStringProperty(medicineID);
        this.medicineName=new SimpleStringProperty(medicineName);
        this.medicineDosage=new SimpleStringProperty(medicineDosage);
        this.presciptionID=new SimpleStringProperty(presciptionID);
    }

    public String getMedicineID() {
        return medicineID.get();
    }

    public SimpleStringProperty medicineIDProperty(){
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID.set(medicineID);
    }

    public String getMedicineName() {
        return medicineName.get();
    }

    public SimpleStringProperty medicineNameProperty() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName.set(medicineName);
    }

    public String getMedicineDosage() {
        return medicineDosage.get();
    }

    public SimpleStringProperty medicineDosageProperty() {
        return medicineDosage;
    }

    public void setMedicineDosage(String medicineDosage) {
        this.medicineDosage.set(medicineDosage);
    }

    public String getPresciptionID() {
        return presciptionID.get();
    }

    public SimpleStringProperty presciptionIDProperty() {
        return presciptionID;
    }

    public void setPresciptionID(String presciptionID) {
        this.presciptionID.set(presciptionID);
    }

}
