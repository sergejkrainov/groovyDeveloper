package sql.orm

import groovy.sql.Sql

class SqlBuilder {

    static def build(String host, String port, String dbName, String login, String password, Closure callable) {
        Sql connection = Sql.newInstance("jdbc:postgresql://${host}:${port}/${dbName}", login, password)
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.delegate = new SqlLib(connection)
        return callable.call()
    }
}
