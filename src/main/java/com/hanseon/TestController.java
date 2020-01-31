package com.hanseon;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onbarcode.barcode.DataMatrix;
import com.onbarcode.barcode.EAN128;
import com.onbarcode.barcode.IBarcode;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

@Controller
public class TestController {

	@RequestMapping("/home")
	public void home() {
	}
	
	
	
	@RequestMapping("/genBarcode")
	//public void generate(  HttpServletRequest request) {
	public String generate(@RequestParam String fullBarcode  ) {
		
//	String fullBarcode = request.getParameter("fullBarcode");	
//	request.setAttribute(name, o);
	
	DataMatrix Dbarcode = new DataMatrix();
		
		/*
		   Data Matrix Valid data char set:
		        ASCII values 0 - 127 in accordance with the US national version of ISO/IEC 646
		            ASCII values 128 - 255 in accordance with ISO 8859-1. These are referred to as extended ASCII.
		
		*/
		Dbarcode.setData(fullBarcode);
		
		
		Dbarcode.setDataMode(DataMatrix.M_AUTO);
		
		// if your selected format mode doesnot have enough space to encode your data,
		// the library will choose the right format mode for you automatically.
		Dbarcode.setFormatMode(DataMatrix.F_10X10);
		
		//  Set the processTilde property to true, if you want use the tilde character "~" to specify special characters in the input data. Default is false.
		//  1-byte character: ~ddd (character value from 0 ~ 255)
		//  ASCII (with EXT): from ~000 to ~255
		//  2-byte character: ~6ddddd (character value from 0 ~ 65535)
		//  Unicode: from ~600000 to ~665535
		//  ECI: from ~7000000 to ~7999999
		Dbarcode.setProcessTilde(true);
		
		// Data Matrix Unit of Measure, pixel, cm, or inch
		Dbarcode.setUom(IBarcode.UOM_PIXEL);
		// Data Matrix barcode bar module width (X) in pixel
		Dbarcode.setX(3f);
		
		Dbarcode.setLeftMargin(10f);
		Dbarcode.setRightMargin(10f);
		Dbarcode.setTopMargin(10f);
		Dbarcode.setBottomMargin(10f);
		// barcode image resolution in dpi
		Dbarcode.setResolution(72);
		
		try {
			Dbarcode.drawBarcode("D:\\images\\barcode.gif");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("데이터 매트릭스 바코드 생성 완료");
		
		// 초기화면으로
	return "redirect:/home";
	
		
		
		
// 1차원바코듣생성하기		
//		 EAN128 barcode = new EAN128();
//			
//			
//			barcode.setData(fullBarcode);
//			
//			//  Set the processTilde property to true, if you want use the tilde character "~"
//			//  to specify special characters in the input data. Default is false.
//			//  1) All 128 ISO/IEC 646 characters, i.e. characters 0 to 127 inclusive, in accordance with ISO/IEC 646.
//			//       NOTE This version consists of the G0 set of ISO/IEC 646 and the C0 set of ISO/IEC 6429 with values 28 - 31
//			//       modified to FS, GS, RS and US respectively.
//			//  2) Characters with byte values 128 to 255 may also be encoded.
//			//  3) 4 non-data function characters.
//			//  4) 4 code set selection characters.
//			//  5) 3 Start characters.
//			//  6) 1 Stop character.
//			barcode.setProcessTilde(true);
//			
//			// GS1-128 Unit of Measure, pixel, cm, or inch
//			barcode.setUom(IBarcode.UOM_PIXEL);
//			// GS1-128 barcode bar module width (X) in pixel
//			barcode.setX(3f);
//			// GS1-128 barcode bar module height (Y) in pixel
//			barcode.setY(75f);
//			
//			// barcode image margins
//			barcode.setLeftMargin(0f);
//			barcode.setRightMargin(0f);
//			barcode.setTopMargin(0f);
//			barcode.setBottomMargin(0f);
//			
//			// barcode image resolution in dpi
//			barcode.setResolution(72);
//			
//			// disply barcode encoding data below the barcode
//			barcode.setShowText(true);
//			// barcode encoding data font style
//			barcode.setTextFont(new Font("Arial", 0, 12));
//			// space between barcode and barcode encoding data
//			barcode.setTextMargin(6);
//			
//			//  barcode displaying angle
//			barcode.setRotate(IBarcode.ROTATE_0);
//			
//			try {
//				barcode.drawBarcode("D:\\images\\barcode.gif");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println("생성완료ㅋ");
			
		
	}
	
	
	
	@RequestMapping("error")
	public void error() {}

	@RequestMapping("/print")
	public String print(@RequestParam String barcodeInput) throws Exception {

		// 파일가져오기
		//1차원 바코드
		ClassPathResource resource = new ClassPathResource("barcode1.prn");
		//2차원 데이터매트릭스
//		ClassPathResource resource = new ClassPathResource("datamatrix.prn");

		// 파일 읽기
		// 버퍼를 이용하여 입력하기 위해
		// 버퍼 ? 데이터를 한 곳에서 다른 한 곳으로 전송하는 동안 일시적으로 데이터를 보관하는 임시 메모리 영역
		// 입출력 속도 향상을 위해 버퍼 사용
		BufferedReader bReader = null;

		byte[] buffers = null;

		try {
			// 파일 내용 한줄한줄 닮을 String
			String s;

			// 파일 객체생성, uri에 있는 파일로
			File file = new File(resource.getURI());

			// 파일 읽어와서 담기
			bReader = new BufferedReader(new FileReader(file));

			// 파일 전체내용을 닮을 객체
			String total = "";

			// 더이상 읽어들일게 없을 때까지 읽어들이게 합니다.
			while ((s = bReader.readLine()) != null) {
				total += s;
				System.out.println(s);
			}
			
			//14자리로 만들기
			//GTIN의 (01)을 사용하려면 14자리여야함. 그래서 14자리 아닐경우 맞춰주기 (16byte)
			// int는 4byte 10자리  -2,147,483,648 ~ 2,147,483,647

			String barcode = String.format("%014d",Long.parseLong(barcodeInput));
			

			// 입력할 바코드값 바꾸기
			String ffff = total.replace("*************", barcode);
			System.out.println(ffff);

			// 바코드 바이트로 가져오기
			buffers = ffff.getBytes();

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

		// 프린터연결
		Connection connection = new TcpConnection("192.168.1.154", TcpConnection.DEFAULT_ZPL_TCP_PORT);
		try {
			connection.open();
			ZebraPrinter zPrinter = ZebraPrinterFactory.getInstance(connection);
			PrinterLanguage pcLanguage = zPrinter.getPrinterControlLanguage();
			System.out.println("Printer Control Language is " + pcLanguage);
			System.out.println("연결됨.................");

			// 파일인쇄
			// connection.write(resource.getInputStream());
			
			// Byte로 읽어서 출력하기
		//	 connection.write(buffers);

			System.out.println("인쇄중임ㅋ.....");

			connection.close();

			System.out.println("연결해제.");
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (ZebraPrinterLanguageUnknownException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		// 초기화면으로
		return "redirect:/home";

	}

}
