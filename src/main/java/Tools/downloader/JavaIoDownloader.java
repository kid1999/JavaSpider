package Tools.downloader;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;

/**
 * @auther: kid1999
 * @desc:
 * @date: 2020/8/15 19:12
 **/
public class JavaIoDownloader implements Downloader {

    @Override
    public boolean download(String localPath, String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
            String filePath = String.valueOf(Paths.get(localPath,fileName));
            System.out.println(filePath);

            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            DataInputStream in = new DataInputStream(connection.getInputStream());
            DataOutputStream out = new DataOutputStream(new FileOutputStream(filePath));
            byte[] buffer = new byte[4096];
            int count = 0;
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            out.close();/*后面三行为关闭输入输出流以及网络资源的固定格式*/
            in.close();
            connection.disconnect();
        } catch (Exception e) {
            System.out.println();
        }
        return true;
    }
}
