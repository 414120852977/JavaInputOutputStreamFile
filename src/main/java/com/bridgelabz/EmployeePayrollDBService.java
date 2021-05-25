package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeePayrollDBService {
    List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
    private PreparedStatement employeePayrollDataStatement;
    private static  EmployeePayrollDBService employeePayrollDBService;

    public EmployeePayrollDBService() {
    }
    public static  EmployeePayrollDBService getInstance() {
        if (employeePayrollDBService == null) {
            employeePayrollDBService = new EmployeePayrollDBService();

        }
        return employeePayrollDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String userName = "root";
        String password = "root";
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("cannot find the driver in the classpath!", e);
        }
        System.out.println("Connecting to database:" + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection is successful!!!!!!" + connection);
        return connection;
    }
    public List<EmployeePayRollData> readData()  {
        List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
        String sql = "select * from employee_payroll";

        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
           // PreparedStatement statement = connection.prepareStatement("update employee_payroll set basicpay = 3000 where name = joe");
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
//                System.out.println("employee data->"+employeePayRollDataList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void retriveData()  {
        String sql = "select SUM(basicpay) from employee_payroll where gender = 'M' GROUP BY gender";
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
             statement.executeQuery(sql);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<EmployeePayRollData> getEmployeeForDateRange(LocalDate startDate, LocalDate endDate) {
        String sql  = String.format("select * from employee_payroll where start BETWEEN '%s' AND '%s';",
                Date.valueOf(startDate), Date.valueOf(endDate));
        List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayRollDataList = this.getEmployeePayrollData(resultSet);
        }catch (SQLException e) {
            e.printStackTrace();
        }
            return employeePayRollDataList;
    }

    private List<EmployeePayRollData> getEmployeePayrollDataUsingDb(String sql) {
        List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(sql);
            employeePayRollDataList = this.getEmployeePayrollData(resultSet);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayRollDataList;
    }

    List<EmployeePayRollData> getEmployeePayrollData(String name) {
        List<EmployeePayRollData> employeePayRollDataList = null;
        if (this.employeePayrollDataStatement == null) {
            this.prepareStatementForEmployeeData();
        }
       try {
           employeePayrollDataStatement.setString(1, name);
           ResultSet resultSet = employeePayrollDataStatement.executeQuery();
           employeePayRollDataList = this.getEmployeePayrollData(resultSet);
       }catch (SQLException e) {
           e.printStackTrace();
       }
       return employeePayRollDataList;
    }

    private void prepareStatementForEmployeeData() {
        try {
            Connection connection = this.getConnection();
            String sql = "select * from employee_payroll where name = ?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private List<EmployeePayRollData> getEmployeePayrollData(ResultSet resultSet){
        List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
        try {
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

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayRollDataList;
    }

    public Map<String, Double> getAverageSalaryByGender() throws SQLException {
        String sql = "select gender , AVG(basicpay) as avg_salary from employee_payroll GROUP BY gender";
        Map<String,Double> genderToAverageSalaryMap = new HashMap<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Double basicpay = resultSet.getDouble("avg_salary");
                genderToAverageSalaryMap.put(gender,basicpay);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return genderToAverageSalaryMap;
    }

    public EmployeePayRollData addEmployeeToPayRollUc8(String name, int phoneNo, String address, String department, String gender, int basicpay, int deductions, int taxablepay, int income_tax, int net_pay, LocalDate start) throws SQLException {
        int id = -1;
        Connection connection = null;
        EmployeePayRollData employeePayRollData = null;
        try {
            connection = this.getConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement statement = connection.createStatement()){
            String sql  = String.format( "insert into employee_payroll(name,phoneNo,address,department,gender,basicpay,deductions,taxablepay,income_tax,net_pay,start) values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"
                    ,name,phoneNo,address,department,gender,basicpay,deductions,taxablepay,income_tax,net_pay,start);
            int rowAffected = statement.executeUpdate(sql,statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet  = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement statement = connection.createStatement()){
            deductions = (int) (basicpay * 0.2);
             taxablepay = basicpay - deductions;
             income_tax = (int) (taxablepay * 0.1);
             net_pay =  basicpay - income_tax;
            String sql  = String.format( "insert into payroll_details(employee_id,basicpay,deductions,taxablepay,income_tax,Net_Pay) values('%s','%s','%s','%s','%s','%s')"
                   ,id,basicpay,deductions,taxablepay,income_tax,net_pay);
            int rowAffected = statement.executeUpdate(sql);
            if (rowAffected == 1) {
                employeePayRollData = new EmployeePayRollData(id, basicpay,deductions ,taxablepay, income_tax, net_pay);
            }
        }
        return employeePayRollData;
    }


    public EmployeePayRollData addEmployeeToPayRoll(String name, int phoneNo, String address, String department, String gender, int basicpay, int deductions, int taxablepay, int income_tax, int net_pay, LocalDate start) {
        int id = -1;
        Connection connection  = null;
        EmployeePayRollData employeePayRollData = null;
        try {
            connection  = this.getConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try (  Statement statement = connection.createStatement()){
            String sql  = String.format( "insert into employee_payroll(name,phoneNo,address,department,gender,basicpay,deductions,taxablepay,income_tax,net_pay,start) values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"
                    ,name,phoneNo,address,department,gender,basicpay,deductions,taxablepay,income_tax,net_pay,start);
            int rowAffected = statement.executeUpdate(sql,statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet  = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
            employeePayRollData = new EmployeePayRollData(id, name, phoneNo, address, department, gender, basicpay, deductions, taxablepay, income_tax, net_pay, start);

        }catch (SQLException e) {
            e.printStackTrace();
        }

         return employeePayRollData;
    }

    public EmployeePayRollData putDataIntoCompanyData(int dept_id, String dept_name) {
        EmployeePayRollData employeePayRollData = null;
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            String sql  = String.format( "insert into department(dept_id,dept_name) values('%s','%s')"
                    ,dept_id,dept_name);
            int rowAffected = statement.executeUpdate(sql,statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet  = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    dept_id = resultSet.getInt(1);
                }

            }
        employeePayRollData = new EmployeePayRollData(dept_id,dept_name);
        }catch (SQLException e) {
            e.printStackTrace();
        }
       return employeePayRollData;
    }

    public EmployeePayRollData deleteRecordFromCompanyData(int dept_id,String dept_name) {
        EmployeePayRollData employeePayRollData = null;
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            String sql  = String.format("delete from department where dept_id = '%s';",dept_id);
            int rowAffected = statement.executeUpdate(sql,statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet  = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    dept_id = resultSet.getInt(1);
                }

            }
            employeePayRollData = new EmployeePayRollData(dept_id,dept_name);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayRollData;
    }
}



