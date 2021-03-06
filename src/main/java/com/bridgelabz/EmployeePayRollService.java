package com.bridgelabz;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class EmployeePayRollService {

EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
    public void printData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO)) new EmployeePayRollFileIOService().printData();
    }

    public long countEntries(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
          return   new EmployeePayRollFileIOService().countEntries();
        return employeePayRollServicelist.size();
    }

    public List<EmployeePayRollData> readEmployeePayrollForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
        if (ioService.equals(IOService.DB_IO)) {
            return employeePayrollDBService.getEmployeeForDateRange(startDate, endDate);

        }
        return null;
    }

    public boolean checkEmployeePayrollSyncWithDatabase(String name) {
      List<EmployeePayRollData> employeePayRollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayRollDataList.get(1).equals(getEmployeePayrollData(name));

    }

    public Map<String, Double> readAverageSalaryByGender(IOService ioService) throws SQLException {
        if (ioService.equals(IOService.DB_IO)) {
            return employeePayrollDBService.getAverageSalaryByGender();
        }
        return null;
    }

    public void addEmployeeToPayroll(String name, int phoneNo, String address, String department, String gender, int basicpay, int deductions, int taxablepay, int income_tax, int  Net_Pay, LocalDate  start) throws SQLException {
    employeePayRollServicelist.add(employeePayrollDBService.addEmployeeToPayRollUc8(name,phoneNo,address,department,gender,basicpay,deductions,taxablepay,income_tax,Net_Pay,start));
    }

    public  void addEmployeeToPayroll(List<EmployeePayRollData> employeePayRollServicelist) {
        employeePayRollServicelist.forEach(employeePayRollData -> {
            System.out.println("employee is being added :"+employeePayRollData.name);
            try {
                this.addEmployeeToPayroll(employeePayRollData.name,employeePayRollData.phoneNo,employeePayRollData.address,employeePayRollData.department,
                        employeePayRollData.gender,employeePayRollData.basicpay,employeePayRollData.deductions,
                        employeePayRollData.taxablepay,employeePayRollData.income_tax,employeePayRollData.net_pay,
                        employeePayRollData.start);
                System.out.println("employee is added :"+employeePayRollData.name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            System.out.println(this.employeePayRollServicelist);
        });
    }

    public void addEmployeeToPayrollWithThreads(List<EmployeePayRollData> employeePayRollServicelist) {
        Map<Integer, Boolean> employeeAdditionStatus = new HashMap<>();
        employeePayRollServicelist.forEach(employeePayRollData -> {
            Runnable task = () -> {
                employeeAdditionStatus.put(employeePayRollData.hashCode(),false);
                System.out.println("employee is being added :"+Thread.currentThread().getName());
                try {
                    this.addEmployeeToPayroll(employeePayRollData.name,employeePayRollData.phoneNo,employeePayRollData.address
                    ,employeePayRollData.department,employeePayRollData.gender,employeePayRollData.basicpay,
                            employeePayRollData.deductions,employeePayRollData.taxablepay,employeePayRollData.income_tax,
                            employeePayRollData.net_pay,employeePayRollData.start);
                    employeeAdditionStatus.put(employeePayRollData.hashCode(),true);
                    System.out.println("employee Added :"+Thread.currentThread().getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            };
            Thread thread = new Thread(task,employeePayRollData.name) ;
        thread.start();
        });
        while (employeeAdditionStatus.containsValue(false)) {
            try {
                Thread.sleep(10);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(employeePayRollServicelist);
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

