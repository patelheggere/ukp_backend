package sconti.ukp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class Global {

	Logger logs = Logger.getLogger("Global");
	

	
	/*public String url = "jdbc:sqlserver://172.31.17.226;user=ksrsacdbuser;password=ksrsacdbuser@123;database= CZBZ_Watch";
	public String url1 = "jdbc:sqlserver://172.31.17.226;user=ksrsacdbuser;password=ksrsacdbuser@123;database= COVID_19";
	*/
	
	public void finallyblock(Connection conn,PreparedStatement ps,ResultSet rs)
	{
		if(conn != null)
		{
			try {
				conn.close();
			} 
			catch (SQLException e) {
				logs.error("exc at finallyblock.."+e.getMessage());
			}
		}
		if(ps != null)
		{
			try {
				ps.close();
			}
			catch (SQLException e) {
				logs.error("exc at finallyblock.."+e.getMessage());
			}
		}
		if(rs != null)
		{
			try {
				rs.close();
			} 
			catch (SQLException e) {
				logs.error("exc at finallyblock.."+e.getMessage());
			}
		}
	}
	
	public void finallyblock(Connection conn,PreparedStatement ps)
	{
		if(conn != null)
		{
			try {
				conn.close();
			} 
			catch (SQLException e) {
				logs.error("exc at finallyblock.."+e.getMessage());
			}
		}
		if(ps != null)
		{
			try {
				ps.close();
			}
			catch (SQLException e) {
				logs.error("exc at finallyblock.."+e.getMessage());
			}
		}

	}
	
	public void finallyblock(Connection conn,CallableStatement ps)
	{
		if(conn != null)
		{
			try {
				conn.close();
			} 
			catch (SQLException e) {
				logs.error("exc at finallyblock.."+e.getMessage());
			}
		}
		if(ps != null)
		{
			try {
				ps.close();
			}
			catch (SQLException e) {
				logs.error("exc at finallyblock.."+e.getMessage());
			}
		}

	}
}



