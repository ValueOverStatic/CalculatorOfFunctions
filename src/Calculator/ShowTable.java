package Calculator;

/*
 * This class initializes all components of secondary window of app
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class ShowTable implements Initializable{
	final ObservableList<Table> dataOutput = FXCollections.observableArrayList();
	@FXML TableView<Table> table = new TableView<Table>();
	@FXML TableColumn<Table, Double> x = new TableColumn<Table, Double>();
	@FXML TableColumn<Table, Double> y = new TableColumn<Table, Double>();
	@FXML Label lb;
	@FXML private Button save;
	
	public void tabFunction(TextField ta, TextField tb, TextField th, TextArea tequation, TextField tvar){
		dataOutput.clear();
		ReversePolishNotation obj = new ReversePolishNotation(tequation.getText(), tvar.getText());
		double a=0;
		double b=0;
		double h=0;
		try{
		a = Double.parseDouble(ta.getText());
		b = Double.parseDouble(tb.getText());
		h = Double.parseDouble(th.getText());
		}catch(Exception e){System.out.println("Enter the interval and pace");};
		ArrayList<Double> x2;
		ArrayList<Double> y2;
		x2 = new ArrayList<Double>((int)((b-a)/h)+1);
		y2 = new ArrayList<Double>((int)((b-a)/h)+1);
		String str = new String();
		for (int i = 0; i < (int)((b-a)/h)+1; i++){
			x2.add(a + h*i);
			str = obj.searchOfVariable(x2.get(i));
			y2.add(obj.Calculating(obj.RPN(str)));
			dataOutput.add(new Table(x2.get(i), y2.get(i)));
		}
	}
	@FXML
	public void save(){
		lb.setText("");
		FileChooser fc = new FileChooser();
		File output = fc.showSaveDialog(null);
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));
		
		if (output != null){
		try {
			FileWriter fw = new FileWriter(output);
			fw.write("X"+"\t"+"Y"+System.getProperty("line.separator")+"-------------------");
			for(int i = 0; i < dataOutput.size();i++)
			fw.write(System.getProperty("line.separator")+
					Double.toString(dataOutput.get(i).getArg())+"\t"+
					Double.toString(dataOutput.get(i).getFunc()));
			fw.close();
			lb.setText("File has been saved successfully");
		} catch (IOException e) {lb.setText("Fail");
		}
		}
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		x.setCellValueFactory(new PropertyValueFactory<Table, Double>("arg"));
		y.setCellValueFactory(new PropertyValueFactory<Table, Double>("func"));
		table.setItems(dataOutput);
	}

}
