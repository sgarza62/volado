package com.example.sgarza621.volado;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.os.Handler;

import java.util.Random;

// TODO: use Timer instead https://docs.oracle.com/javase/8/docs/api/java/util/Timer.html

public class MainActivity extends AppCompatActivity {

    private static final int mFlipCount = 12;
    private static final int mFlipInterval = 30;
    private RelativeLayout mTap;
    private RelativeLayout mTails;
    private Handler mDurationHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTap = (RelativeLayout) findViewById(R.id.tap);
        mTails = (RelativeLayout) findViewById(R.id.tails);
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
            mDurationHandler.postDelayed(flipRunnable, mFlipInterval * (i * i));
        }

        // schedule a reset for once the last flip is complete
        mDurationHandler.postDelayed(resetFlipsRunnable, mFlipInterval * flips * flips);
    }

    ;

    protected Runnable flipRunnable = new Runnable() {
        @Override
        public void run() {
            // just hide and show the overlaying tails screen
            if (mTails.getVisibility() == View.VISIBLE) {
                mTails.setVisibility(View.GONE);
            } else {
                mTails.setVisibility(View.VISIBLE);
            }
        }
    };

    protected Runnable resetFlipsRunnable = new Runnable() {
        @Override
        public void run() {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    };
}
