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

import main.ID3Tag;

public class Mp3CategorizationServer {
	
	
	public Mp3CategorizationServer() {
		Map<File, ID3Tag> mp3s = new HashMap<File, ID3Tag>();
		try {
			ServerSocket ss = new ServerSocket(1003);
			Socket socket = ss.accept();
			System.out.println("client connected");
			
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			System.out.println("streams established");
			
			mp3s = (HashMap<File, ID3Tag>) ois.readObject();
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

//		File file1 = new File("C:\\Music\\01.mp3");
//		File file2 = new File("C:\\Music\\02.mp3");
//		File file3 = new File("C:\\Music\\03.mp3");
//		File file4 = new File("C:\\Music\\04.mp3");
//		File file5 = new File("C:\\Music\\05.mp3");
//		
//		Map<File, ID3Tag> mapp = new HashMap<>();
//		mapp.put(file1, ID3Tag.parse(file1));
//		mapp.put(file2, ID3Tag.parse(file2));
//		mapp.put(file3, ID3Tag.parse(file3));
//		mapp.put(file4, ID3Tag.parse(file4));
//		mapp.put(file5, ID3Tag.parse(file5));
//		
//		System.out.println(MapTransformer.transformMap(mapp, "year"));
	}

}
