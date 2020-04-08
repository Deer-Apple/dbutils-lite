package com.AliceTheCat.handlers;

import com.AliceTheCat.ResultSetHandler;
import com.AliceTheCat.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@code ResultSetHandler} implementation that converts the first
 * {@code ResultSet} row into a JavaBean. This class is thread safe.
 *
 * @param <T> the target bean type
 */
public class BeanHandler<T> implements ResultSetHandler<T> {

    /**
     * The Class of beans produced by this handler.
     */
    private final Class<? extends T> type;

    /**
     * The RowProcessor implementation to use when converting rows
     * into beans.
     */
    private final RowProcessor convert;

    /**
     * Creates a new instance of BeanHandler.
     *
     * @param type The Class that objects returned from {@code handle()}
     *             are created from.
     */
    public BeanHandler(final Class<? extends T> type) {
        this(type, ArrayHandler.ROW_PROCESSOR);
    }

    /**
     * Creates a new instance of BeanHandler.
     *
     * @param type    The Class that objects returned from {@code handle()}
     *                are created from.
     * @param convert The {@code RowProcessor} implementation
     *                to use when converting rows into beans.
     */
    public BeanHandler(final Class<? extends T> type, final RowProcessor convert) {
        this.type = type;
        this.convert = convert;
    }

    /**
     * Convert the first row of the {@code ResultSet} into a bean with the
     * {@code Class} given in the constructor.
     *
     * @param rs {@code ResultSet} to process.
     * @return An initialized JavaBean or {@code null} if there were no
     * rows in the {@code ResultSet}.
     * @throws SQLException if a database access error occurs
     */
    public T handle(final ResultSet rs) throws SQLException {
        // TODO
        return rs.next() ? this.convert.toBean(rs, this.type) : null;
    }

}

