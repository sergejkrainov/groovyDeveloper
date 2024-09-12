package xlsx

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class Workbook {

    private final String path
    private final XSSFWorkbook wb

    Workbook(XSSFWorkbook wb, String path) {
        this.wb = wb
        this.path = path

    }

    XSSFSheet sheet(String name, Closure callable) {
        XSSFSheet sheet = wb.createSheet(name)
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.delegate = new Sheet(sheet, path)
        callable.call()
        sheet
    }
}
