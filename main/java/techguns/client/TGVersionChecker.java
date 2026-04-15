package techguns.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import techguns.TGConfig;
import techguns.Techguns;

public class TGVersionChecker implements Runnable {

	public boolean warned=false;
	public String recommended="";
	public String latest="";
	public byte warn=(byte)0;
	
	@Override
	public void run() {
		InputStream stream;
		try {
			stream = new URL("https://raw.githubusercontent.com/pWn3d1337/Techguns/master/version.txt").openStream();
			InputStreamReader isr = new InputStreamReader(stream);
			BufferedReader br = new BufferedReader(isr);

			recommended = br.readLine();
			latest = br.readLine();
			
			if (Techguns.VERSION.equals(latest)){
				System.out.println("Techguns is up to date.");
				warn=0;
				warned=true;
			} else if (Techguns.VERSION.equals(recommended)){
				if (TGConfig.cl_enableUpdateCheckerBeta){
					warn=2;
				} else {
					System.out.println("Techguns is up to date.");
					warn=0;
					warned=true;
				}
			} else {
				if (TGConfig.cl_enableUpdateCheckerBeta){
					warn=2;
				} else {
					warn=1;
				}
			}
			
			br.close();
			isr.close();
			stream.close();
			
		} catch (MalformedURLException e) {
			System.out.println("Could not check Techguns version: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Could not check Techguns version: "+e.getMessage());
		}
		
	}

}
