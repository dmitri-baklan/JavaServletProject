package periodicals.model.connection;

import org.apache.commons.dbcp.BasicDataSource;
import periodicals.util.DBProperty;
import javax.sql.DataSource;


public class ConnectionDB {

    private static DataSource dataSource;

    public static synchronized DataSource getDataSource(){
        DBProperty property = DBProperty.getDbProperty();
        if(dataSource == null){
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName(property.getProperty("db.main.driver"));
            basicDataSource.setUrl(String.format("%s://localhost:%s/%s",property.getProperty("db.main.url"), property.getProperty("db.main.port"), property.getProperty("db.main.name")));
            basicDataSource.setUsername(property.getProperty("db.main.username"));
            basicDataSource.setPassword(property.getProperty("db.main.password"));
            basicDataSource.setMaxOpenPreparedStatements(100);
            basicDataSource.setMinIdle(5);
            basicDataSource.setMaxIdle(10);
            dataSource = basicDataSource;
        }
        return dataSource;
    }
}
