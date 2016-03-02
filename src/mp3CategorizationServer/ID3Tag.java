package mp3CategorizationServer;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class ID3Tag implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5233207519609692173L;
	private String title;
	private String artist;
	private String album;
	private int year;
	private String comment;
	private int genre;

	private ID3Tag() {}

	private static byte[] readXBytes(byte[] byteArray, int fromPos, int toPos) {
		byte[] resultArray = new byte[toPos - fromPos];
		for (int i = fromPos; i < toPos; i++)
		{
			resultArray[i - fromPos] = byteArray[i];
		}
		return resultArray;
	}

	public static ID3Tag parse(File file)	{
		byte[] last128 = tail(file);
		byte[] baTitle = readXBytes(last128, 3, 33);
		String title = new String(baTitle).trim();
		byte[] baArtist = readXBytes(last128, 33, 63);
		String artist = new String(baArtist).trim();
		byte[] baAlbum = readXBytes(last128, 63, 93);
		String album = new String(baAlbum).trim();
		byte[] baYear = readXBytes(last128, 93, 97);
		int year = Integer.parseInt(new String(baYear).trim());
		byte[] baComment = readXBytes(last128, 97, 125);
		String comment = new String(baComment).trim();
		byte[] baGenre = readXBytes(last128, 127, 128);
		int genre = (int) (new String(baGenre).charAt(0));

		ID3Tag tag = new ID3Tag();
		tag.setTitle(title);
		tag.setArtist(artist);
		tag.setAlbum(album);
		tag.setYear(year);
		tag.setComment(comment);
		tag.setGenre(genre);
		return tag;
	}

	public static byte[] tail(File file) {
		try
		{
			RandomAccessFile fileHandler = new RandomAccessFile(file, "r");
			long fileLength = fileHandler.length() - 1;
			byte[] buffer = new byte[128];

			for (int i = 0; i < buffer.length; i++)
			{
				fileHandler.seek(fileLength - 127 + i);
				buffer[i] = fileHandler.readByte();
			}
			fileHandler.close();
			return buffer;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year)	{
		this.year = year;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getGenre()	{
		return genre;
	}

	public void setGenre(int genre) {
		this.genre = genre;
	}

	@Override
	public boolean equals(Object o) {
		ID3Tag tag = (ID3Tag) o;
		return ((title == null && tag.title == null) || title.equals(tag.title)) && ((artist == null && tag.artist == null) || artist.equals(tag.artist))
				&& ((album == null && tag.album == null) || album.equals(tag.album)) && (year == tag.year) && ((comment == null && tag.comment == null) || comment.equals(tag.comment))
				&& (genre == tag.genre);
	}

	@Override
	public int hashCode()	{
		return -1;
	}

	@Override
	public String toString()
	{
	    StringBuffer mp3Tag = new StringBuffer();
	    mp3Tag.append("Artist: ").append((artist == null ? "NULL" : artist)).append("\n");
	    mp3Tag.append("Title: ").append((title == null ? "NULL" : title)).append("\n");
	    mp3Tag.append("Album: ").append((album == null ? "NULL" : album)).append("\n");
	    mp3Tag.append("Year: ").append((year == 0 ? "NULL" : year)).append("\n");
	    mp3Tag.append("Comment: ").append((comment == null ? "NULL" : comment)).append("\n");
	    mp3Tag.append("Genre: ").append((genre == 0 ? "NULL" : genre)).append("\n");
	    
	    return mp3Tag.toString();
	}
}
