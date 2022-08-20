package Controller;

import Model.*;
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


public class BedDetailController implements Initializable {
    @FXML
    private Button bed20;

    @FXML
    private Button bed21;

    @FXML
    private Button bed22;

    @FXML
    private Button bed23;

    @FXML
    private Button bed24;

    @FXML
    private Button bed26;

    @FXML
    private Button bed25;

    @FXML
    private Button bed27;

    @FXML
    private Button bed28;

    @FXML
    private Button bed29;

    @FXML
    private Button bed30;

    @FXML
    private Button bed31;

    @FXML
    private Button bed32;

    @FXML
    private Button bed33;

    @FXML
    private Button bed34;

    @FXML
    private Button bed35;

    @FXML
    private Button bed36;

    @FXML
    private Button bed38;

    @FXML
    private Button bed37;

    @FXML
    private Button exitthispage;

    @FXML
    private Label message;

    @FXML
    private TextField residentID;

    @FXML
    private TextField residentName;

    @FXML
    private DatePicker admitedtime;

    @FXML
    private DatePicker discharged;

    @FXML
    private ChoiceBox<String> Gender;

    @FXML
    private Button bed1;

    @FXML
    private Button bed2;

    @FXML
    private Button bed3;

    @FXML
    private Button bed4;

    @FXML
    private Button bed7;

    @FXML
    private Button bed5;

    @FXML
    private Button bed8;

    @FXML
    private Button bed6;

    @FXML
    private Button bed9;

    @FXML
    private Button bed10;

    @FXML
    private Button bed13;

    @FXML
    private Button bed11;

    @FXML
    private Button bed14;

    @FXML
    private Button bed12;

    @FXML
    private Button bed15;

    @FXML
    private Button bed16;

    @FXML
    private Button bed18;

    @FXML
    private Button bed17;

    @FXML
    private Button bed19;


    private Manager manager;

    private Room room;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        Gender.getItems().add("male");//put gender into the choice box
        Gender.getItems().add("female");//put gender into the choice box
        room = new Room(0);
        manager = new Manager(0, null, null);
        try {
            checkBed1();//check the bed status in the initialize
            checkBed2();
            checkBed3();
            checkBed4();
            checkBed5();
            checkBed6();
            checkBed7();
            checkBed8();
            checkBed9();
            checkBed10();
            checkBed11();
            checkBed12();
            checkBed13();
            checkBed14();
            checkBed15();
            checkBed16();
            checkBed17();
            checkBed18();
            checkBed19();
            checkBed20();
            checkBed21();
            checkBed22();
            checkBed23();
            checkBed24();
            checkBed25();
            checkBed26();
            checkBed27();
            checkBed28();
            checkBed29();
            checkBed30();
            checkBed31();
            checkBed32();
            checkBed33();
            checkBed34();
            checkBed35();
            checkBed36();
            checkBed37();
            checkBed38();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void inputCanNotNull() throws CanNotNullException {// for input validate
        if (residentID.getText().isBlank() || residentName.getText().isBlank() ||
                admitedtime.getValue() == null || discharged.getValue() == null || Gender.getValue() == null) {

            throw new CanNotNullException();

        }

    }


    public void exitthispag(ActionEvent event) {//for exit button
        Stage stage = (Stage) exitthispage.getScene().getWindow();
        stage.close();


    }


    public void callsucess() throws IOException {//for display the new stage for successful operation
        Stage stageOperation = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
        stageOperation.setTitle("Congratulation");
        Scene scene1 = new Scene(loader.load());
        stageOperation.setScene(scene1);
        stageOperation.show();

    }

    public void checkBed1() throws SQLException, ClassNotFoundException {// check the bed colour
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(601)) {
            if (result.get(601).equals("female")) {
                bed1.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(601).equals("male")) {
                bed1.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed1(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();// for check the input validation
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 601);// this method is for add resident to a bed
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed1.setStyle("-fx-background-color: #00ffff");//change colour to the blue
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed1.setStyle("-fx-background-color: #ff6347");//change colour to the red
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }


    }

    public void checkBed2() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(602)) {
            if (result.get(602).equals("female")) {
                bed2.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(602).equals("male")) {
                bed2.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed2(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter  patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 602);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed2.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed2.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }


    }

