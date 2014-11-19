import java.sql.Statement;
import java.sql.DriverManager;

import org.sqlite.JDBC;

public class SqlDB {

	public static void createTbl(){
        try {
           Class.forName("org.sqlite.JDBC");
           java.sql.Connection connection = null;
           //create a database connection
           connection = DriverManager.getConnection("jdbc:sqlite:E:/eclipseWorkforJava/MyPage/db/movieslist1.db");
           //connection.setAutoCommit(false);
           Statement statement = connection.createStatement();
           statement.setQueryTimeout(30);  // set timeout to 30 sec.
           try {
                statement.executeUpdate("drop table if exists movietable");
                statement.executeUpdate("create table movietbl(id integer, name string)");
                statement.executeUpdate("drop table if exists casttbl");
                statement.executeUpdate("create table casttbl(id integer, age integer)");

                System.out.println("Table insert successfully");
           } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage()+"--cannot create tables");
           }

           statement.close();
           connection.close();
           
       } catch (Exception e2) {
           System.err.println(e2.getClass().getName() + ": " + e2.getMessage()+"--cannot build connection");
       }
        System.out.println("Table created successfully");
   }
	
	public static void insertIntoTable(String table, String statement){
	       java.sql.Connection setConnection = null;
	        Statement setStatement = null;

	        try {
	        	/*Create connection with database*/
	        	Class.forName("org.sqlite.JDBC");
	        	setConnection = DriverManager.getConnection("jdbc:sqlite:E:/eclipseWorkforJava/MyPage/db/movieslist1.db");
	        	System.out.println("Opened database successfully");
	        	/*Create sql statement*/
	        	setStatement = setConnection.createStatement();
	        	try{
	        		setStatement.executeUpdate("insert into "+ table+" values("+ statement+")");
	        	}catch (Exception e) {
	        		System.err.println(e.getClass().getName() + ": " + e.getMessage()+"--cannot open tables");
	        	}

	        	setStatement.close();
	        	setConnection.close();
	        } catch (Exception e2) {
	        	System.err.println(e2.getClass().getName() + ": " + e2.getMessage()+"--cannot execute updates");
	        }
	}
	        
}
