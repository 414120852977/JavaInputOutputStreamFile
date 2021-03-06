package com.bridgelabz;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayRollData {
    public int id;
    public int employee_id;
    public  String name;
    public int phoneNo;
    public String address;
    public String department;
    public String gender;
    public int basicpay;
    public int deductions;
    public int taxablepay;
    public int income_tax;
    public int net_pay;
    public LocalDate start;
    public  int company_id;
    public String company_name;
    public  int dept_id;
    public String dept_name;




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

    public EmployeePayRollData( int id,int company_id, String company_name) {
        this.company_id = company_id;
        this.company_name = company_name;
    }

    public EmployeePayRollData(int dept_id, String dept_name) {
        this.dept_id = dept_id;
        this.dept_name = dept_name;
    }

    public EmployeePayRollData(int id, int basicpay, int deductions, int taxablepay, int income_tax, int net_pay) {
        this.id = id;
        this.basicpay = basicpay;
        this.deductions = deductions;
        this.taxablepay = taxablepay;
        this.income_tax = income_tax;
        this.net_pay = net_pay;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public String getDepartment() {
        return department;
    }

    public String getGender() {
        return gender;
    }

    public int getBasicpay() {
        return basicpay;
    }

    public void setBasicpay(int basicpay) {
        this.basicpay = basicpay;
    }

    public int getDeductions() {
        return deductions;
    }

    public int getTaxablepay() {
        return taxablepay;
    }

    public int getIncome_tax() {
        return income_tax;
    }

    public int getNet_pay() {
        return net_pay;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return true;
        EmployeePayRollData that = (EmployeePayRollData) o;
        return id == that.id && employee_id == that.employee_id && phoneNo == that.phoneNo && basicpay == that.basicpay && deductions == that.deductions && taxablepay == that.taxablepay && income_tax == that.income_tax && net_pay == that.net_pay && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(department, that.department) && Objects.equals(gender, that.gender) && Objects.equals(start, that.start);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employee_id, name, phoneNo, address, department, gender, basicpay, deductions, taxablepay, income_tax, net_pay, start);
    }
}
