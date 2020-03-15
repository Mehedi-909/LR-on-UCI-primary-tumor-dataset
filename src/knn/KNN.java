package knn;

import java.io.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class KNN {
	
	int noOfAttributes;
	ArrayList<Patient>patient;
	int k;

	public KNN (int k){
		patient=new ArrayList<Patient>();
		noOfAttributes=16;
		this.k=k;
	}
	
	public void readFile()
	{
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("E:/My 5th/KNN-primary-tumor/src/knn/input.txt")));
			String str;
			while((str=br.readLine())!=null){
				String []att=str.split(",");
				Patient f=new Patient(Integer.parseInt(att[att.length-2]),Integer.parseInt(att[att.length-1]));
				for(int i=0;i<noOfAttributes;i++){
					if(att[i].equals("?")){
						att[i]="1000";
						f.attribute[i]=Double.valueOf(att[i]);
					}
					else f.attribute[i]=Double.valueOf(att[i]);
					
				}
				patient.add(f);

			}
			br.close();
		}catch(Exception e){

			e.printStackTrace();
		}
		
		System.out.println("Input size : "+ patient.size());
	}
	
	public void runAlgorithm(){
		readFile();
		Patient f;
		int count=0;
		int total=0;
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("E:/My 5th/KNN-primary-tumor/src/knn/test.txt")));
			String str;
			while((str=br.readLine())!=null){
				String []att=str.split(",");
				f=new Patient(Integer.parseInt(att[att.length-2]),Integer.parseInt(att[att.length-1]));
				System.out.println("Expected decision1: "+att[att.length-2]);
				System.out.println("Expected decision2: "+att[att.length-1]);
				
				for(int i=0;i<noOfAttributes;i++){
					//f.attribute[i]=Double.valueOf(att[i]);
					if(att[i].equals("?")){
						att[i]="1000";
						f.attribute[i]=Double.valueOf(att[i]);
					}
					else f.attribute[i]=Double.valueOf(att[i]);
				}
				for(int j=0;j<patient.size();j++){
					patient.get(j).distance=calculateDistance(f.attribute,patient.get(j).attribute);
				}
				Collections.sort(patient, new Comparator<Patient>(){
					public int compare(Patient f1, Patient f2) {
						double dif=f1.distance-f2.distance;
						if(dif>0)
							return 1;
						else if(dif<0)
							return -1;
						else{
							return 0;
						}
					}
				});
				
				
				int decision1=findClass();
				if(decision1==f.d1){
					count++;
				}
				System.out.println("Actual class: "+decision1);
				total++;
				int decision2=findClass2();
				if(decision2==f.d2){
					count++;
				}
				System.out.println("Actual class: "+decision2);
				total++;
				System.out.println();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("\nAccuracy: "+ ((count*100)/total));
	}

	private double calculateDistance(double []d1,double[]d2)
	{
		double total=0;
		for(int i=0;i<noOfAttributes;i++){
			
			if(d1[i] != 1000 || d1[i] != 1000){
				total+=(d1[i]-d2[i])*(d1[i]-d2[i]);
			}
			
		}
		return (Math.sqrt(total));
	}

	private int findClass(){
		int positive=0;
		int negative=0;
		for(int i=0;i<k;i++){
			int className=patient.get(i).d1;
			if(className==2){
				negative++;
			}
			else if(className==1){
				positive++;
			}
		}
		
		if(positive>negative){
			return 1;
		}
		else if(negative>positive){
			return 2;
		}
		return 0;
	}
	
	private int findClass2(){
		int positive=0;
		int negative=0;
		for(int i=0;i<k;i++){
			int className=patient.get(i).d2;
			if(className==2){
				negative++;
			}
			else if(className==1){
				positive++;
			}
		}
		
		if(positive>negative){
			return 1;
		}
		else if(negative>positive){
			return 2;
		}
		return 0;
	}
}
