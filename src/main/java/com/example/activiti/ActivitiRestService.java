package com.example.activiti;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActivitiRestService {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiRestService.class);
    }

    @Autowired
    private TaskService taskService;

    // @Bean
    public CommandLineRunner activitiDemo() {
        return (args) -> {
            taskService.createTaskQuery().list();
            while (true) {
                List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
                System.out.println("Active outstanding tasks: [" + tasks.size() + "]");
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    System.out.println("Processing Task [" + task.getName() + "]");

                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
    }

}
