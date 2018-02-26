import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Comparator;

/**
 * Represents a person with name, surname, rc, gender, parsed and checked DateTime
 */
public class Person implements Comparable {

    /**
     * for logging purposes - to be able to distinguish levels of debug/error messages
     */
    private static final Logger log = LogManager.getRootLogger();

    /**
     * Name of person
     */
    private String name;

    /**
     * Surname of person
     */
    private String surname;


    /**
     * Recognition if WOMAN/MAN
     */
    private GenderEnum gender;

    /**
     * rc id in a format: yymmdd/aaa(a)
     */
    private String rcId;

    /**
     * Checked and converted birthDate
     */
    private LocalDate birthDate = null;


    public Person(String name, String surn, GenderEnum genderIn, int day, int month, int year, String rcIn) {
        this.name = name;
        this.surname = surn;
        gender = genderIn;
        this.resolveDate(year, month, day);
        this.rcId = rcIn;

    }

    /**
     * Creates new instance LocalDate + uses creation for testing boundaries day, month
     * @param year
     * @param month
     * @param day
     */
    public void resolveDate(int year, int month, int day) {
        try {
            this.birthDate = LocalDate.of(year, month, day);
            log.debug("" + DateBirthParser.class.getName() + "read parsedBirthDay: ");
        } catch (DateTimeException e) {
            log.error("Wrong format/interval limit of day/month/year -> inputted: " + "processed: " +
                    day + "." + month + "." + year);
            System.exit(6);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + gender +
                ", rcId='" + rcId + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    /**
     * Checks equality of two Persons according to name, surname and birth date in LocalDate
     * @param o
     * @return
     */
    public boolean equals(Object o) {
        if (o == this) {
            return(true);
        }

        if (!(o instanceof Person)) {
            return(false);
        }

        Person person = (Person) o;

        if (!(this.name.equals(person.name))) {
            return(false);
        }

        if (!(this.surname.equals(person.surname))) {
            return(false);
        }

        if (birthDate.compareTo(person.birthDate) == 0) {
            return(true);
        } else {
            return(false);
        }

    }

    /**
     * Natural sort according to birthday
     * @param o
     * @return
     */
    public int compareTo(Object o) {
        Person person = (Person) o;

        LocalDate pb1 = this.birthDate;
        LocalDate pb2 = ((Person) o).birthDate;

        log.debug("Compare to: "+pb1.compareTo(pb2));
        return(pb1.compareTo(pb2));
    }


    /**
     * Comparator to sort by surname
     */
    public static Comparator<Person> SurnameComparator = new Comparator<Person>() {

        public int compare(Person p1, Person p2) {
            String personSurName1 = p1.getSurname().toUpperCase();
            String personSurName2 = p2.getSurname().toUpperCase();

            //ascending order
            return personSurName1.compareTo(personSurName2);

            //descending order
            //return personSurName2.compareTo(personSurName1);

        }};


    /**
     * Comparator to sort by name
     */
    public static Comparator<Person> NameComparator = new Comparator<Person>() {

        public int compare(Person p1, Person p2) {
            String personName1 = p1.getName().toUpperCase();
            String personName2 = p2.getName().toUpperCase();

            //ascending order
            return personName1.compareTo(personName2);

            //descending order
            //return personName2.compareTo(personName1);
        }};




    ////////////// getters


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public String getRcId() {
        return rcId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
