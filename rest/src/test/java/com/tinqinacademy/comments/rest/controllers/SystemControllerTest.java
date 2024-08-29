package com.tinqinacademy.comments.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.Messages;
import com.tinqinacademy.comments.api.RestAPIRoutes;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SystemControllerTest extends BaseIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void shouldRespondWithOKAndCommentIdWhenEditingUserComment() throws Exception {
        EditUserCommentInput input = EditUserCommentInput.builder()
                .content("Some content")
                .lastName("Russell")
                .firstName("George")
                .roomId("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "2c3a4d9e-2d4c-4e67-8f61-f1f0a7e5e4d1";

        mockMvc.perform(put(RestAPIRoutes.EDIT_USER_COMMENT, commentId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString());

        Comment comment = commentRepository.findById(UUID.fromString(commentId))
                .orElseThrow(() -> new AssertionError("Comment not found"));

        assertEquals(input.getContent(), comment.getContent());
        assertEquals(input.getLastName(), comment.getLastName());
        assertEquals(input.getFirstName(), comment.getFirstName());
        assertEquals(input.getRoomId(), comment.getRoomId().toString());
        assertEquals(input.getUserId(), comment.getUserId().toString());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullContentWhenEditingUserComment() throws Exception {
        EditUserCommentInput input = EditUserCommentInput.builder()
                .content(null)
                .lastName("Russell")
                .firstName("George")
                .roomId("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(put(RestAPIRoutes.EDIT_USER_COMMENT, commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field content cannot be blank"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsContentWhenEditingUserComment() throws Exception {
        EditUserCommentInput input = EditUserCommentInput.builder()
                .content("The rapid advancement of technology in" +
                        " the 21st century has brought about significant changes" +
                        " in various aspects of human life. From the way we communicate" +
                        " to how we conduct business, technological innovations have revolutionized our" +
                        " daily routines. The emergence of the internet, mobile devices, and social media " +
                        "platforms has created a more interconnected world, enabling people to share information" +
                        " and stay in touch regardless of geographical boundaries. This digital transformation has " +
                        "also led to the development of new industries and job opportunities, particularly in the fields " +
                        "of software development, data analysis, and cybersecurity. However, these advancements come with their " +
                        "own set of challenges, such as concerns over privacy, data security, and the digital divide between those " +
                        "who have access to technology and those who do not. As we continue to embrace and integrate these technologies" +
                        " into our lives, it is crucial to address these challenges and ensure that the benefits of technological " +
                        "progress are accessible to all members of society. Moreover, the ethical implications of technologies like " +
                        "artificial intelligence and machine learning must be carefully considered to prevent potential misuse and ensure " +
                        "they are used for the greater good.")
                .lastName("Russell")
                .firstName("George")
                .roomId("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(put(RestAPIRoutes.EDIT_USER_COMMENT, commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field content must be 5-500 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinCharsContentWhenEditingUserComment() throws Exception {
        EditUserCommentInput input = EditUserCommentInput.builder()
                .content("some")
                .lastName("Russell")
                .firstName("George")
                .roomId("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(put(RestAPIRoutes.EDIT_USER_COMMENT, commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field content must be 5-500 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinCharsLastNameWhenEditingUserComment() throws Exception {
        EditUserCommentInput input = EditUserCommentInput.builder()
                .content("content")
                .lastName("R")
                .firstName("George")
                .roomId("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(put(RestAPIRoutes.EDIT_USER_COMMENT, commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field lastName must be 2-30 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsLastNameWhenEditingUserComment() throws Exception {
        EditUserCommentInput input = EditUserCommentInput.builder()
                .content("content")
                .lastName("RussellRussellRussellRussellRussellRussellRussellRussellRussellRussellRussellRussellRussell")
                .firstName("George")
                .roomId("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(put(RestAPIRoutes.EDIT_USER_COMMENT, commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field lastName must be 2-30 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullLastNameWhenEditingUserComment() throws Exception {
        EditUserCommentInput input = EditUserCommentInput.builder()
                .content("content")
                .lastName(null)
                .firstName("George")
                .roomId("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(put(RestAPIRoutes.EDIT_USER_COMMENT, commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field lastName cannot be blank"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsFirstNameWhenEditingUserComment() throws Exception {
        EditUserCommentInput input = EditUserCommentInput.builder()
                .content("content")
                .lastName("Russell")
                .firstName("GeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorge")
                .roomId("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(put(RestAPIRoutes.EDIT_USER_COMMENT, commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field firstName must be 2-30 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinCharsFirstNameWhenEditingUserComment() throws Exception {
        EditUserCommentInput input = EditUserCommentInput.builder()
                .content("content")
                .lastName("Russell")
                .firstName("G")
                .roomId("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(put(RestAPIRoutes.EDIT_USER_COMMENT, commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field firstName must be 2-30 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullFirstNameWhenEditingUserComment() throws Exception {
        EditUserCommentInput input = EditUserCommentInput.builder()
                .content("content")
                .lastName("Russell")
                .firstName(null)
                .roomId("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(put(RestAPIRoutes.EDIT_USER_COMMENT, commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field firstName cannot be blank"));
    }

    @Test
    void shouldRespondWithOKWhenDeletingUserComment() throws Exception {
        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(delete(RestAPIRoutes.DELETE_COMMENT, commentId))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRespondWithBadRequestWhenDeletingUserCommentWithInvalidFormatId() throws Exception {
        String commentId = "id";

        mockMvc.perform(delete(RestAPIRoutes.DELETE_COMMENT, commentId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field commentId must be UUID"));
    }

    @Test
    void shouldRespondWithResourceNotFoundWhenDeletingUnknownRoomComment() throws Exception {
        String commentId = UUID.randomUUID().toString();

        mockMvc.perform(delete(RestAPIRoutes.DELETE_COMMENT, commentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].message")
                        .value(String.format(Messages.RESOURCE_NOT_FOUND, "Comment", "id", commentId)));
    }
}