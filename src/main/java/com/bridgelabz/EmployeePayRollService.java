package com.bridgelabz;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeePayRollService {

EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
    public void printData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO)) new EmployeePayRollFileIOService().printData();
    }

    public long countEntries(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
          return   new EmployeePayRollFileIOService().countEntries();
        return 0;
    }

    public List<EmployeePayRollData> readEmployeePayrollForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
        if (ioService.equals(IOService.DB_IO)) {
            return employeePayrollDBService.getEmployeeForDateRange(startDate, endDate);

        }
        return null;
    }

    public boolean checkEmployeePayrollSyncWithDatabase(String name) {
      List<EmployeePayRollData>  employeePayRollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayRollDataList.get(0).equals(getEmployeePayrollData(name));

    }

    public Map<String, Double> readAverageSalaryByGender(IOService ioService) throws SQLException {
        if (ioService.equals(IOService.DB_IO)) {
            return employeePayrollDBService.getAverageSalaryByGender();
        }
        return null;
    }


    public  enum IOService{CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
    private List<EmployeePayRollData> employeePayRollServicelist;

    public EmployeePayRollService() {
    }

    public EmployeePayRollService(List<EmployeePayRollData> employeePayRollServicelist) {
        this.employeePayRollServicelist = employeePayRollServicelist;
    }

    Scanner scanner = new Scanner(System.in);
    public  void dataInsert() {
//        EmployeePayRollData employeePayRollData = new EmployeePayRollData();
//        ArrayList<EmployeePayRollData> employeePayRollServicelist = new ArrayList<>();
        System.out.println("****enter a data****");
        System.out.println("enter a id :");
        int id = scanner.nextInt();
        System.out.println("enter your name :");
        String name = scanner.nextLine();
        System.out.println("enter your salary:");
        float salary = scanner.nextFloat();
//        employeePayRollServicelist.add(new EmployeePayRollData(id,name,salary));
//        System.out.println("output reading on console :"+employeePayRollServicelist);
    }

    public List<EmployeePayRollData> readEmployeePayRollData(IOService ioService) {
        if (ioService.equals(IOService.DB_IO)) {
            this.employeePayRollServicelist = new EmployeePayrollDBService().readData();
        }
        return this.employeePayRollServicelist;
    }

    public void updateEmployeeSalary(String name, int basicpay) {
        int result = new EmployeePayrollDBService().updateEmployeeData(name, basicpay);
        if (result == 0) return;
        EmployeePayRollData employeePayRollData = this.getEmployeePayrollData(name);
        if (employeePayRollData != null) {
            employeePayRollData.basicpay = basicpay;
        }
    }

    private EmployeePayRollData getEmployeePayrollData(String name) {
        return  this.employeePayRollServicelist.stream()
                .filter(employeePayRollDataItem -> employeePayRollDataItem.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    public void shoeOnConsole(IOService ioService) {
        if (ioService.equals(IOService.CONSOLE_IO))
    System.out.println("output reading on console :"+employeePayRollServicelist);
        else if(ioService.equals(IOService.FILE_IO)) {
            new EmployeePayRollFileIOService().writeData(employeePayRollServicelist);
        }
}
}

