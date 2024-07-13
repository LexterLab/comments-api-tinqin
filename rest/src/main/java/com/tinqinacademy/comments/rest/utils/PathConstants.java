package com.tinqinacademy.comments.rest.utils;

public class PathConstants {
    public static final String ROOT = "/api/v1";
    public static final String HOTEL = "/hotel";
    public static final String SYSTEM = "/system";
    public static final String GET_ROOM_COMMENTS = ROOT + HOTEL +  "/{roomId}/comment";
    public static final String LEAVE_ROOM_COMMENT = ROOT + HOTEL +  "/{roomId}/comment";
    public static final String EDIT_COMMENT = ROOT + HOTEL +  "/comment/{commentId}";
    public static final String EDIT_USER_COMMENT = ROOT + SYSTEM +  "/comment/{commentId}";
    public static final String DELETE_COMMENT = ROOT + SYSTEM +  "/comment/{commentId}";
}