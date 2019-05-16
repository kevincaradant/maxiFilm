/*
* I - Settings for rename mode
*
* 		A) Movie or Music name
* 			1) game of thrones
* 			2) Game of thrones
* 			3) Game Of Thrones
* 			4) game_of_thrones
* 			5) Game_of_thrones
* 			6) Game_Of_Thrones
*
* 		B) For serials : Separation movie name & season/episode
* 			1) MOVIE NAME SEASON/EPISODE
* 			2) MOVIE NAME-SEASON/EPISODE
* 			3) MOVIE NAME_SEASON/EPISODE
* 			4) MOVIE NAME - SEASON/EPISODE
* 			5) MOVIE NAME _ SEASON/EPISODE
*
* 		C) For serials : Season & Episode
* 			1) s00 e00
* 			2) s00-e00
* 			3) s00_e00
* 			4) s00xe00
* 			5) s00e00
* 			6) 00x00
* 			7) S00 E00
* 			8) S00-E00
* 			9) S00_E00
* 			10) S00xE00
* 		 	11) S00E00
*			12) 00X00
*/

/*
* By default :
* A) 4
* B) 3
* C) 11
*/

package org.commons;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("serial")
public class Settings implements Serializable {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[1;31m";
	public static final String ANSI_GREEN = "\u001B[1;32m";
	public static final String ANSI_YELLOW = "\u001B[1;33m";
	public static final String ANSI_BLUE = "\u001B[1;34m";
	public static final String ANSI_PURPLE = "\u001B[1;35m";
	public static final String ANSI_CYAN = "\u001B[1;36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	private int movieName;			 // Typical name for a movie or a serial
	private int maxMovieName;			 // Typical name for a movie or a serial
	private int maxSeasonEpisode;			 // Typical name for a movie or a serial
	private int maxSepaMovieSeasonEp;			 // Typical name for a movie or a serial
	private int sepaMovieSeasonEp;	 // Separator between the serial name & the season/episode number
	private int seasonEpisode;		 // Typical display of the season & episode number
	private boolean removeTrack;		 // Typical display of the season & episode number

	public Settings ()
	{
		this.movieName = 4;
		this.maxMovieName = 6;
		this.sepaMovieSeasonEp = 3;
		this.maxSepaMovieSeasonEp = 5;
		this.seasonEpisode = 11;
		this.maxSeasonEpisode = 12;
		this.removeTrack = false;
	}

	// GET (MAX)
	public int getMaxMovieName() {
		return maxMovieName;
	}

	public int getMaxSepaMovieSeasonEp() {
		return maxSepaMovieSeasonEp;
	}

	public int getMaxSeasonEpisode() {
		return maxSeasonEpisode;
	}

	// GET
	public int getMovieName() {
		return movieName;
	}

	public int getSepaMovieSeasonEp() {
		return sepaMovieSeasonEp;
	}

	public int getSeasonEpisode() {
		return seasonEpisode;
	}

	public Boolean getRemoveTrack() {
		return removeTrack;
	}


	// SET
	public void setMovieName(int movieName) {
		if (movieName>=1 && movieName<=getMaxMovieName())
		this.movieName = movieName;
	}

	public void setRemoveTrack(boolean pRemoveTrack) {
		this.removeTrack = pRemoveTrack;
	}


	public String getSepaSymbolMovieSeasonEp() {
		switch (sepaMovieSeasonEp) {
			case 1:
			return " ";
			case 2:
			return " - ";
			case 3:
			return "-";
			case 4:
			return " _ ";
			case 5:
			return "_";
			default:
			return " ";
		}
	}

	public void setSepaMovieSeasonEp(int sepaMovieSeasonEp) {
		if (sepaMovieSeasonEp >= 1 && sepaMovieSeasonEp <= getMaxSepaMovieSeasonEp())
		this.sepaMovieSeasonEp = sepaMovieSeasonEp;
	}

	public String getSymbolSeasonEpisode(String pNumSeason, String pNumEpisode) {
		switch (seasonEpisode) {
			case 1:
			return "s" + pNumSeason + " e" + pNumEpisode;
			case 2:
			return "S" + pNumSeason + " E" + pNumEpisode;
			case 3:
			return "s" + pNumSeason + "_e" + pNumEpisode;
			case 4:
			return "S" + pNumSeason + "-E" + pNumEpisode;
			case 5:
			return "s" + pNumSeason + "-e" + pNumEpisode;
			case 6:
			return "S" + pNumSeason + "_E" + pNumEpisode;
			case 7:
			return "s" + pNumSeason + "xe" + pNumEpisode;
			case 8:
			return "S" + pNumSeason + "XE" + pNumEpisode;
			case 9:
			return "s" + pNumSeason + "e" + pNumEpisode;
			case 10:
			return "S" + pNumSeason + "E" + pNumEpisode;
			case 11:
			return pNumSeason + "x" + pNumEpisode;
			case 12:
			return pNumSeason + "X" + pNumEpisode;
			default:
			return "";
		}
	}

	public void setSeasonEpisode(int seasonEpisode) {
		if (seasonEpisode>=1 && seasonEpisode<=getMaxSeasonEpisode())
		this.seasonEpisode = seasonEpisode;
	}

	public String getSeparatorMovieName()
	{
		switch(movieName) {
			case 1:
			return " ";
			case 2:
			return "_";
			case 3:
			return " ";
			case 4:
			return "_";
			case 5:
			return " ";
			case 6:
			return "_";
			default:
			return " ";
		}
	}


	public boolean isFirstLetterCapital ()
	{
		if (movieName==4 || movieName==3 || movieName==5 || movieName==6)
		return true;
		return false;
	}

	public boolean areAllFirstLetterCapital ()
	{
		if (movieName==5 || movieName==6)
		return true;
		return false;
	}

	public static void saveSettings (Settings settings){
		try (Writer writer = new FileWriter("settings.json")) {
			Gson gsonSerialize = new GsonBuilder().create();
			gsonSerialize.toJson(settings, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void restoreSettings (){
		Gson gsonDeserialize = new GsonBuilder().create();

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("settings.json"));
			Settings settingsObj = gsonDeserialize.fromJson(br, Settings.class);
			//check if the parameters are correct
			if( validatorMovieName(settingsObj) &&  validatorSeasonEpisode(settingsObj) && validatorSeapaMovieSeasonEp(settingsObj) && validatorRemoveTrack(settingsObj)){
				this.setRemoveTrack(settingsObj.removeTrack);
				this.setMovieName(settingsObj.movieName);
				this.setSeasonEpisode(settingsObj.seasonEpisode);
				this.setSepaMovieSeasonEp(settingsObj.sepaMovieSeasonEp);
			}
		} catch (FileNotFoundException e) {
			System.out.println(ANSI_RED + "FILE SETTINGS NOT FOUND. NEW ONE WILL BE CREATED" + e + ANSI_RED);
			saveSettings(new Settings());
		}
	}
	
	public void restoreSettings (String path){
		Gson gsonDeserialize = new GsonBuilder().create();

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path));
			System.out.println(ANSI_WHITE + "FILE SETTINGS FOUND: " + path + ANSI_WHITE);
			Settings settingsObj = gsonDeserialize.fromJson(br, Settings.class);
			//check if the parameters are correct
			if( validatorMovieName(settingsObj) &&  validatorSeasonEpisode(settingsObj) && validatorSeapaMovieSeasonEp(settingsObj) && validatorRemoveTrack(settingsObj)){
				this.setRemoveTrack(settingsObj.removeTrack);
				this.setMovieName(settingsObj.movieName);
				this.setSeasonEpisode(settingsObj.seasonEpisode);
				this.setSepaMovieSeasonEp(settingsObj.sepaMovieSeasonEp);
			}
		} catch (FileNotFoundException e) {
			System.out.println(ANSI_RED + e + ANSI_RED);
		}
	}

	public Boolean validatorMovieName(Settings settingsObj){
		return settingsObj.movieName <= this.getMaxMovieName();

	}

	public Boolean validatorSeasonEpisode(Settings settingsObj){
		return settingsObj.seasonEpisode <= this.getMaxSeasonEpisode();

	}

	public Boolean validatorSeapaMovieSeasonEp(Settings settingsObj){
		return settingsObj.sepaMovieSeasonEp <= this.maxSepaMovieSeasonEp;

	}

	public Boolean validatorRemoveTrack(Settings settingsObj){
		if (settingsObj.removeTrack == true || settingsObj.removeTrack == false)
		return true;
		return false;
	}
}
