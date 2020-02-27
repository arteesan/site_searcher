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
//        String tableName = "testHiveDriverTable";
//        stmt.execute("drop table if exists " + tableName);
//        stmt.execute("create table " + tableName + " (key int, value string)");
//        // show tables
//        String sql = "show tables '" + tableName + "'";
//        System.out.println("Running: " + sql);
//        ResultSet res = stmt.executeQuery(sql);
//        if (res.next()) {
//            System.out.println(res.getString(1));
//        }
        // describe table
//        sql = "describe " + tableName;
//        System.out.println("Running: " + sql);
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(res.getString(1) + "\t" + res.getString(2));
//        }


// load data into table
// NOTE: filepath has to be local to the hive server
// NOTE: /tmp/a.txt is a ctrl-A separated file with two fields per line
//        String filepath = "/tmp/a.txt";
//        sql = "load data local inpath '" + filepath + "' into table " + tableName;
//        System.out.println("Running: " + sql);
//        stmt.execute(sql);

// select * query
//        sql = "select * from " + tableName;
//        System.out.println("Running: " + sql);
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
//        }

// regular hive query
//        sql = "select count(1) from " + tableName;
//        System.out.println("Running: " + sql);
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(res.getString(1));
//        }
//    }
//}