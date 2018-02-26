import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.*;

/**
 * Manages the collection of Persons, namely: search according to input string rc/ print searched
 * ToDo - menu present logic in different class - here to make it quickly
 *
 */
public class PersonEvidence {

    /**
     * String which will end the simple user interface from system.in
     */
    final private String ENDINPUT = "q";

    /**
     * for logging purposes - to be able to distinguish levels of debug/error messages
     */
    private static final Logger log = LogManager.getRootLogger();

    final private String NA = "NA";


    /**
     * Collection of Persons
     */
    private List<Person> arp;



    public PersonEvidence() {
    }


    public PersonEvidence(List<Person> arp) {
        this.arp = arp;
    }

    /**
     * Shows the main menu
     **/
    public void showMenu() {
        String userChoice = NA; // user choice inputted from console

        log.info("====MENU====");
        log.info("(1) Create new person");
        log.info("(2) Search for a person");
        log.info("(3) Print whole collection");
        log.info("(q) Quit program");
        log.info("========");

        log.info(" Your choice: ");

        while(userChoice.compareTo("NA") == 0) {
            Scanner sc = new Scanner(System.in, "UTF-8");
            userChoice = sc.nextLine();
            switch (userChoice) {
                case "1":
                    this.readNewPersonFromConsole();
                    this.showMenu();
                    break;
                case "2":
                    showMenuSearch();
                    break;
                case "3":
                    this.showMenuSort();
                    this.showMenu(); // recursion
                    break;

                case "q":
                    log.info("Quit program..");
                    sc.close();
                    new CSVFileReader().saveCollectionToCSV(arp);
                    break;

                default:
                    log.info("Invalid selection - repeat selection: 1/2/3/q");
                    userChoice = "NA";
                    break;

            }
        }

    }


    /**
     * Shows the menu for search
     */
    public void showMenuSearch() {
        String userChoice = "NA"; // user choice inputted from console

        log.info("====MENU->Search====");
        log.info("(a) Search by name");
        log.info("(b) Search by surname");
        log.info("(c) Search by rc id");
        log.info("(d) Search by birthday year");
        log.info("(q) Quit program");
        log.info("========");
        log.info(" Your choice: ");

        while(userChoice.compareTo("NA") == 0) {
            Scanner sc = new Scanner(System.in, "UTF-8");
            userChoice = sc.nextLine();
            switch (userChoice) {
                case "a":
                    this.readNameFromConsole();
                    break;
                case "b":
                    this.readSurNameFromConsole();
                    break;
                case "c":
                    this.readRcFromConsole();
                    break;
                case "d":
                    this.readYearFromConsole();
                    break;
                case "q":
                    this.showMenu();
                    break;

                default:
                    log.info("Invalid selection - repeat selection: a/b/c/d/q");
                    userChoice = "NA";
                    break;

            }
        }

    }


    /**
     * Shows the menu for sort type
     */
    public void showMenuSort() {
        String userChoice = "NA"; // user choice inputted from console

        log.info("====MENU->Sort====");
        log.info("(a) Sort by birthday - (Comparable) ");
        log.info("(b) Sort by name - (Comparator) ");
        log.info("(c) Sort by surname - (Comparator) ");
        log.info("(q) Quit program");
        log.info("========");
        log.info(" Your choice: ");

        while(userChoice.compareTo("NA") == 0) {
            Scanner sc = new Scanner(System.in, "UTF-8");
            userChoice = sc.nextLine();
            switch (userChoice) {
                case "a":
                    this.sortPersonsByBirthDate();
                    this.printArrayList();
                    break;
                case "b":
                    this.sortPersonsByName();
                    this.printArrayList();
                    break;
                case "c":
                    this.sortPersonsBySurName();
                    this.printArrayList();
                    break;
                case "q":
                    this.showMenu();
                    break;

                default:
                    log.info("Invalid selection - repeat selection: a/b/c//q");
                    userChoice = "NA";
                    break;

            }
        }

    }



    /**
     * Reads surname from console and shows all found objects
     */
    public void readNewPersonFromConsole() {
        String inputName = ""; // name inputted from console
        String inputSurName = ""; // surname inputted from console
        String inputRc = ""; // rc inputted from console

        Scanner sc = new Scanner(System.in, "UTF-8");

        log.info("Input name:  ");
        inputName = sc.nextLine();
        log.info("Input surname:  ");
        inputSurName = sc.nextLine();
        log.info("Input rc [expected format:yymmdd/aaa(a)]:   ");
        inputRc = sc.nextLine();

        log.info("--Inputted: " + inputName + " " + inputSurName + " " + inputRc);

        this.addNewPerson(inputName,inputSurName,inputRc);
    }


