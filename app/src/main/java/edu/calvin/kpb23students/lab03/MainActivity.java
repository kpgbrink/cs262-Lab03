package edu.calvin.kpb23students.lab03;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

/** This is a single-page, image protecter app based on Murach's Android Programming, 2nd
 *
 * For use as CS 262, Lab 3
 *
 * @author Kristofer
 * @version Fall, 2016
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // widget variables
    private EditText valueEditText;
    private TextView resultTextView;
    private ImageView androidImage;

    // instance variables saved
    private String valueAmountString = "";

    // define the Shared Preferences object
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to the widgets
        valueEditText = (EditText) findViewById(R.id.valuePassword);
        resultTextView = (TextView) findViewById(R.id.result);
        androidImage = (ImageView) findViewById(R.id.imageView);

        // Make image invisible
        androidImage.setVisibility(View.GONE);

        // get the SharedPreference object
        savedValues = getSharedPreferences("SharedValues", MODE_PRIVATE);

        // http://stackoverflow.com/a/3205405/2948122
        valueEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    checkPassword();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onPause() {
        // save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("valueAmountString", valueAmountString);
        editor.apply();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        valueAmountString = savedValues.getString("valueAmountString", "");


        valueEditText.setText(valueAmountString);

        checkPassword();
    }

    // Shows androidImage if password is valid
    public void checkPassword() {
        valueAmountString = valueEditText.getText().toString();
        String value;
        if (valueAmountString.equals("android")) {
            value = "valid password";
            androidImage.setVisibility(View.VISIBLE);
        } else {
            value = "invalid_password";
            androidImage.setVisibility(View.GONE);
        }

        resultTextView.setText(value);
    }

    @Override
    public void onClick(View v) { checkPassword();}
}