package br.com.jaya.currencyconversionapi.domain.user.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public User(String name){
       this.setName(name);
    }

    @Id
    private String id;

    private String name;


}
