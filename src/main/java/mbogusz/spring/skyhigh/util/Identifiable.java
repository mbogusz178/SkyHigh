package mbogusz.spring.skyhigh.util;

public interface Identifiable<T> {
    T getId();
    void setId(T id);
}
