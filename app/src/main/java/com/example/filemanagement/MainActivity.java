package com.example.filemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AddNewFolderDialog.AddFolderCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //we always have to check if externalStorage is accessible
        File file = null;
        if (StorageHelper.isExternalStorageReadable())
            file = getExternalFilesDir(null); // path e foldere app ma ro barmigardune

        assert file != null;
        showFilesFragment(file.getPath() , false);

        View btnAddNewFolder=findViewById(R.id.btn_main_addFile);
        btnAddNewFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new AddNewFolderDialog()).show(getSupportFragmentManager() , null);
            }
        });



    }

    public void showFilesFragment(String path , boolean addToBackStack){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main_fragment , FileListFragment.newInstance(path));
        if (addToBackStack)
        {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onCreateButtonClicked(String folderName) {
        Fragment currentFragment=getSupportFragmentManager().findFragmentById(R.id.frame_main_fragment);
        if (currentFragment instanceof FileListFragment){
            ((FileListFragment) currentFragment).createNewFolder(folderName);
        }
    }
}