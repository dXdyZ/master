package org.another.tasktraker.store.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Setter
@Builder // Автоматически генерируен билдер. Билдер - будут существовать все конструкторы
@FieldDefaults(level = AccessLevel.PRIVATE) // Делает все поля приватными
@NoArgsConstructor // без аргументов
@AllArgsConstructor // со всеми аргументами
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(unique = true) //Поле должно быть уникальным
    String name;

    @Builder.Default
    Instant createdAt = Instant.now();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "project_id", referencedColumnName = "id") //Говорит о том что будет созданно указанно поле и будет собираться список по нему
    List<TaskStateEntity> taskStates = new ArrayList<>();
}
