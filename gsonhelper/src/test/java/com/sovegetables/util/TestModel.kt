package com.sovegetables.util

import com.google.gson.annotations.SerializedName

data class VideoDetail(
    var comment: Int? = null,
    @SerializedName(value = "cover", alternate = ["videoCoverUrl"])
    var cover: String? = "",
    @SerializedName(value = "id", alternate = ["watchRecordId"])
    var id: Long,
    @SerializedName(value = "videoId")
    var videoId: Long = 0,
    @SerializedName("support_num")
    var supportNum: Int? = 0,
    var duration: Long = 0,
    var section: String? = null,
    @SerializedName(value = "title", alternate = ["videoTitle"])
    var title: String = "",
    @SerializedName(value = "url", alternate = ["videoUrl"])
    var url: String = "",
    var isSelect: Boolean = false,
    var type: String? = "item",
    @SerializedName("comment_num")
    var commentNum: Int = 0,
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName(value = "collectStatus")
    var isCollect: Int = 0,
    @SerializedName("is_comment")
    var isComment: Int = 0,
    @SerializedName(value = "likeStatus")
    var isSupport: Int = 0,
    @SerializedName("next_video_id")
    var nextVideoId: Long? = null,
    var originPosition: Int? = null,
    @SerializedName("is_vertical")
    var isVertical: Int = 0,
    @SerializedName("is_answer")
    var isAnswer: Int = 0,
    @SerializedName("pdf_url")
    var pdfUrl: String?,
    @SerializedName("is_show_watermark")
    var isShowWatermark: Int = 1,
    @SerializedName("origin")
    val origin: String? = ""
)  {

    companion object {
        const val VIDEO_VERTICAL = 1
        const val VIDEO_HORIZONTAL = 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VideoDetail

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}