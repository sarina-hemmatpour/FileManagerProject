package com.example.filemanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperationUtil {

    public static boolean copy(File source , File destination) throws IOException {
        if (!destination.exists() || !source.exists())
        {
            return false;
        }
        if (source.isFile())
        {
            //copy file operation
            copySingleFile(source , destination);
            return true;
        }
        else if (source.isDirectory())
        {
            //copy Directory operation
            return copyDirectory(source, destination);
        }

        return false;
    }

    private static boolean copyDirectory(File source , File destination)
    {
        File[] files=source.listFiles();
        File desFolder=new File(destination.getPath() + File.separator+source.getName());

        if (desFolder.mkdir())
        {
            if (files!=null && files.length>0)
            {
                for (File itemFile : files) {
                    if (itemFile.isDirectory()){
                        copyDirectory(itemFile , desFolder);
                    }
                    else {
                        try {
                            copySingleFile(itemFile , desFolder);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        else {
            return false;
        }

        return true;

    }


    private static void copySingleFile(File source , File destination) throws IOException
    {
        File desFile=new File(destination.getPath() + File.separator+source.getName());
        FileInputStream inputStream=new FileInputStream(source);
        FileOutputStream outputStream=new FileOutputStream(desFile);

        byte[] buffer=new byte[1024];
        int length;
        while ((length=inputStream.read(buffer))>0)
        {
            outputStream.write(buffer , 0 , length);
        }

        inputStream.close();
        outputStream.close();
    }

    // Delete
    public static boolean delete(File file)
    {
        if (file.isFile())
        {
            return deleteSingleFile(file);

        }
        File[] files=file.listFiles();



        if (files!=null && files.length>0)
        {
            for (File itemFile : files) {
                delete(itemFile);
                deleteSingleFile(itemFile);
            }
        }

        return deleteSingleFile(file);
    }

    public static boolean deleteSingleFile(File file){
        return file.delete();
    }

    public static boolean move(File source , File destination) throws IOException
    {
        if (!source.getPath().equals(destination.getPath()))
        {
            copy(source, destination);
            delete(source);
            return true;
        }
        return false;
    }
}
