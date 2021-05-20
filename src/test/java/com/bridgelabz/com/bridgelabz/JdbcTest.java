package com.bridgelabz.com.bridgelabz;

import com.bridgelabz.EmployeePayRollData;
import com.bridgelabz.EmployeePayRollService;
import com.bridgelabz.EmployeePayrollDBService;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.bridgelabz.EmployeePayRollService.IOService.DB_IO;

public class JdbcTest {
    @Test
    public void givenEmployeePayrollInDB_WhenRetrived_ShouldMatchEmployeeCount() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData> employeePayRollData = employeePayRollService.readEmployeePayRollData(DB_IO);
        Assert.assertEquals(6,employeePayRollData.size());
    }
    @Test
    public void givenNewSalaryToEmployee_WhenUpdated_ShouldSyncWithDatabase() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData> employeePayRollData = employeePayRollService.readEmployeePayRollData(DB_IO);
        employeePayRollService.updateEmployeeSalary("terissa",3000000);
        boolean result = employeePayRollService.checkEmployeePayrollSyncWithDatabase("terissa");
        Assert.assertTrue(result);
    }

    @Test
    public void givenNewSalaryToEmployee_WhenUpdated_ShouldSyncWithDatabase_PreparedStatement() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData> employeePayRollData = employeePayRollService.readEmployeePayRollData(DB_IO);
        employeePayRollService.updateEmployeeSalary("terissa",3000000);
        boolean result = employeePayRollService.checkEmployeePayrollSyncWithDatabase("terissa");
        Assert.assertTrue(result);
    }



    @Test
    public void RetriveAll_Employees_FromDatabase() {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        List<EmployeePayRollData> employeePayRollDataList = employeePayrollDBService.readData();
        Assert.assertEquals(6,employeePayRollDataList.size());
    }

    @Test
    public void givenDateRange_WhenRetrived_ShouldMatchEmployeeCount() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRollData(DB_IO);
        LocalDate startDate = LocalDate.of(2018,5, 2);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayRollData> employeePayRollData = employeePayRollService.readEmployeePayrollForDateRange(DB_IO,startDate,endDate);
        Assert.assertEquals(6,employeePayRollData.size());
    }

    @Test
    public void givenPayrollData_WhenAverageSalaryRetriveByGender_ShouldReturnProperValue() throws SQLException {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRollData(DB_IO);
        Map<String,Double> averageSalaryByGender = employeePayRollService.readAverageSalaryByGender(DB_IO);
        Assert.assertTrue(averageSalaryByGender.get("M").equals(24847.083333333332)  && averageSalaryByGender
                                                                        .get("F").equals(2001513.75 ));
    }

    @Test
    public void givenNewEmployee_WhenAdded_ShouldSyncWithDatabase() throws SQLException {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRollData(DB_IO);
        employeePayRollService.addEmployeeToPayroll("ashok",445,"mumbai","cse","M",4545,544,454,45454,45, LocalDate.now());
        boolean result = employeePayRollService.checkEmployeePayrollSyncWithDatabase("ashok");
        Assert.assertTrue(result);
    }
}
