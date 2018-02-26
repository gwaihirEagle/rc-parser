import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * For management of inputted surname which cannot be found in collection
 *
 */
public class SurNameNotFoundException extends Exception {

        /**
         * for logging purposes - to be able to distinguish levels of debug/error messages
         */
        private static final Logger log = LogManager.getRootLogger();

        public SurNameNotFoundException(String inputSurName) {
            super("Surname not found in the collection, for input: " + inputSurName);
            log.info("Surname not found in the collection, for input: " + inputSurName);

        }
}
