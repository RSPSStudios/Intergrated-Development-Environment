package com.javatar.api.fs

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class Clipboard {

    var data: ByteArray = byteArrayOf()

    fun isEmpty() = data.isEmpty()

}
