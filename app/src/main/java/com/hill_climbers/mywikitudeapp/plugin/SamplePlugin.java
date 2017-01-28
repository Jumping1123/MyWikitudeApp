package com.hill_climbers.mywikitudeapp.plugin;

import android.util.Log;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.plugin.Frame;
import com.wikitude.architect.plugin.Plugin;
import com.wikitude.architect.plugin.RecognizedTarget;

public class SamplePlugin extends Plugin {

    public SamplePlugin(String identifier, ArchitectView architectView) {
        super(identifier, architectView);
    }

    @Override
    protected void initialize() {
        Log.e(this.getClass().getName(), "Plugin.init");
    }

    @Override
    protected void pause() {
        Log.e(this.getClass().getName(), "Plugin.pause");
    }

    @Override
    protected void resume(long pausedTime) {
        Log.e(this.getClass().getName(), "Plugin.resume");
    }

    @Override
    protected void destroy() {
        Log.e(this.getClass().getName(), "Plugin.destroy");
    }

    @Override
    public void cameraFrameAvailable(final Frame cameraFrame) {
        Log.e(this.getClass().getName(), "Plugin.cameraFrameAvailable");
    }

    @Override
    public void update(RecognizedTarget[] recognizedTargets) {
        Log.e(this.getClass().getName(), "Plugin.update");
    }

}
