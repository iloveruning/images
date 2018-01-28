package com.cll.images.mapper;

import com.cll.images.entity.Image;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenliangliang
 * @date 2018/1/5
 */
@Component
@Mapper
public interface ImageMapper {

    int save(Image image);

    int deleteById(@Param("id") int id);

    List<Image> findAll();

    String findKeyById(@Param("id") int id);

}
