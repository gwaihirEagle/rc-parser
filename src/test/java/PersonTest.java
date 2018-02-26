import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    List<Person> arp;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        CSVFileReader fileReader = new CSVFileReader();
        arp = fileReader.readAllItems();
        PersonEvidence personEvidence = new PersonEvidence(arp);
        personEvidence.printArrayList();

    }

    @org.junit.jupiter.api.BeforeAll
    public static void beforeClass() {
        System.out.println("PersonTest ----> \n ...Initializing tests");


    }

    @org.junit.jupiter.api.AfterAll
    public static void afterClass() {
        System.out.println("---- End of tests ----");
    }


    @Test
    void resolveDate() {
    }

    @Test
    void equals() {

        assertEquals(true, arp.get(0).equals(arp.get(0)) );
        assertEquals(false, arp.get(0).equals(arp.get(1)) );
        assertEquals(false, arp.get(0).equals(arp.get(2)) );
        assertEquals(false, arp.get(0).equals(arp.get(3)) );
        assertEquals(false, arp.get(0).equals(arp.get(4)) );
        assertEquals(false, arp.get(0).equals(arp.get(5)) );

    }

    @Test
    void compareTo() {

        assertEquals(0,arp.get(0).compareTo(arp.get(0))); // test comparison for sorting
//        assertEquals(-60,arp.get(0).compareTo(arp.get(1))); // test comparison for sorting
//        assertEquals(-61,arp.get(0).compareTo(arp.get(2))); // test comparison for sorting
//        assertEquals(-1,arp.get(0).compareTo(arp.get(3))); // test comparison for sorting
//        assertEquals(-3,arp.get(0).compareTo(arp.get(4))); // test comparison for sorting
//        assertEquals(3,arp.get(0).compareTo(arp.get(5))); // test comparison for sorting
//        assertEquals(0,arp.get(1).compareTo(arp.get(5))); // test comparison for sorting

    }
}