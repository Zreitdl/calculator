package com.example.calculator;

public class ExpressionParser implements Parser {
	private Lexem curlex;
	private String v1;
	private int c;
	private String a;
	
	private enum Lexem {
		Num, Open, Close, End, Plus, Minus, Mul, Div, UnMinus, VarX, VarY,
		VarZ, Modulus, LeftShift, RightShift, Abs, Square;
	}
	
	public ExpressionParser() {
	}
	
	public Actions parse(String s) {
		c = -1;
		a = s.trim();
		Actions answer = null;
		
		nextlexem();
		answer = low();
		
		return answer;
	}
	
	private void nextlexem() {
		c++;
		if (c >= a.length()) {
			curlex = Lexem.End;
			return;
		}
		if (Character.isWhitespace(a.charAt(c))) {
			nextlexem();
			return;
		}
		switch (a.charAt(c)) {
			case '(':
				curlex = Lexem.Open;
				break;
            case ')':
                curlex = Lexem.Close;
                break;
			case '+':
				curlex = Lexem.Plus;
				break;
			case '-':
				if ( curlex == Lexem.Num || curlex == Lexem.Close || curlex == Lexem.VarX || curlex == Lexem.VarY || curlex == Lexem.VarZ) {
					curlex = Lexem.Minus;
				} else {
					if (Character.isDigit(a.charAt(c + 1))) {
						c++;
						v1 = "-" + findNumber();
						curlex = Lexem.Num;
						return;
					} else {
						curlex = Lexem.UnMinus;
					}
				}
				break;
			case '*':
				curlex = Lexem.Mul;
				break;
			case '/':
				curlex = Lexem.Div;
				break;
			case 'm':
                if (a.substring(c, c + 3).equals("mod") && !Character.isAlphabetic(a.charAt(c + 3))) {
					curlex = Lexem.Modulus;
					c += 2;
					break;
				}
			case '<':
				if (a.charAt(c + 1) == '<') {	
					curlex = Lexem.LeftShift;
					c++;
					break;
				}
			case '>':
				if (a.charAt(c + 1) == '>') {
					curlex = Lexem.RightShift;
					c++;
					break;
				}
			case 'a':
                if (a.substring(c, c + 3).equals("abs") && !Character.isAlphabetic(a.charAt(c + 3))) {
                    curlex = Lexem.Abs;
                    c += 2;
                    break;
                }
            case 's':
                if (a.substring(c, c + 6).equals("square") && !Character.isAlphabetic(a.charAt(c + 6))) {
                    curlex = Lexem.Square;
                    c += 5;
                    break;
                }
			case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '0':
                v1 = findNumber();
                curlex = Lexem.Num;
                break;
            case ' ':
                break;
            case 'x':
                curlex = Lexem.VarX;
                break;
            case 'y':
                curlex = Lexem.VarY;
                break;
            case 'z':
                curlex = Lexem.VarZ;
                break;
		}
	}
		
	private Actions low() {

        Actions a = expr();
        while (curlex == Lexem.LeftShift || curlex == Lexem.RightShift) {
            Lexem t = curlex;
            nextlexem();
            if (t == Lexem.LeftShift) {
                a = new LeftShift(a, expr());
            }
            if (t == Lexem.RightShift) {
                a = new RightShift(a, expr());
            }
        }
        return a;
    }

    private Actions expr() {

        Actions a = item();
        while (curlex == Lexem.Plus || curlex == Lexem.Minus) {
            Lexem t = curlex;
            nextlexem();
            if (t == Lexem.Plus) {
                a = new CheckedAdd(a, item());
            }
            if (t == Lexem.Minus) {
                a = new CheckedSubtract(a, item());
            }
        }
        return a;

    }

    private Actions item() {
        Actions a = mult();
        while (curlex == Lexem.Mul || curlex == Lexem.Div || curlex == Lexem.Modulus) {
            Lexem t = curlex;
            nextlexem();
            if (t == Lexem.Mul) {
                a = new CheckedMultiply(a, mult());
            } else if (t == Lexem.Div) {
                a = new CheckedDivide(a, mult());
            } else if (t == Lexem.Modulus) {
                a = new Modulus(a, mult());
            }
        }
        return a;
    }

    private Actions mult() {
        Actions answer = null;
        switch (curlex) {
            case UnMinus:
                nextlexem();
                answer = new CheckedNegate(mult());
                break;
            case Num:
                answer = new Const(Integer.parseInt(v1));
                nextlexem();
                break;
            case VarX:
                answer = new Variable("x");
                nextlexem();
                break;
            case VarY:
                answer = new Variable("y");
                nextlexem();
                break;
            case VarZ:
                answer = new Variable("z");
                nextlexem();
                break;
            case Abs:
                nextlexem();
                answer = new Abs(mult());
                break;
            case Square:
                nextlexem();
                answer = new Square(mult());
                break;
            case Open:
                nextlexem();
                answer = low();
                if (curlex == Lexem.Close) nextlexem();
                break;
        }
        return answer;
    }

    private String findNumber() {
        String answer = "";
        while (c < a.length() && Character.isDigit(a.charAt(c))) {
            answer += a.charAt(c);
            c++;
        }
        c--;
        return answer;
    }
}
