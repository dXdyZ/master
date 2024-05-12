package org.another.tascman.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Date;


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
        return "Ander Task: " +
                "subtaskText='" + subtaskText + '\'' +
                ", creationDate=" + creationDate +
                ", taskNameId=" + taskName.getId();
    }
}

