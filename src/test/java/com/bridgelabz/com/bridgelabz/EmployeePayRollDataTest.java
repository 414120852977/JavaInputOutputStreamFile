package com.bridgelabz.com.bridgelabz;

import com.bridgelabz.EmployeePayRollData;
import com.bridgelabz.EmployeePayRollFileIOService;
import com.bridgelabz.EmployeePayRollService;
import com.bridgelabz.EmployeePayrollDBService;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bridgelabz.EmployeePayRollService.IOService.DB_IO;


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
        Assert.assertEquals(2,entries);
    }

    @Test
    public void givenFileOnReadingFromFileShouldMatchEmployeeCount() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        EmployeePayRollFileIOService employeePayRollFileIOService = new EmployeePayRollFileIOService();
        employeePayRollFileIOService.readData();
        long entries = employeePayRollService.countEntries(EmployeePayRollService.IOService.FILE_IO);
        Assert.assertEquals(2,entries);
        Assert.assertEquals(2,entries);
    }


}
