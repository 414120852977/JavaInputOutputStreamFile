package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class EmployeePayRollDataTest {
    /**
     * Emplyee pay roll service to read and write employee payroll to a console.
     */
    @Test
    public void givenEmployeePayRollDataShouldReturnOnConsole() {
        EmployeePayRollData[] arrayOfEmp = {
           new EmployeePayRollData(1,"joe",10000,"pune","cse","M",5656,54545,45454,4545,445, LocalDate.now()),
                new EmployeePayRollData(2,"Ashok",455454,"aurangabad","cs","M",4545,4544,4545,45,4554,LocalDate.of(2020,1,1))
        };
        EmployeePayRollService employeePayRollService;
        employeePayRollService = new EmployeePayRollService(Arrays.asList(arrayOfEmp));
        employeePayRollService.shoeOnConsole(EmployeePayRollService.IOService.FILE_IO);
        employeePayRollService.printData(EmployeePayRollService.IOService.FILE_IO);
        long entries = employeePayRollService.countEntries(EmployeePayRollService.IOService.FILE_IO);
        Assert.assertEquals(3,entries);
    }

    @Test
    public void givenFileOnReadingFromFileShouldMatchEmployeeCount() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
      EmployeePayRollFileIOService employeePayRollFileIOService = new EmployeePayRollFileIOService();
      employeePayRollFileIOService.readData();
        long entries = employeePayRollService.countEntries(EmployeePayRollService.IOService.FILE_IO);
        Assert.assertEquals(4,entries);
      Assert.assertEquals(4,entries);
    }

    @Test
    public void givenEmployeePayrollInDB_WhenRetrived_ShouldMatchEmployeeCount() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData> employeePayRollData = employeePayRollService.readEmployeePayRollData(EmployeePayRollService.IOService.DB_IO);
        Assert.assertEquals(6,employeePayRollData.size());
    }

    @Test
    public void givenNewSalaryToEmployee_WhenUpdated_ShouldSyncWithDatabase() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData> employeePayRollData = employeePayRollService.readEmployeePayRollData(EmployeePayRollService.IOService.DB_IO);
     employeePayRollService.updateEmployeeSalary("terissa",3000000);
    }

    @Test
    public void givenNewSalaryToEmployee_WhenUpdated_ShouldSyncWithDatabase_PreparedStatement() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData> employeePayRollData = employeePayRollService.readEmployeePayRollData(EmployeePayRollService.IOService.DB_IO);
        employeePayRollService.updateEmployeeSalary("joe",30000);
    }

    @Test
    public void RetriveAll_Employees_FromDatabase() {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        ResultSet resultSet = employeePayrollDBService.retriveEmployeeData("joe");
        System.out.println(resultSet);
    }
}
