package com.levko.roma.levkohomework7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by User on 04.03.2016.
 */
public class SelectedView extends AppCompatActivity {
    TextView tvText;
    String sTitle, sText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_view);
        findView();
        setDataToViews();
        addSupportActionBar();
    }

    private void findView() {
        tvText = (TextView) findViewById(R.id.tv_text_ASV);
    }

    private void setDataToViews() {
        Intent intent = getIntent();
        sText = intent.getStringExtra(Constants.PUT_EXTRA_TEXT);
        sTitle = intent.getStringExtra(Constants.PUT_EXTRA_TITLE);
        tvText.setText(sTitle + "\n" + sText);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void addSupportActionBar() {

        final android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle(sTitle);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}