package br.com.jaya.currencyconversionapi.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public User(String name){
       this.setName(name);
    }

    @Id
    private String id;
    @Setter
    private String name;


}
