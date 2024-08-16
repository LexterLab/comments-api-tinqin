package com.tinqinacademy.comments.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.RoomCommentOutput;
import com.tinqinacademy.comments.api.RestAPIRoutes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRespondWithOKAndRoomCommentsWhenRetrievingRoomComments() throws Exception {
       RoomCommentOutput output = RoomCommentOutput.builder()
                .content("This is a test comment 1")
                .firstName("John")
                .lastName("Doe")
                .build();

        mockMvc.perform(get(RestAPIRoutes.GET_ROOM_COMMENTS, UUID.fromString("923364b0-4ed0-4a7e-8c23-ceb5c238ceee")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments[0]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments[0].id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments[0].firstName")
                        .value(output.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments[0].lastName")
                        .value(output.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments[0].content")
                        .value(output.getContent()));

    }

    @Test
    void shouldRespondWithBadRequestWhenRetrievingRoomCommentsWithInvalidIdFormat() throws Exception {
        GetRoomCommentsInput input = GetRoomCommentsInput.builder()
                .roomId("1213")
                .build();

        mockMvc.perform(get(RestAPIRoutes.GET_ROOM_COMMENTS, input.getRoomId()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
               .andExpect(MockMvcResultMatchers.status().isCreated())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinimumCharsFirstNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("G")
                .lastName("Russell")
                .content("Some content")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinimumCharsLastNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("Gandalf")
                .lastName("R")
                .content("Some content")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinimumCharsContentWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("Gandalf")
                .lastName("Russell")
                .content("Som")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsFirstNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("GandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalfGandalf")
                .lastName("Russell")
                .content("Some content")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsLastNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("Gandalf")
                .lastName("RussellRussellRussellRussellRussellRussellRussellRussellRussellRussellRussellRussell")
                .content("Some content")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
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
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingEmptyContentWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("Gandalf")
                .lastName("Russell")
                .content("")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankContentWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("Gandalf")
                .lastName("Russell")
                .content(" ")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullContentWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("Gandalf")
                .lastName("Russell")
                .content(null)
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingEmptyFirstNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("")
                .lastName("Russell")
                .content("some content")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankFirstNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName(" ")
                .lastName("Russell")
                .content("some content")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullFirstNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName(null)
                .lastName("Russell")
                .content("some content")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingEmptyLastNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("George")
                .lastName("")
                .content("some content")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankLastNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("George")
                .lastName(" ")
                .content("some content")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullLastNameWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("George")
                .lastName(null)
                .content("some content")
                .build();

        UUID roomId = UUID.randomUUID();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, roomId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(commentId));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidedBelowMinCharsContentWhenEditingComment() throws Exception {
        EditCommentInput input = EditCommentInput.builder()
                .content("Some")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
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
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidedEmptyContentWhenEditingComment() throws Exception {
        EditCommentInput input = EditCommentInput.builder()
                .content("")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidedBlankContentWhenEditingComment() throws Exception {
        EditCommentInput input = EditCommentInput.builder()
                .content(" ")
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidedNullContentWhenEditingComment() throws Exception {
        EditCommentInput input = EditCommentInput.builder()
                .content(null)
                .build();

        String commentId = "1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d";

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}