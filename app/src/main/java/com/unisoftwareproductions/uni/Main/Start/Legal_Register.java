package com.unisoftwareproductions.uni.Main.Start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.unisoftwareproductions.uni.R;

/**
 * Created by Zachary on 10-Jul-16.
 */
public class Legal_Register extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_legal1);
        findViewById(R.id.legalconfirm1).setOnClickListener(this);
        TextView tvLegal1 = (TextView) findViewById(R.id.tvTermsofUse);
        tvLegal1.setText(Html.fromHtml(getString(R.string.TermsofUse_html)));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_legal);
    }

    // Advances through legal text
    public void onClick(View view) {
        CheckBox cbLegal = (CheckBox) findViewById(R.id.cbLegal);
        if (!cbLegal.isChecked()){
            Toast.makeText(Legal_Register.this, "You must agree to continue!", Toast.LENGTH_SHORT).show();
        }
        if (cbLegal.isChecked()) {
            switch (view.getId()) {
                case R.id.legalconfirm1:
                    setContentView(R.layout.register_legal2);
                    findViewById(R.id.legalconfirm2).setOnClickListener(this);
                    TextView tvLegal2 = (TextView) findViewById(R.id.tvPrivacyPolicy);
                    tvLegal2.setText(Html.fromHtml(getString(R.string.PrivacyPolicy_html)));
                    break;
                case R.id.legalconfirm2:
                    Intent register = new Intent(this, Register.class);
                    register.putExtra("Email", getIntent().getStringExtra("Email"));
                    register.putExtra("Password", getIntent().getStringExtra("Password"));
                    startActivity(register);
                    break;
            }
        }
    }
    public void cancel (View view) { onBackPressed(); }
}
