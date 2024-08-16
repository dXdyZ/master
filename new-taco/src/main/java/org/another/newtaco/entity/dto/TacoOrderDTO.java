package org.another.newtaco.entity.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class TacoOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date placeAt;
    private HashMap<Long, List<String>> userData;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
    private HashMap<Long, List<String>> tacosData;
}
