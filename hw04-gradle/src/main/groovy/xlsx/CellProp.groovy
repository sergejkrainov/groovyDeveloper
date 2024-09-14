package xlsx

class CellProp {

    public def val
    public int id = 0
    public String style
    public void value(def value) {
        this.val = value
    }
    public int idx(int id) {
        this.id = id
    }

    public void style(def style) {
        this.style = style
    }

}
