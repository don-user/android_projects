package ru.yundon.weatherforecast.utils

import ru.yundon.weatherforecast.utils.Constants.SYMBOL_DEGREE
import ru.yundon.weatherforecast.utils.Constants.SYMBOL_GPA
import ru.yundon.weatherforecast.utils.Constants.SYMBOL_HYPHEN
import ru.yundon.weatherforecast.utils.Constants.SYMBOL_MS
import ru.yundon.weatherforecast.utils.Constants.SYMBOL_PERCENT
import ru.yundon.weatherforecast.utils.Constants.SYMBOL_TEMP
import kotlin.math.roundToInt



fun Double?.tempHelper(): String{

    return if (this != null) "${this.roundToInt()}$SYMBOL_TEMP" else SYMBOL_HYPHEN
}

fun Int?.percentHelper(): String{
    return if (this != null) "$this$SYMBOL_PERCENT" else SYMBOL_HYPHEN
}

fun Double?.msHelper(): String{
    return if (this != null) "${this.roundToInt()} $SYMBOL_MS" else SYMBOL_HYPHEN
}

fun Int?.degreeHelper(): String{

    return if (this != null) "$this$SYMBOL_DEGREE" else SYMBOL_HYPHEN
}

fun Int?.gpaHelper(): String{
    return if (this != null) "$this $SYMBOL_GPA" else SYMBOL_HYPHEN
}
