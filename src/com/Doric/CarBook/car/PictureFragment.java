package com.Doric.CarBook.car;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.Doric.CarBook.R;
import com.Doric.CarBook.Static;
import com.Doric.CarBook.utility.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunyao_Will on 2014/4/3.
 */
public class PictureFragment extends Fragment {


    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.car_picture, container, false);
        return  rootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

class ImageLoader {

    /**
     * ͼƬ���漼���ĺ����࣬���ڻ����������غõ�ͼƬ���ڳ����ڴ�ﵽ�趨ֵʱ�Ὣ�������ʹ�õ�ͼƬ�Ƴ�����
     */
    private static LruCache<String, Bitmap> mMemoryCache;

    /**
     * ImageLoader��ʵ����
     */
    private static ImageLoader mImageLoader;

    private ImageLoader() {
        // ��ȡӦ�ó����������ڴ�
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        // ����ͼƬ�����СΪ�����������ڴ��1/8
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    /**
     * ��ȡImageLoader��ʵ����
     *
     * @return ImageLoader��ʵ����
     */
    public static ImageLoader getInstance() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader();
        }
        return mImageLoader;
    }

    /**
     * ��һ��ͼƬ�洢��LruCache�С�
     *
     * @param key
     *            LruCache�ļ������ﴫ��ͼƬ��URL��ַ��
     * @param bitmap
     *            LruCache�ļ������ﴫ������������ص�Bitmap����
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * ��LruCache�л�ȡһ��ͼƬ����������ھͷ���null��
     *
     * @param key
     *            LruCache�ļ������ﴫ��ͼƬ��URL��ַ��
     * @return ��Ӧ�������Bitmap���󣬻���null��
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth) {
        // ԴͼƬ�Ŀ��
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            // �����ʵ�ʿ�Ⱥ�Ŀ���ȵı���
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(String pathName,
                                                         int reqWidth) {
        // ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        // �������涨��ķ�������inSampleSizeֵ
        options.inSampleSize = calculateInSampleSize(options, reqWidth);
        // ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

}