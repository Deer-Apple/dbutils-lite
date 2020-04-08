package com.AliceTheCat;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Arrays;

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
        if (stmt == null || params == null) return;

        ParameterMetaData pmd = stmt.getParameterMetaData();
        if (pmd.getParameterCount() != params.length) {
            throw new SQLException("Unmatched parameters number (expected " + pmd.getParameterCount()
                    + ", actual " + params.length);
        }

        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                stmt.setObject(i + 1, params[i]);
            } else {
                // note from dbutils
                // VARCHAR works with many drivers regardless
                // of the actual column type. Oddly, NULL and
                // OTHER don't work with Oracle's drivers.
                stmt.setNull(i + 1, Types.VARCHAR);
            }
        }
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
        if (stmt == null || bean == null || properties == null) {
            return;
        }
        Object[] params = new Object[properties.length];
        for (int i = 0; i < params.length; i++) {
            PropertyDescriptor propertyDescriptor = properties[i];
            Method readMethod = propertyDescriptor.getReadMethod();
            Object value;
            if (readMethod == null) {
                throw new RuntimeException("No read method for property " + propertyDescriptor.getName()
                        + " in class " + bean.getClass());
            }
            try {
                value = readMethod.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Couldn't invoke method: " + readMethod, e);
            }
            params[i] = value;
        }
        fillStatement(stmt, params);
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
        PropertyDescriptor[] properties;
        try {
            properties = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException("Cannot introspect class " + bean.getClass().toString());
        }
        // we need to sort PropertyDescriptor according to propertyNames
        PropertyDescriptor[] sorted = new PropertyDescriptor[propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++) {
            String propertyName = propertyNames[i];
            if (propertyName == null) {
                throw new NullPointerException("Property name cannot be null");
            }
            boolean found = false;
            for (PropertyDescriptor property : properties) {
                if (propertyName.equals(property.getName())) {
                    found = true;
                    sorted[i] = property;
                    break;
                }
            }
            if (!found) {
                throw new RuntimeException("Cannot find property with name " + propertyName
                        + " in class" + bean.getClass().toString());
            }
        }
        fillStatementWithBean(stmt, bean, sorted);
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
        String causeMessage = cause.getMessage();
        if(causeMessage == null) {
            causeMessage = "";
        }

        StringBuffer newCauseMessage = new StringBuffer(causeMessage);

        newCauseMessage.append(" Query: ");
        newCauseMessage.append(sql);
        newCauseMessage.append(" Params: ");

        if(params == null) {
            newCauseMessage.append("[]");
        } else {
            newCauseMessage.append(Arrays.deepToString(params));
        }

        final SQLException e = new SQLException(newCauseMessage.toString(), cause.getSQLState(), cause.getCause());
        e.setNextException(cause);
        throw e;
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

