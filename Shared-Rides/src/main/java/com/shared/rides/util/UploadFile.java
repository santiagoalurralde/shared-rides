package com.shared.rides.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile {

	public static String uploadFile( MultipartFile file){
		String name = file.getOriginalFilename();;
		
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creando el directorio
				String rootPath = "/home/santiago/workspace/trunk/Shared-Rides/src/main/webapp/resources/profilePic";
				File dir = new File(rootPath + File.separator);
        
				// Creo el archivo en el server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				return "El archivo fue subido exitosamente = " + name;
			} catch (Exception e) {
				return "Fallo al cargar el archivo " + name + " => " + e.getMessage();
			}
			} else {
					return "Fallo al cargar el archivo " + name + " porque el mismo esta vacio.";
			}
	}
}
