package webserver;
import java.io.IOException;
import old.ClientAuthentication;
import old.ClientChunkEncodedPost;
import old.ClientMultipartFormPost;

import org.apache.http.client.ClientProtocolException;


public class TestConnection {
	private static final String HOSTNAME = "192.168.247.128/test.php";
	private static final int PORT = 443;
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String FILEPATH = "C:/test.txt";
	private static final String FILENAME = "test.txt";
	
	public static void main(String[] args) throws Exception {
		new ClientAuthentication().clientAuthentication(HOSTNAME, PORT, USERNAME, PASSWORD);
		new ClientChunkEncodedPost().chunkEncodedPost(FILEPATH, HOSTNAME);
		new ClientMultipartFormPost().multipartFormPost(HOSTNAME, FILEPATH);
	}
}

