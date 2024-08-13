import atm.ATM
import org.junit.jupiter.api.Test

class TestGroovy {

    ATM atm = new ATM()
    @Test
    def void test() {
        def value = 500
        assert atm.put(value, 1) == value
        assert atm.get(value) == "${value}=1, summary: ${value}"
        atm.put(value, 1)
        assert atm.dump() ==~ /ATM:\n1 0\n3 0\n5 0\n10 0\n25 0\n50 0\n100 0\n500 1\n1000 0\n5000 0/
        assert atm.state() == value
    }

}
