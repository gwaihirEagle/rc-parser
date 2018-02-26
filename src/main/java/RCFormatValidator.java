import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.NoSuchElementException;

import java.util.StringTokenizer;

/**
 * Class checking presence of '/', only number digits in rc, number of digits in preffix and suffix , for 10 digits mod 11
 */
public class RCFormatValidator {
    /**
     * for logging purposes - to be able to distinguish levels of debug/error messages
     */
    private static final Logger log = LogManager.getRootLogger();

    /**
     * Delimiting char in rc - which splits rc to prefix and suffix
      */
    final private String DELIMITER = "/";

    /**
     * Expected size of prefix: yymmdd .. everything before '/' in rc
     */
    final private int PREFSIZE = 6;

    /**
     * Expected minimum size of suffix: aaa (before 1954) .. everything after '/' in rc
     */
    final private int SUFFSIZEMIN = 3;

    /**
     * Expected maximum size of suffix: aaaa (after 1954) .. everything after '/' in rc
     */
    final private int SUFFSIZEMAX = 4;

    /**
     * Inputted rc in expected format: 'yymmdd/aaa(b)' to be checked
     */
    private String rcString = "";


    /**
     * Parsed prefix: 'yymmdd' part of the rc
     */
    private String prefix = "";

    /**
     * Parsed suffix: 'aaa(b)' part of the rc
     */
    private String suffix = "";

    /**
     * Object to save the parsed and checked birth: day, month, year + for logical test if day, month within boundaries
     */
    private DateBirthParser dateOfBirth;


    public RCFormatValidator() {

    }

    public RCFormatValidator(String rcIn) {
        this.rcString = rcIn;
    }

    /**
     * Checks presence of '/', only number digits in rc, count of digits in preffix and suffix ,
     * for 10 digits checks crc mod 11
     * calls dateOfBirth checks to logically validate day, month
     * @param rc .. inputted rc string to be checked: expect: 820405/0604
     * @return .. true .. if all tests passed / false if any test method fails
     */
    public boolean runTests(String rc) {

        this.rcString = rc;

        log.debug("\n ----Initiating test for input: "+this.rcString+"\n");

        boolean testDelimiter = findPrefSuff();
        log.debug("findPrefSuff>> Looking for '/' delimiter: " + testDelimiter);
        if (!testDelimiter) {
            log.error("For input: "+this.rcString+"Going exit.. with code:1");
            return false; //System.exit(1);
        }

        savePrefSuff();
        //        System.out.println("getPrefix>> "+getPrefix());
        //        System.out.println("getSuffix>> "+getSuffix());

        boolean testDigits = checkOnlyNumberDigits();
        log.debug("checkOnlyNumberDigits>> Expected[0-9]: " + testDigits);

        if (!testDigits) {
            log.error("For input: "+this.rcString+"Going exit.. with code:2");
            return false; //System.exit(2);
        }

        boolean testNoPref = checkPrefixNoDigits();
        log.debug("checkPrefixNoDigits>> Expected number digits xxxxxx: " + checkPrefixNoDigits());
        if (!testNoPref) {
            log.error("For input: "+this.rcString+"Going exit.. with code:3");
            return false; //System.exit(3);
        }

        boolean testNoSuff = checkSuffixNoDigits();
        log.debug("checkSuffixNoDigits>> Expected number digits yyy(y): " + checkSuffixNoDigits());
        if (!testNoSuff) {
            log.error("For input: "+this.rcString+"Going exit.. with code:4");
            return false; //System.exit(4);
        }


        if (getSuffix().length() == 4) { // after 1954 when there are 4 suffix digits including crc
            boolean testModEleven = checkTenModEleven();
            log.debug("checkTenModEleven>> After 1954 I have to check crc in suffix mod 11: " + checkTenModEleven());
            if (!testModEleven) {
                log.error("For input: "+this.rcString+"Going exit.. with code:6");
                return false; //System.exit(6);
            }
        }

        // all tests passed - creating DateBirthParser object to test and save logical representation of day/month/year
        // Object is created just for test reasons --> name, surname: Testing .. will not be saved..
        this.dateOfBirth = new DateBirthParser();

        try {
            this.dateOfBirth.parseRcDate("Testing", "Testing", this.getPrefix(), this.getSuffix());
            return(true);
        }
        catch(RcDateOutOfLimitsException e) {
            return(false);
        }

    }


