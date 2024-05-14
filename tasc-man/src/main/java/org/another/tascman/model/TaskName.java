package org.another.tascman.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.Instant;


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

    String taskName;

    Instant creationDate = Instant.now();

    @Override
    public String toString() {
        return "Task Name:" + "\n" +
                "id = " + id + "\n" +
                "task Name = " + taskName + "\n" +
                "creation Date = " + creationDate + "\n";
    }

}
