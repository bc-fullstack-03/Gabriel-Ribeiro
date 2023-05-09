package com.sysmap.parrot.entities;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Following {
    private List<String> users;

}