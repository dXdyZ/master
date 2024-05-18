package org.another.tascman.repository;

import org.another.tascman.model.TaskName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskName, Long> {

    @Modifying
    @Query("UPDATE TaskName t SET t.taskName = :newTaskName WHERE t.taskName = :id")
    void updateTaskNameById(String id, String newTaskName);

    @Query("SELECT at FROM TaskName at WHERE at.taskName = :taskName")
    TaskName findByIdTaskName(@Param("taskName") String taskName);
}
