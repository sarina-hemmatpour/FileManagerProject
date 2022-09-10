package com.example.filemanagement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class FileListFragment extends Fragment {

    private String path;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        assert getArguments() != null;
        path=getArguments().getString("path").trim();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_file_list , container , false);

        TextView tvPath=view.findViewById(R.id.tv_fragmentFileList_path);
        View imgBack= view.findViewById(R.id.img_fragmentFileList_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).onBackPressed();
            }
        });

        tvPath.setText(path.trim());

        File currentFile=new File(path);

        //file list

        RecyclerView rvFiles=view.findViewById(R.id.rv_fragmentFileList_files);
        rvFiles.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));
        rvFiles.setAdapter(new FilesAdapter(Arrays.asList(Objects.requireNonNull(currentFile.listFiles())),
                new FilesAdapter.FileAdapterCallBack() {
                    @Override
                    public void onFileClicked(File file) {
                        if (file.isDirectory())
                        {
                            ( (MainActivity) requireActivity()).showFilesFragment(file.getPath() , true);
                        }
                    }
                }));



        return view;
    }


    public static FileListFragment newInstance(String path) {

        Bundle args = new Bundle();
        args.putString("path", path);
        FileListFragment fragment = new FileListFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
