package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class GiveNewPrescriptionController implements Initializable {

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
    private TextField prescriptionID;

    @FXML
    private TableView<Medicine> medcineList;

    @FXML
    private TextField prescriptionName;

    @FXML
    private TextArea reason;

    @FXML
    private TextField Dosage;

    @FXML
    private ChoiceBox<String> medicineName;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<Medicine, String> medicineIDTable;
    @FXML
    private TableColumn<Medicine, String> medicineNameTable;
    @FXML
    private TableColumn<Medicine, String> medicineDosageTable;
    @FXML
    private Label messageMedicine;
    @FXML
    private Label checkP;

    private Manager manager;

    private Doctor doctor;
    private ObservableList<String> choiceMedicine = FXCollections.observableArrayList();
    private Prescription prescription;
    private Room room;
    private HashMap<Integer,Bed> bedMap;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new Manager(0, null, null);
        doctor = new Doctor(0, null, null);
        prescription = new Prescription(0, null, null, null, null,
                0, 0);
        choiceMedicine.addAll(prescription.getMedicineName());//put all the medicine name in the observable list
        medicineName.setItems(choiceMedicine);//put the list in to the choice box

        try {
            addBedButtonInMap();//check the bed colour
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        medicineIDTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineIDProperty());//for display the medicine in the table view
        medicineNameTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineNameProperty());//for display the medicine in the table view
        medicineDosageTable.setCellValueFactory(cellDate -> cellDate.getValue().medicineDosageProperty());//for display the medicine in the table view
    }


    public void input() throws CanNotNullException {//check the input validation
        if(medicineName.getValue()==null||prescriptionID.getText().isBlank()||prescriptionName.getText().isBlank()||
        reason.getText().isBlank()||Dosage.getText().isBlank()||medcineList.getItems().isEmpty()){
            throw new CanNotNullException();
        }
    }

    public void input1() throws CanNotNullException {//check the input validation
        if(medicineName.getValue()==null||Dosage.getText().isBlank()){
            throw new CanNotNullException();
        }


    }
    public void input2() throws CanNotNullException {//check the input validation
        if(medcineList.getSelectionModel().getSelectedItem()==null){
            throw new CanNotNullException();
        }


    }


    public void callsucess() throws IOException {//call the successful
        Stage stageOperation = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
        stageOperation.setTitle("Congratulation");
        Scene scene1 = new Scene(loader.load());
        stageOperation.setScene(scene1);
        stageOperation.show();

    }

    public void addButton(ActionEvent event) {//this method is for add the new medicine in the table view
        try {
            input1();//check the input
        } catch (CanNotNullException e) {
            checkP.setText("please enter medicine detail");
            return;
        }
        String ID = prescription.fineID(medicineName.getValue());
        for(Medicine a:medcineList.getItems()){
          if(a.medicineNameProperty().get().equals(medicineName.getValue())){//loop the all the medicine to check medicine
              messageMedicine.setText("you can not add same medicine");
              return;
          }

      }
        medcineList.getItems().add(new Medicine(ID, medicineName.getValue(), Dosage.getText(), prescriptionID.getText()));//add the medicine in the table view
        messageMedicine.setText("");
    }

    public void deleteButton(ActionEvent event) {
        try {
            input2();//check the input validation
        } catch (CanNotNullException e) {
            checkP.setText("please choice medicine detail");
            return;
        }
        medcineList.getItems().remove(medcineList.getSelectionModel().getSelectedItem());//remove the selection that you remove
    }


    public void bed(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            input();//check the input
        } catch (CanNotNullException e) {
            checkP.setText("please fill prescription detail");
            return;
        }
        HashMap<Integer, Button> bedButtonShow = bedButton();
        room = new Room(0);
        room.initiationBed();
        bedMap = room.getBedMap();
        String bedID = ((Button) event.getSource()).getId().substring(1, 4);//get the bed ID in the pane
        int bedIDInt = Integer.parseInt(bedID);
        HashMap<Integer, String> showBedFromModel = manager.checkBedStatus();//to get the all the bed detail in the HashMap
        int residentID=bedMap.get(bedIDInt).getResidentID();
        if (showBedFromModel.get(bedIDInt).equals("empty")) {//if bed is empty we can not give a prescription to a empty bed
            checkP.setText("we can not give a prescription to a empty bed");

        } else if (!showBedFromModel.get(bedIDInt).equals("empty")) {//if bed is not empty you can add prescription
             ArrayList <Medicine> getMedicineFromList=outPutTableview();
            String result=doctor.addPrescription(getMedicineFromList,prescriptionID.getText(),prescriptionName.getText(),
                    reason.getText(),loginID,residentID);//call the method in the model that can add prescription

            if(result.equals("one resident can have one prescription")){
                checkP.setText(result);
            }else if(result.equals("we can not have  same prescription ID")) {
                checkP.setText(result);
            }else if(result.equals("you are successful add a new \nprescription to a patient")){
                checkP.setText(result);
                medcineList.getItems().clear();
                callsucess();
            }

        }

    }

    public ArrayList<Medicine> outPutTableview(){//get all the medicine list in the table view
        ArrayList<Medicine> createAPrescription =new ArrayList<>();
        for(Medicine medicine:medcineList.getItems()){
            createAPrescription.add(medicine);

        }
        return createAPrescription;
    }





    public void exitthispag(ActionEvent event) {//for exit the page
        Stage stage = (Stage) exitthispage.getScene().getWindow();
        stage.close();
    }


    public HashMap<Integer, Button> bedButton() {//put all the button in the HashMap
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
                bedButtonShow.get(i).setStyle("-fx-background-color: #00ffff");

            } else if (showBedFromModel.get(i).equals("male")) {
                bedButtonShow.get(i).setStyle("-fx-background-color: #ff6347");
            }

        }


    }


}
