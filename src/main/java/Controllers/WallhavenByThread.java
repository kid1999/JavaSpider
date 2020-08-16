package Controllers;

import Tools.downloader.Downloader;
import Tools.downloader.DownloaderFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @desc:
 * @auther: kid1999
 * @date: 2020/8/15 18:15
 **/
public class WallhavenByThread implements Runnable {
    // 当前页码
    private int page;
    public WallhavenByThread(int page) {
        this.page = page;
    }

    // 1.从网页生成Document
    public static Document loadDocumentFromUrl(String url){
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            String title = doc.title();
            System.out.println(title);
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 下载子线程
    static class DownloadThread implements Runnable{
        String localPath;
        String fileUrl;

        public DownloadThread(String localPath, String fileUrl) {
            this.localPath = localPath;
            this.fileUrl = fileUrl;
        }

        void download(){
            Downloader downloader = DownloaderFactory.getDownloader();
            downloader.download(localPath,fileUrl);
        }

        @Override
        public void run() {
            download();
        }
    }


    // 单页爬取子线程
    @Override
    public void run() {
        try {
            Document document = Jsoup.connect("https://wallhaven.cc/toplist?page=" + page).get();
            Elements lists = document.getElementsByClass("preview");
            // 创建一个固定大小的线程池:
            ExecutorService es = Executors.newFixedThreadPool(10);
            for (Element a:lists) {
                String link = a.attr("href");
                Document doc = loadDocumentFromUrl(link);
                Element img = doc.getElementsByTag("img").get(2);
                String imgUrl = img.attr("src");
                // 下载子线程
                es.submit(new DownloadThread("E:\\迅雷下载\\Photos\\wallhaven",imgUrl));
            }
            // 关闭线程池:
            es.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // 爬取所有主线程
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        // 太快 被 ban
//        ExecutorService pool = Executors.newFixedThreadPool(10);
//        for (int i = 1; i <= 124; i++) {
//            // 单页主线程
//            pool.submit(new WallhavenByThread(i));
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        pool.shutdown();

        for (int i = 1; i <=100 ; i++) {
            new Thread(new WallhavenByThread(i)).run();
        }


        long end = System.currentTimeMillis();
        System.out.println("爬取一页用时：" + (end-start) + " ms");
    }
}
