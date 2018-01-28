package com.cll.images.service.impl;

import com.cll.images.entity.Image;
import com.cll.images.mapper.ImageMapper;
import com.cll.images.service.ImageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenliangliang
 * @date 2018/1/7
 */
@Service
public class ImageServiceImpl implements ImageService {

    private ImageMapper imageMapper;

    @Autowired
    protected ImageServiceImpl(ImageMapper imageMapper){
        this.imageMapper=imageMapper;
    }

    @Override
    public PageInfo<Image> listImages(int pageNum) {
        PageHelper.startPage(pageNum,8);
        List<Image> images=imageMapper.findAll();

        return new PageInfo<>(images);
    }
}
