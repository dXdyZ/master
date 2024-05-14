package org.another.tascman.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;


@Entity
@Getter
@Setter
@Builder
@Table(name = "anderTask")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AnderTask {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(nullable = false)
    String subtaskText;

    Instant creationDate = Instant.now();

    @ManyToOne()
    @JoinColumn(name = "task_name_id")
    TaskName taskName;

    @Override
    public String toString() {
        return "Ander Task: " + "\n" +
                "subtask Text = " + subtaskText + "\n" +
                "task Name = " + taskName.getTaskName() + "\n" +
                "task Name Id = " + taskName.getId() + "\n" +
                "creation Date = " + creationDate + "\n";
    }

    public String toStringTaskNameForId() {
        return "Ander Task: " + "\n" +
                "id = " + id + "\n" +
                "subtask Text = " + subtaskText + "\n" +
                "creation date = " + creationDate + "\n";
    }
}


