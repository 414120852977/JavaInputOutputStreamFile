package com.bridgelabz;

import java.time.LocalDate;

public class EmployeePayRollData {
   private final int id;
    public    String name;
    private final int phoneNo;
    private final String address;
    private final String department;
    private final String gender;
    public int basicpay;
    private final int deductions;
    private final int taxablepay;
    private final int income_tax;
    private final int net_pay;
    public LocalDate start;



    public EmployeePayRollData(int id, String name, int phoneNo, String address, String department,
                               String gender, int basicpay, int deductions, int taxablepay, int income_tax,
                               int net_Pay, LocalDate start) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.address = address;
        this.department = department;
        this.gender = gender;
        this.basicpay = basicpay;
        this.deductions = deductions;
        this.taxablepay = taxablepay;
        this.income_tax = income_tax;
        this.net_pay = net_Pay;
        this.start = start;
    }

    @Override
    public String toString() {
        return "EmployeePayRollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber=" + phoneNo +
                ", address='" + address + '\'' +
                ", department='" + department + '\'' +
                ", gender='" + gender + '\'' +
                ", basicpay=" + basicpay +
                ", deductions=" + deductions +
                ", taxablepay=" + taxablepay +
                ", income_tax=" + income_tax +
                ", net_pay=" + net_pay +
                ", start=" + start +
                '}';
    }
}
