package com.cll.images.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenliangliang
 * @date 2018/1/5
 */
public class User implements Serializable {

    private Integer id;

    private String name;

    private String password;

    private Date createTime;

    private Date loginTime;
}
