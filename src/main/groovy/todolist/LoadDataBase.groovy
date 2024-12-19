package todolist


import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoadDataBase {

    //private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);

    @Bean
    CommandLineRunner initDatabase(ActionRepository actionRepository, TaskRepository taskRepository) {

        return (args) -> {

            /*Task task1 = new Task("task1", "2024-11-21", "06:00", "22:00")
            taskRepository.save(task1)
            Task task2 = new Task("task2", "2024-11-22", "07:00", "22:00")
            taskRepository.save(task2)

            Action act1 = new Action("action1", "10:00", "15:00", 1)
            Action act2 = new Action("action2", "16:00", "20:00", 1)
            Action act3 = new Action("action3", "08:00", "12:00", 2)
            Action act4 = new Action("action4", "13:00", "20:00", 2)
            println("Preloading " + actionRepository.save(act1));
            println("Preloading " + actionRepository.save(act2));
            println("Preloading " + actionRepository.save(act3));
            println("Preloading " + actionRepository.save(act4));

            List<Action> actList1 = new ArrayList<Action>()
            List<Action> actList2 = new ArrayList<Action>()
            actList1.add(act1)
            actList1.add(act2)
            actList2.add(act3)
            actList2.add(act4)

            //task1.setActionList(actList1)
            //task2.getActionList(actList2)

            println("Preloading " + taskRepository.save(task1));
            println("Preloading " + taskRepository.save(task2));*/
        }
    }

}
