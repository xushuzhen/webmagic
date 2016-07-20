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


public class WeiboCoreProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(5).setSleepTime(7000);
    public static String site_user_agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
    public static String site_cookie = "_T_WM=5b7f500916924c4f473d3ad2903e3423; SCF=ArvQTS2Fyk0IDcck3gZZvMECqaooFcJsy-vOM2fl3sI2W3hAC-MBR3oiMZkae7miJ3ORKNxI5avqK5xrR1Kg8d0.; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5vuzwjw3XKAvWJS.aC6X0F5JpX5o2p5NHD95QESoBpShzReh5EWs4Dqcj.i--fiK.7iKn4i--ciKLhi-8hi--fiKnfi-zfi--fiKysi-zE; SUHB=0nlvmVEekkmANP; SUB=_2A256ik-rDeTxGeRI71MV8ibMyz6IHXVWdVHjrDV6PUJbkdAKLXDbkW0z1fpkircsBSFqVSdydXF2sgbhxw..; gsid_CTandWM=4uoXd77d1dNfGFHaOWW1Pb59G02";
    public static String now_flag = "zhengwen";

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
//            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//            conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
//            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
//            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
//            conn.setRequestProperty("Cookie", "_T_WM=5b7f500916924c4f473d3ad2903e3423; SUB=_2AkMg0TATdcNhrAFXnPkRzmPibI9H-jzEiebBAn7oJhMyPRh77mckqSdI1pqEm3DdjhCclsxv2mCKC-eHsw..; gsid_CTandWM=4uVUCpOz5sdTrFjjSNJY3b59G02");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            System.out.println(out);
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            Map connDic = conn.getHeaderFields();
            for ( Object key : connDic.keySet()) {

                System.out.println(connDic.get(key) + "\t" + key);

            }
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

    public static Request regroupRequest(Map requestDic,String url){
        Request request=new Request();
        request.setMethod(HttpConstant.Method.POST);
        request.putExtra("requestDic", requestDic);
        request.setUrl(url);

        return request;
    }

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
            IOUtils.write(imgDownload, new FileOutputStream("/Users/xiaoshu/Downloads/code.jpg"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String login(Page page){

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
        Map requestDic = new HashMap();
        requestDic.put("mobile", userId);
        requestDic.put(passwordName, passWord);
        requestDic.put("code", codeInput);
        requestDic.put("remember", remember);
        requestDic.put("backUrl", backUrl);
        requestDic.put("backTitle", backTitle);
        requestDic.put("vk", vk);
        requestDic.put("capId", capId);
        requestDic.put("submit", submit);


        String loginCfg = "";
        return loginCfg;
    }

    public void zhengwen(Page page){
        System.out.println("[粉丝数\t微博正文\t转发数\t评论数\t点赞数]");
        String temp = page.getUrl().toString();
        String user_id = temp.split("/")[4].split("\\?")[0];
        String page_namber = temp.split("/")[4].split("\\?")[1];
        String fans_number = page.getHtml().xpath("//a[@href='http://weibo.cn/" + user_id + "/fans']/text()").toString();
        List<String> text_list =page.getHtml().xpath("//div[@class='c']/div/span[@class='ctt']/text()").all();

        System.out.println(user_id);
        System.out.println(page_namber);
        System.out.println(fans_number);
//        System.out.println(now_time);
        for (int i = 0; i<text_list.size(); i++){
            System.out.println(text_list.get(i).toString());
        }
//        System.out.println(page.getHtml().toString());
//        <div class="c" id="M_DFzfcjsuD">
//        <div>
//        <span class="ctt">满满幸福～[心][心][心][心]</span>&nbsp;[
    }

    public void huati(Page page){
        System.out.println("[话题名\t粉丝数\t讨论数\t阅读数]");
    }

    public void dianying(Page page){
        System.out.println("[]");
    }

    public Site getSite() {
//        site.addHeader("User-Agent", site_user_agent);
//        site.addHeader("Cookie", site_cookie);
//        site.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
//        site.addHeader("Cookie", "_T_WM=5b7f500916924c4f473d3ad2903e3423; SCF=ArvQTS2Fyk0IDcck3gZZvMECqaooFcJsy-vOM2fl3sI2W3hAC-MBR3oiMZkae7miJ3ORKNxI5avqK5xrR1Kg8d0.; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5vuzwjw3XKAvWJS.aC6X0F5JpX5o2p5NHD95QESoBpShzReh5EWs4Dqcj.i--fiK.7iKn4i--ciKLhi-8hi--fiKnfi-zfi--fiKysi-zE; SUHB=0nlvmVEekkmANP; SUB=_2A256ik-rDeTxGeRI71MV8ibMyz6IHXVWdVHjrDV6PUJbkdAKLXDbkW0z1fpkircsBSFqVSdydXF2sgbhxw..; gsid_CTandWM=4uoXd77d1dNfGFHaOWW1Pb59G02");
//        site.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        site.addHeader("Accept-Encoding", "gzip, deflate, sdch");
//        site.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
//        site.addHeader("Connection", "keep-alive");
//        site.addHeader("Cookie", "_T_WM=5b7f500916924c4f473d3ad2903e3423; PHPSESSID=cc72ef36a5418e3ecc72303c4752ddfb; SUB=_2A256idGiDeTxGeRI71MV8ibMyz6IHXVWdf_qrDV6PUJbkdAKLRilkW1DmVbeR8RKTsgAgq2t7aAJdsW_kQ..; gsid_CTandWM=4uVRd77d1TwBfrQIjahUTb59G02");
//        site.addHeader("Cookie", "_T_WM=5b7f500916924c4f473d3ad2903e3423; PHPSESSID=cc72ef36a5418e3ecc72303c4752ddfb; SUB=_2AkMg0SlWdcNhrAFXnPkRzmPibI9H-jzEiebBAn7oJhMyPRgv7g0dqSd14ylkdny7uE-VRECM1hHXDx3OVw..; gsid_CTandWM=4uwGCpOz5OPz0yxswLzpdb59G02");
//        site.addHeader("Cookie", "_T_WM=5b7f500916924c4f473d3ad2903e3423; SUB=_2AkMg0TATdcNhrAFXnPkRzmPibI9H-jzEiebBAn7oJhMyPRh77mckqSdI1pqEm3DdjhCclsxv2mCKC-eHsw..; gsid_CTandWM=4uVUCpOz5sdTrFjjSNJY3b59G02");
//        site.addHeader("Host", "weibo.cn");
//        site.addHeader("Upgrade-Insecure-Requests", "1");
//        site.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

//        site.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        site.addHeader("Accept-Encoding", "gzip, deflate, sdch");
//        site.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
//        site.addHeader("Connection", "keep-alive");
//        site.addHeader("Cache-Control", "max-age=0");
//        site.addHeader("Content-Length", "242");
//        site.addHeader("Content-Type", "application/x-www-form-urlencoded");
//        site.addHeader("Cookie", "_T_WM=c7431b2a5dcab317ee303cca9c252544; ustat=__182.50.117.157_1468899882_0.88474700; genTime=1468899882; historyRecord={\"href\":\"http://sina.cn/\",\"refer\":\"https://www.baidu.com/link?url=uMx11teSuVwxm4QGdtCgg8Rf94ONbb2l_MjUCkZHn0C&wd=&eqid=b2904d560017afc300000002578da21e\"}; SINAGLOBAL=; Apache=2261071522244.826.1468899931153; ULV=1468899931154:1:1:1:2261071522244.826.1468899931153:; dfz_loc=bj-default; sinaWapAddToHome_SetTime=2016-07-19; vt=4; needapp=1468993698; SUB=_2A256ibKHDeTxGeRI71MV8ibMyz6IHXVWdd7PrDV9PUJbktAKLXHjkW0_Ac0QKZaZp72V5nT0B89vp9cGmg..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5vuzwjw3XKAvWJS.aC6X0F5JpX5oz75NHD95QESoBpShzReh5EWs4Dqcj.i--fiK.7iKn4i--ciKLhi-8hi--fiKnfi-zfi--fiKysi-zE; gsid_CTandWM=4uLd73371M0ftrn2cz1cMb59G02");
////        site.addHeader("Host", "newlogin.sina.cn");
////        site.addHeader("Referer", "newlogin.sina.cn");
//        site.addHeader("Upgrade-Insecure-Requests", "1");
//        site.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");




//        Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//        Accept-Encoding:gzip, deflate, sdch
//        Accept-Language:zh-CN,zh;q=0.8
//        Connection:keep-alive
//        Cookie:_T_WM=5b7f500916924c4f473d3ad2903e3423; SUB=_2AkMg0RcDdcNhrAFXnPkRzmPibI9H-jzEiebBAn7oJhMyPRgv7lJRqScte1uImpTlb3qRcssgm6Ggwx_AoQ..; gsid_CTandWM=4uBmCpOz5KaSO6bBJrtYub59G02
//        Host:weibo.cn
//        Upgrade-Insecure-Requests:1
//        User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36
////
//Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//Accept-Encoding:gzip, deflate
//Accept-Language:zh-CN,zh;q=0.8
//Cache-Control:max-age=0
//Connection:keep-alive
//Content-Length:242
//Content-Type:application/x-www-form-urlencoded
//Cookie:_T_WM=c7431b2a5dcab317ee303cca9c252544; ustat=__182.50.117.157_1468899882_0.88474700; genTime=1468899882; historyRecord={"href":"http://sina.cn/","refer":"https://www.baidu.com/link?url=uMx11teSuVwxm4QGdtCgg8Rf94ONbb2l_MjUCkZHn0C&wd=&eqid=b2904d560017afc300000002578da21e"}; SINAGLOBAL=; Apache=2261071522244.826.1468899931153; ULV=1468899931154:1:1:1:2261071522244.826.1468899931153:; dfz_loc=bj-default; sinaWapAddToHome_SetTime=2016-07-19; vt=4; needapp=1468993698; SUB=_2A256ibKHDeTxGeRI71MV8ibMyz6IHXVWdd7PrDV9PUJbktAKLXHjkW0_Ac0QKZaZp72V5nT0B89vp9cGmg..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5vuzwjw3XKAvWJS.aC6X0F5JpX5oz75NHD95QESoBpShzReh5EWs4Dqcj.i--fiK.7iKn4i--ciKLhi-8hi--fiKnfi-zfi--fiKysi-zE; gsid_CTandWM=4uLd73371M0ftrn2cz1cMb59G02
//Host:newlogin.sina.cn
//Origin:http://newlogin.sina.cn
//Referer:http://newlogin.sina.cn/
//Upgrade-Insecure-Requests:1
//User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36
        return site;
    }

    public void process(Page page) {
        boolean submit_flag = true;
        String passwordName = page.getHtml().xpath("//input[@type='password']/@name").toString();
        if (passwordName != null){
            submit_flag = false;
            System.out.println("[需要登录]");
            boolean login_flag = new WeiboLoginProcessor().analyse_page(page);
            Page login_page = page;
//            String login_flag = login(login_page);
            if (login_flag){
                submit_flag = true;
                System.out.println("[登录成功]");
            }
            else{
                System.out.println("[登陆异常]");
                System.exit(0);
            }
        }
        if (submit_flag){
            String now_url = page.getUrl().toString();
            Page now_page = page;
            System.out.println("[待抓取URL] : "+now_url);
            System.out.println(now_flag);
            if (now_flag.equals("zhengwen")){
                zhengwen(page);
            }
            else if (now_flag.equals("huati")){
                huati(page);
            }
            else if (now_flag.equals("dianying")){
                dianying(page);
            }

        }


//            URL url = new URL("http://login.weibo.cn/login/?");
//            URLConnection conn = url.openConnection();
//            Map headers = conn.getHeaderFields();
//            Set<String> keys = headers.keySet();
//            String setFirstCookie = conn.getHeaderField("Set-Cookie");
//            System.out.println(setFirstCookie);
//            for( String key : keys ) {
//                String val = conn.getHeaderField(key);
//                System.out.println(key+"    "+val);
//            }


//            String requestStr = "";
//            Set<String> keys = requestDic.keySet();
//            for( String key : keys ) {
//                Object val = requestDic.get(key);
//                requestStr +=key+"="+val+"&";
//            }
//            requestStr = requestStr.substring(0,requestStr.length()-1);
////            requestStr = "?mobile=15210631912&"+passwordName+"=xsz6134597&code="+codeInput;
//            System.out.println(requestStr);
//
//            URL url = new URL("http://login.weibo.cn/login/"+randPartUrl);
//            URLConnection connection = url.openConnection();
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
//            connection.setDoOutput(true);
//            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//            out.write(requestStr); // 向页面传递数据。post的关键所在！
//            out.flush();
//            out.close();
//            // 一旦发送成功，用以下方法就可以得到服务器的回应：
//            String sCurrentLine;
//            String sTotalString;
//            sCurrentLine = "";
//            sTotalString = "";
//            InputStream l_urlStream;
//            l_urlStream = connection.getInputStream();
//            // 传说中的三层包装阿！
//            BufferedReader l_reader = new BufferedReader(new InputStreamReader(
//                    l_urlStream));
//            while ((sCurrentLine = l_reader.readLine()) != null) {
//                sTotalString += sCurrentLine + "\r\n";
//
//            }
//            System.out.println(sTotalString);





//            String sr=HttpRequest.sendPost("http://login.weibo.cn/login/",requestStr);
//            System.out.println(sr);
//            Request sr = regroupRequest(requestDic, "http://login.weibo.cn/login/");
//            System.out.println("\n\n"+sr);
//            Map responseHeader=WeiboTestProcessor.sendPost("http://login.weibo.cn/login/?", requestDic, setFirstCookie);
//            Set<String> keys = responseHeader.keySet();
//            for( String key : keys ) {
//                Object val = responseHeader.get(key);
//                System.out.println(key+"    "+val);
//            }
//            Request loginRequest=regroupRequest(requestDic,"http://login.weibo.cn/login/");
//            page.addTargetRequest(loginRequest);
//            System.out.println(loginRequest);

//            System.out.println(page.getHtml());
//            URL url2 = new URL("http://weibo.cn/?vt=4");
//            URLConnection conn2 = url2.openConnection();
//            Map headers2 = conn2.getHeaderFields();
//            Set<String> keys2 = headers2.keySet();
//            for( String key : keys2 ) {
//                String val = conn2.getHeaderField(key);
//                System.out.println(key+"    "+val);
//            }

//            String check = page.getHtml().toString();
//            System.out.println(check);
//            String part = "\t";
//            System.out.println(passwordName + part + remember + part + backUrl + part + backTitle + part + vk + part + capId + part + submit + part + codeImgUrl);
//            System.out.println(imgDownload);
//            System.out.println(codeInput);


    }


    public static void main(String args[]) {
//        Scanner now_s = new Scanner(System.in);
//        now_flag = now_s.next();
        Spider spider=Spider.create(new WeiboCoreProcessor());
//        spider.addUrl("http://login.weibo.cn/login/");
        spider.addUrl("http://weibo.cn/u/1320135280?page=1");
//        for (int page_index = 1; page_index <= 1; page_index ++){
//            spider.addUrl("http://weibo.cn/u/1320135280?page=" + page_index);
//        }
        spider.thread(1).run();

    }
}
