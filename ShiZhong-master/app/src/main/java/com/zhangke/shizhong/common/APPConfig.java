package com.zhangke.shizhong.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhangke.shizhong.util.FileUtils;

import java.io.File;

/**
 * app 配置文件
 * <p>
 * Created by ZhangKe on 2018/4/15.
 */

public class APPConfig {

    private static final String TAG = "APPConfig";

    private static final String SP_NAME = "UserConfig";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    /**
     * 存储在 SharedPreferences 的APP主题配置字段key
     */
    private static final String THEME_SP_KEY = "theme";
    /**
     * 海报功能是否开启
     */
    private static final String POSTER_HIDE = "poster_hide";

    /**
     * APP当前主题</br>
     * -1-未初始化</br>
     * 0-夜间</br>
     * 1-日间
     */
    private static int theme = -1;


    /**
     * 获取 APP 主题
     *
     * @return 0-夜间，1-日间
     */
    public static int getTheme() {
        if (theme == -1) {
            getSharedPreferencesInstance();
            theme = sharedPreferences.getInt(THEME_SP_KEY, 0);
        }
        return theme;
    }

    public static void setTheme(int newTheme) {
        APPConfig.theme = newTheme;
        getSharedPreferencesEditorInstance();
        editor.putInt(THEME_SP_KEY, newTheme);
        editor.commit();
    }



    private static void getSharedPreferencesInstance() {
        if (sharedPreferences == null) {
            sharedPreferences = SZApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
    }

    private static void getSharedPreferencesEditorInstance() {
        if (editor == null) {
            getSharedPreferencesInstance();
            editor = sharedPreferences.edit();
        }
    }


}
