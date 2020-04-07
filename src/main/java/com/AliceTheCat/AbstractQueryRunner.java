package com.AliceTheCat;

import java.beans.PropertyDescriptor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The base class for QueryRunner &amp; AsyncQueryRunner. This class is thread safe.
 *
 * @since 1.4 (mostly extracted from QueryRunner)
 */
public abstract class AbstractQueryRunner {
    /**
     * Is {@link ParameterMetaData#getParameterType(int)} broken (have we tried
     * it yet)?
     */
    private volatile boolean pmdKnownBroken = false;

    /**
     * Default constructor, sets pmdKnownBroken to false, ds to null and stmtConfig to null.
     * I don't handle constructor with parameter pmdKnownBroken & stmtConfig in this lite version.
     */
    public AbstractQueryRunner() {
    }

    /**
     * Fill the {@code PreparedStatement} replacement parameters with the
     * given objects.
     *
     * @param stmt   PreparedStatement to fill
     * @param params Query replacement parameters; {@code null} is a valid
     *               value to pass in.
     * @throws SQLException if a database access error occurs
     */
    public void fillStatement(final PreparedStatement stmt, final Object... params)
            throws SQLException {
        // TODO
    }

    /**
     * Fill the {@code PreparedStatement} replacement parameters with the
     * given object's bean property values.
     *
     * @param stmt       PreparedStatement to fill
     * @param bean       a JavaBean object
     * @param properties an ordered array of properties; this gives the order to insert
     *                   values in the statement
     * @throws SQLException if a database access error occurs
     */
    public void fillStatementWithBean(final PreparedStatement stmt, final Object bean,
                                      final PropertyDescriptor[] properties) throws SQLException {
        // TODO
    }

    /**
     * Fill the {@code PreparedStatement} replacement parameters with the
     * given object's bean property values.
     *
     * @param stmt          PreparedStatement to fill
     * @param bean          A JavaBean object
     * @param propertyNames An ordered array of property names (these should match the
     *                      getters/setters); this gives the order to insert values in the
     *                      statement
     * @throws SQLException If a database access error occurs
     */
    public void fillStatementWithBean(final PreparedStatement stmt, final Object bean,
                                      final String... propertyNames) throws SQLException {
        // TODO
    }

    /**
     * Throws a new exception with a more informative error message.
     *
     * @param cause  The original exception that will be chained to the new
     *               exception when it's rethrown.
     * @param sql    The query that was executing when the exception happened.
     * @param params The query replacement parameters; {@code null} is a valid
     *               value to pass in.
     * @throws SQLException if a database access error occurs
     */
    protected void rethrow(final SQLException cause, final String sql, final Object... params)
            throws SQLException {

        // TODO
    }

    /**
     * Wrap the {@code ResultSet} in a decorator before processing it. This
     * implementation returns the {@code ResultSet} it is given without any
     * decoration.
     *
     * <p>
     * Often, the implementation of this method can be done in an anonymous
     * inner class like this:
     * </p>
     *
     * <pre>
     * QueryRunner run = new QueryRunner() {
     *     protected ResultSet wrap(ResultSet rs) {
     *         return StringTrimmedResultSet.wrap(rs);
     *     }
     * };
     * </pre>
     *
     * @param rs The {@code ResultSet} to decorate; never
     *           {@code null}.
     * @return The {@code ResultSet} wrapped in some decorator.
     */
    protected ResultSet wrap(final ResultSet rs) {
        return rs;
    }

}

