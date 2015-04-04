package com.mkpazon.foodnearby;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * Created by mkpazon on 4/4/15.
 * -=Bitbitbitbit=-
 */
public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private Button btnFindNearby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
    }

    private void initializeViews() {
        btnFindNearby = (Button) findViewById(R.id.button_findNearby);
        btnFindNearby.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnFindNearby) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
    }
}
