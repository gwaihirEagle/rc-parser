import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Takes care about the situation when LocalDate is created with the day/month/year out of allowed limits
 * For Example for case: 31.4.1936 (when maximum for April is 30.4.
 */
public class RcDateOutOfLimitsException extends Exception {

    /**
     * for logging purposes - to be able to distinguish levels of debug/error messages
     */
    private static final Logger log = LogManager.getRootLogger();

    public RcDateOutOfLimitsException(String input) {
        super("Date fromat: day/month/year is out of limits, for input: " + input);
        log.info("Date fromat: day/month/year is out of limits, for input: " + input);
    }

}
