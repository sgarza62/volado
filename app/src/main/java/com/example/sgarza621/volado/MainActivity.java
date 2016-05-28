package com.example.sgarza621.volado;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int mFlipCount = 12;
    private static final int mFlipInterval = 30;
    private RelativeLayout mTap;
    private RelativeLayout mTails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mTap = (RelativeLayout) findViewById(R.id.tap);
        mTails = (RelativeLayout) findViewById(R.id.tails);

        if (savedInstanceState != null) {
            String currScreen = savedInstanceState.getString("currScreen", "tap");
            if (currScreen.equals("tap")) {
                mTap.setVisibility(View.VISIBLE);
                mTails.setVisibility(View.GONE);
            }

            if (currScreen.equals("tails")) {
                mTails.setVisibility(View.VISIBLE);
                mTap.setVisibility(View.GONE);
            }

            if (currScreen.equals("heads")) {
                mTap.setVisibility(View.GONE);
                mTails.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String currScreen;
        if (mTap.getVisibility() == View.VISIBLE) {
            currScreen = "tap";
        } else if (mTails.getVisibility() == View.VISIBLE) {
            currScreen = "tails";
        } else {
            currScreen = "heads";
        }

        outState.putString("currScreen", currScreen);
        super.onSaveInstanceState(outState);
    }

    public void onClickFlip(View view) {
        // block touch events
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        // hide the main tap screen
        mTap.setVisibility(View.GONE);

        // maybe do an extra flip
        int flips = mFlipCount + ((new Random().nextBoolean()) ? 1 : 0);

        // add flips to the message queue, where the delayed millis grow quadratically per flip
        for (int i = 0; i < flips; i++) {
            final int currMultiplier = i;
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // just hide and show the overlaying tails screen
                            if (mTails.getVisibility() == View.VISIBLE) {
                                mTails.setVisibility(View.GONE);
                            } else {
                                mTails.setVisibility(View.VISIBLE);
                            }
                        }
                    },
                    mFlipInterval * (currMultiplier * currMultiplier));
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                },
                mFlipInterval * flips * flips);
    }
}
