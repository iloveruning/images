package com.cll.images.utils;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

import java.io.InputStream;

/**
 * @author chenliangliang
 * @date 2018/1/5
 */
public class QiniuUtil {

    private static final String ACCESS_KEY = "GDY_33MtHpOgLHrnMPDdHuWXVlUk-CyeWRB55pak";

    private static final String SECRET_KEY = "6J5Ut73dVXmIw1J-sMx_zHuoCFkC2d3cts0gL7DW";

    private static final String BUCKEY = "arunning6";

    private static UploadManager um;

    private static String token;

    private static BucketManager bm;

    static {
        //华南
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        token = auth.uploadToken(BUCKEY);
        Zone zone = Zone.zone2();
        Configuration config = new Configuration(zone);
        um = new UploadManager(config);
        bm = new BucketManager(auth, config);
    }


    public static JSONObject upload(String key, InputStream in) {

        JSONObject res = null;

        try {
            Response response = um.put(in, key, token, null, null);
            //解析上传成功的结果
            res = JSONObject.parseObject(response.bodyString());
            res.put("status", true);
        } catch (QiniuException e) {
            Response r = e.response;
            System.out.println(r.toString());
            res = new JSONObject();
            res.put("status", false);
        }
        return res;

    }

    /**
     * 删除文件
     * @param key
     * @return
     */
    public static boolean delete(String key) {

        try {
            bm.delete(BUCKEY,key);
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 抓取网络资源到空间
     * @param url 网络资源链接
     * @param key
     * @return
     */
    public static JSONObject upload(String url,String key){

        JSONObject res;
        try {
           FetchRet fetchRet= bm.fetch(url,BUCKEY,key);
           res=new JSONObject(8);
           res.put("status",true);
           res.put("hash",fetchRet.hash);
           res.put("key",fetchRet.key);
           res.put("type",fetchRet.mimeType);
           res.put("size",fetchRet.fsize);
        } catch (QiniuException e) {
            e.printStackTrace();
            res=new JSONObject(4);
            res.put("status",false);
            res.put("msg",e.response.toString());
        }
        return res;
    }


    public static void list(String prefix){
        //文件名前缀
        //String prefix = "2018";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";

        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bm.createFileListIterator(BUCKEY, prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            System.out.println("#####################");
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                System.out.println(item.key);
                System.out.println(item.hash);
                System.out.println(item.fsize/1024+"MB");
                System.out.println(item.mimeType);
                System.out.println(item.putTime);
               // System.out.println(item.endUser);
                System.out.println("====================");
            }
        }
    }


    public static void main(String ...arg){
        list("201801072125");
    }

}
