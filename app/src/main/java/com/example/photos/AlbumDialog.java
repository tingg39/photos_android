package com.example.photos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class AlbumDialog extends DialogFragment {

    public static final String MESSAGE_KEY = "message_key";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        builder.setMessage(bundle.getString(MESSAGE_KEY))
               .setPositiveButton("OK",(dialog,id) -> { });
        return builder.create();
    }
}
