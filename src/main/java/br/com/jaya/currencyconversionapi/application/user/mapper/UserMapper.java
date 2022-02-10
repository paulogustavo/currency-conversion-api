package br.com.jaya.currencyconversionapi.application.user.mapper;

import br.com.jaya.currencyconversionapi.application.user.dto.UserResponseDto;
import br.com.jaya.currencyconversionapi.domain.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto map(User toMap);

}
