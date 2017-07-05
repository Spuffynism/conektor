package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.IProviderAction;

public enum TrelloAction implements IProviderAction {
    ADD("add"),
    DELETE("delete"),
    REMOVE("remove"),
    BOARD("board"),
    BOARDS("boards"),
    LIST("list"),
    LISTS("lists"),
    CARD("card"),
    CARDS("cards"),
    SWITCH_TO("switch-to");

    private String value;

    TrelloAction(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
