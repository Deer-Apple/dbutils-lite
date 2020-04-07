package com.AliceTheCat;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface to define how implementations can interact with column handling when constructing a bean from a
 * {@link java.sql.ResultSet}.  ColumnHandlers do the work of retrieving data correctly from the {@code ResultSet}.
 */
public interface ColumnHandler {
    /**
     * Test whether this {@code ColumnHandler} wants to handle a column targeted for a value type matching
     * {@code propType}.
     *
     * @param propType The type of the target parameter.
     * @return true is this property handler can/wants to handle this value; false otherwise.
     */
    boolean match(Class<?> propType);

    /**
     * Do the work required to retrieve and store a column from {@code ResultSet} into something of type
     * {@code propType}. This method is called only if this handler responded {@code true} after a call to
     * {@link #match(Class)}.
     *
     * @param rs The result set to get data from. This should be moved to the correct row already.
     * @param columnIndex The position of the column to retrieve.
     * @return The converted value or the original value if something doesn't work out.
     * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this method is
     *                called on a closed result set
     */
    Object apply(ResultSet rs, int columnIndex) throws SQLException;
}
