import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads csv data from input file. Expected format: name;surname;rc
 * Example: marek;novotny;820504/0405
 *
 */
public class CSVFileReader {

    /**
     * for logging purposes - to be able to distinguish levels of debug/error messages
     */
    private static final Logger log = LogManager.getRootLogger();

    /**
     * Name of the file which will be read
     */
    final private String FILEINPUTPATH = "inRC.csv";

    /**
     * Delimiting char inside of the .CSV file
     */
    final private String CSVDELIM = ";";


    /**
     * Parses input csv file in expected format 'name;surname;yymmdd/aaa(a)'
     *
     * @return collection of Person objects
     */
    public List<Person> readAllItems() {
        BufferedReader fileBuffPers;
        String row;

        Person person;
        DateBirthParser dateBirthParser;
        RCFormatValidator validator = new RCFormatValidator();

        List<Person> arp = new ArrayList<>(); // from Java 7 can be this way without ArrayList<Person>

        try {
            log.debug("Reading input file: " + FILEINPUTPATH);
            fileBuffPers = new BufferedReader(new FileReader(FILEINPUTPATH));

            // Todo - compareTo -> find better way to solve -- why must be there??due to cr lf??
            while (((row = fileBuffPers.readLine()) != null) && (row.compareTo("") != 0)) {
                String[] rowRead = row.split(CSVDELIM);
                // test (row.compareTo(csvDelimiter) because of possibility of blank row at the end of file
                log.debug(" row: " + "name: " + rowRead[0] + ", surname: " + rowRead[1] + ", rc: " + rowRead[2]);

                if (validator.runTests(rowRead[2])) {
                    dateBirthParser = new DateBirthParser();
                    try {
                        person = dateBirthParser.parseRcDate(rowRead[0], rowRead[1], validator.getPrefix(), validator.getSuffix());
                        arp.add(person);
                        log.debug(person);
                    } catch (RcDateOutOfLimitsException e) {
                        log.error("Wrong date format (probably not within boundaries)");
                    }

                } else {
                    log.error("error while reading file and processing..");
                }

                //arp.add(new Person(rowRead[0], rowRead[1], rowRead[2]));

            }
            fileBuffPers.close();
            return (arp);

        } catch (IOException e) {
            log.error(" Error reading file: " + FILEINPUTPATH + " ,file has to be in actual directory");
            return (null);
        }
    }


    public void saveCollectionToCSV(List<Person> arp) {

        BufferedWriter fileBuffPers;

        try {
            log.info("Writing whole collection to output file: " + FILEINPUTPATH);
            fileBuffPers = new BufferedWriter(new FileWriter(FILEINPUTPATH));

            for (Person person : arp) {
                fileBuffPers.write("" + person.getName() + ";" + person.getSurname() + ";" + person.getRcId() + "\n");
            }

            fileBuffPers.close();

        } catch (IOException e) {
            log.error(" Error writing file: " + FILEINPUTPATH + " ,file has to be in actual directory");
        }
    }

}

