package com.example.wilson.viewpagerdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wilson on 2015/9/6.
 */
public class ViewPagerItemView extends FrameLayout {

    private ImageView mImageView;
    private TextView mNameTextView;

    private Bitmap mBitmap; //bitmap of the image
    private JSONObject mJSONObject;

    public ViewPagerItemView(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.viewpager_itemview, null);
        mImageView = (ImageView) view.findViewById(R.id.imageView);
        mNameTextView = (TextView) view.findViewById(R.id.textView_name);
        addView(view);
    }

    public void recycle() {
        mImageView.setImageBitmap(null);

        if (mBitmap == null || mBitmap.isRecycled()) {
            return;
        }

        mBitmap.recycle();
        mBitmap = null;
    }

    public void setData(JSONObject jsonObject) {
        mJSONObject = jsonObject;
        try {
            int resId = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            mBitmap = BitmapFactory.decodeResource(getResources(), resId);
            mImageView.setImageBitmap(mBitmap);
            mNameTextView.setText(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void reload() { //bitmap maybe recycled
        try {
            int resId = mJSONObject.getInt("id");
            mBitmap = BitmapFactory.decodeResource(getResources(), resId);
            mImageView.setImageBitmap(mBitmap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
