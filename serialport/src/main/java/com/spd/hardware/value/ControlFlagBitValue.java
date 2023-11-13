package com.spd.hardware.value;

import android.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;




/**
 * Author by HanWenRui, Date on 2023/11/13.
 * 功能描述:流控列表
 */

public class ControlFlagBitValue {
    /**
     * 不使用流控
     */
    public static final int NONE = 0;

    /**
     * 硬件流控
     */
    public static final int HARDWARE = 1;

    /**
     * 软件流控
     */
    public static final int SOFTWARE = 2;

    @IntDef(value = {NONE, HARDWARE, SOFTWARE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ControlFlagBit {
    }
}
