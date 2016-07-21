package us.codecraft.webmagic.samples;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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

/**
 * Created by xiaoshu on 16/7/19.
 */


public class WeiboTestProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(5).setSleepTime(10000);

    public Site getSite() {
//        site.addHeader("User-Agent", site_user_agent);
//        site.addHeader("Cookie", site_cookie);
        site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36");
        site.addHeader("Cookie", "SUB=_2A256lEudDeTxGeRI71MV8ibMyz6IHXVWd1XVrDV9PUJbktAKLRLXkW1a-NvUBaYs2Q2XJhDe0I5GrRPdKw..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5vuzwjw3XKAvWJS.aC6X0F5JpX5oz75NHD95QESoBpShzReh5EWs4Dqcj.i--fiK.7iKn4i--ciKLhi-8hi--fiKnfi-zfi--fiKysi-zE; _T_WM=b3523cb582476c09055034a6f2b36b78; gsid_CTandWM=4ubSf6a21mxZdZdPFV3Qob59G02; SUB=_2A256lEudDeTxGeRI71MV8ibMyz6IHXVWd1XVrDV6PUJbkdAKLWTgkW2gXk8aq7tWrEJPm4Al0ZAynXLsIw..; _T_WM=7df6f006cb5a03a7f146c7fc0bdffa73; gsid_CTandWM=4ubSf6a21mxZdZdPFV3Qob59G02; SSOLoginState=146907028; SUB=_2A256lEueDeTxGeRI71MV8ibMyz6IHXVWd1XWrDV8PUJbkNANLULwkW06np7PxsaXCoMfMp-c-ECt63T7RQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5vuzwjw3XKAvWJS.aC6X0F5JpX5oz75NHD95QESoBpShzReh5EWs4Dqcj.i--fiK.7iKn4i--ciKLhi-8hi--fiKnfi-zfi--fiKysi-zE");
//        site.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        site.addHeader("Accept-Encoding", "gzip, deflate, sdch");
//        site.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        return site;
    }


    public void process(Page page) {
        System.out.print(page.getHtml().toString());
//        System.out.println("[粉丝数\t微博正文\t转发数\t评论数\t点赞数]");
//        String temp = page.getUrl().toString();
//        String user_id = temp.split("/")[4].split("\\?")[0];
//        String page_namber = temp.split("/")[4].split("\\?")[1];
//        String fans_number = page.getHtml().xpath("//a[@href='http://weibo.cn/" + user_id + "/fans']/text()").toString();
//        List<String> text_list =page.getHtml().xpath("//div[@class='c']/div/span[@class='ctt']/text()").all();
//
//        System.out.println(user_id);
//        System.out.println(page_namber);
//        System.out.println(fans_number);
////        System.out.println(now_time);
//        for (int i = 0; i<text_list.size(); i++){
//            System.out.println(text_list.get(i).toString());
//        }
    }


    public static void main(String args[]) {
        Spider spider=Spider.create(new WeiboTestProcessor());
        spider.addUrl("http://weibo.cn/u/1320135280?page=2");
//        for (int page_index = 2; page_index <= 2; page_index ++){
//            spider.addUrl("http://weibo.cn/u/1320135280?page=" + page_index);
//        }
        spider.thread(1).run();
    }
}
