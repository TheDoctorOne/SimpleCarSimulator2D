package Input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Main.GamePanel;
import Main.Main;

@SuppressWarnings("serial")
public class TextHandler extends Component implements InputHandler{
	
	private static TextHandler INSTANCE = new TextHandler(Main.INPUT_FILE_NAME_STRING);
	private final File inputFile;
	private String previousString = "";
	private String keySplit = " ";
		
	public static TextHandler getInstance() {
		return INSTANCE!=null ? INSTANCE : (INSTANCE = new TextHandler(Main.INPUT_FILE_NAME_STRING));
	}
	
	private TextHandler(String fileName) {
		inputFile = new File(fileName);
		
		if(!fileName.trim().equals(""))
			new Thread(this).start();
	}
	
	@Override
	public void run() {
		
		while(true) {
			if(!GamePanel.GetInstance().getInputOverride() && inputFile.exists())
				Handler();
			try {
				if(inputFile.exists())
					Thread.sleep(5);
				else
					Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void Handler() {
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(inputFile));
			String tmp = bReader.readLine();
			if(tmp == null)
				tmp = "";
			System.out.println(tmp + ":" + previousString);
			if(tmp!=null && !tmp.equals(previousString)) {
				for(String k : previousString.trim().split(keySplit)) {KeyRelease(handleString(k));}
				previousString = tmp;
			}
			for(String k : previousString.trim().split(keySplit)) {KeyPressed(handleString(k));}
			
			bReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int handleString(String k) {
		switch (k.toUpperCase()) {
			case "LEFT":
				return 37;
			case "RIGHT":
				return 39;
			case "FORWARD":
				return 38;
			case "BACK":
				return 40;
		}
		return -1;
	}

	@Override
	public void KeyRelease(int key) {
		GamePanel.GetInstance().keyReleased(new KeyEvent(this, 0, 0, 0, key, (char) key));
		
	}

	@Override
	public void KeyPressed(int key) {
		GamePanel.GetInstance().keyPressed(new KeyEvent(this, 0, 0, 0, key, (char) key));
		
	}

}
