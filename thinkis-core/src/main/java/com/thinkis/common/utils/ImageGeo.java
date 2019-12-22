package com.thinkis.common.utils;

import com.drew.metadata.*;
import com.drew.metadata.exif.*;
import com.drew.imaging.jpeg.*;
import com.drew.lang.*;
import java.io.*;

public class ImageGeo {
	public double lat = 0.0;
	public double lon = 0.0;
	public double alt = 0.0;
	public boolean error = false;

	public ImageGeo(String filename) {
		try {
			error = false;
			File jpegFile = new File(filename);
			Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);

			GpsDirectory gpsdir = (GpsDirectory) metadata
					.getDirectory(GpsDirectory.class);
			Rational latpart[] = gpsdir
					.getRationalArray(GpsDirectory.TAG_GPS_LATITUDE);
			Rational lonpart[] = gpsdir
					.getRationalArray(GpsDirectory.TAG_GPS_LONGITUDE);
			String northing = gpsdir
					.getString(GpsDirectory.TAG_GPS_LATITUDE_REF);
			String easting = gpsdir
					.getString(GpsDirectory.TAG_GPS_LONGITUDE_REF);

			try {
				alt = gpsdir.getDouble(GpsDirectory.TAG_GPS_ALTITUDE);
			} catch (Exception ex) {
			}

			double latsign = 1.0d;
			if (northing.equalsIgnoreCase("S"))
				latsign = -1.0d;
			double lonsign = 1.0d;
			if (easting.equalsIgnoreCase("W"))
				lonsign = -1.0d;
			lat = (Math.abs(latpart[0].doubleValue())
					+ latpart[1].doubleValue() / 60.0d + latpart[2]
					.doubleValue() / 3600.0d) * latsign;
			lon = (Math.abs(lonpart[0].doubleValue())
					+ lonpart[1].doubleValue() / 60.0d + lonpart[2]
					.doubleValue() / 3600.0d) * lonsign;

			if (Double.isNaN(lat) || Double.isNaN(lon))
				error = true;
		} catch (Exception ex) {
			error = true;
		}
		System.out.println(filename + ": (" + lat + ", " + lon + ")");
	}
	
//	public static void main(String[] args) {
//		ImageGeo imageGeo = new ImageGeo(ImageGeo.class.getResource("IMAG0068.jpg").getFile());
//		System.out.println(imageGeo.lon+","+imageGeo.lat);
//	}
	public static void main(String[] args) {
		byte [] p = hexToByteArray("183");
		int v1 = (p[1]>>4)*10;
		int v2 = p[1]&0x0F;
		int v3 = (p[0]>>4)*10;
		int v4 = p[0]&0x0F;
		System.out.println((v3*10+v4)*100+v1+v2);
		int s=((p[0]>>4)*10 + p[0]&0x0F)*100 + ((p[1]>>4)*10 + p[1]&0x0F);
			System.out.println(s*0.01);
			System.out.println(s*0.001);
	}

	public static byte[] hexToByteArray(String inHex){
		int hexlen = inHex.length();
		byte[] result;
		if (hexlen % 2 == 1){
			//奇数
			hexlen++;
			result = new byte[(hexlen/2)];
			inHex="0"+inHex;
		}else {
			//偶数
			result = new byte[(hexlen/2)];
		}
		int j=0;
		for (int i = 0; i < hexlen; i+=2){
			result[j]=hexToByte(inHex.substring(i,i+2));
			j++;
		}
		return result;
	}
	public static byte hexToByte(String inHex){
		return (byte)Integer.parseInt(inHex,16);
	}

	private static String intToHex(int n) {
		StringBuffer s = new StringBuffer();
		String a;
		char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		while(n != 0){
			s = s.append(b[n%16]);
			n = n/16;
		}
		a = s.reverse().toString();
		if(a.length()<4){
			int len = a.length();
			for(int i=0;i<4-len;i++)
				a = "0"+a;
		}
		return a;
	}


}
