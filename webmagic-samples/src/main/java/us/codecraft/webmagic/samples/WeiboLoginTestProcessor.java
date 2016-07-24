package us.codecraft.webmagic.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


/**
 * Created by xushuzhen on 2016/7/21.
 */
public class WeiboLoginTestProcessor {

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String result1 = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流

            // 发送请求参数

//            in = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//            System.out.println("0"+result)
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line1;
            while ((line1 = in.readLine()) != null) {
                result1 += line1;
            }
            System.out.println("1"+result1);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
//            try{
//                if(out!=null){
//                    out.close();
//                }
//                if(in!=null){
//                    in.close();
//                }
//            }
//            catch(IOException ex){
//                ex.printStackTrace();
//            }
        }
        return result1;
    }

    public String login_test(String uid){

//        Response<String> resp = Requests.get(url).text();
        String test_cookie = "eeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
        System.out.println("LoginTest" + uid);
        System.out.println("LoginTest" + test_cookie);
        return test_cookie;

    }
    public String login(String uid){
        String test_cookie = "eeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
        System.out.println("LoginTest" + uid);
        System.out.println("LoginTest" + test_cookie);


        return test_cookie;
    }
    public static void main(String[] args) {
        //发送 POST 请求
        String sr=WeiboLoginTestProcessor.sendPost("http://login.weibo.cn/login/", "key=123&v=456");
        System.out.println(sr);
    }
}

/*
新浪微博账号	密码
13838493405	zxc123456
14786462574	zxc123456
18843301639	zxc123456
18377112160	zxc123456
15164150394	zxc123456
18275089692	zxc123456
18296765763	zxc123456
15124877209	zxc123456
18789891951	zxc123456
15275176700	zxc123456

 */
