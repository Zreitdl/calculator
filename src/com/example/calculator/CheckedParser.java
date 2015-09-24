package com.example.calculator;

public class CheckedParser implements Parser {
    private Lexem curlex;
    private String number;
    private int place;
    private String a;

    private enum Lexem {
        Begin, Num, Open, Close, End, Plus, Mul, Minus, Div, UnMinus, VarX, VarY, VarZ, Mod, Left, Right, Abs, Square, Sqrt, Pow, Log
    }

    public CheckedParser() {
    }

    private int brackets;

    public Actions parse(String s) throws MyException {
        place = -1;
        a = s.trim();
        a += ' ';
        Actions answer;
        brackets = 0;
        curlex = Lexem.Begin;
        nextlexem();
        answer = low();
        if (brackets != 0)
            throw new MyException("wrong parenthesis");
        return answer;

    }

    private void nextlexem() throws MyException {
        place++;
        if (place >= a.length()) {
            curlex = Lexem.End;
            return;
        }
        if (Character.isWhitespace(a.charAt(place))) {
            nextlexem();
            return;
        }
        switch (a.charAt(place)) {
            case '(':
                curlex = Lexem.Open;
                brackets++;
                break;
            case ')':
                curlex = Lexem.Close;
                brackets--;
                if (brackets < 0)
                    throw new MyException("wrong parenthesis");
                break;
            case '+':
                curlex = Lexem.Plus;
                break;
            case '-':
                if (curlex != Lexem.Begin && (curlex == Lexem.Num || curlex == Lexem.Close || curlex == Lexem.VarX || curlex == Lexem.VarY || curlex == Lexem.VarZ)) {
                    curlex = Lexem.Minus;
                } else {
                    if (Character.isDigit(a.charAt(place + 1))) {
                        place++;
                        number = "-" + findNumber();
                        curlex = Lexem.Num;
                        return;
                    } else {
                        curlex = Lexem.UnMinus;
                    }
                }
                break;
            case '*':
                if (a.charAt(place + 1) == '*') {
                    curlex = Lexem.Pow;
                    place++;
                } else {
                    curlex = Lexem.Mul;
                }
                break;
            case '/':
                if (a.charAt(place + 1) == '/') {
                    curlex = Lexem.Log;
                    place++;
                } else {
                    curlex = Lexem.Div;
                }
                break;
            case 'm':
                if (a.substring(place, place + 3).equals("mod") && !Character.isAlphabetic(a.charAt(place + 3))) {
                    curlex = Lexem.Mod;
                    place += 2;
                    break;
                }
            case '<':
                if (a.charAt(place + 1) == '<') {
                    curlex = Lexem.Left;
                    place++;
                    break;
                }
            case '>':
                if (a.charAt(place + 1) == '>') {
                    curlex = Lexem.Right;
                    place++;
                    break;
                }
            case 'a':
                if (a.substring(place, place + 3).equals("abs") && !Character.isAlphabetic(a.charAt(place + 3))) {
                    curlex = Lexem.Abs;
                    place += 2;
                    break;
                }
            case 's':
                if (a.substring(place, place + 6).equals("square") && !Character.isAlphabetic(a.charAt(place + 6))) {
                    curlex = Lexem.Square;
                    place += 5;
                    break;
                }
                if (a.substring(place, place + 4).equals("sqrt") && !Character.isAlphabetic(a.charAt(place + 4))) {
                    curlex = Lexem.Sqrt;
                    place += 3;
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
                number = findNumber();
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
            default:
                throw new MyException("wrong symbol");
        }
    }

    private Actions low() throws MyException {

        Actions a = expr();
        if (a == null)
            throw new MyException("wrong arguments");
        while (curlex == Lexem.Left || curlex == Lexem.Right) {
            Lexem t = curlex;
            nextlexem();
            if (t == Lexem.Left) {
                a = new LeftShift(a, expr());
            }
            if (t == Lexem.Right) {
                a = new RightShift(a, expr());
            }
        }
        return a;
    }

    private Actions expr() throws MyException {

        Actions a = item();
        if (a == null) {
            throw new MyException("wrong arguments");
        }
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

    private Actions item() throws MyException {
        Actions a = high();
        if (a == null) {
            throw new MyException("wrong arguments");
        }
        while (curlex == Lexem.Mul || curlex == Lexem.Div || curlex == Lexem.Mod) {
            Lexem t = curlex;
            nextlexem();
            if (t == Lexem.Mul) {
                a = new CheckedMultiply(a, high());
            }
            if (t == Lexem.Div) {
                a = new CheckedDivide(a, high());
            }
            if (t == Lexem.Mod) {
                a = new Modulus(a, high());
            }
        }
        return a;
    }

    private Actions high() throws MyException {
        Actions a = mult();
        if (a == null) {
            throw new MyException("wrong arguments");
        }
        while (curlex == Lexem.Pow || curlex == Lexem.Log) {
            Lexem t = curlex;
            nextlexem();
            if (t == Lexem.Pow) {
                a = new Pow(a, mult());
            }
            if (t == Lexem.Log) {
                a = new Log(a, mult());
            }
        }
        return a;
    }

    private Actions mult() throws MyException {
        Actions answer;
        switch (curlex) {
            case UnMinus:
                nextlexem();
                answer = new CheckedNegate(mult());
                break;
            case Num:
                try {
                    answer = new Const(Integer.parseInt(number));
                } catch (NumberFormatException e) {
                    throw new MyException("overflow");
                }
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
            case Sqrt:
                nextlexem();
                answer = new Sqrt(mult());
                break;
            case Open:
                nextlexem();
                answer = low();
                if (curlex == Lexem.Close) {
                    nextlexem();
                } else
                    throw new MyException("wrong parenthesis");
                break;
            default:
                throw new MyException("wrong arguments");


        }
        return answer;
    }

    private String findNumber() throws MyException {
        String answer = "";
        while (place < a.length() && Character.isDigit(a.charAt(place))) {
            answer += a.charAt(place);
            place++;
        }
        place--;
        return answer;
    }
}
