package us.codecraft.webmagic.samples;

import us.codecraft.webmagic.Page;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Xiaoshu on 2016/7/24.
 */
public class WeiboZhengwenTest {
    public static void zhengwen(Page page){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String hehe = dateFormat.format( now );
        System.out.println(hehe.toString());
        List<String> zhengwen_list = page.getHtml().xpath("//div[@class='c']/div/span[@class='ctt']/text()").all();
        for (int i = 0; i< zhengwen_list.size(); i ++){
            System.out.println(zhengwen_list.get(i).toString());
        }
    }
}
