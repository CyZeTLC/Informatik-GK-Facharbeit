package de.cyzetlc.roadsystem.service.database;

import lombok.Getter;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Getter
public class MySQLQueryBuilder {
    private final ExecutorService executorService;
    private final Connection connection;
    private final IMySQLExtension extension;

    private RowSetFactory factory;
    private LinkedList<Object> params;
    private String query;

    public MySQLQueryBuilder(IMySQLExtension extension) {
        this.executorService = Executors.newFixedThreadPool(15);
        this.params = new LinkedList<>();
        this.extension = extension;
        this.connection = extension.getNewConnection();
        try {
            this.factory = RowSetProvider.newFactory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The `executeQuerySync` function returns the result of executing a query synchronously.
     *
     * @return A CachedRowSet object is being returned.
     */
    public CachedRowSet executeQuerySync() {
        return this.executeQueryOrUpdateSync(false);
    }

    /**
     * The `executeUpdateSync` function returns the result of executing an update query synchronously.
     *
     * @return A CachedRowSet is being returned.
     */
    public CachedRowSet executeUpdateSync() {
        return this.executeQueryOrUpdateSync(true);
    }

    /**
     * The `executeQueryAsync` method asynchronously executes a query and calls a callback function with the result in a
     * CachedRowSet.
     *
     * @param callback The `callback` parameter is a `Consumer` functional interface that accepts a `CachedRowSet` as
     * input. It is used to handle the result of the asynchronous query execution.
     */
    public void executeQueryAsync(Consumer<CachedRowSet> callback) {
        this.executeQueryOrUpdateAsync(callback, false);
    }

    /**
     * The `executeUpdateAsync` method calls `executeQueryOrUpdateAsync` with a null parameter and a flag set to true.
     */
    public void executeUpdateAsync() {
        this.executeQueryOrUpdateAsync(null, true);
    }

    /**
     * The function `executeQueryOrUpdateAsync` asynchronously executes a query or update statement and invokes a callback
     * with the result.
     *
     * @param callback The `callback` parameter is a `Consumer` functional interface that accepts a `CachedRowSet` as
     * input. It is used to handle the result of the asynchronous query or update operation.
     * @param useUpdateStatement The `useUpdateStatement` parameter is a boolean flag that indicates whether the query
     * being executed is an update statement or not. If `useUpdateStatement` is `true`, it means that the query being
     * executed is an update statement; otherwise, it is not an update statement.
     */
    private void executeQueryOrUpdateAsync(Consumer<CachedRowSet> callback, boolean useUpdateStatement) {
        this.executorService.execute(() -> {
            CachedRowSet rs = MySQLQueryBuilder.this.executeQueryOrUpdateSync(useUpdateStatement);
            if (callback != null) {
                callback.accept(rs);
            }
        });
    }

    /**
     * This Java function executes a query or update statement synchronously and returns a CachedRowSet result.
     *
     * @param useUpdateStatement A boolean flag indicating whether to use an update statement or not. If
     * `useUpdateStatement` is `true`, an update statement will be executed; otherwise, a query will be executed.
     * @return The method `executeQueryOrUpdateSync` returns a `CachedRowSet` object.
     */
    private CachedRowSet executeQueryOrUpdateSync(boolean useUpdateStatement) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        CachedRowSet var5;

        try {
            statement = this.connection.prepareStatement(this.query);

            for (int i = 0; i < this.params.size(); ++i) {
                statement.setObject(i + 1, this.params.get(i));
            }

            CachedRowSet crs;

            if (useUpdateStatement) {
                statement.executeUpdate();
                this.closeItems(null, statement);
                return null;
            }

            rs = statement.executeQuery();
            if (rs == null) {
                return null;
            }

            crs = factory.createCachedRowSet();
            crs.populate(rs);
            this.closeItems(rs, statement);
            var5 = crs;
        } catch (SQLException var17) {
            this.printDebugInformation();
            var17.printStackTrace();
            return null;
        } finally {
            try {
                this.closeItems(rs, statement);
            } catch (SQLException var16) {
                var16.printStackTrace();
            }

        }

        return var5;
    }

    /**
     * The `closeItems` method closes the ResultSet, PreparedStatement, and connection objects to release resources and
     * prevent memory leaks.
     *
     * @param rs ResultSet object to be closed after processing data.
     * @param statement The `statement` parameter in the `closeItems` method is a `PreparedStatement` object. It is used to
     * execute parameterized SQL queries against the database. In the provided code snippet, the `statement` object is
     * being checked for null and then closed to release any resources associated with it.
     */
    private void closeItems(ResultSet rs, PreparedStatement statement) throws SQLException {
        if (rs != null) {
            rs.close();
        }

        if (this.connection != null) {
            this.connection.close();
        }

        if (statement != null) {
            statement.close();
        }

        this.extension.closeConnection(this.connection);
    }

    /**
     * The `printDebugInformation` method prints debug information including the query and parameters in Java.
     */
    public void printDebugInformation() {
        System.out.println("-----------------------------");
        System.out.println("Query - Debug");
        System.out.println("Query: " + this.query);
        System.out.println(" ");
        System.out.println("Parameters: ");

        for (Object param : this.params) {
            System.out.println("- " + param);
        }

        System.out.println("-----------------------------");
    }

    /**
     * The `setQuery` function in Java sets the query string and returns the MySQLQueryBuilder object for method chaining.
     *
     * @param qry The `qry` parameter in the `setQuery` method is a String that represents the SQL query that you want to
     * set for the MySQL query builder.
     * @return The `MySQLQueryBuilder` object is being returned.
     */
    public MySQLQueryBuilder setQuery(String qry) {
        this.query = qry;
        return this;
    }

    /**
     * The `addParameter` function in a MySQL query builder class adds a parameter to the list of parameters and returns
     * the instance of the query builder for method chaining.
     *
     * @param obj The `addParameter` method in the `MySQLQueryBuilder` class is used to add a parameter to the list of
     * parameters stored in the `params` field of the `MySQLQueryBuilder` instance. The method takes an `Object` as a
     * parameter and adds it to the list of parameters.
     * @return The `MySQLQueryBuilder` object is being returned.
     */
    public MySQLQueryBuilder addParameter(Object obj) {
        this.params.add(obj);
        return this;
    }

    /**
     * The `addParameters` method in a MySQL query builder class adds a collection of objects as parameters and returns the
     * instance for method chaining.
     *
     * @param objects The `addParameters` method in the `MySQLQueryBuilder` class takes a `Collection` of `Object` as
     * input. It adds all the elements in the input `Collection` to the `params` list of the `MySQLQueryBuilder` instance
     * and then returns the updated `MySQLQueryBuilder` instance.
     * @return The `MySQLQueryBuilder` object is being returned.
     */
    public MySQLQueryBuilder addParameters(Collection<Object> objects) {
        this.params.addAll(objects);
        return this;
    }
}
