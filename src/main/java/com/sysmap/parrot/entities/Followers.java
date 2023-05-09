package com.sysmap.parrot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Followers {
    private List<String> users;

}

