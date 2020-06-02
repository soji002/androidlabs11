package com.dd.lab001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Switch switch1;
    LinearLayout linearLayout;
    GridLayout gridlayout;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        Button button = findViewById(R.id.button);
        switch1 = (Switch)findViewById(R.id.switch1);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        gridlayout = (GridLayout) findViewById(R.id.gridlayout);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        switch1.setOnCheckedChangeListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                Toast.makeText(getApplicationContext(),R.string.toast_message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        if(switch1.isChecked()){
            switch1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar = Snackbar
                            .make(gridlayout, "The switch is now on", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            buttonView.setChecked(!isChecked);
                        }
                    });
                    snackbar.show();
                }
            });
        }else{
            switch1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar = Snackbar
                            .make(gridlayout, "The switch is now off", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    buttonView.setChecked(!isChecked);
                                }
                            });

                    snackbar.show();
                }
            });
        }
    }
}
