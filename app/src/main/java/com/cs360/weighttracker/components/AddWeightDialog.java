package com.cs360.weighttracker.components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cs360.weighttracker.R;

public class AddWeightDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater.
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog.
        builder.setView(inflater.inflate(R.layout.dialog_add_weight, null))
                .setPositiveButton(R.string.cancel, (dialog, id) -> {
                    // Sign in the user.
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    assert AddWeightDialog.this.getDialog() != null;
                    AddWeightDialog.this.getDialog().cancel();
                });
        return builder.create();
    }
}
