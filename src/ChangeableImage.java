import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ChangeableImage {

    private BufferedImage myPicture;

    public ChangeableImage(String filepath){
        try {
            myPicture = ImageIO.read(new File(filepath));
        }
        catch(Exception e){
            System.err.println("Unable to load image: " + filepath);
            e.printStackTrace();
        }
    }

    public void grayScale(){
        for  (int height=0; height < myPicture.getHeight(); height++){
            for (int width=0; width < myPicture.getWidth(); width++){
                Color pixelColor = new Color(myPicture.getRGB(width, height));
                int grayNum = (pixelColor.getRed()+pixelColor.getBlue()+pixelColor.getGreen())/3;
                Color grayColor = new Color (grayNum, grayNum, grayNum);
                myPicture.setRGB(width, height, grayColor.getRGB());
            }
        }
    }

    //The comment in this method allows parallelism using the omp4j preprocessor: http://www.omp4j.org/
    //The resulting source code can be seen in parSrc/ChangeableImage
    public void flipVertical(){
        // omp parallel for
        for  (int height=0; height <= myPicture.getHeight()/2; height++){
            for (int width=0; width < myPicture.getWidth(); width++){
                int pixel1 = myPicture.getRGB(width, height);
                int pixel2 = myPicture.getRGB(width, myPicture.getHeight()-height-1);
                myPicture.setRGB(width, height, pixel2);
                myPicture.setRGB(width, myPicture.getHeight()-height-1, pixel1);
            }
        }
    }

    public void flipHorizontal(){
        for  (int height=0; height < myPicture.getHeight(); height++){
            for (int width=0; width <= myPicture.getWidth()/2; width++){
                int pixel1 = myPicture.getRGB(width, height);
                int pixel2 = myPicture.getRGB(myPicture.getWidth()-width-1, height);
                myPicture.setRGB(width, height, pixel2);
                myPicture.setRGB(myPicture.getWidth()-width-1, height, pixel1);
            }
        }
    }

    public JLabel getDisplayLabel(){
        return new JLabel(new ImageIcon(myPicture));
    }

    public void displayInFrame(){
        JPanel jPanel = new JPanel();
        jPanel.add(getDisplayLabel());

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(new Dimension(myPicture.getWidth(), myPicture.getHeight()));
        f.add(jPanel);
        f.setVisible(true);
    }

    public static void main (String[] args) {
        ChangeableImage myImage = new ChangeableImage("resources/theDude.jpg");
        myImage.flipVertical();
        myImage.flipHorizontal();
        myImage.displayInFrame();
    }
}
