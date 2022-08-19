package sconti.ukp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import Model.APIResponseModel;
import Model.AttendanceTransSyncModel;
import Model.DSRInsertionModel;
import Model.DSRMainModel;
import Model.DSRModelClass;
import Model.DSRSubModel;
import Model.DeptDataModel;
import Model.DistModel;
import Model.DocumentModel;
import Model.EditDsrSubModel;
import Model.EditDsrmainModel;
import Model.EmpDetList;
import Model.FieldMeasurementsModel;
import Model.FloorPlanElementMainModel;
import Model.FloorPlanElementsModel;
import Model.FmReportByGenInfoModel;
import Model.GenInfoReport;
import Model.GeneralInfoModel;
import Model.GstValueModel;
import Model.HobliModel;
import Model.InsertUserModel;
import Model.LatestFMModel;
import Model.RateOfDepreciationModel;
import Model.ReportModel;
import Model.StaffRegDataModel;
import Model.StrElementModelClass;
import Model.SubDsrDetailsModel;
import Model.TalukModel;
import Model.TextEntryModel;
import Model.UpdateFMModel;
import Model.UpdatePwdModel;
import Model.UpdateTextEntryModel;
import Model.UserDetailsModel;
import Model.UserRolesModel;
import Model.VillageModel;

public class Dbresource extends Global {
	Logger log = Logger.getLogger(Dbresource.class);
//	public String DBNAME = "UKPDEV";
	public static String DBNAME = "SVM_DEV";

	String username = "Mobile_1-KSRSAC";
	String password = "ksrsac@1234";
	String senderid = "KSRSAC";
	String secureKey = "3812db87-b000-4b8a-aa05-a813485e7fdd";
	String message = "Training@KSRSAC\r\n" + "        Topic:Testing\r\n" + "        From Date:20/04/2021 \r\n"
			+ "        To Date:20/04/2021\r\n" + "        Venue:KSRSAC\r\n" + "        20/04/2021\r\n"
			+ "        -KSRSAC";

	String otpMessage = "123456" + " is OTP for registration .PLS DO NOT SHARE WITH ANYONE.-KSRSAC";
	SMSServices sMSServices = new SMSServices();

