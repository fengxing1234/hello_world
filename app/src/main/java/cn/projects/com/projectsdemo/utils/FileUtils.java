package cn.projects.com.projectsdemo.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fengxing on 2017/4/26.
 */

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();
    private static final int BUFFER_SIZE = 1024 * 1000;

    /**
     * 把文件夹下面的所有mp4文件改成mp41
     */
    public static void amendFileLastNameDemo(){
        String path = "/sdcard/beiyunhe/";
        String sourName = ".mp4";
        String newName = ".mp41";
        amendFileLastName(path,sourName,newName);
    }

    /**
     * 更改文件的后缀名
     * @param path
     * @param soureName
     * @param newName
     */
    public static void amendFileLastName(String path,String soureName,String newName){
        File file = new File(path);
        String[] lists = file.list();
        for(String name:lists){
            if(name.endsWith(soureName)){
                Log.d(TAG, "amendFileLastName: "+name);
                String[] split = name.split(soureName);
                File oldFile = new File(file,name);
                String newFileName = split[0] + newName;
                File newFile = new File(path,newFileName);

                FileInputStream inputStream = null;
                FileOutputStream outputStream = null;
                try {
                    inputStream = new FileInputStream(oldFile);
                    outputStream = new FileOutputStream(newFile);
                    byte[] bytes = new byte[BUFFER_SIZE];
                    int line=0;
                    while ((line = inputStream.read(bytes))!=-1){
                        outputStream.write(bytes,0,line);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(inputStream!=null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(outputStream !=null){
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                boolean delete = oldFile.delete();
                Log.d(TAG, "amendFileLastName: "+delete);
            }
        }
    }

}
