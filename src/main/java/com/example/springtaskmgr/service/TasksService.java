package com.example.springtaskmgr.service;

import com.example.springtaskmgr.entities.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TasksService {
    private final List<Task> taskList;
    private AtomicInteger taskId = new AtomicInteger(0);

    public  static class TaskNotFoundException extends IllegalArgumentException{
        public TaskNotFoundException(Integer id) {
            super("Task with id " + id + " not found");
        }
    }

    public TasksService() {
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(), "task 1", "description 1", "16-02-2023"));
        taskList.add(new Task(taskId.incrementAndGet(), "task 2", "description 2", "16-02-2023"));
        taskList.add(new Task(taskId.incrementAndGet(), "task 3", "description 3", "16-02-2023"));
    }

    public  List<Task> getTasks(){
        return taskList;
    }

    public Task createTask(String title, String description, String dueDate){
        var newTask = new Task(taskId.incrementAndGet(), title, description, dueDate);
        taskList.add(newTask);
        return newTask;
    }

    public Task getTaskById(Integer id){
       return taskList.stream().
               filter(task -> task.getId().equals(id)).findFirst()
               .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task deleteTask(Integer id){
        Task task = getTaskById(id);
        taskList.remove(task);
        return task;
    }

    public Task updateTask(Integer id, String title, String description, String dueDate){
        Task task = getTaskById(id);
        if(title != null) task.setTitle(title);
        if(description != null) task.setDescription(description);
        if(dueDate != null) task.setDueDate(dueDate);
        return task;
    }

}
