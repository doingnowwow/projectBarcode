package com.hanseon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	
	
	/**
	 * full 바코드를 생성하기 위해 ajax 통신하는 메서드
	 * @param request : fullbarcode 생성하기 위해 jsp 에서 값 보내주는 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genBarcode")
	@ResponseBody
	public String generate(  HttpServletRequest request)  throws Exception{
//	public String generate(@RequestParam String fullBarcode  )  throws Exception{
		
	String fullBarcode = request.getParameter("fullBarcode");	
		/*
		 * byte separator = 0x1D; byte fncst = (byte) 0xE8;
		 * 
		 * String sss= fullBarcode.split("01")[0];
		 * 
		 * System.out.println(sss);
		 */
	
	
		/*
		 * BarcodeData bar =new BarcodeData();
		 * 
		 * bar.setBarcode("08809877654321"); bar.setName("비타민");
		 * 
		 * 
		 * bar.getBarcode(); bar.getName();
		 * 
		 * ArrayList< BarcodeData > barList = new ArrayList<BarcodeData>();
		 * barList.add(bar); barList.add(bar1); barList.add(bar2); barList.add(bar3);
		 * barList.add(bar4);
		 * 
		 */
	
		String result = fullBarcode;
	
		DataMatrix Dbarcode = new DataMatrix();
		Dbarcode.setData(fullBarcode);
		Dbarcode.setDataMode(DataMatrix.M_AUTO);
		Dbarcode.setFormatMode(DataMatrix.F_10X10);
		Dbarcode.setProcessTilde(true);
		Dbarcode.setUom(IBarcode.UOM_PIXEL);
		Dbarcode.setX(3f);
		
		//margin 주는거
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
		
		return result;

	}
	
	
	
	/**
	 * 라벨로 프린터 하기 위해 ajax 통신을 위한 메서드
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/print")
	@ResponseBody
	public void print(HttpServletRequest request) throws Exception {
		
	//파라미터값 가져오기
	String gs1code = request.getParameter("gs1code");
	String expireIn = request.getParameter("expireIn");
	String lineNumIn = request.getParameter("lineNumIn");
	String serialNumIn = request.getParameter("serialNumIn");

		// 파일가져오기
		//2차원 데이터매트릭스
		ClassPathResource resource = new ClassPathResource("hihi.prn");
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
			
			// 입력할 바코드값 바꾸기
			String ffff = total.replace("**************", gs1code).replace("zzz", expireIn).replace("###", lineNumIn).replace("vvvvvvvvvvvvvvvvvvvv", serialNumIn);
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
			 connection.write(buffers);
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
	}

	
	@RequestMapping("/search")
	public ModelAndView searchBarcode(ModelAndView andView,@RequestParam String inputBarcode) {
		
		System.out.println("넘어옴");
		//01/08806469007114/17/200131/10/010101/21/a4545
		//010880999999999717180131.1014001.2100000014
		 CharSequence gs = Character.toString((char) 29);
		 System.out.println("contains gs ? " + inputBarcode.contains(gs));
		
		System.out.println(inputBarcode);
		
		System.out.println("---------------------");
		System.out.println(inputBarcode.length());
		BarcodeVO vo = new BarcodeVO();
		
		
		vo.setGs1code(inputBarcode.substring(0,16));
		vo.setExpire(inputBarcode.substring(16,24));
		
//		vo.setlNum(inputBarcode.split((String) gs));
		
		vo.setlNum(inputBarcode.substring(26,31));
		vo.setsNum(inputBarcode.substring(34));
		
		andView.addObject("zz",vo);
		andView.addObject("msg","나가세요");
		
		andView.setViewName("/home");
		
		System.out.println("dd");
		return andView;
	}
	
	@RequestMapping("error")
	public void error() {}

}
