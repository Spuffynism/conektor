package com.springmvc.model.provider.trello;

import com.springmvc.model.provider.IProviderAction;

public enum TrelloAction implements IProviderAction {
    ADD_CARD_TO_LIST ("add"),
    DELETE_CARD_FROM_LIST ("delete"),
    CREATE_BOARD ("create-board"),
    CREATE_LIST("create-list"),
    SWITCH_TO("switch-to"),
    BOARD("board");

    private String action;

    TrelloAction(String action) {
        this.action = action;
    }

}
