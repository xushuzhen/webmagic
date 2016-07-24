package us.codecraft.webmagic.samples;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.helpers.SyslogWriter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import us.codecraft.webmagic.samples.WeiboLoginTestProcessor;
/**
 * Created by xiaoshu on 16/7/19.
 */


public class WeiboTestProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(5).setSleepTime(5000);
    public static String test_cookie = "SUB=_2A256lEudDeTxGeRI71MV8ibMyz6IHXVWd1XVrDV9PUJbktAKLRLXkW1a-NvUBaYs2Q2XJhDe0I5GrRPdKw..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5vuzwjw3XKAvWJS.aC6X0F5JpX5oz75NHD95QESoBpShzReh5EWs4Dqcj.i--fiK.7iKn4i--ciKLhi-8hi--fiKnfi-zfi--fiKysi-zE; _T_WM=b3523cb582476c09055034a6f2b36b78; gsid_CTandWM=4ubSf6a21mxZdZdPFV3Qob59G02; SUB=_2A256lEudDeTxGeRI71MV8ibMyz6IHXVWd1XVrDV6PUJbkdAKLWTgkW2gXk8aq7tWrEJPm4Al0ZAynXLsIw..; _T_WM=7df6f006cb5a03a7f146c7fc0bdffa73; gsid_CTandWM=4ubSf6a21mxZdZdPFV3Qob59G02; SSOLoginState=146907028; SUB=_2A256lEueDeTxGeRI71MV8ibMyz6IHXVWd1XWrDV8PUJbkNANLULwkW06np7PxsaXCoMfMp-c-ECt63T7RQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5vuzwjw3XKAvWJS.aC6X0F5JpX5oz75NHD95QESoBpShzReh5EWs4Dqcj.i--fiK.7iKn4i--ciKLhi-8hi--fiKnfi-zfi--fiKysi-zE";
    public static String cookie = "no_cookie";
    public static String ip = "127.0.0.1";
    public static int port = 8000;

    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                ip = tempString.split("\t")[0];
                port = Integer.parseInt(tempString.split("\t")[1]);
                cookie = tempString.split("\t")[2].trim();
                System.out.println("line " + line + ": " + tempString);
                line++;
                break;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public Site getSite() {
        site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36");
        site.addHeader("Cookie", cookie);
//        site.addHeader("Cookie", test_cookie);
//        site.setHttpProxy(new HttpHost(ip,port));
        return site;
    }

    public void process(Page page) {
        String flag = "Y";
        if (cookie == "no_cookie"){
            flag = "N";
        }
        int page_number = Integer.parseInt(page.getUrl().toString().split("=")[1]);
//        if (page_number % 5 == 2){
//            flag = "N";
//        }
        System.out.println("flag = " + flag);
        if (flag == "N"){
            readFileByLines("E:\\workplace\\WeiboLogin\\Cookies.cfg");
//            System.out.println("WeiboTest" + cookie);
            spider_core(page_number,cookie);
        }
        if (cookie != "no_cookie"){
            flag = "Y";
        }
        System.out.println("flag = " + flag);
        System.out.println("WeiboTest" + ip);
        System.out.println("WeiboTest" + cookie);

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String hehe = dateFormat.format( now );
        System.out.println(hehe.toString());
        List<String> zhengwen_list = page.getHtml().xpath("//div[@class='c']/div/span[@class='ctt']/text()").all();
        for (int i = 0; i< zhengwen_list.size(); i ++){
            System.out.println(zhengwen_list.get(i).toString());
        }
    }
    public static void spider_core(int i, String ck){
        cookie = ck;
        Spider spider=Spider.create(new WeiboTestProcessor());
        for (int page_index = i; page_index <= 60; page_index ++){
            spider.addUrl("http://weibo.cn/u/1320135280?page=" + page_index);
        }
        spider.thread(1).run();
    }
    public static void main(String args[]) {
        spider_core(1, "no_cookie");
    }
}
