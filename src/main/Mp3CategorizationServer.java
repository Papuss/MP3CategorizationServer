package main;

import common.GenreMapper;
import common.ID3Tag;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Property;

public class Mp3CategorizationServer {
	
	final int PORT = 1003;
	
	public Mp3CategorizationServer() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			Socket socket = ss.accept();
			System.out.println("client connected");
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			System.out.println("streams established");

			while(true) {
				Map<File, ID3Tag> mp3FilesMap = (HashMap<File, ID3Tag>) ois.readObject();
				System.out.println("file objects received");
				Property chosenTag = (Property) ois.readObject();
				System.out.println("chosen tag recieved");
				Map<String, List<File>> dirsAndFiles = transformMap(mp3FilesMap, chosenTag);
				oos.writeObject(dirsAndFiles);
				System.out.println("directory and files layout created and sent");
				if (socket.isClosed()) break; }

			oos.close(); os.close(); ois.close(); is.close(); socket.close(); ss.close();
			System.out.println("server closed."); }
		catch (Exception e) {e.printStackTrace();} }

	public Map<String, List<File>> transformMap(Map<File, ID3Tag> mp3s, Property chosenTag) {
		Map<String, List<File>> dirsAndFiles = new HashMap<>();

		for (HashMap.Entry<File, ID3Tag> entry : mp3s.entrySet()) {
			String property = null;
			if (chosenTag.equals(Property.TITLE)){property = entry.getValue().getTitle();}
			else if (chosenTag.equals(Property.ARTIST)){property = entry.getValue().getArtist();}
			else if (chosenTag.equals(Property.ALBUM)){property = entry.getValue().getAlbum();}
			else if (chosenTag.equals(Property.YEAR)){property = String.valueOf((entry.getValue().getYear()));}
			else if (chosenTag.equals(Property.GENRE)){property = GenreMapper.types.get(entry.getValue().getGenre());}
			if (!dirsAndFiles.containsKey(property)){dirsAndFiles.put(property,new ArrayList<>());}
			dirsAndFiles.get(property).add(entry.getKey()); }
		return dirsAndFiles; }

	public static void main(String[] args) {
		new Mp3CategorizationServer();
	}
}
