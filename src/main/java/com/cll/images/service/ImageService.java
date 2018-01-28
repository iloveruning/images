package com.cll.images.service;

import com.cll.images.entity.Image;
import com.github.pagehelper.PageInfo;

/**
 * @author chenliangliang
 * @date 2018/1/7
 */
public interface ImageService {

    PageInfo<Image> listImages(int pageNum);
}
