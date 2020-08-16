package Controllers;

import Tools.downloader.Downloader;
import Tools.downloader.DownloaderFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @desc:
 * @auther: kid1999
 * @date: 2020/8/15 18:15
 **/
public class Wallhaven {

    // 1.从网页生成Document
    public static Document loadDocumentFromUrl(int page){
        Document doc;
        try {
            doc = Jsoup.connect("https://wallhaven.cc/toplist?page=" + page).get();
            String title = doc.title();
            System.out.println(title);
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
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

    // 2.获取Element及节点属性、文本、html
    public static void handleElement(Document document){
        Elements lists = document.getElementsByClass("preview");
        Downloader downloader = DownloaderFactory.getDownloader();
        for (Element a:lists) {
            String link = a.attr("href");
            Document doc = loadDocumentFromUrl(link);
            Element img = doc.getElementsByTag("img").get(2);
            String imgUrl = img.attr("src");
            downloader.download("C:\\Users\\kid1999\\Desktop\\test",imgUrl);
        }
    }





    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Document document = loadDocumentFromUrl(1);
        handleElement(document);
        long end = System.currentTimeMillis();
        System.out.println("爬取一页用时：" + (end-start) + " ms");
    }
}
