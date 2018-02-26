import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.BeforeEach*;


class DateBirthParserTest {

    DateBirthParser dateBirthParser;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        dateBirthParser = new DateBirthParser();

    }

    @org.junit.jupiter.api.BeforeAll
    public static void beforeClass() {
        System.out.println("DateBirthParserTest ----> \n ...Initializing tests");

    }

    @org.junit.jupiter.api.AfterAll
    public static void afterClass() {
        System.out.println("---- End of tests ----");
    }


    @Test
    void runTests() {
        // just procedure to run tests == no reason to further test
    }

    @Test
    void resolveGenderTest() {

        try {
            dateBirthParser.parseRcDate("testing","testing","855102","0701");
            assertEquals(GenderEnum.FEMALE,dateBirthParser.resolveGender());
        }
        catch (RcDateOutOfLimitsException e) {
            e.printStackTrace();
        }

    }

    @Test
    void resolveMonthTest() {

        dateBirthParser.setDdMmYy("851302");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(13,dateBirthParser.resolveMonth());


        dateBirthParser.setDdMmYy("852302");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(3,dateBirthParser.resolveMonth());


        dateBirthParser.setDdMmYy("855302");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(3,dateBirthParser.resolveMonth());


        dateBirthParser.setDdMmYy("857302");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(3,dateBirthParser.resolveMonth());


        dateBirthParser.setDdMmYy("858302");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(13,dateBirthParser.resolveMonth());

    }

    @Test
    void resolveDayTest() {

        dateBirthParser.setDdMmYy("850402");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(2,dateBirthParser.resolveDay());


        dateBirthParser.setDdMmYy("850413");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(13,dateBirthParser.resolveDay());


        dateBirthParser.setDdMmYy("850430");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(30,dateBirthParser.resolveDay());


        dateBirthParser.setDdMmYy("850400");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(0,dateBirthParser.resolveDay());


        dateBirthParser.setDdMmYy("850431");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(31,dateBirthParser.resolveDay());


        dateBirthParser.setDdMmYy("850432");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(32,dateBirthParser.resolveDay());
    }

    @Test
    void resolveYearTest() {

        dateBirthParser.setDdMmYy("230402");
        dateBirthParser.setSuffixRC("078");
        assertEquals(1923,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("530402");
        dateBirthParser.setSuffixRC("070");
        assertEquals(1953,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("530402");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(2053,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("540402");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(1954,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("550402");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(1955,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("850402");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(1985,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("000402");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(2000,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("010502");
        dateBirthParser.setSuffixRC("0741");
        assertEquals(2001,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("120402");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(2012,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("220402");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(2022,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("850402");
        dateBirthParser.setSuffixRC("0701");
        assertEquals(1985,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("855402");
        dateBirthParser.setSuffixRC("0706");
        assertEquals(1985,dateBirthParser.resolveYear());


        dateBirthParser.setDdMmYy("150402");
        dateBirthParser.setSuffixRC("0793");
        assertEquals(2015,dateBirthParser.resolveYear());

    }

    //ToDo test exceptions throwing
    @Test
    void createTestDateTest() {

    }

}