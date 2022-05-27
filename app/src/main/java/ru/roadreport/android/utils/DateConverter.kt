package ru.roadreport.android.utils

import java.text.SimpleDateFormat

fun toAppDate(mills: Long): String {

    return SimpleDateFormat(DISPLAY_DATE_FORMAT).format(mills)
}
