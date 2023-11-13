package com.spd.hardware.value;

import android.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Author by HanWenRui, Date on 2023/11/13.
 * 功能描述:校验位列表
 */
public class CrcBitValue {
    /**
     * 无奇偶校验
     */
    public static final int NONE = 0;
    /**
     * 奇校验
     */
    public static final int ODD = 1;
    /**
     * 偶校验
     */
    public static final int EVEN = 2;

    @IntDef(value = {NONE, ODD, EVEN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CrcBit {
    }
}
