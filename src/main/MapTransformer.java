package mp3CategorizationServer;

import java.io.File;
import java.util.*;
import main.Properties;

import main.ID3Tag;

public class MapTransformer {
	
	public static Map<String, List<File>> transformMap(Map<File, ID3Tag> mp3s, String chosenTag){
		
		Map<String, List<File>> dirsAndFiles = new HashMap<String, List<File>>();
		List<String> collectedTags = new ArrayList<String>();
		
		for (Map.Entry<File, ID3Tag> entry : mp3s.entrySet())
		{
			if (chosenTag.equals("title")) {
				collectedTags.add(entry.getValue().getTitle());
		    }
		    if (chosenTag.equals("artist")) {
				collectedTags.add(entry.getValue().getArtist());
		    }
		    if (chosenTag.equals("album")) {
				collectedTags.add(entry.getValue().getAlbum());
		    }
		    if (chosenTag.equals("year")) {
		    	collectedTags.add(String.valueOf(entry.getValue().getYear()));
		    }
		    if (chosenTag.equals("genre")) {
				collectedTags.add(GenreMapper.types.get(entry.getValue().getGenre()));
		    }
		}
		
		for (String tag : collectedTags) {
			List<File> sortedFiles = new ArrayList<>();
			dirsAndFiles.put(tag, sortedFiles);
		}
		
		for (HashMap.Entry<File, ID3Tag> entry : mp3s.entrySet()){
			if (chosenTag.equals("title")) { /*itt kell maguskodni*/
				String tagDirName = entry.getValue().getTitle();
				File fileToAdd = entry.getKey();
				dirsAndFiles.get(tagDirName).add(fileToAdd);
		    }
		    if (chosenTag.equals("artist")) {
		    	String tagDirName = entry.getValue().getArtist();
		    	File fileToAdd = entry.getKey();
				dirsAndFiles.get(tagDirName).add(fileToAdd);
		    }
		    if (chosenTag.equals("album")) {
		    	String tagDirName = entry.getValue().getAlbum();
		    	File fileToAdd = entry.getKey();
				dirsAndFiles.get(tagDirName).add(fileToAdd);
		    }
		    if (chosenTag.equals("year")) {
		    	String tagDirName = String.valueOf(entry.getValue().getYear());
		    	File fileToAdd = entry.getKey();
				dirsAndFiles.get(tagDirName).add(fileToAdd);
		    }
		    if (chosenTag.equals("genre")) {
		    	String tagDirName = GenreMapper.types.get(entry.getValue().getGenre());
		    	File fileToAdd = entry.getKey();
				dirsAndFiles.get(tagDirName).add(fileToAdd);
		    }
		}
		
		return dirsAndFiles;

//		for (HashMap.Entry<File, ID3Tag> entry : mp3s.entrySet())
//		{
//			String property = null;
//		    if (chosenTag.equals("title")){
//		    	property = entry.getValue().getTitle();
//			}
//			if (!dirsAndFiles.containsKey(property)){
//				dirsAndFiles.put(property,new ArrayList<>());
//			}
//			dirsAndFiles.get(property).add(entry.getKey());
//		}
//
	}
}
