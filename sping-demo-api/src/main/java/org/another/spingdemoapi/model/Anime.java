package org.another.spingdemoapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anime {
    @Id
    private Long id;
    private String name;
    private String russian;
    private float score;
    private String status;
    private String kind;
    private int episodes;
    private String url;
}
