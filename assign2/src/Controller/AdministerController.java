package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import static Model.CareHome.loginID;
import static Model.CareHome.loginName;

public class AdministerController implements Initializable {
    @FXML
    private Button B601;

    @FXML
    private Button B602;

    @FXML
    private Button B603;

    @FXML
    private Button B604;

    @FXML
    private Button B607;

    @FXML
    private Button B605;

    @FXML
    private Button B608;

    @FXML
    private Button B606;

    @FXML
    private Button B609;

    @FXML
    private Button B610;

    @FXML
    private Button B613;

    @FXML
    private Button B611;

    @FXML
    private Button B614;

    @FXML
    private Button B612;

    @FXML
    private Button B615;

    @FXML
    private Button B616;

    @FXML
    private Button B618;

    @FXML
    private Button B617;

    @FXML
    private Button B619;

    @FXML
    private Label checkP;

    @FXML
    private Button B620;

    @FXML
    private Button B621;

    @FXML
    private Button B622;

    @FXML
    private Button B623;

    @FXML
    private Button B624;

    @FXML
    private Button B626;

    @FXML
    private Button B625;

    @FXML
    private Button B627;

    @FXML
    private Button B628;

    @FXML
    private Button B629;

    @FXML
    private Button B630;

    @FXML
    private Button B631;

    @FXML
    private Button B632;

    @FXML
    private Button B633;

    @FXML
    private Button B634;

    @FXML
    private Button B635;

    @FXML
    private Button B636;

    @FXML
    private Button B638;

    @FXML
    private Button B637;

    @FXML
    private Button exitthispage;

    @FXML
    private TableView<Medicine> medcineList;

    @FXML
    private TableColumn<Medicine, String> medicineIDTable;

    @FXML
    private TableColumn<Medicine, String> medicineNameTable;

    @FXML
    private TableColumn<Medicine, String> medicineDosageTable;

    @FXML
    private TextArea prescriptiondetail;

    @FXML
    private TextArea patientdetail;
    @FXML
    private Button givemedicine;

