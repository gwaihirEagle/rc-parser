import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * For management of inputted birthday year which cannot be found in collection
 *
 */
public class YearNotFoundException extends Exception {

    /**
     * for logging purposes - to be able to distinguish levels of debug/error messages
     */
    private static final Logger log = LogManager.getRootLogger();

    public YearNotFoundException(String inputYear) {
        super("Year not found in the collection, for input: " + inputYear);
        log.info("Year not found in the collection, for input: " + inputYear);

    }

}