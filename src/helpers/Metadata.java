package helpers;

public class Metadata {
	private String filename, filesize, uri;
	
	public Metadata() {
		filename = ""; 
		filesize = "";
		uri = "";
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	
	public String getFilesize() {
		return filesize;
	}
	
	public void setURI(String uri) {
		this.uri = uri;
	}
	
	public String getURI() {
		return uri;
	}
}

