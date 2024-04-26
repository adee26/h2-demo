package com.h2test.repository;

import com.h2test.dto.UserFilters;
import com.h2test.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomUserRepository {

    List<User> findUserByFirstNameAndLastName(String firstName, String lastName);

    List<User> findUserByFirstNameAndLastNameNative(String firstName, String lastName);

    List<UserFilters> findUserFiltersForEnabledUsers(Boolean enabled);

    List<UserFilters> findUserFiltersForEnabledUsersHardcoded();

}
