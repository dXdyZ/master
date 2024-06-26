package org.another.tascman.repository;

import org.another.tascman.model.AnderTask;
import org.another.tascman.model.TaskName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnderTaskRepository extends JpaRepository<AnderTask, Long> {

    @Modifying
    @Query("DELETE FROM AnderTask at WHERE at.taskName.id = :id")
    void deleteByTaskNameId(@Param("id") Long id);

    @Query("SELECT at FROM AnderTask at WHERE at.taskName.id = :id")
    List<AnderTask> findAllByTaskNameId(@Param("id") Long id);

    @Modifying
    @Query("UPDATE AnderTask a SET a.subtaskText = :subtaskText WHERE a.id = :id")
    void updateAnderTaskBySubtaskText(@Param("id") Long id, @Param("subtaskText") String subtaskText);
}
