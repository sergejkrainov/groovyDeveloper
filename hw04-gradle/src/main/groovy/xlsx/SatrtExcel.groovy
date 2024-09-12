package xlsx


static void main(String[] args) {

    XlsxBuilder.build("test.xlsx") {
        sheet("Sheet1") {
            row(0) {
                cell {
                    value 1
                    style "NORMAL"
                }
                cell {
                    value 2
                    style "BOLD"
                }
                cell {
                    idx  3
                    value "test1"
                    style "BOLD"
                }
            }
            row(2) {
                cell {
                    value 3
                    style "NORMAL"
                }
                cell {
                    value 4
                }
                cell {
                    value 5
                }
                cell {
                    idx  5
                    value "test2"
                    style "BOLD"
                }
            }
        }
    }
}