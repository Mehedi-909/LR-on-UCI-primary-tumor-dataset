package knn;

public class Patient{
	double[] attribute;
	String className;
	double distance;
	int d1,d2;
	public Patient(int a,int b) {
		this.attribute = new double[16];
		//this.className = className;
		this.distance = 0;
		d1=a;
		d2=b;
	} 	
}
