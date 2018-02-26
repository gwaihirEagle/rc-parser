import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * For management of inputted rc which cannot be found in collection
 *
 */
public class RcNotFoundException extends Exception {
    private static final Logger log = LogManager.getRootLogger();

        public RcNotFoundException(String searchRc) {
            super("Searched RC cannot be found in collection");
            log.info("rc not found in the collection, for input: " + searchRc);
        }
}