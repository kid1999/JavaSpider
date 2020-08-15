package Tools;

import Tools.downloader.Downloader;
import Tools.downloader.DownloaderFactory;

/**
 * @auther: kid1999
 * @desc:
 * @date: 2020/8/15 19:16
 **/
public class tools {
    public static void main(String[] args) {
        Downloader downloader = DownloaderFactory.getDownloader();
        downloader.download("C:\\Users\\kid1999\\Desktop\\test","https://w.wallhaven.cc/full/xl/wallhaven-xlq1rv.jpg");
    }
}
