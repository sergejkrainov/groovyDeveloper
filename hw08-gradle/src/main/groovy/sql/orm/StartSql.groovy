package sql.orm

import groovy.sql.GroovyRowResult
import sql.datamodel.Employees

static void main(String[] args) {
  /*def connection = Sql.newInstance("jdbc:postgresql://localhost:5432/employees", "postgres", "admin")

  String sqlInsAct = """
    insert into action values (4, 'Action4', 2, '12:00', '14:00', true)
  """
  String sqlInsTask = """
    insert into task values (2, 'task2', '09:00', '20:00', '2024-11-15', true)
  """
  String sqlSelect  = "select a.*, t.* from action a,task t where a.indexoftask = t.id and t.id=1"
  //connection.executeInsert(sqlInsTask)

  connection.query(sqlSelect,
          { ResultSet it ->
            int i = 1
            while(it.next()){
                for(int j = 1; j < it.getMetaData().getColumnCount(); j++) {
                    print(it.getMetaData().getColumnName(j) + ":")
                    print(it.getObject(j))
                    print(" ")
                }
                println{" "}
                i++
            }
          })
    List<GroovyRowResult> result = connection.rows(sqlSelect)
    result.each {
        it.each {
            print(it.getKey())
            print(":")
            print(it.getValue())
            print(" ")
        }
        println(" ")
        i++
    }*/

    List<Employees> employeesList = SqlBuilder.build("localhost", "5432", "employees", "postgres", "admin") {
        query {
            select("first_name", "last_name", "division", "salary")
            from("Employees")
            where {
                eq("division", "division1")
                and()
                nonEq("salary", 200)
            }
        }
    }
}