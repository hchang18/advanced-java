package edu.pdx.cs410j.haeyoon.apptbook_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Readme extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.readme_layout);

    }

    public void onReturnHomeFromHelp(View view) {

        finish();

    }
}
