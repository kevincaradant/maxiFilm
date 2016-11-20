package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import models.RenameFiles;
import models.Settings;

public class RegexRenameFile {
	// TEST TV SHOWS
	
	// TEST MOVIE NAME
	@Test
	public void regexM1S1SE1() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan s04 e01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(1);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM2S1SE1() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray_donovan s04 e01";
		Settings settings = new Settings();
		settings.setMovieName(2);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(1);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM3S1SE1() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "Ray donovan s04 e01";
		Settings settings = new Settings();
		settings.setMovieName(3);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(1);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM4S1SE1() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "Ray_donovan s04 e01";
		Settings settings = new Settings();
		settings.setMovieName(4);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(1);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM5S1SE1() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "Ray Donovan s04 e01";
		Settings settings = new Settings();
		settings.setMovieName(5);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(1);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM6S1SE1() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "Ray_Donovan s04 e01";
		Settings settings = new Settings();
		settings.setMovieName(6);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(1);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	// TEST SEPARATOR SERIAL NAME
	@Test
	public void regexM1S2SE1() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan - s04 e01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(2);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(1);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S3SE1() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan-s04 e01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(3);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(1);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S4SE1() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan _ s04 e01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(4);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(1);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S5SE1() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan_s04 e01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(5);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(1);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}

	
	// TEST MOVIE SEPARATOR SEASON EPISODE
	@Test
	public void regexM1S1SE2() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan S04 E01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(2);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S1SE3() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan s04_e01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(3);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S1SE4() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan S04-E01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(4);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S1SE5() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan s04-e01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(5);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S1SE6() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan S04_E01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(6);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S1SE7() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan s04xe01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(7);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S1SE8() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan S04XE01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(8);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S1SE9() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan s04e01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(9);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S1SE10() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan S04E01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(10);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S1SE11() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan 04x01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(11);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
	
	@Test
	public void regexM1S1SE12() {
		String movieDropped = "Ray Donovan S04E01 FASTSUB VOSTFR 720p HDTV x264-SDTEAM www Zone-Telechargement.mkv";
		String movieCleaned = "ray donovan 04X01";
		Settings settings = new Settings();
		settings.setMovieName(1);
		settings.setSepaMovieSeasonEp(1);
		settings.setRemoveTrack(true);
		settings.setSeasonEpisode(12);
		RenameFiles film = new RenameFiles(new File(movieDropped), settings);
		assertEquals(movieCleaned, film.getCleanName());
	}
}
