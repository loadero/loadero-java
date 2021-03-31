package loadero.types;

import com.google.gson.annotations.SerializedName;

public enum WebRtcAsserts implements AssertPath {

    @SerializedName("webrtc/audio/bitrate/out/avg")
    WEBRTC_AUDIO_BITRATE_OUT_AVG("webrtc/audio/bitrate/out/avg"),
    @SerializedName("webrtc/video/rtt/stddev")
    WEBRTC_VIDEO_RTT_STDDEV("webrtc/video/rtt/stddev"),
    @SerializedName("webrtc/video/fps/out/25th")
    WEBRTC_VIDEO_FPS_OUT_25TH("webrtc/video/fps/out/25th"),
    @SerializedName("webrtc/video/bitrate/out/95th")
    WEBRTC_VIDEO_BITRATE_OUT_95TH("webrtc/video/bitrate/out/95th"),
    @SerializedName("webrtc/video/bitrate/in/avg")
    WEBRTC_VIDEO_BITRATE_IN_AVG("webrtc/video/bitrate/in/avg"),
    @SerializedName("webrtc/audio/codec/in")
    WEBRTC_AUDIO_CODEC_IN("webrtc/audio/codec/in"),
    @SerializedName("webrtc/video/jitterBuffer/25th")
    WEBRTC_VIDEO_JITTERBUFFER_25TH("webrtc/video/jitterBuffer/25th"),
    @SerializedName("webrtc/video/bitrate/in/75th")
    WEBRTC_VIDEO_BITRATE_IN_75TH("webrtc/video/bitrate/in/75th"),
    @SerializedName("webrtc/video/bitrate/out/99th")
    WEBRTC_VIDEO_BITRATE_OUT_99TH("webrtc/video/bitrate/out/99th"),
    @SerializedName("webrtc/video/jitter/99th")
    WEBRTC_VIDEO_JITTER_99TH("webrtc/video/jitter/99th"),
    @SerializedName("webrtc/video/fps/in/99th")
    WEBRTC_VIDEO_FPS_IN_99TH("webrtc/video/fps/in/99th"),
    @SerializedName("webrtc/audio/bitrate/in/75th")
    WEBRTC_AUDIO_BITRATE_IN_75TH("webrtc/audio/bitrate/in/75th"),
    @SerializedName("webrtc/video/packets/out/25th")
    WEBRTC_VIDEO_PACKETS_OUT_25TH("webrtc/video/packets/out/25th"),
    @SerializedName("webrtc/audio/level/out/75th")
    WEBRTC_AUDIO_LEVEL_OUT_75TH("webrtc/audio/level/out/75th"),
    @SerializedName("webrtc/audio/packets/out/75th")
    WEBRTC_AUDIO_PACKETS_OUT_75TH("webrtc/audio/packets/out/75th"),
    @SerializedName("webrtc/video/bitrate/in/stddev")
    WEBRTC_VIDEO_BITRATE_IN_STDDEV("webrtc/video/bitrate/in/stddev"),
    @SerializedName("webrtc/video/packets/out/stddev")
    WEBRTC_VIDEO_PACKETS_OUT_STDDEV("webrtc/video/packets/out/stddev"),
    @SerializedName("webrtc/video/jitterBuffer/max")
    WEBRTC_VIDEO_JITTERBUFFER_MAX("webrtc/video/jitterBuffer/max"),
    @SerializedName("webrtc/audio/jitterBuffer/25th")
    WEBRTC_AUDIO_JITTERBUFFER_25TH("webrtc/audio/jitterBuffer/25th"),
    @SerializedName("webrtc/audio/bitrate/out/95th")
    WEBRTC_AUDIO_BITRATE_OUT_95TH("webrtc/audio/bitrate/out/95th"),
    @SerializedName("webrtc/audio/packets/in/25th")
    WEBRTC_AUDIO_PACKETS_IN_25TH("webrtc/audio/packets/in/25th"),
    @SerializedName("webrtc/audio/rtt/rstddev")
    WEBRTC_AUDIO_RTT_RSTDDEV("webrtc/audio/rtt/rstddev"),
    @SerializedName("webrtc/video/bitrate/in/25th")
    WEBRTC_VIDEO_BITRATE_IN_25TH("webrtc/video/bitrate/in/25th"),
    @SerializedName("webrtc/audio/level/in/rstddev")
    WEBRTC_AUDIO_LEVEL_IN_RSTDDEV("webrtc/audio/level/in/rstddev"),
    @SerializedName("webrtc/audio/rtt/50th")
    WEBRTC_AUDIO_RTT_50TH("webrtc/audio/rtt/50th"),
    @SerializedName("webrtc/video/packets/in/50th")
    WEBRTC_VIDEO_PACKETS_IN_50TH("webrtc/video/packets/in/50th"),
    @SerializedName("webrtc/video/rtt/75th")
    WEBRTC_VIDEO_RTT_75TH("webrtc/video/rtt/75th"),
    @SerializedName("webrtc/audio/level/in/50th")
    WEBRTC_AUDIO_LEVEL_IN_50TH("webrtc/audio/level/in/50th"),
    @SerializedName("webrtc/audio/packets/out/stddev")
    WEBRTC_AUDIO_PACKETS_OUT_STDDEV("webrtc/audio/packets/out/stddev"),
    @SerializedName("webrtc/audio/packetsLost/in/total")
    WEBRTC_AUDIO_PACKETSLOST_IN_TOTAL("webrtc/audio/packetsLost/in/total"),
    @SerializedName("webrtc/audio/packetsLost/in/percent")
    WEBRTC_AUDIO_PACKETSLOST_IN_PERCENT("webrtc/audio/packetsLost/in/percent"),
    @SerializedName("webrtc/video/jitter/75th")
    WEBRTC_VIDEO_JITTER_75TH("webrtc/video/jitter/75th"),
    @SerializedName("webrtc/video/bitrate/out/avg")
    WEBRTC_VIDEO_BITRATE_OUT_AVG("webrtc/video/bitrate/out/avg"),
    @SerializedName("webrtc/audio/bitrate/out/99th")
    WEBRTC_AUDIO_BITRATE_OUT_99TH("webrtc/audio/bitrate/out/99th"),
    @SerializedName("webrtc/video/rtt/avg")
    WEBRTC_VIDEO_RTT_AVG("webrtc/video/rtt/avg"),
    @SerializedName("webrtc/video/bitrate/out/50th")
    WEBRTC_VIDEO_BITRATE_OUT_50TH("webrtc/video/bitrate/out/50th"),
    @SerializedName("webrtc/video/bitrate/in/95th")
    WEBRTC_VIDEO_BITRATE_IN_95TH("webrtc/video/bitrate/in/95th"),
    @SerializedName("webrtc/video/bytes/in/total")
    WEBRTC_VIDEO_BYTES_IN_TOTAL("webrtc/video/bytes/in/total"),
    @SerializedName("webrtc/audio/bitrate/in/rstddev")
    WEBRTC_AUDIO_BITRATE_IN_RSTDDEV("webrtc/audio/bitrate/in/rstddev"),
    @SerializedName("webrtc/audio/jitterBuffer/75th")
    WEBRTC_AUDIO_JITTERBUFFER_75TH("webrtc/audio/jitterBuffer/75th"),
    @SerializedName("webrtc/audio/level/in/stddev")
    WEBRTC_AUDIO_LEVEL_IN_STDDEV("webrtc/audio/level/in/stddev"),
    @SerializedName("webrtc/video/bitrate/in/99th")
    WEBRTC_VIDEO_BITRATE_IN_99TH("webrtc/video/bitrate/in/99th"),
    @SerializedName("webrtc/audio/jitter/stddev")
    WEBRTC_AUDIO_JITTER_STDDEV("webrtc/audio/jitter/stddev"),
    @SerializedName("webrtc/audio/bitrate/in/stddev")
    WEBRTC_AUDIO_BITRATE_IN_STDDEV("webrtc/audio/bitrate/in/stddev"),
    @SerializedName("webrtc/video/fps/in/75th")
    WEBRTC_VIDEO_FPS_IN_75TH("webrtc/video/fps/in/75th"),
    @SerializedName("webrtc/audio/packets/in/max")
    WEBRTC_AUDIO_PACKETS_IN_MAX("webrtc/audio/packets/in/max"),
    @SerializedName("webrtc/video/jitter/max")
    WEBRTC_VIDEO_JITTER_MAX("webrtc/video/jitter/max"),
    @SerializedName("webrtc/audio/packets/out/max")
    WEBRTC_AUDIO_PACKETS_OUT_MAX("webrtc/audio/packets/out/max"),
    @SerializedName("webrtc/video/rtt/25th")
    WEBRTC_VIDEO_RTT_25TH("webrtc/video/rtt/25th"),
    @SerializedName("webrtc/audio/jitterBuffer/max")
    WEBRTC_AUDIO_JITTERBUFFER_MAX("webrtc/audio/jitterBuffer/max"),
    @SerializedName("webrtc/video/packets/in/total")
    WEBRTC_VIDEO_PACKETS_IN_TOTAL("webrtc/video/packets/in/total"),
    @SerializedName("webrtc/audio/level/in/avg")
    WEBRTC_AUDIO_LEVEL_IN_AVG("webrtc/audio/level/in/avg"),
    @SerializedName("webrtc/video/packetsLost/out/total")
    WEBRTC_VIDEO_PACKETSLOST_OUT_TOTAL("webrtc/video/packetsLost/out/total"),
    @SerializedName("webrtc/audio/packets/out/95th")
    WEBRTC_AUDIO_PACKETS_OUT_95TH("webrtc/audio/packets/out/95th"),
    @SerializedName("webrtc/audio/bitrate/in/25th")
    WEBRTC_AUDIO_BITRATE_IN_25TH("webrtc/audio/bitrate/in/25th"),
    @SerializedName("webrtc/audio/packets/out/99th")
    WEBRTC_AUDIO_PACKETS_OUT_99TH("webrtc/audio/packets/out/99th"),
    @SerializedName("webrtc/audio/bytes/out/total")
    WEBRTC_AUDIO_BYTES_OUT_TOTAL("webrtc/audio/bytes/out/total"),
    @SerializedName("webrtc/video/bitrate/out/rstddev")
    WEBRTC_VIDEO_BITRATE_OUT_RSTDDEV("webrtc/video/bitrate/out/rstddev"),
    @SerializedName("webrtc/video/jitterBuffer/75th")
    WEBRTC_VIDEO_JITTERBUFFER_75TH("webrtc/video/jitterBuffer/75th"),
    @SerializedName("webrtc/audio/codec/out")
    WEBRTC_AUDIO_CODEC_OUT("webrtc/audio/codec/out"),
    @SerializedName("webrtc/audio/level/in/25th")
    WEBRTC_AUDIO_LEVEL_IN_25TH("webrtc/audio/level/in/25th"),
    @SerializedName("webrtc/video/packets/out/max")
    WEBRTC_VIDEO_PACKETS_OUT_MAX("webrtc/video/packets/out/max"),
    @SerializedName("webrtc/audio/bitrate/out/rstddev")
    WEBRTC_AUDIO_BITRATE_OUT_RSTDDEV("webrtc/audio/bitrate/out/rstddev"),
    @SerializedName("webrtc/video/fps/in/25th")
    WEBRTC_VIDEO_FPS_IN_25TH("webrtc/video/fps/in/25th"),
    @SerializedName("webrtc/audio/jitterBuffer/rstddev")
    WEBRTC_AUDIO_JITTERBUFFER_RSTDDEV("webrtc/audio/jitterBuffer/rstddev"),
    @SerializedName("webrtc/audio/level/out/avg")
    WEBRTC_AUDIO_LEVEL_OUT_AVG("webrtc/audio/level/out/avg"),
    @SerializedName("webrtc/audio/rtt/99th")
    WEBRTC_AUDIO_RTT_99TH("webrtc/audio/rtt/99th"),
    @SerializedName("webrtc/audio/bitrate/in/min")
    WEBRTC_AUDIO_BITRATE_IN_MIN("webrtc/audio/bitrate/in/min"),
    @SerializedName("webrtc/video/codec/out")
    WEBRTC_VIDEO_CODEC_OUT("webrtc/video/codec/out"),
    @SerializedName("webrtc/audio/jitter/max")
    WEBRTC_AUDIO_JITTER_MAX("webrtc/audio/jitter/max"),
    @SerializedName("webrtc/audio/rtt/95th")
    WEBRTC_AUDIO_RTT_95TH("webrtc/audio/rtt/95th"),
    @SerializedName("webrtc/audio/level/out/stddev")
    WEBRTC_AUDIO_LEVEL_OUT_STDDEV("webrtc/audio/level/out/stddev"),
    @SerializedName("webrtc/audio/rtt/stddev")
    WEBRTC_AUDIO_RTT_STDDEV("webrtc/audio/rtt/stddev"),
    @SerializedName("webrtc/audio/jitter/rstddev")
    WEBRTC_AUDIO_JITTER_RSTDDEV("webrtc/audio/jitter/rstddev"),
    @SerializedName("webrtc/video/packets/out/50th")
    WEBRTC_VIDEO_PACKETS_OUT_50TH("webrtc/video/packets/out/50th"),
    @SerializedName("webrtc/audio/bitrate/out/50th")
    WEBRTC_AUDIO_BITRATE_OUT_50TH("webrtc/audio/bitrate/out/50th"),
    @SerializedName("webrtc/audio/jitter/50th")
    WEBRTC_AUDIO_JITTER_50TH("webrtc/audio/jitter/50th"),
    @SerializedName("webrtc/video/jitter/95th")
    WEBRTC_VIDEO_JITTER_95TH("webrtc/video/jitter/95th"),
    @SerializedName("webrtc/video/fps/in/max")
    WEBRTC_VIDEO_FPS_IN_MAX("webrtc/video/fps/in/max"),
    @SerializedName("webrtc/audio/bitrate/in/max")
    WEBRTC_AUDIO_BITRATE_IN_MAX("webrtc/audio/bitrate/in/max"),
    @SerializedName("webrtc/video/packets/in/avg")
    WEBRTC_VIDEO_PACKETS_IN_AVG("webrtc/video/packets/in/avg"),
    @SerializedName("webrtc/audio/level/out/25th")
    WEBRTC_AUDIO_LEVEL_OUT_25TH("webrtc/audio/level/out/25th"),
    @SerializedName("webrtc/video/fps/out/75th")
    WEBRTC_VIDEO_FPS_OUT_75TH("webrtc/video/fps/out/75th"),
    @SerializedName("webrtc/audio/packets/in/75th")
    WEBRTC_AUDIO_PACKETS_IN_75TH("webrtc/audio/packets/in/75th"),
    @SerializedName("webrtc/video/packetsLost/in/total")
    WEBRTC_VIDEO_PACKETSLOST_IN_TOTAL("webrtc/video/packetsLost/in/total"),
    @SerializedName("webrtc/video/fps/out/avg")
    WEBRTC_VIDEO_FPS_OUT_AVG("webrtc/video/fps/out/avg"),
    @SerializedName("webrtc/video/bitrate/in/50th")
    WEBRTC_VIDEO_BITRATE_IN_50TH("webrtc/video/bitrate/in/50th"),
    @SerializedName("webrtc/audio/bitrate/out/min")
    WEBRTC_AUDIO_BITRATE_OUT_MIN("webrtc/audio/bitrate/out/min"),
    @SerializedName("webrtc/video/jitter/rstddev")
    WEBRTC_VIDEO_JITTER_RSTDDEV("webrtc/video/jitter/rstddev"),
    @SerializedName("webrtc/audio/jitterBuffer/99th")
    WEBRTC_AUDIO_JITTERBUFFER_99TH("webrtc/audio/jitterBuffer/99th"),
    @SerializedName("webrtc/video/bitrate/in/min")
    WEBRTC_VIDEO_BITRATE_IN_MIN("webrtc/video/bitrate/in/min"),
    @SerializedName("webrtc/video/codec/in")
    WEBRTC_VIDEO_CODEC_IN("webrtc/video/codec/in"),
    @SerializedName("webrtc/video/packets/out/total")
    WEBRTC_VIDEO_PACKETS_OUT_TOTAL("webrtc/video/packets/out/total"),
    @SerializedName("webrtc/audio/jitterBuffer/95th")
    WEBRTC_AUDIO_JITTERBUFFER_95TH("webrtc/audio/jitterBuffer/95th"),
    @SerializedName("webrtc/video/bitrate/out/max")
    WEBRTC_VIDEO_BITRATE_OUT_MAX("webrtc/video/bitrate/out/max"),
    @SerializedName("webrtc/video/fps/out/rstddev")
    WEBRTC_VIDEO_FPS_OUT_RSTDDEV("webrtc/video/fps/out/rstddev"),
    @SerializedName("webrtc/audio/level/out/rstddev")
    WEBRTC_AUDIO_LEVEL_OUT_RSTDDEV("webrtc/audio/level/out/rstddev"),
    @SerializedName("webrtc/audio/bitrate/in/50th")
    WEBRTC_AUDIO_BITRATE_IN_50TH("webrtc/audio/bitrate/in/50th"),
    @SerializedName("webrtc/audio/packets/out/50th")
    WEBRTC_AUDIO_PACKETS_OUT_50TH("webrtc/audio/packets/out/50th"),
    @SerializedName("webrtc/audio/bitrate/out/25th")
    WEBRTC_AUDIO_BITRATE_OUT_25TH("webrtc/audio/bitrate/out/25th"),
    @SerializedName("webrtc/video/bitrate/in/max")
    WEBRTC_VIDEO_BITRATE_IN_MAX("webrtc/video/bitrate/in/max"),
    @SerializedName("webrtc/video/packets/in/99th")
    WEBRTC_VIDEO_PACKETS_IN_99TH("webrtc/video/packets/in/99th"),
    @SerializedName("webrtc/video/rtt/max")
    WEBRTC_VIDEO_RTT_MAX("webrtc/video/rtt/max"),
    @SerializedName("webrtc/video/packets/in/95th")
    WEBRTC_VIDEO_PACKETS_IN_95TH("webrtc/video/packets/in/95th"),
    @SerializedName("webrtc/video/jitterBuffer/95th")
    WEBRTC_VIDEO_JITTERBUFFER_95TH("webrtc/video/jitterBuffer/95th"),
    @SerializedName("webrtc/audio/level/out/50th")
    WEBRTC_AUDIO_LEVEL_OUT_50TH("webrtc/audio/level/out/50th"),
    @SerializedName("webrtc/audio/rtt/avg")
    WEBRTC_AUDIO_RTT_AVG("webrtc/audio/rtt/avg"),
    @SerializedName("webrtc/audio/rtt/75th")
    WEBRTC_AUDIO_RTT_75TH("webrtc/audio/rtt/75th"),
    @SerializedName("webrtc/video/jitterBuffer/99th")
    WEBRTC_VIDEO_JITTERBUFFER_99TH("webrtc/video/jitterBuffer/99th"),
    @SerializedName("webrtc/audio/packets/in/rstddev")
    WEBRTC_AUDIO_PACKETS_IN_RSTDDEV("webrtc/audio/packets/in/rstddev"),
    @SerializedName("webrtc/video/fps/out/99th")
    WEBRTC_VIDEO_FPS_OUT_99TH("webrtc/video/fps/out/99th"),
    @SerializedName("webrtc/video/packets/out/99th")
    WEBRTC_VIDEO_PACKETS_OUT_99TH("webrtc/video/packets/out/99th"),
    @SerializedName("webrtc/video/rtt/50th")
    WEBRTC_VIDEO_RTT_50TH("webrtc/video/rtt/50th"),
    @SerializedName("webrtc/audio/bitrate/out/stddev")
    WEBRTC_AUDIO_BITRATE_OUT_STDDEV("webrtc/audio/bitrate/out/stddev"),
    @SerializedName("webrtc/video/packets/out/95th")
    WEBRTC_VIDEO_PACKETS_OUT_95TH("webrtc/video/packets/out/95th"),
    @SerializedName("webrtc/audio/packets/in/total")
    WEBRTC_AUDIO_PACKETS_IN_TOTAL("webrtc/audio/packets/in/total"),
    @SerializedName("webrtc/video/jitterBuffer/avg")
    WEBRTC_VIDEO_JITTERBUFFER_AVG("webrtc/video/jitterBuffer/avg"),
    @SerializedName("webrtc/audio/packets/in/95th")
    WEBRTC_AUDIO_PACKETS_IN_95TH("webrtc/audio/packets/in/95th"),
    @SerializedName("webrtc/video/fps/out/stddev")
    WEBRTC_VIDEO_FPS_OUT_STDDEV("webrtc/video/fps/out/stddev"),
    @SerializedName("webrtc/video/fps/out/95th")
    WEBRTC_VIDEO_FPS_OUT_95TH("webrtc/video/fps/out/95th"),
    @SerializedName("webrtc/audio/jitter/99th")
    WEBRTC_AUDIO_JITTER_99TH("webrtc/audio/jitter/99th"),
    @SerializedName("webrtc/audio/packets/out/avg")
    WEBRTC_AUDIO_PACKETS_OUT_AVG("webrtc/audio/packets/out/avg"),
    @SerializedName("webrtc/audio/packets/in/99th")
    WEBRTC_AUDIO_PACKETS_IN_99TH("webrtc/audio/packets/in/99th"),
    @SerializedName("webrtc/video/bitrate/out/25th")
    WEBRTC_VIDEO_BITRATE_OUT_25TH("webrtc/video/bitrate/out/25th"),
    @SerializedName("webrtc/video/fps/in/50th")
    WEBRTC_VIDEO_FPS_IN_50TH("webrtc/video/fps/in/50th"),
    @SerializedName("webrtc/video/bitrate/out/min")
    WEBRTC_VIDEO_BITRATE_OUT_MIN("webrtc/video/bitrate/out/min"),
    @SerializedName("webrtc/video/jitter/50th")
    WEBRTC_VIDEO_JITTER_50TH("webrtc/video/jitter/50th"),
    @SerializedName("webrtc/audio/level/in/75th")
    WEBRTC_AUDIO_LEVEL_IN_75TH("webrtc/audio/level/in/75th"),
    @SerializedName("webrtc/audio/level/in/max")
    WEBRTC_AUDIO_LEVEL_IN_MAX("webrtc/audio/level/in/max"),
    @SerializedName("webrtc/audio/jitter/95th")
    WEBRTC_AUDIO_JITTER_95TH("webrtc/audio/jitter/95th"),
    @SerializedName("webrtc/video/packets/in/stddev")
    WEBRTC_VIDEO_PACKETS_IN_STDDEV("webrtc/video/packets/in/stddev"),
    @SerializedName("webrtc/video/bitrate/out/75th")
    WEBRTC_VIDEO_BITRATE_OUT_75TH("webrtc/video/bitrate/out/75th"),
    @SerializedName("webrtc/audio/rtt/25th")
    WEBRTC_AUDIO_RTT_25TH("webrtc/audio/rtt/25th"),
    @SerializedName("webrtc/video/fps/in/rstddev")
    WEBRTC_VIDEO_FPS_IN_RSTDDEV("webrtc/video/fps/in/rstddev"),
    @SerializedName("webrtc/audio/packetsLost/out/total")
    WEBRTC_AUDIO_PACKETSLOST_OUT_TOTAL("webrtc/audio/packetsLost/out/total"),
    @SerializedName("webrtc/video/jitter/stddev")
    WEBRTC_VIDEO_JITTER_STDDEV("webrtc/video/jitter/stddev"),
    @SerializedName("webrtc/audio/packets/out/25th")
    WEBRTC_AUDIO_PACKETS_OUT_25TH("webrtc/audio/packets/out/25th"),
    @SerializedName("webrtc/audio/bitrate/in/95th")
    WEBRTC_AUDIO_BITRATE_IN_95TH("webrtc/audio/bitrate/in/95th"),
    @SerializedName("webrtc/audio/jitterBuffer/50th")
    WEBRTC_AUDIO_JITTERBUFFER_50TH("webrtc/audio/jitterBuffer/50th"),
    @SerializedName("webrtc/video/fps/out/max")
    WEBRTC_VIDEO_FPS_OUT_MAX("webrtc/video/fps/out/max"),
    @SerializedName("webrtc/audio/rtt/max")
    WEBRTC_AUDIO_RTT_MAX("webrtc/audio/rtt/max"),
    @SerializedName("webrtc/audio/jitterBuffer/stddev")
    WEBRTC_AUDIO_JITTERBUFFER_STDDEV("webrtc/audio/jitterBuffer/stddev"),
    @SerializedName("webrtc/audio/packets/in/avg")
    WEBRTC_AUDIO_PACKETS_IN_AVG("webrtc/audio/packets/in/avg"),
    @SerializedName("webrtc/video/packets/in/75th")
    WEBRTC_VIDEO_PACKETS_IN_75TH("webrtc/video/packets/in/75th"),
    @SerializedName("webrtc/audio/level/out/99th")
    WEBRTC_AUDIO_LEVEL_OUT_99TH("webrtc/audio/level/out/99th"),
    @SerializedName("webrtc/audio/jitterBuffer/avg")
    WEBRTC_AUDIO_JITTERBUFFER_AVG("webrtc/audio/jitterBuffer/avg"),
    @SerializedName("webrtc/video/jitter/avg")
    WEBRTC_VIDEO_JITTER_AVG("webrtc/video/jitter/avg"),
    @SerializedName("webrtc/audio/jitter/25th")
    WEBRTC_AUDIO_JITTER_25TH("webrtc/audio/jitter/25th"),
    @SerializedName("webrtc/audio/level/out/95th")
    WEBRTC_AUDIO_LEVEL_OUT_95TH("webrtc/audio/level/out/95th"),
    @SerializedName("webrtc/video/jitterBuffer/50th")
    WEBRTC_VIDEO_JITTERBUFFER_50TH("webrtc/video/jitterBuffer/50th"),
    @SerializedName("webrtc/video/jitterBuffer/rstddev")
    WEBRTC_VIDEO_JITTERBUFFER_RSTDDEV("webrtc/video/jitterBuffer/rstddev"),
    @SerializedName("webrtc/audio/packets/in/stddev")
    WEBRTC_AUDIO_PACKETS_IN_STDDEV("webrtc/audio/packets/in/stddev"),
    @SerializedName("webrtc/video/packets/out/avg")
    WEBRTC_VIDEO_PACKETS_OUT_AVG("webrtc/video/packets/out/avg"),
    @SerializedName("webrtc/video/packets/out/rstddev")
    WEBRTC_VIDEO_PACKETS_OUT_RSTDDEV("webrtc/video/packets/out/rstddev"),
    @SerializedName("webrtc/video/packets/in/max")
    WEBRTC_VIDEO_PACKETS_IN_MAX("webrtc/video/packets/in/max"),
    @SerializedName("webrtc/video/jitter/25th")
    WEBRTC_VIDEO_JITTER_25TH("webrtc/video/jitter/25th"),
    @SerializedName("webrtc/video/bytes/out/total")
    WEBRTC_VIDEO_BYTES_OUT_TOTAL("webrtc/video/bytes/out/total"),
    @SerializedName("webrtc/audio/bitrate/in/avg")
    WEBRTC_AUDIO_BITRATE_IN_AVG("webrtc/audio/bitrate/in/avg"),
    @SerializedName("webrtc/video/fps/in/stddev")
    WEBRTC_VIDEO_FPS_IN_STDDEV("webrtc/video/fps/in/stddev"),
    @SerializedName("webrtc/video/fps/in/avg")
    WEBRTC_VIDEO_FPS_IN_AVG("webrtc/video/fps/in/avg"),
    @SerializedName("webrtc/video/packetsLost/in/percent")
    WEBRTC_VIDEO_PACKETSLOST_IN_PERCENT("webrtc/video/packetsLost/in/percent"),
    @SerializedName("webrtc/video/jitterBuffer/stddev")
    WEBRTC_VIDEO_JITTERBUFFER_STDDEV("webrtc/video/jitterBuffer/stddev"),
    @SerializedName("webrtc/video/packets/out/75th")
    WEBRTC_VIDEO_PACKETS_OUT_75TH("webrtc/video/packets/out/75th"),
    @SerializedName("webrtc/video/rtt/95th")
    WEBRTC_VIDEO_RTT_95TH("webrtc/video/rtt/95th"),
    @SerializedName("webrtc/audio/bytes/in/total")
    WEBRTC_AUDIO_BYTES_IN_TOTAL("webrtc/audio/bytes/in/total"),
    @SerializedName("webrtc/video/bitrate/in/rstddev")
    WEBRTC_VIDEO_BITRATE_IN_RSTDDEV("webrtc/video/bitrate/in/rstddev"),
    @SerializedName("webrtc/video/bitrate/out/stddev")
    WEBRTC_VIDEO_BITRATE_OUT_STDDEV("webrtc/video/bitrate/out/stddev"),
    @SerializedName("webrtc/audio/packetsLost/out/percent")
    WEBRTC_AUDIO_PACKETSLOST_OUT_PERCENT("webrtc/audio/packetsLost/out/percent"),
    @SerializedName("webrtc/video/packetsLost/out/percent")
    WEBRTC_VIDEO_PACKETSLOST_OUT_PERCENT("webrtc/video/packetsLost/out/percent"),
    @SerializedName("webrtc/audio/packets/out/total")
    WEBRTC_AUDIO_PACKETS_OUT_TOTAL("webrtc/audio/packets/out/total"),
    @SerializedName("webrtc/video/rtt/99th")
    WEBRTC_VIDEO_RTT_99TH("webrtc/video/rtt/99th"),
    @SerializedName("webrtc/audio/jitter/avg")
    WEBRTC_AUDIO_JITTER_AVG("webrtc/audio/jitter/avg"),
    @SerializedName("webrtc/audio/level/in/99th")
    WEBRTC_AUDIO_LEVEL_IN_99TH("webrtc/audio/level/in/99th"),
    @SerializedName("webrtc/audio/bitrate/out/max")
    WEBRTC_AUDIO_BITRATE_OUT_MAX("webrtc/audio/bitrate/out/max"),
    @SerializedName("webrtc/audio/level/out/max")
    WEBRTC_AUDIO_LEVEL_OUT_MAX("webrtc/audio/level/out/max"),
    @SerializedName("webrtc/video/packets/in/25th")
    WEBRTC_VIDEO_PACKETS_IN_25TH("webrtc/video/packets/in/25th"),
    @SerializedName("webrtc/audio/bitrate/in/99th")
    WEBRTC_AUDIO_BITRATE_IN_99TH("webrtc/audio/bitrate/in/99th"),
    @SerializedName("webrtc/video/fps/in/95th")
    WEBRTC_VIDEO_FPS_IN_95TH("webrtc/video/fps/in/95th"),
    @SerializedName("webrtc/audio/bitrate/out/75th")
    WEBRTC_AUDIO_BITRATE_OUT_75TH("webrtc/audio/bitrate/out/75th"),
    @SerializedName("webrtc/video/fps/out/50th")
    WEBRTC_VIDEO_FPS_OUT_50TH("webrtc/video/fps/out/50th"),
    @SerializedName("webrtc/audio/packets/in/50th")
    WEBRTC_AUDIO_PACKETS_IN_50TH("webrtc/audio/packets/in/50th"),
    @SerializedName("webrtc/video/packets/in/rstddev")
    WEBRTC_VIDEO_PACKETS_IN_RSTDDEV("webrtc/video/packets/in/rstddev"),
    @SerializedName("webrtc/audio/jitter/75th")
    WEBRTC_AUDIO_JITTER_75TH("webrtc/audio/jitter/75th"),
    @SerializedName("webrtc/audio/level/in/95th")
    WEBRTC_AUDIO_LEVEL_IN_95TH("webrtc/audio/level/in/95th"),
    @SerializedName("webrtc/video/rtt/rstddev")
    WEBRTC_VIDEO_RTT_RSTDDEV("webrtc/video/rtt/rstddev"),
    @SerializedName("webrtc/audio/packets/out/rstddev")
    WEBRTC_AUDIO_PACKETS_OUT_RSTDDEV("webrtc/audio/packets/out/rstddev");

    private final String label;

    WebRtcAsserts(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}