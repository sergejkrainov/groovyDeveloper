package xlsx

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow

class Row {

    private final XSSFRow row
    private final String path
    private int cellIdx
    private def value
    //def value
    //def idx

    Row(XSSFRow row, String path) {
        this.row = row
        this.cellIdx = 0
        this.path = path
    }

    XSSFCell cell(Closure closure) {
        CellProp clProp = new CellProp()
        closure.delegate = clProp
        closure.call()
        def index
        if(clProp.id !== 0) {
            index = (clProp.id - 1)
        } else {
            index = cellIdx
            cellIdx++
        }
        value = clProp.val
        XSSFCell cell = row.createCell(index)
        cell.setCellValue(value.toString())
        CellStyle style = cell.getRow().getSheet().getWorkbook().createCellStyle()
        Font font = cell.getRow().getSheet().getWorkbook().createFont()
        if(clProp.style.equals("BOLD")) {
            font.setBold(true)
        }
        style.setFont(font)
        style.setAlignment(HorizontalAlignment.CENTER)
        cell.setCellStyle(style)

        cell.getRow().getSheet().getWorkbook().write(new FileOutputStream(path))
    }
}
