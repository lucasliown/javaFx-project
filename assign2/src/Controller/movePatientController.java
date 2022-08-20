package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
import java.util.HashMap;
import java.util.ResourceBundle;

public class movePatientController implements Initializable {
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
    private Label moveDetail;

    @FXML
    private Label message;
    private HashMap<Integer, Integer> getMoveDetail;
    ;
    private Manager manager;
    private ObservableMap<Integer, String> bedShow = FXCollections.observableHashMap();
    private Doctor doctor;
    private Room room;
    private HashMap<Integer, Bed> bedMap;
    private HashMap<Integer, String> getbedStatus;
    private Nurse nurse;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        getMoveDetail = new HashMap<>();//to store the bed detail in the first time
        getbedStatus = new HashMap<>();//to store the bed detail in the first time
        manager = new Manager(0, null, null);
        doctor = new Doctor(0, null, null);
        nurse = new Nurse(0, null, null);
        try {
            addBedButtonInMap();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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


    public void bed(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {//this method is for move patient
        HashMap<Integer, Button> bedButtonShow = bedButton();
        room = new Room(0);
        room.initiationBed();
        bedMap = room.getBedMap();
        String bedID = ((Button) event.getSource()).getId().substring(1, 4);
        int bedIDInt = Integer.parseInt(bedID);
        HashMap<Integer, String> showBedFromModel = manager.checkBedStatus();
        String result = doctor.checkBedDetail(bedIDInt);
        message.setText(result);
        if (showBedFromModel.get(bedIDInt).equals("empty")) {//if bed is empty we have a lot of situation
            if (getMoveDetail.isEmpty()) {
                moveDetail.setText("We can not move empty bed");

            } else {
                for (int i = 601; i <= 638; i++) {
                    int changeIDInt = Integer.parseInt(bedButtonShow.get(i).getId().substring(1, 4));
                    if (getMoveDetail.containsKey(changeIDInt)) {
                        bedButtonShow.get(i).setStyle("");
                    }

                }
                for (int i = 601; i <= 638; i++) {
                    if (getbedStatus.get(i) != null) {
                        if (getbedStatus.get(i).equals("female")) {
                            ((Button) event.getSource()).setStyle("-fx-background-color: #00ffff");

                            for (Integer moveID : getMoveDetail.keySet()) {
                                nurse.changeResidentToBed(moveID, getMoveDetail.get(i), getbedStatus.get(i), bedIDInt);
                            }
                            getMoveDetail.clear();
                            getbedStatus.clear();
                            String result2 = doctor.checkBedDetail(bedIDInt);
                            message.setText(result2);
                            moveDetail.setText("move patient successful");
                            callsucess();

                        } else if (getbedStatus.get(i).equals("male")) {
                            ((Button) event.getSource()).setStyle("-fx-background-color: #ff6347");
                            for (Integer moveID : getMoveDetail.keySet()) {
                                nurse.changeResidentToBed(moveID, getMoveDetail.get(i), getbedStatus.get(i), bedIDInt);
                            }
                            getMoveDetail.clear();
                            getbedStatus.clear();
                            String result3 = doctor.checkBedDetail(bedIDInt);
                            message.setText(result3);
                            moveDetail.setText("move patient successful");
                            callsucess();
                        }
                    } else {

                    }
                }

            }

        } else if (!showBedFromModel.get(bedIDInt).equals("empty")) {
            ((Button) event.getSource()).setStyle("-fx-background-color: #D8E82A");
            int moveIDInt = bedIDInt;
            int moveResidentID = bedMap.get(moveIDInt).getResidentID();
            String bedStatus = bedMap.get(moveIDInt).getBedState();
            if (getMoveDetail.isEmpty()) {
                getMoveDetail.put(moveIDInt, moveResidentID);
                getbedStatus.put(moveIDInt, bedStatus);
            } else {
                int moveIDForMap = 0;
                for (Integer moveID : getMoveDetail.keySet()) {
                    moveIDForMap = moveID;
                }
                if (moveIDForMap == bedIDInt) {
                    if (showBedFromModel.get(bedIDInt).equals("female")) {
                        ((Button) event.getSource()).setStyle("-fx-background-color: #00ffff");
                    } else if (showBedFromModel.get(bedIDInt).equals("male")) {
                        ((Button) event.getSource()).setStyle("-fx-background-color: #ff6347");
                    }
                    getMoveDetail.clear();
                    getbedStatus.clear();
                    moveDetail.setText("cancel selection");
                } else {
                    moveDetail.setText("We can not move 2 bed\n at same time");
                    if (showBedFromModel.get(bedIDInt).equals("female")) {
                        ((Button) event.getSource()).setStyle("-fx-background-color: #00ffff");
                    } else if (showBedFromModel.get(bedIDInt).equals("male")) {
                        ((Button) event.getSource()).setStyle("-fx-background-color: #ff6347");
                    }
                }
            }

        }
    }

    public void exitthispag(ActionEvent event) {//for exit the page
        Stage stage = (Stage) exitthispage.getScene().getWindow();
        stage.close();
    }

    public HashMap<Integer, Button> bedButton() {//store all the button in the hashMap
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
