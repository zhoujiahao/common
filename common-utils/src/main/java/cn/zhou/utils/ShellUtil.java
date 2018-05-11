package cn.zhou.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellUtil {

    private static Logger logger = LoggerFactory.getLogger(ShellUtil.class);

    public static int exec(String cmd) throws Exception {
        logger.info("***** exec cmd : " + cmd + " *****");
        Process exec = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", cmd});
        return waitForResult(exec);
    }

    public static int waitForResult(Process exec) throws Exception {
        //读取标准错误流
        BufferedReader brError = new BufferedReader(new InputStreamReader(exec.getErrorStream(), "utf8"));
        //读取标准输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        Thread t1 = new Thread() {
            public void run() {
                String line = null;
                try {
                    while ((line = brError.readLine()) != null) {
                        System.out.println("***** ERROR *****  " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (brError != null)
                            brError.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                String line = null;
                try {
                    while ((line = br.readLine()) != null) {
                        System.out.println("***** INFO  *****  " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (br != null)
                            br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        t2.start();
        return exec.waitFor();
    }

    public static int exec(String dir, String cmd) throws Exception {
        logger.info("***** In dir : " + dir + " , exec cmd : " + cmd + " ***");
        Process exec = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", cmd}, null, new File(dir));
        return waitForResult(exec);
    }
}
