package jdbcDemo;

import java.sql.*;
import java.util.Scanner;

public class JDBCCRUDOperations {
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
        System.out.println("CRUD Operations");
        System.out.println("****************");
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. Update Student");
        System.out.println("4. Find Student");
        System.out.println("5. Filter Student");
        System.out.println("6. Exit");
        System.out.println("Enter your Choice: ");

    }
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = getConnection("ksrct");
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rSet = null;
        
        int choice;
        do {
            String name;
            int rollNo;
            float cgpa;
            String sql = null;
            mainMenu();
            choice = sc.nextInt();
            switch (choice){
                case 1:
                    sc.nextLine();
                    name = sc.nextLine();
                    rollNo = sc.nextInt();
                    cgpa = sc.nextFloat();
                    sql = "insert into student values(" + rollNo + ",'" + name + "'," + cgpa + ")";
                    if(stmt.executeUpdate(sql)!=0)
                        System.out.println("Record has been inserted");
                    else
                        System.out.println("Insertion error!");
                    break;
                case 2:
                    rollNo = sc.nextInt();
                    sql = "delete from student where id =" + rollNo;
                    if(stmt.executeUpdate(sql)!=0)
                        System.out.println("Record has been removed successfully!");
                    else
                        System.out.println("Deletion error");
                    break;
                case 3:
                    rollNo = sc.nextInt();
                    sql = "select * from student where id=" + rollNo;
                    rSet = stmt.executeQuery(sql);
                    if (rSet.next()) {
                        name = sc.nextLine();
                        name = sc.nextLine();
                        cgpa = sc.nextFloat();
                        sql = "update student set  name='" + name +"', cgpa=" + cgpa + " where id = "+ rollNo;
                        if(stmt.executeUpdate(sql)!=0)
                            System.out.println("Record has been updated successfully!");
                        else
                            System.out.println("Updation error");
                    }
                    else
                        System.out.println("Invalid student Id");
                    break;
                case 4:
                    rollNo = sc.nextInt();
                    sql = "select * from student where id=" + rollNo;
                    rSet = stmt.executeQuery(sql);
                    if (rSet.next()) {
                        System.out.println("{" + rSet.getInt(1) +","+ rSet.getString(2)+ "," + rSet.getFloat(3) + "}");
                    }
                    else
                        System.out.println("Invalid id!");
                    break;
                case 5:
                    System.out.println("Enter condition (fields : id, name, cgpa) : ");
                    String condition = sc.nextLine();
                    condition = sc.nextLine();
                    sql = "select * from student where " + condition;
                    rSet = stmt.executeQuery(sql);
                    while(rSet.next()) {
                        System.out.println(rSet.getInt(1) +","+ rSet.getString(2)+ "," + rSet.getFloat(3));
                    }
                    break;
                case 6:
                    System.out.println("Thank you!");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }while(choice != 6);
        stmt.close();
        con.close();
    }

}
