package loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Enum to define compute units types provided for usage in Loadero.
 */
public enum LoaderoComputeUnitsType implements Labeled {
    @SerializedName("g0.5")
    G05("g0.5"),
    @SerializedName("g1")
    G1("g1"),
    @SerializedName("g2")
    G2("g2"),
    @SerializedName("g3")
    G3("g3"),
    @SerializedName("g4")
    G4("g4");
    
    private final String label;
    
    LoaderoComputeUnitsType(String label) {
        this.label = label;
    }
    
    @Override
    public String label() {
        return label;
    }
}
