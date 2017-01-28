package com.hill_climbers.mywikitudeapp.plugin.zxing;

import android.util.Log;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.plugin.Frame;
import com.wikitude.architect.plugin.Plugin;
import com.wikitude.architect.plugin.RecognizedTarget;

public class SampleZXingPlugin extends Plugin {

    private final ZXingResultListener resultListener;

    public SampleZXingPlugin(String identifier, ArchitectView architectView, ZXingResultListener resultListener) {
        super(identifier, architectView);
        this.resultListener = resultListener;
    }

    @Override
    public void cameraFrameAvailable(Frame cameraFrame) {
//        Log.e(this.getClass().getName(), "Plugin.cameraFrameAvailable");
        new ZXingDecodeTask(resultListener).execute(cameraFrame);
    }

    @Override
    public void update(RecognizedTarget[] recognizedTargets) {
        if (recognizedTargets != null) {
//            Log.e(this.getClass().getName(), "Plugin.update");
        }
    }

}