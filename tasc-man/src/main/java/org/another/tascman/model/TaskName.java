package org.another.tascman.model;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Date;
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

    String taskName;

    Instant creationDate = Instant.now();

    @Override
    public String toString() {
        return "Task Name" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", creationDate=" + creationDate;
    }
}
