package com.h2test.repository.tuple;

import com.h2test.dto.UserFilters;
import org.hibernate.query.TupleTransformer;

public class UserFiltersTupleTransformer implements TupleTransformer<UserFilters> {
    @Override
    public UserFilters transformTuple(Object[] tuple, String[] aliases) {
        int position = 0;
        return UserFilters.builder()
                .filter(String.valueOf(tuple[position++]))
                .value(String.valueOf(tuple[position]))
                .build();
    }
}
