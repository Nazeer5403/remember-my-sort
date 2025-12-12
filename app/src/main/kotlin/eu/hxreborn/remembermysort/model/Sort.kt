package eu.hxreborn.remembermysort.model

internal object Sort {
    enum class Direction(
        val value: Int,
    ) {
        ASC(1),
        DESC(2),
        ;

        companion object {
            fun fromValue(value: Int): Direction? = entries.find { it.value == value }
        }

        operator fun invoke(): Int = value
    }
}
