package com.example.wilson.viewpagerdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
    implements ViewPager.OnPageChangeListener{

    private static final int ALBUM_NUM = 12;

    private static final int[] ALBUM_RES = {
            R.drawable.dog1, R.drawable.dog2, R.drawable.dog3, R.drawable.dog4,
            R.drawable.dog5, R.drawable.dog6, R.drawable.dog7, R.drawable.dog8,
            R.drawable.dog9, R.drawable.dog10, R.drawable.dog11, R.drawable.dog12,

    };

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private LinearLayout mLinearLayout;

    private ImageView[] indicators;

    private JSONArray mJSONArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDataSources();

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(this, mJSONArray);

        mLinearLayout = (LinearLayout) findViewById(R.id.viewGroup);
        initialSetImageIndicators();

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    private void setupDataSources() {
        mJSONArray = new JSONArray();
        for (int i = 0; i < ALBUM_NUM; ++i) {
            JSONObject object = new JSONObject();
            try {
                object.put("id", ALBUM_RES[i % ALBUM_RES.length]);
                object.put("name", "Image dog" + i);
                mJSONArray.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        setImageIndicators(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void initialSetImageIndicators() {
        indicators = new ImageView[ALBUM_NUM];
        for (int i = 0; i < ALBUM_NUM; ++i) {
            ImageView imageView = new ImageView(this);
            if (i == 0) {
                imageView.setImageResource(R.drawable.indicator_select);
            } else {
                imageView.setImageResource(R.drawable.indicator_idle);
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 5;
            lp.rightMargin = 5;
            indicators[i] = imageView;
            mLinearLayout.addView(imageView, lp);
        }
    }

    private void setImageIndicators(int pos) {
        for (int i = 0; i < ALBUM_NUM; ++i) {
            if (i == pos) {
                indicators[i].setImageResource(R.drawable.indicator_select);
            } else {
                indicators[i].setImageResource(R.drawable.indicator_idle);
            }
        }
    }
}
