package com.h2test.repository;

import com.h2test.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.saveAll(buildUsersList());
    }

    @Test
    void shouldReturnAllUsers() {
        final var users = userRepository.findAll();
        assertEquals(4, users.size());
    }

    @Test
    void shouldReturnUserById() {
        final var user = userRepository.findById(1);
        assertNotNull(user);
    }

    @Test
    void shouldReturnUsersUsingHibernateQuery() {
        final var user = userRepository.findUserByFirstNameAndLastName("John", "Doe");
        assertNotNull(user);
        assertEquals("John", user.get(0).getFirstName());
        assertEquals("Doe", user.get(0).getLastName());
        assertEquals("john.doe@email.com", user.get(0).getEmail());
        assertEquals("1234567891", user.get(0).getPhoneNumber());
    }

    @Test
    void shouldReturnUsersUsingHibernateNativeQuery() {
        final var user = userRepository.findUserByFirstNameAndLastNameNative("John", "Doe");
        assertNotNull(user);
        assertEquals("John", user.get(0).getFirstName());
        assertEquals("Doe", user.get(0).getLastName());
        assertEquals("john.doe@email.com", user.get(0).getEmail());
        assertEquals("1234567891", user.get(0).getPhoneNumber());
    }


    @Test
    void shouldReturnUserFiltersUsingHibernateNativeQueryHardcoded() {
        final var userFilters = userRepository.findUserFiltersForEnabledUsersHardcoded();
        assertNotNull(userFilters);
        assertFalse(userFilters.isEmpty());
    }

    /**
     * FAILS
     */

    @Test
    void shouldReturnUserFiltersUsingHibernateNativeQuery() {
        final var userFilters = userRepository.findUserFiltersForEnabledUsers(true);
        assertNotNull(userFilters);
        assertFalse(userFilters.isEmpty());
    }

    private List<User> buildUsersList() {
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@email.com");
        user1.setPhoneNumber("1234567891");
        user1.setEnabled(true);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@email.com");
        user2.setPhoneNumber("1234567891");
        user2.setEnabled(true);

        User user3 = new User();
        user3.setFirstName("Mark");
        user3.setLastName("Doe");
        user3.setEmail("mark.doe@email.com");
        user3.setPhoneNumber("1234567891");
        user3.setEnabled(true);

        User user4 = new User();
        user4.setFirstName("Mike");
        user4.setLastName("Doe");
        user4.setEmail("mike.doe@email.com");
        user4.setPhoneNumber("1234567891");
        user4.setEnabled(true);

        return List.of(user1, user2, user3, user4);

    }
}
