package com.example.codeit.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.codeit.entity.Status;
import com.example.codeit.entity.Task;

@org.springframework.stereotype.Controller
public class Controller {
	
	Task task1 = new Task(1,"30 sec = 30 Pushups",null,false,"../img/pushups.webp");
	Task task2 = new Task(2,"20 sec = 30 ABS",null,false,"../img/abs1.webp");
	Task task3 = new Task(3,"13 sec = 15 Pushups",null,false,"../img/withclap.webp");
	Task task4 = new Task(4,"30 sec = 25 ABS",null,false,"../img/abs2.webp");
	Task task5 = new Task(5,"55 sec = 10 Bench Lifts X 50 kg",null,false,"../img/bench2.gif");
	Task task6 = new Task(6,"55 sec = 8 Biceps Lifts X 30 kg",null,false,"../img/biceps2.gif");
	Task task7 = new Task(7,"75 sec = 6 Biceps Lifts X 70 kg",null,false,"../img/biceps1.webp");
	Task task8 = new Task(8,"85 sec = 6 SitUps X 70 kg ",null,true,"../img/situps.webp");
	Task task9 = new Task(9,"85 sec = 4 Overhead lifts X 80 kg",null,true,"../img/overhead1.gif");
	Task task10 = new Task(10,"105 sec = 6 Bench Lifts X 80 kg",null,true,"../img/bench1.gif");
	
	List<Task> availableTasks = new ArrayList<>();
	LinkedList<Task> selectedTasks = new LinkedList<>();
	Stack<Task> scheduledTasks = new Stack<>();
	Stack<Task> taskInProgress = new Stack<>();
	List<Task> completedTasks = new ArrayList<>();
	List<Task> notProccessedTasks = new ArrayList<>();
	
	@GetMapping("/")
	public String openTasksPage (Model model) {
		
		if(availableTasks.isEmpty() && completedTasks.isEmpty()) {
			
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
		
		List<Task> toRemove = new ArrayList<Task>();
		
		for (Task task : availableTasks) {
			if(!completedTasks.isEmpty() && completedTasks.contains(task) ) {
				toRemove.add(task);
			}
		}
		
		availableTasks.removeAll(toRemove);
		
		model.addAttribute("availableTasks", availableTasks);
		model.addAttribute("selectedTasks", selectedTasks);
		
		return "index";
	}
		
	@GetMapping("/addTask/{id}")
	public String addTaskInList(@PathVariable("id")Integer id) {
		
		List<Task> toRemove = new ArrayList<Task>();
		
		for (Task task : availableTasks) {
			if(task.getId() == id) {
			selectedTasks.addFirst(task);
			toRemove.add(task);
			}
		}
		availableTasks.removeAll(toRemove);
		return "redirect:/";
		
	}
	
	@GetMapping("/removeTask/{id}")
	public String removeTaskFromList(@PathVariable("id")Integer id) {
		
		List<Task> toRemove = new ArrayList<Task>();
		
		for (Task task : selectedTasks) {
			if(task.getId() == id) {
			availableTasks.add(task);
			   toRemove.add(task);
			}
		}
		selectedTasks.removeAll(toRemove);
		
		return "redirect:/";
		
	}
	
	@GetMapping("/startTask")
	public String completeTasks(Model model) throws InterruptedException {
		
		List<Task> toRemove = new ArrayList<Task>();
		
		for (Task task : selectedTasks) {
			task.setStatus(Status.NOT_PROCESSED);
			scheduledTasks.push(task);
			
			toRemove.add(task);
		}
		
		selectedTasks.removeAll(toRemove);
		
		if(!scheduledTasks.isEmpty()) {
		   Task current = scheduledTasks.pop();
		     model.addAttribute("current", current);
		
		if(!scheduledTasks.isEmpty()) {
		   Task next = scheduledTasks.peek();
		     model.addAttribute("next", next);
		}
		
		taskInProgress.push(current);
		
	    }
		     model.addAttribute("completedTasks", completedTasks);
		
		return "start";
	}
	
	@GetMapping("/back")
	public String backToSelectingPage() {
		
		selectedTasks.add(taskInProgress.pop());
		
		for (Task task : scheduledTasks) {
		    scheduledTasks.peek();
		    selectedTasks.add(task);
			
			
		}
		scheduledTasks.clear();
		
		return "redirect:/";
		
	}
	
  
	@GetMapping("/completeTask")
	public String nextTask(Model model) {
		
		Task task = taskInProgress.peek();
		task.setStatus(Status.PROCESSED);
		Integer number = Integer.valueOf(task.getDescription().substring(0, task.getDescription().indexOf(" ")));
		model.addAttribute("task", task);
		model.addAttribute("number", number);
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
	
	@GetMapping("/newStart")
	public String startOver() {
		
		for (Task task : completedTasks) {
			availableTasks.add(task);
		}
		completedTasks.clear();
	
		return "redirect:/";
	}
	
	@GetMapping("/cancel")
	public String cancelExcercises(Model model) {
		
           completedTasks.add(taskInProgress.pop());
		
		for (Task task : scheduledTasks) {
		    scheduledTasks.peek();
		    completedTasks.add(task);
			
			
		}
		scheduledTasks.clear();
		
		return "redirect:/startTask";
		
	}
	

}
