package com.example.filemanagement;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder> {

    List<File> files;
    FileAdapterCallBack callback;

    public FilesAdapter(List<File> files , FileAdapterCallBack fileAdapterCallBack) {
        this.files = new ArrayList<>(files);
        this.callback =fileAdapterCallBack;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        holder.bind(files.get(position));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        ImageView imgType;
        View btnMore;
        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_item_file);
            imgType=itemView.findViewById(R.id.img_itemFile_type);
            btnMore=itemView.findViewById(R.id.img_itemFile_more);
        }

        public void bind(File file){
            if (file.isDirectory())
            {
                imgType.setImageResource(R.drawable.ic_folder_black_32dp);
            }else
                imgType.setImageResource(R.drawable.ic_file_black_32dp);

            tvName.setText(file.getName().trim());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onFileClicked(file);
                }
            });

            btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //creating a popup menu
                    PopupMenu popupMenu=new PopupMenu(view.getContext() , view); //mige be kodum view bechasbe
                    popupMenu.getMenuInflater().inflate(R.menu.menu_file_operations , popupMenu.getMenu());
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @SuppressLint("NonConstantResourceId")
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId())
                            {
                                case R.id.menu_delete:
                                    callback.onDeleteItemClicked(file);
                                    break;
                                case R.id.menu_copy:
                                    callback.onCopyItemClicked(file);
                                    break;
                                case R.id.menu_move:
                                    callback.onMoveItemClicked(file);
                                    break;
                            }

                            return false;
                        }
                    });

                }
            });
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFiles(List<File> files)
    {
        this.files=files;
        notifyDataSetChanged();
    }

    public List<File> search(String query , List<File> allFiles)
    {
        List<File> result=new ArrayList<>();

        for (File itemFile :
                allFiles) {
            if (itemFile.getName().trim().toLowerCase().contains(query.trim().toLowerCase())){
                result.add(itemFile);
            }
        }

        return result;
    }

    public interface FileAdapterCallBack {
        void onFileClicked(File file);
        void onDeleteItemClicked(File file);
        void onMoveItemClicked(File file);
        void onCopyItemClicked(File file);
    }

    public void addFile(File newFolder)
    {
        files.add(0 , newFolder);
        notifyItemInserted(0);
    }

    public void deleteFile(File file)
    {
        int index=files.indexOf(file);
        if (index>-1)
        {
            files.remove(index);
            notifyItemRemoved(index);
        }
    }
}
