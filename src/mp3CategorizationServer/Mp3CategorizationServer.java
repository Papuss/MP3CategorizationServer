package mp3CategorizationServer;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mp3CategorizationServer {
	
	
	public Mp3CategorizationServer() {
		try {
			ServerSocket ss = new ServerSocket(1003);
			Socket socket = ss.accept();
			System.out.println("client connected");
			
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			System.out.println("streams established");
			
			Map<File, ID3Tag> mp3s = (HashMap<File, ID3Tag>) ois.readObject();
			System.out.println("file objects received");
			String chosenTag = (String) ois.readObject();
			System.out.println("chosen tag recieved");
			Map<String, List<File>> dirsAndFiles = MapTransformer.transformMap(mp3s, chosenTag);
			oos.writeObject(dirsAndFiles);
			System.out.println("directory and files layout created and sent");
			
			oos.close();
			os.close();
			ois.close();
			is.close();
			socket.close();
			ss.close();
			System.out.println("server closed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {

		new Mp3CategorizationServer();
	}

}
