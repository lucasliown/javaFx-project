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
import java.util.HashMap;
import java.util.ResourceBundle;

import static Model.CareHome.loginID;
import static Model.CareHome.loginName;
public class CheckBedDetilController implements Initializable{
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

    public void initialize(URL url, ResourceBundle resourceBundle){
        manager = new Manager(0, null, null);
        doctor = new Doctor(0, null, null);
        nurse = new Nurse(0, null, null);
        prescription = new Prescription(0, null, null, null, null,
                0, 0);
        try {
            addBedButtonInMap();//for check the bed colour
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        medicineIDTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineIDProperty());//for display the medicine in the table
        medicineNameTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineNameProperty());//for display the medicine in the table
        medicineDosageTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineDosageProperty());//for display the medicine in the table
    }



    public void bed(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {//for check the detail of bed
        medcineList.getItems().clear();
        room = new Room(0);
        room.initiationBed();
        bedMap = room.getBedMap();
        String bedID = ((Button) event.getSource()).getId().substring(1, 4);
        bedIDInt = Integer.parseInt(bedID);
        residentID = bedMap.get(bedIDInt).getResidentID();
        prescriptionID = doctor.searchByResidentID(residentID);//use resident id for search the prescription ID
        String display = doctor.searchPrescription(prescriptionID);// use prescription ID for search prescription detail
        String result = doctor.checkBedDetail(bedIDInt);// use bed ID to check the bed status
        patientdetail.setText(result);
        if (display != null) {// if prescription is  exist we will display the prescription detail
            prescriptiondetail.setText(display);
            medicineFromBack = doctor.searchMedicneList(prescriptionID);//for display the medicine in the table view
            medcineList.getItems().addAll(medicineFromBack);
        } else {
            prescriptiondetail.setText("prescription not exists");
        }


    }



    public void exitthispag(ActionEvent event) {//for exit the page
        Stage stage = (Stage) exitthispage.getScene().getWindow();
        stage.close();
    }

    public HashMap<Integer, Button> bedButton() {//put all the button in the hashMap
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

    public void addBedButtonInMap() throws SQLException, ClassNotFoundException {//for check the bed colour
        HashMap<Integer, String> showBedFromModel = manager.checkBedStatus();
        HashMap<Integer, Button> bedButtonShow = bedButton();
        for (int i = 601; i <= 638; i++) {
            if (showBedFromModel.get(i).equals("female")) {
                bedButtonShow.get(i).setStyle("-fx-background-color: #00ffff");//if the patient is female then change the colour to the blue

            } else if (showBedFromModel.get(i).equals("male")) {
                bedButtonShow.get(i).setStyle("-fx-background-color: #ff6347");//if the patient is male then change the colour to the red
            }

        }


    }



}
