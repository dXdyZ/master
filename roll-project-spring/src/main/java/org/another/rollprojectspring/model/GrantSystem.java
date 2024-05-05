package org.another.rollprojectspring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grant_system")
@Getter
@Setter
@Builder // Автоматически генерируен билдер. Билдер - будут существовать все конструкторы
@FieldDefaults(level = AccessLevel.PRIVATE) // Делает все поля приватными
@NoArgsConstructor // без аргументов
@AllArgsConstructor // со всеми аргументами
public class GrantSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @OneToMany
    List<User> users;

    @ManyToOne
    User currentUser;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    List<Project> projects = new ArrayList<>();

    int minSum;
    int gradFond;

}
