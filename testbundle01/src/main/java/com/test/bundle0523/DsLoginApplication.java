package com.test.bundle0523;

import android.app.Application;

import anetwork.channel.config.NetworkConfigCenter;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.global.SwitchConfig;
import mtopsdk.mtop.intf.Mtop;
import mtopsdk.mtop.intf.MtopEnablePropertyType;
import mtopsdk.mtop.intf.MtopSetting;
import mtopsdk.security.LocalInnerSignImpl;

/**
 * Created by andy on 2018/5/16.
 */

public class DsLoginApplication extends Application {

    private boolean mUseHttp = true;

    @Override
    public void onCreate() {
        super.onCreate();
//        initMtop();
    }

    private void initMtop() {
        TBSdkLog.setTLogEnabled(false);
        //关闭密文
        if (mUseHttp) {
            NetworkConfigCenter.setSSLEnabled(false);
        }
        //[option]关闭MTOP请求长链,调用后Mtop请求直接调用NetworkSDK的HttpNetwork发请求
        SwitchConfig.getInstance().setGlobalSpdySwitchOpen(false);
        //关闭MTOPSDK NewDeviceID逻辑
        MtopSetting.setEnableProperty(Mtop.Id.INNER, MtopEnablePropertyType.ENABLE_NEW_DEVICE_ID, false);
        //设置自定义全局访问域名
        MtopSetting.setMtopDomain(Mtop.Id.INNER, "mMtopHost", "mMtopHost", "mMtopHost");
        //设置自定义签名使用的appKey和appSecret
        MtopSetting.setISignImpl(Mtop.Id.INNER, new LocalInnerSignImpl("mAppkey", "mAppSecret"));
        MtopSetting.setAppVersion(Mtop.Id.INNER, BuildConfig.VERSION_NAME);
        Mtop mtopInstance = Mtop.instance(Mtop.Id.INNER, getApplicationContext(), "");
        //设置日常环境
//        if (mEnv == DEBUG) {
//            mtopInstance.switchEnvMode(EnvModeEnum.TEST);
//        }
        mtopInstance.registerTtid("mTTid");
    }
}
