package com.hill_climbers.mywikitudeapp.plugin.zxing;

import android.os.AsyncTask;
import android.util.Log;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.wikitude.architect.plugin.Frame;

public class ZXingDecodeTask extends AsyncTask<Frame, Void, Result> {

    private final ZXingResultListener resultListener;

    public ZXingDecodeTask(ZXingResultListener resultListener) {
        this.resultListener = resultListener;
    }

    @Override
    protected Result doInBackground(Frame... params) {
        Result result = null;
        Frame cameraFrame = params[0];

        byte[] data = cameraFrame.getData();
        int height = cameraFrame.getSize().getHeight();
        int width = cameraFrame.getSize().getWidth();

        //ZXingで処理させる画像を90度回転させる：画面を縦(Portrait)で使うときに必要
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                rotatedData[x * height + height - y - 1] = data[x + y * width];
        }
        int tmp = width;
        width = height;
        height = tmp;

        PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(rotatedData,
                width, height, 0, 0, width, height, false);

        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        MultiFormatReader reader = new MultiFormatReader();
        try {
            result = reader.decode(bitmap);
        } catch (NotFoundException e) {
        }

        return result;
    }

    @Override
    protected void onPostExecute(Result result) {
        resultListener.onZxingDecodeTaskCompleted(result);
    }
}
