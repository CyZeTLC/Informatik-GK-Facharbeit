package de.cyzetlc.roadsystem.utils.mysql;

import de.cyzetlc.roadsystem.service.database.IDatabaseCredentials;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MySQLCredentials implements IDatabaseCredentials {
    public String username;
    public String password;
    public String hostname;
    public String database;
    public int port;
    public int poolSize;
}
