import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DateTimeException;
import java.time.LocalDate;


/**
 * Parses rc String to: prefix and suffix + checks logical representation of day, month
 * if day is within allowed levels [1..28,30,31] accordign to chosen month
 * if month is within levels [1..12]
 *
 * */
public class DateBirthParser {

    /**
     * for logging purposes - to be able to distinguish levels of debug/error messages
     */
    private static final Logger log = LogManager.getRootLogger();

    /**
     * Limiting level in month to be able to recognize a woman, if month > 50
     */
    final private int LIMITMONTHWOMAN = 50;

    /**
     * When same birthdays and suffix --> +20 to month information
     */
    final private int LIMITMONTHEQUAL = 20;

    /**
     * Prefix of rc -- all chars before '/' in format yymmdd
     *
     */
    private String ddMmYy = "";

    /**
     * Suffix information from rc - everything after '/'
     */
    private String suffixRC = "";

    
    /**
     * to check logical validity of day, month within boundaries
     */
    private LocalDate parsedBirthDay = null;


    /**
     * Parses class attribute: ddMmYy to get isolated atributes: day, month, year and conduct logical tests
     * if day,month are within allowed boundaries
     * @param name .. name of the person which will be created
     * @param surName .. surName of the person which will be created
     * @return Person
     */
    public Person parseRcDate(String name, String surName, String pref, String suff) throws RcDateOutOfLimitsException  {

        int day = 0;
        int month = 0;
        int year = 0;
        GenderEnum gender; // Parsed gender information if month >50 -- I know its WOMAN
        String rc; // yymmdd/aaa(a)

        this.ddMmYy = pref;
        this.suffixRC = suff;

        rc = ""+this.ddMmYy+"/"+this.suffixRC;

        gender = this.resolveGender();
        month = this.resolveMonth();
        day = this.resolveDay();
        year = this.resolveYear();

        if (this.testDate(year,month,day)) {
            return(new Person(name, surName, gender, day, month, year, rc));
        }
        else {
            throw new RcDateOutOfLimitsException(""+year+" "+month+" "+day);

        }


    }

    /**
     * Resolves gender from month: 'yymmdd'
     * >50 for woman,
     * > 50+20 (+20 when same day and suffix for more persons)
     * @return .. "woman" if  mm in yymmdd >50
     * */
    public GenderEnum resolveGender() {
        int tempMonth = Integer.valueOf(this.ddMmYy.substring(2, 4));
            if (tempMonth > LIMITMONTHWOMAN) {
                return(GenderEnum.FEMALE);
            } else {
                return(GenderEnum.MALE);
            }
    }

    /**
     * Determines month according to the algorithm
     * month can be >50 for women
     * month can be maximum >50+20 in general for same IDs..
     * in general: month [1 .. 12+20+50]
     * @return .. parsed and processed month
     */
    public int resolveMonth() {
        int tempMonth = Integer.valueOf(this.ddMmYy.substring(2, 4));

        if (tempMonth > LIMITMONTHWOMAN) {
            tempMonth = tempMonth - LIMITMONTHWOMAN;
        }

        if (tempMonth > LIMITMONTHEQUAL) {
            return(tempMonth - LIMITMONTHEQUAL);
        }
        else {
            return(tempMonth);
        }
    }


    /**
     * Parses input string format 'yymmdd'
     * @return .. parsed and processed day
      */
    public int resolveDay() {
        return(Integer.valueOf(this.ddMmYy.substring(4, 6)));
    }


    /**
     * Resolves year from string value yymmdd
     * and adds 1900/2000 according to known rule rc: until 1954 6+3 digits after 6+4 digits
     * @return .. parsed and processed year
     * */
    public int resolveYear() {
        int year = 0;

        year = Integer.valueOf(this.ddMmYy.substring(0, 2));

        if ( (year < 54) && (this.suffixRC.length() == 3) ) {
            return(year+1900);
        }
        else {
            if ((year >= 54) && (year <= 99)) {
                return(year+1900);
            }

            else {
                return(year+2000);
            }
        }

    }

    /**
     * Creates parsedBirthDay structure and checks if day,month, year within allowed boundaries + checks leap years 02
     * Test logical representation of day 1..31
     * Test logical representation of month 1..12
     * @return .. true .. if day/month/year within allowed boundaries
     **/
    public boolean testDate(int year, int month, int day) {

        try {
            parsedBirthDay = LocalDate.of(year,month ,day );
            log.debug(""+DateBirthParser.class.getName()+"read parsedBirthDay: ");
            return(true);
        }
        catch (DateTimeException e){
            log.error("Wrong format/interval limit of day/month/year -> inputted: " + this.ddMmYy + ", processed: " +
                    day + "." + month + "." + year);
            System.exit(6);
            return(false);
        }
    }

    @Override
    public String toString() {
        return "DateBirthParser{" +
                "ddMmYy='" + ddMmYy + '\'' +
                ", suffixRC='" + suffixRC + '\'' +
                ", parsedBirthDay=" + parsedBirthDay +
                '}';
    }


    /////////// Getters/Setters methods


    public String getDdMmYy() {
        return ddMmYy;
    }

    public String getSuffixRC() {
        return suffixRC;
    }

    public LocalDate getParsedBirthDay() {
        return parsedBirthDay;
    }

    public void setDdMmYy(String ddMmYy) {
        this.ddMmYy = ddMmYy;
    }

    public void setSuffixRC(String suffixRC) {
        this.suffixRC = suffixRC;
    }

}
