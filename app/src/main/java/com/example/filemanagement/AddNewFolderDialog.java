package com.example.filemanagement;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddNewFolderDialog extends DialogFragment {

    private AddFolderCallback callback;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.callback=(AddFolderCallback) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view= LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_new_folder , null , false);

        TextInputEditText etFolderName=view.findViewById(R.id.et_dialogAddFolder_folderName);
        TextInputLayout etlName=view.findViewById(R.id.etl_dialogAddFolder_folderName);
        Button btnCreate=view.findViewById(R.id.btn_dialogAddFolder_create);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etFolderName.length()>0)
                {
                    callback.onCreateButtonClicked(etFolderName.getText().toString().trim());
                    dismiss();
                }else
                    etlName.setError(getString(R.string.etlError));
            }
        });

        return builder.setView(view).create();
    }

    public interface AddFolderCallback{
        void onCreateButtonClicked(String folderName);
    }
}
