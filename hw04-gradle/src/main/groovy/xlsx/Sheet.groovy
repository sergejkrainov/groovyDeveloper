package xlsx

import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet

class Sheet {
    private final XSSFSheet sheet
    private final String path

    Sheet(XSSFSheet sheet, String path) {
        this.sheet = sheet
        this.path = path
    }

    XSSFRow row(int rowIdx, Closure callable) {
        XSSFRow row = sheet.createRow(rowIdx)
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.delegate = new Row(row, path)
        callable.call()
        row
    }
}
