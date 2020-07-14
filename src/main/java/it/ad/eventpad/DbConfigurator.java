    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.sql.DataSource;

/**
 *
 * @author alfonsodomenici
 */
@DataSourceDefinition(
        className = DbConfigurator.MARIADB_CLASS_NAME,
        name = DbConfigurator.DS_JNDI_NAME,
        serverName = DbConfigurator.MARIADB_HOST,
        portNumber = DbConfigurator.MARIADB_PORT,
        user = DbConfigurator.MARIADB_USR,
        password = DbConfigurator.MARIADB_USER_PWD,
        databaseName = DbConfigurator.MARIADB_DATABASE_NAME,
        properties = {"validate-on-match=true", "background-validation=false"}
)
@Singleton()
@Startup
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DbConfigurator {

    public static final String MARIADB_HOST = "localhost";
    public static final int MARIADB_PORT = 3306;
    public static final String MARIADB_PROTOCOL = "tcp";
    public static final String MARIADB_USR = "eventpad";
    public static final String MARIADB_USER_PWD = "eventpad";
    public static final String MARIADB_DATABASE_NAME = "eventpad";
    public static final String MARIADB_CLASS_NAME = "org.mariadb.jdbc.MariaDbDataSource";
    public static final String DS_JNDI_NAME = "java:global/jdbc/eventpad";

    @Resource(lookup = DS_JNDI_NAME)
    private DataSource pw;

    private String jdbcBaseUrl;

    @Inject
    Logger LOG;
    
    @PostConstruct
    public void init() {
        LOG.log(Level.INFO, "----------------------- Init DbConfiguration-----------------------");
        jdbcBaseUrl = "jdbc:mariadb://" + MARIADB_HOST + ":" + MARIADB_PORT + "/";
        checkDasource();
        //migrate();
        LOG.log(Level.INFO, "----------------------- End DbConfiguration-----------------------");
    }

    private void checkDasource() {
        try ( Connection connection = pw.getConnection()) {
            System.out.println(
                    connection.getMetaData().getDatabaseProductName() + "-"
                    + connection.getCatalog()
            );
            System.out.println("---------------- check datasource ok -------------------");
        } catch (SQLException e) {
            LOG.log(Level.ERROR, e.getMessage());
        }
    }

//    public boolean migrate() {
//        Flyway flyway = Flyway
//                .configure()
//                .dataSource(jdbcBaseUrl + MARIADB_DATABASE_NAME, MARIADB_USR, MARIADB_USER_PWD)
//                .baselineOnMigrate(true)
//                .load();
//        int result = flyway.migrate();
//        System.out.println("-------------------------result migrate------------- " + result);
//        return result > 0;
//    }

}
