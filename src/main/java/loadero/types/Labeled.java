package loadero.types;

/**
 * Interface to define specific behaviour for every Loadero enum type, that are used
 * to set Loadero's parameters such as network conditions, test mode etc.
 */
public interface Labeled {
    String label();
}
