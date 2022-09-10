package com.example.filemanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FileListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_file_list , container , false);

        RecyclerView rvFiles=view.findViewById(R.id.rv_fragmentFileList_files);
        rvFiles.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));
        rvFiles.setAdapter(new FilesAdapter());

        return view;
    }


}
