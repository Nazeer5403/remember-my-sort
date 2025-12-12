package eu.hxreborn.remembermysort.model

import java.lang.reflect.Field

internal data class ReflectedSortModel(
    val clazz: Class<*>,
    val isUserSpecified: Field,
    val dimensions: Field,
    val sortedDimension: Field,
)
