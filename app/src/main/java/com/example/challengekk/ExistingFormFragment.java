package com.example.challengekk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExistingFormFragment extends AppCompatDialogFragment {
    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_existing_form, null);
        builder.setView(view);
//        builder.setTitle("Событие с такой темой уже существует")
//                .setMessage("Тут должна быть подробная инфа о событии")
//                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // Закрываем окно
//                        dialog.cancel();
//                    }
//                });
        return builder.create();
    }
}
