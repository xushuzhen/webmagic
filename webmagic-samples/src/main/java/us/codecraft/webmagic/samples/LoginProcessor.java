package us.codecraft.webmagic.samples;

import org.apache.commons.io.IOUtils;
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
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author code4crafter@gmail.com <br>
 * @since 0.3.2
 */
public class LoginProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(7000);
    public static String flag = "true";
    public static String cookie = "no cookie";
    public static String url = "http://login.weibo.cn/login/";
    public static String post_data = "no data";
    public static Page now_page;
    public static NameValuePair []headerDict = new NameValuePair[9];

    public static byte[] getimage(HttpClient http,String url){
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
            IOUtils.write(imgDownload, new FileOutputStream("E:/code.jpg"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost2(String url, Map param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void get_data(Page page){
        String userId = "15210631912";
        String passWord = "xsz6134597";
        String passwordName = page.getHtml().xpath("//input[@type='password']/@name").toString();
        String submit = page.getHtml().xpath("//input[@name='submit']/@value").toString();
        String remember = "on";
        String backUrl = page.getHtml().xpath("//input[@name='backurl']/@value").toString();
        String backTitle = page.getHtml().xpath("//input[@name='backtitle']/@value").toString();
        String vk = page.getHtml().xpath("//input[@name='vk']/@value").toString();
        String capid = page.getHtml().xpath("//input[@name='capid']/@value").toString();
        System.out.println("Input code:");
        Scanner s = new Scanner(System.in);
        String codeInput = s.next();
        headerDict[0]=new BasicNameValuePair("mobile",userId);
        headerDict[1]=new BasicNameValuePair(passwordName,passWord);
        headerDict[2]=new BasicNameValuePair("code",codeInput);
        headerDict[3]=new BasicNameValuePair("remember",remember);
        headerDict[4]=new BasicNameValuePair("backUrl",backUrl);
        headerDict[5]=new BasicNameValuePair("backTitle",backTitle);
        headerDict[6]=new BasicNameValuePair("vk",vk);
        headerDict[7]=new BasicNameValuePair("capid",capid);
        headerDict[8]=new BasicNameValuePair("submit",submit);
    }

    public static Request regroupRequest(String url ,String post_data){
        Request request=new Request();
        request.setMethod(HttpConstant.Method.POST);
        String postParam=post_data;
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
//    business=tabdata&person=12664&personName=&startIndex=1&cateId=3&lastYear=9999&sourceSite=14&kind=&filter=&tclientip=
    public void process(Page page) {
        System.out.println(page.getHtml().toString());
        now_page = page;
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
        download_picture(codeImgUrl);
        System.out.println("[CheckCode Downloaded] : " + codeImgUrl);
//        Scanner s = new Scanner(System.in);
//        String codeInput = s.next();
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
        String data = "mobile="+userId+"&"+passwordName+passWord+"&code="+"codeInput"+"&remember="+remember+"&backUrl="
                +backUrl+"&backTitle="+backTitle+"&vk="+vk+"&capId="+capId+"&submit="+submit;
        System.out.println(data);
//        String sr=sendPost2("http://login.weibo.cn/login/", requestDic);
//        System.out.println(sr);
//		Request re1=regroupRequest(url, post_data);
//		page.addTargetRequest(re1);
//        System.out.println(page.getHtml().toString());
    }

    public Site getSite() {
        site.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
//        System.out.println(site.getHeaders().toString());
        return site;
    }
    public static String login(String temp_url,String temp_post_data){
        url = temp_url;
        post_data = temp_post_data;
//        Request requests=new Request();
//        requests.setMethod(HttpConstant.Method.POST);
//        requests.setUrl(url);
//        NameValuePair []headerDict = new NameValuePair[1];
//        headerDict[0]=new BasicNameValuePair("name","XX1233");
//        requests.putExtra("nameValuePair",headerDict);
//        Spider spider=Spider.create(new LoginProcessor()) .addPipeline(new ConsolePipeline()).addRequest(requests);
//        Spider spider=Spider.create(new LoginProcessor()).addUrl(url);
//        Request re=regroupRequest(url,url);
//        spider.addUrl("http://d.weibo.com/1087030002_2975_1003_0?page=3#Pl_Core_F4RightUserList__4");
//        spider.addRequest(re);
//        spider.addRequest(regroupRequest(url ,post_data));
//        spider.thread(1).run();
        Request requests=new Request();
        requests.setMethod(HttpConstant.Method.POST);
        requests.setUrl(url);
//        NameValuePair []headerDict = new NameValuePair[1];
        for (int i = 0; i <9; i ++){
            headerDict[i]=new BasicNameValuePair("name","XX1233");
        }
        requests.putExtra("nameValuePair",headerDict);
        Spider spider=Spider.create(new LoginProcessor()) .addPipeline(new ConsolePipeline()).addRequest(requests);
        System.out.println(headerDict[0]);
        spider.thread(1).run();
        get_data(now_page);
        System.out.println(headerDict[0]);
        Spider spider2=Spider.create(new LoginProcessor()) .addPipeline(new ConsolePipeline()).addRequest(requests);
        spider2.thread(1).run();
        String return_str = flag + "\t" + cookie;
        return return_str;
    }

    public static void main(String args[] ) {
        login("http://login.weibo.cn/login/","http://login.weibo.cn/login/");
    }
}