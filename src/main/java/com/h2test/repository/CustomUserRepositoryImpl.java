package com.h2test.repository;

import com.h2test.dto.UserFilters;
import com.h2test.entity.User;
import com.h2test.repository.tuple.UserFiltersTupleTransformer;
import com.h2test.repository.tuple.UserTupleTransformer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final EntityManager entityManager;

    public static final String HIBERNATE_QUERY = "SELECT u.id, u.firstName, u.lastName, u.phoneNumber, u.email " +
            "from User u where u.firstName = :firstName and u.lastName = :lastName";

    public static final String HIBERNATE_NATIVE_QUERY = "SELECT u.id, u.first_name, u.last_name, u.phone_number, u.email " +
            "from users u where u.first_name = :firstName and u.last_name = :lastName";

    public static final String HIBERNATE_COMPLEX_NATIVE_QUERY = "WITH users_filters AS" +
            " (SELECT u.first_name, u.last_name, u.phone_number, u.email FROM users u" +
            " WHERE u.enabled = :enabled )" +
            " SELECT u_filter, u_value FROM ( " +
            " (SELECT DISTINCT 'first_name' AS u_filter, first_name AS u_value FROM users_filters) UNION " +
            " (SELECT DISTINCT 'last_name' AS u_filter, last_name AS u_value FROM users_filters) UNION" +
            " (SELECT DISTINCT 'phone_number' AS u_filter, phone_number AS u_value FROM users_filters) UNION " +
            " (SELECT DISTINCT 'email' AS u_filter, email AS u_value FROM users_filters)) AS filter_list";

    public static final String HIBERNATE_COMPLEX_NATIVE_QUERY_HARDCODED = "WITH users_filters AS" +
            " (SELECT u.first_name, u.last_name, u.phone_number, u.email FROM users u" +
            " WHERE u.enabled = true )" +
            " SELECT u_filter, u_value FROM ( " +
            " (SELECT DISTINCT 'first_name' AS u_filter, first_name AS u_value FROM users_filters) UNION " +
            " (SELECT DISTINCT 'last_name' AS u_filter, last_name AS u_value FROM users_filters) UNION" +
            " (SELECT DISTINCT 'phone_number' AS u_filter, phone_number AS u_value FROM users_filters) UNION " +
            " (SELECT DISTINCT 'email' AS u_filter, email AS u_value FROM users_filters)) AS filter_list";


    @Override
    @SuppressWarnings("unchecked")
    public List<User> findUserByFirstNameAndLastName(String firstName, String lastName) {

        return entityManager.createQuery(HIBERNATE_QUERY)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .unwrap(org.hibernate.query.Query.class)
                .setTupleTransformer(new UserTupleTransformer())
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findUserByFirstNameAndLastNameNative(String firstName, String lastName) {
        return entityManager.createNativeQuery(HIBERNATE_NATIVE_QUERY)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .unwrap(org.hibernate.query.Query.class)
                .setTupleTransformer(new UserTupleTransformer())
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserFilters> findUserFiltersForEnabledUsers(Boolean enabled) {
        return entityManager.createNativeQuery(HIBERNATE_COMPLEX_NATIVE_QUERY)
                .setParameter("enabled", enabled)
                .unwrap(org.hibernate.query.Query.class)
                .setTupleTransformer(new UserFiltersTupleTransformer())
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserFilters> findUserFiltersForEnabledUsersHardcoded() {
        return entityManager.createNativeQuery(HIBERNATE_COMPLEX_NATIVE_QUERY_HARDCODED)
                .unwrap(org.hibernate.query.Query.class)
                .setTupleTransformer(new UserFiltersTupleTransformer())
                .getResultList();
    }


}
