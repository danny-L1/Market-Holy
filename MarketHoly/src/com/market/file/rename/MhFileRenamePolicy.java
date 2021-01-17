package com.market.file.rename;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MhFileRenamePolicy implements FileRenamePolicy {

	public File rename(File f) {
		// 한글파일이름을 영문으로 변환해 저장
		long currTime = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		int randomNum = (int) (Math.random() * 100000.0D);
		String saveFileName = randomNum + df.format(currTime);

		String name = f.getName();
		String body = null;
		String ext = null;
		int dot = name.lastIndexOf(".");
		if (dot != -1) {
			body = name.substring(0, dot);
			ext = name.substring(dot);
		} else {
			body = name;
			ext = "";
		}

		String tempName = saveFileName + ext;
		f = new File(f.getParent(), tempName);
		if (createNewFile(f)) {
			return f;
		}

		int count = 0;
		while ((!createNewFile(f)) && (count < 9999)) {
			count++;
			String newName = saveFileName + "_" + count + ext;
			f = new File(f.getParent(), newName);
		}
		return f;
	}

	private boolean createNewFile(File f) {
		try {
			return f.createNewFile();
		} catch (IOException ignored) {
		}
		return false;
	}

}
