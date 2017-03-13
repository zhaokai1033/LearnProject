package com.zk.baselibrary.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ================================================
 *
 * @Describe ：
 * 判断ＳＤ卡是否挂载
 * 获取ＳＤ卡的根目录
 * 获取ＳＤ卡总容量
 * 获取ＳＤ卡剩余容量
 * 向ＳＤ卡９大共有目录保存数据
 * 向ＳＤ卡私有File目录写入数据
 * 向ＳＤ卡私有Cache目录写入数据
 * 从ＳＤ卡中读取数据
 * Created by zhaokai on 2017/3/8.
 * Email zhaokai1033@126.com
 * ================================================
 */

@SuppressWarnings("unused")
public class SDCardUtils {

    /**
     * 判断ＳＤ卡是否挂载
     */
    @SuppressWarnings("WeakerAccess")
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 返回ＳＤ卡根目录
     */
    @SuppressWarnings("WeakerAccess")
    public static String getSDCardRootDir() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取ＳＤ卡总容量
     */
    public static long getSDCardTotalSize() {
        if (isSDCardMounted()) {
            //StatFs Statistic File System  簇
            StatFs sf = new StatFs(getSDCardRootDir());
            long blockSize;
            long blockCount;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = sf.getBlockSizeLong();
                blockCount = sf.getBlockCountLong();
            } else {
                //noinspection deprecation
                blockSize = sf.getBlockSize();
                //noinspection deprecation
                blockCount = sf.getBlockCount();
            }

            return blockSize * blockCount / 1024 / 1024;  //返回ＭＢ
        }
        return 0;
    }

    /**
     * 获取ＳＤ卡可用容量
     */
    public static long getSDCardAvailableSize() {
        if (isSDCardMounted()) {
            StatFs sf = new StatFs(getSDCardRootDir());

            long availableBlockCount;
            long blockSize;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                availableBlockCount = sf.getAvailableBlocksLong();
                blockSize = sf.getBlockSizeLong();
            } else {
                //noinspection deprecation
                availableBlockCount = sf.getAvailableBlocks();
                //noinspection deprecation
                blockSize = sf.getBlockSize();
            }
            return availableBlockCount * blockSize / 1024 / 1024;
        }
        return 0;
    }

    /**
     * 向ＳＤ卡９大共有目录保存数据
     *
     * @param data     需要保存的数据
     * @param type     　区分９大共有目录的type
     * @param fileName 另存为的文件名称
     */
    public static boolean saveFileToPublicDirectory(byte[] data,
                                                    String type, String fileName) {
        if (isSDCardMounted()) {
            BufferedOutputStream bos = null;
            File fileDir =
                    Environment.getExternalStoragePublicDirectory(type);
            File file = new File(fileDir, fileName);
            if (!file.exists()) {
                try {
                    boolean ignored = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 向ＳＤ卡私有File目录写入数据
     *
     * @param context  　上下文，用来找到storage/sdcard0/Android/data/packageName/files
     * @param data     需要保存的数据
     * @param type     　文件加类型
     * @param fileName 　另存为的文件名名称
     */
    public static boolean saveFileToExternalFileDir(Context context, byte[] data,
                                                    String type, String fileName) {
        if (isSDCardMounted()) {
            BufferedOutputStream bos = null;
            File fileDir = context.getExternalFilesDir(null);
            File file = new File(fileDir, fileName);
            if (!file.exists()) {
                try {
                    boolean ignored = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 向ＳＤ卡私有Cache目录写入数据
     *
     * @param context  　上下文，用来找到storage/sdcard0/Android/data/packageName/cache
     * @param data     需要保存的数据
     * @param fileName 　另存为的文件名名称
     */
    public static boolean saveFileToExternalCacheDir(Context context, byte[] data,
                                                     String fileName) {
        if (isSDCardMounted()) {
            BufferedOutputStream bos = null;
            File fileDir = context.getExternalCacheDir();
            File file = new File(fileDir, fileName);
            if (!file.exists()) {
                try {
                    boolean ignored = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 从ＳＤ卡中读取数据
     *
     * @param fileAbsolutePath 　　读取文件的绝对路径
     */
    public static byte[] loadDataFromSDCard(String fileAbsolutePath) {
        if (isSDCardMounted()) {
            BufferedInputStream bis = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try {
                bis = new BufferedInputStream(
                        new FileInputStream(fileAbsolutePath));
                byte[] buffer = new byte[1024 * 8];
                int len;
                while ((len = bis.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                    outputStream.flush();
                }
                return outputStream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
