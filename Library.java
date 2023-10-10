//Yusuf Hamed (Library.java)
//Student number - 501161616

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{

		//Storing the type into the String type, for comparitive purposes
		String type = content.getType();


		//-----------------------------------------IF SONG-----------------------------------------
		//if the type of the audio content is a song
		if(type.equals(Song.TYPENAME)){

			//Type casting content into a song since we know its a song type
			Song song = (Song) content;

			//song already in list
			for(int i = 0; i < songs.size(); i++)
			{
				if(songs.get(i).equals(song)){
					throw new SongAlreadyExistsException(song.getType() + " " + song.getTitle() + " Already Downloaded");
				}
			}

			//if song not in list, adding to list of songs and returning true
			songs.add(song);
			System.out.println(song.getType() + " " + song.getTitle() + " Added To Library");

		}//----------------------------------END OF SONG-------------------------------------------

		//-----------------------------------IF AUDIOBOOK------------------------------------------
		//if the type of the audio content is an audiobook
		if(type.equals(AudioBook.TYPENAME)){

			//Type casting content into a Audiobook since we know its a Audiobook type
			AudioBook a_book = (AudioBook) content;

			//audiobook already in list
			for(int k = 0; k < audiobooks.size(); k++) {
				if (audiobooks.get(k).equals(a_book)) {
					//throwing exception if already downladed
					throw new AudioBookAlreadyExistsException(a_book.getType() + " " + a_book.getTitle() + " Already Downloaded");
				}
			}

			//if Audiobook not in list, adding to list of Audiobooks and returning true
			audiobooks.add(a_book);
			System.out.println(a_book.getType() + " " + a_book.getTitle() + " Added To Library");

		}//----------------------------------END OF AUDIOBOOK-------------------------------------------

	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			//printing the info of the songs with the content number
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		//for loop to iterate through audiobooks and print the info
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{

	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		//for loop to iterate through playlists, grab the titles and print out with content number
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i+1;
			System.out.print("" + index + ". ");
			System.out.println(playlists.get(i).getTitle());
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names

		ArrayList<String> artists = new ArrayList<String>();

		for(int i = 0; i < songs.size(); i++){
			if(!(artists.contains(songs.get(i).getArtist()))){
				artists.add(songs.get(i).getArtist());
			}
		}

		for(int j = 0; j < artists.size(); j++){
			System.out.println(j + 1 + ". " + artists.get(j));
		}
		
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		//checking to see if index is valid
		if(index < 1 || index > songs.size()){
			throw new SongNotFoundException("Song not found");
		}

		//checking if the songs is part of any playlist
		for(int i = 0; i < playlists.size(); i++){

			for(int j = 0; j < playlists.get(i).getContent().size(); j++) {
				//if song part of playlist, removing from list
				if (playlists.get(i).getContent().get(j).equals(songs.get(index - 1))) {
					playlists.get(i).getContent().remove(j);
				}
			}
		}

		//removing song from songs list
		songs.remove(songs.get(index - 1));
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort()
		Collections.sort(songs, new SongYearComparator());
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song song_1, Song song_2){
			return Integer.compare(song_1.getYear(), song_2.getYear());
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort()
		Collections.sort(songs, new SongLengthComparator());

	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song song_1, Song song_2){
			return Integer.compare(song_1.getLength(), song_2.getLength());
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code

		Collections.sort(songs);
	}

	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new SongNotFoundException("Song not found");
		}
		songs.get(index-1).play();
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		//checking if not valid index
		if (index < 1 || index > audiobooks.size())
		{
			//throwing exception if not valid
			throw new AudioBookNotFoundException("Audiobook Not Found");
		}

		//selecting the chapter and playing
		audiobooks.get(index-1).selectChapter(chapter);
		audiobooks.get(index-1).play();

	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		//checking if index not valid
		if(index < 1 || index > audiobooks.size()) {

			//throwing exception if not valid
			throw new AudioBookNotFoundException("Audiobook Not Found");
		}

		//printing the toc of specified audiobook
		audiobooks.get(index-1).printTOC();
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		//for loop to check if the playlist wanting to be made already exists, if it is return false
		for(int i = 0; i < playlists.size(); i++){
			if(playlists.get(i).getTitle().equals(title)){
				throw new PlaylistAlreadyExistsException("Playlist with this title already exists");
			}
		}

		//Making new playlist with given name as not found within the list
		Playlist newP = new Playlist(title);

		//adding to playlist
		playlists.add(newP);
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		//for loop to check if the playlist exists
		for(int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(title)) {
				playlists.get(i).printContents();
				return;
			}
		}

		throw new PlaylistNotFoundException("Playlist title not found");
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		//variable to hold the index of the title, if it exists
		int index = -1;

		//for loop to check if the playlist exists, if it does, storing index into variable index
		for(int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(playlistTitle)) {
				index = i;
			}
		}

		//playlist not found in playlists list so throwing exception
		if(index == -1){
			throw new PlaylistNotFoundException("Playlist title not found");
		}

		//play content in playlist
		playlists.get(index).playAll();
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		//for loop to check if the playlist exists, if it does, storing index into variable index
		for(int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(playlistTitle)) {
				if(indexInPL < 1 || indexInPL > playlists.get(i).getContent().size()){
					throw new AudioContentNotFoundException("Content not found");

				}
				//playing specific song
				playlists.get(i).play(indexInPL - 1);
				return;
			}
		}

		//if playlist not found throwing exception
		throw new PlaylistNotFoundException("Playlist title not found");
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		//if the type is of song
		if(type.equalsIgnoreCase(Song.TYPENAME)){

			//if index given not valid throw exception
			if(index < 0 || index > songs.size()){
				throw new AudioContentNotFoundException("Content not found");
			}

			//checking for playlist within playlists
			for(int i = 0; i < playlists.size(); i++){
				if(playlists.get(i).getTitle().equals(playlistTitle)) {
					playlists.get(i).addContent(songs.get(index - 1));
					return;
				}
			}

			//if playlist didn't exist throwing playlistnotfoundexception
			throw new PlaylistNotFoundException("Playlist title not found");

		}

		//if the type is of Audiobook
		if(type.equalsIgnoreCase(AudioBook.TYPENAME)) {

			//if index given not valid throwing exception
			if (index < 0 || index > audiobooks.size()) {
				throw new AudioContentNotFoundException("Content not found");
			}

			//checking for playlist within playlists
			for (int i = 0; i < playlists.size(); i++) {
				if (playlists.get(i).getTitle().equals(playlistTitle)) {
					playlists.get(i).addContent(audiobooks.get(index - 1));
					return;
				}
			}

			//if playlist didn't exist throwing playlistnotfoundexception
			throw new PlaylistNotFoundException("Playlist title not found");
		}

		//if type not known throwing typenotfound exception
		throw new TypeNotFoundException("Content type not found");

	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		//for loop to find the playlist with the given title
		for(int i = 0; i < playlists.size(); i++){
			if(playlists.get(i).getTitle().equals(title)){
				if(index >= 0 && index < playlists.get(i).getContent().size()) {
					playlists.get(i).deleteContent(index);
					return;
				}
				else{
					throw new AudioContentNotFoundException("Content not found");
				}
			}
		}

		throw new PlaylistNotFoundException("Playlist title not found");
	}
	
}

class AudioContentNotFoundException extends RuntimeException{

	public AudioContentNotFoundException(String message){
		super(message);
	}
}

class PlaylistNotFoundException extends RuntimeException{
	public PlaylistNotFoundException(String message){
		super(message);
	}
}

class TypeNotFoundException extends RuntimeException{
	public TypeNotFoundException(String message){
		super(message);
	}
}

class PlaylistAlreadyExistsException extends RuntimeException{
	public PlaylistAlreadyExistsException(String message){
		super(message);
	}
}

class AudioBookAlreadyExistsException extends RuntimeException{
	public AudioBookAlreadyExistsException(String message){
		super(message);
	}
}

class AudioBookNotFoundException extends RuntimeException{
	public AudioBookNotFoundException(String message){
		super(message);
	}
}

class SongNotFoundException extends RuntimeException{
	public SongNotFoundException(String message){
		super(message);
	}
}

class SongAlreadyExistsException extends RuntimeException{
	public SongAlreadyExistsException(String message){
		super(message);
	}
}


