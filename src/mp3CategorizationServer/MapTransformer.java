package mp3CategorizationServer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			dirsAndFiles.put(tag, null);
		}
		
		for (HashMap.Entry<File, ID3Tag> entry : mp3s.entrySet()){
			if (chosenTag.equals("title")) {
				String tag = entry.getValue().getTitle();
				dirsAndFiles.get(tag).add(entry.getKey());
		    }
		    if (chosenTag.equals("artist")) {
		    	String tag = entry.getValue().getArtist();
				dirsAndFiles.get(tag).add(entry.getKey());
		    }
		    if (chosenTag.equals("album")) {
		    	String tag = entry.getValue().getAlbum();
				dirsAndFiles.get(tag).add(entry.getKey());
		    }
		    if (chosenTag.equals("year")) {
		    	String tag = String.valueOf(entry.getValue().getYear());
				dirsAndFiles.get(tag).add(entry.getKey());
		    }
		    if (chosenTag.equals("genre")) {
		    	String tag = GenreMapper.types.get(entry.getValue().getGenre());
				dirsAndFiles.get(tag).add(entry.getKey());
		    }
		}
		
		return dirsAndFiles;
		/*
		for (HashMap.Entry<File, ID3Tag> entry : mp3s.entrySet())
		{
		    if (chosenTag.equals("title")){
		    	String property = entry.getValue().getTitle();
		    	if (dirsAndFiles.containsKey(property)){
		    		dirsAndFiles.get(property).add(entry.getKey());
		    	} else {
		    		dirsAndFiles.put(property, null);
		    		dirsAndFiles.get(property).add(entry.getKey());
		    	}
		    }
		}
		*/
	}
}
