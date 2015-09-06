package com.example.wilson.viewpagerdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final int ALBUM_NUM = 100;

    private static final int[] ALBUM_RES = {
            R.drawable.dog1, R.drawable.dog2, R.drawable.dog3, R.drawable.dog4,
            R.drawable.dog5, R.drawable.dog6, R.drawable.dog7, R.drawable.dog8,
            R.drawable.dog9, R.drawable.dog10, R.drawable.dog11, R.drawable.dog12,

    };

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    private JSONArray mJSONArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDataSources();

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(this, mJSONArray);
        mViewPager.setAdapter(mViewPagerAdapter);
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

}
