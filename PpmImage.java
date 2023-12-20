
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class PpmImage extends Image {

    //Importing user Image input --- Scanner Class


    //Constructor : instantiates each pixel to black
    public PpmImage(int width, int height) {
        super(width, height);
        for (int w = 0; w < this.getHeight(); w++) {
            for (int h = 0; h < this.getWidth(); h++) {
                this.setColor(w, h, 0, 0, 0); //(r: 0 , g: 0 , b : 0) => black pixel
            }
        }
    }

    //Constructor : Reads data from a provided .ppm file name
    public PpmImage(String image) {
        super(0, 0);
        readImage(image); // => Uses readImage instance method
    }

    /**
     * String -> void
     * Takes a file in the for a String .txt and reads the images pixel data.
     * @param file - String (the file)
     */
    void readImage(String file){
        try {
            Scanner in = new Scanner(new File(file));
            //Skip header data
            String header = in.nextLine();
            int width = in.nextInt();
            int height = in.nextInt();
            in.nextLine(); // Skip the newline.
            int colorData = in.nextInt();
            in.nextLine();
            this.setWidth(width);
            this.setHeight(height);
            this.setColors(new Color[height][width]);

            // Read the colors.
            // NOTE : w = height direction and h = width direction
            for(int w = 0; w < this.getColors().length; w++){ // Height
                for(int h = 0; h < this.getColors()[0].length; h++){ // Width
                    int r = in.nextInt();
                    int g = in.nextInt();
                    int b = in.nextInt();
                    this.setColor(w, h, r, g, b);
                }
                if (in.hasNextLine()) {
                    in.nextLine();
                } else {
                    break;
                }
            }
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Takes a String .txt file name and serialize its data to a file with the provided file name
     * This is what allows our methods in ImageOperations to output edited images into file
     * @param filename
     */
    @Override
    public void output(String filename) {

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filename));
            //Print Header
            pw.println("P3");
            pw.println(this.getWidth()); //Width
            pw.println(this.getHeight()); //Height
            pw.println(255); //Color Data max

            //serialize data to a file with the provided file name
            for (int w = 0; w < this.getHeight(); w++) {
                for (int h = 0; h < this.getWidth(); h++) {
                    int r = this.getColorAtIndex(w,h).getRed();
                    int g = this.getColorAtIndex(w,h).getGreen();
                    int b = this.getColorAtIndex(w,h).getBlue();
                    pw.printf("%d %d %d ", r, g, b);
                }
                pw.println();
            }
            pw.close();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }


}
