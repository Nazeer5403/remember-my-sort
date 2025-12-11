package eu.hxreborn.remembermysort

import android.database.Cursor
import android.util.Log
import android.util.SparseArray
import io.github.libxposed.api.XposedInterface
import io.github.libxposed.api.XposedInterface.BeforeHookCallback
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam
import io.github.libxposed.api.XposedModuleInterface.PackageLoadedParam
import io.github.libxposed.api.annotations.BeforeInvocation
import io.github.libxposed.api.annotations.XposedHooker
import java.lang.reflect.Field

private const val TAG = "RememberMySort"
private const val TARGET_PACKAGE = "com.google.android.documentsui"
private const val DOCS_UI = "com.android.documentsui"
private const val SORT_DESCENDING = 2

class RememberMySortModule(
    base: XposedInterface,
    param: ModuleLoadedParam,
) : XposedModule(base, param) {
    override fun onPackageLoaded(param: PackageLoadedParam) {
        if (!param.shouldHandlePackage()) return

        runCatching { hookSortCursor(param.classLoader) }
            .onSuccess { Log.i(TAG, "Hooked sortCursor") }
            .onFailure { Log.e(TAG, "Hook failed", it) }
    }

    private fun hookSortCursor(classLoader: ClassLoader) {
        val sortModel = classLoader.loadClass("$DOCS_UI.sorting.SortModel")
        val lookup = classLoader.loadClass("$DOCS_UI.base.Lookup")
        val method = sortModel.getDeclaredMethod("sortCursor", Cursor::class.java, lookup)
        hook(method, SortCursorHooker::class.java)
    }
}

@XposedHooker
object SortCursorHooker : XposedInterface.Hooker {
    @JvmStatic
    @BeforeInvocation
    fun beforeInvocation(callback: BeforeHookCallback) {
        val sortModel = callback.thisObject ?: return
        runCatching { applyDateDescendingSort(sortModel) }
            .onFailure { Log.e(TAG, "Failed to set sort", it) }
    }

    private fun applyDateDescendingSort(sortModel: Any) {
        if (sortModel.getBooleanField("mIsUserSpecified")) return

        val dimensions = sortModel.getSparseArrayField("mDimensions") ?: return
        val dateDimension =
            dimensions.findFirst { dim ->
                dim.getIntField("mDefaultSortDirection") == SORT_DESCENDING
            } ?: return

        dateDimension.setIntField("mSortDirection", SORT_DESCENDING)
        sortModel.setFieldValue("mSortedDimension", dateDimension)
        Log.d(TAG, "Set default: date descending")
    }

    private inline fun <T> SparseArray<T>.findFirst(predicate: (T) -> Boolean): T? {
        for (index in 0 until size()) {
            val value = valueAt(index) ?: continue
            if (predicate(value)) return value
        }
        return null
    }
}

private fun PackageLoadedParam.shouldHandlePackage(): Boolean = packageName == TARGET_PACKAGE && isFirstPackage

private fun Class<*>.accessibleField(name: String): Field = getDeclaredField(name).apply { isAccessible = true }

private fun Any.getBooleanField(name: String): Boolean = javaClass.accessibleField(name).getBoolean(this)

private fun Any.getIntField(name: String): Int = javaClass.accessibleField(name).getInt(this)

private fun Any.setIntField(
    name: String,
    value: Int,
) {
    javaClass.accessibleField(name).setInt(this, value)
}

private fun Any.setFieldValue(
    name: String,
    value: Any?,
) {
    javaClass.accessibleField(name).set(this, value)
}

private fun Any.getSparseArrayField(name: String): SparseArray<*>? = javaClass.accessibleField(name).get(this) as? SparseArray<*>
