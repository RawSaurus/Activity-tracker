package com.miroslav.acitivity_tracker.comment.service;

import com.miroslav.acitivity_tracker.comment.dto.CommentRequest;
import com.miroslav.acitivity_tracker.comment.dto.CommentResponse;
import com.miroslav.acitivity_tracker.comment.mapper.CommentMapper;
import com.miroslav.acitivity_tracker.comment.mapper.CommentMapperImpl;
import com.miroslav.acitivity_tracker.comment.model.Comment;
import com.miroslav.acitivity_tracker.comment.repository.CommentRepository;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.template.repository.TemplateRepository;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private UserContext userContext;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TemplateRepository templateRepository;
    @Spy
    private CommentMapper commentMapper = new CommentMapperImpl();
    @InjectMocks
    private CommentService commentService;

    CommentServiceTest(){
        MockitoAnnotations.openMocks(this);
    }


    User user;
    private Profile profile;
    private Template template;
    private Comment comment1;
    private Comment comment2;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setUserId(1);
        user.setEmail("email@gmail.com");
        user.setFirstName("first");
        user.setLastName("last");
        profile= Profile.builder()
                .profileId(user.getUserId())
                .user(user)
                .username("username")
                .build();
        template = Template.builder()
                .templateId(1)
                .profile(profile)
                .name("template")
                .comments(new ArrayList<>())
                .build();
        comment1 = Comment.builder()
                .commentId(1)
                .header("header1")
                .profile(profile)
                .text("text1")
                .likes(1)
                .template(template)
                .build();
        comment2 = Comment.builder()
                .commentId(2)
                .header("header2")
                .profile(profile)
                .text("text2")
                .likes(2)
                .template(template)
                .build();
    }

    @Test
    public void should_find_by_id(){
        when(commentRepository.findById(comment1.getCommentId())).thenReturn(Optional.of(comment1));

        CommentResponse res = commentService.findById(comment1.getCommentId());

        assertNotNull(res);
        assertEquals(comment1.getHeader(), res.header());
        assertEquals(comment1.getText(), res.text());
    }

    @Test
    public void should_find_page_by_template_id(){
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "header");
        Page<Comment> page = new PageImpl<>(List.of(comment1, comment2), pageable, 2);

        when(commentRepository.findAllByTemplateTemplateId(any(), any())).thenReturn(page);

        Page<CommentResponse> res = commentService.findAllByTemplateId(1, pageable);

        assertNotNull(res.getContent());
        assertEquals(2, res.getContent().size());
        assertEquals("header2", res.getContent().get(1).header());
    }

    @Test
    public void should_create_comment(){
        CommentRequest request = new CommentRequest("header", "text");

        when(templateRepository.findById(template.getTemplateId())).thenReturn(Optional.of(template));
        when(commentRepository.save(any())).thenReturn(comment1);

        Integer res = commentService.createComment(request, template.getTemplateId());

        assertNotNull(res);
        assertEquals(1, res);
    }

    @Test
    public void should_update_comment(){
        CommentRequest request = new CommentRequest("header upd", "text upd");

        when(commentRepository.findById(comment1.getCommentId())).thenReturn(Optional.of(comment1));
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(commentRepository.save(comment1)).thenReturn(comment1);

        CommentResponse res = commentService.updateComment(comment1.getCommentId(), request);

        assertNotNull(res);
        assertEquals("header upd", res.header());
        assertEquals("text upd", res.text());
    }

    @Test
    public void should_delete_comment(){

        when(commentRepository.findById(comment1.getCommentId())).thenReturn(Optional.of(comment1));
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        String res = commentService.deleteComment(comment1.getCommentId());

        assertEquals("Comment deleted successfully", res);
        verify(commentRepository, times(1)).delete(comment1);
    }

}
