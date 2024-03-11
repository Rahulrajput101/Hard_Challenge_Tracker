package com.ondevop.core_domain.uitl
import android.util.Base64
import android.text.TextUtils
import java.io.IOException
import java.security.KeyFactory
import java.security.PublicKey
import java.security.Signature
import java.security.spec.X509EncodedKeySpec

class Security {
    private val KEY_FACTORY_ALGORITHM = "RSA"
    private val SIGNATURE_ALGORITHM = "SHA1withRSA"

    @Throws(IOException::class)
    fun verifyPurchases(
        base64PublicKey: String,
        signedData: String,
        signature: String
    ): Boolean {
        if (TextUtils.isEmpty(signedData) || TextUtils.isEmpty(base64PublicKey)
            || TextUtils.isEmpty(signature)
        ) {
            return false
        }

        val key = generatePublicKey(base64PublicKey)
        return verify(key, signature, signedData)
    }

    private fun verify(key: Any, signature: String, signedData: String): Boolean {
        try {
            val sig = Signature.getInstance(SIGNATURE_ALGORITHM)
            sig.initVerify(key as PublicKey)
            sig.update(signedData.toByteArray())
            return sig.verify(Base64.decode(signature, Base64.DEFAULT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @Throws(IOException::class)
    private fun generatePublicKey(encodePublicKey: String?): PublicKey {
        return try {
            val decodeKey = Base64.decode(encodePublicKey, Base64.DEFAULT)
            val keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM)
            val publicKeySpec = X509EncodedKeySpec(decodeKey)
            keyFactory.generatePublic(publicKeySpec)
        } catch (e: Exception) {
            throw IOException("Failed to generate public key", e)
        }
    }
}
