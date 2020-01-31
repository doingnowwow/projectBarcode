package com.hanseon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

public class FileTest {

	// 라벨인쇄하기
	public static void main(String[] args) throws Exception {

		// resource에 있는 파일가져오기
		ClassPathResource resource = new ClassPathResource("datamatrix.prn");

		String filename = resource.getFilename();

		// 버퍼를 이용하여 입력하기 위해
		// 버퍼 ? 데이터를 한 곳에서 다른 한 곳으로 전송하는 동안 일시적으로 데이터를 보관하는 임시 메모리 영역
		// 입출력 속도 향상을 위해 버퍼 사용
		BufferedReader bReader = null;

		try {

			String s;
			// 파일 객체생성, uri에 있는 파일로
			File file = new File(resource.getURI());
			// 파일 읽어와서 담기
			bReader = new BufferedReader(new FileReader(file));

			String total = "";
			// 더이상 읽어들일게 없을 때까지 읽어들이게 합니다.
			while ((s = bReader.readLine()) != null) {

				total += s;
				System.out.println(s);
			}
			System.out.println("///////////////////");
			System.out.println(total);
			System.out.println("````````````````````````````````");
			String ffff = total.replace("*************", "08809566300010");
			System.out.println(ffff);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bReader != null)
					bReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
