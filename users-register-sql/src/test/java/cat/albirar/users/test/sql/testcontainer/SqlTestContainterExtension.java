/*
 * This file is part of "albirar users-register-sql".
 * 
 * "albirar users-register-sql" is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * "albirar users-register-sql" is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with "albirar users-register-sql" source
 * code. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 * Copyright (C) 2020 Octavi Fornés
 */
package cat.albirar.users.test.sql.testcontainer;

import java.util.Optional;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import cat.albirar.users.repos.sql.config.PropertiesSql;
import cat.albirar.users.test.sql.UsersRegisterSqlTestConfig;

/**
 * The extension to start a sql database container.
 * 
 * @author Octavi Forn&eacute;s &lt;<a href="mailto:ofornes@albirar.cat">ofornes@albirar.cat</a>&gt;
 * @since 1.0.0
 */
public class SqlTestContainterExtension implements BeforeAllCallback, AfterAllCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlTestContainterExtension.class);

    @SuppressWarnings("rawtypes")
    private static JdbcDatabaseContainer container;

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Optional<String> conf;
        
        conf = context.getConfigurationParameter(UsersRegisterSqlTestConfig.SQL_TEST_DBTYPE);
        
        if(conf.orElseGet(()-> "mysql").equals("mysql")) {
            LOGGER.debug("Start SQL test containers albirar extension for MYSQL...");
            if(container == null) {
                container = new MySQLContainer<>();
            } else {
                if(container.isRunning()) {
                    container.stop();
                }
            }
        }
        else {
            LOGGER.debug("Start SQL test containers albirar extension for POSTGRESQL...");
            if(container == null) {
                container = new PostgreSQLContainer<>();
            } else {
                if(container.isRunning()) {
                    container.stop();
                }
            }
        }

        container.start();

        System.setProperty(PropertiesSql.SQL_DATASOURCE_DRIVER, container.getDriverClassName());
        System.setProperty(PropertiesSql.SQL_DATASOURCE_URL, container.getJdbcUrl());
        System.setProperty(PropertiesSql.SQL_DATASOURCE_USERNAME, container.getUsername());
        System.setProperty(PropertiesSql.SQL_DATASOURCE_PASSWORD, container.getPassword());
        LOGGER.debug("SQL testcontainer albirar extension STARTED!");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        LOGGER.debug("End SQL testcontainers albirar extension...");
        if(container != null && container.isRunning()) {
            LOGGER.debug("Stopping SQL testcontainers albirar extension...");
            container.stop();
            LOGGER.debug("SQL testcontainers albirar extension stopped");
            container = null;
        }
    }
}
