package com.uu.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class UplodForm {


    public  static String processUplodForm(FileItem fileItem, String storeDirectory) {
        try {
            InputStream is = fileItem.getInputStream();

            String name = fileItem.getName();
            makeDir(storeDirectory,name);
            if(name!=null){
                name = FilenameUtils.getName(name);
            }
            name = UUID.randomUUID().toString()+"_"+name;
            File file = new File(storeDirectory,name);
            try {
                fileItem.write(file);
                return name;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  static String makeDir(String storeDirectory,String filename){

        int hashCode = filename.hashCode();
        int dir1 = hashCode&0xf0;
        int dir2 = dir1 >> 4;
        String directory = dir1 + File.separator + dir2;
        File file = new File(storeDirectory,directory);
        if(!file.exists()){
            file.mkdirs();
        }
        return storeDirectory + File.separator + directory;

    }

}
