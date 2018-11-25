package xyz.ndlr.interfaces.rest;

class Route {
    static final String ME = "/me";
    static final String ID = "/{" + Attribute.ID + "}";

    class Attribute {
        static final String ID = "id";
    }
}
