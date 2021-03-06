package com.AliceTheCat;

import java.sql.*;

/**
 * Executes SQL queries with pluggable strategies for handling
 * {@code ResultSet}s.  This class is thread safe.
 *
 * @see ResultSetHandler
 */
public class QueryRunner extends AbstractQueryRunner {

    /**
     * Constructor for QueryRunner.
     */
    public QueryRunner() {
        super();
    }

    /**
     * Calls query after checking the parameters to ensure nothing is null.
     *
     * @param conn      The connection to use for the query call.
     * @param closeConn True if the connection should be closed, false otherwise. * do not handle in this project *
     * @param sql       The SQL statement to execute.
     * @param params    An array of query replacement parameters.  Each row in
     *                  this array is one set of batch replacement values.
     * @return The results of the query.
     * @throws SQLException If there are database or parameter errors.
     */
    private <T> T query(final Connection conn, final boolean closeConn, final String sql, final ResultSetHandler<T> rsh, final Object... params)
            throws SQLException {
        // TODO
        if (conn == null) {
            throw new SQLException("database connection is null");
        } else if (conn.isClosed()) {
            throw new SQLException("database connection is closed");
        }

        if (sql == null) {
            throw new SQLException("SQL query cannot be null");
        }

        if (rsh == null) {
            throw new SQLException("ResultSetHandler cannot be null");
        }

        ResultSet rs = null;
        T result = null;

        try {
            if (params != null && params.length > 0) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                this.fillStatement(stmt, params);
                rs = stmt.executeQuery();

                stmt.close();

                result = rsh.handle(rs);
            } else {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);

                stmt.close();

                result = rsh.handle(rs);
            }
        } catch (SQLException e) {
            this.rethrow(e, sql, params);
        } finally {
            rs.close();
        }
        return result;
    }

    /**
     * Calls update after checking the parameters to ensure nothing is null.
     *
     * @param conn      The connection to use for the update call.
     * @param closeConn True if the connection should be closed, false otherwise. * do not handle in this project *
     * @param sql       The SQL statement to execute.
     * @param params    An array of update replacement parameters.  Each row in
     *                  this array is one set of update replacement values.
     * @return The number of rows updated.
     * @throws SQLException If there are database or parameter errors.
     */
    private int update(final Connection conn, final boolean closeConn, final String sql, final Object... params) throws SQLException {
        // TODO
        if (conn == null) {
            throw new SQLException("database connection is null");
        } else if (conn.isClosed()) {
            throw new SQLException("database connection is closed");
        }

        if (sql == null) {
            throw new SQLException("SQL query cannot be null");
        }

        int row = 0;

        try {
            if (params != null && params.length > 0) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                this.fillStatement(stmt, params);
                row = stmt.executeUpdate();

                stmt.close();
            } else {
                Statement stmt = conn.createStatement();
                row = stmt.executeUpdate(sql);

                stmt.close();
            }
        } catch (SQLException e) {
            this.rethrow(e, sql, params);
        }


        return row;
    }

    /**
     * Executes the given INSERT SQL statement.
     *
     * @param conn      The connection to use for the query call.
     * @param closeConn True if the connection should be closed, false otherwise. * do not handle in this project *
     * @param sql       The SQL statement to execute.
     * @param rsh       The handler used to create the result object from
     *                  the {@code ResultSet} of auto-generated keys.
     * @param params    The query replacement parameters.
     * @return An object generated by the handler.
     * @throws SQLException If there are database or parameter errors.
     * @since 1.6
     */
    private <T> T insert(final Connection conn, final boolean closeConn, final String sql, final ResultSetHandler<T> rsh, final Object... params)
            throws SQLException {
        // TODO
        if (conn == null) {
            throw new SQLException("database connection is null");
        } else if (conn.isClosed()) {
            throw new SQLException("database connection is closed");
        }

        if (sql == null) {
            throw new SQLException("SQL query cannot be null");
        }

        T result = null;

        try {
            if (params != null && params.length > 0) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                this.fillStatement(stmt, params);
                stmt.executeUpdate();
                result = rsh.handle(stmt.getGeneratedKeys());
                stmt.close();
            } else {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                result = rsh.handle(stmt.getGeneratedKeys());
                stmt.close();
            }
        } catch (SQLException e) {
            this.rethrow(e, sql, params);
        }

        return result;
    }

    /**
     * Executes the given batch of INSERT SQL statements.
     *
     * @param conn      The connection to use for the query call.
     * @param closeConn True if the connection should be closed, false otherwise. * do not handle in this project*
     * @param sql       The SQL statement to execute.
     * @param rsh       The handler used to create the result object from
     *                  the {@code ResultSet} of auto-generated keys.
     * @param params    The query replacement parameters.
     * @return The result generated by the handler.
     * @throws SQLException If there are database or parameter errors.
     * @since 1.6
     */
    private <T> T insertBatch(final Connection conn, final boolean closeConn, final String sql, final ResultSetHandler<T> rsh, final Object[][] params)
            throws SQLException {
        // TODO
        if (conn == null) {
            throw new SQLException("database connection is null");
        } else if (conn.isClosed()) {
            throw new SQLException("database connection is closed");
        }

        if (sql == null) {
            throw new SQLException("SQL query cannot be null");
        }

        T result = null;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (Object[] param : params) {
                this.fillStatement(stmt, param);
                stmt.addBatch();
            }
            stmt.executeBatch();
            result = rsh.handle(stmt.getGeneratedKeys());
        } catch (SQLException e) {
            this.rethrow(e, sql, params);
        }

        return result;
    }
}

