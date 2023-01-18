package org.acme.mapping;

import org.acme.domain.User;
import org.acme.domain.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserMapper {
    /*
    See https://github.com/coding-daddy-xyz/example-quarkus-postgresql
     */
    UserDto toResource(User user);
    User fromResource(UserDto userDto);
}
