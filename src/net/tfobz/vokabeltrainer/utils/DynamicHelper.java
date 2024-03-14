package net.tfobz.vokabeltrainer.utils;

public class DynamicHelper {
    private double sizeWidth_relative;
    private double sizeHeight_relative;
    private double locationX_relative;
    private double locationY_relative;
    private int windowWidth;
    private int windowHeight;
    public enum Location { LEFT, RIGHT, CENTER }
    private Location mode = Location.LEFT;
    private int minWidthComponent_absolute = 0;
    private int minHeightComponent_absolute = 0;

    public DynamicHelper(int sizeWidth_relative, int sizeHeight_relative, int locationX_relative, int locationY_relative, int windowWidth, int windowHeight) throws IllegalArgumentException {
        this.sizeWidth_relative = (double) sizeWidth_relative;
        this.sizeHeight_relative = (double) sizeHeight_relative;
        this.locationX_relative = (double) locationX_relative;
        this.locationY_relative = (double) locationY_relative;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public DynamicHelper(int sizeWidth_relative, int sizeHeight_relative, int locationX_relative, int locationY_relative, int windowWidth, int windowHeight, Location mode) throws IllegalArgumentException {
        this.sizeWidth_relative = (double) sizeWidth_relative;
        this.sizeHeight_relative = (double) sizeHeight_relative;
        this.locationX_relative = locationX_relative;
        this.locationY_relative = locationY_relative;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.mode = mode;
    }

    public DynamicHelper(int sizeWidth_relative, int sizeHeight_relative, int locationX_relative, int locationY_relative, int windowWidth, int windowHeight, int minWidthComponent_absolute, int minHeightComponent_absolute) throws IllegalArgumentException {
        this.sizeWidth_relative = (double) sizeWidth_relative;
        this.sizeHeight_relative = (double) sizeHeight_relative;
        this.locationX_relative = locationX_relative;
        this.locationY_relative = locationY_relative;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.minWidthComponent_absolute = minWidthComponent_absolute;
        this.minHeightComponent_absolute = minHeightComponent_absolute;
    }

    public DynamicHelper(int sizeWidth_relative, int sizeHeight_relative, int locationX_relative, int locationY_relative, int windowWidth, int windowHeight, int minWidthComponent_absolute, int minHeightComponent_absolute, Location mode) throws IllegalArgumentException {
        this.sizeWidth_relative = (double) sizeWidth_relative;
        this.sizeHeight_relative = (double) sizeHeight_relative;
        this.locationX_relative = locationX_relative;
        this.locationY_relative = locationY_relative;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.minWidthComponent_absolute = minWidthComponent_absolute;
        this.minHeightComponent_absolute = minHeightComponent_absolute;
        this.mode = mode;
    }

    public int getX() {
        switch (mode) {
            case LEFT:
                return (int) (this.windowWidth / 100. * this.locationX_relative);
            case CENTER:
                return (int) ((this.windowWidth / 100. * locationX_relative) - (this.getWidth() / 2.));
            default:
                return (int) ((this.windowWidth / 100. * locationX_relative) - (this.getWidth() / 2.));
        }
    }

    public int getY() {
        switch (mode) {
            case LEFT:
                return (int) (this.windowHeight / 100. * this.locationY_relative);
            case CENTER:
                return (int) ((this.windowHeight / 100. * locationY_relative) - (this.getHeight() / 2.));
            default:
                return (int) ((this.windowHeight / 100. * locationY_relative) - (this.getHeight() / 2.));

        }
    }

    public int getWidth() {
        int result =  (int) (this.windowWidth / 100. * this.sizeWidth_relative);
        if (result < this.minWidthComponent_absolute / 2)
            return this.minWidthComponent_absolute / 2;
        return result;
    }

    public int getHeight() {
        int result = (int) (this.windowHeight / 100. * this.sizeHeight_relative);
        if (result < this.minHeightComponent_absolute / 2)
            return this.minHeightComponent_absolute / 2;
        return result;
    }

    public void updateWindowSize(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }
}