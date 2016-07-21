package us.codecraft.webmagic.samples;

//import net.dongliu.requests;

/**
 * Created by xushuzhen on 2016/7/21.
 */
public class WeiboLoginTestProcessor {
    public String login(String uid){

//        Response<String> resp = Requests.get(url).text();
        String test_cookie = "eeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
        System.out.println("LoginTest" + uid);
        System.out.println("LoginTest" + test_cookie);
        return test_cookie;

    }
}
