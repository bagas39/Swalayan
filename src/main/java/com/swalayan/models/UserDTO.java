package com.swalayan.models;

import com.fasterxml.jackson.annotation.JsonProperty; 
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String nama;
    private String username;
    private String password; 
    private String role;     
    
    @JsonProperty("noHp") 
    private String noHp;     
}