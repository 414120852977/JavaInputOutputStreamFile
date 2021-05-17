package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class EmployeePayRollDataTest {
    /**
     * Emplyee pay roll service to read and write employee payroll to a console.
     */
    @Test
    public void givenEmployeePayRollDataShouldReturnOnConsole() {
        EmployeePayRollData[] arrayOfEmp = {
           new EmployeePayRollData(1,"joe",10000),
           new EmployeePayRollData(2,"bidden",20000),
           new EmployeePayRollData(3,"ambani",30000),
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
}
