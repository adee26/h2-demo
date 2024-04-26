package com.h2test.repository.tuple;

import com.h2test.entity.User;
import org.hibernate.query.TupleTransformer;

public class UserTupleTransformer implements TupleTransformer<User> {
    @Override
    public User transformTuple(Object[] tuple, String[] aliases) {
        int position = 0;
        User user = new User();
        user.setId(Integer.parseInt(String.valueOf(tuple[position++])));
        user.setFirstName(String.valueOf(tuple[position++]));
        user.setLastName(String.valueOf(tuple[position++]));
        user.setPhoneNumber(String.valueOf(tuple[position++]));
        user.setEmail(String.valueOf(tuple[position]));
        return user;
    }
}
