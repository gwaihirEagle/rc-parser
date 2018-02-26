import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
     * For management of inputted name which cannot be found in collection
     *
     */
    public class NamePersonNotFoundException extends Exception {

    /**
     * for logging purposes - to be able to distinguish levels of debug/error messages
     */
    private static final Logger log = LogManager.getRootLogger();

        public NamePersonNotFoundException(String inputName) {
            super("Name not found in the collection, for input: " + inputName);
            log.info("Name not found in the collection, for input: " + inputName);

        }


    }
