package sconti.ukp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Constants {
	public Constants() {

	}

	
	public static void setValues() {
		int point = 0;
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			System.out.println("Local HostAddress: " + addr.getHostAddress());
			String hostname = addr.getHostName();
			System.out.println("Local host name: " + hostname);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (addr.getHostAddress().contains("192.168.1.22")) {
			point = 2;
		} else if (addr.getHostAddress().contains("192.168.1.101")) {
			point = 1;
		} else if (!addr.getHostAddress().contains("192.168.2.81")) {
			//10.10.31.147
			point = 3;
		}
		//point = 1;
		MODE = point;
		IPAdds = addr.getHostAddress();
	}

	public static String IPAdds = "";

	public static int MODE = 0; // 1 for dev, 2- for stage 3 for prod 0 for local

	public static String BASE_URL = "http://localhost:8888/";
	
	public static String UKPDBName = "ukp";
	public static String UKPDBServer = "127.0.0.1:3306";
	public static String UKPDBUserName = "devadmin";
	public static String UKPDBPwd = "Sconti@0987";

	
	public static String UKPMySQLURL = "jdbc:mysql://"+UKPDBServer+"/"+UKPDBName;
	
	public static String SMSURL = "http://smspush.openhouseplatform.com/smsmessaging/1/outbound/tel%3A%2BKSRSAC/requests";

	public static void setBaseURLs() {
		setValues();
		if (MODE == 0) {

		} else if (MODE == 1) {
			BASE_URL = "http://117.254.85.204:8086/";
			DBCreateurl = DevIPAdds+"\\SQLEXPRESS;user="+StageUserName+";password="+StagePassword+"";
			DRIVE = "D";
			url = "jdbc:sqlserver://192.168.1.101\\SQLEXPRESS"+";user="+"sa"+";password="+"$cont!@123"+";database=";
			Dbresource.DBNAME = "UKPDEV";

		} else if (MODE == 2) {
			BASE_URL = "http://117.254.85.199:8085/";
			DBCreateurl =  StageIPAdds+"\\SQLEXPRESS;user="+StageUserName+";password="+StagePassword+"";
			DRIVE = "E";
		} else if (MODE == 3) {
			BASE_URL = "https://kgis.ksrsac.in:9000/";
			DBCreateurl =  ProdIPAdds+";user="+ProdUserName+";password="+ProdPassword+"";
			SMSURL = "http://10.10.30.183/smsmessaging/1/outbound/tel%3A%2BKSRSAC/requests";
			DRIVE = "E";
		}
		System.out.println("New:"+url);
		
	}
	
// nothing	
	
	public static void setValues(String dbname, int value) {
		int point = 1;
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			System.out.println("Local HostAddress: " + addr.getHostAddress());
			String hostname = addr.getHostName();
			System.out.println("Local host name: " + hostname);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (addr.getHostAddress().contains("192.168.1.22")) {
			point = 2;
		} else if (addr.getHostAddress().contains("192.168.1.43") || addr.getHostAddress().contains("192.168.2.22")) {
			point = 1;
		} 
		else if (addr.getHostAddress().contains("10.10.31.147")) {
			//10.10.31.147
			point = 4;
		}
		
		else if (!addr.getHostAddress().contains("192.168.2.22")) {
			//10.10.31.147
			point = 3;
		}
		if(value==99)
		{
			point = 3;
		}
		
		if(value==999)
		{
			point = 4;
		}
		
		if(value==9999)
		{
			point = 5;
		}
		MODE = point;
		IPAdds = addr.getHostAddress();
		if(dbname!=null && !dbname.isEmpty())
		{
			DBName = dbname;
		}
		
	}

	public static String DBName = "AdministrativeBoundaries";
	

	public static String DRIVE = "D";

	public static String USER = "patelheggere";
	public static String PASS = "ksrsac@10883";
	
	public static void setBaseURLs(String dbname, int value) {
		setValues(dbname, value);
		if (MODE == 0) {

		} else if (MODE == 1) {
			BASE_URL = "http://117.254.85.204:8086/";
			DBCreateurl = DevIPAdds+"\\SQLEXPRESS;user="+StageUserName+";password="+StagePassword+"";
			USER = DevUserName;
			PASS = DevPassword;
		} else if (MODE == 2) {
			BASE_URL = "http://117.254.85.199:8085/";
			DBCreateurl =  StageIPAdds+"\\SQLEXPRESS;user="+StageUserName+";password="+StagePassword+"";
			USER = StageUserName;
			PASS = StagePassword;
		} else if (MODE == 3) {
			BASE_URL = "https://kgis.ksrsac.in:9000/";
			SMSURL = "http://10.10.30.183/smsmessaging/1/outbound/tel%3A%2BKSRSAC/requests";
			DBCreateurl =  ProdIPAdds+";user="+ProdUserName+";password="+ProdPassword+"";

			USER = ProdUserName;
			PASS = ProdPassword;
		}
		else if (MODE == 4) {
			BASE_URL = "https://kgis.ksrsac.in:9000/";
			DBCreateurl =  ProdIPAdds2+";user="+ProdUserName2+";password="+ProdPassword2+"";
			SMSURL = "http://10.10.30.183/smsmessaging/1/outbound/tel%3A%2BKSRSAC/requests";
			USER = ProdUserName;
			PASS = ProdPassword;
		}
		else if (MODE == 5) {
			BASE_URL = "https://kgis.ksrsac.in:9000/";
			DBCreateurl =  ProdIPAdds3+";user="+ProdUserName3+";password="+ProdPassword3+"";
			SMSURL = "http://10.10.30.183/smsmessaging/1/outbound/tel%3A%2BKSRSAC/requests";
			DBCreateurl =  ProdIPAdds3+";user="+ProdUserName3+";password="+ProdPassword3+"";
			USER = ProdUserName;
			PASS = ProdPassword;
		}
		System.out.println(url);
		
	}
	
	public static final String ProdUserName = "dbuser";
	public static final String ProdUserName2 = "ksrsacdbuser";
	public static final String ProdUserName3 = "ksrsacdbuser";
	public static final String StageUserName = "sa";
	public static final String DevUserName = "patelheggere";
	
	public static final String ProdPassword = "Ksrsacdb#1234";
	public static final String ProdPassword2 = "ksrsacdbuser@123";
	public static final String ProdPassword3 = "ksrsacdbuser@123";
	public static final String StagePassword = "Ksrsacdb#1234";
	public static final String DevPassword = "ksrsac@10883";
	
	public static final String ProdIPAdds = "jdbc:sqlserver://172.31.33.130";
	public static final String ProdIPAdds2 = "jdbc:sqlserver://172.31.17.226";
	public static final String ProdIPAdds3 = "jdbc:sqlserver://172.31.17.226";
	public static final String StageIPAdds = "jdbc:sqlserver://192.168.1.23";
	public static final String DevIPAdds= "jdbc:sqlserver://192.168.1.45";
	
	public static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	//public static String url = DevIPAdds+"\\SQLEXPRESS;user="+DevUserName+";password="+DevPassword+";database=";
	public static String DBCreateurl = DevIPAdds+"\\SQLEXPRESS;user="+DevUserName+";password="+DevPassword+"";

	public static String mysql_driver = "com.mysql.jdbc.Driver";

	
	public static String url = "jdbc:sqlserver://svm.cssoc8dxnisl.ap-south-1.rds.amazonaws.com:1433"+";user="+"admin"+";password="+"f0hskDREQmyzW]cCh"+";database=";

	
//	public static String url  = "jdbc:sqlserver://10.10.5.122\\SQLEXPRESS"+";user="+"sa"+";password="+"$cont!@123"+";database=";
	
	
	//public String URLICMR = "jdbc:sqlserver://172.31.17.226;user=ksrsacdbuser;password=ksrsacdbuser@123;database= CZBZ_Watch";

//	public String PROD_TEST = "jdbc:sqlserver://172.31.17.226;user=ksrsacdbuser;password=ksrsacdbuser@123;database= CZBZ_Watch";


 

    
    //production
	/*public  static String url = "jdbc:sqlserver://172.31.33.130;user=dbuser;password=Ksrsacdb#1234;database= CADASTRAL_V2";
	 public  static String url1 = "jdbc:sqlserver://172.31.33.130;user=dbuser;password=Ksrsacdb#1234;database= KGISApplication_V2";
     public  static String url2 = "jdbc:sqlserver://172.31.33.130;user=dbuser;password=Ksrsacdb#1234;database= KGISAdminBoundary_V2";
*/
	


	public static void finallyblock(Connection conn, CallableStatement cStmt,ResultSet rs)
	{
		if(conn !=null)
			{
		      try {
				conn.close();
			} catch (SQLException e) {
			}
			}
			if(cStmt != null)
			{
				try {
					cStmt.close();
				} catch (SQLException e) {
				}
			}	
			if(rs != null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}	
		}		
	
	
	public static void finallyblock(Connection conn, PreparedStatement ps,ResultSet rs)
	{
		if(conn !=null)
			{
		      try {
				conn.close();
			} catch (SQLException e) {
			}
			}
			if(ps != null)
			{
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}	
			if(rs != null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}	
		}		
	
}