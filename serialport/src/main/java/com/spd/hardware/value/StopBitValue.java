package com.spd.hardware.value;

import android.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Author by HanWenRui, Date on 2023/11/13.
 * 功能描述:停止位列表
 */
public class StopBitValue {
    /**
     * 1位停止位
     */
    public static final int B1 = 1;

    /**
     * 2位停止位
     */
    public static final int B2 = 2;

    @IntDef(value = {B1, B2})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StopBit {
    }
}
