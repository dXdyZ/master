package org.another.tascman.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnderTaskRequest {
    Long taskId;
    String subtaskText;
}
