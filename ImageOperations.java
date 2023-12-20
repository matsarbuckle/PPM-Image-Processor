import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ImageOperations {

    public static void main(String[] args) {

        // TEST CASES FROM TERMINAL ARGUMENT
        String expression = args[0];
        switch(expression) {
            case ("--zerored"):
                Image image = new PpmImage(args[1]);
                String output = args[2];
                zeroRed(image).output(output);
                break;

            case ("--greyscale"):
                Image imageT = new PpmImage(args[1]);
                String outputT = args[2];
                greyscale(imageT).output(outputT);
                break;
            case ("--invert"):
                Image imageTH = new PpmImage(args[1]);
                String outputTH = args[2];
                invert(imageTH).output(outputTH);
                break;
            case ("--crop"):
                int x = Integer.parseInt(args[1]);
                int y = Integer.parseInt(args[2]);
                int w = Integer.parseInt(args[3]);
                int h = Integer.parseInt(args[4]);
                Image imageF = new PpmImage(args[5]);
                String outputF = args[6];
                crop(imageF, x, y, h, w).output(outputF);
                break;
            case ("--mirror"):
                String dir = args[1];
                Image imageFV = new PpmImage(args[2]);
                String outputFV = args[3];
                mirror(imageFV, dir).output(outputFV);
                break;
            case ("--repeat"):
                String dirc = args[1];
                int n = Integer.parseInt(args[2]);
                Image imageS = new PpmImage(args[3]);
                String outputS = args[4];
                repeat(imageS, n, dirc).output(outputS);
                break;
        }

    }


    /**
     * Image -> Image
     * Takes an Image and sets every red pixel to zero
     * Hence removing all red from the photo
     * @param img - Image
     * @return - returns an Image that has no red pixels.
     */
    public static Image zeroRed(Image img) {

        Image newImg = new PpmImage(img.getWidth(), img.getHeight());

        // Read the colors and set r = 0
        for (int h = 0; h < img.getHeight(); h++) { // Height
            for (int w = 0; w < img.getWidth(); w++) { // Width
                int r = 0; // set red pixel to zero
                int g = img.getColorAtIndex(h, w).getGreen();
                int b = img.getColorAtIndex(h, w).getBlue();
                newImg.setColor(h, w, r, g, b);
            }
        }

        return newImg;
    }

    /**
     * Image -> Image
     * takes an image and inverts the color pixel data
     * @param img - Image
     * @return - returns an image with the color pixel data inverted
     */
    public static Image invert(Image img) {
        Image newImg = new PpmImage(img.getWidth(), img.getHeight());

        for (int h = 0; h < img.getHeight(); h++) { // Height
            for (int w = 0; w < img.getWidth(); w++) { // Width
                int r = Math.abs(img.getColorAtIndex(h, w).getRed() - 255); // inverting color data
                int g = Math.abs(img.getColorAtIndex(h, w).getGreen() - 255); // inverting color data
                int b = Math.abs(img.getColorAtIndex(h, w).getBlue() - 255); // inverting color data
                newImg.setColor(h, w, r, g, b);
            }
        }

        return newImg;
    }

    /**
     * Image -> Image
     * takes an image and changes the color pixel data
     * to black, grey, and white.
     * @param img - Image
     * @return - returns an image with black, white, and grey pixel data
     */
    public static Image greyscale(Image img) {

        Image newImg = new PpmImage(img.getWidth(), img.getHeight());
        // Read the colors and set r = 0
        for (int h = 0; h < img.getHeight(); h++) { // Height
            for (int w = 0; w < img.getWidth(); w++) { // Width
                int r = img.getColorAtIndex(h, w).getRed();
                int g = img.getColorAtIndex(h, w).getGreen();
                int b = img.getColorAtIndex(h, w).getBlue();
                int average = (r + g + b) / 3; // averaging the colors converts it to greyscale
                newImg.setColor(h, w, average, average, average); // set average for r,g,b
            }
        }
        return newImg;
    }

    /**
     * Image Integer Integer Integer Integer -> Image
     * Crop the given image via integer inputs x,y,h,w
     * @param img - Image
     * @param x - int [x direction]
     * @param y - int [y direction]
     * @param h - int [Height]
     * @param w - int [Width]
     * @return returns new cropped image based on the given (x,y) starting point
     * and the new height and width values.
     */
    public static Image crop(Image img, int x, int y, int h, int w) {
        Image newImg = new PpmImage(w, h);

        //Starts @ (x,y) to (x + w, y + h)
        for (int s = y; s < y + h; s++) { // Height [denoted by "s"]
            for (int t = x; t < x + w; t++) { // Width [denoted by "t"]
                int r = img.getColorAtIndex(s, t).getRed();
                int g = img.getColorAtIndex(s, t).getGreen();
                int b = img.getColorAtIndex(s, t).getBlue();
                newImg.setColor(s - y, t - x, r, g, b);
            }
        }
        return newImg;
    }


    /**
     * Image String -> Image
     * takes an image and mirrors the image based on the given String either horizontally, or vertically.
     * @param img - Image
     * @param mode - String (direction [Horizontal = H|Vertical = V])
     * @return -  mirrors the given image based on the given String either horizontally, or vertically.
     */
    public static Image mirror(Image img, String mode) {
        Image newImg = new PpmImage(img.getWidth(), img.getHeight());

        if (mode.equals("H")) { //Horizontal Mirror
            // NOTE : w = height  and h = width !!
            for (int w = 0; w < newImg.getHeight(); w++) { // Height
                for (int h = 0; h < newImg.getWidth(); h++) { // Width
                    int r = img.getColorAtIndex(w, h).getRed();
                    int g = img.getColorAtIndex(w, h).getGreen();
                    int b = img.getColorAtIndex(w, h).getBlue();
                    newImg.setColor(w, h, r, g, b);

                    //when height exceeds half the images height => Mirror
                    if (w >= newImg.getHeight() / 2) {
                        int nr = img.getColorAtIndex(newImg.getHeight() - (w - 1), h).getRed();
                        int ng = img.getColorAtIndex(newImg.getHeight() - (w - 1), h).getGreen();
                        int nb = img.getColorAtIndex(newImg.getHeight() - (w - 1), h).getBlue();
                        newImg.setColor(w, h, nr, ng, nb);
                    }
                }
            }

        } else if (mode.equals("V")) { // Vertical Mirror
            // NOTE : w = height  and h = width !!
            for (int w = 0; w < newImg.getHeight(); w++) { // Height
                for (int h = 0; h < newImg.getWidth(); h++) { // Width
                    int r = img.getColorAtIndex(w, h).getRed();
                    int g = img.getColorAtIndex(w, h).getGreen();
                    int b = img.getColorAtIndex(w, h).getBlue();
                    newImg.setColor(w, h, r, g, b);

                    //when width exceeds half the images height => flip
                    if (h >= newImg.getWidth() / 2) {
                        int nr = img.getColorAtIndex(w, newImg.getWidth() - (h - 1)).getRed();
                        int ng = img.getColorAtIndex(w, newImg.getWidth() - (h - 1)).getGreen();
                        int nb = img.getColorAtIndex(w, newImg.getWidth() - (h - 1)).getBlue();
                        newImg.setColor(w, h, nr, ng, nb);
                    }
                }
            }
        }
        return newImg;
    }

    /**
     * Image Integer String -> Image
     * takes an image and repeats the same image n # of times in either horizontally, or vertically
     * @param img - Image
     * @param n - int (n # of images)
     * @param dir - String (direction [Horizontal = H|Vertical = V])
     * @return returns an image with the image repeated either
     * horizontally, or vertically n # of times
     */

    public static Image repeat(Image img, int n, String dir) {
        Image newImg;

        if (dir.equals("V")) { //Repeat in the Vertical Direction
            newImg = new PpmImage(img.getWidth(), img.getHeight() * n);
            for (int i = 0; i < n; i++) {
                for (int h = (i * img.getHeight()); h < newImg.getHeight(); h++) { // Height
                    for (int w = 0; w < newImg.getWidth(); w++) { // Width
                        if (h > img.getHeight() - 1) { //When width becomes greater than old width
                            // Current height % (old height - 1)
                            int nr = img.getColorAtIndex(h % (img.getHeight() - 1), w).getRed();
                            int ng = img.getColorAtIndex(h % (img.getHeight() - 1), w).getGreen();
                            int nb = img.getColorAtIndex(h % (img.getHeight() - 1), w).getBlue();
                            newImg.setColor(h, w, nr, ng, nb);
                        } else {
                            // Old Values for first image in the repeater
                            int r = img.getColorAtIndex(h, w).getRed();
                            int g = img.getColorAtIndex(h, w).getGreen();
                            int b = img.getColorAtIndex(h, w).getBlue();
                            newImg.setColor(h, w, r, g, b);
                        }
                    }
                }
            }
            return newImg;
        }else if(dir.equals("H")){ //Repeat in the Horizontal Direction
            newImg = new PpmImage(img.getWidth() * n, img.getHeight());
            for (int i = 0; i < n; i++) {
                for (int h = 0; h < newImg.getHeight(); h++) { // Height
                    for (int w = (i * img.getWidth()); w < newImg.getWidth(); w++) { // Width

                        if (w > img.getWidth() - 1) { //When width becomes greater than old width
                            // Current width % (old width - 1)
                            int nr = img.getColorAtIndex(h, w % (img.getWidth() - 1)).getRed();
                            int ng = img.getColorAtIndex(h, w % (img.getWidth() - 1)).getGreen();
                            int nb = img.getColorAtIndex(h, w % (img.getWidth() - 1)).getBlue();
                            newImg.setColor(h, w, nr, ng, nb);
                        } else {
                            // Old Values for first image in the repeater
                            int r = img.getColorAtIndex(h, w).getRed();
                            int g = img.getColorAtIndex(h, w).getGreen();
                            int b = img.getColorAtIndex(h, w).getBlue();
                            newImg.setColor(h, w, r, g, b);
                        }
                    }
                }
            }
            return newImg;
        }
        return null;
    }
}
