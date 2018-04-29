package Calculator;

import javafx.beans.property.SimpleDoubleProperty;

public class Table {
	private SimpleDoubleProperty arg;
	private SimpleDoubleProperty func;
	public Table(double x, double y){
		this.arg = new SimpleDoubleProperty(x);
		this.func = new SimpleDoubleProperty(y);
	}
	public double getArg() {
		return arg.get();
	}
	public double getFunc() {
		return func.get();
	}
	public void setFunc(SimpleDoubleProperty func) {
		this.func = func;
	}
	public void setArg(SimpleDoubleProperty arg) {
		this.arg = arg;
	}
	

}
