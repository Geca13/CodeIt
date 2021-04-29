package com.example.codeit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.codeit.TaskRepository;
import com.example.codeit.entity.Status;
import com.example.codeit.entity.Task;

@org.springframework.stereotype.Controller
public class Controller {
	
	Timer timer;
	
	@Autowired
	TaskRepository taskRepository;
	
	Task task1 = new Task(1,"30 Pushups in 30 seconds",null,false,"");
	Task task2 = new Task(2,"30 ABS in 20 seconds",null,false,"");
	Task task3 = new Task(3,"15 Pushups in 13 seconds",null,false,"");
	Task task4 = new Task(4,"25 ABS in 30 seconds",null,false,"");
	Task task5 = new Task(5,"50 kg , 10 Bench Lifts",null,false,"");
	Task task6 = new Task(6,"30 kg , 8 Biceps Lifts",null,false,"");
	Task task7 = new Task(7,"70 kg , 6 Bench Lifts",null,false,"");
	Task task8 = new Task(8,"70 kg , 6 SitUps",null,true,"");
	Task task9 = new Task(9,"80 kg , 4 SitUps",null,true,"");
	Task task10 = new Task(10,"80 kg , 6 Bench Lifts",null,true,"");
	
	List<Task> availableTasks = new ArrayList<>();
	Stack<Task> scheduledTasks = new Stack<>();
	
	
	
	@GetMapping("/")
	public String openTasksPage (Model model) {
		
		if(availableTasks.isEmpty()) {
			
		availableTasks.add(task1);
		availableTasks.add(task2);
		availableTasks.add(task3);
		availableTasks.add(task4);
		availableTasks.add(task5);
		availableTasks.add(task6);
		availableTasks.add(task7);
		availableTasks.add(task8);
		availableTasks.add(task9);
		availableTasks.add(task10);
		
		}
		model.addAttribute("availableTasks", availableTasks);
		
		return "index";
	}
		
	@GetMapping("/addTask/{id}")
	public String addTaskInStack(@PathVariable("id")Integer id) {
		
		for (Task task : availableTasks) {
			if(task.getId() == id) {
				task.setStatus(Status.NOT_PROCESSED);
				scheduledTasks.push(task);
			}
		}
		
		return "redirect:/";
		
	}
	
	@GetMapping("/peek")
	public String startTasks(Model model) {
		
		Task task = scheduledTasks.peek();
		model.addAttribute("task", task);
		
		return "start";
		
	}
	
	@GetMapping("/doTasks")
	public String completeTasks() {
		
		while(scheduledTasks.size() > 0) {
			scheduledTasks.peek();
			Task task = scheduledTasks.peek();
			Long seconds = Long.valueOf(task.getDescription().substring(0, task.getDescription().indexOf(" ")));
			
			
			
			}
		
		
		return "redirect:/peek";
	}

	
	
	
	
}
