package todolist

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param;

interface TaskRepository extends JpaRepository<Task, Long> {

    String sqlRequest = "select * from TASK where DUEDATE = :dueDate"
    @Query(value = sqlRequest, nativeQuery = true)
    List<Task> findAllByDueDate(@Param("dueDate") String dueDate)

}
