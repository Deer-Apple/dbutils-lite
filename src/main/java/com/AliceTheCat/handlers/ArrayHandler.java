package com.AliceTheCat.handlers;

import com.AliceTheCat.BasicRowProcessor;
import com.AliceTheCat.ResultSetHandler;
import com.AliceTheCat.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@code ResultSetHandler} implementation that converts a
 * {@code ResultSet} into an {@code Object[]}. This class is
 * thread safe.
 *
 */
public class ArrayHandler implements ResultSetHandler<Object[]> {

    /**
     * Singleton processor instance that handlers share to save memory.  Notice
     * the default scoping to allow only classes in this package to use this
     * instance.
     */
    static final RowProcessor ROW_PROCESSOR = new BasicRowProcessor();

    /**
     * An empty array to return when no more rows are available in the ResultSet.
     */
    private static final Object[] EMPTY_ARRAY = new Object[0];

    /**
     * The RowProcessor implementation to use when converting rows
     * into arrays.
     */
    private final RowProcessor convert;

    /**
     * Creates a new instance of ArrayHandler using a
     * {@code BasicRowProcessor} for conversion.
     */
    public ArrayHandler() {
        this(ROW_PROCESSOR);
    }

    /**
     * Creates a new instance of ArrayHandler.
     *
     * @param convert The {@code RowProcessor} implementation
     * to use when converting rows into arrays.
     */
    public ArrayHandler(final RowProcessor convert) {
        super();
        this.convert = convert;
    }

    /**
     * Places the column values from the first row in an {@code Object[]}.
     * @param rs {@code ResultSet} to process.
     * @return An Object[]. If there are no rows in the {@code ResultSet}
     * an empty array will be returned.
     *
     * @throws SQLException if a database access error occurs
     */
    public Object[] handle(final ResultSet rs) throws SQLException {
        // TODO
        return null;
    }

}