    public void checkBed3() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(603)) {
            if (result.get(603).equals("female")) {
                bed3.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(603).equals("male")) {
                bed3.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed3(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 603);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed3.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed3.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }


    }

    public void checkBed4() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(604)) {
            if (result.get(604).equals("female")) {
                bed4.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(604).equals("male")) {
                bed4.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed4(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 604);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed4.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed4.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }

    }


    public void checkBed7() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(607)) {
            if (result.get(607).equals("female")) {
                bed7.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(607).equals("male")) {
                bed7.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed7(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 607);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed7.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed7.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }


    }


    public void checkBed5() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(605)) {
            if (result.get(605).equals("female")) {
                bed5.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(605).equals("male")) {
                bed5.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed5(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 605);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed5.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed5.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }

    }


    public void checkBed8() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(608)) {
            if (result.get(608).equals("female")) {
                bed8.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(608).equals("male")) {
                bed8.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed8(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 608);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed8.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed8.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed6() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(606)) {
            if (result.get(606).equals("female")) {
                bed6.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(606).equals("male")) {
                bed6.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed6(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 606);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed6.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed6.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }

    public void checkBed9() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(609)) {
            if (result.get(609).equals("female")) {
                bed9.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(609).equals("male")) {
                bed9.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed9(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 609);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed9.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed9.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }


    }

    public void checkBed10() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(610)) {
            if (result.get(610).equals("female")) {
                bed10.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(610).equals("male")) {
                bed10.setStyle("-fx-background-color: #ff6347");
            }
        }
    }

    public void bed10(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 610);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed10.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed10.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed13() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(613)) {
            if (result.get(613).equals("female")) {
                bed13.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(613).equals("male")) {
                bed13.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed13(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 613);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed13.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed13.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed11() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(611)) {
            if (result.get(611).equals("female")) {
                bed11.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(611).equals("male")) {
                bed11.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed11(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 611);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed11.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed11.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }


    }

    public void checkBed14() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(614)) {
            if (result.get(614).equals("female")) {
                bed14.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(614).equals("male")) {
                bed14.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed14(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 614);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed14.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed14.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }

    }


    public void checkBed12() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(612)) {
            if (result.get(612).equals("female")) {
                bed12.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(612).equals("male")) {
                bed12.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed12(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 612);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed12.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed12.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }

    public void checkBed15() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(615)) {
            if (result.get(615).equals("female")) {
                bed15.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(615).equals("male")) {
                bed15.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed15(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 615);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed15.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed15.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed16() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(616)) {
            if (result.get(616).equals("female")) {
                bed16.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(616).equals("male")) {
                bed16.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed16(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 616);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed16.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed16.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed18() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(618)) {
            if (result.get(618).equals("female")) {
                bed18.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(618).equals("male")) {
                bed18.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed18(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 618);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed18.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed18.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed17() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(617)) {
            if (result.get(617).equals("female")) {
                bed17.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(617).equals("male")) {
                bed17.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed17(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 617);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed17.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed17.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed19() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(619)) {
            if (result.get(619).equals("female")) {
                bed19.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(619).equals("male")) {
                bed19.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed19(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 619);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed19.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed19.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }

    public void checkBed20() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(620)) {
            if (result.get(620).equals("female")) {
                bed20.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(620).equals("male")) {
                bed20.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed20(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 620);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed20.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed20.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed21() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(621)) {
            if (result.get(621).equals("female")) {
                bed21.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(621).equals("male")) {
                bed21.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed21(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 621);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed21.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed21.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }

    public void checkBed22() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(622)) {
            if (result.get(622).equals("female")) {
                bed22.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(622).equals("male")) {
                bed22.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed22(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 622);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed22.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed22.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed23() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(623)) {
            if (result.get(623).equals("female")) {
                bed23.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(623).equals("male")) {
                bed23.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed23(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 623);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed23.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed23.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }

    public void checkBed24() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(624)) {
            if (result.get(624).equals("female")) {
                bed24.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(624).equals("male")) {
                bed24.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed24(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 624);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed24.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed24.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed26() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(626)) {
            if (result.get(626).equals("female")) {
                bed26.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(626).equals("male")) {
                bed26.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed26(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 626);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed26.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed26.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed25() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(625)) {
            if (result.get(625).equals("female")) {
                bed25.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(625).equals("male")) {
                bed25.setStyle("-fx-background-color: #ff6347");
            }
        }
    }

    public void bed25(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 625);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed25.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed25.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed27() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(627)) {
            if (result.get(627).equals("female")) {
                bed27.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(627).equals("male")) {
                bed27.setStyle("-fx-background-color: #ff6347");
            }
        }
    }

    public void bed27(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 627);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed27.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed27.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed28() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(628)) {
            if (result.get(628).equals("female")) {
                bed28.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(628).equals("male")) {
                bed28.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed28(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 628);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed28.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed28.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }

    }


    public void checkBed29() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(629)) {
            if (result.get(629).equals("female")) {
                bed29.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(629).equals("male")) {
                bed29.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed29(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 629);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed29.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed29.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }

    }

    public void checkBed30() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(630)) {
            if (result.get(630).equals("female")) {
                bed30.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(630).equals("male")) {
                bed30.setStyle("-fx-background-color: #ff6347");
            }
        }
    }

    public void bed30(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 630);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed30.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed30.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed31() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(631)) {
            if (result.get(631).equals("female")) {
                bed31.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(631).equals("male")) {
                bed31.setStyle("-fx-background-color: #ff6347");
            }
        }
    }

    public void bed31(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 631);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed31.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed31.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }

    public void checkBed32() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(632)) {
            if (result.get(632).equals("female")) {
                bed32.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(632).equals("male")) {
                bed32.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed32(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 632);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed32.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed32.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed33() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(633)) {
            if (result.get(633).equals("female")) {
                bed33.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(633).equals("male")) {
                bed33.setStyle("-fx-background-color: #ff6347");
            }
        }
    }

    public void bed33(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 633);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed33.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed33.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed34() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(634)) {
            if (result.get(634).equals("female")) {
                bed34.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(634).equals("male")) {
                bed34.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed34(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 634);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed34.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed34.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }

    public void checkBed35() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(635)) {
            if (result.get(635).equals("female")) {
                bed35.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(635).equals("male")) {
                bed35.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed35(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 635);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed35.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed35.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }

    }

    public void checkBed36() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(636)) {
            if (result.get(636).equals("female")) {
                bed36.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(636).equals("male")) {
                bed36.setStyle("-fx-background-color: #ff6347");
            }
        }
    }

    public void bed36(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 636);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed36.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed36.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed38() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(638)) {
            if (result.get(638).equals("female")) {
                bed38.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(638).equals("male")) {
                bed38.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed38(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 638);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed38.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed38.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


    public void checkBed37() throws SQLException, ClassNotFoundException {
        HashMap<Integer, String> result = manager.checkBedStatus();

        if (result.containsKey(637)) {
            if (result.get(637).equals("female")) {
                bed37.setStyle("-fx-background-color: #00ffff");

            } else if (result.get(637).equals("male")) {
                bed37.setStyle("-fx-background-color: #ff6347");
            }
        }
    }


    public void bed37(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter patient detail");
            return;
        }
        int residentIDInt = Integer.parseInt(residentID.getText());
        String admitedtimeString = String.valueOf(admitedtime.getValue());
        String diachargedString = String.valueOf(discharged.getValue());
        String checkColour = manager.addResidentToBed(residentIDInt, residentName.getText(), Gender.getValue(), admitedtimeString
                , diachargedString, 637);
        if (checkColour.equals("you are add a female patient to a bed")) {
            bed37.setStyle("-fx-background-color: #00ffff");
            message.setText("you are add a female \npatient to a bed");
            callsucess();

        } else if (checkColour.equals("you are add a male patient to a bed")) {
            bed37.setStyle("-fx-background-color: #ff6347");
            message.setText("you are add a male \npatient to a bed");
            callsucess();
        }else if (checkColour.equals("resident can not have same ID")){
            message.setText("resident can not have same ID");
        }
    }


}
