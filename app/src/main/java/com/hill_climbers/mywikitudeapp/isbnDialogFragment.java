package com.hill_climbers.mywikitudeapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class isbnDialogFragment extends DialogFragment {
    public static final String ARG_TITLE = "title";
    public static final String ARG_MESSAGE = "message";

    private int mTitle;
    private String mMessage;

    public static isbnDialogFragment newInstance(int title, String message) {
        isbnDialogFragment fragment = new isbnDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mTitle = getArguments().getInt(ARG_TITLE);
            mMessage = getArguments().getString(ARG_MESSAGE);
//            setShowsDialog(true);
        }
        return new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // do nothing
                                setShowsDialog(false);
                            }
                        }
                )
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // ここで詳細画面へ遷移
                                // いろいろここに書く。
                                final Intent poiDetailIntent = new Intent(getActivity(), SamplePoiDetailActivity.class);
                                poiDetailIntent.putExtra(SamplePoiDetailActivity.EXTRAS_KEY_POI_TITILE, "本の詳細" );
                                poiDetailIntent.putExtra(SamplePoiDetailActivity.EXTRAS_KEY_POI_URL, "https://www.amazon.co.jp" );
                                getActivity().startActivity(poiDetailIntent);
                                setShowsDialog(false);
                            }
                        }
                )
                .create();
    }

}