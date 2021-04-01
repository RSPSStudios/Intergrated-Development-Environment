package com.javatar.api.http

data class CredentialsUpdateRequest(val email: String, val currentPassword: String, val newPassword: String)