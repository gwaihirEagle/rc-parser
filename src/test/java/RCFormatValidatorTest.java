import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;


class RCFormatValidatorTest {
    RCFormatValidator rcFormatValidator;


    @org.junit.jupiter.api.BeforeAll
    public static void beforeClass() {
        System.out.println("RCFormatValidatorTest ----> \n ...Initializing tests");

    }

    @org.junit.jupiter.api.AfterAll
    public static void afterClass() {
        System.out.println("---- End of tests ----");
    }



    @org.junit.jupiter.api.Test
    void runTests() {
        // just procedure -- no need for testing..?

    }

    @org.junit.jupiter.api.Test
    void findPrefSuffTest() {
        RCFormatValidator rcFormatValidator = new RCFormatValidator("850402*0701");
        assertEquals(false,rcFormatValidator.findPrefSuff());

        RCFormatValidator rcFormatValidator1 = new RCFormatValidator("8504020701");
        assertEquals(false,rcFormatValidator1.findPrefSuff());

        RCFormatValidator rcFormatValidator2 = new RCFormatValidator("850402/0701");
        assertEquals(true,rcFormatValidator2.findPrefSuff());
    }

    // todo - find out best way how to test exception treatment
    @org.junit.jupiter.api.Test
    void savePrefSuff() throws Exception {
        try {
            RCFormatValidator rcFormatValidator = new RCFormatValidator("850402*0701"); // missing / delimiter
            //rcFormatValidator.savePrefSuff();
            //Assert.fail("Expected exception: 'NoSuchElementException' to be thrown");
        } catch (NoSuchElementException e) {
            System.out.println("Jsem tu");
            //assertEquals(true,rcFormatValidator2.findPrefSuff());
            /*assertThat(e)
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("Required delimiter: '"+"/"+"' not found"); */
        }


    }

    @org.junit.jupiter.api.Test
    void checkOnlyNumberDigits() {
        RCFormatValidator rcFormatValidator;

        rcFormatValidator = new RCFormatValidator("850402/0701");
        rcFormatValidator.findPrefSuff();
        rcFormatValidator.savePrefSuff();
        assertEquals(true,rcFormatValidator.checkOnlyNumberDigits());

        rcFormatValidator = new RCFormatValidator("85a0402/0701");
        rcFormatValidator.findPrefSuff();
        rcFormatValidator.savePrefSuff();
        assertEquals(false,rcFormatValidator.checkOnlyNumberDigits());

        rcFormatValidator = new RCFormatValidator(".850402/0701");
        rcFormatValidator.findPrefSuff();
        rcFormatValidator.savePrefSuff();
        assertEquals(false,rcFormatValidator.checkOnlyNumberDigits());

        rcFormatValidator = new RCFormatValidator("85040f/0701");
        rcFormatValidator.findPrefSuff();
        rcFormatValidator.savePrefSuff();
        assertEquals(false,rcFormatValidator.checkOnlyNumberDigits());

        rcFormatValidator = new RCFormatValidator("850402/a701");
        rcFormatValidator.findPrefSuff();
        rcFormatValidator.savePrefSuff();
        assertEquals(false,rcFormatValidator.checkOnlyNumberDigits());

        rcFormatValidator = new RCFormatValidator("850402/070c");
        rcFormatValidator.findPrefSuff();
        rcFormatValidator.savePrefSuff();
        assertEquals(false,rcFormatValidator.checkOnlyNumberDigits());
    }

    @org.junit.jupiter.api.Test
    void checkIsOnlyDigit() {
        RCFormatValidator rcFormatValidator;
        rcFormatValidator = new RCFormatValidator("850402/0701");

        assertEquals(false,rcFormatValidator.checkIsOnlyDigit("212121a"));
        assertEquals(false,rcFormatValidator.checkIsOnlyDigit("a212121"));
        assertEquals(false,rcFormatValidator.checkIsOnlyDigit(".212121"));
        assertEquals(false,rcFormatValidator.checkIsOnlyDigit("a21.2121"));
        assertEquals(false,rcFormatValidator.checkIsOnlyDigit("2#12121"));
        assertEquals(false,rcFormatValidator.checkIsOnlyDigit("212121)"));
        assertEquals(false,rcFormatValidator.checkIsOnlyDigit("212 121)"));
        assertEquals(false,rcFormatValidator.checkIsOnlyDigit("212;121)"));
        assertEquals(false,rcFormatValidator.checkIsOnlyDigit(" 212121"));
        assertEquals(false,rcFormatValidator.checkIsOnlyDigit("212121 "));
    }

    @org.junit.jupiter.api.Test
    void checkPrefixNoDigits() {
        RCFormatValidator rcFormatValidator;
        rcFormatValidator = new RCFormatValidator("850402/0701");

        rcFormatValidator.setPrefix("850402");
        assertEquals(true,rcFormatValidator.checkPrefixNoDigits());
        rcFormatValidator.setPrefix("85002");
        assertEquals(false,rcFormatValidator.checkPrefixNoDigits());
        rcFormatValidator.setPrefix("8502");
        assertEquals(false,rcFormatValidator.checkPrefixNoDigits());
        rcFormatValidator.setPrefix("8500211");
        assertEquals(false,rcFormatValidator.checkPrefixNoDigits());
        rcFormatValidator.setPrefix("85002114");
        assertEquals(false,rcFormatValidator.checkPrefixNoDigits());

    }

    @org.junit.jupiter.api.Test
    void checkSuffixNoDigits() {
        RCFormatValidator rcFormatValidator;
        rcFormatValidator = new RCFormatValidator("850402/0701");

        rcFormatValidator.setSuffix("0701");
        assertEquals(true,rcFormatValidator.checkSuffixNoDigits());
        rcFormatValidator.setSuffix("071");
        assertEquals(true,rcFormatValidator.checkSuffixNoDigits());

        rcFormatValidator.setSuffix("07");
        assertEquals(false,rcFormatValidator.checkSuffixNoDigits());
        rcFormatValidator.setSuffix("");
        assertEquals(false,rcFormatValidator.checkSuffixNoDigits());
        rcFormatValidator.setSuffix("07011");
        assertEquals(false,rcFormatValidator.checkSuffixNoDigits());
        rcFormatValidator.setSuffix("070113");
        assertEquals(false,rcFormatValidator.checkSuffixNoDigits());

    }

    @org.junit.jupiter.api.Test
    void checkTenModEleven() {
        RCFormatValidator rcFormatValidator;
        rcFormatValidator = new RCFormatValidator("850402/0701");

        rcFormatValidator.setPrefix("850402");
        rcFormatValidator.setSuffix("0701");
        assertEquals(true,rcFormatValidator.checkTenModEleven());

        rcFormatValidator.setPrefix("850402");
        rcFormatValidator.setSuffix("0702");
        assertEquals(false,rcFormatValidator.checkTenModEleven());

        rcFormatValidator.setPrefix("850403");
        rcFormatValidator.setSuffix("0701");
        assertEquals(false,rcFormatValidator.checkTenModEleven());

        rcFormatValidator.setPrefix("850402");
        rcFormatValidator.setSuffix("0790"); // test mod 11 that crc equals 0
        assertEquals(true,rcFormatValidator.checkTenModEleven());

    }

    @org.junit.jupiter.api.Test
    void getSuffix() {
    }

    @org.junit.jupiter.api.Test
    void getPrefix() {
    }

}