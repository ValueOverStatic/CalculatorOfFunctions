package Calculator;

/*
 * This class initializes all components of primary window of app
 */
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController{
	@FXML MenuBar menu;
	@FXML MenuItem help, close;
	@FXML TextField ta, tb, tvar, tres, teps, th;
	@FXML TextArea tequation;
	@FXML private Button build, clear, calculate, showTab;
	@FXML LineChart<Number, Number> linechart;
	public MainController(){
		tequation = new TextArea();
		ta = new TextField();
		tb = new TextField();
		th = new TextField();
		tvar = new TextField();
		tres = new TextField();
		teps = new TextField();
	}
	@FXML
	public void build(){
		ReversePolishNotation obj = new ReversePolishNotation(tequation.getText(), tvar.getText());
		double a = 0;
		double b = 0;
		double h = 0;
		try{
			a = Double.parseDouble(ta.getText());
			b = Double.parseDouble(tb.getText());
			h = Double.parseDouble(th.getText());
			if (a > b){
				a = Double.parseDouble(tb.getText());;
				b = Double.parseDouble(ta.getText());
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Enter the interval and pace", "Error", JOptionPane.ERROR_MESSAGE);
		};
		ArrayList<Double> x = new ArrayList<Double>((int)((b-a)/h)+1);
		ArrayList<Double> y = new ArrayList<Double>((int)((b-a)/h)+1);
		String str = new String();
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		linechart.setCreateSymbols(false);
		for (int i = 0; i < (int)((b-a)/h)+1; i++){
			x.add(a + h*i);
			str = obj.searchOfVariable(x.get(i));
			y.add(obj.Calculating(obj.RPN(str)));
			series.getData().add(new XYChart.Data<Number, Number>(x.get(i), y.get(i)));
		}
		linechart.getData().add(series);
	}
	@FXML
	public void clearChart(){
		linechart.getData().clear();
	}
	@FXML
	public void calc(){
		tres.clear();
		ReversePolishNotation obj = new ReversePolishNotation(tequation.getText(), tvar.getText());
		double a=0;
		double b=0;
		try{
		a = Double.parseDouble(ta.getText());
		b = Double.parseDouble(tb.getText());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Enter the interval and pace", "Error", JOptionPane.ERROR_MESSAGE);
		};
		
		tres.setText(
				Double.toString(new Calc().dichotomy(obj, a, b, 0,
						Double.parseDouble(teps.getText()))));
	}
	@FXML
	public void showTab(){
		try{
			AnchorPane tableLayout;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("GraphFX.fxml"));
			Stage tableStage = new Stage();
			tableStage.setTitle("Result");
			tableLayout = loader.load();
			Scene scene = new Scene(tableLayout);
			ShowTable st = (ShowTable)loader.getController();
			st.tabFunction(ta, tb, th, tequation, tvar);
			tableStage.setScene(scene);
			tableStage.show();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
};
		
	}
	@FXML
	public void help(){
		try{
		AnchorPane helpLayout;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("HelpFX.fxml"));
		Stage helpStage = new Stage();
		helpStage.setTitle("Help");
		helpLayout = loader.load();
		Scene scene = new Scene(helpLayout);
		Help hp = (Help)loader.getController();
		hp.hp();
		helpStage.setScene(scene);
		helpStage.showAndWait();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error of \"Help\" loading", "Error", JOptionPane.ERROR_MESSAGE);
			};
	}
	@FXML
	public void close(){
		Platform.exit();
		System.exit(0);
	}
}
