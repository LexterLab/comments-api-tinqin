package com.tinqinacademy.comments.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.roomcomment.RoomCommentOutput;
import com.tinqinacademy.comments.api.RestAPIRoutes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRespondWithOKAndRoomCommentsWhenRetrievingRoomComments() throws Exception {
       RoomCommentOutput output = RoomCommentOutput.builder()
                .content("Content")
                .lastEditedBy("Me")
                .firstName("George")
                .lastName("Russell")
                .build();

        mockMvc.perform(get(RestAPIRoutes.GET_ROOM_COMMENTS, 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments[0]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments[0].id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments[0].firstName")
                        .value(output.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments[0].lastName")
                        .value(output.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments[0].lastEditedBy")
                        .value(output.getLastEditedBy()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomComments[0].content")
                        .value(output.getContent()));

    }

    @Test
    void shouldRespondWithCREATEDAndCommentIdWhenLeavingRoomComment() throws Exception {
        LeaveRoomCommentInput input = LeaveRoomCommentInput.builder()
                .firstName("George")
                .lastName("Russell")
                .content("Some content")
                        .build();

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
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

        mockMvc.perform(post(RestAPIRoutes.LEAVE_ROOM_COMMENT, 2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldRespondWithOKAndCommentIdWhenEditingComment() throws Exception {
        EditCommentInput input = EditCommentInput.builder()
                .content("Some content")
                .build();

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidedBelowMinCharsContentWhenEditingComment() throws Exception {
        EditCommentInput input = EditCommentInput.builder()
                .content("Some")
                .build();

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, 1)
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

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, 1)
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

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, 1)
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

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, 1)
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

        mockMvc.perform(patch(RestAPIRoutes.EDIT_COMMENT, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}