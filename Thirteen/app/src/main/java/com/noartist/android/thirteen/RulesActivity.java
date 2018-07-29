package com.noartist.android.thirteen;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.rulesToolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        String buttonClicked = getIntent().getStringExtra("EXTRA_BUTTON");

        //Test code for working with strings
        TextView rules = (TextView) findViewById(R.id.tvRules);
        String rulesString = null;
        switch(buttonClicked){
            case "rules":
                setTitle(getString(R.string.title_activity_rules));
                rulesString = getString(R.string.rules);
                break;
            case "help":
                setTitle(getString(R.string.app_help));
                rulesString = getString(R.string.help);
                break;
            default:
                break;
        }
        CharSequence rulesChar = Html.fromHtml(rulesString);
        rules.setText(rulesChar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.toolbar_menu_rules:
                return true;
            case R.id.toolbar_menu_help:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
