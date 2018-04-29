package Calculator;

import javax.swing.JOptionPane;

public class Calc {
	public double dichotomy(ReversePolishNotation obj, double a, double b, double x, double eps){
		String stra = new String(obj.searchOfVariable(a));
		String strb = new String(obj.searchOfVariable(b));
		double c;
		if (Math.abs(obj.Calculating(obj.RPN(stra)))<=eps ||
				Math.abs(obj.Calculating(obj.RPN(strb)))<=eps){
			if (Math.abs(obj.Calculating(obj.RPN(stra)))<=eps)
				c=a;
			else
				c=b;
			}
		else{
		if (obj.Calculating(obj.RPN(stra))*obj.Calculating(obj.RPN(strb)) > 0){
			if (Math.abs(obj.Calculating(obj.RPN(obj.searchOfVariable(extremum(obj, a, b, x, eps))))) <= eps)
				c = extremum(obj, a, b, x, eps);
			else{
				JOptionPane.showMessageDialog(null, "There is not one root");
				throw new ArrayIndexOutOfBoundsException();
			}
		}
			c = (a+b)/2;
			String strc = new String(obj.searchOfVariable(c));
			while (Math.abs(obj.Calculating(obj.RPN(strc))) > eps){
				if (obj.Calculating(obj.RPN(stra))*obj.Calculating(obj.RPN(strc)) > 0){
					a = c;
					stra = obj.searchOfVariable(a); //refresh stda
				}
				else{
					b = c;
					strb = obj.searchOfVariable(b); //refresh stdb
				}
				c = (a+b)/2;
				strc = obj.searchOfVariable(c); //refresh stdc
				
			}
		}
		return c;
	}
	public double extremum(ReversePolishNotation obj, double a, double b, double x, double eps){
		double dYa = obj.Calculating(obj.RPN(obj.searchOfVariable(a+eps)))-obj.Calculating(obj.RPN(obj.searchOfVariable(a)));
		double dYb = obj.Calculating(obj.RPN(obj.searchOfVariable(b)))-obj.Calculating(obj.RPN(obj.searchOfVariable(b-eps)));
		double c;
		if (Math.abs(dYa)<=eps ||
				Math.abs(dYb)<=eps){
			if (Math.abs(dYa)<=eps)
				c=a;
			else
				c=b;
			}
		else{
		if (dYa*dYb > 0){
			JOptionPane.showMessageDialog(null, "There is not one extremum");
			throw new ArrayIndexOutOfBoundsException();
		}
			c = (a+b)/2;
			double dYc = obj.Calculating(obj.RPN(obj.searchOfVariable(c+eps)))-obj.Calculating(obj.RPN(obj.searchOfVariable(c)));
			while (Math.abs(dYc) > eps){
				if (dYa*dYc > 0){
					a = c;
					dYa = obj.Calculating(obj.RPN(obj.searchOfVariable(a+eps)))-obj.Calculating(obj.RPN(obj.searchOfVariable(a))); //refresh dYa
				}
				else{
					b = c;
					dYb = obj.Calculating(obj.RPN(obj.searchOfVariable(b)))-obj.Calculating(obj.RPN(obj.searchOfVariable(b-eps))); //refresh dYb
				}
				c = (a+b)/2;
				dYc = obj.Calculating(obj.RPN(obj.searchOfVariable(c+eps)))-obj.Calculating(obj.RPN(obj.searchOfVariable(c))); //refresh dYc
			}
		}
		return c;
	}
}
