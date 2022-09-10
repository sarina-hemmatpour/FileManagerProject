package com.example.filemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // path e foldere app ma ro barmigardune
        File file=getExternalFilesDir(null);

        showFilesFragment(file.getPath() , false);



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

}