package com.example.moviecatch.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class AvatarCanvas constructor(private val userName: String) {

    private var _bitmap: Bitmap? = null
    private val bitmap get() = _bitmap!!

    init {
        create(userName)
    }

    private fun create(userName: String) {
        // Avatarın genişliği ve yüksekliği
        val avatarSize = 200

        // Boş bir Bitmap oluştur
        val bitmap = Bitmap.createBitmap(avatarSize, avatarSize, Bitmap.Config.ARGB_8888)

        // Bitmap üzerinde çizim yapacak Paint nesnesi oluştur
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(63, 81, 181) // Özel bir renk seçebilirsiniz
            style = Paint.Style.FILL
        }

        // Canvas üzerine metin çiz
        val canvas = Canvas(bitmap)
        canvas.drawCircle(
            (avatarSize / 2).toFloat(),
            (avatarSize / 2).toFloat(),
            (avatarSize / 2).toFloat(),
            paint
        )

        // Metin özellikleri için Paint nesnesi oluştur
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = 80f
        }

        // Metni Bitmap'e çiz
        val textBounds = Rect()
        textPaint.getTextBounds(userName.substring(0, 1), 0, 1, textBounds)
        canvas.drawText(
            userName.substring(0, 1),
            (avatarSize / 2).toFloat() - textBounds.exactCenterX(),
            (avatarSize / 2).toFloat() - textBounds.exactCenterY(),
            textPaint
        )

// Bitmap'i ImageView'e ata
        _bitmap = bitmap
    }

    fun getAvatar(): Bitmap {
        return bitmap
    }
}