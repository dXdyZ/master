package org.another.tascman.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "taskName")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TaskName {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String  nameTask;

    @Builder.Default
    Instant createAt = Instant.now();

    @OneToMany
    @JoinColumn(name = "task_name_id", referencedColumnName = "id")
    List<AnderTask> anderTasks;
}
