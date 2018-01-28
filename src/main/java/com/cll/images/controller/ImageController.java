package com.cll.images.controller;

import com.alibaba.fastjson.JSONObject;
import com.cll.images.entity.Image;
import com.cll.images.mapper.ImageMapper;
import com.cll.images.utils.DateUtil;
import com.cll.images.utils.QiniuUtil;
import com.cll.images.utils.Result;
import com.cll.images.utils.ResultUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * @author chenliangliang
 * @date 2018/1/5
 */
@RestController
public class ImageController {


    private ImageMapper imageMapper;

    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    protected ImageController(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    /**
     * 上传图片接口
     *
     * @param file 图片文件
     * @return
     */
    @ResponseBody
    @PostMapping("/pic")
    public Result savePic(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResultUtil.fail("上传的图片不能为空");
        }

        String desc = request.getParameter("desc");
        System.out.println("desc: " + desc);
        String type = file.getContentType();
        if (type.contains("image")) {
            String originName = file.getOriginalFilename();
            String fileType = originName.substring(originName.lastIndexOf(".") + 1);
            logger.info("上传的文件名：" + originName);
            InputStream is = null;
            try (InputStream in = file.getInputStream();
                 ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                String key = DateUtil.dateTimeString();
                Thumbnails.of(in).scale(1.0).outputQuality(0.25f).toOutputStream(os);
                is = new ByteArrayInputStream(os.toByteArray());
                JSONObject object = QiniuUtil.upload(key, is);
                boolean status = object.getBooleanValue("status");
                if (status) {
                    logger.info("文件 " + originName + " 上传到七牛云成功！");
                    Image image = new Image();
                    image.setName(originName);
                    image.setKey_(object.getString("key"));
                    image.setHash(object.getString("hash"));
                    image.setDescription(desc);
                    image.setType(fileType);
                    System.out.println(image);
                    //object.put("type", fileType);
                    //object.put("name",originName);
                    int res = imageMapper.save(image);
                    if (res == 1) {
                        logger.info("文件 " + originName + " 保存到数据库成功！");
                        return ResultUtil.OK();
                    } else {
                        logger.error("文件 " + originName + " 保存到数据库失败！");
                        return ResultUtil.fail("文件保存到云服务器成功，但保存到数据库失败");
                    }


                } else {
                    logger.error("文件 " + originName + " 上传到七牛云失败！");
                    return ResultUtil.fail("文件 " + originName + " 上传到七牛云失败！");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                }
            }

        } else {
            return ResultUtil.fail("上传的不是图片类型");
        }
        return ResultUtil.fail("未知错误，请重新上传");

    }


    @DeleteMapping("/image/{id}")
    public Result delete(@PathVariable("id") int id) {

        String key = imageMapper.findKeyById(id);

        if (key == null) {
            return ResultUtil.fail("该id对应的图片不存在!");
        }

        if (!QiniuUtil.delete(key)) {
            logger.error("七牛云服务器删除图片失败!");
            return ResultUtil.fail("七牛云服务器删除图片失败!");
        }
        logger.info("七牛云服务器删除图片成功!");

        int res = imageMapper.deleteById(id);
        if (res == 1) {
            return ResultUtil.OK();
        } else {
            logger.error("七牛云服务器删除图片成功,但删除数据库数据失败！");
            return ResultUtil.fail("七牛云服务器删除图片成功,但删除数据库数据失败！");
        }
    }

    @PostMapping("/image/url")
    public Result saveRemotePic(@RequestParam("url") String url) {

        if (url == null || url.trim().length() < 10 || !url.matches("(https?)+://[^\\s]*")) {
            return ResultUtil.fail("超链接格式错误");
        }
        String key = DateUtil.dateTimeString();
       JSONObject obj= QiniuUtil.upload(url,key);
       boolean status=obj.getBooleanValue("status");
       if (status){
           Image image=new Image();
           image.setType(obj.getString("type"));
           image.setHash(obj.getString("hash"));
           image.setKey_(key);
           image.setName("网络图片");
          int res= imageMapper.save(image);
           if (res == 1) {
               logger.info("网络图片 {} 保存到数据库成功！",key);
               return ResultUtil.OK();
           } else {
               logger.error("网络图片 {} 保存到数据库失败！",key);
               return ResultUtil.fail("网络图片保存到云服务器成功，但保存到数据库失败");
           }
       }else {
           logger.error("网络图片 {} 上传到七牛云失败！",key);
           return ResultUtil.fail("网络图片" + key + " 上传到七牛云失败！");
       }
    }

}
