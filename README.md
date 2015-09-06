### ViewPager
ListView enables us to populate and show thousands of datum within it and it is a a vertical flip up and down. However, many of time, we need to flip horizontally. For example, we may want to make an album to show images and which enables us to flip images one by one.

#### 1. ViewPager
ViewPager is defined in Android android.support.v4 library. One can layout with a ViewPager as below.
```xml
<?xml version="1.0" encoding="utf-8"?>  
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    android:layout_width="fill_parent"  
    android:layout_height="fill_parent"  
    android:orientation="vertical" >  
  
    <android.support.v4.view.ViewPager  
        android:id="@+id/viewpager"  
        android:layout_width="fill_parent"  
        android:layout_height="fill_parent"  
         />  
</LinearLayout>  
```

#### 2. ViewPagerAdapter
ViewPagerAdapter is the key class which we used to populate Item views of ViewPager. we define a ViewPagerAdapter class by extending PagerAdapter. The minimum required methods which we to override are:
```java
instantiateItem(ViewGroup, int)
destroyItem(ViewGroup, int, Object)
getCount()
isViewFromObject(View, Object)
```

##### instantiateItem(ViewGroup, int)
Get called when need to instantiate and Item, much like getView() in ListView adapter.
Besides returning the Item view, you should add the Item view as child view to the param ViewGroup.

##### destroyItem(ViewGroup, int, Object)
Get called when flip away; recycle behavior is defined here.

##### getCount()
Return the number of the datum.

##### An example of ViewPagerAdapter
```java
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
```
#### 3. MainActivity
```java
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
```

>**Tips:** Even though we recycled the bitmap objects both in Java layer and C layer. Hoever, GC won't be sure to execute promptly thus you still may receive **OutOfMemory(OOM)** error. To avoid this, a lazy way is to declare to use android:largeHeap="true" in your application in AndroidManifest.xml  
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.wilson.viewpagerdemo" >
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme"
        android:largeHeap="true">
        <activity
            android:name=".MainActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```      

>**Tips:** Cache Strategy of ViewPager is to cache three views in total. Android will instantiate the next Item view and keep the previous Item view.  
![cache log](https://lh3.googleusercontent.com/-phu3l9sA4CM/Veu6gqzv5VI/AAAAAAAAA6I/zAipbswngBM/s520-Ic42/adapter_cache.png)

#### 5. Source Code
[https://github.com/princegyw/ViewPagerDemo](https://github.com/princegyw/ViewPagerDemo)

 
