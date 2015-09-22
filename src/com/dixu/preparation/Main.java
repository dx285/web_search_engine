package com.dixu.preparation;

import java.io.FileOutputStream;
import java.io.PrintStream;

import com.dixu.utils.MyProperties;

public class Main {

	public static void main(String[] args) throws Exception {
//		PrintStream log = new PrintStream(new FileOutputStream(MyProperties.LOG_FILE), false);
//		System.setOut(log);
//		
//		NZAnalyzer.main(null);
//		MergeSort.main(null);
		System.out.println(String.format("%2$08d", -3123,-5566));
		// Remember take a snapshot after this program finished
		Object[] sendData = new Object[4];  
        sendData[0] = Integer.valueOf(1);  
        sendData[1] = "172.12.1.2";  
        sendData[2] = Integer.valueOf(123);  
        sendData[3] = "testadfaerfa";  
        String sendDataString = String.format("%d,%s,%d,%s",(Object[]) sendData);  
        System.out.println(sendDataString); 
	}

}
