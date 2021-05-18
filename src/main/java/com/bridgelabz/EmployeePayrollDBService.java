package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String userName = "root";
        String password = "root";
        Connection connection;
        System.out.println("Connecting to database:" + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection is successful!!!!!!" + connection);
        return connection;
    }
    public List<EmployeePayRollData> readData()  {
        String sql = "select * from employee_payroll";
        List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
           // Statement statement = connection.createStatement();
            PreparedStatement statement = connection.prepareStatement("update employee_payroll set basicpay = 3000 where name = joe");
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getNString("name");
                int phoneNo  = resultSet.getInt("phoneNo");
                String address = resultSet.getNString("address");
                String department = resultSet.getNString("department");
                String gender  = resultSet.getNString("gender");
                int basicpay = resultSet.getInt("basicpay");
                int deductions  = resultSet.getInt("deductions");
                int taxablepay = resultSet.getInt("taxablepay");
                int income_tax  = resultSet.getInt("income_tax");
                int Net_Pay  = resultSet.getInt("Net_Pay");
                LocalDate start = resultSet.getDate("start").toLocalDate();
                employeePayRollDataList.add(new EmployeePayRollData(id,name,phoneNo,address,department,gender,basicpay,deductions,taxablepay,income_tax,Net_Pay,start));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayRollDataList;
    }


    public int updateEmployeeData(String name, double basicpay) {
        String sql = String.format("update employee_payroll set basicpay = %.2f where name = '%s';", basicpay,name);
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public ResultSet retriveEmployeeData(String name) {
        String sql = String.format("select * from employee_payroll");
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            return  statement.executeQuery(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
