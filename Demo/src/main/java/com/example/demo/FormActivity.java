package com.example.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends Activity {

    private EditText firstName;
    private EditText lastName;
    private Button buttonSubmit;

    DecelerateInterpolator decelerator = new DecelerateInterpolator();
    OvershootInterpolator overshooter = new OvershootInterpolator(10f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Show the Up button in the action bar.
        setupActionBar();

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = firstName.getText().toString();
                String last = lastName.getText().toString();
                String message = "Hello " + first + " " + last + "!";
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        buttonSubmit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonSubmit.animate().setInterpolator(decelerator).scaleX(.7f).scaleY(.7f);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonSubmit.animate().setInterpolator(overshooter).scaleX(1f).scaleY(1f);
                }
                return false;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
