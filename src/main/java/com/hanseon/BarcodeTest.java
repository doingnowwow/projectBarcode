package com.hanseon;

import java.awt.Font;


import com.onbarcode.barcode.DataMatrix;
import com.onbarcode.barcode.EAN128;
import com.onbarcode.barcode.IBarcode;

public class BarcodeTest  {
	
   public static void main(String[] args) {
	   
	   EAN128 barcode = new EAN128();
		
		/*
		   GS1-128 / EAN-128 Valid data char set:
		        all 128 ASCII characters (Char from 0 to 127)
		
		   to encode Application Identifier (AI), add "()" around the AI code, and followed by the AI data
		*/
		barcode.setData("(01)08806411123459(17)101231(10)Q12345(21)A213291199");
		
		//  Set the processTilde property to true, if you want use the tilde character "~"
		//  to specify special characters in the input data. Default is false.
		//  1) All 128 ISO/IEC 646 characters, i.e. characters 0 to 127 inclusive, in accordance with ISO/IEC 646.
		//       NOTE This version consists of the G0 set of ISO/IEC 646 and the C0 set of ISO/IEC 6429 with values 28 - 31
		//       modified to FS, GS, RS and US respectively.
		//  2) Characters with byte values 128 to 255 may also be encoded.
		//  3) 4 non-data function characters.
		//  4) 4 code set selection characters.
		//  5) 3 Start characters.
		//  6) 1 Stop character.
		barcode.setProcessTilde(true);
		
		// GS1-128 Unit of Measure, pixel, cm, or inch
		barcode.setUom(IBarcode.UOM_PIXEL);
		// GS1-128 barcode bar module width (X) in pixel
		barcode.setX(3f);
		// GS1-128 barcode bar module height (Y) in pixel
		barcode.setY(75f);
		
		// barcode image margins
		barcode.setLeftMargin(0f);
		barcode.setRightMargin(0f);
		barcode.setTopMargin(0f);
		barcode.setBottomMargin(0f);
		
		// barcode image resolution in dpi
		barcode.setResolution(72);
		
		// disply barcode encoding data below the barcode
		barcode.setShowText(true);
		// barcode encoding data font style
		barcode.setTextFont(new Font("Arial", 0, 12));
		// space between barcode and barcode encoding data
		barcode.setTextMargin(6);
		
		//  barcode displaying angle
		barcode.setRotate(IBarcode.ROTATE_0);
		
		try {
			barcode.drawBarcode("D:\\ean128.gif");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("생성완료ㅋ");
		
		
		///////////////////////////////////////////////////////////////////////
		
		DataMatrix Dbarcode = new DataMatrix();
		
		/*
		   Data Matrix Valid data char set:
		        ASCII values 0 - 127 in accordance with the US national version of ISO/IEC 646
		            ASCII values 128 - 255 in accordance with ISO 8859-1. These are referred to as extended ASCII.
		
		*/
		
		
		String ba = "01088064111234591710123110Q1234521Az13291199";
		String gs1 = "0108806411123459";
		String gs2 = "21Az13291199";
		
		byte[] gs1byte = gs1.getBytes(); 
		byte[] gs2byte = gs2.getBytes(); 

		//gs1.getBytes();
		
	//	byte separator = 0x1D;
	//	byte fncst =  (byte) 0xE8;
		
		byte fncst = (byte) 0xE8;   // 시작문자 (ASCII 232)
        byte separator = 0x1D;       // 필드분리자(Separator) (ASCII 29:<GS>)
        
        byte[] buffers = gs1.getBytes();
        
        System.out.println("buffers 길이 => " + buffers.length);
        
        byte[] tempbytes = new byte[buffers.length+1];
        tempbytes[0] = fncst;
        for(int i=0; i<buffers.length; i++) {
           tempbytes[i+1] = buffers[i];
        }
        
        String bt = new String(tempbytes);	
		System.out.println(tempbytes);
		
		String aa = new String(bt);
		System.out.println(bt);
		
		
	
		
		
		
	// sss += separator;
		// sss += gs1byte;
	
		
//		System.out.println(bbb);
//		System.out.println(ccc);
//		
		Dbarcode.setData(bt);
		
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
			Dbarcode.drawBarcode("D:\\datamatrix.gif");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("데이터 매트릭스 바코드 생성 완료");
		
		
		
		
		
		
		
		/////////////////////////////////////////////////////////////////////////
		
			DataMatrix Dbarcode1 = new DataMatrix();
		
		/*
		   Data Matrix Valid data char set:
		        ASCII values 0 - 127 in accordance with the US national version of ISO/IEC 646
		            ASCII values 128 - 255 in accordance with ISO 8859-1. These are referred to as extended ASCII.
		
		*/
			
			
			
//		Dbarcode1.setData("(01)08806411123459(17)101231(10)Q12345(21)A213291199");
		Dbarcode1.setData("08806411123459");
		
		Dbarcode1.setDataMode(DataMatrix.M_AUTO);
		
		// if your selected format mode doesnot have enough space to encode your data,
		// the library will choose the right format mode for you automatically.
		Dbarcode1.setFormatMode(DataMatrix.F_10X10);
		
		//  Set the processTilde property to true, if you want use the tilde character "~" to specify special characters in the input data. Default is false.
		//  1-byte character: ~ddd (character value from 0 ~ 255)
		//  ASCII (with EXT): from ~000 to ~255
		//  2-byte character: ~6ddddd (character value from 0 ~ 65535)
		//  Unicode: from ~600000 to ~665535
		//  ECI: from ~7000000 to ~7999999
		Dbarcode1.setProcessTilde(true);
		
		// Data Matrix Unit of Measure, pixel, cm, or inch
		Dbarcode1.setUom(IBarcode.UOM_PIXEL);
		// Data Matrix barcode bar module width (X) in pixel
		Dbarcode1.setX(3f);
		
		Dbarcode1.setLeftMargin(10f);
		Dbarcode1.setRightMargin(10f);
		Dbarcode1.setTopMargin(10f);
		Dbarcode1.setBottomMargin(10f);
		// barcode image resolution in dpi
		Dbarcode1.setResolution(72);
		
		try {
			Dbarcode1.drawBarcode("D:\\datamatrix111111.gif");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("데이터 매트릭스 바코드 생성 완료");



   }

  
}