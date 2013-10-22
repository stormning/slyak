package com.slyak.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.slyak.user.model.User;
import com.slyak.user.util.UserQuery;

public interface UserExtDao {

	Page<User> pageUsers(Pageable pageable, UserQuery query);

}
