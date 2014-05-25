package com.Doric.CarBook;

import android.app.Application;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Sunyao_Will on 2014/5/14.
 */
/*
 *  ��ʼ��Application
 */
public class CarBookApplication extends Application {
    private static final String TAG = "JPush";

    @Override
    public void onCreate() {
        Log.d(TAG,"[CarBookApplication] onCreate");
        super.onCreate();

        JPushInterface.setDebugMode(true); 	// ���ÿ�����־,����ʱ��ر���־
        JPushInterface.init(this);     		// ��ʼ�� JPush
    }
}
