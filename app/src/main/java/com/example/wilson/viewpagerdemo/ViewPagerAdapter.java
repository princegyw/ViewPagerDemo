package com.example.wilson.viewpagerdemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by wilson on 2015/9/6.
 * An example of ViewPagerAdapter
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private JSONArray mJSONArray; //data source

    private HashMap<Integer, ViewPagerItemView> mHashMap;

    public ViewPagerAdapter(Context context, JSONArray JSONArray) {
        mContext = context;
        mJSONArray = JSONArray;
        mHashMap = new HashMap<>();
    }

    @Override
    public int getCount() {
        return mJSONArray.length();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    //call when need to instantiate and Item, much like getView() in ListView adapter
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("gyw", "ViewPagerAdapter::instantiateItem(); position:" + position);
        ViewPagerItemView itemView;
        if (mHashMap.containsKey(position)) { //view is cached but bitmap maybe recycled
            itemView = mHashMap.get(position);
            itemView.reload();
        } else {
            itemView = new ViewPagerItemView(mContext);
            try {
                JSONObject jsonObject = mJSONArray.getJSONObject(position);
                itemView.setData(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mHashMap.put(position, itemView);
            container.addView(itemView);
        }
        return  itemView;
    }

    //call when flip away; recycle behavior is defined here
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("gyw", "ViewPagerAdapter::destroyItem(); position:" + position);
        ((ViewPagerItemView) object).recycle();
    }

}
