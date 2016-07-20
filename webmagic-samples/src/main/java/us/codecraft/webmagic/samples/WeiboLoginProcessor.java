package us.codecraft.webmagic.samples;

import us.codecraft.webmagic.Page;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by xiaoshu on 16/7/20.
 */

public class WeiboLoginProcessor {

    public boolean analyse_page(Page page){

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
//        return loginCfg;
        boolean login_flag = true;
        return login_flag;
    }

    public static void main(String args[]) {

    }
}