package com.xhkj.khjmvp.utils

import android.content.Context
import android.content.SharedPreferences


/**
 * Created by
 * User -> Summer
 * Date -> 2018/5/16
 *
 * Description: SharedPreferences工具类
 *
 */
class Preference() {

    private lateinit var mSharedPreferences: SharedPreferences

    constructor(context: Context) : this(context, "KHJ")

    constructor(context: Context, sharedPreferencesName: String) : this() {
        if (sharedPreferencesName.isEmpty()) {
            throw Exception("SharedPreferences名为空")
        }
        mSharedPreferences = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
    }

    /**
     * 写入String类型数据
     */
    fun put(key: String, value: String) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    /**
     * 写入String类型数据
     * @return 是否写入成功
     */
    fun putResult(key: String, value: String): Boolean {
        return mSharedPreferences.edit().putString(key, value).commit()
    }

    /**
     * 获取String
     */
    fun getString(key: String): String {
        return mSharedPreferences.getString(key, "")
    }

    /**
     * 获取String
     */
    fun getString(key: String, defaultValue: String): String {
        return mSharedPreferences.getString(key, defaultValue)
    }

    /**
     * 写入Boolean类型数据
     */
    fun put(key: String, value: Boolean) {
        mSharedPreferences.edit().putBoolean(key, value).apply()
    }

    /**
     * 写入Boolean类型数据
     * @return 是否写入成功
     */
    fun putResult(key: String, value: Boolean): Boolean {
        return mSharedPreferences.edit().putBoolean(key, value).commit()
    }

    /**
     * 获取Boolean
     */
    fun getBoolean(key: String): Boolean {
        return mSharedPreferences.getBoolean(key, false)
    }

    /**
     * 获取Boolean
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(key, defaultValue)
    }

    /**
     * 写入Float类型数据
     */
    fun put(key: String, value: Float) {
        mSharedPreferences.edit().putFloat(key, value).apply()
    }

    /**
     * 写入Float类型数据
     * @return 是否写入成功
     */
    fun putResult(key: String, value: Float): Boolean {
        return mSharedPreferences.edit().putFloat(key, value).commit()
    }

    /**
     * 获取Float
     */
    fun getFloat(key: String): Float {
        return mSharedPreferences.getFloat(key, 0f)
    }

    /**
     * 获取Float
     */
    fun getFloat(key: String, defaultValue: Float): Float {
        return mSharedPreferences.getFloat(key, defaultValue)
    }

    /**
     * 写入Int类型数据
     */
    fun put(key: String, value: Int) {
        mSharedPreferences.edit().putInt(key, value).apply()
    }

    /**
     * 写入Int类型数据
     * @return 是否写入成功
     */
    fun putResult(key: String, value: Int): Boolean {
        return mSharedPreferences.edit().putInt(key, value).commit()
    }

    /**
     * 获取Int
     */
    fun getInt(key: String): Int {
        return mSharedPreferences.getInt(key, 0)
    }

    /**
     * 获取Int
     */
    fun getInt(key: String, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    /**
     * 写入Long类型数据
     */
    fun put(key: String, value: Long) {
        mSharedPreferences.edit().putLong(key, value).apply()
    }

    /**
     * 写入Long类型数据
     * @return 是否写入成功
     */
    fun putResult(key: String, value: Long): Boolean {
        return mSharedPreferences.edit().putLong(key, value).commit()
    }

    /**
     * 获取Long
     */
    fun getLong(key: String): Long {
        return mSharedPreferences.getLong(key, 0)
    }

    /**
     * 获取Long
     */
    fun getLong(key: String, defaultValue: Long): Long {
        return mSharedPreferences.getLong(key, defaultValue)
    }

    /**
     * 写入Set<String>类型数据
     */
    fun put(key: String, value: Set<String>) {
        mSharedPreferences.edit().putStringSet(key, value).apply()
    }

    /**
     * 写入Set<String>类型数据
     * @return 是否写入成功
     */
    fun putResult(key: String, value: Set<String>): Boolean {
        return mSharedPreferences.edit().putStringSet(key, value).commit()
    }

    /**
     * 获取Set<String>
     */
    fun getStringSet(key: String): Set<String>? {
        return mSharedPreferences.getStringSet(key, null)
    }

    /**
     * 获取Set<String>
     */
    fun getStringSet(key: String, defaultValue: Set<String>): Set<String> {
        return mSharedPreferences.getStringSet(key, defaultValue)
    }

    /**
     * 删除key对应的值
     */
    fun remove(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }

    /**
     * 删除key对应的值
     * @return 是否删除成功
     */
    fun removeResult(key: String): Boolean {
        return mSharedPreferences.edit().remove(key).commit()
    }

    /**
     * 清除所有数据
     */
    fun clearAll() {
        mSharedPreferences.edit().clear().apply()
    }

    /**
     * 清除所有数据
     * @return 是否清除成功
     */
    fun clearAllResult(): Boolean {
        return mSharedPreferences.edit().clear().commit()
    }

    /**
     * 是否包含Key
     */
    fun contains(key: String): Boolean {
        return mSharedPreferences.contains(key)
    }

    /**
     * 返回所有值
     */
    fun all(): Map<String, *> {
        return mSharedPreferences.all
    }
}
