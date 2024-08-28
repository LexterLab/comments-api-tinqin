package com.tinqinacademy.comments.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.RestAPIRoutes;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void shouldRespondWithOKAndRoomCommentsWhenRetrievingRoomComments() throws Exception {

        mockMvc.perform(get(RestAPIRoutes.GET_ROOM_COMMENTS, UUID.fromString("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.roomComments").isArray())
                .andExpect(jsonPath("$.roomComments[0]").isNotEmpty())
                .andExpect(jsonPath("$.roomComments[0].id").isString())
                .andExpect(jsonPath("$.roomComments[0].firstName")
                        .isNotEmpty())
                .andExpect(jsonPath("$.roomComments[0].lastName")
                        .isNotEmpty())
                .andExpect(jsonPath("$.roomComments[0].content")
                        .isNotEmpty());

    }

    @Test
    void shouldRespondWithBadRequestWhenRetrievingRoomCommentsWithInvalidIdFormat() throws Exception {
        GetRoomCommentsInput input = GetRoomCommentsInput.builder()
                .roomId("1213")
                .build();

        mockMvc.perform(get(RestAPIRoutes.GET_ROOM_COMMENTS, input.getRoomId()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field roomId must be UUID"));
    }

    @Test
    void shouldRespondWithCREATEDAndCommentIdWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("George")
                .lastName("Russell")
                .content("Some content")
                .userId(UUID.randomUUID().toString())
                .build();

        UUID roomId = UUID.randomUUID();

        long numberOfCommentsBefore = commentRepository.count();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
               .andExpect(MockMvcResultMatchers.status().isCreated())
               .andExpect(jsonPath("$.id").isString());

        long numberOfCommentsAfter = commentRepository.count();

        Assertions.assertTrue(numberOfCommentsAfter > numberOfCommentsBefore);
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinimumCharsFirstNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("G")
                .lastName("Russell")
                .content("Some content")
                .userId(UUID.randomUUID().toString())
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field firstName must be between 2-30 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinimumCharsLastNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("Gandalf")
                .lastName("R")
                .content("Some content")
                .userId(UUID.randomUUID().toString())
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field lastName must be between 2-30 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinimumCharsContentWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("Gandalf")
                .lastName("Russell")
                .content("Som")
                .userId(UUID.randomUUID().toString())
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field content must be between 5-500 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsFirstNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("GandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalf")
                .lastName("Russell")
                .content("Some content")
                .userId(UUID.randomUUID().toString())
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field firstName must be between 2-30 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsLastNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("Gandalf")
                .lastName("RussellRussellRussellRussellRussellRussellRussellRussellRussellRussellRussellRussell")
                .content("Some content")
                .userId(UUID.randomUUID().toString())
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field lastName must be between 2-30 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsContentWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("Gandalf")
                .lastName("Russell")
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
                .userId(UUID.randomUUID().toString())
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field content must be between 5-500 characters"));
    }


    @Test
    void shouldRespondWithBadRequestWhenProvidingNullContentWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("Gandalf")
                .lastName("Russell")
                .content(null)
                .userId(UUID.randomUUID().toString())
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field content cannot not be blank"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullFirstNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName(null)
                .lastName("Russell")
                .content("some content")
                .userId(UUID.randomUUID().toString())
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field firstName cannot be blank"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullLastNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("George")
                .lastName(null)
                .content("some content")
                .userId(UUID.randomUUID().toString())
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field lastName cannot be blank"));
    }

    @Test
    void shouldRespondWithOKAndCommentIdWhenEditingComment() throws Exception {
        EditCommentInput input = EditCommentInput.builder()
                .content("Some content")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.id").value(commentId));

        Comment updatedComment = commentRepository.findById(UUID.fromString(commentId))
                .orElseThrow(() -> new AssertionError("Comment not found for ID: " + commentId));

        assertEquals(input.getContent(), updatedComment.getContent());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidedBelowMinCharsContentWhenEditingComment() throws Exception {
        EditCommentInput input = EditCommentInput.builder()
                .content("Some")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field content must be 5-500 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidedAboveMaxCharsContentWhenEditingComment() throws Exception {
        EditCommentInput input = EditCommentInput.builder()
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
                .userId(UUID.randomUUID().toString())
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field content must be 5-500 characters"));
    }


    @Test
    void shouldRespondWithBadRequestWhenProvidedNullContentWhenEditingComment() throws Exception {
        EditCommentInput input = EditCommentInput.builder()
                .content(null)
                .userId(UUID.randomUUID().toString())
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field content cannot be blank"));
    }

}