    /**
     * Tries to find '/' delimiter in rc
     * @return .. true .. if '/' found
     */
    public boolean findPrefSuff() {
        // the index of the first occurrence of the specified substring, or -1 if there is no such occurrence.
        int position = this.rcString.indexOf(DELIMITER);

        if (position == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Parses rc String to this.prefix and this.suffix according to delimiter '/'
     */
    public void savePrefSuff() {

        try {
            StringTokenizer stringTokenizer = new StringTokenizer(this.rcString,DELIMITER);

            this.prefix=stringTokenizer.nextToken();
            this.suffix=stringTokenizer.nextToken();
        }
        catch(NullPointerException e) {
            log.error("Required delimiter: '"+DELIMITER+"' not found");
            //e.printStackTrace();
        }
        catch(NoSuchElementException e) {
            log.error("Required delimiter: '"+DELIMITER+"' not found");
            //e.printStackTrace();

        }

    }

    /**
     * Checks if there are only numbers in the digits of prefix/suffix
     * * Suppose prefix and suffix already splitted to attributes
     * @return .. true .. if there are only number digits in both suffix and prefix of rc
     */
    public boolean checkOnlyNumberDigits() {
        if (this.checkIsOnlyDigit(prefix) && this.checkIsOnlyDigit(suffix)) {
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Checks if there are only number digits in inputted string
     * @param str .. inputted string to be checked
     * @return .. true if there are only number digits, false if there is any nonNumber digit in inputted String
     */
    public boolean checkIsOnlyDigit(String str) {
        boolean isDigit = true;
        int i = 0;

        while ((isDigit == true) && i<str.length()) {
            isDigit = Character.isDigit(str.charAt(i));
            i++;
        }
        return(isDigit);
    }

    /**
     * Checks if there are only 6 chars in prefix - supposing xxxxxx chars
     * @return .. true if there are exactly 6 chars in prefix
     */
    public boolean checkPrefixNoDigits() {
        if (this.prefix.length() == PREFSIZE) {
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Checks if there are only only 3..4 chars in suffix - supposing yyy(y) chars
     * rc number was issued before 1953 .. 3 digits
     * rc number was issued after 1953 .. 4 digits
     * @return .. true if there are 3-4 digits
     */
    public boolean checkSuffixNoDigits() {
        if ((this.suffix.length() >= SUFFSIZEMIN) && (this.suffix.length() <= SUFFSIZEMAX)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if after 1953 - 10 digits rc numbers -- number mod 11 = last digit .. mod 0..9 (0 if result == 10)
     * @return .. true if last digit = rc(0:9) mod 11
     */
    public boolean checkTenModEleven() {
        int number;
        int crc;
        int checksum;

        String pom;

        if (this.suffix.length() == 4) {
            pom = this.prefix + this.suffix.substring(0,3); // System.out.println("pom: " + pom);
            number = Integer.parseInt(pom); //System.out.println("int: " + number);
            crc = Integer.parseInt(""+this.suffix.charAt(3)); //System.out.println("crc: " + crc);
            checksum = number % 11; //System.out.println("checksum: " + checksum);
            if (checksum != 10) {
                if (crc == checksum) {
                    return true;
                }
            }
            else {
                if (crc == 0) {
                    return true;
                }
            }
        }

        return false;

    }

    ////////// Getter Setter block

    public String getSuffix() {
        return suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getRcString() {
        return rcString;
    }

    public DateBirthParser getDateOfBirth() {
        return dateOfBirth;
    }


}
