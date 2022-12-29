package com.libre.framework.logging.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.libre.toolkit.time.DatePattern;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志
 * </p>
 */
@Data
@TableName("sys_log")
public class SysLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Long id;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 登陆名
	 */
	private String username;

	/**
	 * 日志类别
	 */
	private String logType;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * url 请求参数
	 */
	private String params;

	/**
	 * post data
	 */
	private String data;

	/**
	 * 是否成功[0失败,1成功]
	 */
	private Integer success;

	/**
	 * 类-方法
	 */
	private String classMethod;

	/**
	 * 请求ip
	 */
	private String requestIp;

	/**
	 * 请求耗时
	 */
	private Long requestTime;

	private String requestMethod;

	/**
	 * 系统
	 */
	private String os;

	/**
	 * 浏览器
	 */
	private String browser;

	/**
	 * 请求者地址
	 */
	private String address;

	private String stackTrace;

	/**
	 * 异常名
	 */

	private String exceptionName;

	/**
	 * 异常消息
	 */
	@Nullable
	private String message;

	/**
	 * 类名
	 */
	@Nullable
	private String className;

	/**
	 * 文件名
	 */
	@Nullable
	private String fileName;

	/**
	 * 方法名
	 */
	@Nullable
	private String methodName;

	/**
	 * 代码行数
	 */
	@Nullable
	private Integer lineNumber;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
	@JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
	@ApiModelProperty(value = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime gmtCreate;

}