    /**
     * Reads name from console and shows all found objects
     */
    public void readNameFromConsole() {
        String inputName = ""; // name inputted from console
        List<Person> arpSub = new ArrayList<>();

        Scanner sc = new Scanner(System.in, "UTF-8");
        this.printArrayList();

        while(inputName.compareTo(ENDINPUT) != 0) {
            log.info("Input searched name of the person:  "); //
            inputName = sc.nextLine();
            log.info("--Inputted: "+ inputName);
            try {
                arpSub = this.searchPersonName(inputName);
                log.info("Person(s) found: ");
                this.printArrayList(arpSub);
            }
            catch (NamePersonNotFoundException e) {
                ; // just message in the exception..
            }

            log.info("(Return to main menu by entering 'q'..)");
        }
        this.showMenu();

    }

    /**
     * Reads surname from console and shows all found objects
     */
    public void readSurNameFromConsole() {
        String inputSurName = ""; // name inputted from console
        List<Person> arpSub = new ArrayList<>();

        Scanner sc = new Scanner(System.in, "UTF-8");
        this.printArrayList();

        while(inputSurName.compareTo(ENDINPUT) != 0) {
            log.info("Input searched surname of the person:  ");
            inputSurName = sc.nextLine();
            log.info("--Inputted: "+ inputSurName);
            try {
                arpSub = this.searchPersonSurname(inputSurName);
                log.info("Person(s) found: ");
                this.printArrayList(arpSub);
            }
            catch (SurNameNotFoundException e) {
                ; // covered by output message in the exception
            }

            log.info("(Return to main menu by entering 'q'..)");
        }
        this.showMenu();

    }



    /**
     * Reads in a loop from console input for: rc to be searched in format yymmdd/aaa(a) until 'q'
     */
    public void readRcFromConsole() {

        String inputRc = ""; // rc inputted from console, expected format: yymmdd/aaa(a)

        Scanner sc = new Scanner(System.in, "UTF-8");
        this.printArrayList();
        log.info("Input searched RC [expected format: yymmdd/aaa(a): ");
        inputRc = sc.nextLine();
        while(inputRc.compareTo(ENDINPUT) != 0) {
            log.info("--Inputted: "+ inputRc);

            RCFormatValidator validator = new RCFormatValidator();
            DateBirthParser dateBirthParser = new DateBirthParser();
            if (validator.runTests(inputRc)) {

                    //log.debug(dateBirthParser.parseRcDate("test", "test", validator.getPrefix(), validator.getSuffix()));
                    log.info("");
                    try {
                        log.info("Person found: " + this.searchPersonRc(inputRc));
                    }
                    catch(RcNotFoundException e) {
                        log.info("Searched person with rc:"+inputRc+"not found in the collection");
                    }

            }
            else {
                log.error("Wrong RC inputted");
            }

            //this.printArrayList();
            log.info("(You will quit the input by writing 'q' + enter)");
            log.info("Input searched RC [expected format: yymmdd/aaa(a): ");
            inputRc = sc.nextLine();
        }

        this.showMenu();
    }



    /**
     * Reads birthday year from console and shows all found objects
     */
    public void readYearFromConsole() {
        String inputDate = ""; // date inputted from console
        List<Person> arpSub = new ArrayList<>();

        Scanner sc = new Scanner(System.in, "UTF-8");
        this.printArrayList();
        log.info("Input searched birthday year of the person:  ");
        inputDate = sc.nextLine();

        while(inputDate.compareTo(ENDINPUT) != 0) {
            log.info("--Inputted: "+ inputDate);
            try {
                arpSub = this.searchPersonYear(inputDate);
                log.info("Person(s) found: ");
                this.printArrayList(arpSub);
            }
            catch (YearNotFoundException e) {
                ; // covered by output message in the exception
            }

            log.info("(Return to main menu by entering 'q'..)");
            log.info("Input searched birthday year of the person:  ");
            inputDate = sc.nextLine();

        }
        this.showMenu();

    }

    /**
     * Natural collection sort by BirthDate
     *
     * */
    public void sortPersonsByBirthDate() {
        Collections.sort(this.arp);
    }

    /**
     * Sort collection by surname
     *
     * */
    public void sortPersonsBySurName() {
        Collections.sort(this.arp, Person.SurnameComparator);
    }

