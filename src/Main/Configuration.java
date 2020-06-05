package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
	
	private static String[][] properties = {
			{"WINDOW_HEIGHT","700"},
			{"WINDOW_WIDTH","1000"},
			{"CAR_HEIGHT","50"},
			{"CAR_WIDTH","30"},
			{"CAR_X","150"},
			{"CAR_Y","200"},
			{"MAX_SPEED","3.0"},
			{"VELOCITY","0.4"},
			{"TEXT_INPUT_ACTIVE","true"},
			//{"",""}
	};
	private static String configFileName = "config.ini"; 
	private static File configFile = null;
	
	
	
	public static void readConfig() {
		configFile = new File(configFileName);
		try {
			if(configFile.exists()) {
				BufferedReader bReader = new BufferedReader(new FileReader(configFile));
				String tmpString = "";
				while((tmpString = bReader.readLine()) != null) {
					for(String[] s: properties) {
						if(tmpString.trim().toUpperCase().startsWith(s[0])) {
							s[1] = tmpString.split("=")[1].trim();
						}
					}
				}
				bReader.close();
			} else {
				if(configFile.createNewFile()) {
					BufferedWriter bWriter = new BufferedWriter(new FileWriter(configFile));
					for (String[] s: properties) {
						bWriter.append(s[0] + "=" + s[1] + "\n");
					}
					bWriter.flush();
					bWriter.close();
				}
			}
		} catch (IOException e) {
			
		}
	}
	
	public static String getProperty(String propertyName) {
		readConfig();
		for(String[] s: properties)
			if (s[0].trim().toUpperCase().equals(propertyName.toUpperCase()))
				return s[1].trim();
		return null;
	}
	
	public static Boolean getBooleanProperty(String propertyName) {
		readConfig();
		for(String[] s: properties)
			if (s[0].trim().toUpperCase().equals(propertyName.toUpperCase()))
				return Boolean.parseBoolean(s[1].trim());
		return null;
	}
	
	public static Double getDoubleProperty(String propertyName) {
		readConfig();
		for(String[] s: properties)
			if (s[0].trim().toUpperCase().equals(propertyName.toUpperCase()))
				return Double.parseDouble(s[1].trim());
		return null;
	}
	
	private Configuration() {}

}
