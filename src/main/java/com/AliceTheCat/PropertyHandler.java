package com.AliceTheCat;

/**
 * Interface to define how implementations can interact with property handling when constructing a bean from a
 * {@link java.sql.ResultSet}.  PropertyHandlers do the work of coercing a value into the target type required.
 */
public interface PropertyHandler {

    /**
     * Test whether this {@code PropertyHandler} wants to handle setting {@code value} into something of type
     * {@code parameter}.
     *
     * @param parameter The type of the target parameter.
     * @param value The value to be set.
     * @return true is this property handler can/wants to handle this value; false otherwise.
     */
    boolean match(Class<?> parameter, Object value);

    /**
     * Do the work required to store {@code value} into something of type {@code parameter}. This method is
     * called only if this handler responded {@code true} after a call to {@link #match(Class, Object)}.
     *
     * @param parameter The type of the target parameter.
     * @param value The value to be set.
     * @return The converted value or the original value if something doesn't work out.
     */
    Object apply(Class<?> parameter, Object value);
}
