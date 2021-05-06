package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can be used to set machine paths for an {@link com.loadero.model.Assert}.
 */
public enum MachineAsserts implements AssertPath {
    @SerializedName("machine/ram/percent/avg")
    MACHINE_RAM_PERCENT_AVG("machine/ram/percent/avg"),
    @SerializedName("machine/ram/percent/99th")
    MACHINE_RAM_PERCENT_99TH("machine/ram/percent/99th"),
    @SerializedName("machine/ram/percent/95th")
    MACHINE_RAM_PERCENT_95TH("machine/ram/percent/95th"),
    @SerializedName("machine/network/errors/out/total")
    MACHINE_NETWORK_ERRORS_OUT_TOTAL("machine/network/errors/out/total"),
    @SerializedName("machine/network/bytes/in/total")
    MACHINE_NETWORK_BYTES_IN_TOTAL("machine/network/bytes/in/total"),
    @SerializedName("machine/network/packets/in/total")
    MACHINE_NETWORK_PACKETS_IN_TOTAL("machine/network/packets/in/total"),
    @SerializedName("machine/network/bitrate/in/75th")
    MACHINE_NETWORK_BITRATE_IN_75TH("machine/network/bitrate/in/75th"),
    @SerializedName("machine/network/bitrate/out/50th")
    MACHINE_NETWORK_BITRATE_OUT_50TH("machine/network/bitrate/out/50th"),
    @SerializedName("machine/ram/percent/max")
    MACHINE_RAM_PERCENT_MAX("machine/ram/percent/max"),
    @SerializedName("machine/network/bitrate/out/75th")
    MACHINE_NETWORK_BITRATE_OUT_75TH("machine/network/bitrate/out/75th"),
    @SerializedName("machine/ram/percent/stddev")
    MACHINE_RAM_PERCENT_STDDEV("machine/ram/percent/stddev"),
    @SerializedName("machine/network/bytes/out/total")
    MACHINE_NETWORK_BYTES_OUT_TOTAL("machine/network/bytes/out/total"),
    @SerializedName("machine/network/bitrate/in/50th")
    MACHINE_NETWORK_BITRATE_IN_50TH("machine/network/bitrate/in/50th"),
    @SerializedName("machine/network/packetsLost/in/percent")
    MACHINE_NETWORK_PACKETSLOST_IN_PERCENT("machine/network/packetsLost/in/percent"),
    @SerializedName("machine/network/bitrate/in/25th")
    MACHINE_NETWORK_BITRATE_IN_25TH("machine/network/bitrate/in/25th"),
    @SerializedName("machine/network/bitrate/in/stddev")
    MACHINE_NETWORK_BITRATE_IN_STDDEV("machine/network/bitrate/in/stddev"),
    @SerializedName("machine/network/packetsLost/out/percent")
    MACHINE_NETWORK_PACKETSLOST_OUT_PERCENT("machine/network/packetsLost/out/percent"),
    @SerializedName("machine/ram/percent/75th")
    MACHINE_RAM_PERCENT_75TH("machine/ram/percent/75th"),
    @SerializedName("machine/network/bitrate/in/max")
    MACHINE_NETWORK_BITRATE_IN_MAX("machine/network/bitrate/in/max"),
    @SerializedName("machine/ram/percent/50th")
    MACHINE_RAM_PERCENT_50TH("machine/ram/percent/50th"),
    @SerializedName("machine/ram/percent/25th")
    MACHINE_RAM_PERCENT_25TH("machine/ram/percent/25th"),
    @SerializedName("machine/network/bitrate/out/99th")
    MACHINE_NETWORK_BITRATE_OUT_99TH("machine/network/bitrate/out/99th"),
    @SerializedName("machine/network/packets/out/total")
    MACHINE_NETWORK_PACKETS_OUT_TOTAL("machine/network/packets/out/total"),
    @SerializedName("machine/network/bitrate/out/rstddev")
    MACHINE_NETWORK_BITRATE_OUT_RSTDDEV("machine/network/bitrate/out/rstddev"),
    @SerializedName("machine/network/bitrate/out/stddev")
    MACHINE_NETWORK_BITRATE_OUT_STDDEV("machine/network/bitrate/out/stddev"),
    @SerializedName("machine/network/bitrate/out/max")
    MACHINE_NETWORK_BITRATE_OUT_MAX("machine/network/bitrate/out/max"),
    @SerializedName("machine/network/errors/in/total")
    MACHINE_NETWORK_ERRORS_IN_TOTAL("machine/network/errors/in/total"),
    @SerializedName("machine/network/bitrate/in/99th")
    MACHINE_NETWORK_BITRATE_IN_99TH("machine/network/bitrate/in/99th"),
    @SerializedName("machine/network/bitrate/out/25th")
    MACHINE_NETWORK_BITRATE_OUT_25TH("machine/network/bitrate/out/25th"),
    @SerializedName("machine/network/bitrate/out/95th")
    MACHINE_NETWORK_BITRATE_OUT_95TH("machine/network/bitrate/out/95th"),
    @SerializedName("machine/network/bitrate/in/95th")
    MACHINE_NETWORK_BITRATE_IN_95TH("machine/network/bitrate/in/95th"),
    @SerializedName("machine/network/bitrate/in/avg")
    MACHINE_NETWORK_BITRATE_IN_AVG("machine/network/bitrate/in/avg"),
    @SerializedName("machine/network/bitrate/in/min")
    MACHINE_NETWORK_BITRATE_IN_MIN("machine/network/bitrate/in/min"),
    @SerializedName("machine/network/bitrate/in/rstddev")
    MACHINE_NETWORK_BITRATE_IN_RSTDDEV("machine/network/bitrate/in/rstddev"),
    @SerializedName("machine/network/bitrate/out/avg")
    MACHINE_NETWORK_BITRATE_OUT_AVG("machine/network/bitrate/out/avg"),
    @SerializedName("machine/network/bitrate/out/min")
    MACHINE_NETWORK_BITRATE_OUT_MIN("machine/network/bitrate/out/min"),
    @SerializedName("machine/cpu/used/max")
    MACHINE_CPU_USED_MAX("machine/cpu/used/max"),
    @SerializedName("machine/cpu/used/avg")
    MACHINE_CPU_USED_AVG("machine/cpu/used/avg"),
    @SerializedName("machine/cpu/used/stddev")
    MACHINE_CPU_USED_STDDEV("machine/cpu/used/stddev"),
    @SerializedName("machine/ram/used/max")
    MACHINE_RAM_USED_MAX("machine/ram/used/max"),
    @SerializedName("machine/ram/used/avg")
    MACHINE_RAM_USED_AVG("machine/ram/used/avg"),
    @SerializedName("machine/ram/used/stddev")
    MACHINE_RAM_USED_STDDEV("machine/ram/used/stddev"),
    @SerializedName("machine/ram/used/rstddev")
    MACHINE_RAM_USED_RSTDEV("machine/ram/used/rstddev"),
    @SerializedName("machine/cpu/used/25th")
    MACHINE_CPU_USED_25TH("machine/cpu/used/25th"),
    @SerializedName("machine/ram/used/25th")
    MACHINE_RAM_USED_25TH("machine/ram/used/25th"),
    @SerializedName("machine/cpu/used/50th")
    MACHINE_CPU_USED_50TH("machine/cpu/used/50th"),
    @SerializedName("machine/ram/used/50th")
    MACHINE_RAM_USED_50TH("machine/ram/used/50th"),
    @SerializedName("machine/cpu/used/75th")
    MACHINE_CPU_USED_75TH("machine/cpu/used/75th"),
    @SerializedName("machine/ram/used/75th")
    MACHINE_RAM_USED_75TH("machine/ram/used/75th"),
    @SerializedName("machine/cpu/used/95th")
    MACHINE_CPU_USED_95TH("machine/cpu/used/95th"),
    @SerializedName("machine/ram/used/95th")
    MACHINE_RAM_USED_95TH("machine/ram/used/95th"),
    @SerializedName("machine/cpu/used/99th")
    MACHINE_CPU_USED_99TH("machine/cpu/used/99th"),
    @SerializedName("machine/ram/used/99th")
    MACHINE_RAM_USED_99TH("machine/ram/used/99th"),
    @SerializedName("machine/cpu/available/total")
    MACHINE_CPU_AVAILABLE_TOTAL("machine/cpu/available/total"),
    @SerializedName("machine/ram/available/total")
    MACHINE_RAM_AVAILABLE_TOTAL("machine/ram/available/total"),
    @SerializedName("machine/cpu/used/rstddev")
    MACHINE_CPU_USED_RSTDEV("machine/cpu/used/rstddev"),
    @SerializedName("machine/cpu/percent/avg")
    MACHINE_CPU_PERCENT_AVG("machine/cpu/percent/avg"),
    @SerializedName("machine/cpu/percent/max")
    MACHINE_CPU_PERCENT_MAX("machine/cpu/percent/max"),
    @SerializedName("machine/cpu/percent/stddev")
    MACHINE_CPU_PERCENT_STDDEV("machine/cpu/percent/stddev"),
    @SerializedName("machine/cpu/percent/25th")
    MACHINE_CPU_PERCENT_25TH("machine/cpu/percent/25th"),
    @SerializedName("machine/cpu/percent/50th")
    MACHINE_CPU_PERCENT_50TH("machine/cpu/percent/50th"),
    @SerializedName("machine/cpu/percent/75th")
    MACHINE_CPU_PERCENT_75TH("machine/cpu/percent/75th"),
    @SerializedName("machine/cpu/percent/95th")
    MACHINE_CPU_PERCENT_95TH("machine/cpu/percent/95th"),
    @SerializedName("machine/cpu/percent/99th")
    MACHINE_CPU_PERCENT_99TH("machine/cpu/percent/99th");

    private final String label;
    private static final EnumLookupHelper<MachineAsserts> helper = new EnumLookupHelper<>(values());

    public static MachineAsserts getConstant(String name) {
        return helper.getConstant(name);
    }

    MachineAsserts(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
