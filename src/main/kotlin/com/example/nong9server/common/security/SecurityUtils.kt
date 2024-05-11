package com.example.nong9server.common.security

import java.security.MessageDigest

private val SHA256: MessageDigest = MessageDigest.getInstance("SHA-256")

private fun bytesToHex(bytes: ByteArray): String = bytes.fold("") { previous, current -> previous + "%02x".format(current) }

fun sha256Encrypt(plainText: String): String = bytesToHex(SHA256.digest(plainText.toByteArray()))
