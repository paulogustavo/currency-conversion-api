package br.com.jaya.currencyconversionapi.application.user.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Id
    private String id;
    @Setter
    private String name;


}
