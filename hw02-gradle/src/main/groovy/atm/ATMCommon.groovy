package atm

trait ATMCommon {

    def abstract put(def value, def count, def currency)
    abstract void dump()
    abstract void state()
    def abstract get(def sumNeed, def currency)


}