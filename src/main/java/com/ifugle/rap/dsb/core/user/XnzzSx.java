/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 *
 */
package com.ifugle.rap.dsb.core.user;

import java.io.Serializable;

import lombok.Data;

/**
 * 虚拟组织属性
 * 
 * @since 2016年8月27日 上午10:09:36
 * @version $Id: XnzzSx.java 31082 2017-04-06 10:33:54Z HuZhihuai $
 * @author HuZhihuai
 *
 */
@Data
public class XnzzSx implements Serializable {
	private static final long serialVersionUID = 7027263975247535879L;
	private String xnzzCorpId;
	private Long xnzzId;
	private String userId;
	private String sjhm;
	private String xnzzmc;
	private String ssjgDm;
	private String xzqhszDm;
	private boolean glybj;
	private Long yhId;
	private String openId;
	private String xm;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
		result = prime * result + ((getXnzzId() == null) ? 0 : getXnzzId().hashCode());
		result = prime * result + ((getXnzzCorpId() == null) ? 0 : getXnzzCorpId().hashCode());
		result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
		result = prime * result + ((getXnzzmc() == null) ? 0 : getXnzzmc().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		XnzzSx other = (XnzzSx) obj;
		return (this.getXnzzId() == null ? other.getXnzzId() == null : this.getXnzzId().equals(other.getXnzzId()))
				&& (this.getXnzzCorpId() == null ? other.getXnzzCorpId() == null : this.getXnzzCorpId().equals(other.getXnzzCorpId()))
				&& (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
				&& (this.getXnzzmc() == null ? other.getXnzzmc() == null : this.getXnzzmc().equals(other.getXnzzmc()));
	}

}
