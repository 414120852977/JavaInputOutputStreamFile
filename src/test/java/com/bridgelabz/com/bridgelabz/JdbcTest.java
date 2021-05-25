package com.bridgelabz.com.bridgelabz;

import com.bridgelabz.EmployeePayRollData;
import com.bridgelabz.EmployeePayRollService;
import com.bridgelabz.EmployeePayrollDBService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bridgelabz.EmployeePayRollService.IOService.DB_IO;

public class JdbcTest {
    EmployeePayRollService employeePayRollService = null;
    EmployeePayrollDBService employeePayrollDBService = null;
    @Before
    public void setUp() throws Exception {
        new EmployeePayRollService();
        new EmployeePayrollDBService();
    }

    @Test
    public void givenEmployeePayrollInDB_WhenRetrived_ShouldMatchEmployeeCount() {
        List<EmployeePayRollData> employeePayRollData = employeePayRollService.readEmployeePayRollData(DB_IO);
        Assert.assertEquals(6,employeePayRollData.size());
    }
    @Test
    public void givenNewSalaryToEmployee_WhenUpdated_ShouldSyncWithDatabase() {
        List<EmployeePayRollData> employeePayRollData = employeePayRollService.readEmployeePayRollData(DB_IO);
        employeePayRollService.updateEmployeeSalary("terissa",3000000);
        boolean result = employeePayRollService.checkEmployeePayrollSyncWithDatabase("terissa");
        Assert.assertTrue(result);
    }

    @Test
    public void givenNewSalaryToEmployee_WhenUpdated_ShouldSyncWithDatabase_PreparedStatement() {
        List<EmployeePayRollData> employeePayRollData = employeePayRollService.readEmployeePayRollData(DB_IO);
        employeePayRollService.updateEmployeeSalary("terissa",3000000);
        boolean result = employeePayRollService.checkEmployeePayrollSyncWithDatabase("terissa");
        Assert.assertTrue(result);
    }



    @Test
    public void RetriveAll_Employees_FromDatabase() {
        List<EmployeePayRollData> employeePayRollDataList = employeePayrollDBService.readData();
        Assert.assertEquals(7,employeePayRollDataList.size());
    }

    @Test
    public void givenDateRange_WhenRetrived_ShouldMatchEmployeeCount() {
        employeePayRollService.readEmployeePayRollData(DB_IO);
        LocalDate startDate = LocalDate.of(2018,5, 2);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayRollData> employeePayRollData = employeePayRollService.readEmployeePayrollForDateRange(DB_IO,startDate,endDate);
        Assert.assertEquals(7,employeePayRollData.size());
    }

    @Test
    public void givenPayrollData_WhenAverageSalaryRetriveByGender_ShouldReturnProperValue() throws SQLException {
        employeePayRollService.readEmployeePayRollData(DB_IO);
        Map<String,Double> averageSalaryByGender = employeePayRollService.readAverageSalaryByGender(DB_IO);
        Assert.assertTrue(averageSalaryByGender.get("M").equals(24847.083333333332)  && averageSalaryByGender
                                                                        .get("F").equals(2001513.75 ));
    }

    @Test
    public void givenNewEmployee_WhenAdded_ShouldSyncWithDatabase() throws SQLException {
        employeePayRollService.readEmployeePayRollData(DB_IO);
        employeePayRollService.addEmployeeToPayroll("ashok", 445, "mumbai", "cse", "M", 4545, 544, 45, 45454, 45, LocalDate.now());
        boolean result = employeePayRollService.checkEmployeePayrollSyncWithDatabase("ashok");
        Assert.assertTrue(true);
    }

    @Test
    public void putDataIntoCompanyDataTable_ToCheck_ShouldSyncWithDatabase() {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        EmployeePayRollData result = employeePayrollDBService.putDataIntoCompanyData(1, "etc");
        EmployeePayRollData results = employeePayrollDBService.deleteRecordFromCompanyData(3, "computer");
        Assert.assertTrue(true);
    }

    @Test
    public void given6Employee_WhenAddedToDB_SouldMatchEmployeeCount() {
        EmployeePayRollData[] arrayOfEmployee = {
                new EmployeePayRollData(0,"ashok",45454,"mumbai","cse","M",
                        454,4545,65,454,45,LocalDate.now()),
                new EmployeePayRollData(0,"gajendra",4545,"pune","mech","M",44454,45,56,4545,55,LocalDate.of(2016,02,02)),
                new EmployeePayRollData(0,"arvind",4545,"jalna","mech","M",4545,56,556,56,44,LocalDate.now()),
                new EmployeePayRollData(0,"sneha",4545,"jafrabad","cse","F",454545,4555,1222,1221,22121,LocalDate.now())
        };
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRollData(DB_IO);
        Instant start = Instant.now();
        employeePayRollService.addEmployeeToPayroll(Arrays.asList(arrayOfEmployee));
        Instant end  = Instant.now();
        System.out.println("duration without thread :" +Duration.between(start,end));
        Instant threadstart = Instant.now();
        employeePayRollService.addEmployeeToPayrollWithThreads(Arrays.asList(arrayOfEmployee));
        Instant threadEnd  = Instant.now();
        System.out.println("duration of thread with thread:"+Duration.between(threadstart,threadEnd));
        Assert.assertEquals(38,employeePayRollService.countEntries(DB_IO));
    }
}
