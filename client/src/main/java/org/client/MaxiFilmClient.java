package org.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.commons.RenameFiles;
import org.commons.Settings;

public class MaxiFilmClient {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[1;31m";
	public static final String ANSI_GREEN = "\u001B[1;32m";
	public static final String ANSI_YELLOW = "\u001B[1;33m";
	public static final String ANSI_BLUE = "\u001B[1;34m";
	public static final String ANSI_PURPLE = "\u001B[1;35m";
	public static final String ANSI_CYAN = "\u001B[1;36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	private static List<File> files = new ArrayList<File>();
	private static Settings settings = new Settings();
	private static RenameFiles film;
	private static String answerLoopRename = "";

	private static String JUMP = "\n\n";

	private static File[] listRecursiveFiles(Path path) {
		File[] arrayFiles = new File[]{};
		try {
			arrayFiles = Files.walk(Paths.get(path.toUri())).parallel().map(file -> file.toFile())
					.toArray(File[]::new);

		} catch (IOException e) {
			System.out.println(ANSI_RED + "PATH INCORRECT. NO FILE TO RENAME" + ANSI_RED);
		}
		return arrayFiles;
	}

	public static void main(String[] args) {
		// Read all folders to get files
		for (String path : args) {
			if (!path.equalsIgnoreCase("-f") && !path.equalsIgnoreCase("-force")) {
				File[] filesT = listRecursiveFiles(Paths.get(path));
				if (filesT == null) {
					System.out.println(ANSI_RED + "PATH INCORRECT. NO FILE TO RENAME" + ANSI_RED);
					System.exit(1);
				}
				files.addAll(Arrays.asList(filesT));
			} else {
				answerLoopRename = "ya";
			}
		}

		// Restore the settings if available
		settings.restoreSettings();

		// Loop on all files
		System.out.println("\n" + ANSI_PURPLE + files.size() + " files detected" + ANSI_PURPLE);
		for (int i = 0; i < files.size(); i++) {
			int indice = i + 1;
			// If you want to rename all files
			if (answerLoopRename.equalsIgnoreCase("ya") || answerLoopRename.equalsIgnoreCase("yes all")) {
				film = new RenameFiles(files.get(i), settings);
				film.applyRename(film.getCleanName());

				// Else If you don't refuse all files
			} else if (!answerLoopRename.equalsIgnoreCase("na")) {

				// You ask for the first item
				film = new RenameFiles(files.get(i), settings);
				Scanner scanner = new Scanner(System.in);

				System.out.println(JUMP);

				if (!film.getNameWithoutExt().equals("")) {

					// Ask the answer of the rename file
					System.out.println(ANSI_BLUE + "------------------------------------" + ANSI_BLUE);
					System.out.println(ANSI_BLUE + "------ File To Rename (" + ANSI_WHITE + indice + " / "
							+ files.size() + ANSI_BLUE + ") ------" + ANSI_BLUE);
					System.out.println(ANSI_BLUE + "------------------------------------" + ANSI_BLUE);
					System.out.println(ANSI_RED + "\nFile Before: " + film.getNameWithoutExt() + ANSI_RED);
					System.out.println(ANSI_GREEN + "File After: " + film.getCleanName() + ANSI_GREEN);
					System.out.println(ANSI_RESET + "\nDo you want to rename the file ?" + ANSI_RESET);
					System.out.println(ANSI_CYAN + "y / yes: ONLY rename this file" + ANSI_CYAN);
					System.out.println(ANSI_CYAN + "n / no: ONLY refuse to rename this file" + ANSI_CYAN);
					System.out.println(ANSI_CYAN + "na / no all: Refuse for all files" + ANSI_CYAN);
					System.out.println(ANSI_CYAN + "ya / yes all: Accept to rename all files ?" + ANSI_CYAN);
					answerLoopRename = scanner.next();

					// If we accept to rename one or all files
					if (answerLoopRename.equalsIgnoreCase("y") || answerLoopRename.equalsIgnoreCase("yes")
							|| answerLoopRename.equalsIgnoreCase("ya")
							|| answerLoopRename.equalsIgnoreCase("yes all")) {
						film = new RenameFiles(files.get(i), settings);
						film.applyRename(film.getCleanName());
					}
				} else {
					System.out.println(ANSI_RED + "------ File can't be renamed (" + ANSI_WHITE + indice + " / "
							+ files.size() + ANSI_RED + ") ------" + ANSI_RED);
				}
			}
			if (files.size() == indice) {
				System.out.println(ANSI_GREEN + JUMP + "|--------------| " + ANSI_GREEN);
				System.out.println(ANSI_GREEN + "|  Finished !  | " + ANSI_GREEN);
				System.out.println(ANSI_GREEN + "|--------------| " + ANSI_GREEN);
			}
		}
		
		System.exit(0);
	}
}
