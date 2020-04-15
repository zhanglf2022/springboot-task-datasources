package com.lingoace.task.entity.master;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class OpsDataSynchronization implements Serializable {
    private static final long serialVersionUID = 8632028851499026468L;

    private Integer id;

    private String tbName;

    private Integer syncId;

    private Integer status;

    private Integer syncResult;

    private Integer times;

    private Date startTime;

    private Date endTime;

}