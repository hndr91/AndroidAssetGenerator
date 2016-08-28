/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package androidassetsgenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import net.coobird.thumbnailator.*;
import net.coobird.thumbnailator.name.Rename;

/**
 *
 * @author afifhendrawan
 */
public class AssetGenerator {

    private BufferedImage asset = null;
    
    public void scaleThumbnailator(File input, String fileName, int baseIndex, String outputDir) throws IOException {
        asset = ImageIO.read(input);
        
        String file = FilenameUtils.removeExtension(fileName);
        String fileExtension = FilenameUtils.getExtension(fileName);
        
        System.out.println(baseIndex);
        
        BufferedImage outputLdpi, outputMdpi, outputHdpi, outputXhdpi, outputXxhdpi, outputXxxhdpi;
        
        switch(baseIndex){
            // Write from ldpi to ldpi -> kind of useless
            case 0:
                writeOutput(asset, path(fileName, outputDir, Constants.DRAWABLE_LDPI) );
                break;
            // Write from mdpi to ldpi
            case 1:
                writeOutput(scale34(asset), path(fileName, outputDir, Constants.DRAWABLE_LDPI) );
                break;
            // Write from hdpi to mdpi, ldpi
            case 2:
                outputMdpi = scale23(asset);
                // Write to mdpi
                writeOutput(outputMdpi, path(fileName, outputDir, Constants.DRAWABLE_MDPI));
                // Write to ldpi
                writeOutput(scale34(outputMdpi), path(fileName, outputDir, Constants.DRAWABLE_LDPI) );
                break;
            // Write from xhdpi to hdpi, mdpi, ldpi
            case 3:
                outputHdpi = scale34(asset);
                outputMdpi = scale23(outputHdpi);
                // Write to hdpi
                writeOutput(outputHdpi, path(fileName, outputDir, Constants.DRAWABLE_HDPI));
                // Write to mdpi
                writeOutput(outputMdpi, path(fileName, outputDir, Constants.DRAWABLE_MDPI));
                // Write to ldpi
                writeOutput(scale34(outputMdpi), path(fileName, outputDir, Constants.DRAWABLE_LDPI));
                break;
            // Write from xxhdpi to xhdpi, hdpi, mdpi, ldpi
            case 4:
                outputXhdpi = scale23(asset);
                outputHdpi = scale34(outputXhdpi);
                outputMdpi = scale23(outputHdpi);
                // Write to xhdpi
                writeOutput(outputXhdpi, path(fileName, outputDir, Constants.DRAWABLE_XHDPI));
                // Write to hdpi
                writeOutput(outputHdpi, path(fileName, outputDir, Constants.DRAWABLE_HDPI));
                // Write to mdpi
                writeOutput(outputMdpi, path(fileName, outputDir, Constants.DRAWABLE_MDPI));
                // Write to ldpi
                writeOutput(scale34(outputMdpi), path(fileName, outputDir, Constants.DRAWABLE_LDPI));
                break;
            // Write from xxxhdpi to xxhdpi, xhdpi, hdpi, mdpi, ldpi
            case 5:
                outputXxhdpi = scale34(asset);
                outputXhdpi = scale23(outputXxhdpi);
                outputHdpi = scale34(outputXhdpi);
                outputMdpi = scale23(outputHdpi);
                // Write to xxhdpi
                writeOutput(outputXxhdpi, path(fileName, outputDir, Constants.DRAWABLE_XXHDPI));
                // Write to xhdpi
                writeOutput(outputXhdpi, path(fileName, outputDir, Constants.DRAWABLE_XHDPI));
                // Write to hdpi
                writeOutput(outputHdpi, path(fileName, outputDir, Constants.DRAWABLE_HDPI));
                // Write to mdpi
                writeOutput(outputMdpi, path(fileName, outputDir, Constants.DRAWABLE_MDPI));
                // Write to ldpi
                writeOutput(scale34(outputMdpi), path(fileName, outputDir, Constants.DRAWABLE_LDPI));
                break;
        }
        
    }
    
    /** Scale for 3:4 or 0.75 */
    private BufferedImage scale34(BufferedImage input) throws IOException {
        BufferedImage output = Thumbnails.of(input)
                .scale(0.75)
                .asBufferedImage();
        return output;
    }
    
    /** Scale for 2/3 or 0.6666666667 */
    private BufferedImage scale23(BufferedImage input) throws IOException {
        BufferedImage output = Thumbnails.of(input)
                .scale(0.6666666667)
                .asBufferedImage();
        return output;
    }
    
    /** Write Output Image */
    private void writeOutput(BufferedImage input, File path) throws IOException {
        Thumbnails.of(input)
                .scale(1)
                .toFile(path);
    }
    
    private File path(String fileName, String outputDir, String outputSize) {
        String name = FilenameUtils.removeExtension(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        Path path = Paths.get(outputDir + Constants.SLASH + Constants.PREFIX + name.toUpperCase() 
                + Constants.SLASH + outputSize);
        
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        File file = new File(path.toString() + Constants.SLASH
                + Constants.PREFIX+name.toUpperCase() + Constants.DOT + extension);
        
        return file;
    }

}
