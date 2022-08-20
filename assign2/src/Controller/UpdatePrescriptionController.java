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


public class UpdatePrescriptionController implements Initializable {
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
    private TextField Dosage;

    @FXML
    private ChoiceBox<String> medicineName;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label messageMedicine;

    @FXML
    private TextArea prescriptiondetail;

    @FXML
    private Button confirm;
    private Manager manager;
    private Doctor doctor;
    private Nurse nurse;
    private ObservableList<String> choiceMedicine = FXCollections.observableArrayList();
    private Prescription prescription;
    @FXML
    private Label messagedispaly;
    private Room room;
    private HashMap<Integer, Bed> bedMap;
    private ArrayList<Medicine> medicineFromBack;
    private int bedIDInt;
    private int residentID;
    private int prescriptionID;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new Manager(0, null, null);
        doctor = new Doctor(0, null, null);
        nurse = new Nurse(0, null, null);
        prescription = new Prescription(0, null, null, null, null,
                0, 0);
        try {
            addBedButtonInMap();//check the bed colour
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        choiceMedicine.addAll(prescription.getMedicineName());//put all the medicine name in the choice box
        medicineName.setItems(choiceMedicine);//put all the medicine name in the choice box
        medicineIDTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineIDProperty());//put the medicine in the table view
        medicineNameTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineNameProperty());//put the medicine in the table view
        medicineDosageTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineDosageProperty());//put the medicine in the table view

    }


    public void callsucess() throws IOException {//call the successful method
        Stage stageOperation = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
        stageOperation.setTitle("Congratulation");
        Scene scene1 = new Scene(loader.load());
        stageOperation.setScene(scene1);
        stageOperation.show();

    }

    public void input1() throws CanNotNullException {//check the input validation
        if (medicineName.getValue() == null || Dosage.getText().isBlank()) {
            throw new CanNotNullException();
        }


    }

    public void input2() throws CanNotNullException {//check the input validation
        if (medcineList.getSelectionModel().getSelectedItem() == null) {
            throw new CanNotNullException();
        }


    }


    public void bed(ActionEvent event) throws SQLException, ClassNotFoundException {//this method is for display the prescription detail
        medcineList.getItems().clear();
        room = new Room(0);
        room.initiationBed();
        bedMap = room.getBedMap();
        String bedID = ((Button) event.getSource()).getId().substring(1, 4);
        bedIDInt = Integer.parseInt(bedID);
        residentID = bedMap.get(bedIDInt).getResidentID();
        prescriptionID = doctor.searchByResidentID(residentID);
        String display = doctor.searchPrescription(prescriptionID);
        if (display != null) {
            prescriptiondetail.setText(display);
            medicineFromBack = doctor.searchMedicneList(prescriptionID);
            medcineList.getItems().addAll(medicineFromBack);
        } else {
            prescriptiondetail.setText("prescription not exists");
        }
    }

    public void exitthispag(ActionEvent event) {//for exit the page
        Stage stage = (Stage) exitthispage.getScene().getWindow();
        stage.close();
    }

    public void addButton(ActionEvent event) {//this method is for add the new medicine in the list
        try {
            input1();//check the input validation
        } catch (CanNotNullException e) {
            messagedispaly.setText("please enter medicine detail");
            return;
        }
        String ID = prescription.fineID(medicineName.getValue());
        for (Medicine a : medcineList.getItems()) {
            if (a.medicineNameProperty().get().equals(medicineName.getValue())) {//can not add the medicine that have the same name
                messagedispaly.setText("you can not add same medicine");
                return;
            }

        }
        medcineList.getItems().add(new Medicine(ID, medicineName.getValue(), Dosage.getText(), null));
        messagedispaly.setText("");
    }


    public void deleteButton(ActionEvent event) {//this method is for delete the medicine
        try {
            input2();//check the input
        } catch (CanNotNullException e) {
            messagedispaly.setText("please choice medicine detail");
            return;
        }
        medcineList.getItems().remove(medcineList.getSelectionModel().getSelectedItem());

    }

    public ArrayList<Medicine> outPutTableview() {//get the medicine in the table view
        ArrayList<Medicine> createAPrescription = new ArrayList<>();
        for (Medicine medicine : medcineList.getItems()) {
            createAPrescription.add(medicine);

        }
        return createAPrescription;
    }


    public void confirm(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {//this method is for confirm the prescription that you update
        ArrayList<Medicine> medicineModify = outPutTableview();
        String display = doctor.searchPrescription(prescriptionID);
        if (display != null) {//if the prescription is not the null you can modify
            String result = doctor.ModifyMedicine(medicineModify, prescriptionID, loginID);
            if (result.equals("you do not have authority to modify \nother doctor's prescription")) {
                checkP.setText(result);
                prescriptiondetail.clear();
                medcineList.getItems().clear();
                medicineFromBack.clear();

            } else if (result.equals("you are successful update \nprescription to a patient")) {
                checkP.setText(result);
                prescriptiondetail.clear();
                medcineList.getItems().clear();
                callsucess();
                medicineFromBack.clear();
            }else if(result.equals("prescription deleted")){
                checkP.setText(result);
                prescriptiondetail.clear();
                medcineList.getItems().clear();
                callsucess();
                medicineFromBack.clear();
            }
        } else {
            checkP.setText("you can not modify a prescription that\n doesn't exist");

        }

    }


    public HashMap<Integer, Button> bedButton() {//put all the button into HashMap
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

    public void addBedButtonInMap() throws SQLException, ClassNotFoundException {//for check the bed status
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
