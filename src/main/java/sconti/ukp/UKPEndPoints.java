package sconti.ukp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.hpsf.Array;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.gson.Gson;

import Model.APIResponseModel;
import Model.DSRInsertionModel;
import Model.RateOfDepreciationModel;



/**
 * Root resource (exposed at "myresource" path)
 */
@Path("endpoints")
public class UKPEndPoints {


	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt(@HeaderParam("authorization") String token) {
		System.out.println("Token:" + token);
		return "Got it!";
	}
	Impobj impobj = new Impobj();
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadFile(
	        @FormDataParam("file") InputStream uploadedInputStream,
	        @FormDataParam("file") FormDataContentDisposition fileDetail,
	        @FormDataParam("path") String path) {

	    String uploadedFileLocation = path  + fileDetail.getFileName();

	    // save it
	    writeToFile(uploadedInputStream, uploadedFileLocation);

	    String output = "File uploaded to : " + uploadedFileLocation;

	    return output;

	}
	
	@POST
	@Path("/uploadSRDFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadSRDFile(
	        @FormDataParam("file") InputStream uploadedInputStream,
	        @FormDataParam("file") FormDataContentDisposition fileDetail,
	        @FormDataParam("path") String path) {
		String path2 = "D:\\excelupload\\";
	    String uploadedFileLocation = path2  + fileDetail.getFileName();

	    // save it
	    String resp = writeSRDFile(uploadedInputStream, uploadedFileLocation);

	    String output = "File uploaded to : " + uploadedFileLocation;

	    return resp;

	}
	
	
	@POST
	@Path("/uploadDSRFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadDsrExcelFile(
	        @FormDataParam("file") InputStream uploadedInputStream,
	        @FormDataParam("file") FormDataContentDisposition fileDetail,
	        @FormDataParam("path") String path) {
		String path2 = "D:\\excelupload\\";
	    String uploadedFileLocation = path2  + fileDetail.getFileName();

	    // save it
	    String resp = writeDSRFile(uploadedInputStream, uploadedFileLocation);

	    String output = "File uploaded to : " + uploadedFileLocation;

	    return resp;

	}
	
	@POST
	@Path("/uploadDocumentFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadDocumentFile(
	        @FormDataParam("file") InputStream uploadedInputStream,
	        @FormDataParam("file") FormDataContentDisposition fileDetail,
	        @FormDataParam("GenInfoID") String GenInfoID,  @FormDataParam("fileType") String fileType,  @FormDataParam("uploadedByID") String uploadedBy) {

		String path = "D:\\ukpdocumentfiles\\";
	    String uploadedFileLocation = path  + GenInfoID+"_"+fileDetail.getFileName();

	    // save it
	    String msg = writeDocumentFile(uploadedInputStream, uploadedFileLocation);
	    if(msg.equalsIgnoreCase("success")) {
	   		return new Gson().toJson(impobj.updateFileUploaded(GenInfoID,fileType,uploadedBy, fileDetail ));
	    }
	    else
	    return msg;

	}
	
