package Tools.downloader;

/**
 * @author kid1999
 * @desc:
 * @date: 2020/8/15 18:59
 **/
public class DownloaderFactory {
    public static Downloader getDownloader(String downloadFunction){
        Downloader downloader = null;
        if(downloadFunction.equals("javaIO")){
            downloader = new JavaIoDownloader();
        }else if(downloadFunction.equals("NIO")){
            downloader = new NIODownloader();
        }else {     // 第三方
            downloader = new HutoolDownloader();
        }
        return downloader;
    }
    public static Downloader getDownloader(){
        return new HutoolDownloader();
    }
}
