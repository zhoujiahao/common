package cn.zhou.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ShellUtil {

    private static Logger logger = LoggerFactory.getLogger(ShellUtil.class);

    private ShellUtil() {
    }

    public static int exec(String cmd) throws Exception {
        logger.info("***** exec cmd : " + cmd + " *****");
        Process exec = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", cmd});
        BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            logger.error("************* INFO ************" + line);
        }
        return exec.waitFor();
    }

    public static int exec(String dir, String cmd) throws Exception {
        logger.info("***** In dir : " + dir + " , exec cmd : " + cmd + " ***");
        Process exec = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", cmd}, null, new File(dir));
        BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            logger.error("************* INFO ************" + line);
        }
        return exec.waitFor();
    }
}
