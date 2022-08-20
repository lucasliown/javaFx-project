package Model;

import static org.junit.Assert.*;

import org.junit.*;

import java.io.*;
import java.sql.SQLException;


public class CareHomeTest {
    private CareHome careHome;
    private Manager manager;
    private Doctor doctor;
    private Nurse nurse;
    private Prescription prescription;

    @Before
    public void setUp() {
        careHome = new CareHome();
        manager = new Manager(1, null, null);
        doctor = new Doctor(0, null, null);
        nurse = new Nurse(0, null, null);
        prescription = new Prescription(0, null, null, null,
                null, 0, 0);
        careHome.initiationCareHome();
    }



    @Test
    public void validateLoginNurse() throws IOException {
        assertEquals(1, careHome.validateLogin("N", "1"));
    }

    @Test
    public void validateLoginDoctor() throws IOException {
        assertEquals(2, careHome.validateLogin("D", "1"));
    }

    @Test
    public void validateLoginManager() throws IOException {
        assertEquals(3, careHome.validateLogin("M", "1"));
    }


    @Test
    public void validateSearchStaffForModifyDoctor() {
        assertEquals(2, careHome.validateSearchSaff("103", "D"));
    }


    @Test
    public void validateSearchStaffForModifyNurse() {
        assertEquals(1, careHome.validateSearchSaff("203", "N"));
    }

    @Test
    public void validateSearchStaffForModifyManager() {
        assertEquals(3, careHome.validateSearchSaff("302", "M"));
    }


    @Test
    public void findDoctorShiftID() {
        assertEquals("DMON2", doctor.findShiftID("Monday 10 am to 11 am"));
    }


    @Test
    public void findNurseShiftID() {
        assertEquals("NMON1", nurse.findShiftID("Monday 8 am to 4 pm"));
    }

    @Test
    public void findFindMedicineIDM1() {
        assertEquals("M1", prescription.fineID("Bacitracin"));

    }

    @Test
    public void findFindMedicineIDM2() {
        assertEquals("M2", prescription.fineID("Adacel"));

    }


}



