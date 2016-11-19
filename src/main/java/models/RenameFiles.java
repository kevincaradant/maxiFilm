package models;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RenameFiles {
	
	private File renameFile;
	private int numSeason, numEpisode;
	private String serialPattern;
	private String spaceChar;
	private Settings settings;
	private final String REG_GET_EXTENSION = ".*(\\..*)$";
	private final String REG_GET_EXTENSION_WITHOUT_NAME = "(.*)(\\..*)$";
	private final String REG_FORMAT_SERIAL_S00E00 = ".*((s|S)([0-9]{1,2}).{0,1}(e|E)([0-9]{1,2})).*";
	private final String REG_FORMAT_SERIAL_00x00 = ".*(([0-9]{1,2})(x|X)([0-9]{1,2})).*";
	private final String REG_FORMAT_MUSIC = "([0-9]{3})(.*)";
	private final String REG_CLEANING_NUMBER = "(.*)(\\(.*\\))(.*)";
	private final String REG_CLEANING_BRACKET = "(.*)(\\[.*\\])(.*)";
	private final String REG_CLEANING_ACCOLADE = "(.*)(\\{.*\\})(.*)";
	private final String REG_CLEANING_DATE = "(.*)([0-9]{4})(.*)";
	private final String REG_CLEANING_ACCENT = 	"[^\\p{ASCII}]";
	private final String REG_1_CLEANING_ZONE_TELECHARGEMENT = "(.*)(www.*zone.*telechargement.*com)(.*)";

	public RenameFiles (File pFileFilm, Settings pSettings)
	{
		renameFile = pFileFilm;
		spaceChar = " ";
		settings = pSettings;
	}
	
	public String getSpaceChar()
	{
		return spaceChar;
	}
	
	public void setSpaceChar(String pSpaceChar)
	{
		spaceChar = pSpaceChar;
	}
	
	public void setSpaceCharFromSettings(String pSpaceChar)
	{
		spaceChar = pSpaceChar;
	}
	
	public String getPath()
	{
		try {
			return "" + renameFile.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String getPathWithoutFile()
	{
		try {
			Pattern p = Pattern .compile("(.*)(" + this.echapAllChar(this.getName()) + ")$");
			
			Matcher m = p.matcher(this.getPath());
			
			if (m.find())
			return m.group(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	private String echapAllChar (String pString)
	{
		StringBuilder sb = new StringBuilder();
		
		for (char c : pString.toCharArray())
		{
			switch(c)
			{
				case '[':
				case ']':
				case '.':
				case '^':
				case '+':
				case '=':
				case '$':
				case '(':
				case ')':
				case '{':
				case '}':
				case '\\':
				sb.append('\\');
				default:
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
	
	public String getExtension()
	{
		try{
			Pattern p = Pattern .compile(REG_GET_EXTENSION);
			Matcher m = p.matcher(renameFile.getName());
			if (m.find())
			return m.group(1);
		}catch(PatternSyntaxException pse){
		}
		return "";
	}
	
	public String getNameWithoutExt()
	{
		try{
			Pattern p = Pattern .compile(REG_GET_EXTENSION_WITHOUT_NAME);
			Matcher m = p.matcher(renameFile.getName());
			if (m.find()){
				return m.group(1);
			}
		}catch(PatternSyntaxException pse){
		}
		return "";
	}
	
	public String getName ()
	{
		return renameFile.getName();
	}
	
	public boolean isSerial ()
	{
		// return true if it's a serie
		// return false if it's a movie or a music
		
		try{
			// S02E10
			Pattern p = Pattern .compile(REG_FORMAT_SERIAL_S00E00);
			
			Matcher m = p.matcher(renameFile.getName());
			
			if (m.find())
			{
				// set the season & episode number
				serialPattern = m.group(1);
				numSeason = Integer.valueOf(m.group(3));
				numEpisode = Integer.valueOf(m.group(5));
				
				return true;
			}
			
			else
			{
				// 02x10
				p = Pattern .compile(REG_FORMAT_SERIAL_00x00);
				
				m = p.matcher(renameFile.getName());
				
				if (m.find())
				{
					// set the season & episode number
					serialPattern = m.group(1);
					numSeason = Integer.valueOf(m.group(2));
					numEpisode = Integer.valueOf(m.group(4));
					
					return true;
				}
			}
		}catch(PatternSyntaxException pse){
		}
		return false;
	}
	
	public String getNumSeasonString (int pFormat)
	{
		if (!isSerial())
		return "";
		
		if (pFormat==0)
		{
			if (numSeason<=9)
			return "0" + numSeason;
		}
		
		return "" + numSeason;
	}
	
	public String getNumEpisodeString (int pFormat)
	{
		// pFormat = 0 to have "01" for the episode 1
		// pFormat = 1 to have "1"
		
		if (!isSerial())
		return "";
		
		if (pFormat==0)
		{
			if (numEpisode<=9)
			return "0" + numEpisode;
		}
		
		return "" + numEpisode;
	}
	
	public Integer getNumSeasonInt ()
	{
		if (!isSerial())
		return -1;
		
		return numSeason;
	}
	
	public Integer getNumEpisodeInt ()
	{
		if (!isSerial())
		return -1;
		
		return numEpisode;
	}
	
	public String getCleanName()
	{	
		Pattern p;
		Matcher m;
		
		String nameTemp = getNameWithoutExt().toLowerCase();
		try{
			// If it's a music
			if(extensionIsMusic() && settings.getRemoveTrack()){
				p = Pattern .compile(REG_FORMAT_MUSIC);
				m = p.matcher(nameTemp);
				if (m.find())
				nameTemp = (m.group(2));
			}
			// CLEANING
			// 0-9
			p = Pattern .compile(REG_CLEANING_NUMBER);
			
			m = p.matcher(nameTemp);
			if (m.find())
			nameTemp = (m.group(1)+m.group(3));
			// []
			p = Pattern .compile(REG_CLEANING_BRACKET);
			
			m = p.matcher(nameTemp);
			if (m.find())
			nameTemp = (m.group(1)+m.group(3));
			// {}
			p = Pattern .compile(REG_CLEANING_ACCOLADE);
			
			m = p.matcher(nameTemp);
			if (m.find())
			nameTemp = (m.group(1)+m.group(3));
			
			// cleaning date if position is more than 8 characters
			p = Pattern .compile(REG_CLEANING_DATE);
			
			m = p.matcher(nameTemp);
			if (m.find())
			{
				if (m.start(2)>=8)
				nameTemp = (m.group(1));
			}
			
			// cleaning accents
			nameTemp = Normalizer.normalize(nameTemp, Normalizer.Form.NFD).replaceAll(REG_CLEANING_ACCENT, "");
			
			// cleaning "www_zone_telechargement_com"
			p = Pattern .compile(REG_1_CLEANING_ZONE_TELECHARGEMENT);
			
			m = p.matcher(nameTemp);
			if (m.find())
			{
				if (m.start(2)>=8)
				nameTemp = (m.group(1) + " " + m.group(3));
			}
			// cleaning of the spaces
			nameTemp = replaceWeirdChar(nameTemp, " ");
			// trim
			// cleaning of the spaces
			nameTemp = replaceWeirdChar(nameTemp, spaceChar);
			nameTemp = cleanDoubleSpace(nameTemp); // after deleted the useless words, sometimes you can have two spaces
			// SETTINGS
			// if it's a serial --> erase all after SxEx
			if (isSerial())
			{
				p = Pattern .compile("(.*)(" + replaceWeirdChar(serialPattern.toLowerCase(), spaceChar) + ")(.*)");
				
				m = p.matcher(nameTemp);
				if (m.find())
				nameTemp = (m.group(1));
			}
			// all the first letter in capital
			if (this.settings.areAllFirstLetterCapital()){
				nameTemp = allFirstLetterInCapital(nameTemp);
			}
			
			
			// first letter in capital
			else if (this.settings.isFirstLetterCapital()){
				nameTemp = firstLetterInCapital(nameTemp);
				replaceWeirdChar(nameTemp, " ");
				nameTemp = nameTemp.trim();
			}
			// set separator and clean
			setSpaceChar(settings.getSeparatorMovieName());
			// cleaning of the spaces
			nameTemp = replaceWeirdChar(nameTemp, spaceChar);
			// trim
			nameTemp = nameTemp.trim();
			
			// if it's a serial :
			if (isSerial())
			{
				//adding of the separator
				nameTemp += settings.getSepaSymbolMovieSeasonEp();
				
				// adding of the season&episode number
				nameTemp += settings.getSymbolSeasonEpisode(this.getNumSeasonString(0), this.getNumEpisodeString(0));
			}
			
			// trim
			nameTemp = nameTemp.trim();
			
		}catch(PatternSyntaxException pse){
		}
		
		return nameTemp;
	}
	
	private boolean extensionIsMusic() {
		ArrayList<String> list_ext_music = new ArrayList<String>();
		list_ext_music.add(".mp3");
		list_ext_music.add(".flac");
		list_ext_music.add(".wav");
		list_ext_music.add(".ogg");
		list_ext_music.add(".oga");
		list_ext_music.add(".raw");
		list_ext_music.add(".alac");
		list_ext_music.add(".wma");
		list_ext_music.add(".au");
		list_ext_music.add(".asf");
		list_ext_music.add(".aac");
		list_ext_music.add(".atrac");
		list_ext_music.add(".riff");
		
		if(list_ext_music.contains(getExtension()))
		return true;
		return false;
	}
	
	private String cleanDoubleSpace (String pString)
	{
		int strLenOld = pString.length(), strLenNew = -1;
		
		// double "__"
		while (strLenOld!=strLenNew)
		{
			strLenOld = pString.length();
			pString = pString.replaceAll("__", "_");
			strLenNew = pString.length();
		}
		
		// double "  "
		strLenOld = pString.length();
		strLenNew = -1;
		
		while (strLenOld!=strLenNew)
		{
			strLenOld = pString.length();
			pString = pString.replaceAll("  ", " ");
			strLenNew = pString.length();
		}
		return pString;
	}
	
	private String firstLetterInCapital (String pString)
	{
		// my sentence --> My sentence
		char[] charTable = pString.toCharArray();
		if(charTable.length >0){
			charTable[0]=Character.toUpperCase(charTable[0]);
			return new String(charTable);
			
		}else{
			return new String("");
			
		}
	}
	
	private String allFirstLetterInCapital (String pString)
	{
		// my super sentence --> My Super Sentence
		String newString = ""; // string which contain the whole sentence with all the words with the first letter in capital
		
		pString = replaceWeirdChar(pString, " ");
		
		String str[]=pString.split(" ");
		// for all words --> 1) put the first letter in capital, 2) add this word at the new string to rebuild the full string
		for (int i=0 ; i<=str.length-1 ; i++)
		{
			if(i != str.length-1){
				newString += firstLetterInCapital(str[i]) + settings.getSeparatorMovieName();
			}
			else{
				newString += firstLetterInCapital(str[i]);
			}
		}
		return newString;
	}
	
	private String replaceWeirdChar(String pBase, String pCarSpace)
	{
		// pBase	 : String where you want to replace the spaces
		// pCarSpace : the caracter to put for each space
		String newBase = "";
		
		for (int i=0 ; i<=pBase.length()-1 ; i++)
		{
			Pattern p = Pattern.compile("[\\p{Alpha}\\p{Digit}]");
			
			Matcher m = p.matcher(String.valueOf(pBase.charAt(i)));
			if (m.find()){
				newBase += pBase.substring(i,i+1);
			}
			
			
			else{
				newBase += spaceChar;
			}
			
			
			
		}
		return newBase;
	}
	
	public void applyRename (String pRename)
	{
		//Obtain the reference of the existing file
		File oldFile = new File(this.getPath());
		
		//Now invoke the renameTo() method on the reference, oldFile in this case
		oldFile.renameTo(new File(this.getPathWithoutFile() + pRename + getExtension()));
	}
}
