package Tools.downloader;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @auther: kid1999
 * @desc:
 * @date: 2020/8/15 21:02
 **/
public class HutoolDownloader implements Downloader{
    @Override
    public boolean download(String localPath, String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        String filePath = String.valueOf(Paths.get(localPath,fileName));
        System.out.println(filePath);

        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            DataInputStream in = new DataInputStream(connection.getInputStream());
            BufferedOutputStream out = FileUtil.getOutputStream(filePath);
            IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
