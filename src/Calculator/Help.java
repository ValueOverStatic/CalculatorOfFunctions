package Calculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class Help {
	@FXML private TextArea helpText;
	public Help(){
		helpText = new TextArea();
	}
	public void hp(){
		try {
			String str = new String("");
			File help = new File("Help.txt");
			Scanner scan = new Scanner(help);
			while (scan.hasNextLine())
				str += scan.nextLine()+System.getProperty("line.separator");
			helpText.setText(str);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
