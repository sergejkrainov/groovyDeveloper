package atm

class Banknote {

    private Currency curr
    private Nominal nominal

    Banknote(Nominal nominal, Currency curr) {
        this.curr = curr
        this.nominal = nominal
    }
    public Nominal getNominal() {
        return this.nominal
    }
    public Currency getCurrency() {
        return this.curr
    }

}
