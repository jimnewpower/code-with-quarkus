package org.acme.database;

import org.eclipse.microprofile.config.ConfigProvider;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    //    @ConfigProperty(name = "quarkus.datasource.db-kind")
    String dbType;

    //    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    String url;

    //    @ConfigProperty(name = "quarkus.datasource.username")
    String user;

    //    @ConfigProperty(name = "quarkus.datasource.password")
    String password;

    public Database() {
        this.dbType = ConfigProvider.getConfig().getValue("quarkus.datasource.db-kind", String.class);
        this.url = ConfigProvider.getConfig().getValue("quarkus.datasource.jdbc.url", String.class);
        this.user = ConfigProvider.getConfig().getValue("quarkus.datasource.username", String.class);
        this.password = ConfigProvider.getConfig().getValue("quarkus.datasource.password", String.class);
    }

    public java.sql.Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public String getDbType() {
        return dbType;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
