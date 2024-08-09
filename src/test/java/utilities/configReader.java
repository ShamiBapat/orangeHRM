package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configReader {

    public static Properties prop = new Properties();

    /**
     * This method is used to load the properties from congig.properties file
     * @return it returns Properties prop object
     */

    public static Properties init_prop() {

        Properties prop = new Properties();
        try {

            FileInputStream ip = new FileInputStream("./src/test/resources/config.properties");
            prop.load(ip);

        }
        catch(IOException ignored)
        {

        }
        return prop;
    }




}
