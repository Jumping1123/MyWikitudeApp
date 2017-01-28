package com.hill_climbers.mywikitudeapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.hill_climbers.mywikitudeapp.plugin.SamplePlugin;
import com.hill_climbers.mywikitudeapp.plugin.zxing.SampleZXingPlugin;
import com.hill_climbers.mywikitudeapp.plugin.zxing.ZXingResultListener;


public class BarcodeScanActivity extends MainActivity implements ZXingResultListener {
    private static final String TAG = BarcodeScanActivity.class.getSimpleName();

    // Barcode
    private SampleZXingPlugin zXingPlugin;
    private SamplePlugin samplePlugin;
    private Toast messageToast;
    DialogFragment mDialogFragment;

    //(4) onPostCreateメソッド: onCreateの後で呼ばれる
    @Override
    protected void onPostCreate( final Bundle savedInstanceState ) {
        super.onPostCreate(savedInstanceState);

        if ( this.architectView != null ) {
            //バーコード読み取り用プラグインの登録
            this.zXingPlugin = new SampleZXingPlugin("com.plugin.zxing", architectView, this);
            boolean temp = architectView.registerPlugin(zXingPlugin);
//            Log.d(TAG, "registerPlugin: " + temp);
        }
    }

    //バーコード読み取り
    @Override
    public void onZxingDecodeTaskCompleted(Result result) {
//        Log.d(TAG, "onZxingDecodeTaskCompleted: " + result);
        if (result != null) {
//            if (messageToast != null) {
//                messageToast.setText(result.getText());
//            } else {
//                messageToast = Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT);
//            }
//            messageToast.show();

            if (mDialogFragment == null) {
                mDialogFragment = isbnDialogFragment.newInstance(R.string.isbn_dialog_title, "ISBN: " + result.getText());
                mDialogFragment.show(getFragmentManager(), "dialog");
            } else {
                if(!mDialogFragment.getShowsDialog()){
                    mDialogFragment = null;
                }
            }
        }
    }
}