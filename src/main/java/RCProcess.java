import java.util.List;

/**
 * Main business logic control
 */
public class RCProcess {

    public static void main(String[] args) {
        List<Person> arp;
        CSVFileReader fileReader = new CSVFileReader();

        arp = fileReader.readAllItems();
        PersonEvidence personEvidence = new PersonEvidence(arp);
        personEvidence.sortPersonsByBirthDate();
        personEvidence.showMenu();
    }

}
