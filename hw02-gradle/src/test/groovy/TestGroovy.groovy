import atm.ATM
import org.junit.jupiter.api.Test

class TestGroovy {

    ATM atm = new ATM()
    @Test
    void test() {
        def value = 500
        def currency = "Rur"
        assert atm.put(value, 1, currency) == value
        assert atm.get(value, currency) == "${value}=1, summary: ${value}"
    }

}
