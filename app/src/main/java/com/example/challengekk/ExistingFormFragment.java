package com.example.challengekk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExistingFormFragment extends AppCompatDialogFragment {
    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_existing_form, null);

        Bundle bundle = getArguments();
        String theme = bundle.getString("theme");
        String event = bundle.getString("event");

        TextView themeFragment = (TextView) view.findViewById(R.id.themeFragmentTextView);
        TextView eventFragment = (TextView) view.findViewById(R.id.eventFragmentTextView);
        themeFragment.setText(theme);
        eventFragment.setText(event);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
//
        return builder.create();
    }
}
