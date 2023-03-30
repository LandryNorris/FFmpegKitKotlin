package com.example.ffmpegkit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class FFprobeOutput(
    val format: MediaFormat,
    val streams: List<StreamInformation> = listOf()
)

@Serializable
data class MediaFormat(
    val format: String? = null,
    val filename: String,
    val formatName: String? = null,
    @SerialName("format_long_name")
    val formatLongName: String? = null,
    @SerialName("start_time")
    val startTime: Double = 0.0,
    val duration: Double = 0.0,
    val size: Long = 0L,
    @SerialName("bit_rate")
    val bitrate: Long = 0L,
    val tags: JsonElement? = null
)

@Serializable
data class StreamInformation(
    val index: Int,
    @SerialName("codec_type")
    val codecType: String,
    @SerialName("codec_name")
    val codecName: String? = null,
    @SerialName("codec_long_name")
    val codecLongName: String? = null,
    @SerialName("pix_fmt")
    val pixelFormat: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    @SerialName("bit_rate")
    val bitrate: String = "",
    @SerialName("sample_rate")
    val sampleRate: String? = null,
    @SerialName("channel_layout")
    val channelLayout: String? = null,
    @SerialName("sample_aspect_ratio")
    val sampleAspectRatio: String? = null,
    @SerialName("display_aspect_ratio")
    val displayAspectRatio: String? = null,
    @SerialName("avg_frame_rate")
    val averageFrameRate: String? = null,
    @SerialName("r_frame_rate")
    val rFrameRate: String? = null,
    @SerialName("time_base")
    val timeBase: String? = null,
    @SerialName("codec_time_base")
    val codecTimeBase: String? = null,
    val tags: JsonElement? = null
)
