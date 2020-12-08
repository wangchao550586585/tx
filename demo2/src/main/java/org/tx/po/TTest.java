package org.tx.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.baomidou.mybatisplus.annotation.IdType;


/**
 * 
 *
 * @author wangchao
 * @date 2020-11-27 13:07:21
 */
@Data
@TableName("t_test")
@EqualsAndHashCode(callSuper = true)
public class TTest extends Model<TTest> {
private static final long serialVersionUID = 1L;


    /**
     * 
     */
    @TableId (type = IdType.AUTO) 
    private Integer id;

    /**
     * 
     */
    private String name;


}
