package de.cyzetlc.roadsystem.service.database;

public interface IDatabaseCredentials {
    String getUsername();

    String getPassword();

    String getHostname();

    String getDatabase();

    int getPort();

    int getPoolSize();
}
