package org.tx.service;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/9 11:46
 */
public class Demo{
    public static void main(String[] args) {
        String content="正在阅读你分享《 %s 》文章，一共阅读了%d秒，快去与TA聊聊吧～";
        int startIndex = content.indexOf("《");
        int endIndex = content.indexOf("》");
        String s = content.substring(0, startIndex+1);
        String s1 = content.substring(endIndex);
        System.out.println(s);
        System.out.println(s1);
    }
}
