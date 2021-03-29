package com.example.mydesignapplication.ui.album

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns.*
import android.util.Log
import com.example.mydesignapplication.ui.album.AlbumBean


class AlbumSelectUtils {
    private val TAG = "AlbumSelectUtils"
    private val projection = arrayOf(
//        ALBUM,
//        ALBUM_ARTIST,
//        ARTIST,
//        AUTHOR,
//        BITRATE,
//        BUCKET_DISPLAY_NAME,
//        BUCKET_ID,
//        CAPTURE_FRAMERATE,
//        CD_TRACK_NUMBER,
//        COMPILATION,
//        COMPOSER,

//        DATE_EXPIRES,

//        DATE_TAKEN,
//        DISC_NUMBER,

//        DOCUMENT_ID,
//        DURATION,
//        GENERATION_ADDED,
//        GENERATION_MODIFIED,
//        GENRE,

//        INSTANCE_ID,
//        IS_DOWNLOAD,
//        IS_DRM,
//        IS_FAVORITE,
//        IS_PENDING,
//        IS_TRASHED,

//        NUM_TRACKS,
//        ORIENTATION,
//        ORIGINAL_DOCUMENT_ID,
//        OWNER_PACKAGE_NAME,
//        RELATIVE_PATH,
//        RESOLUTION,

//        VOLUME_NAME,
        DATA,
        DATE_ADDED,
        DATE_MODIFIED,
        DISPLAY_NAME,
        HEIGHT,
        MIME_TYPE,
        SIZE,
        TITLE,
        WIDTH,
//        WRITER,
//        XMP,
//        YEAR
    )

    fun getImages(context: Context): ArrayList<AlbumBean>? {
        val contentResolver = context.contentResolver
        val cursor: Cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,  //限制类型为图片
            projection,
            MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
            arrayOf("image/jpeg", "image/png"),  // 这里筛选了jpg和png格式的图片
            MediaStore.Images.Media.DATE_ADDED
        ) ?: return null
        val result = ArrayList<AlbumBean>()
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                val albumBean = AlbumBean(
                    cursor.getString(cursor.getColumnIndex(DATA)),
                    cursor.getString(cursor.getColumnIndex(DATE_ADDED)),
                    cursor.getString(cursor.getColumnIndex(DATE_MODIFIED)),
                    cursor.getString(cursor.getColumnIndex(DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(HEIGHT)),
                    cursor.getString(cursor.getColumnIndex(MIME_TYPE)),
                    cursor.getString(cursor.getColumnIndex(SIZE)),
                    cursor.getString(cursor.getColumnIndex(TITLE)),
                    cursor.getString(cursor.getColumnIndex(WIDTH))
                )
                Log.d(TAG, albumBean.toString())
                result.add(albumBean)
            }
        }
        cursor.close()
        return result
    }


}