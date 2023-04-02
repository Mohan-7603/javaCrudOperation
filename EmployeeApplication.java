package jdbcDemo;

import java.sql.*;
import java.util.Scanner;

public class EmployeeApplication {
    public static Connection getConnection(String dbName)throws SQLException {
        String url = "jdbc:mysql://localhost:3306/"+dbName;
        String user = "root";
        try {
            return DriverManager.getConnection(url,user,"");
        }catch(SQLException ex) {
            throw ex;
        }
    }
    public static  void mainMenu(){
        System.out.println("1. Add Employee");
        System.out.println("2. Delete Employee");
        System.out.println("3. Upraise  Salary");
        System.out.println("4. Resigned Employee");
        System.out.println("5. Filter Employee");
        System.out.println("6. Exit");
        System.out.println("Enter your Choice: ");

    }
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = getConnection("employeedetails");
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rSet = null;
        int choice;
        do{
            String name;
            int empId;
            String dept;
            int salary;
            boolean status;
            String sql = null;
            mainMenu();
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter Employee Name : ");
                    sc.nextLine();
                    name = sc.nextLine();
                    System.out.println("Enter Employee Id : ");
                    empId = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter Employee DeptName : ");
                    dept = sc.nextLine();
                    System.out.println("Enter Employee Salary : ");
                    salary = sc.nextInt();
                    sql = "insert into employee values(" + empId + ",'" + name + "','" + dept + "'," + salary + "," + 1 + ")";
                    System.out.println(sql);
                    if(stmt.executeUpdate(sql)!=0)
                        System.out.println("Record has been inserted");
                    else
                        System.out.println("Insertion error!");
                    break;
                case 2:
                    empId = sc.nextInt();
                    sql = "update employee set status=0 where id =" + empId;
                    if(stmt.executeUpdate(sql)!=0)
                        System.out.println("Record has been removed successfully!");
                    else
                        System.out.println("Deletion error");
                    break;
                case 3:
                    int percentage = sc.nextInt();
                    sql = "update employee set salary = (salary +(salary*" + percentage+ ")/100)";
                    if(stmt.executeUpdate(sql)!=0)
                        System.out.println("Salary has been raised successfully!");
                    else
                        System.out.println("Upraisal error");
                    break;
                case 4:
                    sql = "select * from employee where status=0";
                    rSet = stmt.executeQuery(sql);
                    if (rSet.next()) {
                        System.out.println(rSet.getInt(1) +","+ rSet.getString(2)+ "," +
                                rSet.getString(3)+","+ rSet.getInt(4)+ "," + rSet.getInt(5));
                    }
                    else
                        System.out.println("No Employees Resigned!");
                    break;
                case 5:
                    System.out.println("Enter Department name to Filter: ");
                    String condition = sc.nextLine();
                    condition = sc.nextLine();
                    sql = "select * from employee where department='" + condition +"'";
                    rSet = stmt.executeQuery(sql);
                    while(rSet.next()) {
                        System.out.println(rSet.getInt(1) +","+ rSet.getString(2)+ "," +
                                rSet.getString(3)+","+ rSet.getInt(4)+ "," + rSet.getInt(5));
                    }
                    break;
                case 6:
                    System.out.println("Thank you!");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }while(choice!=6);
        stmt.close();
        con.close();
    }
}
