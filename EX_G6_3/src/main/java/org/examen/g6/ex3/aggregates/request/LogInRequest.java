package org.examen.g6.ex3.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInRequest {
    private String email;
    private String password;
}