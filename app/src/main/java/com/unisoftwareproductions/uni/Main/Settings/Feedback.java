package com.unisoftwareproductions.uni.Main.Settings;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;

import com.unisoftwareproductions.uni.R;

/**
 * Created by Zachary on 20-Jul-16.
 */
public class Feedback extends AppCompatActivity {

    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App = new App(this);
        setContentView(R.layout.feedback);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_feedback);
    }

    public void sendFeedback (View view) {
        EditText et = (EditText) findViewById(R.id.etFeedback);
        String toast;
        if (App.getClient().feedback(et.getText().toString())) {toast = "Sent! Thank you for your feedback.";}
        else {toast = "Error Sending. Please try again later.";}
        Toast.makeText(Feedback.this, toast, Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
