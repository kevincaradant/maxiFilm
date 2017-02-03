package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.RenameFiles;
import models.Settings;
import models.Recurse;

/**
 * Hello world!
 *
 */
public class MaxiFilmCli {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[1;31m";
	public static final String ANSI_GREEN = "\u001B[1;32m";
	public static final String ANSI_YELLOW = "\u001B[1;33m";
	public static final String ANSI_BLUE = "\u001B[1;34m";
	public static final String ANSI_PURPLE = "\u001B[1;35m";
	public static final String ANSI_CYAN = "\u001B[1;36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	private static ArrayList<File> files = new ArrayList<File>();
	private static Settings settings = new Settings();
	private static RenameFiles film;
	private static String answerLoopRename = "";
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {

			ArrayList<ArrayList<File>> filesTemp = new ArrayList<ArrayList<File>>();

			// Read all folders to get files
			for (String path : args) {
				if(!path.equalsIgnoreCase("-f") && !path.equalsIgnoreCase("-force")) {
					Recurse r = new Recurse();
					Files.walkFileTree(Paths.get(path), r);
					filesTemp.add(r.getFiles());
				} else {
					answerLoopRename = "ya";
				}
			}

			// Flatten to have only one arrayList of data
			for (ArrayList<File> pathsArrayList : filesTemp) {
				for (int i = 0; i < pathsArrayList.size(); i++) {
					files.add(pathsArrayList.get(i));
				}
			}

			// Restore the settings if available
			restoreSettings();

			// Loop on all files
			System.out.println("\n" +ANSI_PURPLE + files.size() + " files detected" + ANSI_PURPLE);
			for (int i = 0; i < files.size(); i++) {
				int indice = i+1;
				// If you want to rename all files
				if(answerLoopRename.equalsIgnoreCase("ya") || answerLoopRename.equalsIgnoreCase("yes all")) {
					film = new RenameFiles(files.get(i), settings);
					film.applyRename(film.getCleanName());
					
				// Else If you don't refuse all files
				} else if (!answerLoopRename.equalsIgnoreCase("na")) {
					
					// You ask for the first item
					film = new RenameFiles(files.get(i), settings);
					Scanner scanner = new Scanner(System.in);
					
					// Avoid a big space on the first loop
					if(i != 0) {
						System.out.println("\n\n\n\n");
					}
					
					// Ask the answer of the rename file
					System.out.println(ANSI_BLUE + "\n------------------------------------" + ANSI_BLUE);
					System.out.println(ANSI_BLUE + "------ File To Rename (" + ANSI_WHITE + indice +" / "+files.size() + ANSI_BLUE + ") ------" + ANSI_BLUE);
					System.out.println(ANSI_BLUE + "------------------------------------" + ANSI_BLUE);
					System.out.println(ANSI_RED + "\nFile Before: " + film.getNameWithoutExt() + ANSI_RED);
					System.out.println(ANSI_GREEN + "File After: " + film.getCleanName() + ANSI_GREEN);
					System.out.println(ANSI_RESET + "\nDo you want to rename the file ?" +ANSI_RESET);
					System.out.println(ANSI_CYAN + "y / yes: ONLY rename this file" + ANSI_CYAN);
					System.out.println(ANSI_CYAN + "n / no: ONLY refuse to rename this file" + ANSI_CYAN);
					System.out.println(ANSI_CYAN + "na / no all: Refuse for all files" + ANSI_CYAN);
					System.out.println(ANSI_CYAN + "ya / yes all: Accept to rename all files ?" + ANSI_CYAN);
					answerLoopRename = scanner.next();

					// If we accept to rename one or all files
					if (answerLoopRename.equalsIgnoreCase("y") || answerLoopRename.equalsIgnoreCase("yes") || answerLoopRename.equalsIgnoreCase("ya") || answerLoopRename.equalsIgnoreCase("yes all")) {
						film = new RenameFiles(files.get(i), settings);
						film.applyRename(film.getCleanName());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void restoreSettings() {
		Gson gsonDeserialize = new GsonBuilder().create();

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("settings.json"));
			Settings settingsObj = gsonDeserialize.fromJson(br, Settings.class);

			// Check if the parameters are correct
			if (settingsObj.getMovieName() <= settings.getMaxMovieName()
					&& settingsObj.getSeasonEpisode() <= settings.getMaxSeasonEpisode()
					&& settingsObj.getSepaMovieSeasonEp() <= settings.getMaxSepaMovieSeasonEp()) {
				settings.setMovieName(settingsObj.getMovieName());
				settings.setSeasonEpisode(settingsObj.getSeasonEpisode());
				settings.setSepaMovieSeasonEp(settingsObj.getSepaMovieSeasonEp());
				settings.setRemoveTrack(settingsObj.getRemoveTrack());
			}
		} catch (FileNotFoundException e) {
			System.out.println(ANSI_RED + "File settings.json not Found" +ANSI_RED);
			Logger.getLogger(MaxiFilmCli.class.getName()).log(Level.CONFIG, "File Not Found", e);
		}
	}
}
