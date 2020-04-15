package com.lingoace.task.entity.master;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class OpsUserInfo implements Serializable {
    private static final long serialVersionUID = -6050019686403208791L;

    private Integer id;

    private String code;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String nameZh;

    private String nameEn;

    private String realName;

    private Integer gender;

    private String wechat;

    private String wechatQrcode;

    private String whatsapp;

    private Date lastLoginTime;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}