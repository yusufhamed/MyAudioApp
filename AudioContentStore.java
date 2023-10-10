//Yusuf Hamed (AudioContentStore.java)
//Student number - 501161616

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.*;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents;
		private Map<String, Integer> search = new HashMap<>();
		private Map<String, ArrayList<Integer>> searchA = new HashMap<>();
		private Map<String, ArrayList<Integer>> searchG = new HashMap<>();
		
		public AudioContentStore() {
			contents = new ArrayList<>();

			try {
				contents = contentMaker("store.txt");
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}

			//Maps a title string (the key) to an integer index value.
			// This integer represents the index for the song/book in the contents array list.
			for (int i = 0; i < contents.size(); i++) {
				search.put(contents.get(i).getTitle(), i);
			}

			//Map artist (String) --> Integer ArrayList (Integers in the arrlist rep
			//the indicies into the contents array list (Author for audiobook)
			for (int i = 0; i < contents.size(); i++) {
				if (contents.get(i).getType().equals(Song.TYPENAME)) {

					Song tempSong = (Song) contents.get(i);

					if (searchA.containsKey(tempSong.getArtist())) {
						searchA.get(tempSong.getArtist()).add(i);
					} else {
						searchA.put(tempSong.getArtist(), new ArrayList<Integer>());
						searchA.get(tempSong.getArtist()).add((i));
					}
				}

				if (contents.get(i).getType().equals(AudioBook.TYPENAME)) {

					AudioBook tempBook = (AudioBook) contents.get(i);

					if (searchA.containsKey(tempBook.getAuthor())) {
						searchA.get(tempBook.getAuthor()).add(i);
					} else {
						searchA.put(tempBook.getAuthor(), new ArrayList<Integer>());
						searchA.get(tempBook.getAuthor()).add(i);
					}
				}

			}//end of for loop

			//Create a third map that uses the genre (string) of a song as a key and maps to an
			// integer arraylist(rather than a single integer).
			//The integers in the array list represent indices into the contents array list.
			for (int i = 0; i < contents.size(); i++) {

				if (contents.get(i).getType().equals(Song.TYPENAME)) {

					Song tempS = (Song) contents.get(i);

					if (searchG.containsKey(String.valueOf(tempS.getGenre()))) {
						searchG.get(tempS.getGenre().name()).add(i);
					} else {
						searchG.put(String.valueOf(tempS.getGenre()), new ArrayList<Integer>());
						searchG.get(String.valueOf(tempS.getGenre())).add(i);
					}

				}
			}
		}


		private ArrayList<AudioContent> contentMaker(String filename) throws IOException {

			ArrayList<AudioContent> content = new ArrayList<>();

			//Creating fileReader to read from file
			FileReader reader = new FileReader(filename);

			//Scanner to iterate through the file
			Scanner fileIn = new Scanner(reader);

			while (fileIn.hasNextLine()) {

				//Storing String of file line into variable
				String line = fileIn.nextLine();

				String id = fileIn.nextLine();
				String title = fileIn.nextLine();
				int year = Integer.parseInt(fileIn.nextLine());
				int length = Integer.parseInt(fileIn.nextLine());

				ArrayList<String> args = new ArrayList<>();

				if (line.trim().length() == 0) {
					continue;
				}

				switch (line) {
					case "SONG":
						System.out.println("Loading SONG");
						String artist = fileIn.nextLine();
						String composer = fileIn.nextLine();
						String genre = fileIn.nextLine();
						String lyrics = "";
						int numOfLyrics = Integer.parseInt(fileIn.nextLine());

						for (int i = 0; i < numOfLyrics; i++) {
							lyrics += fileIn.nextLine();
						}

						Song s = new Song(title, year, id, Song.TYPENAME, lyrics, length, artist, composer, Song.Genre.valueOf(genre), lyrics);
						content.add(s);
						break;

					case "AUDIOBOOK":
						System.out.println("Loading AUDIOBOOK");
						String author = fileIn.nextLine();
						String narrator = fileIn.nextLine();
						int numOfChaps = Integer.parseInt(fileIn.nextLine());
						ArrayList<String> titles = new ArrayList<>();
						ArrayList<String> chapters = new ArrayList<>();
						String chapterstxt = "";

						for (int i = 0; i < numOfChaps; i++) {
							titles.add(fileIn.nextLine());
						}

						for (int i = 0; i < numOfChaps; i++) {
							int numOfLines = Integer.parseInt(fileIn.nextLine());
							for (int j = 0; j < numOfLines; j++) {
								chapterstxt += fileIn.nextLine();
							}

							chapters.add(chapterstxt);
							chapterstxt = "";
						}

						AudioBook a = new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, titles, chapters);
						content.add(a);
						break;
				}
			}

			return content;
		}

		//function to use the search map to find a title
		public void searchByTitle(String title){
			if(search.containsKey(title)){
				System.out.print(search.get(title) + 1 + ". ");
				contents.get(search.get(title)).printInfo();
				System.out.println();
			}
			else{
				System.out.println("No matches for " + title);
			}
		}

		//Function to use the SearchA map to find an artist
		public void searchByArtist(String aritst){

			if(searchA.containsKey(aritst)){
				for(int k: searchA.get(aritst)){
					System.out.print((k + 1) + ". ");
					contents.get(k).printInfo();
					System.out.println();
				}
			}
			else{
				System.out.println("No matches for " + aritst);
			}
		}

		//Function to use the SearchG map to search by genre
		public void searchByGenre(String genre){
			if(searchG.containsKey(genre)){
				for(int k: searchG.get(genre)){
					System.out.print((k + 1) + ". ");
					contents.get(k).printInfo();
					System.out.println();
				}
			}
			else{
				System.out.println("No matches for " + genre);
			}
		}

		//Function for downlaodA
		public ArrayList<Integer> downloadA(String artist){

			return searchA.get(artist);

		}

		//Function for downloadG
		public ArrayList<Integer> downloadG(String genre){

			return searchG.get(genre);

		}


		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
		private ArrayList<String> makeHPChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("The Riddle House");
			titles.add("The Scar");
			titles.add("The Invitation");
			titles.add("Back to The Burrow");
			return titles;
		}
		
		private ArrayList<String> makeHPChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("In which we learn of the mysterious murders\r\n"
					+ " in the Riddle House fifty years ago, \r\n"
					+ "how Frank Bryce was accused but released for lack of evidence, \r\n"
					+ "and how the Riddle House fell into disrepair. ");
			chapters.add("In which Harry awakens from a bad dream, \r\n"
					+ "his scar burning, we recap Harry's previous adventures, \r\n"
					+ "and he writes a letter to his godfather.");
			chapters.add("In which Dudley and the rest of the Dursleys are on a diet,\r\n"
					+ " and the Dursleys get letter from Mrs. Weasley inviting Harry to stay\r\n"
					+ " with her family and attend the World Quidditch Cup finals.");
			chapters.add("In which Harry awaits the arrival of the Weasleys, \r\n"
					+ "who come by Floo Powder and get trapped in the blocked-off fireplace\r\n"
					+ ", blast it open, send Fred and George after Harry's trunk,\r\n"
					+ " then Floo back to the Burrow. Just as Harry is about to leave, \r\n"
					+ "Dudley eats a magical toffee dropped by Fred and grows a huge purple tongue. ");
			return chapters;
		}
		
		private ArrayList<String> makeMDChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Loomings.");
			titles.add("The Carpet-Bag.");
			titles.add("The Spouter-Inn.");
			return titles;
		}
		private ArrayList<String> makeMDChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("Call me Ishmael. Some years ago never mind how long precisely having little\r\n"
					+ " or no money in my purse, and nothing particular to interest me on shore,\r\n"
					+ " I thought I would sail about a little and see the watery part of the world.");
			chapters.add("stuffed a shirt or two into my old carpet-bag, tucked it under my arm, \r\n"
					+ "and started for Cape Horn and the Pacific. Quitting the good city of old Manhatto, \r\n"
					+ "I duly arrived in New Bedford. It was a Saturday night in December.");
			chapters.add("Entering that gable-ended Spouter-Inn, you found yourself in a wide, \r\n"
					+ "low, straggling entry with old-fashioned wainscots, \r\n"
					+ "reminding one of the bulwarks of some condemned old craft.");
			return chapters;
		}
		
		private ArrayList<String> makeSHChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Prologue");
			titles.add("Chapter 1");
			titles.add("Chapter 2");
			titles.add("Chapter 3");
			return titles;
		}
		
		private ArrayList<String> makeSHChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("The gale tore at him and he felt its bite deep within\r\n"
					+ "and he knew that if they did not make landfall in three days they would all be dead");
			chapters.add("Blackthorne was suddenly awake. For a moment he thought he was dreaming\r\n"
					+ "because he was ashore and the room unbelieveable");
			chapters.add("The daimyo, Kasigi Yabu, Lord of Izu, wants to know who you are,\r\n"
					+ "where you come from, how ou got here, and what acts of piracy you have committed.");
			chapters.add("Yabu lay in the hot bath, more content, more confident than he had ever been in his life.");
			return chapters;
		}
		
		// Podcast Seasons
		/*
		private ArrayList<Season> makeSeasons()
		{
			ArrayList<Season> seasons = new ArrayList<Season>();
		  Season s1 = new Season();
		  s1.episodeTitles.add("Bay Blanket");
		  s1.episodeTitles.add("You Don't Want to Sleep Here");
		  s1.episodeTitles.add("The Gold Rush");
		  s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		  		+ "lip-syncing, but some people believe they were used to spread\r\n"
		  		+ " smallpox and decimate entire Indigenous communities. \r\n"
		  		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		  		+ " very complicated story of the iconic striped blanket.");
		  s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeLengths.add(31);
		  s1.episodeLengths.add(32);
		  s1.episodeLengths.add(45);
		  seasons.add(s1);
		  Season s2 = new Season();
		  s2.episodeTitles.add("Toronto vs Everyone");
		  s2.episodeTitles.add("Water");
		  s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"
		  		+ " In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"
		  		+ " and vampires, and discuss some big concerns currently facing Canada's water."); 
		  s2.episodeLengths.add(45);
		  s2.episodeLengths.add(50);
		 
		  seasons.add(s2);
		  return seasons;
		}
		*/
}
