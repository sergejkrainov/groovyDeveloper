package todolist

import grails.gorm.services.Service

@Service(Action)
interface ActionService {

    Action get(Serializable id)

    List<Action> list(Map args)

    Long count()

    void delete(Serializable id)

    Action save(Action action)

}