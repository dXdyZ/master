package com.example.receiverrabbit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
