/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package androidassetsgenerator;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author afifhendrawan
 */
public class AssetGenerator {

    private BufferedImage asset = null;
    private static final int SCALE_1 = 0;
    private static final int SCALE_2 = 1;

    private int INT_WIDTH, INT_HEIGHT;

    public void scaleAsset(File input, String fileName, String outputDir) throws IOException {
        asset = ImageIO.read(input);
        int type = asset.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : asset.getType();
        BufferedImage resize = reSizeImg(asset, type, SCALE_1);
        
        String file = FilenameUtils.removeExtension(fileName);
        String mainPath = path(fileName, outputDir).toString();
        
        
        File output = new File(mainPath + Constants.SLASH + Constants.PREFIX + file.toUpperCase() + Constants.PNG);
        ImageIO.write(resize, "png", output);
    }
    
    private Path path(String fileName, String outputDir) {
        String file = FilenameUtils.removeExtension(fileName);
        Path path = Paths.get(outputDir + Constants.SLASH + Constants.PREFIX + file.toUpperCase());
        
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        return path;
    }

    private BufferedImage reSizeImg(BufferedImage input, int type, int scale) {
        switch (scale) {
            case 0:
                INT_WIDTH = (input.getWidth() * 3) / 4;
                INT_HEIGHT = (input.getHeight() * 3) / 4;
                break;
            case 1:
                INT_WIDTH = (input.getWidth() * 2) / 3;
                INT_HEIGHT = (input.getHeight() * 2) / 3;
                break;

        }

        BufferedImage reSizeImg = new BufferedImage(INT_WIDTH, INT_HEIGHT, type);
        Graphics2D g = reSizeImg.createGraphics();
        g.drawImage(input, 0, 0, INT_WIDTH, INT_HEIGHT, null);
        g.dispose();
        return reSizeImg;

    }
    
    /** @TODO: Resizing method for every dimension */

}
