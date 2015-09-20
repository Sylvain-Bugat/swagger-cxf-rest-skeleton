
package com.github.sbugat.samplerest.data;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.sbugat.samplerest.dao.UserDao;
import com.github.sbugat.samplerest.model.User;

@Named
public class UserData {

	@Inject
	private UserDao userDao;

	@PostConstruct
	public void initializeData() {
		userDao.save(createUser("admin1", "password1", "user1@domain.com", "lastname1", "firstname1", "ROLE_USER, ROLE_ADMIN", 1));
		userDao.save(createUser("admin2", "password2", "user2@domain.com", "lastname2", "firstname2", "ROLE_USER, ROLE_ADMIN", 2));

		userDao.save(createUser("user1", "password1", "user1@domain.com", "lastname1", "firstname1", "ROLE_USER", 1));
		userDao.save(createUser("user2", "password2", "user2@domain.com", "lastname2", "firstname2", "ROLE_USER", 2));
		userDao.save(createUser("user3", "password3", "user3@domain.com", "lastname3", "firstname3", "ROLE_USER", 3));
		userDao.save(createUser("user4", "password4", "user4@domain.com", "lastname4", "firstname4", "ROLE_USER", 4));
		userDao.save(createUser("user5", "password5", "user5@domain.com", "lastname5", "firstname5", "ROLE_USER", 1));
	}

	private static User createUser(final String username, final String password, final String email, final String lastName, final String firstName, final String roles, final int userStatus) {

		final User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setRoles(roles);
		user.setUserStatus(userStatus);

		return user;
	}
}