	private String writeSRDFile(InputStream uploadedInputStream,
	        String uploadedFileLocation) {
		System.out.println("uploadedInputStream:"+uploadedInputStream+" path:"+uploadedFileLocation);
	    try {
	    	Gson gson = new Gson();
	        OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
	        int read = 0;
	        byte[] bytes = new byte[1024];

	        out = new FileOutputStream(new File(uploadedFileLocation));
	        while ((read = uploadedInputStream.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        out.flush();
	        out.close();
	        
	        FileInputStream inputStream = new FileInputStream(uploadedFileLocation);
	        
            Workbook workbook = new XSSFWorkbook(inputStream);
 
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();
          
            int count = 0;
            int successcount = 0;
            int failureCount = 0;
            rowIterator.next(); // skip the header row
            
             
            while (rowIterator.hasNext()) 
            {
            	count++;
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                RateOfDepreciationModel rate = new RateOfDepreciationModel();
                while (cellIterator.hasNext()) 
                {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    
                    String str="";
                    if(nextCell.getCellType() == CellType.NUMERIC) {
                        str = NumberToTextConverter.toText(nextCell.getNumericCellValue());
                    }
                    else
                    {
                    	str = nextCell.getStringCellValue();
                    }
                    if(columnIndex==0)
                    	rate.setNoOfYear(str);
                    if(columnIndex==1)
                    	rate.setAtOnePercent(str);
                    if(columnIndex==2)
                    	rate.setAtOneAndHalfPercent(str);
                    if(columnIndex==3)
                    	rate.setAtTwoPercent(str);
                    if(columnIndex==4)
                    	rate.setAtFourPercent(str);
                    	
                    rate.setCreatedBy("D99FF689-7F83-4ACB-9E4D-C3128C79154B");
                    rate.setDescription("Description");
                    rate.setInactive("1");
                    
               		
                }
                APIResponseModel ap=impobj.InsertStandRateDepreciation(gson.toJson(rate));
           		if(ap.isStatus())
           			successcount++;
           		else
           			failureCount++;
                
            }
            String res = "Succes count:"+successcount+" \n failure count:"+failureCount;
 
            workbook.close();
	        return res;
	    } catch (IOException e) {

	        e.printStackTrace();
	        return e.getLocalizedMessage();
	    }
	    

	   }
	
	private String writeDSRFile(InputStream uploadedInputStream,
	        String uploadedFileLocation) {
		System.out.println("uploadedInputStream:"+uploadedInputStream+" path:"+uploadedFileLocation);
	    try {
	    	Gson gson = new Gson();
	        OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
	        int read = 0;
	        byte[] bytes = new byte[1024];

	        out = new FileOutputStream(new File(uploadedFileLocation));
	        while ((read = uploadedInputStream.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        out.flush();
	        out.close();
	        
	        FileInputStream inputStream = new FileInputStream(uploadedFileLocation);
	        
            Workbook workbook = new XSSFWorkbook(inputStream);
 
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();
          
            int count = 0;
            int successcount = 0;
            int failureCount = 0;
            rowIterator.next(); // skip the header row
            
             
            while (rowIterator.hasNext()) 
            {
            	count++;
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                DSRInsertionModel rate = new DSRInsertionModel();
                while (cellIterator.hasNext()) 
                {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    
                    String str="";
                    if(nextCell.getCellType() == CellType.NUMERIC) {
                        str = NumberToTextConverter.toText(nextCell.getNumericCellValue());
                    }
                    else
                    {
                    	str = nextCell.getStringCellValue();
                    }
                    if(columnIndex==0)
                    	rate.setDsrCode(str);
                    if(columnIndex==1)
                    	rate.setDsrName(str);
                    if(columnIndex==2)
                    	rate.setDsrMainCode(str);
                    if(columnIndex==3)
                    	rate.setDsrMainDescription(str);
                    if(columnIndex==4)
                    	rate.setDsrMainIsDsr(str);
                    if(columnIndex==5)
                    	rate.setDsrMainOrder(str);
                    if(columnIndex==6)
                    	rate.setDsrSubCode(str);
                    if(columnIndex==7)
                    	rate.setDsrSubDescription(str);
                    if(columnIndex==8)
                    	rate.setDsrSubUOM(str);
                    if(columnIndex==9)
                    	rate.setRate(str);
                    if(columnIndex==10)
                    	rate.setDsrSubOrder(str);
                  
                }
                rate.setCreatedBy("D99FF689-7F83-4ACB-9E4D-C3128C79154B");

                System.out.print(" "+new Gson().toJson(rate));
                System.out.println("");

               
                APIResponseModel ap=impobj.importDsr(gson.toJson(rate));
           		if(ap.isStatus())
           			successcount++;
           		else
           			failureCount++;
                
            }
            String res = "Succes count:"+successcount+" \n failure count:"+failureCount;
 
            workbook.close();
	        return res;
	    } catch (IOException e) {

	        e.printStackTrace();
	        return e.getLocalizedMessage();
	    }
	    

	   }
	
	
	private String writeDocumentFile(InputStream uploadedInputStream,
	        String uploadedFileLocation) {
		System.out.println("uploadedInputStream:"+uploadedInputStream+" path:"+uploadedFileLocation);
	    try {
	        OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
	        int read = 0;
	        byte[] bytes = new byte[1024];

	        out = new FileOutputStream(new File(uploadedFileLocation));
	        while ((read = uploadedInputStream.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        out.flush();
	        out.close();
	       return "success";
	    } catch (IOException e) {
	        e.printStackTrace();
	        return e.getLocalizedMessage();
	    }

	   }
	

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
	        String uploadedFileLocation) {
		System.out.println("uploadedInputStream:"+uploadedInputStream+" path:"+uploadedFileLocation);
	    try {
	        OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
	        int read = 0;
	        byte[] bytes = new byte[1024];

	        out = new FileOutputStream(new File(uploadedFileLocation));
	        while ((read = uploadedInputStream.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        out.flush();
	        out.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	   }
	
	@POST
	@Path("/v2/file")
	@Produces("text/plain")
	public Response getFileV2( String filePath) {
		DataModel dataModel = new Gson().fromJson(filePath, DataModel.class);
		File file = new File(dataModel.getPath());
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=\""+file.getName()+"\"");
		return response.build();
	}
	
	@POST
	@Path("/v2/GetDirs")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetDirsV2(String data) {
		DataModel dataModel = new DataModel();
		dataModel = new Gson().fromJson(data, DataModel.class);
		if (dataModel.getType()== 0) {
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			dataModel.setCurrentDir(System.getProperty("user.dir"));
			File f = new File(System.getProperty("user.dir"));
			String currentList[] = f.list();
			dataModel.setParentDirFiles(Arrays.asList(currentList));
			dataModel.setParentDir(f.getParentFile().getAbsolutePath());
			System.out.println("Parent folder:" + f.getParentFile().getAbsolutePath());
			File directoryPath = new File(f.getParentFile().getAbsolutePath());
			// List of all files and directories
			String contents[] = directoryPath.list();
			dataModel.setCurrentDirFiles(Arrays.asList(contents));
		}
		else if(dataModel.getType()==1)
		{
			File f = new File(dataModel.getPath());
			if (f != null) {
				String currentList[] = f.list();
				dataModel.setCurrentDirFiles(Arrays.asList(currentList));

				if (f.getParentFile() != null)
					dataModel.setParentDir(f.getParentFile().getAbsolutePath());
				if (f.getParentFile() != null) 
				{
					File directoryPath = new File(f.getParentFile().getAbsolutePath());
					if (directoryPath != null) {
						String contents[] = directoryPath.list();
						dataModel.setParentDirFiles(Arrays.asList(contents));

					}
				}
			}
		}
		return new Gson().toJson(dataModel);
	}
	
	@POST
	@Path("/DeleteFile")
	@Produces(MediaType.APPLICATION_JSON)
	public String DeleteFile( String path) {
		DataModel dataModel = new Gson().fromJson(path, DataModel.class);
		int success = 0, deleted = 0;
		for(int i = 0;i<dataModel.getListOfPaths().size(); i++) {
		File myObj = new File(dataModel.getListOfPaths().get(i)); 
	    if (myObj.delete()) { 
	    	success++;
	      System.out.println("Deleted the folder: " + myObj.getName());
	    } else {
	    	deleted++;
	      System.out.println("Failed to delete the folder.");
	    } 
		}
	    return "Status of deletion:success:"+success+" failed:"+deleted+"";

	}
	
	@GET
    @Path("/GetDBNames")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String GetDBNames(@QueryParam("query") String query, @DefaultValue("0")@QueryParam("value") int value)
    {
 	    Gson gson = new Gson();	    	
    	return gson.toJson(impobj.GetDBNames(query,value));
    }
    
    @GET
    @Path("/GetTbNames")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String GetTbNames(@QueryParam("dbname") String dbname, @QueryParam("column") String column,@DefaultValue("0") @QueryParam("value") int value)
    {
 	    Gson gson = new Gson();	    	
    	return gson.toJson(impobj.GetTbNames(dbname, column,value));
    }
    
    
    @GET
    @Path("/GetColumnNames")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String GetColumnNames(@QueryParam("dbname") String dbName, @QueryParam("tblName") String tblName, @DefaultValue("0")@QueryParam("value") int value)
    {
 	    Gson gson = new Gson();	    	
    	return gson.toJson(impobj.GetColumnsNames(dbName, tblName,value));
    }
    
    @POST
    @Path("/GetData")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String GetData(@QueryParam("dbname") String dbName, @QueryParam("tblName") String tblName, String data, @DefaultValue("0")@QueryParam("value") int value)
    {
 	    Gson gson = new Gson();	    	
    	return gson.toJson(impobj.GetData(dbName, tblName, data,value));
    }
    
    @POST
    @Path("/CreateDB")
    @Produces(MediaType.APPLICATION_JSON)
    public String CreateDB(String data)
    {
 	    Gson gson = new Gson();	    	
    	return gson.toJson(impobj.CreateDB(data));
    }
    
    
    @POST
    @Path("/GetBaseURL")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String GetBaseURL(@QueryParam("dbname") String dbName, @DefaultValue("0")@QueryParam("value") int value)
    {
 	    Gson gson = new Gson();	    	
    	return gson.toJson(impobj.getBaseURL(dbName, value));
    }
    
    @GET
   	@Path("/verifyUser")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String verifyUser(@QueryParam("uname") String uname, @QueryParam("pwd") String pwd) {
   		return new Gson().toJson(impobj.verifyUser(uname, pwd));
   	}
    
    
    @GET
   	@Path("/GetAllUser")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetAllUser() {
   		return new Gson().toJson(impobj.GetAllUser());
   	}
    
    @GET
   	@Path("/GetAllRoles")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetAllRoles() {
   		return new Gson().toJson(impobj.GetAllRoles());
   	}
    
    @GET
   	@Path("/GetDsrCodeNames")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetDsrCodeNames() {
   		return new Gson().toJson(impobj.GetDsrCodeNames());
   	}
    
    @GET
   	@Path("/GetGSTValues")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetGSTValues() {
   		return new Gson().toJson(impobj.GetGSTValues());
   	}
  
    @GET
   	@Path("/GetAllDistrict")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetAllDistrict() {
   		return new Gson().toJson(impobj.GetAllDistrict());
   	}
    
    @GET
   	@Path("/GetTalukByDist")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetTalukByDist(@QueryParam("Dist_ID") String did) {
   		return new Gson().toJson(impobj.GetTalukByDist(did));
   	}
    
    @GET
   	@Path("/GetHobliByTaluk")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetHobliByTaluk(@QueryParam("Taluk_ID") String tid) {
   		return new Gson().toJson(impobj.GetHobliByTaluk(tid));
   	}
    
     
    
    @GET
   	@Path("/GetMainDsrByDSRID")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String USP_GetMainDsrByDSRID(@QueryParam("DSR_ID") String dsrID) {
   		return new Gson().toJson(impobj.USP_GetMainDsrByDSRID(dsrID));
   	}
    
    @GET
   	@Path("/GetSubDsrByDSRMainID")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String USP_GetSubDsrByDSRMainID(@QueryParam("DSRMain_ID") String dsrID) {
   		return new Gson().toJson(impobj.USP_GetSubDsrByDSRMainID(dsrID));
   	}
    
    @GET
   	@Path("/GetVillageByHobli")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetVillageByByHobli(@QueryParam("Hobli_ID") String hid) {
   		return new Gson().toJson(impobj.GetVillageByHobli(hid));
   	}
    
    
    @GET
   	@Path("/GetVillageByTaluk")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetVillageByTaluk(@QueryParam("TID") String tid) {
   		return new Gson().toJson(impobj.GetVillageByTaluk(tid));
   	}
    
    @GET
   	@Path("/GetDsr")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetDsr() {
   		return new Gson().toJson(impobj.GetDsr());
   	}
    
    @GET
   	@Path("/GetDsrOnly")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetDsrOnly() {
   		return new Gson().toJson(impobj.GetDsrOnly());
   	}
    
    @GET
   	@Path("/GetMainDsrByDSRNonDSR")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetMainDsrByDSRNonDSR(@QueryParam("Label") String label, @QueryParam("DSRID") String dsrID) {
   		return new Gson().toJson(impobj.GetMainDsrByDSRNonDSR(label, dsrID));
   	}
    
    @GET
   	@Path("/GetStrType")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetStrType() {
   		return new Gson().toJson(impobj.GetStrType());
   	}
    
    
    @GET
   	@Path("/GetStrElement")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetStrElement() {
   		return new Gson().toJson(impobj.GetStrElement());
   	}
    
    @GET
   	@Path("/GetFieldMeasuremntByGenInfoID")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetFieldMeasuremntByGenInfoID(@QueryParam("GenInfoID") String GenInfoID) {
   		return new Gson().toJson(impobj.GetFieldMeasuremntByGenInfoID(GenInfoID));
   	}
    
    
    @GET
   	@Path("/GetUOM")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetUOM() {
   		return new Gson().toJson(impobj.GetUOM());
   	}
    
    @GET
   	@Path("/GetRateOfDepreciation")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetRateOfDepreciation() {
   		return new Gson().toJson(impobj.GetRateOfDepreciation());
   	}
    
    @GET
   	@Path("/GetTextEntry")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetTextEntry(@QueryParam("UserID") String UserID) {
   		return new Gson().toJson(impobj.GetTextEntry(UserID));
   	}
    
    @GET
   	@Path("/GetTextEntryByGenInfoID")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetTextEntryByGenInfoID(@QueryParam("GenInfoID") String GenInfoID) {
   		return new Gson().toJson(impobj.GetTextEntryByGenInfoID(GenInfoID));
   	}
    
    
    
    @GET
   	@Path("/GetReport")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetReport(@QueryParam("GenInfoID") String GenInfoID) {
   		return new Gson().toJson(impobj.GetReport(GenInfoID));
   	}
    
   
    @GET
   	@Path("/GetAllGenInfo")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetAllGenInfo() {
   		return new Gson().toJson(impobj.GetAllGenInfo());
   	}
    
    
    @GET
   	@Path("/GetAllGenInfoV2")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetAllGenInfoV2(@QueryParam("UserID") String UserID) {
   		return new Gson().toJson(impobj.GetAllGenInfoV2(UserID));
   	}
    
    @POST
   	@Path("/UpdatePassword")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String UpdatePassword(String data) {
   		return new Gson().toJson(impobj.UpdatePassword(data));
   	}
  
    @POST
   	@Path("/UpdateDistrict")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String UpdateDistrict(String data) {
   		return new Gson().toJson(impobj.UpdateDistrict(data));
   	}
    
    
    @POST
   	@Path("/UpdateDsrMain")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String UpdateDsrMain(String data) {
   		return new Gson().toJson(impobj.UpdateDsrMain(data));
   	}
    
    @POST
   	@Path("/UpdateDsrSub")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String UpdateDsrSub(String data) {
   		return new Gson().toJson(impobj.UpdateDsrSub(data));
   	}
    
    
    @GET
   	@Path("/GetReportWithElements")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetReportWithElements(@QueryParam("GenInfoID") String GenInfoID) {
   		return new Gson().toJson(impobj.GetReportWithElements(GenInfoID));
   	}
    
    @GET
   	@Path("/GetReportWithElementsDsrDetails")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetReportWithElementsDsrDetails(@QueryParam("GenInfoID") String GenInfoID) {
   		return new Gson().toJson(impobj.GetReportWithElementsDsrDetails(GenInfoID));
   	}
    
    
    @GET
   	@Path("/GetFloorElements")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetFloorElements(@QueryParam("GenInfoID") String GenInfoID) {
   		return new Gson().toJson(impobj.GetFloorElements(GenInfoID));
   	}
    
    @GET
   	@Path("/GetDocumentList")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String GetDocumentList(@QueryParam("ID") String ID, @QueryParam("type") int type) {
   		return new Gson().toJson(impobj.GetDocumentList(ID, type));
   	}
    
    @DELETE
   	@Path("/DeleteData")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String DeleteData(@QueryParam("ID") String GenInfoID, @QueryParam("type") int type) {
   		return new Gson().toJson(impobj.DeleteData(GenInfoID, type));
   	}
    
    
    @POST
   	@Path("/InsertDSRMst")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertDSRMst(String data) {
   		return new Gson().toJson(impobj.InsertDSRMst(data));
   	}
    
    @POST
   	@Path("/InsertGST")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertGST(String data) {
   		return new Gson().toJson(impobj.InsertGST(data));
   	}
    
    @POST
   	@Path("/InsertDSRMain")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertDSRMain(String data) {
   		return new Gson().toJson(impobj.InsertDsrMain(data));
   	}
    
    @POST
   	@Path("/InsertDSRSub")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertDSRSub(String data) {
   		return new Gson().toJson(impobj.InsertDSRSub(data));
   	}
    
    @POST
   	@Path("/InsertStrTypeMst")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertStrTypeMst(String data) {
   		return new Gson().toJson(impobj.InsertStrTypeMst(data));
   	}
    
    @POST
   	@Path("/InsertStElementMst")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertStElementMst(String data) {
   		return new Gson().toJson(impobj.InsertStElementMst(data));
   	}
  
    
    @POST
   	@Path("/InsertDistrict")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertUOMMst(String data) {
   		return new Gson().toJson(impobj.InsertDistrict(data));
   	}
    
    @POST
   	@Path("/InsertTaluk")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertTaluk(String data) {
   		return new Gson().toJson(impobj.InsertTaluk(data));
   	}
    
    @POST
   	@Path("/InsertVillage")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertVillage(String data) {
   		return new Gson().toJson(impobj.InsertVillage(data));
   	}
    
    @POST
   	@Path("/InsertGeneralInfo")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertGeneralInfo(String data) {
   		return new Gson().toJson(impobj.InsertGeneralInfo(data));
   	}
    
    @POST
   	@Path("/UpdateGeneralInfo")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String UpdateGenInfo(String data) {
   		return new Gson().toJson(impobj.UpdateGenInfo(data));
   	}
    
    @POST
   	@Path("/UpdateFieldMeasurments")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String UpdateFieldMeasurments(String data) {
   		return new Gson().toJson(impobj.UpdateFieldMeasurments(data));
   	}
    
    @POST
   	@Path("/UpdateTextEntry")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String UpdateTextEntry(String data) {
   		return new Gson().toJson(impobj.UpdateTextEntry(data));
   	}
    
    
    @POST
   	@Path("/InsertUOM")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertUOM(String data) {
   		return new Gson().toJson(impobj.InsertUOM(data));
   	}
    
    @POST
   	@Path("/UpdateGST")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String UpdateGST(String data) {
   		return new Gson().toJson(impobj.UpdateGST(data));
   	}
    
    @POST
   	@Path("/InsertStandRateDepreciation")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertStandRateDepreciation(String data) {
   		return new Gson().toJson(impobj.InsertStandRateDepreciation(data));
   	}
    
    @POST
   	@Path("/InsertTextEntry")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertTextEntry(String data) {
   		return new Gson().toJson(impobj.InsertTextEntry(data));
   	}
    
    @POST
    @Path("/InsertFieldMeaurements")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String InsertFieldMeaurements(String data) {
   		return new Gson().toJson(impobj.InsertFieldMeaurements(data));
   	}
    
    
    
    @POST
   	@Path("/UserRegistration")
   	@Produces(MediaType.APPLICATION_JSON)
   	public String UserRegistration(String data) {
   		return new Gson().toJson(impobj.UserRegistration(data));
   	}
    
    @POST
	@Path("/FileUpload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String FileUpload(
	        @FormDataParam("file") InputStream uploadedInputStream,
	        @FormDataParam("file") FormDataContentDisposition fileDetail,
	        @FormDataParam("path") String path) {
	    String uploadedFileLocation = path  + fileDetail.getFileName();

	    // save it
	    writeToFile(uploadedInputStream, uploadedFileLocation);

	    String output = "File uploaded to : " + uploadedFileLocation;

	    return output;

	}
    
	
}

