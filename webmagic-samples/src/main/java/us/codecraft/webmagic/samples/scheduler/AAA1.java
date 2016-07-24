package us.codecraft.webmagic.samples;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
//import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
//import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
//import us.codecraft.webmagic.selector.Html;
//import us.codecraft.webmagic.util.BeanFactorry;
import us.codecraft.webmagic.utils.HttpConstant;
/**new
 * spider 测试类
 * @author luoyunfei
 *
 */
public class AAA1 implements PageProcessor {

    //请求设置用参数
    private static Site site = Site.me().setRetryTimes(3).setSleepTime(7000);

    public static byte[] getimage(HttpClient http, String url){
        try{HttpGet hg=new HttpGet(url);
            HttpResponse hr=http.execute(hg);
            HttpEntity he=hr.getEntity();
            if(he!=null){
                InputStream is=he.getContent();
                return IOUtils.toByteArray(is);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void download_picture(String codeImgUrl){
        try {
            HttpClient http = new DefaultHttpClient();
            byte[] imgDownload = getimage(http, codeImgUrl);
            IOUtils.write(imgDownload, new FileOutputStream("E://code.jpg"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Request regroupRequest(String post,String url){
        Request request=new Request();
        request.setMethod(HttpConstant.Method.POST);
        //String posta=URLDecoder.decode(post);
        String postParam=post;
        if(!"".equals(postParam) && postParam !=null){
            String [] str=postParam.split("&");
            int size=str.length;
            if(size>0){
                NameValuePair [] headerDict = new NameValuePair[size];
                for(int i=0 ; i< size ;i++){
                    String [] param=str[i].split("=");
                    if(param.length==2){
                        headerDict[i]=new BasicNameValuePair(param[0], param[1]);

                    }else {
                        headerDict[i]=new BasicNameValuePair(param[0], "");
                    }
                }

                request.putExtra("nameValuePair", headerDict);
                request.setUrl(url);
            }
        }
        return request;
    }

    /**
     * 爬虫入口   后期需做处理
     * @param args
     */
    public static void main(String[] args) {
		Spider spider=Spider.create(new AAA1());
//		spider.thread(1).runAsync();

        Request re=regroupRequest("key=123&v=456","http://login.weibo.cn/login/");
//		spider.addUrl("http://d.weibo.com/1087030002_2975_1003_0?page=3#Pl_Core_F4RightUserList__4");
        spider.addRequest(re);
        spider.thread(1).run();
    }

    /**
     *
     * 爬虫具体处理类
     */
    public void process(Page page) {
//        System.out.println(page.getHtml().xpath("//div[@class='c']/div[1]/span[@class='ctt']/text()").all().size());
        System.out.println(page.getHtml().toString());
        String userId = "15210631912";
        String passWord = "xsz6134597";
        String passwordName = page.getHtml().xpath("//input[@type='password']/@name").toString();
        String submit = page.getHtml().xpath("//input[@name='submit']/@value").toString();
        String remember = "on";
        String backUrl = page.getHtml().xpath("//input[@name='backurl']/@value").toString();
        String backTitle = page.getHtml().xpath("//input[@name='backtitle']/@value").toString();
        String vk = page.getHtml().xpath("//input[@name='vk']/@value").toString();
        String capId = page.getHtml().xpath("//input[@name='capid']/@value").toString();
        String codeImgUrl = page.getHtml().xpath("//img/@src").toString();
//                String randPartUrl = page.getHtml().xpath("//form/@action").toString();
        download_picture(codeImgUrl);
        System.out.println("[CheckCode Downloaded] : " + codeImgUrl);
        System.out.println("Input code:");
        Scanner s = new Scanner(System.in);
        String codeInput = s.next();
//        Map requestDic = new HashMap();
//        requestDic.put("mobile", userId);
//        requestDic.put(passwordName, passWord);
//        requestDic.put("code", codeInput);
//        requestDic.put("remember", remember);
//        requestDic.put("backUrl", backUrl);
//        requestDic.put("backTitle", backTitle);
//        requestDic.put("vk", vk);
//        requestDic.put("capId", capId);
//        requestDic.put("submit", submit);
        String outstr = "mobile="+userId+"&"+passwordName+"="+passWord+"&code"+codeInput+"&remember="+remember+"&backUrl="+backUrl+"&backTitle="+backTitle+"&vk="+vk+"&capId="+capId+"&submit="+submit;
        System.out.println(outstr);
        Spider spider2=Spider.create(new AAA1());
        Request re2=regroupRequest(outstr,"http://login.weibo.cn/login/");
        spider2.addRequest(re2);
        spider2.thread(1).run();
//		System.out.println(page.getStatusCode());

    }

    public Site getSite() {
//		site.addHeader("Host", "d.weibo.com");
        site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
//		site.addHeader("Accept-Encoding","gzip, deflate");
//		site.addHeader("Accept"," text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		site.addHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//		site.addHeader("Referer", "http://hotels.ctrip.com/hotel/895232.html?isFull=F");
//		site.addHeader("X-Requested-With", "XMLHttpRequest");
//		site.addCookie("Cookie", " _T_WM=5fa864c89b607f776902233500b244c1; ALF=1470276961; SCF=AoIr4Pf5XbJrgupZfJ0U_doJLRdm-_nzf0yBOWM_h-GrQWyC_Q3VbOuuM2wKJ3dlssVRhKX9lcFfMSYgwAQxwTs.; SUHB=0h15DxKWa_eUSD; SSOLoginState=1467684984; SUB=_2A256f2goDeTxGeNI41oT8izFzTiIHXVZgAhgrDV6PUJbktBeLVnWkW2C2ixgsDeUIUJXBMpLj7uR0dcrGQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFhpsdKuNalZdGpdQ9uMYml5JpX5o2p5NHD95QfSonReozE1KqXWs4DqcjIMNH79PiSqgRLxKBLBonLBoqLxK.L1-zL1Kzt; gsid_CTandWM=4uYACpOz5eqiduh8ZqojjnRLwfy");
        //site.addHeader("Cookie", "CNZZDATA1256409906=975967535-1464769640-%7C1465872942; uvCode=FC58DA15-5648-46FD-BFBD-E2695CF3DD08; pgv_pvi=4096296960; JSESSIONID=4892E01D0D481A8EFECE290933D8D5C9");
//		site.addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
//		site.addHeader("Referer", "http://d.weibo.com/1087030002_2975_1003_0");
//		site.addHeader("Connection","keep-alive");
//		site.addHeader("If-Modified-Since","Thu, 01 Jan 1970 00:00:00 GMT");
//    	site.addCookie("UOR", "tvs.youku.com,widget.weibo.com,y.qq.com");
//    	site.addCookie("JSESSIONID", "4892E01D0D481A8EFECE290933D8D5C9");
//    	site.addCookie("SINAGLOBAL", "9062899665993.047.1463968337305");

        return site;
    }

    public static void setSite(String str){
        for (String hreader : str.split("\r\n")) {
            String []ss=hreader.split(": ");
            if(ss[0].indexOf("Cookie")>-1){
                site.addCookie(ss[0], ss[1]);
                System.out.println("0:"+ss[0]);
                System.out.println("1:"+ss[1]);
            }else {
                site.addHeader(ss[0], ss[1]);
                System.out.println("0:"+ss[0]);
                System.out.println("1:"+ss[1]);
            }
        }
    }
}
