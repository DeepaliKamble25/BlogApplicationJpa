package com.bikkadit.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bikkadit.services.FileService;
@Service
public class FileServiceImpl implements FileService {
	
	Logger logger=LoggerFactory.getLogger(FileServiceImpl.class);

	@Override
	public String uploadimage (String path, MultipartFile file) throws IOException {
	
		logger.info("At Entering point this is the info for uploadimage level logger");
//		file name
		String name=file.getOriginalFilename();
		//random name generation
            String randomID=UUID.randomUUID().toString();
		
		String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));
		
//		fullpath
		String filePath=path+ File.separator+fileName1;
		
		
		
//		create folder if not created
		File f=new File(path);
		if(!f.exists()) 
		{
			f.mkdir();
		}
		
//		file copy
		Files.copy(file.getInputStream(),Paths.get(filePath));
		
		
		 logger.info("At Exist point this is the info for uploadimage level logger");
		
		return fileName1;//random file name return
	}

	@Override
	public InputStream getResource(String path, String fileName) throws  FileNotFoundException
	{

		logger.info("At Entering point this is the info for getResource level logger");
		     String fullPath=path+ File.separator+fileName;
		     InputStream is= new FileInputStream(fullPath);
		     //db logic to input stream
		     logger.info("At Exist point this is the info for getResource level logger");
				
		return is;
	}

}
