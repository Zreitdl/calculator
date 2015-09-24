package com.example.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MyActivity extends Activity implements View.OnClickListener {
    public static String expr = "";
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        System.out.println("123");

        TextView textView = (TextView)findViewById(R.id.textView);

        //buttons Numbers
        Button buttonOne = (Button)findViewById(R.id.buttonOne);
        Button buttonTwo = (Button)findViewById(R.id.buttonTwo);
        Button buttonThree = (Button)findViewById(R.id.buttonThree);
        Button buttonFour = (Button)findViewById(R.id.buttonFour);
        Button buttonFive = (Button)findViewById(R.id.buttonFive);
        Button buttonSix = (Button)findViewById(R.id.buttonSix);
        Button buttonSeven = (Button)findViewById(R.id.buttonSeven);
        Button buttonEight = (Button)findViewById(R.id.buttonEight);
        Button buttonNine = (Button)findViewById(R.id.buttonNine);
        Button buttonZero = (Button)findViewById(R.id.buttonZero);

        //buttons signs
        Button buttonPlus = (Button)findViewById(R.id.buttonPlus);
        Button buttonMinus = (Button)findViewById(R.id.buttonMinus);
        Button buttonMultiply = (Button)findViewById(R.id.buttonMultiply);
        Button buttonDivide = (Button)findViewById(R.id.buttonDivide);
        Button buttonEquals = (Button)findViewById(R.id.buttonEquals);
        Button buttonLeftbracket = (Button)findViewById(R.id.buttonLeftbracket);
        Button buttonRightbracket = (Button)findViewById(R.id.buttonRightbracket);
        Button buttonPoint = (Button)findViewById(R.id.buttonPoint);

        //other buttons
        Button buttonClear = (Button)findViewById(R.id.buttonClear);
        Button buttonBackspace = (Button)findViewById(R.id.buttonBackspace);

        //System.out.println("123");

        //Listen it
        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonFive.setOnClickListener(this);
        buttonSix.setOnClickListener(this);
        buttonSeven.setOnClickListener(this);
        buttonEight.setOnClickListener(this);
        buttonNine.setOnClickListener(this);
        buttonZero.setOnClickListener(this);

        buttonMinus.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonLeftbracket.setOnClickListener(this);
        buttonRightbracket.setOnClickListener(this);
        buttonPoint.setOnClickListener(this);

        buttonBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)findViewById(R.id.textView);
                String text = (String)textView.getText();
                if (text != null && !text.equals("")) {
                    text = text.substring(0,text.length() - 1);
                }
                textView.setText(text);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)findViewById(R.id.textView);
                textView.setText("");
            }
        });

        //equals listener
        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)findViewById(R.id.textView);
                String text = (String)textView.getText();
                CheckedParser parser = new CheckedParser();
                try {
                    Actions result = parser.parse(text);
                    int x = result.evaluate(0);
                    text = Integer.toString(x);
                } catch (MyException e) {
                    String exceptionText = e.text;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyActivity.this);
                    // set title
                    alertDialogBuilder.setTitle("Something wrong :(");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Click yes to exit!")
                            .setCancelable(false)
                            .setTitle("Что-то пошло не так :(")
                            .setMessage(exceptionText)
                            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                } catch (ArithmeticException e) {
                    text = "wrong";
                }
                textView.setText(text);
            }
        });
    }

    @Override
    public void onClick(View v) {
        TextView textView = (TextView)findViewById(R.id.textView);
        String text = textView.getText().toString();
        Button button = (Button)v;
        text += button.getText().toString();
        textView.setText(text);
    }
}


