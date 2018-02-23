package neil.com.kotlinone.base

import android.content.Context
import android.content.SharedPreferences
import neil.com.kotlinone.constant.Constant
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * SharedPreferences 操作
 *
 * ReadWriteProperty 委托属性
 *
 * @author neil
 * @date 2018/2/22
 */
class Preference<T>(val name: String, val default: T) : ReadWriteProperty<Any?, T> {

    // 定义一个伴生对象
    companion object {
        lateinit var preferences: SharedPreferences

        fun setContext(context: Context) {
            preferences = context.getSharedPreferences(
                    context.packageName + Constant.SHARED_NAME,
                    Context.MODE_PRIVATE)
        }

        fun clear() {
            // 提交清除属性事务
            preferences.edit().clear().apply()
        }

    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = findPreference(name, default)


    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun <U> findPreference(name: String, default: U): U = with(preferences) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
        res as U
    }


    private fun <U> putPreference(name: String, value: U) = with(preferences.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name,value)
            is Float -> putFloat(name,value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }


}
