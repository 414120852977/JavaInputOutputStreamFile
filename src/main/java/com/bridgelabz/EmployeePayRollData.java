package com.bridgelabz;

public class EmployeePayRollData {
    int id;
    String name;
    float salary;

    public EmployeePayRollData(int id, String name, float salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public EmployeePayRollData() {
    }

    @Override
    public String toString() {
        return "EmployeePayRollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
