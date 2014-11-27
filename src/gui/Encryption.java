/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Gian
 */
public class Encryption {

    private final List<SelectedFile> selectedFiles;
    private final String outputPath;

    public Encryption(List<SelectedFile> selectedFiles, String outputPath) {
        this.selectedFiles = selectedFiles;
        this.outputPath = outputPath;

        zipFiles();
        //createSessionKey();

        //ZIP -> nosave
        //KeyGeneration -> QR-Code
        //AES-GCM -> save & key
    }

    private void zipFiles() {
        try (FileOutputStream fos = new FileOutputStream(outputPath);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ZipOutputStream zos = new ZipOutputStream(bos)) {

            selectedFiles.stream().forEach((selectedFile) -> {
                System.out.println(selectedFile.getName());

                try (FileInputStream fis = new FileInputStream(selectedFile.getFile().getPath())) {
                    zos.putNextEntry(new ZipEntry(selectedFile.getFile().getName()));
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    zos.closeEntry();
                } catch (IOException ex) {
                    Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
