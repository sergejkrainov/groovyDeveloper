package xlsx

import org.apache.poi.xssf.usermodel.XSSFWorkbook

class XlsxBuilder {

    static XSSFWorkbook build(String path, Closure callable) {
        XSSFWorkbook wb = new XSSFWorkbook()
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.delegate = new Workbook(wb, path)
        callable.call()

        wb
    }
}
