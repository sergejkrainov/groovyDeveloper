package todolist

import org.springframework.data.jpa.repository.JpaRepository;

interface ActionRepository extends JpaRepository<Action, Long> {

}
