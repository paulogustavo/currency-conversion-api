package br.com.jaya.currencyconversionapi.application.user.mapper;

import br.com.jaya.currencyconversionapi.application.user.dto.UserResponseDto;
import br.com.jaya.currencyconversionapi.domain.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Maps a User object to a UserResponseDto object
     * @return UserResponseDto application object
     */
    UserResponseDto map(User toMap);

}
