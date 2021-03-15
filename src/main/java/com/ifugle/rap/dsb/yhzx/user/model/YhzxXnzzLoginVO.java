/**
 * Copyright(C) 2019 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 */
package com.ifugle.rap.dsb.yhzx.user.model;

import com.ifugle.rap.dsb.core.model.yhzx.YhzxXnzz;

/**
 * @since 2019年05月29日 17:23
 * @version $Id: YhzxXnzzLoginVO.java 100199 2019-06-03 05:58:47Z HuJiajian $
 * @author HuZhihuai
 */
public class YhzxXnzzLoginVO extends YhzxXnzz {
    private static final long serialVersionUID = 1L;
    private String ssjgmc;

    public String getSsjgmc() {
        return ssjgmc;
    }

    public void setSsjgmc(String ssjgmc) {
        this.ssjgmc = ssjgmc;
    }
}