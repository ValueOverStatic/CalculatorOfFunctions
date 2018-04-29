package Calculator;


/*
 * This class realizes methods for formulas recognizing
 */

import java.util.ArrayList;
public class ReversePolishNotation{
	private String infixNotation;
	private String var;
	ArrayList<Character> rpn = new ArrayList<Character>(); //Stack of symbols
	public ReversePolishNotation(String str, String v) {
		infixNotation = new String(str);
		var = new String(v);
	}
public ArrayList<String> RPN(String infixNotation){
	ArrayList<String> postfixNotation = new ArrayList<String>();
    String buf = new String();
    for (int i = 0; i < infixNotation.length(); i++){
        if ((infixNotation.charAt(i) >= '0' && infixNotation.charAt(i) <= '9') ||
infixNotation.charAt(i) == '.' || (infixNotation.charAt(i) == '-' &&
(i == 0 || buf.isEmpty() && infixNotation.charAt(i-1) != ')' || infixNotation.charAt(i-1) == 'E')) ||
(infixNotation.charAt(i) == 'E' && (infixNotation.charAt(i+1)>='0' && infixNotation.charAt(i+1)<='9' ||
infixNotation.charAt(i+1) == '-'))){
            buf += infixNotation.charAt(i);
        }
        else{
            postfixNotation.add(buf);
            buf = "";
            if (infixNotation.charAt(i) == ')'){
                while ((char)rpn.get(rpn.size()-1) != '('){
                    buf = rpn.get(rpn.size()-1).toString();
                    rpn.remove(rpn.size()-1);
                    postfixNotation.add(buf);
                    buf = "";
                }
                rpn.remove(rpn.size()-1);
                while (!rpn.isEmpty() && (char)rpn.get((rpn.size()-1)) >= 'a'){
                    buf += rpn.get((rpn.size()-1));
                    rpn.remove(rpn.size()-1);
                }
                postfixNotation.add(buf);
                buf = "";
            }
            else{
                if (rpn.isEmpty())
                    rpn.add(infixNotation.charAt(i));
                else{
                    if (infixNotation.charAt(i) == '(')
                        rpn.add(infixNotation.charAt(i));
                    else{
                        if (infixNotation.charAt(i) >= 'a' && infixNotation.charAt(i) <= 'z')
                            rpn.add(infixNotation.charAt(i));
                        else{
                            if (Priority(infixNotation.charAt(i)) < Priority((char)rpn.get(rpn.size()-1)))
                                rpn.add(infixNotation.charAt(i));
                            else{
                                while (!rpn.isEmpty() && (Priority(infixNotation.charAt(i)) >= Priority((char)rpn.get(rpn.size()-1)))){
                                    buf=rpn.get(rpn.size()-1).toString();
                                    rpn.remove(rpn.size()-1);
                                    postfixNotation.add(buf);
                                }
                                buf = "";
                                rpn.add(infixNotation.charAt(i));
                            }
                        }
                    }
                }
            }
        }
    }
    postfixNotation.add(buf);
    while (!rpn.isEmpty()){
        buf = rpn.get(rpn.size()-1).toString();
        rpn.remove(rpn.size()-1);
        postfixNotation.add(buf);
    }
    //erase helps to get rid of "rubbish" from remove to the end:
    while(postfixNotation.contains(""))
    	postfixNotation.remove(postfixNotation.indexOf(""));
    return postfixNotation;
}

double Calculating(ArrayList<String> postfixNotation){
    ArrayList<Double> res = new ArrayList<Double>();
    double a, b;
    for (int i = 0; i < postfixNotation.size(); i++){
        if((postfixNotation.get(i).charAt(0) >= '0' && postfixNotation.get(i).charAt(0) <= '9') ||
        		(postfixNotation.get(i).charAt(0) == '-' && postfixNotation.get(i).length() > 1))
        	res.add(Double.parseDouble(((String)postfixNotation.get(i))));
        else
            if(postfixNotation.get(i).charAt(0) >= 'a' && postfixNotation.get(i).charAt(0) <= 'z'){
                b = (double)res.get(res.size()-1);
                res.remove(res.size()-1);
                res.add(MathFunc(postfixNotation.get(i), b));
            }
            else{
                b = (double)res.get(res.size()-1);
                res.remove(res.size()-1);
                a = (double)res.get(res.size()-1);
                res.remove(res.size()-1);
                res.add(MathFunc(postfixNotation.get(i),a, b));
            }
    }
    return (double)res.get(0);
}

char Priority(char c){
	char res;
    switch (c){
        case '^': res=1;
        break;
        case '*': res=2;
        break;
        case '/': res=2;
        break;
        case '+': res=3;
        break;
        case '-': res=3;
        break;
        default: res=4;
    }
    return res;
}
double MathFunc(String f, double x){
    double res = 0;
    // in "" is name of math functions, written in reversely (for example "nat" -> "tan" - tangents) 
    if (f.equals("sba")) res = Math.abs(x);
    if (f.equals("trqs")) res = Math.sqrt(x);
    if (f.equals("nis")) res = Math.sin(x);
    if (f.equals("soc")) res = Math.cos(x);
    if (f.equals("nat")) res = Math.tan(x);
    if (f.equals("natc")) res = 1/Math.tan(x);
    if (f.equals("nl")) res = Math.log(x);
    if (f.equals("gl")) res = Math.log10(x);
    if (f.equals("pxe")) res = Math.exp(x);
    if (f.equals("hnis")) res = Math.sinh(x);
    if (f.equals("hsoc")) res = Math.cosh(x);
    if (f.equals("ht")) res = Math.tanh(x);
    if (f.equals("htoc")) res = 1/Math.tanh(x);
    return res;
}

double MathFunc(String f, double a, double b){ //(actually - math operations)
	double res;
    switch (f.charAt(0)){
        case '+': res=a+b;
        break;
        case '-': res=a-b;
        break;
        case '*': res=a*b;
        break;
        case '/': res=a/b;
        break;
        case '^': res=Math.pow(a,b);
        break;
        default: res=0;
    }
    return res;
}
public String searchOfVariable(double x){
	String str = new String(infixNotation);
	for (int i = 0; i < str.length(); i++){
		int j = 0;
		if(str.charAt(i) == var.charAt(0)){
			if (i == 0 || str.charAt(i-1) < 'a' || str.charAt(i-1) > 'z'){
				while (j < var.length() && (str.charAt(i+j) == var.charAt(j)))
					j++;
				if ((j == var.length()) && (((i+j-1) == str.length()-1 || str.charAt(i+j) < 'a' || (str.charAt(i+j) > 'z'))))
					if (x >= 0 || i == 0) str = str.substring(0, i)+Double.toString(x)+str.substring(i+j, str.length());
					else if (i==1 && str.charAt(0) == '-') str = str.substring(0, i-1)+Double.toString(-x)+str.substring(i+j, str.length());
					else str = str.substring(0, i)+'('+Double.toString(x)+')'+str.substring(i+j, str.length());
			}
		}
	}
	return str;
}

}

