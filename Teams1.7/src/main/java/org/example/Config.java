package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Config {
    private String email;
    private String password;
    private String roomName;
    private int day;
    private int hour;
    private int minute;
    private int duration;
}