    /**
     * Sort collection by name
     *
     * */
    public void sortPersonsByName() {
        Collections.sort(this.arp, Person.NameComparator);
    }


    /**
     * Checks validity of RC -- if valid saves new person in the collection
     * @param searchName .. name of a new person
     * @param searchSurName .. surname of a new person
     * @param searchRc .. rc of a new person
     * @return true if rc is valid
     */
    public boolean addNewPerson(String searchName,String searchSurName, String searchRc  ) {
        Person person;

        DateBirthParser dateBirthParser = new DateBirthParser();
        RCFormatValidator validator = new RCFormatValidator();

        log.debug(" row: " + "name: " + searchName + ", surname: " + searchSurName + ", rc: " + searchRc);

        if (validator.runTests(searchRc)) {
            try {
                person = dateBirthParser.parseRcDate(searchName, searchSurName, validator.getPrefix(), validator.getSuffix());
                arp.add(person);
                log.info("Inputting new person to collection: " + person);
                return true;
            } catch (RcDateOutOfLimitsException e) {
                log.info("Wrong date format (probably rc date information not within allowed boundaries)");
                return false;
            }

        } else {
            log.info("inappropriate format of rc..");
            return false;
        }

    }


    /**
     * Search person according to his/her name in a whole collection
     * @param searchName ..name to be searched in the collection
     * @return .. collection of all persons who fullfill the searched pattern name
     * @throws NamePersonNotFoundException
     */
    private List<Person> searchPersonName(String searchName) throws NamePersonNotFoundException {
        List<Person> arpSub = new ArrayList<>(); // collection of all found persons

        for (Person person : arp) {
            if (person.getName().compareTo(searchName) == 0) {
                arpSub.add(person);

            }
        }

        // for cycle did not find person with rc in the collection
        if (arpSub.size()<1) {
            throw new NamePersonNotFoundException(searchName);
        }
        else {
            return arpSub;
        }

    }

    /**
     * Search person according to his/her surname in a whole collection
     * @param searchSurName ..name to be searched in the collection
     * @return .. collection of all persons who fullfill the searched pattern name
     * @throws SurNameNotFoundException thrown when not any object found
     */
    private List<Person> searchPersonSurname(String searchSurName) throws SurNameNotFoundException {
        List<Person> arpSub = new ArrayList<>(); // collection of all found persons

        for (Person person : arp) {
            if (person.getSurname().compareTo(searchSurName) == 0) {
                arpSub.add(person);

            }
        }

        // for cycle did not find person with rc in the collection
        if (arpSub.size()<1) {
            throw new SurNameNotFoundException(searchSurName);
        }
        else {
            return arpSub;
        }

    }

    /**
     * Searches person in the collection according to the rc, I suppose rc was already validated
     * @param searchRc .. searched rc in format: yymmdd/aaa(a)
     * @return
     */
    private Person searchPersonRc(String searchRc) throws RcNotFoundException {
            for (Person person : arp) {
                if (person.getRcId().compareTo(searchRc) == 0) {
                    return (person);
                }
            }

        // for cycle did not find person with rc in the collection
            throw new RcNotFoundException(searchRc);

    }

    /**
     * Searches person in the collection according to the birthday year
     * @param searchYear .. searched year in format: yyyy
     * @return subcollection of all found person
     * @throws YearNotFoundException
     */
    private List<Person> searchPersonYear(String searchYear) throws YearNotFoundException {
        List<Person> arpSub = new ArrayList<>(); // collection of all found persons

        for (Person person : arp) {
            if (person.getBirthDate().getYear() == Integer.parseInt(searchYear)) {
                arpSub.add(person);
            }
        }

        // for cycle did not find person with rc in the collection
        if (arpSub.size()<1) {
            throw new YearNotFoundException(searchYear);
        }
        else {
            return arpSub;
        }

    }

    /**
     * Print whole class atribute collection
     * */
    public void printArrayList() {
        log.info("--Printing the whole collection--");
        for (Person person : arp) {
            log.info(person);
        }
        log.info("----");
    }

    /**
     * Print subcollection - typically found persons according to chosen criteria
     * @param subArp .. subCollection of found persons
     */
    public void printArrayList(List<Person> subArp) {
        log.info("--Printing the whole collection--");
        for (Person person : subArp) {
            log.info(person);
        }
        log.info("----");

    }


    ////////// Getter,setter block

}
