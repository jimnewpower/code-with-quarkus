package org.acme.resources;

import org.acme.database.Database;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/tables")
public class TablesResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String tables() {
        StringBuilder messageBuilder = new StringBuilder();
        Database database = new Database();
        try {
            Connection connection = database.connect();
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};

            messageBuilder.append("Database type: ").append(database.getDbType()).append("\n");
            messageBuilder.append("Database URL: ").append(database.getUrl()).append("\n");
            messageBuilder.append("Username: ").append(database.getUser()).append("\n");

            messageBuilder.append("Database: ").append(metaData.getDatabaseProductName()).append("\n");
            messageBuilder.append("Driver: ").append(metaData.getDriverName()).append("\n");
            messageBuilder.append("Version: ").append(metaData.getDriverVersion()).append("\n");

            messageBuilder.append("Tables:").append("\n");
            ResultSet tables = metaData.getTables(null, null, "%", types);
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                messageBuilder.append(String.format("\t%s\n", tableName));
            }
        } catch (SQLException e) {
            messageBuilder.append(e.getMessage()).append("\n");
            messageBuilder
                    .append("quarkus.datasource.db-kind=")
                    .append(ConfigProvider.getConfig().getValue("quarkus.datasource.db-kind", String.class))
                    .append("\n");
            messageBuilder
                    .append("quarkus.datasource.jdbc.url=")
                    .append(ConfigProvider.getConfig().getValue("quarkus.datasource.jdbc.url", String.class))
                    .append("\n");
            messageBuilder
                    .append("quarkus.datasource.username=")
                    .append(ConfigProvider.getConfig().getValue("quarkus.datasource.username", String.class))
                    .append("\n");
        }
        return messageBuilder.toString();
    }

}
