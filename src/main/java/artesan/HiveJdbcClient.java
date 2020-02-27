package artesan;

import java.sql.*;

public class HiveJdbcClient {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    private  Statement stmt;

    public void init() {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            Connection con = DriverManager.getConnection("jdbc:hive2://192.168.233.140:10000/nartemenko", "admin", "admin");
            this.stmt = con.createStatement();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    public ResultSet executeSelect (String sql){
        try {
            return stmt.executeQuery(sql);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void execute (String sql){
        try {
            stmt.execute(sql);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
