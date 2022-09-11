package com.example.filemanagement;

import android.os.Environment;

public class StorageHelper {

    public static boolean isExternalStorageWritable(){
        String state= Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable(){
        String state= Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

}
