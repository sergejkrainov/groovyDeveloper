package atm

enum Nominal {


    One(1), Three(3), Five(5), Ten(10), Twenty_five(25), Fifti(50), One_hundred(100), Five_hundred(500), Thousand(1000), Five_thousand(5000)
    def amount
    Nominal(def amount){
        this.amount = amount;
    }
}