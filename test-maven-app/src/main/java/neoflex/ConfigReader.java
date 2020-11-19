package neoflex;
 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
public class ConfigReader{

    private String result = "";
	private InputStream inputStream;
    private static final Logger logger = LogManager.getLogger();

	public String getValue(String confValue) throws IOException {
   
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            result = prop.getProperty(confValue);

		} catch (Exception e) {
			logger.info("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
}