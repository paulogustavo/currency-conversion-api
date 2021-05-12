package br.com.jaya.currencyconversionapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
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
