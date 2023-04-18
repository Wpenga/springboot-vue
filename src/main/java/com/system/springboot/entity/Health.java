package com.system.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴泽鹏
 * @since 2023-04-14
 */
@Getter
@Setter
  @TableName("stu_health")
@ApiModel(value = "Health对象", description = "")
public class Health implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("主键 id")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("学号")
      private String username;

      @ApiModelProperty("当前地址")
      private String address;

      @ApiModelProperty("是否发烧")
      private Boolean isFever;

      @ApiModelProperty("是否经过高危地区")
      private Boolean goRisk;

      @ApiModelProperty("疫苗针数")
      private String vaccineCount;

//      @TableField(value="punch_date")
      @ApiModelProperty("打卡时间")
      /** 创建时间 */
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date punchDate;

}
