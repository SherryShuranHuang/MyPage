import java.sql.Statement;
import java.sql.DriverManager;

import org.sqlite.JDBC;

public class SqlDB {

	public static void createTbl(String tblName){
        try {
           Class.forName("org.sqlite.JDBC");
           java.sql.Connection connection = null;
           //create a database connection
           connection = DriverManager.getConnection("jdbc:sqlite:E:/eclipseWorkforJava/MyPage/db/movieslist_new.db");
           //connection.setAutoCommit(false);
           Statement statement = connection.createStatement();
           statement.setQueryTimeout(30);  // set timeout to 30 sec.
           try {
                statement.executeUpdate("drop table if exists movietable");
        	    String sql = "CREATE TABLE " + tblName
                       + "(id             integer   NOT NULL,"
                       + "movie_name      string    NOT NULL, "
                       + "cast_age		  integer   NOT NULL)";
        	    
        	    statement.executeUpdate(sql);
        	    
//                statement.executeUpdate("create table movietbl(id integer, name string)");
//                statement.executeUpdate("drop table if exists casttbl");
//                statement.executeUpdate("create table casttbl(id integer, age integer)");        	   
        	    
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
	        	setConnection = DriverManager.getConnection("jdbc:sqlite:E:/eclipseWorkforJava/MyPage/db/movieslist_new.db");
	        	System.out.println("Opened database successfully");
	        	/*Create sql statement*/
	        	setStatement = setConnection.createStatement();
	        	try{
	        		String sql ="insert into "+ table+" values("+ statement+",0)"; // 0 is the initial average age value
	        		setStatement.executeUpdate(sql);
	        		
	        	}catch (Exception e) {
	        		System.err.println(e.getClass().getName() + ": " + e.getMessage()+"--can not insert into tables");
	        	}

	        	setStatement.close();
	        	setConnection.close();
	        } catch (Exception e2) {
	        	System.err.println(e2.getClass().getName() + ": " + e2.getMessage()+"--cannot open tables");
	        }
	}
	public static void updateAge(String table,int id, int age){
	       java.sql.Connection setConnection = null;
	        Statement setStatement = null;

	        try {
	       	/*Create connection with database*/
	        	Class.forName("org.sqlite.JDBC");
	        	setConnection = DriverManager.getConnection("jdbc:sqlite:E:/eclipseWorkforJava/MyPage/db/movieslist_new.db");
	        	System.out.println("Opened database successfully");
	        	/*Create sql statement*/
	        	setStatement = setConnection.createStatement();
	        	try{

	        		String sql = "update "+ table +" set cast_age = "+ age +" where id = " + id;        	    
	        		setStatement.executeUpdate(sql);
	        		
	        	}catch (Exception e) {
	        		System.err.println(e.getClass().getName() + ": " + e.getMessage()+"--cannot update age");
	        	}

	        	setStatement.close();
	        	setConnection.close();
	        } catch (Exception e2) {
	        	System.err.println(e2.getClass().getName() + ": " + e2.getMessage()+"--cannot open tables");
	        }
	}
	
//	public static void dbToCsv(){
//	       java.sql.Connection setConnection = null;
//	        Statement setStatement = null;
//
//	        try {
//	        	/*Create connection with database*/
//	        	Class.forName("org.sqlite.JDBC");
//	        	setConnection = DriverManager.getConnection("jdbc:sqlite:E:/eclipseWorkforJava/MyPage/db/movieslist1.db");
//	        	System.out.println("Opened database successfully");
//	        	/*Create sql statement*/
//	        	setStatement = setConnection.createStatement();
//	        	
//	        	 
//	        	try{
////	        		setStatement.executeUpdate(".open movieslist1.db");
////	        		setStatement.executeUpdate(".headers on");
////	        		setStatement.executeUpdate(".mode csv");
////	        		setStatement.executeUpdate(".output resulttest.csv");
//	        		
//	        		setStatement.executeUpdate("select name, age from movietbl, casttbl where movietbl.id = casttbl.id");
//	        		
//	        	}catch (Exception e) {
//	        		System.err.println(e.getClass().getName() + ": " + e.getMessage()+"--cannot open tables");
//	        	}
//
//	        	setStatement.close();
//	        	setConnection.close();
//	        } catch (Exception e2) {
//	        	System.err.println(e2.getClass().getName() + ": " + e2.getMessage()+"--cannot execute updates");
//	        }
//	}
//	  public static void main(String[]args)  
//	    {  
//	    	//SqlDB.createTbl("movietbl");
//	    	//SqlDB.insertIntoTable("movietbl", "mokingJay");
//	    	//SqlDB.insertIntoTable("movietbl", 0 +",'mokingJay'");
//	    	SqlDB.updateAge("movietbl", 0, 23);
//    	    
//	    }       
}
