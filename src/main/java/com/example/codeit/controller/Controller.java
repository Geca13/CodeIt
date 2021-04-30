package com.example.codeit.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.codeit.entity.Status;
import com.example.codeit.entity.Task;

@org.springframework.stereotype.Controller
public class Controller {
	
	
	Task task1 = new Task(1,"30 Pushups in 30 seconds",null,30,false,"");
	Task task2 = new Task(2,"30 ABS in 20 seconds",null,30,false,"");
	Task task3 = new Task(3,"15 Pushups in 13 seconds",null,15,false,"");
	Task task4 = new Task(4,"25 ABS in 30 seconds",null,25,false,"");
	Task task5 = new Task(5,"50 kg , 10 Bench Lifts",null,10,false,"");
	Task task6 = new Task(6,"30 kg , 8 Biceps Lifts",null,8,false,"");
	Task task7 = new Task(7,"70 kg , 6 Bench Lifts",null,6,false,"");
	Task task8 = new Task(8,"70 kg , 6 SitUps",null,6,true,"");
	Task task9 = new Task(9,"80 kg , 4 SitUps",null,4,true,"");
	Task task10 = new Task(10,"80 kg , 6 Bench Lifts",null,6,true,"");
	
	List<Task> availableTasks = new ArrayList<>();
	LinkedList<Task> selectedTasks = new LinkedList<>();
	Stack<Task> scheduledTasks = new Stack<>();
	Stack<Task> taskInProgress = new Stack<>();
	List<Task> completedTasks = new ArrayList<>();
	
	
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
		model.addAttribute("selectedTasks", selectedTasks);
		
		return "index";
	}
		
	@GetMapping("/addTask/{id}")
	public String addTaskInList(@PathVariable("id")Integer id) {
		
		for (Task task : availableTasks) {
			if(task.getId() == id) {
			selectedTasks.addFirst(task);
			}
		}
		
		return "redirect:/";
		
	}
	
	@GetMapping("/removeTask/{id}")
	public String removeTaskFromList(@PathVariable("id")Integer id) {
		
		List<Task> toRemove = new ArrayList<Task>();
		
		for (Task task : selectedTasks) {
			if(task.getId() == id) {
			   toRemove.add(task);
			}
		}
		selectedTasks.removeAll(toRemove);
		
		return "redirect:/";
		
	}
	
	@GetMapping("/peek")
	public String startTasks(Model model, @ModelAttribute("current") Task current) {
		
		List<Task> toRemove = new ArrayList<Task>();
			
			for (Task task : selectedTasks) {
				task.setStatus(Status.NOT_PROCESSED);
				scheduledTasks.push(task);
				
				toRemove.add(task);
			}
			
		Task next = scheduledTasks.peek();
		model.addAttribute("next", next);
		
		model.addAttribute("completedTasks",completedTasks);
		
		selectedTasks.removeAll(toRemove);
		Integer number = 30;
		model.addAttribute("number",number);
		return "start";
		
	}
	
	
	
	@GetMapping("/startTask")
	public String completeTasks(Model model) throws InterruptedException {
		
		if(!scheduledTasks.isEmpty()) {
		   Task current = scheduledTasks.pop();
		     model.addAttribute("current", current);
		
		if(!scheduledTasks.isEmpty()) {
		   Task next = scheduledTasks.peek();
		     model.addAttribute("next", next);
		}
		
		taskInProgress.push(current);
		     model.addAttribute("taskInProgress", taskInProgress);
	    }
		     model.addAttribute("completedTasks", completedTasks);
		
		return "start";
	}
	
	
  
	@GetMapping("/completeTask")
	public String nextTask(Model model) {
		
		Task task = taskInProgress.peek();
		task.setStatus(Status.PROCESSED);
		model.addAttribute("task", task);
		
		completedTasks.add(task);
		
		taskInProgress.pop();
		
		return "counter";
		
	}
	
	
	@GetMapping("/skip")
	public String skipTask() {
		
		Task task = taskInProgress.peek();
		task.setStatus(Status.FAILED);
		completedTasks.add(task);
		taskInProgress.pop();
		
		return "redirect:/startTask";
		
	}
	
	@GetMapping("/startTimer")
	public String timer (Model model) {
		model.addAttribute("endDate", LocalDateTime.now().plusSeconds(20));
		return "redirect:/startTask";

	}
	
	
	
	
}
