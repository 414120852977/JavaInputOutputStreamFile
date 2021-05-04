package com.bridgelabz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayRollService {
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
public void shoeOnConsole() {
    System.out.println("output reading on console :"+employeePayRollServicelist);
}
}

