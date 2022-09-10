package com.example.filemanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_item_file);
            imgType=itemView.findViewById(R.id.img_itemFile_type);
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
        }


    }

    public interface FileAdapterCallBack {
        void onFileClicked(File file);
    }

    public void addFile(File newFolder)
    {
        files.add(0 , newFolder);
        notifyItemInserted(0);
    }
}
