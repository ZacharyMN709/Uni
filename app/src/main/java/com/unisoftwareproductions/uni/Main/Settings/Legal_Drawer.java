package com.unisoftwareproductions.uni.Main.Settings;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.unisoftwareproductions.uni.R;

/**
 * Created by Zachary on 10-Jul-16.
 */
public class Legal_Drawer extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legal_legal1);
        findViewById(R.id.legalconfirm1).setOnClickListener(this);
        TextView tvLegal1 = (TextView) findViewById(R.id.tvTermsofUse);
        tvLegal1.setText(Html.fromHtml(getString(R.string.TermsofUse_html)));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_legal);
}

    // Advances through legal text
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.legalconfirm1:
                setContentView(R.layout.legal_legal2);
                findViewById(R.id.legalconfirm2).setOnClickListener(this);
                TextView tvLegal2 = (TextView) findViewById(R.id.tvPrivacyPolicy);
                tvLegal2.setText(Html.fromHtml(getString(R.string.PrivacyPolicy_html)));
                break;
            case R.id.legalconfirm2:
                setContentView(R.layout.legal_legal3);
                findViewById(R.id.legalconfirm3).setOnClickListener(this);
                TextView tvLegal3 = (TextView) findViewById(R.id.tvProhibitedItems);
                tvLegal3.setText(Html.fromHtml(getString(R.string.ProhibitedPosting_html)));
                break;
            case R.id.legalconfirm3:
                setContentView(R.layout.legal_legal4);
                findViewById(R.id.legalconfirm4).setOnClickListener(this);
                TextView tvLegal4 = (TextView) findViewById(R.id.tvHosuingPolicy);
                tvLegal4.setText(Html.fromHtml(getString(R.string.EqualHousing_html)));
                break;
            case R.id.legalconfirm4:
                onBackPressed();
                break;
        }
    }

    public void cancel (View view) {
        onBackPressed();
    }

}
