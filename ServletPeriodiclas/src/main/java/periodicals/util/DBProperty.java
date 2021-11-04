package periodicals.util;


import periodicals.exception.DataBaseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBProperty {

    private static final String FILE_PATH = "statements.properties";

    private static DBProperty dbProperty;

    private Properties properties;

    private DBProperty(){

        properties = new Properties();

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_PATH)){
            properties.load(inputStream);
        }catch (IOException ex){
            throw new DataBaseException();
        }
    }

    public static synchronized DBProperty getDbProperty(){
        return dbProperty == null ? new DBProperty() : dbProperty;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
