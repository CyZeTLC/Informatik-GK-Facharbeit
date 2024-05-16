package de.cyzetlc.roadsystem.service.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class QueryHandler implements IMySQLExtension {
    private final HikariDataSource hikari;

    public QueryHandler(IDatabaseCredentials credentials) {
        HikariConfig config = new HikariConfig();
        config.setUsername(credentials.getUsername());
        config.setPassword(credentials.getPassword());
        config.setMaximumPoolSize(credentials.getPoolSize());
        config.setConnectionTimeout(6000L);

        String jdbcConStr = String.format("jdbc:mysql://%s:%d/%s?useSSL=false&verifyServerCertificate=false&characterEncoding=latin1",
                credentials.getHostname(), credentials.getPort(), credentials.getDatabase());
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(jdbcConStr);

        this.hikari = new HikariDataSource(config);
    }

    public MySQLQueryBuilder createBuilder(String qry) {
        return new MySQLQueryBuilder(this).setQuery(qry);
    }

    @Override
    public Connection getNewConnection() {
        try {
            return this.hikari.getConnection();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public void closeConnection(Connection connection) {
        this.hikari.evictConnection(connection);
    }

    @Override
    public void stop() {
        if (!this.hikari.isClosed()) {
            this.hikari.close();
        }
    }
}
