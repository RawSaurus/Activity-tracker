package com.miroslav.acitivity_tracker.comment.repository;

import com.miroslav.acitivity_tracker.RepositoryTestConfig;
import com.miroslav.acitivity_tracker.comment.model.Comment;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Profile profile;
    private Comment comment1;
    private Comment comment2;

    @BeforeEach
    public void setUp(){
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setFirstName("first");
        user.setLastName("last");
        User savedUser = testEntityManager.persist(user);
        Profile profileToSave = Profile.builder()
                .profileId(savedUser.getUserId())
                .user(savedUser)
                .username("username")
                .build();
        profile = testEntityManager.persist(profileToSave);
        Template template = testEntityManager.persist(new Template());
        comment1 = Comment.builder()
                .header("header1")
                .profile(profile)
                .text("text1")
                .likes(1)
                .template(template)
                .build();
        comment2 = Comment.builder()
                .header("header2")
                .profile(profile)
                .text("text2")
                .likes(2)
                .template(template)
                .build();

        commentRepository.saveAll(List.of(comment1, comment2));
   }

    @AfterEach
    public void tearDown(){
        commentRepository.deleteAll();
    }

    @Test
    public void should_find_page_by_template_id(){
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "header");

        Page<Comment> foundPage = commentRepository.findAllByTemplateTemplateId(comment1.getTemplate().getTemplateId(), pageable);

        assertNotNull(foundPage.getContent());
        assertEquals(2, foundPage.getContent().size());
        assertEquals("header2", foundPage.getContent().get(1).getHeader());
    }

    @Test
    public void should_find_by_id(){
        Optional<Comment> foundComment= commentRepository.findById(comment1.getCommentId());

        assertTrue(foundComment.isPresent());
        assertEquals(comment1.getHeader(), foundComment.get().getHeader());
    }

    @Test
    public void should_save_comment(){
        Comment commentToSave = Comment.builder()
                .header("Save")
                .build();
        Comment savedComment = commentRepository.save(commentToSave);

        assertNotNull(savedComment);
        assertEquals(commentToSave.getHeader(), savedComment.getHeader());
    }

    @Test
    public void should_delete_comment(){
        commentRepository.delete(comment2);
        Optional<Comment> deletedComment = commentRepository.findById(comment2.getCommentId());

        assertTrue(deletedComment.isEmpty());
    }
}
