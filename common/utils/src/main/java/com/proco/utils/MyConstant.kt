package com.proco.utils

import android.os.Environment
import java.io.File

object MyConstant {

    private const val Domain = "192.168.51.157"
    const val BaseUrl = "http://$Domain:8080/"

    var ConnectionTimeOut = 25L
    var ReadTimeOut = 25L
    var WriteTimeOut = 25L

    // values
    val DownloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator
    const val DefaultPageNumber = 1
}