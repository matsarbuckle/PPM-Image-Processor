import java.awt.*;

public abstract class Image implements Writable {

    //Instance Varibles
    private int width;

    private int height;

    private Color[][] pixels;

    //Constructor
    public Image(int width, int height){
        this.width = width;
        this.height = height;
        this.pixels = new Color[this.height][this.width];
    }

    //Accessors
    public Color[][] getColors(){
        return this.pixels;
    }
    public void setColors(Color[][] colors) {
        this.pixels = colors;
    }
    public Color getColorAtIndex(int i, int j) {
        return this.pixels[i][j];
    }
    public int getHeight() {
        return this.height;
    }
    public int getWidth() {
        return this.width;
    }



    //Mutators
    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setColor(int i, int j, int r, int g, int b){
        this.pixels[i][j] = new Color(r, g, b);
    }
}
