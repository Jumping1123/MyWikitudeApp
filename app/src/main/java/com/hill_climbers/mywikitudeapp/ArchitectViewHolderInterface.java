package com.hill_climbers.mywikitudeapp;

import android.location.LocationListener;
import com.wikitude.architect.ArchitectView.ArchitectUrlListener;
import com.wikitude.architect.ArchitectView.SensorAccuracyChangeListener;

public interface ArchitectViewHolderInterface {
    public static interface ILocationProvider {
        public void onResume();
        public void onPause();
    }
}