	// sMSServices.sendSingleSMS(username, password, message, senderid,
	// "9620000944", secureKey, "1107161001225505566");
	// sMSServices.sendSingleSMS(username, password, otpMessage, senderid,
	// "9620000944", secureKey, "1107161001230026638");

	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		System.setProperty("current.date", dateFormat.format(new Date()));
		Constants.setBaseURLs();
	}

	Logger logger = Logger.getLogger(Dbresource.class);

	public static String DBName = null;

	List<String> names;

	public List<String> getDbs(String query, int value) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Constants.setBaseURLs(DBName, value);

		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url);
			names = new ArrayList();
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet res = meta.getCatalogs();
			System.out.println("List of databases: ");
			while (res.next()) {
				System.out.println(res.getString(query));
				names.add(res.getString(query));
			}
			res.close();
			return names;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getTables(String dbName, String column, int value) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Constants.setBaseURLs(dbName, value);
		try {
			Class.forName(Constants.driver);
			names = new ArrayList();

			conn = DriverManager.getConnection(Constants.url);
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM  " + dbName + ".INFORMATION_SCHEMA.TABLES";

			rs = stmt.executeQuery(sql);
			System.out.println("List of Tables: ");
			while (rs.next()) {
				System.out.print(rs.getString(column));
				System.out.println();
				names.add(rs.getString(column));
			}

			if (conn != null)
				conn.close();
			return names;
		} catch (Exception e) {
			e.printStackTrace();
			return names;
		}
	}

	public List<String> getColmns(String dbName, String tblName, int value) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Constants.setBaseURLs(dbName, value);
		try {
			Class.forName(Constants.driver);
			names = new ArrayList();
			conn = DriverManager.getConnection(Constants.url);
			Statement stmt = conn.createStatement();
			String sql = " SELECT *  FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tblName + "'";
			rs = stmt.executeQuery(sql);
			System.out.println("List of columns: ");
			while (rs.next()) {
				System.out.print(rs.getString(4));
				System.out.println();
				names.add(rs.getString(4));

			}

			if (conn != null)
				conn.close();
			return names;
		} catch (Exception e) {
			e.printStackTrace();
			return names;
		}
	}

	public List<DataModel> getData(String dbName, String tblName, String sql, int value) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		DataModel dataModel = new Gson().fromJson(sql, DataModel.class);
		List<DataModel> dataList = new ArrayList();
		Constants.setBaseURLs(dbName, value);
		try {
			Class.forName(Constants.driver);
			names = new ArrayList();
			conn = DriverManager.getConnection(Constants.url);
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(dataModel.getSql());
			int i = 0;
			while (rs.next()) {
				DataModel dataModel2 = new DataModel();
				Map<String, Object> mapdata = new HashMap();
				for (int j = 0; j < dataModel.getColumns().size(); j++) {
					String col = dataModel.getColumns().get(j);
					mapdata.put(col, rs.getString(col));
				}
				dataModel2.setMapdata(mapdata);
				dataList.add(dataModel2);
			}

			if (conn != null)
				conn.close();
			return dataList;
		} catch (Exception e) {
			e.printStackTrace();
			return dataList;
		}
	}

	public String CreateDB(String data) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		DataModel dataModel = new Gson().fromJson(data, DataModel.class);
		Constants.setBaseURLs(dataModel.getDbName(), dataModel.getType());
		try {
			Class.forName(Constants.driver);
			if (dataModel.getDbName() == null) {
				conn = DriverManager.getConnection(Constants.DBCreateurl);
			} else {
				conn = DriverManager.getConnection(Constants.url);
			}
			Statement stmt = conn.createStatement();

			stmt.executeUpdate(dataModel.getSql());
			System.out.println("Query Executed successfully...");

			if (conn != null)
				conn.close();
			return "Query Executed successfully...";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
	}

	public String getBaseURL(String dbName, int value) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		List<DataModel> dataList = new ArrayList();
		Constants.setBaseURLs(dbName, value);
		try {
			Class.forName(Constants.driver);

			return Constants.url;
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	public String Otp_generation(String mobile_no) {
		String otpstr = "";

		String mob = mobile_no;
		otpstr = mob.substring(4, 6);
		System.out.println("otpstr" + otpstr);

		LocalTime now = LocalTime.now();
		System.out.println(now.getHour());
		int hr = now.getHour();

		String otp = otpstr.concat(String.valueOf(hr));

		System.out.println(otp);

		return otp;
	}

	public List<String> getObjectToList(Object obj) {
		List<String> params = new ArrayList<>();
		int i = 0;
		String str = "";
		for (Field field : obj.getClass().getDeclaredFields()) {
			// field.setAccessible(true); // if you want to modify private fields
			try {
				++i;
				// str+="'"+field.get(obj).toString()+"',";
				if (field.get(obj) == null)
					params.add("");
				else
					params.add(field.get(obj).toString());
				
			//	System.out.println("I insode:"+i+" Value:"+params.get(i-1));

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	//	System.out.println("Value:" + str);
	//	System.out.println("I Value:" + i);

		return params;
	}

	public APIResponseModel GetReport(String startDate, String mobile) {
		APIResponseModel apiResponseModel = new APIResponseModel();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call [HAT_GetAttendanceReport](?,?)}");
			cs.setString(1, startDate);
			// cs.setString(2, endDate);
			cs.setString(2, mobile);

			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<ReportModel>> resultHandler2 = new BeanListHandler<ReportModel>(ReportModel.class);
			List<ReportModel> dataList = runner.RsMapping(resultHandler2, rs);
			apiResponseModel.setmReportModelList(dataList);
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			return apiResponseModel;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			apiResponseModel.setMessage(e.getLocalizedMessage());
			return apiResponseModel;
		}
	}

	public APIResponseModel verifyUser(String uname, String pwd) {

		APIResponseModel apiResponseModel = new APIResponseModel();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);

			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call [USP_VerifyUser](?,?)}");
			cs.setString(1, uname);
			// cs.setString(2, endDate);
			cs.setString(2, pwd);

			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);
			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			return apiResponseModel;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			apiResponseModel.setMessage(e.getLocalizedMessage());
			return apiResponseModel;
		}
	}

	public String healthcheckV2(String[] firstArg, String f ) {
		//String[] stringArray = {firstArg, secondArg, thirdArg, fourthArg, fifthArg};
		try {
			File fil = new File(f);
			String cd = firstArg[0]+" \""+fil.getPath()+"\"";
			Process process = Runtime.getRuntime().exec(cd);
			InputStream inputStream = process.getInputStream(); 
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                log.error(line);
            }
            return line;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		
	}
	
	public String healthcheck(String[] firstArg ) {
		//String[] stringArray = {firstArg, secondArg, thirdArg, fourthArg, fifthArg};
		try {
			Process process = Runtime.getRuntime().exec(firstArg);
			InputStream inputStream = process.getInputStream(); 
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            return line;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		
	}
	
	public APIResponseModel GetDistricts() {

		APIResponseModel apiResponseModel = new APIResponseModel();

		String SQL_SELECT = "Select * from dist_master_tbl ";
		log.info(Constants.url + DBNAME);

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetAllDistrict}");

			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<DistModel>> resultHandler2 = new BeanListHandler<DistModel>(DistModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<DistModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmDistList(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}

		/*
		 * try { Class.forName(Constants.mysql_driver); } catch (ClassNotFoundException
		 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 * 
		 * try (Connection conn = DriverManager.getConnection(Constants.UKPMySQLURL,
		 * Constants.UKPDBUserName, Constants.UKPDBPwd)) {
		 * 
		 * if (conn != null) { PreparedStatement preparedStatement =
		 * conn.prepareStatement(SQL_SELECT);
		 * 
		 * ResultSet resultSet = preparedStatement.executeQuery();
		 * 
		 * ResultSetHandler<List<DistModel>> resultHandler = new
		 * BeanListHandler<DistModel>(DistModel.class); CustomQueryRunner runner = new
		 * CustomQueryRunner(); List<DistModel> distList =
		 * runner.RsMapping(resultHandler, resultSet);
		 * 
		 * if (resultSet != null) { resultSet.close(); } if (conn != null) {
		 * conn.close(); } apiResponseModel.setStatus(true);
		 * apiResponseModel.setStatusCode(200); apiResponseModel.setmDistList(distList);
		 * return apiResponseModel;
		 * 
		 * } else { System.out.println("Failed to make connection!"); }
		 * 
		 * } catch (SQLException e) { log.error(e.getLocalizedMessage());
		 * System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		 * apiResponseModel.setStatus(false); apiResponseModel.setStatusCode(500);
		 * apiResponseModel.setMessage(e.getLocalizedMessage());
		 * 
		 * } catch (Exception e) { e.printStackTrace();
		 * apiResponseModel.setStatus(false); apiResponseModel.setStatusCode(500);
		 * apiResponseModel.setMessage(e.getLocalizedMessage()); } finally {
		 * 
		 * }
		 */
	}

	public APIResponseModel EmpReg(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		AttendanceTransSyncModel AttendanceTransSyncModel = new Gson().fromJson(data, AttendanceTransSyncModel.class);
		Constants.setBaseURLs();
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(AttendanceTransSyncModel);
			int j = 0;
			String sql = "{call [HAT_INsertIntoTrans] (";
			for (int i = 0; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 0; i < fiedsList.size(); i++) {
				cs.setString(i + 1, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			System.out.println("Params:" + params);
			rs = cs.executeQuery();

			ResultSetHandler<APIResponseModel> resultHandler = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);
			CustomQueryRunner runner = new CustomQueryRunner();
			apiResponseModel = runner.RsMapping(resultHandler, rs);

			finallyblock(conn, cs);
			return apiResponseModel;

		} catch (Exception e) {
			finallyblock(conn, cs);
			log.error("/Register user : exc at register user..." + e.getMessage() + "\n User data Data:" + data);
			System.out.println("/register user : exc at register data..." + e.getMessage());
			e.printStackTrace();
			apiResponseModel.setStatus(false);
			apiResponseModel.setMessage(e.getLocalizedMessage());
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetTalukByDist(String did) {

		APIResponseModel apiResponseModel = new APIResponseModel();

		String SQL_SELECT = "Select * from dist_master_tbl ";
		log.info(SQL_SELECT);

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetTalukByDistID(?)}");
			cs.setString(1, did);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<DistModel>> resultHandler2 = new BeanListHandler<DistModel>(DistModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<DistModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmDistList(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}

		/*
		 * String SQL_SELECT = "SELECT * FROM `taluk_list` WHERE DistCode =" + did;
		 * log.info(SQL_SELECT);
		 * 
		 * try { Class.forName(Constants.mysql_driver); } catch (ClassNotFoundException
		 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 * 
		 * try (Connection conn = DriverManager.getConnection(Constants.UKPMySQLURL,
		 * Constants.UKPDBUserName, Constants.UKPDBPwd)) {
		 * 
		 * if (conn != null) { PreparedStatement preparedStatement =
		 * conn.prepareStatement(SQL_SELECT);
		 * 
		 * ResultSet resultSet = preparedStatement.executeQuery();
		 * 
		 * ResultSetHandler<List<TalukModel>> resultHandler = new
		 * BeanListHandler<TalukModel>(TalukModel.class); CustomQueryRunner runner = new
		 * CustomQueryRunner(); List<TalukModel> talukList =
		 * runner.RsMapping(resultHandler, resultSet);
		 * 
		 * if (resultSet != null) { resultSet.close(); } if (conn != null) {
		 * conn.close(); } apiResponseModel.setStatus(true);
		 * apiResponseModel.setStatusCode(200);
		 * apiResponseModel.setmTalukList(talukList); return apiResponseModel;
		 * 
		 * } else { System.out.println("Failed to make connection!"); }
		 * 
		 * } catch (SQLException e) { System.err.format("SQL State: %s\n%s",
		 * e.getSQLState(), e.getMessage()); apiResponseModel.setStatus(false);
		 * apiResponseModel.setStatusCode(500);
		 * apiResponseModel.setMessage(e.getLocalizedMessage());
		 * 
		 * } catch (Exception e) { log.error(e.getLocalizedMessage());
		 * e.printStackTrace(); apiResponseModel.setStatus(false);
		 * apiResponseModel.setStatusCode(500);
		 * apiResponseModel.setMessage(e.getLocalizedMessage()); } finally {
		 * 
		 * } return apiResponseModel;
		 */
	}

	public APIResponseModel GetHobliByTaluk(String tid) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		String SQL_SELECT = "SELECT * FROM `hobli_list` WHERE TalukCode =" + tid;
		log.info(SQL_SELECT);

		try {
			Class.forName(Constants.mysql_driver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Constants.UKPMySQLURL, Constants.UKPDBUserName,
				Constants.UKPDBPwd)) {

			if (conn != null) {
				PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);

				ResultSet resultSet = preparedStatement.executeQuery();

				ResultSetHandler<List<HobliModel>> resultHandler = new BeanListHandler<HobliModel>(HobliModel.class);
				CustomQueryRunner runner = new CustomQueryRunner();
				List<HobliModel> HobliList = runner.RsMapping(resultHandler, resultSet);

				if (resultSet != null) {
					resultSet.close();
				}
				if (conn != null) {
					conn.close();
				}
				apiResponseModel.setStatus(true);
				apiResponseModel.setStatusCode(200);
				apiResponseModel.setmHobliList(HobliList);
				return apiResponseModel;

			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			apiResponseModel.setMessage(e.getLocalizedMessage());

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			apiResponseModel.setMessage(e.getLocalizedMessage());
		} finally {

		}
		return apiResponseModel;
	}

	public APIResponseModel GetVillageByByHobli(String hid) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		String SQL_SELECT = "SELECT * FROM `village_list` WHERE HobliCode =" + hid;
		log.info(SQL_SELECT);
		try {
			Class.forName(Constants.mysql_driver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Constants.UKPMySQLURL, Constants.UKPDBUserName,
				Constants.UKPDBPwd)) {

			if (conn != null) {
				PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);

				ResultSet resultSet = preparedStatement.executeQuery();

				ResultSetHandler<List<VillageModel>> resultHandler = new BeanListHandler<VillageModel>(
						VillageModel.class);
				CustomQueryRunner runner = new CustomQueryRunner();
				List<VillageModel> talukList = runner.RsMapping(resultHandler, resultSet);

				if (resultSet != null) {
					resultSet.close();
				}
				if (conn != null) {
					conn.close();
				}
				apiResponseModel.setStatus(true);
				apiResponseModel.setStatusCode(200);
				apiResponseModel.setmVillageList(talukList);
				return apiResponseModel;

			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			apiResponseModel.setMessage(e.getLocalizedMessage());

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			apiResponseModel.setMessage(e.getLocalizedMessage());
		} finally {

		}
		return apiResponseModel;
	}
	// USP_InsertDSR

	public APIResponseModel InsertDSRMst(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		DistModel distModel = new DistModel();
		distModel = gson.fromJson(data, DistModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_InsertDSR(?,?,?)}");
			cs.setString(1, distModel.getCode());
			cs.setString(2, distModel.getName());
			cs.setString(3, distModel.getCreadted_by());
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertStrTypeMst(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		DistModel distModel = new DistModel();
		distModel = gson.fromJson(data, DistModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_InsertStrType(?,?,?)}");
			cs.setString(1, distModel.getCode());
			cs.setString(2, distModel.getName());
			cs.setString(3, distModel.getCreadted_by());
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertUOMMst(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		DistModel distModel = new DistModel();
		distModel = gson.fromJson(data, DistModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_InsertUOM(?,?,?)}");
			cs.setString(1, distModel.getCode());
			cs.setString(2, distModel.getName());
			cs.setString(3, distModel.getCreadted_by());
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertStrElementMst(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Gson gson = new Gson();
		StrElementModelClass model = gson.fromJson(data, StrElementModelClass.class);
		String SQL = "INSERT INTO `structure_element_mst`("
				+ " `Code`, `Name`, `Description`, `LabelPrefix`, `LengthRequired`,"
				+ " `HeighDepthRequired`, `BreadthWidthThicknessRequried`, `DiameterRequired`,"
				+ " `CircumferenceRequired`, `OrientationRequired`, `PlanRequired`, "
				+ "`CrossSectionRequired`, `QuantityRequired`, `XRequired`, `YRequired`,"
				+ " `UomID`, `VolumeFormula`, `AreaFormula`, `CreatedBy`," + " `ModifiedBy`) " + " VALUES (" + " '"
				+ model.getCode() + "','" + model.getName() + "','" + model.getDescription() + "','"
				+ model.getLabelPrefix() + "','" + model.getLengthRequired() + "'," + " '"
				+ model.getHeighDepthRequired() + "','" + model.getBreadthWidthThicknessRequried() + "','"
				+ model.getDiameterRequired() + "'," + " '" + model.getCircumferenceRequired() + "','"
				+ model.getOrientationRequired() + "','" + model.getPlanRequired() + "', " + " '"
				+ model.getCrossSectionRequired() + "','" + model.getQuantityRequired() + "','" + model.getXRequired()
				+ "','" + model.getYRequired() + "'," + " '" + model.getUomID() + "','" + model.getVolumeFormula()
				+ "','" + model.getAreaFormula() + "','" + model.getCreatedBy() + "', " + " '" + model.getModifiedBy()
				+ "')";
		log.info(SQL);
		try {
			Class.forName(Constants.mysql_driver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Constants.UKPMySQLURL, Constants.UKPDBUserName,
				Constants.UKPDBPwd)) {

			if (conn != null) {
				PreparedStatement preparedStatement = conn.prepareStatement(SQL);

				// ResultSet resultSet = preparedStatement.executeQuery();
				int res = preparedStatement.executeUpdate();

				/*
				 * ResultSetHandler<List<VillageModel>> resultHandler = new
				 * BeanListHandler<VillageModel>(VillageModel.class); CustomQueryRunner runner =
				 * new CustomQueryRunner(); List<VillageModel> talukList =
				 * runner.RsMapping(resultHandler, resultSet);
				 * 
				 * if(resultSet!=null) { resultSet.close(); }
				 */

				if (conn != null) {
					conn.close();
				}
				if (res > 0) {
					apiResponseModel.setStatus(true);
					apiResponseModel.setStatusCode(200);
				} else {
					apiResponseModel.setStatus(false);
					apiResponseModel.setStatusCode(500);
				}
				return apiResponseModel;

			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			apiResponseModel.setMessage(e.getLocalizedMessage());

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			apiResponseModel.setMessage(e.getLocalizedMessage());
		} finally {

		}
		return apiResponseModel;
	}

	public APIResponseModel UserRegistration(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Gson gson = new Gson();
		UserDetailsModel userModel = gson.fromJson(data, UserDetailsModel.class);
		String SQL = "INSERT INTO `logintbl`(`uname`, `pwd`, `user_role`) VALUES ('" + userModel.getUsername() + "','"
				+ userModel.getPassword() + "','" + userModel.getUser_role() + "')";
		log.info(SQL);
		try {
			Class.forName(Constants.mysql_driver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Constants.UKPMySQLURL, Constants.UKPDBUserName,
				Constants.UKPDBPwd)) {

			if (conn != null) {
				PreparedStatement preparedStatement = conn.prepareStatement(SQL);

				// ResultSet resultSet = preparedStatement.executeQuery();
				int res = preparedStatement.executeUpdate();

				/*
				 * ResultSetHandler<List<VillageModel>> resultHandler = new
				 * BeanListHandler<VillageModel>(VillageModel.class); CustomQueryRunner runner =
				 * new CustomQueryRunner(); List<VillageModel> talukList =
				 * runner.RsMapping(resultHandler, resultSet);
				 * 
				 * if(resultSet!=null) { resultSet.close(); }
				 */

				if (conn != null) {
					conn.close();
				}
				if (res > 0) {
					apiResponseModel.setStatus(true);
					apiResponseModel.setStatusCode(200);
				} else {
					apiResponseModel.setStatus(false);
					apiResponseModel.setStatusCode(500);
				}
				return apiResponseModel;

			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			apiResponseModel.setMessage(e.getLocalizedMessage());

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			apiResponseModel.setMessage(e.getLocalizedMessage());
		} finally {

		}
		return apiResponseModel;
	}

	public APIResponseModel GetVillageByTaluk(String tid) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		String SQL_SELECT = "Select * from dist_master_tbl ";
		log.info(SQL_SELECT);

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetVillageByTalukID(?)}");
			cs.setString(1, tid);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<VillageModel>> resultHandler2 = new BeanListHandler<VillageModel>(VillageModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<VillageModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmVillageList(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertDistrict(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		DistModel distModel = new DistModel();
		distModel = gson.fromJson(data, DistModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_Insert_distrcit(?,?,?,?)}");
			cs.setString(1, distModel.getCode());
			cs.setString(2, distModel.getName());
			cs.setString(3, distModel.getCreadted_by());
			cs.setString(4, distModel.getModifiedBy());
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertGeneralInfo(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		GeneralInfoModel generalInfoModel = new GeneralInfoModel();
		generalInfoModel = gson.fromJson(data, GeneralInfoModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(generalInfoModel);
			int j = 0;
			String sql = "{call [USP_Insert_GenInfo] (";
			for (int i = 1; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 1; i < fiedsList.size(); i++) {
				cs.setString(i, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetDsr() {
		APIResponseModel apiResponseModel = new APIResponseModel();

		String SQL_SELECT = "Select * from dist_master_tbl ";
		log.info(SQL_SELECT);

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetDsr}");

			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<DistModel>> resultHandler2 = new BeanListHandler<DistModel>(DistModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<DistModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			/*
			cs = conn.prepareCall("{call USP_GetDSRMain}");

			
			rs = cs.executeQuery();
			runner = new CustomQueryRunner();
			ResultSetHandler<List<DSRMainModel>> resultHandler3 = new BeanListHandler<DSRMainModel>(DSRMainModel.class);

			List<DSRMainModel> mDSRMainList = runner.RsMapping(resultHandler3, rs);

			// USP_GetDSRSub

			cs = conn.prepareCall("{call USP_GetDSRSub}");

			rs = cs.executeQuery();
			runner = new CustomQueryRunner();
			ResultSetHandler<List<DSRSubModel>> resultHandler4 = new BeanListHandler<DSRSubModel>(DSRSubModel.class);

			List<DSRSubModel> mDSRSubList = runner.RsMapping(resultHandler4, rs);
			
			*/

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmDistList(tewntyMtrGridCodeAdds);
			// apiResponseModel.setmDSRMainList(mDSRMainList);
			// apiResponseModel.setmDSRSubList(mDSRSubList);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetDsrOnly() {
		APIResponseModel apiResponseModel = new APIResponseModel();

		String SQL_SELECT = "Select * from dist_master_tbl ";
		log.info(SQL_SELECT);

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetDsr}");

			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<DistModel>> resultHandler2 = new BeanListHandler<DistModel>(DistModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<DistModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			/*
			 * cs = conn.prepareCall("{call USP_GetDSRMain}");
			 * 
			 * rs = cs.executeQuery(); runner = new CustomQueryRunner();
			 * ResultSetHandler<List<DSRMainModel>> resultHandler3 = new
			 * BeanListHandler<DSRMainModel>(DSRMainModel.class);
			 * 
			 * List<DSRMainModel> mDSRMainList = runner.RsMapping(resultHandler3, rs);
			 * 
			 * // USP_GetDSRSub
			 * 
			 * cs = conn.prepareCall("{call USP_GetDSRSub}");
			 * 
			 * rs = cs.executeQuery(); runner = new CustomQueryRunner();
			 * ResultSetHandler<List<DSRSubModel>> resultHandler4 = new
			 * BeanListHandler<DSRSubModel>(DSRSubModel.class);
			 * 
			 * List<DSRSubModel> mDSRSubList = runner.RsMapping(resultHandler4, rs);
			 * 
			 */
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmDistList(tewntyMtrGridCodeAdds);
			// apiResponseModel.setmDSRMainList(mDSRMainList);
			// apiResponseModel.setmDSRSubList(mDSRSubList);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetStrType() {
		APIResponseModel apiResponseModel = new APIResponseModel();

		String SQL_SELECT = "Select * from dist_master_tbl ";
		log.info(SQL_SELECT);

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetStrType}");

			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<DistModel>> resultHandler2 = new BeanListHandler<DistModel>(DistModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<DistModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmDistList(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertStElementMst(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		StrElementModelClass generalInfoModel = new StrElementModelClass();
		generalInfoModel = gson.fromJson(data, StrElementModelClass.class);
		generalInfoModel.setModifiedBy(generalInfoModel.getCreatedBy());
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(generalInfoModel);
			int j = 0;
			String sql = "{call [USP_InsertStrElement] (";
			for (int i = 1; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 1; i < fiedsList.size(); i++) {
				cs.setString(i, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetStrElement() {
		APIResponseModel apiResponseModel = new APIResponseModel();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetStrElement}");
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<StrElementModelClass>> resultHandler2 = new BeanListHandler<StrElementModelClass>(
					StrElementModelClass.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<StrElementModelClass> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);
			Collections.sort(tewntyMtrGridCodeAdds);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmStrElementList(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetFieldMeasuremntByGenInfoID(String genInfoID) {
		System.out.println("GetFieldMeasuremntByGenInfoID");
		APIResponseModel apiResponseModel = new APIResponseModel();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetFieldMeasurementByGenInfoID(?)}");
			cs.setString(1, genInfoID);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<LatestFMModel>> resultHandler2 = new BeanListHandler<LatestFMModel>(
					LatestFMModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<LatestFMModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmLatestFMModel(tewntyMtrGridCodeAdds);
			Gson gson=new Gson();
			
			System.out.println(gson.toJson(tewntyMtrGridCodeAdds));
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetFieldMeasuremntByGenInfoIDWithDetails(String genInfoID) {
		APIResponseModel apiResponseModel = new APIResponseModel();
		Connection conn = null;
		ResultSet rs = null;
		int strType = 3;
		String year = "15";
		CallableStatement cs = null;
		Map<String, String> subdsrID = new HashMap<String, String>();
		Map<String, List<FieldMeasurementsModel>> itemList = new HashMap<String, List<FieldMeasurementsModel>>();
		Map<String, Double> mAreaDeductionList = new HashMap<String, Double>(); 

		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetFieldMeasurementByGenInfoID(?)}");
			cs.setString(1, genInfoID);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<FieldMeasurementsModel>> resultHandler2 = new BeanListHandler<FieldMeasurementsModel>(
					FieldMeasurementsModel.class);

			List<FieldMeasurementsModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			cs = conn.prepareCall("{call USP_GetGeneralInfoByGenInfoID(?)}");
			cs.setString(1, genInfoID);
			rs = cs.executeQuery();
			rs.next();
			year = rs.getString("AgeOfStructure");
			strType = rs.getInt("StructureTypeCode");
			int gstValue = rs.getInt("GST_Value");
			
			System.out.println("Age:"+year+" str type:"+strType);
			 cs = conn.prepareCall("{call [USP_GetDeprByYearAndType](?, ?)}");
			 cs.setString(1,year); 
			 cs.setInt(2, strType); 
			 rs = cs.executeQuery(); 
			 double depr =0;
			  while(rs.next())
			  {
				  depr = rs.getDouble("depr");
				  }
			  if(depr>0.25) 
				  depr = .25;
			  
			 
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			String doorWindowRegEx = "[WR]\\d{1,2}";
			String paintPointRegEx = "[P][OA]\\d{1,2}";
			String cupboardRegEx = "[C][B]\\d{1,2}";
			String chullaRegEx = "[C][L]\\d{1,2}";
			String plasteringRegEx = "[P][R]\\d{1,2}";

			String wallRegEx = "[W][W]\\d{1,2}";
			Map<String, List<String>> mainLabelsubLabelMap = new HashMap<String, List<String>>();
			Map<String, Integer> mPositionsOfMainWallLabels = new HashMap<String, Integer>();

			for (int i = 0; i < tewntyMtrGridCodeAdds.size(); i++) 
			{
				double doorArea = 0;
				double paint = 0;
				FieldMeasurementsModel fmElement = tewntyMtrGridCodeAdds.get(i);
				if(Pattern.matches(wallRegEx, fmElement.getMainLabel()) && Pattern.matches(wallRegEx, fmElement.getSubLabel()))
				{
					mPositionsOfMainWallLabels.put(fmElement.getMainLabel(), i);
				}
				if ((Pattern.matches(doorWindowRegEx, tewntyMtrGridCodeAdds.get(i).getSubLabel())
						&& Pattern.matches(wallRegEx, tewntyMtrGridCodeAdds.get(i).getMainLabel()))
						|| (Pattern.matches(paintPointRegEx, tewntyMtrGridCodeAdds.get(i).getSubLabel())
								&& Pattern.matches(wallRegEx, tewntyMtrGridCodeAdds.get(i).getMainLabel()))
						|| (Pattern.matches(cupboardRegEx, tewntyMtrGridCodeAdds.get(i).getSubLabel())
								&& Pattern.matches(wallRegEx, tewntyMtrGridCodeAdds.get(i).getMainLabel()))
						
						|| (Pattern.matches(chullaRegEx, tewntyMtrGridCodeAdds.get(i).getSubLabel())
								&& Pattern.matches(wallRegEx, tewntyMtrGridCodeAdds.get(i).getMainLabel()))
						
						|| (Pattern.matches(plasteringRegEx, tewntyMtrGridCodeAdds.get(i).getSubLabel())
								&& Pattern.matches(wallRegEx, tewntyMtrGridCodeAdds.get(i).getMainLabel()))
						
						) 
				{
					
					if (!fmElement.getB().equalsIgnoreCase("9999.00") && !fmElement.getH().equalsIgnoreCase("9999.00")
							&& !tewntyMtrGridCodeAdds.get(i).getB().equalsIgnoreCase("9999.00")) {
						doorArea = Double.parseDouble(fmElement.getB()) * Double.parseDouble(fmElement.getH())
								* Double.parseDouble(tewntyMtrGridCodeAdds.get(i).getB());
						tewntyMtrGridCodeAdds.get(i).setDeductionArea(doorArea + "");

						paint = Double.parseDouble(fmElement.getB()) * Double.parseDouble(fmElement.getH());
						tewntyMtrGridCodeAdds.get(i).setPaintDeductionArea(paint + "");
						
					}
					List<String> subList = mainLabelsubLabelMap.get(fmElement.getMainLabel());
					if(subList==null)
					{
						subList = new ArrayList<String>();
					}
				
					//subList.add(fmElement.getSubLabel());
					subList.add(i+"");
					mainLabelsubLabelMap.put(fmElement.getMainLabel(), subList);
				}
				subdsrID.put(tewntyMtrGridCodeAdds.get(i).getDsrSubID(), tewntyMtrGridCodeAdds.get(i).getDsrSubID());
			}
			
			for(String key: mainLabelsubLabelMap.keySet())
			{
				for(int h=0; h<mainLabelsubLabelMap.get(key).size(); h++)
					{
					int pos = Integer.parseInt(mainLabelsubLabelMap.get(key).get(h));
					System.out.println("Main :"+key+" Index:"+mainLabelsubLabelMap.get(key).get(h) +" Sublabel:"+tewntyMtrGridCodeAdds.get(pos).getSubLabel());
					}
				
			}
			
			double EstimatedAmount = 0;
			double gst = (double)gstValue/100;
			double dep = depr;
			double payableAmount = 0;
			
			

			List<SubDsrDetailsModel> subDsrDetailsModels = new ArrayList<SubDsrDetailsModel>();
			for (String key : subdsrID.keySet()) {
				cs = conn.prepareCall("{call USP_SubDsrDetailsByID(?)}");
				cs.setString(1, key);
				rs = cs.executeQuery();

				ResultSetHandler<SubDsrDetailsModel> resultHandler3 = new BeanHandler<SubDsrDetailsModel>(
						SubDsrDetailsModel.class);
				SubDsrDetailsModel subDsrDetailsModel = runner.RsMapping(resultHandler3, rs);
				double quantity = 0;

				List<FieldMeasurementsModel> subListforTemp = new ArrayList<FieldMeasurementsModel>();
				/*
				 * for (int k = 0; k < tewntyMtrGridCodeAdds.size(); k++) {
				 * FieldMeasurementsModel fmElement = tewntyMtrGridCodeAdds.get(k); if
				 * (Pattern.matches(doorWindowRegEx,fmElement.getSubLabel()) ||
				 * Pattern.matches(paintPointRegEx,fmElement.getSubLabel()) ) { double doorArea
				 * = 0; double paint = 0;
				 * 
				 * for (int m = 0; m < tewntyMtrGridCodeAdds.size(); m++) { if
				 * (tewntyMtrGridCodeAdds.get(m).getMainLabel().equalsIgnoreCase(fmElement.
				 * getMainLabel())) {
				 * 
				 * 
				 * 
				 * } }
				 * 
				 * } }
				 */
				quantity = 0;

				subListforTemp = new ArrayList<FieldMeasurementsModel>();
				for (int k = 0; k < tewntyMtrGridCodeAdds.size(); k++) 
				{
					if (tewntyMtrGridCodeAdds.get(k).getDsrSubID().equalsIgnoreCase(key)) {
						FieldMeasurementsModel fmElement = tewntyMtrGridCodeAdds.get(k);

						String[] form = null;
						String formula = null;
						double val = 1;
						try {
							if (null != fmElement.getAreaFormula()) {
								formula = fmElement.getAreaFormula();
								String f = fmElement.getAreaFormula().replace("*", "-");
								form = f.split("-");
							} else if (null != fmElement.getVolumeFormula()) {
								formula = fmElement.getVolumeFormula();
								String f = fmElement.getVolumeFormula().replace("*", "-");
								form = f.split("-");
							}
						} catch (Exception e) {
							String f = fmElement.getAreaFormula().replace("*", "-");
							form = f.split("-");
						}

						if (form != null && form.length > 0)
							for (int l = 0; l < form.length; l++) {
								if (form[l].equalsIgnoreCase("L") && !fmElement.getL().equalsIgnoreCase("9999.00")) {
									val *= Double.parseDouble(fmElement.getL());
								} else if (form[l].equalsIgnoreCase("B")
										&& !fmElement.getB().equalsIgnoreCase("9999.00") /* && !fmElement.getSubLabel().contains("CB")*/ ) {
									val *= Double.parseDouble(fmElement.getB());
								} else if (form[l].equalsIgnoreCase("H")
										&& !fmElement.getH().equalsIgnoreCase("9999.00")) {
									val *= Double.parseDouble(fmElement.getH());
								}
								
								else if (form[l].equalsIgnoreCase("Q")
										&& !fmElement.getQ().equalsIgnoreCase("9999.00")) {
									val *= Double.parseDouble(fmElement.getQ());
								}
								
							}
						
					   if(formula.equalsIgnoreCase("C*C*L*Q*7/88")) {
						   if(!fmElement.getQ().equalsIgnoreCase("9999.00") &&  !fmElement.getL().equalsIgnoreCase("9999.00") &&  !fmElement.getC().equalsIgnoreCase("9999.00"))
							{
							   val = Double.parseDouble(fmElement.getC()) * Double.parseDouble(fmElement.getC())*Double.parseDouble(fmElement.getL()) *Double.parseDouble(fmElement.getQ()) *7/88;
							   System.out.println("Main Label:"+fmElement.getMainLabel()+ "Sub Label Label:"+fmElement.getMainLabel()+" \n"+"Circle:"+val+"quantity :"+quantity);
							}
						}
					   
					   if(formula.equalsIgnoreCase("C*C*H*Q*7/88")) {
						   if(!fmElement.getQ().equalsIgnoreCase("9999.00") &&  !fmElement.getH().equalsIgnoreCase("9999.00") &&  !fmElement.getC().equalsIgnoreCase("9999.00"))
							{
							   val = Double.parseDouble(fmElement.getC()) * Double.parseDouble(fmElement.getC())*Double.parseDouble(fmElement.getH()) *Double.parseDouble(fmElement.getQ()) *7/88;
							   System.out.println("C*C*H*Q*7/88 Main Label:"+fmElement.getMainLabel()+ "Sub Label Label:"+fmElement.getMainLabel()+" \n"+"Circle:"+val+"quantity :"+quantity);
							}
						}
					   
					   if(Pattern.matches(plasteringRegEx, fmElement.getSubLabel()))
					   {
						   System.out.println("Plastering value:"+val);
					   }

					   if(!fmElement.getCode().equalsIgnoreCase("CH"))
						quantity += val;
						
						//System.out.println(" paint:"+Pattern.matches(paintPointRegEx, fmElement.getSubLabel())+ " door "+Pattern.matches(doorWindowRegEx, fmElement.getSubLabel())+" Wall:"+Pattern.matches(wallRegEx, fmElement.getMainLabel()));
						if (  (
									!Pattern.matches(paintPointRegEx, fmElement.getSubLabel())  
								&&  Pattern.matches(doorWindowRegEx, fmElement.getSubLabel()) 
								&&  Pattern.matches(cupboardRegEx, fmElement.getSubLabel()) 
								&&  Pattern.matches(chullaRegEx, fmElement.getSubLabel()) 
								&&  Pattern.matches(wallRegEx, fmElement.getSubLabel())  ) 
								&&  Pattern.matches(wallRegEx, fmElement.getMainLabel())) 
						{		
							FieldMeasurementsModel mainLabelData = null;
							for(int n=0; n<tewntyMtrGridCodeAdds.size(); n++)
							{
								if(tewntyMtrGridCodeAdds.get(n).getMainLabel().equalsIgnoreCase(tewntyMtrGridCodeAdds.get(n).getSubLabel()))
									mainLabelData = tewntyMtrGridCodeAdds.get(n);
							}
							
							List<String> poslist = mainLabelsubLabelMap.get(fmElement.getMainLabel());
							if(poslist!=null)
							for(int i = 0; i<poslist.size(); i++)
							{

								if(Pattern.matches(doorWindowRegEx,tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()))
									{

									    double de = Double.parseDouble(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getB()) 
									    		* Double.parseDouble(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getH()) * Double.parseDouble(mainLabelData.getB());
									    
									    quantity = quantity - de;
									   System.out.println("Main labe:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+" Sub Label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+" Ded area:"+de);
									}				
							}
							
						}
						else if(Pattern.matches(paintPointRegEx, fmElement.getSubLabel()) &&  Pattern.matches(wallRegEx, fmElement.getMainLabel())) 
						{
							
							List<String> poslist = mainLabelsubLabelMap.get(fmElement.getMainLabel());
							if(poslist!=null)
								for(int i = 0; i<poslist.size(); i++) 
								{
									System.out.println("Plastering position :"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel());
									System.out.println("expr paint point Code :"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getCode()+" Maib:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel());
									if(Pattern.matches(doorWindowRegEx,tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()))
									{	
									    System.out.println("Before plastering dedcution quantity:"+quantity);
										double area= Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getB())*  Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getH());
									    quantity = quantity - area;
									    System.out.println("Quantityi:"+quantity+" Area:"+area);
										 System.out.println("Adding mAreaDeductionList:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+" Sub Label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+" Ded area:"+area);

									    mAreaDeductionList.put(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+"-"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel(), area);
									}
									
									else if(Pattern.matches(cupboardRegEx,tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()))
									{	
										double area= Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getL())*  Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getH());
									    quantity = quantity - area;
									    System.out.println("cupboardRegEx Quantity:"+quantity+" Area:"+area);
										System.out.println("Adding mAreaDeductionList:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+" Sub Label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+" Ded area:"+area);

									    mAreaDeductionList.put(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+"-"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel(), area);
									}
									
								
								}
						}
						else if(Pattern.matches(paintPointRegEx, fmElement.getSubLabel()) &&  Pattern.matches(wallRegEx, fmElement.getMainLabel())) 
						{
							
							List<String> poslist = mainLabelsubLabelMap.get(fmElement.getMainLabel());
							if(poslist!=null)
								for(int i = 0; i<poslist.size(); i++) 
								{
									System.out.println("Plastering position :"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel());
									System.out.println("expr paint point Code :"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getCode()+" Maib:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel());
									if(Pattern.matches(doorWindowRegEx,tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()))
									{	
									    System.out.println("Before plastering dedcution quantity:"+quantity);
										double area= Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getB())*  Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getH());
									    quantity = quantity - area;
									    System.out.println("Quantityi:"+quantity+" Area:"+area);
										System.out.println("Adding mAreaDeductionList:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+" Sub Label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+" Ded area:"+area);

									    mAreaDeductionList.put(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+"-"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel(), area);
									}
										
									
							
								}
						}
						else if(Pattern.matches(cupboardRegEx, fmElement.getSubLabel()) &&  Pattern.matches(wallRegEx, fmElement.getMainLabel())) 
						{
							
							List<String> poslist = mainLabelsubLabelMap.get(fmElement.getMainLabel());
							if(poslist!=null)
								for(int i = 0; i<poslist.size(); i++) 
								{
									System.out.println("expr cupboard  Code :"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getCode()+" Maib:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel());
									if(Pattern.matches(doorWindowRegEx,tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()))
									{	
									    double area= Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getB())*  Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getH());
									    quantity = quantity - area;
									    System.out.println("Quantityi:"+quantity+" Area:"+area);
										System.out.println("Adding mAreaDeductionList:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+" Sub Label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+" Ded area:"+area);

									    mAreaDeductionList.put(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+"-"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel(), area);
									}
							
								}
						}
						else if(Pattern.matches(wallRegEx, fmElement.getSubLabel()) &&  Pattern.matches(wallRegEx, fmElement.getMainLabel())) 
						{
							
							List<String> poslist = mainLabelsubLabelMap.get(fmElement.getMainLabel());
							if(poslist!=null)
								for(int i = 0; i<poslist.size(); i++) 
								{
									System.out.println("Positions:"+mPositionsOfMainWallLabels);
									System.out.println("wall  Code :"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getCode()+" Maib:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel());
									if(Pattern.matches(doorWindowRegEx,tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()))
									{	
										int postion = mPositionsOfMainWallLabels.get(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel());

									    double area= Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getB())*  
									    		Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getH()) *
									    		Double.parseDouble(tewntyMtrGridCodeAdds.get(postion).getB()) ;
									    quantity = quantity - area;
									    System.out.println("Door Quantity:"+quantity+" Area:"+" Wall Width: "+tewntyMtrGridCodeAdds.get(postion).getB()+area+" Door H:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getH()+" Door len:"+ tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getL()+" Door B:"+ tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getB());
										 System.out.println("Wall label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+" Sub Label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+" Ded area:"+area);
										 System.out.println("Adding mAreaDeductionList:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+" Sub Label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+" Ded area:"+area);

									    mAreaDeductionList.put(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+"-"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+"-wall", area);
									}
									else if(Pattern.matches(cupboardRegEx,tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()))
									{	
										int postion = mPositionsOfMainWallLabels.get(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel());
									    double area= Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getL()) * 
									    		Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getB())
									    		* Double.parseDouble(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getH()) ;
									    quantity = quantity - area;
									    System.out.println("Cupboard Quantity:"+quantity+" Area:"+area+" Wall B:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getH()+" Cupboard len:"+ tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getL()+" Cupboard B:"+ tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getB());
										System.out.println("Cupboard label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+" Sub Label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+" Ded area:"+area);
										System.out.println("Adding mAreaDeductionList:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+" Sub Label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+" Ded area:"+area);

									    mAreaDeductionList.put(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+"-"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+"-wall", area);
									}
									
									else if(Pattern.matches(chullaRegEx,tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()))
									{	
										int postion = mPositionsOfMainWallLabels.get(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel());
									    double area= Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getL()) * 
									    		Double.parseDouble( tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getB())
									    		* Double.parseDouble(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getH()) ;
									    quantity = quantity - area;
									    System.out.println("Chulla Quantity:"+quantity+" Area:"+area+" Wall B:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getH()+" Cupboard len:"+ tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getL()+" Cupboard B:"+ tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getB());
										System.out.println("Chulla label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+" Sub Label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+" Ded area:"+area);
										 System.out.println("Adding mAreaDeductionList:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+" Sub Label:"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+" Ded area:"+area);

									    mAreaDeductionList.put(tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getMainLabel()+"-"+tewntyMtrGridCodeAdds.get(Integer.parseInt(poslist.get(i))).getSubLabel()+"-wall", area);
									}
							
								}
						}
						System.out.println("mAreaDeductionList size iterate:"+mAreaDeductionList.size());
						System.out.println("mAreaDeductionList size iterate:"+mAreaDeductionList);
						subListforTemp.add(fmElement);
					}
				}

				double rate = Double.parseDouble(subDsrDetailsModel.getSubDsrRate());
				double amount = rate * quantity;

				if(rate>25000)
				{
					 float  newval = Float.parseFloat(new DecimalFormat("#.####").format(quantity));

					amount = rate * newval;
				}
				EstimatedAmount += amount;
				subDsrDetailsModel.setmTotalQuantity(quantity);
				subDsrDetailsModel.setmAmount(amount);
				subDsrDetailsModel.setmFmList(subListforTemp);
				subDsrDetailsModels.add(subDsrDetailsModel);
				
				
				
			}

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			System.out.println("mAreaDeductionList size:"+mAreaDeductionList.size());
			System.out.println("mAreaDeductionList size:"+mAreaDeductionList);

			for (Map.Entry<String, Double> entry : mAreaDeductionList.entrySet()) 
			{
			    Double value = entry.getValue();
			    for(int j=0; j<subDsrDetailsModels.size(); j++)
			    {
			    	for(int n = 0; n<subDsrDetailsModels.get(j).getmFmList().size(); n++) 
			    	{
			    		String split[] = entry.getKey().split("-");
			    		//if(Pattern.matches(cupboardRegEx, split[1]) && Pattern.matches(wallRegEx, split[0]) )
						if(Pattern.matches(paintPointRegEx,subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel()) && 
								subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel().equalsIgnoreCase(split[0]) && split.length==2)	
						{
							System.out.println("Plastering dedu:Main Label:"+subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel()+" Sub label:" +subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel()+" value:"+value);
							
							if(Pattern.matches(plasteringRegEx, subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel()) &&  Pattern.matches(plasteringRegEx, subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel()))
							{
								
							}
								
								double ta = Float.parseFloat(subDsrDetailsModels.get(j).getmFmList().get(n).getTotalArea()) - value;
			    			
							subDsrDetailsModels.get(j).getmFmList().get(n).setTotalArea(ta+"");
			    			subDsrDetailsModels.get(j).getmFmList().get(n).setTotalAreaCalculated(ta+"");		    			
						}
						else if(Pattern.matches(wallRegEx,subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel()) 
								&& 
								(subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel().equalsIgnoreCase(split[0]) && split.length==3 )							
								) {
							double ta = Float.parseFloat(subDsrDetailsModels.get(j).getmFmList().get(n).getTotalVolumeCalculated()) - value;
							System.out.println("Wall dedu:"+subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel()+" "+subDsrDetailsModels.get(j).getmFmList().get(n).getTotalVolumeCalculated() +" value: "+value+"="+ta);
			    			subDsrDetailsModels.get(j).getmFmList().get(n).setTotalVolume(ta+"");
			    			subDsrDetailsModels.get(j).getmFmList().get(n).setTotalVolumeCalculated(ta+"");
						}
						else if(Pattern.matches(chullaRegEx,subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel()) && 
								subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel().equalsIgnoreCase(split[0])	
								
								) {
							double ta = Float.parseFloat(subDsrDetailsModels.get(j).getmFmList().get(n).getTotalVolumeCalculated()) - value;
			    			subDsrDetailsModels.get(j).getmFmList().get(n).setTotalVolume(ta+"");
			    			subDsrDetailsModels.get(j).getmFmList().get(n).setTotalVolumeCalculated(ta+"");
						}
						
						
						if(Pattern.matches(cupboardRegEx,split[1]) && 
								subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel().equalsIgnoreCase(split[0])  )
						{
							System.out.println("Cupboard addition minus:"+subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel()+" sublabel: "+subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel()+" added value:"+value);
							
							if(!Pattern.matches(wallRegEx, subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel()) && !Pattern.matches(wallRegEx, subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel()))
							{
								double ta = Float.parseFloat(subDsrDetailsModels.get(j).getmFmList().get(n).getTotalVolumeCalculated()) + value;							
						
			    			subDsrDetailsModels.get(j).getmFmList().get(n).setTotalVolume(ta+"");
			    			subDsrDetailsModels.get(j).getmFmList().get(n).setTotalVolumeCalculated(ta+"");
							}
							else {
								System.out.println("Splitted vlaues:"+split.length);
								System.out.println("Cupboard not adding addition minus:"+subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel()+" sublabel: "+subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel()+" added value:"+value);
								if(split.length==3 && Pattern.matches(wallRegEx, subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel()) && Pattern.matches(wallRegEx, subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel())) {
								double ta = Float.parseFloat(subDsrDetailsModels.get(j).getmFmList().get(n).getTotalVolumeCalculated()) + value;							
								
				    			subDsrDetailsModels.get(j).getmFmList().get(n).setTotalVolume(ta+"");
				    			subDsrDetailsModels.get(j).getmFmList().get(n).setTotalVolumeCalculated(ta+"");
								}
							}
							
						}
						
						
			    		
			    	}
			    }
			}
			
			for(int j=0; j<subDsrDetailsModels.size(); j++)
		    {
		    	for(int n = 0; n<subDsrDetailsModels.get(j).getmFmList().size(); n++) 
		    	{
					if(Pattern.matches(wallRegEx,subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel()) && 
							Pattern.matches(wallRegEx,subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel())		){
						
		    			List<String> mappingList = mainLabelsubLabelMap.get(subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel());
		    			System.out.println("Mappinglist:"+mappingList+" sublabel:"+subDsrDetailsModels.get(j).getmFmList().get(n).getSubLabel()+" Main Label:"+subDsrDetailsModels.get(j).getmFmList().get(n).getMainLabel());
					
		    			if(mappingList!=null) {
		    			for(int u=0; u<mappingList.size(); u++)
		    			{
		    				if(Pattern.matches(cupboardRegEx, tewntyMtrGridCodeAdds.get(Integer.parseInt(mappingList.get(u))).getSubLabel()))
		    				{
		    					FieldMeasurementsModel fmmodel = tewntyMtrGridCodeAdds.get(Integer.parseInt(mappingList.get(u)));
		    					System.out.println("Codes:"+fmmodel.getSubLabel());
		    					double area = Double.parseDouble(fmmodel.getL()) * Double.parseDouble(fmmodel.getB()) * Double.parseDouble(fmmodel.getH()); 
		    					double ta = Float.parseFloat(subDsrDetailsModels.get(j).getmFmList().get(n).getTotalVolume()) - area;  
		    					subDsrDetailsModels.get(j).getmFmList().get(n).setTotalVolume(ta+"");
				    			 	subDsrDetailsModels.get(j).getmFmList().get(n).setTotalVolumeCalculated(ta+"");
		    				}
		    			}
		    			}
		    			
		    			
					}

		    		
		    	}
		    }
			
			
			RateAbstractModel rateAbstractModel = new RateAbstractModel();
			EstimatedAmount += gst * EstimatedAmount;
			payableAmount = EstimatedAmount - (EstimatedAmount * dep);
			rateAbstractModel.setEstimatedAmount(EstimatedAmount);
			rateAbstractModel.setPayableAmount(payableAmount);
			rateAbstractModel.setDep(dep);
			rateAbstractModel.setGst(gst);
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setEstimatedAbstractModel(rateAbstractModel);
			apiResponseModel.setSubDsrDetailsWithFM(subDsrDetailsModels);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.toString());
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close(); 
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setMessage(e.getLocalizedMessage());
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetUOM() {
		// USP_GetUOM
		// USP_InsertUOM
		// TODO Auto-generated method stub
		APIResponseModel apiResponseModel = new APIResponseModel();

		String SQL_SELECT = "Select * from dist_master_tbl ";
		log.info(SQL_SELECT);

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetUOM}");
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<VillageModel>> resultHandler2 = new BeanListHandler<VillageModel>(VillageModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<VillageModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmUoMList(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetRateOfDepreciation() {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetDepreciation}");
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<RateOfDepreciationModel>> resultHandler2 = new BeanListHandler<RateOfDepreciationModel>(
					RateOfDepreciationModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<RateOfDepreciationModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmRateOfDepr(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}
	// USP_InsertSRDepre

	public APIResponseModel InsertStandRateDepreciation(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		RateOfDepreciationModel generalInfoModel = new RateOfDepreciationModel();
		generalInfoModel = gson.fromJson(data, RateOfDepreciationModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(generalInfoModel);
			int j = 0;
			String sql = "{call [USP_InsertSRDepre] (";
			for (int i = 1; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 1; i < fiedsList.size(); i++) {
				cs.setString(i, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel importDsr(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		DSRInsertionModel generalInfoModel = new DSRInsertionModel();
		generalInfoModel = gson.fromJson(data, DSRInsertionModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(generalInfoModel);
			System.out.println("List size;"+fiedsList.size());
			int j = 0;
			String sql = "{call [USP_InsertBulkDSR] (";
			for (int i = 0; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";
			
			System.out.println("SQL:"+sql);
			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 0; i < fiedsList.size(); i++) {
				System.out.println("setting:"+fiedsList.get(i)+" I="+i);
				cs.setString(i+1, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			log.info(params);
			System.out.println("Params:"+params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	
	
	public APIResponseModel GetTextEntry(String userID) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetTextEntry(?)}");
			cs.setString(1, userID);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<TextEntryModel>> resultHandler2 = new BeanListHandler<TextEntryModel>(
					TextEntryModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<TextEntryModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmTextEntryList(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetTextEntryByGenInfoID(String geninfoid) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetTextEntryByGenInfoID(?)}");
			cs.setString(1, geninfoid);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<TextEntryModel>> resultHandler2 = new BeanListHandler<TextEntryModel>(
					TextEntryModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<TextEntryModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmTextEntryList(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertTextEntry(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		TextEntryModel generalInfoModel = new TextEntryModel();
		generalInfoModel = gson.fromJson(data, TextEntryModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(generalInfoModel);
			int j = 0;
			String sql = "{call [USP_InsertTextEntry] (";
			for (int i = 1; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 1; i < fiedsList.size(); i++) {
				cs.setString(i, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertFieldMeaurements(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		FieldMeasurementsModel generalInfoModel = new FieldMeasurementsModel();
		generalInfoModel = gson.fromJson(data, FieldMeasurementsModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(generalInfoModel);
			int j = 0;
			String sql = "{call [USP_InsertFieldMeasurement] (";
			for (int i = 1; i < fiedsList.size() - 5; i++) {
				j = i;
				if (i == fiedsList.size() - 6) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 1; i < fiedsList.size() - 5; i++) {
				cs.setString(i, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertTaluk(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		TalukModel distModel = new TalukModel();
		distModel = gson.fromJson(data, TalukModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_InsertTaluk(?,?,?,?,?,?)}");
			cs.setString(1, distModel.getCode());
			cs.setString(2, distModel.getName());
			cs.setString(3, distModel.getDistrictID());
			cs.setString(4, distModel.getDistrictCode());
			cs.setString(5, distModel.getCreatedBy());
			cs.setString(6, distModel.getModifiedBy());
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertVillage(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		VillageModel distModel = new VillageModel();
		distModel = gson.fromJson(data, VillageModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call [USP_InsertVillage](?,?,?,?,?,?,?,?)}");
			cs.setString(1, distModel.getCode());
			cs.setString(2, distModel.getName());
			cs.setString(3, distModel.getDistrictID());
			cs.setString(4, distModel.getDistrictCode());
			cs.setString(5, distModel.getTalukID());
			cs.setString(6, distModel.getTalukCode());
			cs.setString(7, distModel.getCreatedBy());
			cs.setString(8, distModel.getModifiedBy());
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetReport(String genInfoID) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		CallableStatement cs2 = null;

		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call [CalculateGeneralInfo_New](?)}");
			cs.setString(1, genInfoID);
			cs.execute();

			cs2 = conn.prepareCall("{call [USP_GetReportByGenInfoID](?)}");
			cs2.setString(1, genInfoID);
			rs = cs2.executeQuery();

			CustomQueryRunner runner = new CustomQueryRunner();
			// ResultSetHandler<List<TextEntryModel>> resultHandler2 = new
			// BeanListHandler<TextEntryModel>(TextEntryModel.class);
			ResultSetHandler<GenInfoReport> resultHandler2 = new BeanHandler<GenInfoReport>(GenInfoReport.class);

			GenInfoReport genInfoReport = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmGenInfoReport(genInfoReport);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetReportWithElements(String genInfoID) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		CallableStatement cs2 = null;
		CallableStatement cs3 = null;

		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call [CalculateGeneralInfo_New](?)}");
			cs.setString(1, genInfoID);
			cs.execute();

			cs2 = conn.prepareCall("{call [USP_GetReportByGenInfoID](?)}");
			cs2.setString(1, genInfoID);
			rs = cs2.executeQuery();

			CustomQueryRunner runner = new CustomQueryRunner();
			// ResultSetHandler<List<TextEntryModel>> resultHandler2 = new
			// BeanListHandler<TextEntryModel>(TextEntryModel.class);
			ResultSetHandler<GenInfoReport> resultHandler2 = new BeanHandler<GenInfoReport>(GenInfoReport.class);

			GenInfoReport genInfoReport = runner.RsMapping(resultHandler2, rs);

			cs3 = conn.prepareCall("{call [USP_FieldMeasurementByGenInfo](?)}");
			cs3.setString(1, genInfoID);
			rs = cs3.executeQuery();

			ResultSetHandler<List<FmReportByGenInfoModel>> resultHandler3 = new BeanListHandler<FmReportByGenInfoModel>(
					FmReportByGenInfoModel.class);
			cs3.setString(1, genInfoID);
			rs = cs3.executeQuery();
			List<FmReportByGenInfoModel> FmReportList = runner.RsMapping(resultHandler3, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}

			if (cs2 != null)
				cs2.close();
			if (conn != null) {
				conn.close();
			}

			if (cs3 != null)
				cs3.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmGenInfoReport(genInfoReport);
			apiResponseModel.setFmList(FmReportList);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetFloorElements(String genInfoID) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		CallableStatement cs2 = null;
		CallableStatement cs3 = null;

		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);

			/*
			 * cs = conn.prepareCall("{call [CalculateGeneralInfo_New](?)}");
			 * cs.setString(1, genInfoID); cs.execute();
			 * 
			 * cs2 = conn.prepareCall("{call [USP_GetReportByGenInfoID](?)}");
			 * cs2.setString(1, genInfoID); rs = cs2.executeQuery();
			 */
			CustomQueryRunner runner = new CustomQueryRunner();
			// ResultSetHandler<List<TextEntryModel>> resultHandler2 = new
			// BeanListHandler<TextEntryModel>(TextEntryModel.class);

			cs3 = conn.prepareCall("{call [USP_GetFloorElementsByGenInfoID](?)}");
			cs3.setString(1, genInfoID);
			rs = cs3.executeQuery();

			ResultSetHandler<List<FloorPlanElementsModel>> resultHandler3 = new BeanListHandler<FloorPlanElementsModel>(
					FloorPlanElementsModel.class);
			cs3.setString(1, genInfoID);
			rs = cs3.executeQuery();
			List<FloorPlanElementsModel> FmReportList = runner.RsMapping(resultHandler3, rs);

			List<FloorPlanElementsModel> mainList = new ArrayList<FloorPlanElementsModel>();
			String doorWindowRegEx = "[WD]\\d{1,2}";
			String kattaRegEx = "[K]\\d{1,2}";
			String cupboardREgExp = "[C][BH]\\d{1,2}";
			String wallRegEx = "[W][L]\\d{1,2}";

			for (int i = 0; i < FmReportList.size(); i++) {
				cs2 = conn.prepareCall("{call [USP_GetFloorElementsByMainLabel](?,?)}");
				cs2.setString(1, genInfoID);
				cs2.setString(2, FmReportList.get(i).getHorizontalWall1());
				rs = cs2.executeQuery();
				List<FloorPlanElementsModel> subLabelList = runner.RsMapping(resultHandler3, rs);
				FloorPlanElementMainModel floorpaln = new FloorPlanElementMainModel();
				// floorpaln.setMainLabel(FmReportList.get(i));
				for(int j=0; j<subLabelList.size(); j++)
				{
					if(Pattern.matches(doorWindowRegEx, subLabelList.get(j).getSubLabel()) 
							|| Pattern.matches(wallRegEx, subLabelList.get(j).getSubLabel()) || Pattern.matches(kattaRegEx, subLabelList.get(j).getSubLabel())
							)
					{
						mainList.add(subLabelList.get(j));
					}
					
				}
				floorpaln.setFloorPlanElementList(subLabelList);
				// mainList.add(floorpaln);

				//mainList.addAll(subLabelList);

				cs2 = conn.prepareCall("{call [USP_GetFloorElementsByMainLabel](?,?)}");
				cs2.setString(1, genInfoID);
				cs2.setString(2, FmReportList.get(i).getHorizontalWall2());
				rs = cs2.executeQuery();
				List<FloorPlanElementsModel> subLabelList2 = runner.RsMapping(resultHandler3, rs);
				FloorPlanElementMainModel floorpaln2 = new FloorPlanElementMainModel();
				// floorpaln.setMainLabel(FmReportList.get(i));
				for(int j=0; j<subLabelList2.size(); j++)
				{
					if(Pattern.matches(doorWindowRegEx, subLabelList2.get(j).getSubLabel()) 
							|| Pattern.matches(wallRegEx, subLabelList2.get(j).getSubLabel()) 
							|| Pattern.matches(kattaRegEx, subLabelList2.get(j).getSubLabel())
							|| Pattern.matches(cupboardREgExp, subLabelList2.get(j).getSubLabel())
							 )
					{
						mainList.add(subLabelList2.get(j));
					}
					
				}
				floorpaln.setFloorPlanElementList(subLabelList2);
				// mainList.add(floorpaln);

				//mainList.addAll(subLabelList2);

				cs2 = conn.prepareCall("{call [USP_GetFloorElementsByMainLabel](?,?)}");
				cs2.setString(1, genInfoID);
				cs2.setString(2, FmReportList.get(i).getVerticalWall1());
				rs = cs2.executeQuery();
				List<FloorPlanElementsModel> subLabelList3 = runner.RsMapping(resultHandler3, rs);
				FloorPlanElementMainModel floorpaln3 = new FloorPlanElementMainModel();
				// floorpaln.setMainLabel(FmReportList.get(i));
				for(int j=0; j<subLabelList3.size(); j++)
				{
					if(Pattern.matches(doorWindowRegEx, subLabelList3.get(j).getSubLabel())
							|| Pattern.matches(wallRegEx, subLabelList3.get(j).getSubLabel()) || Pattern.matches(kattaRegEx, subLabelList3.get(j).getSubLabel())
							|| Pattern.matches(cupboardREgExp, subLabelList3.get(j).getSubLabel())
						 )
					{
						mainList.add(subLabelList3.get(j));
					}
					
				}
				floorpaln.setFloorPlanElementList(subLabelList3);
				// mainList.add(floorpaln);

				//mainList.addAll(subLabelList3);

				cs2 = conn.prepareCall("{call [USP_GetFloorElementsByMainLabel](?,?)}");
				cs2.setString(1, genInfoID);
				cs2.setString(2, FmReportList.get(i).getVerticalWall2());
				logger.info("value:" + genInfoID + " mainlabel:" + FmReportList.get(i).getMainLabel());
				rs = cs2.executeQuery();
				List<FloorPlanElementsModel> subLabelList4 = runner.RsMapping(resultHandler3, rs);
				FloorPlanElementMainModel floorpaln4 = new FloorPlanElementMainModel();
				// floorpaln.setMainLabel(FmReportList.get(i));
				for(int j=0; j<subLabelList4.size(); j++)
				{
					if(Pattern.matches(doorWindowRegEx, subLabelList4.get(j).getSubLabel()) 
							||  Pattern.matches(wallRegEx, subLabelList4.get(j).getSubLabel()) || Pattern.matches(kattaRegEx, subLabelList4.get(j).getSubLabel())
							|| Pattern.matches(cupboardREgExp, subLabelList4.get(j).getSubLabel())
							 )
					{
						mainList.add(subLabelList4.get(j));
					}
					
				}
				floorpaln.setFloorPlanElementList(subLabelList4);
				logger.info("fllorPlan:" + new Gson().toJson(floorpaln4));
				// mainList.add(floorpaln);

			//	mainList.addAll(subLabelList4);

			}

			if (conn != null) {
				conn.close();
			}

			if (cs3 != null)
				cs3.close();

			if (cs2 != null)
				cs2.close();

			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setStructureElements(mainList);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();

			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetAllGenInfo() {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetAllGenInfo}");
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<GenInfoReport>> resultHandler2 = new BeanListHandler<GenInfoReport>(
					GenInfoReport.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<GenInfoReport> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			for (int i = 0; i < tewntyMtrGridCodeAdds.size(); i++) {
				tewntyMtrGridCodeAdds.get(i).setSl_No((i + 1));
			}

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmGenInfoReportModel(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetAllGenInfoV2(String userid) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call GetallGeninfoV2(?)}");
			cs.setString(1, userid);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<GenInfoReport>> resultHandler2 = new BeanListHandler<GenInfoReport>(
					GenInfoReport.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<GenInfoReport> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			for (int i = 0; i < tewntyMtrGridCodeAdds.size(); i++) {
				tewntyMtrGridCodeAdds.get(i).setSl_No((i + 1));
			}

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmGenInfoReportModel(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetAllUser() {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetAllUsers}");
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<UserDetailsModel>> resultHandler2 = new BeanListHandler<UserDetailsModel>(
					UserDetailsModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<UserDetailsModel> userList = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmUserList(userList);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetAllRoles() {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetAllRoles}");
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<UserRolesModel>> resultHandler2 = new BeanListHandler<UserRolesModel>(
					UserRolesModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<UserRolesModel> userList = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmUserRolesList(userList);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertUSers(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		InsertUserModel distModel = new InsertUserModel();
		distModel = gson.fromJson(data, InsertUserModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(distModel);
			int j = 0;
			String sql = "{call [USP_InsertUser] (";
			for (int i = 0; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 0; i < fiedsList.size(); i++) {
				cs.setString(i + 1, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			System.out.println("Params:" + params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetDsrCodeNames() {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetDsrCodeName}");
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<DSRSubModel>> resultHandler2 = new BeanListHandler<DSRSubModel>(DSRSubModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<DSRSubModel> userList = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmDSRSubList(userList);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel USP_GetMainDsrByDSRID(String dsrID) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetMainDsrByDSRID(?)}");
			cs.setString(1, dsrID);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<DSRMainModel>> resultHandler2 = new BeanListHandler<DSRMainModel>(DSRMainModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<DSRMainModel> userList = runner.RsMapping(resultHandler2, rs);
			// Collections.sort(userList);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmDSRMainList(userList);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel USP_GetSubDsrByDSRMainID(String dsrID) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetSubDsrByDSRMainID(?)}");
			cs.setString(1, dsrID);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<DSRSubModel>> resultHandler2 = new BeanListHandler<DSRSubModel>(DSRSubModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<DSRSubModel> userList = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmDSRSubList(userList);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetMainDsrByDSRNonDSR(String label, String dsrID) {
		APIResponseModel apiResponseModel = new APIResponseModel();
		int labelType;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			if (label.equalsIgnoreCase("DSR"))
				labelType = 1;
			else
				labelType = 0;

			cs = conn.prepareCall("{call USP_GetMainDsrByDSRNonDSR(?,?)}");
			cs.setInt(1, labelType);
			cs.setString(2, dsrID);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<DSRMainModel>> resultHandler2 = new BeanListHandler<DSRMainModel>(DSRMainModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<DSRMainModel> userList = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmDSRMainList(userList);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertDsrMain(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		DSRMainModel generalInfoModel = new DSRMainModel();
		generalInfoModel = gson.fromJson(data, DSRMainModel.class);
		// generalInfoModel.setModifiedBy(generalInfoModel.getCreatedBy());
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(generalInfoModel);
			int j = 0;
			String sql = "{call [USP_InsertDsrMain] (";
			for (int i = 1; i < fiedsList.size() - 1; i++) {
				j = i;
				if (i == fiedsList.size() - 2) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 1; i < fiedsList.size() - 1; i++) {
				cs.setString(i, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			System.out.println("Params:" + params);
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel InsertDSRSub(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		DSRSubModel generalInfoModel = new DSRSubModel();
		generalInfoModel = gson.fromJson(data, DSRSubModel.class);
		// generalInfoModel.setModifiedBy(generalInfoModel.getCreatedBy());
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(generalInfoModel);
			int j = 0;
			String sql = "{call [USP_InsertDsrSub] (";
			for (int i = 1; i < fiedsList.size() - 2; i++) {
				j = i;
				if (i == fiedsList.size() - 3) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 1; i < fiedsList.size() - 2; i++) {
				cs.setString(i, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			System.out.println("Params:" + params);
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel DeleteData(String genInfoID, int type) {
		APIResponseModel apiResponseModel = new APIResponseModel();
		int labelType;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);

			cs = conn.prepareCall("{call USP_Delete(?,?)}");
			cs.setString(1, genInfoID);
			cs.setInt(2, type);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			// ResultSetHandler<List<DSRMainModel>> resultHandler2 = new
			// BeanListHandler<DSRMainModel>(DSRMainModel.class);
			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel UpdatePassword(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();
		int labelType;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		UpdatePwdModel updatePwdModel = new UpdatePwdModel();
		updatePwdModel = new Gson().fromJson(data, UpdatePwdModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);

			cs = conn.prepareCall("{call UpdatePassword(?,?,?)}");
			cs.setString(1, updatePwdModel.getOldPwd());
			cs.setString(2, updatePwdModel.getNewPwd());
			cs.setString(3, updatePwdModel.getUserID());
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			// ResultSetHandler<List<DSRMainModel>> resultHandler2 = new
			// BeanListHandler<DSRMainModel>(DSRMainModel.class);
			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}

	}

	public APIResponseModel UpdateDistrict(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();
		int labelType;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		DistModel distModel = new DistModel();
		distModel = new Gson().fromJson(data, DistModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);

			cs = conn.prepareCall("{call USP_Update_distrcit(?,?,?,?)}");
			cs.setString(1, distModel.getID());
			cs.setString(2, distModel.getCode());
			cs.setString(3, distModel.getName());
			cs.setString(4, distModel.getModifiedBy());

			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			// ResultSetHandler<List<DSRMainModel>> resultHandler2 = new
			// BeanListHandler<DSRMainModel>(DSRMainModel.class);
			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}

	}

	public APIResponseModel UpdateGenInfo(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		GeneralInfoModel generalInfoModel = new GeneralInfoModel();
		generalInfoModel = gson.fromJson(data, GeneralInfoModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(generalInfoModel);
			int j = 0;
			String sql = "{call [USP_Update_GenInfo] (";
			for (int i = 0; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 0; i < fiedsList.size(); i++) {
				cs.setString(i + 1, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}
	
	public APIResponseModel UpdateFieldMeasurments(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		UpdateFMModel fieldMeasuremetModel = new UpdateFMModel();
		fieldMeasuremetModel = gson.fromJson(data, UpdateFMModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(fieldMeasuremetModel);
			System.out.println("Size:"+fiedsList.size());
			int j = 0;
			String sql = "{call [USP_UpdateFieldMeasurement] (";
			for (int i = 0; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 0; i < fiedsList.size(); i++) {
				cs.setString(i + 1, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel updateFileUploaded(String genInfoID, String fileType, String uploadedBy, FormDataContentDisposition fileDetail) {
		APIResponseModel apiResponseModel = new APIResponseModel();
		int labelType;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		String ext2 = FilenameUtils.getExtension(fileDetail.getFileName()); // returns "exe"

		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);

			cs = conn.prepareCall("{call USP_UpdateDocumentFile(?,?,?,?,?)}");
			cs.setString(1, genInfoID);
			cs.setString(2, fileType);
			cs.setString(3, ext2);
			cs.setString(4, fileDetail.getFileName());
			cs.setString(5, uploadedBy);

			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			// ResultSetHandler<List<DSRMainModel>> resultHandler2 = new
			// BeanListHandler<DSRMainModel>(DSRMainModel.class);
			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetDocumentList(String iD, int type) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_GetDocuments(?,?)}");
			cs.setInt(1, type);
			cs.setString(2, iD);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<DocumentModel>> resultHandler2 = new BeanListHandler<DocumentModel>(DocumentModel.class);
			
			List<DocumentModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmDocumentList(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel UpdateTextEntry(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		UpdateTextEntryModel updateTextEntryModel = new UpdateTextEntryModel();
		updateTextEntryModel = gson.fromJson(data, UpdateTextEntryModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(updateTextEntryModel);
			System.out.println("Size:"+fiedsList.size());
			int j = 0;
			String sql = "{call [USP_UpdateTextEntry] (";
			for (int i = 0; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 0; i < fiedsList.size(); i++) {
				cs.setString(i + 1, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel UpdateDsrMain(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		EditDsrmainModel updateTextEntryModel = new EditDsrmainModel();
		updateTextEntryModel = gson.fromJson(data, EditDsrmainModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(updateTextEntryModel);
			System.out.println("Size:"+fiedsList.size());
			int j = 0;
			String sql = "{call [USP_EditDsrMain] (";
			for (int i = 0; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 0; i < fiedsList.size(); i++) {
				cs.setString(i + 1, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel UpdateDsrSub(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		EditDsrSubModel updateTextEntryModel = new EditDsrSubModel();
		updateTextEntryModel = gson.fromJson(data, EditDsrSubModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			List<String> fiedsList = getObjectToList(updateTextEntryModel);
			System.out.println("Size:"+fiedsList.size());
			int j = 0;
			String sql = "{call [USP_EditDsrSub] (";
			for (int i = 0; i < fiedsList.size(); i++) {
				j = i;
				if (i == fiedsList.size() - 1) {
					sql += "?";
				} else {
					sql += "?,";
				}
			}
			sql = sql + ")}";

			cs = conn.prepareCall(sql);
			String params = "";
			for (int i = 0; i < fiedsList.size(); i++) {
				cs.setString(i + 1, fiedsList.get(i));
				params += "'" + fiedsList.get(i) + "',";
			}
			log.info(params);
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(
					APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel GetGSTValues() 
	{
		APIResponseModel apiResponseModel = new APIResponseModel();

		String SQL_SELECT = "Select * from dist_master_tbl ";
		log.info(Constants.url + DBNAME);

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_Get_GSTValues}");

			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();
			ResultSetHandler<List<GstValueModel>> resultHandler2 = new BeanListHandler<GstValueModel>(GstValueModel.class);
			// ResultSetHandler<RoadTransDataModel> resultHandler2 = new
			// BeanHandler<RoadTransDataModel>(RoadTransDataModel.class);

			List<GstValueModel> tewntyMtrGridCodeAdds = runner.RsMapping(resultHandler2, rs);

			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			apiResponseModel.setStatus(true);
			apiResponseModel.setStatusCode(200);
			apiResponseModel.setmGSTList(tewntyMtrGridCodeAdds);
			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
	}
	}

	public APIResponseModel InsertGST(String data) {

		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		GstValueModel distModel = new GstValueModel();
		distModel = gson.fromJson(data, GstValueModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_InsertGST(?,?)}");
			cs.setString(1, distModel.getGST_Value());
			cs.setString(2, distModel.getCreatedBy());
		
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	public APIResponseModel UpdateGST(String data) {
		APIResponseModel apiResponseModel = new APIResponseModel();

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Gson gson = new Gson();
		GstValueModel distModel = new GstValueModel();
		distModel = gson.fromJson(data, GstValueModel.class);
		// Constants.setBaseURLs();
		try {
			Class.forName(Constants.driver);
			conn = DriverManager.getConnection(Constants.url + DBNAME);
			cs = conn.prepareCall("{call USP_Update_GST_Master(?,?,?)}");
			cs.setString(1, distModel.getID());
			cs.setString(2, distModel.getGST_Value());
			cs.setString(3, distModel.getCreatedBy());

		
			rs = cs.executeQuery();
			CustomQueryRunner runner = new CustomQueryRunner();

			ResultSetHandler<APIResponseModel> resultHandler2 = new BeanHandler<APIResponseModel>(APIResponseModel.class);

			apiResponseModel = runner.RsMapping(resultHandler2, rs);
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}

			return apiResponseModel;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			if (cs != null)
				try {
					cs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (conn != null) {
				try {
					conn.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			apiResponseModel.setStatus(false);
			apiResponseModel.setStatusCode(500);
			return apiResponseModel;
		}
	}

	
}
