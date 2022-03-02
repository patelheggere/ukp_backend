package sconti.ukp;

import java.util.List;
import java.util.Map;

public class DataModel {
	int type;
	String path;
	List<String> listOfPaths;
	String currentDir;
	String parentDir;
	List<String> currentDirFiles;
	List<String> parentDirFiles;

	Map<String, Object> mapdata;
	String sql;
	List<String> columns;
	String dbName;

	
	public String getCurrentDir() {
		return currentDir;
	}
	public void setCurrentDir(String currentDir) {
		this.currentDir = currentDir;
	}
	public String getParentDir() {
		return parentDir;
	}
	public void setParentDir(String parentDir) {
		this.parentDir = parentDir;
	}
	public List<String> getDirsFiles() {
		return currentDirFiles;
	}
	public void setDirsFiles(List<String> dirsFiles) {
		this.currentDirFiles = dirsFiles;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<String> getCurrentDirFiles() {
		return currentDirFiles;
	}
	public void setCurrentDirFiles(List<String> currentDirFiles) {
		this.currentDirFiles = currentDirFiles;
	}
	public List<String> getParentDirFiles() {
		return parentDirFiles;
	}
	public void setParentDirFiles(List<String> parentDirFiles) {
		this.parentDirFiles = parentDirFiles;
	}
	public Map<String, Object> getMapdata() {
		return mapdata;
	}
	public void setMapdata(Map<String, Object> mapdata) {
		this.mapdata = mapdata;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	public List<String> getListOfPaths() {
		return listOfPaths;
	}
	public void setListOfPaths(List<String> listOfPaths) {
		this.listOfPaths = listOfPaths;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
}
