package com.javatar.ui

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 12 2021
 */

class Crumb(val name: String, val action: () -> Unit = {}) {
    override fun toString(): String {
        return name
    }
}
