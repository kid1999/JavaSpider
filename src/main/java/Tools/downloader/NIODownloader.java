package Tools.downloader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;

/**
 * @auther: kid1999
 * @desc:
 * @date: 2020/8/15 19:13
 **/
public class NIODownloader implements Downloader {
    @Override
    public boolean download(String localPath, String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
            String filePath = String.valueOf(Paths.get(localPath,fileName));
            System.out.println(filePath);

            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            ReadableByteChannel readableByteChannel = Channels.newChannel(connection.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            System.out.println();
        }
        return true;
    }
}
