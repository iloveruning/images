package com.cll.images.controller;

import com.cll.images.entity.Image;
import com.cll.images.service.ImageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author chenliangliang
 * @date 2018/1/5
 */
@Controller
public class MainController {


    private ImageService imageService;

    @Autowired
    protected MainController( ImageService imageService){
        this.imageService=imageService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }


    @GetMapping("/list/{pageNum}")
    public String upload(@PathVariable("pageNum") int pageNum, ModelMap map){

        if (pageNum<=0){
            pageNum=1;
        }

        PageInfo<Image> pageInfo=imageService.listImages(pageNum);

        map.addAttribute("list",pageInfo.getList());
        map.addAttribute("total",pageInfo.getPages());
        map.addAttribute("next",pageInfo.getNextPage());
        map.addAttribute("pre",pageInfo.getPrePage());
        map.addAttribute("page",pageInfo.getPageNum());


        return "list";
    }




}
