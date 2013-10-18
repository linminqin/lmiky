package com.lmiky.jdp.logger.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 日志
 * @author lmiky
 * @date 2013-5-10
 */
@Entity
@Table(name="logger")
public class Logger extends BasePojo {
	private static final long serialVersionUID = -424096974521890936L;
	public static final String OPE_TYPE_ADD = "add";
	public static final String OPE_TYPE_UPDATE = "update";
	public static final String OPE_TYPE_DELETE = "delete";
	
	private String pojoName;
	private Long pojoId;
	private Long userId;
	private String userName;
	private Date logTime;
	private String opeType;
	private String opeClassName;
	private String logDesc;
	
	/**
	 * @return the pojoName
	 */
	public String getPojoName() {
		return pojoName;
	}
	/**
	 * @param pojoName the pojoName to set
	 */
	public void setPojoName(String pojoName) {
		this.pojoName = pojoName;
	}
	/**
	 * @return the pojoId
	 */
	public Long getPojoId() {
		return pojoId;
	}
	/**
	 * @param pojoId the pojoId to set
	 */
	public void setPojoId(Long pojoId) {
		this.pojoId = pojoId;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the logTime
	 */
	public Date getLogTime() {
		return logTime;
	}
	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	/**
	 * @return the opeType
	 */
	public String getOpeType() {
		return opeType;
	}
	/**
	 * @param opeType the opeType to set
	 */
	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}
	/**
	 * @return the opeClassName
	 */
	public String getOpeClassName() {
		return opeClassName;
	}
	/**
	 * @param opeClassName the opeClassName to set
	 */
	public void setOpeClassName(String opeClassName) {
		this.opeClassName = opeClassName;
	}
	/**
	 * @return the logDesc
	 */
	public String getLogDesc() {
		return logDesc;
	}
	/**
	 * @param logDesc the logDesc to set
	 */
	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}
}