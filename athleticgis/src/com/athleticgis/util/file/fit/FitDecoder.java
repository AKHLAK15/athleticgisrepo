package com.athleticgis.util.file.fit;

import java.io.InputStream;

import com.garmin.fit.Decode;
import com.garmin.fit.FitRuntimeException;
import com.garmin.fit.MesgBroadcaster;
import com.garmin.fit.RecordMesgListener;

public class FitDecoder {
	public static void decode(InputStream in) {
		Decode decode = new Decode();
		MesgBroadcaster mesgBroadcaster = new MesgBroadcaster(decode);
		FitListener listener = new FitListener();

//		try {
//			if (!Decode.checkIntegrity((InputStream) in))
//				throw new RuntimeException("FIT file integrity failed.");
//		} finally {
//			try {
//				in.close();
//			} catch (java.io.IOException e) {
//				throw new RuntimeException(e);
//			}
//		}
		mesgBroadcaster.addListener((RecordMesgListener) listener);

		try {
			mesgBroadcaster.run(in);
		} catch (FitRuntimeException e) {
			System.err.print("Exception decoding file: ");
			System.err.println(e.getMessage());

			try {
				in.close();
			} catch (java.io.IOException f) {
				throw new RuntimeException(f);
			}

			return;
		}

		try {
			in.close();
		} catch (java.io.IOException e) {
			throw new RuntimeException(e);
		}

		System.out.println("Decoded FIT file.");
	}
}
