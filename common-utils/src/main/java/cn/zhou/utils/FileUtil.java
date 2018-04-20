package cn.zhou.utils;

import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static void main(String[] args) {
        //System.out.println(getMd5ByFile(new File("C:\\Users\\seekcy\\Desktop\\buildjudge_t101_all (8).zip")));
       // System.out.println(getMd5ByFile(new File("C:\\Users\\seekcy\\Desktop\\indoorlocate_t301_200027.zip")));
        //System.out.println(getMd5ByFile(new File("C:\\Users\\seekcy\\Desktop\\indoorlocate_t301_200002.zip")));
       // appendWriteFile("C:\\Users\\seekcy\\Desktop\\text.txt","\r\n qwr");
        //readTxtFile("C:\\Users\\seekcy\\Desktop\\text.txt");
      /*  try {
            InputStream in = null;
            File f = new File("C:\\Users\\seekcy\\Desktop\\text.txt");
            
            byte[] b = new byte[55];
            
            int i = 0;
            in = new FileInputStream(f);
            while ((i = in.read(b)) != -1) {
                String str = new String(b,"GBK");
                System.out.print(str);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        
        System.out.println(getAllFileName("C:\\Users\\seekcy\\Desktop\\信号覆盖工具"));
    }

    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static void readTxtFile(String filePath){
        try {
                String encoding="UTF-8";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        System.out.println(lineTxt);//打印到控制台
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
     
    }
    
    public static void appendWriteFile(String fullPathFileName, String content){
        FileWriter writer = null;  
        try {     
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
            writer = new FileWriter(fullPathFileName, true);     
            writer.write(content);       
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
    }
    
    public static byte[] readAsByte(File img) {
        if (img.exists() && img.isFile()) {
            int len = (int) img.length();
            byte[] data = new byte[len];
            InputStream is = null;
            try {
                is = new FileInputStream(img);
                is.read(data);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return data;
        }
        return null;
    }
    
    public static void appendWriteFile1(String file, String conent) {     
        BufferedWriter out = null;     
        try {     
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));     
            out.write(conent);     
        } catch (Exception e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(out != null){  
                    out.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }     
    }     
    
    public static byte[] readAsByteArray(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[] ret = FileUtil.readAsByteArray(in);
        in.close();
        return ret;
    }

    public static byte[] readAsByteArray(InputStream inStream) throws IOException {
        int size = 1024;
        byte[] ba = new byte[size];
        int readSoFar = 0;

        while (true) {
            int nRead = inStream.read(ba, readSoFar, size - readSoFar);
            if (nRead == -1) {
                break;
            }
            readSoFar += nRead;
            if (readSoFar == size) {
                int newSize = size * 2;
                byte[] newBa = new byte[newSize];
                System.arraycopy(ba, 0, newBa, 0, size);
                ba = newBa;
                size = newSize;
            }
        }

        byte[] newBa = new byte[readSoFar];
        System.arraycopy(ba, 0, newBa, 0, readSoFar);
        return newBa;
    }

    /**
     * getDir 2017年2月21日 上午9:13:33
     * 
     * @param strPath
     * TODO：获取一个文件夹下的所有子文件夹
     */
    public static List<String> getDir(String strPath) {
        File file = new File(strPath);
        List<String> directoryList = new ArrayList<>();
        if (file.isDirectory()) {
            File[] fList = file.listFiles();
            if(fList != null){
	            for (int j = 0; j < fList.length; j++) {
	                if (fList[j].isDirectory())
	                    directoryList.add(fList[j].getName());
	                // getDir(fList[j].getPath()); //获取所有文件
	                // System.out.println(fList[j].getName());
	            }
            }
        }
        return directoryList;
    }

    /*
     ** 
     * 复制单个文件
     * 
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * 
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * 
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 复制整个文件夹内容
     * 
     * @param oldPath
     *            String 原文件路径 如：c:/fqf
     * @param newPath
     *            String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            if(file == null){
            	return;
            }
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
    }

    public static String getFileNameNoSuffix(String fileFullName) {
        String fileName = null;
        if (StringUtil.notEmpty(fileFullName)) {
            return fileFullName.substring(0, fileFullName.indexOf("."));
        }
        return fileName;
    }

    public static String getFileNameByFullPath(String fileFullPath) {
        String fileName = null;
        if (StringUtil.notEmpty(fileFullPath)) {
            int index = fileFullPath.lastIndexOf(File.separator);
            if (index >= 0 && index < (fileFullPath.length() - 1)) {
                return fileFullPath.substring(index + 1);
            }
        }
        return fileName;
    }

    public static boolean writeToFile(String fullFileName, Byte[] bytes) {
        if (StringUtil.isEmpty(fullFileName) || bytes == null) {
            return false;
        }
        byte[] bs = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bs[i] = bytes[i];
        }
        return writeToFile(fullFileName, bs);
    }

    public static boolean writeToFile(String fullFileName, byte[] bytes) {
        if (StringUtil.isEmpty(fullFileName) || bytes == null) {
            return false;
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(new File(fullFileName)));
            bos.write(bytes);
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
        return false;
    }

    public static boolean writeToFile(String fullFileName, String content) {
        if (StringUtil.isEmpty(fullFileName)) {
            return false;
        }
        if (content == null) {
            content = "";
        }

        FileWriter fw = null;

        try {
            fw = new FileWriter(new File(fullFileName));
            fw.write(content);
            fw.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Exception: new FileWriter(new File(" + fullFileName + ")");
            return false;
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void generateFolder(String path) {
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
    }

    /**
     * 删除空目录
     * 
     * @param dir
     *            将要删除的目录路径
     */
    public static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * 
     * @param dir
     *            将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     *         deletion fails, the method stops attempting to delete and returns
     *         "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                String[] children = dir.list();
                if(children != null){
	                // 递归删除目录中的子目录下
	                for (int i = 0; i < children.length; i++) {
	                    boolean success = deleteDir(new File(dir, children[i]));
	                    if (!success) {
	                        return false;
	                    }
	                }
                }
            }
            // 目录此时为空，可以删除
            return dir.delete();
        }
        return true;
    }

    public static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
            value = StringUtil.leftPad(value, 32, '0');
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static String readToJsonStrFromFile(String filePath) {
        StringBuffer buffer = new StringBuffer();
        InputStream is;
        try {
            is = new FileInputStream(filePath);
            String line; // 用来保存每行读取的内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine(); // 读取第一行
            while (line != null) { // 如果 line 为空说明读完了
                buffer.append(line); // 将读到的内容添加到 buffer 中
                line = reader.readLine(); // 读取下一行
            }
            reader.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }
    
    public static List<String> readToJsonStrListFromFile(String filePath) {
        List<String> strList = new ArrayList<>();
        InputStream is;
        try {
            is = new FileInputStream(filePath);
            String line; // 用来保存每行读取的内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine(); // 读取第一行
            while (line != null) { // 如果 line 为空说明读完了
                strList.add(line); // 将读到的内容添加到 buffer 中
                line = reader.readLine(); // 读取下一行
            }
            reader.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }

    public static String getExt(String headerField) {
        if(StringUtil.notEmpty(headerField)){
            headerField = headerField.replace("\"", "");
            int index = headerField.lastIndexOf(".");
            if(index >= 0){
                return headerField.substring(index);
            }
        }
        return null;
    }

    public static List<String> getAllFileName(String folderPath) {
        List<String> fileNameList = new ArrayList<>();
        File f = new File(folderPath);
        if (!f.exists()) {
             logger.info(folderPath + " not exists");
             return null;
         }
         
         File fa[] = f.listFiles();
         if(fa != null){
	         for (int i = 0; i < fa.length; i++) {
	              File fs = fa[i];
	              if (fs.isDirectory()) {
	                  logger.info(fs.getName() + "[目录]");
	              } else {
	                  logger.info(fs.getName());
	                  if(fs.getName() != null){
	                      String fileName = fs.getName().substring(0, fs.getName().lastIndexOf("."));
	                      fileNameList.add(fileName);
	                  }
	              }
	         }
         }
         return fileNameList;
    }

    public static String readFileContent(String file){
        File f = new File(file);
        if(!f.exists() || !f.isFile()){
            return null;
        }

        StringBuffer bf = new StringBuffer();
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String line = null;
            while ((line = br.readLine()) != null){
                bf.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bf.toString();
    }

    public static List<String> readFileLines(File file) {
        List<String> result = new ArrayList();
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str;
            while((str = bufferedReader.readLine()) != null) {
                if (StringUtil.notEmpty(str)) {
                    result.add(str);
                }
            }
        } catch (IOException var17) {
            var17.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException var16) {
                    var16.printStackTrace();
                }
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }

        }

        return result;
    }
}
