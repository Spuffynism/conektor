package xyz.ndlr.model.provider.mathpix;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Region {
    @JsonProperty("top_left_x")
    private int topLeftX;
    @JsonProperty("top_left_y")
    private int topLeftY;
    @JsonProperty("width")
    private int width;
    @JsonProperty("height")
    private int height;

    public void setCoordinates(int topLeftX, int topLeftY) {
        this.setTopLeftX(topLeftX);
        this.setTopLeftY(topLeftY);
    }

    public void setSize(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    public int getTopLeftX() {
        return topLeftX;
    }

    public void setTopLeftX(int topLeftX) {
        this.topLeftX = topLeftX;
    }

    public int getTopLeftY() {
        return topLeftY;
    }

    public void setTopLeftY(int topLeftY) {
        this.topLeftY = topLeftY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
