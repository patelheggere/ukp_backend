package sconti.ukp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.ws.rs.Consumes;
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


import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.gson.Gson;



/**
 * Root resource (exposed at "myresource" path)
 */
@Path("Login")
public class UKPLogin {


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
		return "Got it Login!";
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

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
	        String uploadedFileLocation) {
		System.out.println("uploadedInputStream:"+uploadedInputStream+" path:"+uploadedFileLocation);
	    try {
	        OutputStream out = new FileOutputStream(new File(
	                uploadedFileLocation));
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
    
  

	
	
	
}

