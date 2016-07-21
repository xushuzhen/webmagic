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