    private Manager manager;
    private Doctor doctor;
    private Nurse nurse;
    private Prescription prescription;
    private Room room;
    private HashMap<Integer, Bed> bedMap;
    private int bedIDInt;
    private int residentID;
    private int prescriptionID;
    private ArrayList<Medicine> medicineFromBack;
    private ArrayList<String> medicineName;
    private ArrayList<String> medicineID;
    private ArrayList<String> medicineDosage;
    private ArrayList<String> medicineDetailArray;
    private String display;
    private String result;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new Manager(0, null, null);
        doctor = new Doctor(0, null, null);
        nurse = new Nurse(0, null, null);
        prescription = new Prescription(0, null, null, null, null,
                0, 0);
        try {
            addBedButtonInMap();//display the bed detail when you open
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        medicineIDTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineIDProperty());//table view for display medicine detail
        medicineNameTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineNameProperty());//table view for display medicine detail
        medicineDosageTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineDosageProperty());//table view for display medicine detail
    }

    public void callsucess() throws IOException {// this method is for call the new window for talk user operation successful
        Stage stageOperation = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
        stageOperation.setTitle("Congratulation");
        Scene scene1 = new Scene(loader.load());
        stageOperation.setScene(scene1);
        stageOperation.show();

    }


    public void bed(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        medcineList.getItems().clear();
        room = new Room(0);
        room.initiationBed();
        bedMap = room.getBedMap();
        String bedID = ((Button) event.getSource()).getId().substring(1, 4);// this is for find specific bed id for bed
        bedIDInt = Integer.parseInt(bedID);
        residentID = bedMap.get(bedIDInt).getResidentID();
        prescriptionID = doctor.searchByResidentID(residentID);//use resident id for search the prescription ID
        display = doctor.searchPrescription(prescriptionID);// use prescription ID for search prescription detail
        result = doctor.checkBedDetail(bedIDInt);// use bed ID to check the bed status
        patientdetail.setText(result);// display the patient detail
        if (display != null) {// if prescription is  exist we will display the prescription detail
            prescriptiondetail.setText(display);
            medicineFromBack = doctor.searchMedicneList(prescriptionID);
            medcineList.getItems().addAll(medicineFromBack);
        } else {
            prescriptiondetail.setText("prescription not exists");// if prescription is not exist we will not display the prescription detail
        }


    }


    public void givemedicine(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        medicineDetailArray = new ArrayList<>();
        Date operationTime = new Date();
        if (display != null) {
            medicineName = doctor.searchMedicneListForName(prescriptionID);// find all the medicine name in the medicine list
            medicineID = doctor.searchMedicneListForID(prescriptionID);// find all the medicine ID in the medicine list
            medicineDosage = doctor.searchMedicneListForDosage(prescriptionID);// find all the medicine dosage in the medicine list
            for (int i = 0; i < medicineName.size(); i++) {
                String medicineDetail = "medicine ID: " + medicineID.get(i) + " medicine Name: " +
                        medicineName.get(i) + " medicine Dosage: " + medicineDosage.get(i);
                medicineDetailArray.add(medicineDetail);
            }
            String medicineForLog = String.join(" / ", medicineDetailArray);//put all the medicine into the a line
            String logDetail = "log detail: " + " administer medicine " + " patient ID: " + residentID + " " + medicineForLog + " time: " + operationTime
                    + " Nurse ID: " + loginID;
            nurse.administerMedicine(logDetail, residentID);//give a log for administer medicine
            callsucess();
            checkP.setText("you are administer a prescription to a patient");
        } else {
            prescriptiondetail.setText("prescription not exists");
            checkP.setText("you can not administer medicine that not exist ");
        }

    }





    public void exitthispag(ActionEvent event) {// for the exit button
        Stage stage = (Stage) exitthispage.getScene().getWindow();
        stage.close();
    }

    public HashMap<Integer, Button> bedButton() {//put all the button in the map for loop the button
        HashMap<Integer, Button> bedButton = new HashMap<>();
        bedButton.put(601, B601);
        bedButton.put(602, B602);
        bedButton.put(603, B603);
        bedButton.put(604, B604);
        bedButton.put(605, B605);
        bedButton.put(606, B606);
        bedButton.put(607, B607);
        bedButton.put(608, B608);
        bedButton.put(609, B609);
        bedButton.put(610, B610);
        bedButton.put(611, B611);
        bedButton.put(612, B612);
        bedButton.put(613, B613);
        bedButton.put(614, B614);
        bedButton.put(615, B615);
        bedButton.put(616, B616);
        bedButton.put(617, B617);
        bedButton.put(618, B618);
        bedButton.put(619, B619);
        bedButton.put(620, B620);
        bedButton.put(621, B621);
        bedButton.put(622, B622);
        bedButton.put(623, B623);
        bedButton.put(624, B624);
        bedButton.put(625, B625);
        bedButton.put(626, B626);
        bedButton.put(627, B627);
        bedButton.put(628, B628);
        bedButton.put(629, B629);
        bedButton.put(630, B630);
        bedButton.put(631, B631);
        bedButton.put(632, B632);
        bedButton.put(633, B633);
        bedButton.put(634, B634);
        bedButton.put(635, B635);
        bedButton.put(636, B636);
        bedButton.put(637, B637);
        bedButton.put(638, B638);
        return bedButton;
    }

    public void addBedButtonInMap() throws SQLException, ClassNotFoundException {//check the bed status in the API
        HashMap<Integer, String> showBedFromModel = manager.checkBedStatus();
        HashMap<Integer, Button> bedButtonShow = bedButton();
        for (int i = 601; i <= 638; i++) {
            if (showBedFromModel.get(i).equals("female")) {
                bedButtonShow.get(i).setStyle("-fx-background-color: #00ffff");

            } else if (showBedFromModel.get(i).equals("male")) {
                bedButtonShow.get(i).setStyle("-fx-background-color: #ff6347");
            }

        }


    }


}
