package com.spd.hardware.value;

import android.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Author by HanWenRui, Date on 2023/11/13.
 * 功能描述:数据位列表
 */
public class DataBitValue {
    /**
     * 5位数据位
     */
    public static final int CS5 = 5;
    /**
     * 6位数据位
     */
    public static final int CS6 = 6;
    /**
     * 7位数据位
     */
    public static final int CS7 = 7;
    /**
     * 8位数据位
     */
    public static final int CS8 = 8;

    @IntDef(value = {CS5, CS6, CS7, CS8})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataBit {
    }
}
