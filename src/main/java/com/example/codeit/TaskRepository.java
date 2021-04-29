package com.example.codeit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.codeit.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

}
