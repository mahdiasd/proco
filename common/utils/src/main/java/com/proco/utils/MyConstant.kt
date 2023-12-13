package com.proco.utils

import android.os.Environment
import java.io.File

object MyConstant {

    private const val Domain = "api.procoapp.com"
    const val BaseUrl = "https://$Domain/"

    var ConnectionTimeOut = 25L
    var ReadTimeOut = 25L
    var WriteTimeOut = 25L

    // values
    val DownloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator
    const val defaultPageNumber = 1
    const val minRangeTime  = 15
}