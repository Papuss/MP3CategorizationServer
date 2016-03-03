package main;

import java.io.File;
import java.util.*;
import main.Properties;

import main.ID3Tag;

public class MapTransformer {
	
	public static Map<String, List<File>> transformMap(Map<File, ID3Tag> mp3s, Properties chosenTag){
		
		Map<String, List<File>> dirsAndFiles = new HashMap<String, List<File>>();

		for (HashMap.Entry<File, ID3Tag> entry : mp3s.entrySet())
		{
			String property = null;
		    if (chosenTag.equals(Properties.TITLE)){
		    	property = entry.getValue().getTitle();
			}
		    else if (chosenTag.equals(Properties.ARTIST)){
		    	property = entry.getValue().getArtist();
			}
		    else if (chosenTag.equals(Properties.ALBUM)){
		    	property = entry.getValue().getAlbum();
			}
		    else if (chosenTag.equals(Properties.YEAR)){
		    	property = String.valueOf((entry.getValue().getYear()));
			}
		    else if (chosenTag.equals(Properties.GENRE)){
		    	property = GenreMapper.types.get(entry.getValue().getGenre());
			}
			if (!dirsAndFiles.containsKey(property)){
				dirsAndFiles.put(property,new ArrayList<>());
			}
			dirsAndFiles.get(property).add(entry.getKey());
		}
		

		return dirsAndFiles;

	}
}
