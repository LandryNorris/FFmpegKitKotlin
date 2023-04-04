package com.example.ffmpegkit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

data class MediaInformation(
    val format: String?,
    val filename: String,
    val formatName: String?,
    val formatLongName: String?,
    val startTime: Double?,
    val duration: Double?,
    val size: Long?,
    val bitrate: Long?,
    val tags: JsonElement?,

    val streams: List<StreamInformation>
)

internal fun mediaInformation(format: MediaFormat, streams: List<StreamInformation>) = MediaInformation(
    format = format.format,
    filename = format.filename,
    formatName = format.formatName,
    formatLongName = format.formatLongName,
    startTime = format.startTime,
    duration = format.duration,
    size = format.size,
    bitrate = format.bitrate,
    tags = format.tags,
    streams = streams
)

@Serializable
internal data class FFprobeOutput(
    val format: MediaFormat,
    val streams: List<StreamInformation> = listOf()
)

@Serializable
internal data class MediaFormat(
    val format: String? = null,
    val filename: String,
    val formatName: String? = null,
    @SerialName("format_long_name")
    val formatLongName: String? = null,
    @SerialName("start_time")
    val startTime: Double? = null,
    val duration: Double? = null,
    val size: Long? = null,
    @SerialName("bit_rate")
    val bitrate: Long? = null,
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
    val bitrate: String? = null,
    @SerialName("sample_rate")
    val sampleRate: String? = null,
    @SerialName("sample_fmt")
    val sampleFormat: String? = null,
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
