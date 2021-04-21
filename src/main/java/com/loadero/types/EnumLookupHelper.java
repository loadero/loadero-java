package com.loadero.types;

import com.loadero.exceptions.ApiException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for Enum's values lookup. Basically let us get Enum's value from String.
 */
final class EnumLookupHelper<E extends Enum<E>> {
    private final Map<String, E> lookup;

    public EnumLookupHelper(E[] values) {
        Map<String, E> map = new HashMap<>();
        for (E value : values) {
            map.put(value.toString(), value);
        }
        lookup = Collections.unmodifiableMap(map);
    }

    E getConstant(String name) {
        E constant = lookup.get(name);

        if (constant == null) {
            String msg = String.format("This Enum currently doesn't contains %s.", name);
            throw new ApiException(msg);
        }

        return constant;
    }
}
