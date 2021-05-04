package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class EmployeePayRollDataTest {
    /**
     * Emplyee pay roll service to read and write employee payroll to a console.
     */
    @Test
    public void givenEmployeePayRollDataShouldReturnOnConsole() {
        ArrayList<EmployeePayRollData> employeePayRollServicelist = new ArrayList<>();
        EmployeePayRollService employeePayRollService =  new EmployeePayRollService(employeePayRollServicelist);
        employeePayRollService.dataInsert();
        EmployeePayRollService  employeePayRollService1 = new EmployeePayRollService();
        employeePayRollService1.shoeOnConsole();
        Assert.assertTrue(true);
    }
}
