package com.stanic.pda.bean;

public class MessageWrap {

    public final Object message;

    public static MessageWrap getInstance(Object message){
        return new MessageWrap(message);
    }

    private MessageWrap(Object message){
        this.message = message;
    }
}
