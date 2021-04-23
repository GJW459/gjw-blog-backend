package com.gjw.gjwblog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author gjw
 * 文件上传的相关工具
 */
public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);


    /**
     * 将字符串写入指定文件
     * 当指定的父路径中文件夹不存在时，会最大程度去创建，以保证保存成功
     *
     * @param res      原字符串
     * @param filePath 文件路径
     * @return 是否成功
     */
    public static boolean stringToFile(String res, String filePath) {

        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            // 新建一个File对象 获得文件的相关属性，看是否需要创建文件夹
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists()) {
                // 当文件夹不存在时 创建文件夹
                distFile.getParentFile().mkdirs();
            }
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];// 字符缓冲区
            int len;
            // 将string读入字符缓冲区 再将字符缓冲区里面的字符写入指定文件
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;//发生异常返回失败
            return flag;
        } finally {

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return flag;
        }
    }

    /**
     * File文件转String
     * 通过字节输入流读文件并将其输出为字符串
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String fileToString(String filePath) throws IOException {

        File file = new File(filePath);
        if (file.exists()) {
            byte[] data = new byte[(int) file.length()];
            boolean result;
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                int len = inputStream.read(data);
                result = len == data.length;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            if (result) {
                return new String(data);
            }
        }
        return null;
    }

    /**
     * 获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            // 当字符串不为空 并且不为空串时执行
            // 获得最后一个.的位置索引
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * java文件操作 获得不带扩展名的文件名
     *
     * @param fileName
     * @return
     */
    public static String getFileNameNoEx(String fileName) {

        if ((fileName != null) && (fileName.length() > 0)) {
            // 当字符串不为空 并且不为空串时执行
            // 获得最后一个.的位置索引
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length() - 1))) {
                return fileName.substring(0, dot);
            }
        }
        return fileName;
    }

    /**
     * 文件上传存储到本地
     *
     * @param file     上传文件
     * @param filePath 保存的路径
     * @return
     */
    public static File upload(MultipartFile file, String filePath) {

        // 文件进行上传
        // 1.获得上传的文件名和后缀名 file.getOriginalFilename()获得 one.jpeg 带后缀
        String name = FileUtils.getFileNameNoEx(file.getOriginalFilename());
        String extensionName = FileUtils.getExtensionName(file.getOriginalFilename());
        String nowStr = DateUtils.now2();// 返回20201020..
        // 设置保存的文件名
        String fileName = name + nowStr + "." + extensionName;
        // 文件保存的全路径
        String path = filePath + fileName;
        // getCanonicalFile 可解析正确各种路径
        try {
            File dest = new File(path).getCanonicalFile();
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                // 不存在就创建
                if (!dest.getParentFile().mkdirs()) {
                    System.out.println("was not successful");
                }
            }
            //文件写入
            file.transferTo(dest);
            return dest;
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return null;


    }

    public static void main(String[] args) {
        try {
            String str = FileUtils.fileToString("C:/coding/note/test.md");
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
