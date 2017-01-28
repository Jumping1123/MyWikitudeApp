package com.hill_climbers.mywikitudeapp.plugin.zxing;

import com.google.zxing.Result;

public interface ZXingResultListener {

    public void onZxingDecodeTaskCompleted(Result result);

}
