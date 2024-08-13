package atm

trait ATMCommon {

    def abstract put(def value, def count)
    def abstract dump()
    def abstract state()
    def abstract get(def sumNeed)


}