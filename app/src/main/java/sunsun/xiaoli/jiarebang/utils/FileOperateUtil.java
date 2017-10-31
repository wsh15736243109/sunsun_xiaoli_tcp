package sunsun.xiaoli.jiarebang.utils;

import android.os.Environment;

import com.itboye.pondteam.utils.Const;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sunsun.xiaoli.jiarebang.beans.FileBean;

/**
 * Created by Mr.w on 2017/5/9.
 */

public class FileOperateUtil {

    /**
     * 读取某个文件夹下的所有文件
     */
    public static ArrayList<FileBean> readfile(String filepath) throws FileNotFoundException, IOException {
        ArrayList<FileBean> fileList = new ArrayList<>();
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
//                ShiShiHistoryBean deviceDetailModel = new ShiShiHistoryBean();
//                deviceDetailModel.setFileName(file.getAbsolutePath());
//                deviceDetailModel.setCreate_time(TimeUtil.getParseTime(file.lastModified()));
//                fileList.add(deviceDetailModel);
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        FileBean model = new FileBean();
                        model.setImgUrl(getFileSavePath() + filelist[i]);
//                        deviceDetailModel.setCreate_time(TimeUtil.getParseTime(readfile.lastModified()));
                        fileList.add(model);
                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return fileList;
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    //	获取照片录像保存路径
    public static String getFileSavePath() {
        StringBuilder log = new StringBuilder();
        String inPath = Environment.getExternalStorageDirectory().getPath();
        String path = inPath + Const.IMAGEPATH;
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        return path;
    }

    //	获取时间
    public static String getTimesString() {
        SimpleDateFormat dateFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        String time = dateFormat24.format(Calendar.getInstance().getTime());
        time = time.replace(" ", "_");
        return time.replace(":", "-");
    }
}
