package com.javatar.api.http

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 22 2021
 */

data class CredentialsResponse(val msg: String, val loggedIn: Boolean = false)
