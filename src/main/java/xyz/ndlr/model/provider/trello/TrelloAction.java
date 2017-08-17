package xyz.ndlr.model.provider.trello;

import xyz.ndlr.model.provider.IProviderAction;

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

    private final String value;

    TrelloAction(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
