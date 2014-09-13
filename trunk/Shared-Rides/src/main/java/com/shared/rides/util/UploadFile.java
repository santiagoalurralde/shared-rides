package com.shared.rides.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile {

	public static String uploadFile(MultipartFile file, String fileName, String pathFile){
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				File dir = new File(pathFile + File.separator);
        
				// Creo el archivo en el server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				return "El archivo fue subido exitosamente = " + fileName;
			} catch (Exception e) {
				return "Fallo al cargar el archivo " + fileName + " => " + e.getMessage();
			}
			} else {
				return "Fallo al cargar el archivo " + fileName + " porque el mismo esta vacio.";
			}
	}
}
