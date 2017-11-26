package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties getProperty(String fileName) {
        Properties properties = new Properties();
        String path = "config/" + fileName;
        FileInputStream fileInputStream=null;
        try {
            fileInputStream=new FileInputStream(path);
            InputStreamReader is = new InputStreamReader(fileInputStream, "UTF-8");
            properties.load(is);
            return properties;
        } catch (Exception e) {
            throw new RuntimeException(  path + " is not exist.");
        }finally{
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
