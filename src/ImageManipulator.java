//This import can be included (for the parallelized version of ChangeableImage), or commented out for serial version
import parSrc.ChangeableImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageManipulator {

    private JFrame mainFrame;
    private JLabel imageLabel;
    private long timerHorizontal;
    private long timerVertical;

    private ChangeableImage image;

    public ImageManipulator(String filepath, int width, int height) {
        image = new ChangeableImage(filepath);

        mainFrame = new JFrame("Image Manipulator");
        mainFrame.setSize(width, height);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imageLabel = image.getDisplayLabel();
        JButton flipHorizontalButton = new JButton("Flip Horizontal");
        JButton flipVerticalButton = new JButton("Flip Vertical");
        mainFrame.add(flipHorizontalButton);
        mainFrame.add(flipVerticalButton);
        mainFrame.add(imageLabel);

        flipHorizontalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Horizontal Flip start");
                timerHorizontal = System.nanoTime();

                image.flipHorizontal();

                timerHorizontal = System.nanoTime() - timerHorizontal;
                System.out.println("Horizontal Flip end: " + (timerHorizontal * 0.000000001) + " seconds");
                refreshImage();
            }
        });

        flipVerticalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Vertical Flip start");
                timerVertical = System.nanoTime();

                image.flipVertical();

                timerVertical = System.nanoTime()-timerVertical;
                System.out.println("Vertical Flip end: " + (timerVertical * 0.000000001) + " seconds");
                System.out.println("Vertical faster than last horizontal by:" + ((timerHorizontal - timerVertical) * 0.000000001) + " seconds");
                refreshImage();
            }
        });

        mainFrame.setVisible(true);
    }

    private void refreshImage(){
        mainFrame.remove(imageLabel);
        imageLabel = image.getDisplayLabel();
        mainFrame.add(imageLabel);
        mainFrame.validate();
    }

    public static void main(String[] args) {
        new ImageManipulator("resources/shore.jpg", 1000, 1000);
    }
}


