package com.example.filemanagement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class FileListFragment extends Fragment implements FilesAdapter.FileAdapterCallBack {

    private String path;

    private static File source;
    private static File destination;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        assert getArguments() != null;
        path=getArguments().getString("path").trim();
    }

    private FilesAdapter adapter;
    private RecyclerView rvFiles;


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


        File currentFile=new File(path);

        tvPath.setText(currentFile.getName().equalsIgnoreCase("files")?"External Storage":currentFile.getName().trim());

        //file list

        rvFiles=view.findViewById(R.id.rv_fragmentFileList_files);
        rvFiles.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));

        adapter=new FilesAdapter(Arrays.asList(Objects.requireNonNull(currentFile.listFiles())), this);

        rvFiles.setAdapter(adapter);



        return view;
    }


    public static FileListFragment newInstance(String path) {

        Bundle args = new Bundle();
        args.putString("path", path);
        FileListFragment fragment = new FileListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //we have  path here
    public void createNewFolder(String name){
        File newFolder=new File(path+File.separator+name);
        if (!newFolder.exists()){
            if (newFolder.mkdir()){
                adapter.addFile(newFolder);
                rvFiles.scrollToPosition(0);
            }
        }
    }

    @Override
    public void onFileClicked(File file) {
        if (file.isDirectory())
        {
            ( (MainActivity) requireActivity()).showFilesFragment(file.getPath() , true);
        }
    }

    @Override
    public void onDeleteItemClicked(File file) {
        if (FileOperationUtil.delete(file))
        {
            String name=file.getName();
            adapter.deleteFile(file);
            Toast.makeText(getContext(), name+" file/folder is deleted", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onMoveItemClicked(File file) {
        File des=getDesFolder();
        if (!des.exists()){
            if (des.mkdir())
            {
                adapter.addFile(des);
                rvFiles.scrollToPosition(0);
            }
        }
        try {
            if (FileOperationUtil.move(file , des))
            {
                adapter.deleteFile(file);
                Toast.makeText(getContext(), file.getName() + " file/folder is moved", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), ":|", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCopyItemClicked(File file) {
        File des=getDesFolder();
        if (!des.exists()){
            if (des.mkdir())
            {
                adapter.addFile(des);
                rvFiles.scrollToPosition(0);
            }
        }
        try {
            FileOperationUtil.copy(file , des);
            Toast.makeText(getContext(), file.getName() + " file/folder is copied", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getDesFolder()
    {
        return new File(requireActivity().getExternalFilesDir(null).getPath()+File.separator+"Destination");
    }
}
