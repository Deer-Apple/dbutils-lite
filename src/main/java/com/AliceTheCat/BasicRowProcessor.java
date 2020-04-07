package com.AliceTheCat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Basic implementation of the {@code RowProcessor} interface.
 *
 * <p>
 * This class is thread-safe.
 * </p>
 *
 * @see RowProcessor
 */
public class BasicRowProcessor implements RowProcessor {

    /**
     * The default BeanProcessor instance to use if not supplied in the
     * constructor.
     */
    private static final BeanProcessor defaultConvert = new BeanProcessor();

    /**
     * Use this to process beans.
     */
    private final BeanProcessor convert;

    /**
     * BasicRowProcessor constructor.  Bean processing defaults to a
     * BeanProcessor instance.
     */
    public BasicRowProcessor() {
        this(defaultConvert);
    }

    /**
     * BasicRowProcessor constructor.
     * @param convert The BeanProcessor to use when converting columns to
     * bean properties.
     * @since DbUtils 1.1
     */
    private BasicRowProcessor(final BeanProcessor convert) {
        super();
        this.convert = convert;
    }

    /**
     * Convert a {@code ResultSet} row into an {@code Object[]}.
     * This implementation copies column values into the array in the same
     * order they're returned from the {@code ResultSet}.  Array elements
     * will be set to {@code null} if the column was SQL NULL.
     *
     * @param rs ResultSet that supplies the array data
     * @throws SQLException if a database access error occurs
     * @return the newly created array
     */
    public Object[] toArray(final ResultSet rs) throws SQLException {
        // TODO
        return null;
    }

    /**
     * Convert a {@code ResultSet} row into a JavaBean.  This
     * implementation delegates to a BeanProcessor instance.
     * @param <T> The type of bean to create
     * @param rs ResultSet that supplies the bean data
     * @param type Class from which to create the bean instance
     * @throws SQLException if a database access error occurs
     * @return the newly created bean
     */
    public <T> T toBean(final ResultSet rs, final Class<? extends T> type) throws SQLException {
        // TODO
        return null;
    }

    /**
     * Convert a {@code ResultSet} into a {@code List} of JavaBeans.
     * This implementation delegates to a BeanProcessor instance.
     * @param <T> The type of bean to create
     * @param rs ResultSet that supplies the bean data
     * @param type Class from which to create the bean instance
     * @throws SQLException if a database access error occurs
     * @return A {@code List} of beans with the given type in the order
     * they were returned by the {@code ResultSet}.
     */
    public <T> List<T> toBeanList(final ResultSet rs, final Class<? extends T> type) throws SQLException {
        // TODO
        return null;
    }

    /**
     * Convert a {@code ResultSet} row into a {@code Map}.
     *
     * <p>
     * This implementation returns a {@code Map} with case insensitive column names as keys. Calls to
     * {@code map.get("COL")} and {@code map.get("col")} return the same value. Furthermore this implementation
     * will return an ordered map, that preserves the ordering of the columns in the ResultSet, so that iterating over
     * the entry set of the returned map will return the first column of the ResultSet, then the second and so forth.
     * </p>
     *
     * @param rs ResultSet that supplies the map data
     * @return the newly created Map
     * @throws SQLException if a database access error occurs
     */
    public Map<String, Object> toMap(final ResultSet rs) throws SQLException {
        return null;
    }
}

