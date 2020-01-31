package com.hanseon;


import org.springframework.core.io.ClassPathResource;

import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.PrinterStatus;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.util.internal.FileReader;

public class PrintTest {
	
	//라벨인쇄하기
	public static void main(String[] args) throws Exception{
		
		
			//파일가져오기
			ClassPathResource resource = new ClassPathResource("test1.prn");
			
			
			
		
			//프린터연결
	         Connection connection = new TcpConnection("192.168.1.154", TcpConnection.DEFAULT_ZPL_TCP_PORT);
	     //    Connection connection = new TcpConnection("192.168.1.98","3911);
	         try {
	             connection.open();
	             ZebraPrinter zPrinter = ZebraPrinterFactory.getInstance(connection);
	             PrinterLanguage pcLanguage = zPrinter.getPrinterControlLanguage();
	             System.out.println("Printer Control Language is " + pcLanguage);
	             System.out.println("연결됨");
	             
	             //파일인쇄
	             connection.write(resource.getInputStream());
	              
	             System.out.println("인쇄중임ㅋ");
	             
	             connection.close();
	             
	             System.out.println("연결해제");
	         } catch (ConnectionException e) {
	             e.printStackTrace();
	         } catch (ZebraPrinterLanguageUnknownException e) {
	             e.printStackTrace();
	         } finally {
	             connection.close();
	         }
	     }
	 
		
	}


