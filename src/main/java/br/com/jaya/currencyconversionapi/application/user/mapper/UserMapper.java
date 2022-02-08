package br.com.jaya.currencyconversionapi.application.user.mapper;

import br.com.jaya.currencyconversionapi.application.user.dto.UserDto;
import br.com.jaya.currencyconversionapi.domain.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    UserDto map(User toMap);

}
