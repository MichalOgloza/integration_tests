package edu.iis.mto.blog.domain.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.model.LikePost;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository repository;

    private LikePost likePost;
    private LikePost likePost2;

    @Before
    public void setUp() {
        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("kowal@domain.com");
        user.setAccountStatus(AccountStatus.CONFIRMED);
        entityManager.persist(user);

        User user2 = new User();
        user2.setFirstName("Piotr");
        user2.setLastName("Nowak");
        user2.setEmail("nowy@domain.com");
        user2.setAccountStatus(AccountStatus.CONFIRMED);
        entityManager.persist(user2);

        BlogPost post = new BlogPost();
        post.setEntry("example");
        post.setUser(user);
        entityManager.persist(post);

        BlogPost post2 = new BlogPost();
        post2.setEntry("example2");
        post2.setUser(user2);
        entityManager.persist(post2);

        likePost = new LikePost();
        likePost.setPost(post);
        likePost.setUser(user2);

        likePost2 = new LikePost();
        likePost2.setPost(post2);
        likePost2.setUser(user);
    }

    @Test
    public void shouldFindNoLikesIfRepositoryIsEmpty() {

        List<LikePost> likes = repository.findAll();

        assertThat(likes, hasSize(0));
    }

    @Test
    public void shouldFindOneLikeIfRepositoryContainsOneLikeEntity() {
        LikePost persistedLike = entityManager.persist(likePost);
        List<LikePost> likes = repository.findAll();

        assertThat(likes, hasSize(1));
        assertThat(likes.get(0)
                        .getUser(),
                equalTo(persistedLike.getUser()));
    }

    @Test
    public void shouldStoreANewLike() {

        LikePost persistedLike = repository.save(likePost);

        assertThat(persistedLike.getId(), notNullValue());
    }

    @Test
    public void findSpecificLikePostTest()
    {
        LikePost persistedLike = entityManager.persist(likePost);
        entityManager.persist(likePost2);
        Optional<LikePost> like = repository.findByUserAndPost(persistedLike.getUser(), persistedLike.getPost());
        assertTrue(like.isPresent());
        assertEquals(persistedLike, like.get());
    }

    @Test
    public void likeNotFoundTest()
    {
        LikePost persistedLike = entityManager.persist(likePost);
        LikePost persistedLike2 = entityManager.persist(likePost2);
        Optional<LikePost> like = repository.findByUserAndPost(persistedLike.getUser(), persistedLike2.getPost());
        assertFalse(like.isPresent());
    }
}
