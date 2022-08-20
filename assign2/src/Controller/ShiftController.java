package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ShiftController implements Initializable {
    @FXML
    private ListView<String> shiftViewLeft;

    @FXML
    private Button selectShift;

    @FXML
    private TextArea shiftViewRight;


    @FXML
    private ChoiceBox<String> addShiftID;

    @FXML
    private ChoiceBox<String> AccountType;

    @FXML
    private Label message;
    @FXML
    private Button search;
    @FXML
    private ListView<String> viewListLeft;

    @FXML
    private Button deleteShift;

    @FXML
    private TextArea viewListRight;

    @FXML
    private Label messageModify;

    @FXML
    private ChoiceBox<String> AccountTypeModify;

    @FXML
    private ChoiceBox<String> ModifyShiftIDForChioce;

    @FXML
    private Button searchModify;
    @FXML
    private Button outputLog;
    @FXML
    private Button discharging;

    private ArrayList<String> display;
    private Doctor doctor;
    private Manager manager;
    private Nurse nurse;
    private String lastID = null;
    private ObservableList<String> nurseHaveShift = FXCollections.observableArrayList();
    private ObservableList<String> doctorHaveShift = FXCollections.observableArrayList();
    private CareHome careHome;
    private HashMap<Integer, Nurse> nurseMap;
    private HashMap<Integer, Doctor> doctorMap;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccountType.getItems().add("Doctor");//give the String doctor in the choice box
        AccountType.getItems().add("Nurse");//give the String nurse in the choice box
        AccountTypeModify.getItems().add("Doctor");//give the String doctor in the choice box
        AccountTypeModify.getItems().add("Nurse");//give the String nurse in the choice box
        manager = new Manager(0, null, null);
        nurse = new Nurse(0, null, null);
        doctor = new Doctor(0, null, null);
        addShiftIDisplay();
        ModifyShiftIDisplay();

    }


    public void addShiftIDisplay() {//add the the listener to the staff id
        AccountType.valueProperty().addListener((observableValue, get, change) ->
        {
            if (change != null) {
                addShiftID.getItems().clear();

                if (change.equals("Doctor")) {
                    addShiftID.getItems().addAll(manager.outPutDoctor("Doctor"));
                } else if (change.equals("Nurse")) {
                    addShiftID.getItems().addAll(manager.outPutNurse("Nurse"));
                }
            }
        });

    }

    public void ModifyShiftIDisplay() {//add the the listener to the staff id
        AccountTypeModify.valueProperty().addListener((observableValue, get, change) ->
        {
            if (change != null) {
                ModifyShiftIDForChioce.getItems().clear();

                if (change.equals("Doctor")) {
                    ModifyShiftIDForChioce.getItems().addAll(manager.outPutDoctor("Doctor"));
                } else if (change.equals("Nurse")) {
                    ModifyShiftIDForChioce.getItems().addAll(manager.outPutNurse("Nurse"));
                }
            }
        });

    }


    public void inputCanNotNull() throws CanNotNullException {//input validation
        if (addShiftID.getValue() == null || AccountType.getValue() == null) {

            throw new CanNotNullException();

        }

    }

    public void inputCanNotNull2() throws CanNotNullException {//input validation
        if (ModifyShiftIDForChioce.getValue() == null || AccountTypeModify.getValue() == null) {

            throw new CanNotNullException();

        }

    }

    public void inputCanNotNull3() throws CanNotNullException {//input validation
        if (addShiftID.getValue() == null || AccountType.getValue() == null
                || shiftViewLeft.getItems().isEmpty()) {

            throw new CanNotNullException();

        }

    }

    public void inputCanNotNull4() throws CanNotNullException {//input validation
        if (ModifyShiftIDForChioce.getValue() == null || AccountTypeModify.getValue() == null
                || viewListLeft.getItems().isEmpty()) {

            throw new CanNotNullException();

        }

    }


    public void checkDisplayListModify() {//display the shift name in the list view
        viewListLeft.getItems().clear();
        nurseHaveShift.clear();
        doctorHaveShift.clear();
        String check = AccountTypeModify.getValue();
        if (check.equals("Nurse")) {
            nurseHaveShift.addAll(nurse.findShiftByStaffID(ModifyShiftIDForChioce.getValue()));
            viewListLeft.setItems(nurseHaveShift);
        } else if (check.equals("Doctor")) {
            doctorHaveShift.addAll(doctor.findShiftByStaffID(ModifyShiftIDForChioce.getValue()));
            viewListLeft.setItems(doctorHaveShift);
        }
    }


    public void checkDisplayList() {//display the shift name in the list view
        String check = AccountType.getValue();
        shiftViewLeft.getItems().clear();
        if (message.getText().equals("we can not find the result")) {
            return;
        } else {

            if (check.equals("Nurse")) {
                shiftViewLeft.getItems().addAll("Monday 8 am to 4 pm", "Monday 2 pm to 10 pm",
                        "Tuesday 8 am to 4 pm", "Tuesday 2 pm to 10 pm", "Wednesday 8 am to 4 pm",
                        "Wednesday 2 pm to 10 pm", "Thursday 8 am to 4 pm", "Thursday 2 pm to 10 pm",
                        "Friday 8 am to 4 pm", "Friday 2 pm to 10 pm", "Saturday 8 am to 4 pm",
                        "Saturday 2 pm to 10 pm", "Sunday 8 am to 4 pm", "Sunday 2 pm to 10 pm");


            } else if (check.equals("Doctor")) {
                shiftViewLeft.getItems().addAll("Monday 10 am to 11 am", "Monday 3 pm to 4 pm",
                        "Tuesday 9 am to 10 am", "Tuesday 2 pm to 3 pm", "Wednesday 9 am to 10 am",
                        "Wednesday 1 pm to 2 pm", "Thursday 7 am to 8 am", "Thursday 4 pm to 5 pm",
                        "Friday 7 am to 8 am", "Friday 4 pm to 5 pm", "Saturday 9 am to 10 am",
                        "Saturday 3 pm to 4 pm", "Sunday 10 am to 11 am", "Sunday 2 pm to 3 pm");

            }

        }
    }

    public void viewTheChoose(ActionEvent event) {


        try {
            inputCanNotNull3();//check the input validation
        } catch (CanNotNullException e) {
            message.setText("please fill Staff detail");
            return;
        }
        String textArea = " ";
        ObservableList list = shiftViewLeft.getSelectionModel().getSelectedItems();
        for (Object item : list) {

            textArea += String.format("%s%n", (String) item);
        }
        shiftViewRight.setText(textArea);
        try {
            if (AccountType.getValue().equals("Nurse")) {//if the account type is nurse you can add the nurse shift
                    String outPut=nurse.addShift(addShiftID.getValue(), shiftViewLeft.getSelectionModel().getSelectedItem());//call the method in the backend

                    if(outPut.equals("add shift successfully")){
                    message.setText("add shift successfully");
                    Stage stageOperation = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
                    stageOperation.setTitle("Congratulation");
                    Scene scene1 = new Scene(loader.load());
                    stageOperation.setScene(scene1);
                    stageOperation.show();
                    }
                    else if(outPut.equals("we can not add doctor shift to the nurse")){

                        message.setText("we can not add doctor \nshift to the nurse");
                    }
            } else if (AccountType.getValue().equals("Doctor")) {//if the account type is doctor you can add the nurse shift
                    String outPut=doctor.addShift(addShiftID.getValue(), shiftViewLeft.getSelectionModel().getSelectedItem());//call the method in the backend
                    if(outPut.equals("add shift successfully")){
                    message.setText("add shift successfully");
                    Stage stageOperation = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
                    stageOperation.setTitle("Congratulation");
                    Scene scene1 = new Scene(loader.load());
                    stageOperation.setScene(scene1);
                    stageOperation.show();
                    }else if(outPut.equals("we can not add nurse shift to the doctor")){
                        message.setText("we can not add nurse \nshift to the doctor");

                }
            }
        } catch (exceedWorkHourException e) {
            message.setText("Staff exceed the work time");
            return;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search(ActionEvent event) throws CanNotNullException {//this method is for display the shift name
        try {
            inputCanNotNull();
        } catch (CanNotNullException e) {
            message.setText("please enter Staff detail");
            return;
        }
        String result = manager.searchByID(AccountType.getValue(), addShiftID.getValue());
        message.setText(result);
        checkDisplayList();
    }


    public void deleteShift(ActionEvent event) {//this method is for delete the shift name
        try {
            inputCanNotNull4();//check the input validation
        } catch (CanNotNullException e) {
            messageModify.setText("please fill Staff detail");
            return;
        }
        String result = null;
        String textArea = " ";
        ObservableList list = viewListLeft.getSelectionModel().getSelectedItems();
        for (Object item : list) {

            textArea += String.format("%s%n", (String) item);
        }
        viewListRight.setText(textArea);
        try {
            if (AccountTypeModify.getValue().equals("Nurse")) {//if the account type is nurse you can add the nurse shift
                result = nurse.deleteShift(ModifyShiftIDForChioce.getValue(), viewListLeft.getSelectionModel().getSelectedItem());
                if (result.charAt(0) == 'y') {
                    messageModify.setText(result);
                    Stage stageOperation = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
                    stageOperation.setTitle("Congratulation");
                    Scene scene1 = new Scene(loader.load());
                    stageOperation.setScene(scene1);
                    stageOperation.show();
                    checkDisplayListModify();
                } else if (result.equals("The Shift not exist")) {
                    messageModify.setText("The Shift not exist");
                }


            } else if (AccountTypeModify.getValue().equals("Doctor")) {//if the account type is doctor you can add the nurse shift
                result = doctor.deleteShift(ModifyShiftIDForChioce.getValue(), viewListLeft.getSelectionModel().getSelectedItem());
                if (result.charAt(0) == 'y') {
                    messageModify.setText(result);
                    Stage stageOperation = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
                    stageOperation.setTitle("Congratulation");
                    Scene scene1 = new Scene(loader.load());
                    stageOperation.setScene(scene1);
                    stageOperation.show();
                    checkDisplayListModify();
                } else if (result.equals("The Shift not exist")) {
                    messageModify.setText("The Shift not exist");
                }


            }
        } catch (exceedWorkHourException e) {
            message.setText("nurse exceed the work time");
            return;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void searchModify(ActionEvent event) {//this method is for display the shift name
        try {
            inputCanNotNull2();
        } catch (CanNotNullException e) {
            messageModify.setText("please enter Staff detail");
            return;
        }
        String result = manager.searchByID(AccountTypeModify.getValue(), ModifyShiftIDForChioce.getValue());
        messageModify.setText(result);
        checkDisplayListModify();

    }


    public void outputLog(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {//this method is for write the log in the careHome
        careHome = new CareHome();
        careHome.writeTheLogDetail();//write the log
        Stage stageOperation = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/operationsuccessful.fxml"));
        stageOperation.setTitle("Congratulation");
        Scene scene1 = new Scene(loader.load());
        stageOperation.setScene(scene1);
        stageOperation.show();
    }

    public void discharging(ActionEvent event) throws IOException {// this method is for call the window that can discharge the patient
        Stage stageOperation = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/dischargingPatient.fxml"));
        stageOperation.setTitle("discharging");
        Scene scene1 = new Scene(loader.load());
        stageOperation.setScene(scene1);
        stageOperation.show();

    }

}
