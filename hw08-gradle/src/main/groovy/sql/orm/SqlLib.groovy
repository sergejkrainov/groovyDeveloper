package sql.orm

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import sql.datamodel.Employees

import java.sql.ResultSet

class SqlLib {

    private final Sql connection
    String sqlQuery = "select "

    SqlLib(Sql connection) {
        this.connection = connection
    }

    List<Employees> query(Closure callable){
        callable.call()
        println(sqlQuery)
        return executeQuery(sqlQuery)
    }

    String select(String ... params) {
        params.each {
            sqlQuery+= (it + ",")
        }
        sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 1) + " "
    }

    def from(String className) {
        if(sqlQuery.equals("select ")) {
            sqlQuery += " * "
        }
        sqlQuery += "from " + className.toLowerCase()
    }

    def where(Closure callable) {
        sqlQuery += " where "
        callable.call()
    }

    def or(Closure callable) {
        sqlQuery += " or "
    }

    def and(Closure callable) {
        sqlQuery += " and "
    }

    def eq(def column, Object value) {
        def operator = "="
        if(value == "null") {
            operator = "is"
        }
        if(value.getClass().getName().contains("String")){
            sqlQuery += " ${column} ${operator} '${value}' "
        } else {
            sqlQuery += " ${column} ${operator} ${value} "
        }

    }

    def nonEq(def column, Object value) {
        def operator = "!="
        if(value == "null") {
            operator = "is not"
        }
        if(value.getClass().getName().contains("String")) {
            sqlQuery += " ${column} ${operator} '${value}' "
        } else {
            sqlQuery += " ${column} ${operator} ${value} "
        }

    }

    def gt(def column, Object value) {
        if(value.getClass().getName().contains("String")) {
            sqlQuery += " ${column} > '${value}' "
        } else {
            sqlQuery += " ${column} > ${value} "
        }
    }

    def gte(def column, Object value) {
        if(value.getClass().getName().contains("String")) {
            sqlQuery += " ${column} >= '${value}' "
        } else {
            sqlQuery += " ${column} >= ${value} "
        }
    }

    def ls(def column, Object value) {
        if(value.getClass().getName().contains("String")) {
            sqlQuery += " ${column} < '${value}' "
        } else {
            sqlQuery += " ${column} < ${value} "
        }
    }

    def lse(def column, Object value) {
        if(value.getClass().getName().contains("String")) {
            sqlQuery += " ${column} <= '${value}' "
        } else {
            sqlQuery += " ${column} <= ${value} "
        }
    }



    List<Employees> executeQuery(String sqlQuery) {
        List<Employees> lst = new ArrayList<Employees>()
        String columnName = ""
        def columnValue
        connection.query(sqlQuery,
                { ResultSet it ->
                    int i = 1
                    while(it.next()){
                        Employees employees = new Employees()
                        for(int j = 1; j < it.getMetaData().getColumnCount() + 1; j++) {
                            //print(it.getMetaData().getColumnName(j) + ":")
                            //print(it.getObject(j))
                            //print(" ")
                            columnName = it.getMetaData().getColumnName(j)
                            columnValue = it.getObject(j)
                            print(it.getObject(j))
                            print(" ")
                            employees.getMetaClass().setProperty(employees, columnName, columnValue)
                        }
                        println{" "}
                        i++
                        lst.add(employees)
                    }
                })
        List<Employees> lst2 = lst
        return lst
    }
}
