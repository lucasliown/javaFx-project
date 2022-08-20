package Controller;

import javafx.event.ActionEvent;
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

public class DischargingController implements Initializable {
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
    private Manager manager;
    private Room room;
    private HashMap<Integer, Bed> bedMap;
    private int bedIDInt;
    private int residentID;
    private int prescriptionID;
    private Doctor doctor;
    private CareHome careHome;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new Manager(0, null, null);
        doctor = new Doctor(0, null, null);
        careHome=new CareHome();
        try {
            addBedButtonInMap();//check the bed colour
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void callsucess() throws IOException {//for the successful operation
        Stage stageOperation = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
        stageOperation.setTitle("Congratulation");
        Scene scene1 = new Scene(loader.load());
        stageOperation.setScene(scene1);
        stageOperation.show();

    }

    public void bed(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {//click on the bed can discharge the patient
        room = new Room(0);
        room.initiationBed();
        bedMap = room.getBedMap();
        String bedID = ((Button) event.getSource()).getId().substring(1, 4);//for get the bed id in the pane
        bedIDInt = Integer.parseInt(bedID);
        residentID = bedMap.get(bedIDInt).getResidentID();
        prescriptionID = doctor.searchByResidentID(residentID);
        String result=manager.discharging(bedIDInt,residentID,prescriptionID);//call the method in the manager can discharge the patient
        if(result.equals("you can not discharging a patient that not exist")){//if the patient not exist you can not delete the patient
            checkP.setText("you can not discharging a patient that not exist");
        }else if(result.equals("you are discharging successful")) {//if the patient exist you can  delete the patient
            ((Button) event.getSource()).setStyle("");
            careHome.writeThePatientLog(residentID);
            checkP.setText("discharging patient successful");
            callsucess();
        }
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

    public void addBedButtonInMap() throws SQLException, ClassNotFoundException {//for display the colour
        HashMap<Integer, String> showBedFromModel = manager.checkBedStatus();
        HashMap<Integer, Button> bedButtonShow = bedButton();
        for (int i = 601; i <= 638; i++) {
            if (showBedFromModel.get(i).equals("female")) {
                bedButtonShow.get(i).setStyle("-fx-background-color: #00ffff");//if the patient is female can display the blue

            } else if (showBedFromModel.get(i).equals("male")) {
                bedButtonShow.get(i).setStyle("-fx-background-color: #ff6347");//if the patient is female can display the red
            }

        }


    }



}
