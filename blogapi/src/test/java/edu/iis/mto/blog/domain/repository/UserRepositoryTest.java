package edu.iis.mto.blog.domain.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User user;
    private User user2;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);
        user2 = new User();
        user2.setFirstName("Piotr");
        user2.setEmail("nowak@domain.com");
        user2.setAccountStatus(AccountStatus.NEW);
    }

    @Test
    public void shouldFindNoUsersIfRepositoryIsEmpty() {

        List<User> users = repository.findAll();

        assertThat(users, hasSize(0));
    }

    @Test
    public void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(user);
        List<User> users = repository.findAll();

        assertThat(users, hasSize(1));
        assertThat(users.get(0)
                        .getEmail(),
                equalTo(persistedUser.getEmail()));
    }

    @Test
    public void shouldStoreANewUser() {

        User persistedUser = repository.save(user);

        assertThat(persistedUser.getId(), notNullValue());
    }

    @Test
    public void findSpecificUserTest() {
        User persistedUser = entityManager.persist(user);
        entityManager.persist(user2);
        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("Jan", "", "john@domain.com");
        assertThat(users, hasSize(1));
        assertEquals(users.get(0), persistedUser);
    }

    @Test
    public void findUserContainingSpecifiedStringsInNameAndEmailTest() {
        User persistedUser = entityManager.persist(user);
        entityManager.persist(user2);
        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("an", "", "hn");
        assertThat(users, hasSize(1));
        assertEquals(users.get(0), persistedUser);
    }

    @Test
    public void findUserIgnoreCaseTest() {
        User persistedUser = entityManager.persist(user);
        entityManager.persist(user2);
        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("jAN", "", "JOHN@dOmAiN.cOm");
        assertThat(users, hasSize(1));
        assertEquals(users.get(0), persistedUser);
    }

    @Test
    public void userNotFoundTest() {
        User persistedUser = entityManager.persist(user);
        entityManager.persist(user2);
        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("Janusz", "", "kowalski@domain.com");
        assertThat(users, hasSize(0));
    }

    @Test
    public void findFirstUserByNameAndSecondByEmail() {
        User persistedUser = entityManager.persist(user);
        entityManager.persist(user2);
        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("Jan", "", "nowak@domain.com");
        assertThat(users, hasSize(2));
    }

}
