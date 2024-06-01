package system;

public class Student {
	private String name;
	private int roll_NUM;
	private double grade;
	 
	public Student(String n,int r,double g){
		this.name=n;
		this.roll_NUM=r;
		this.grade=g;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoll_NUM() {
		return roll_NUM;
	}

	public void setRoll_NUM(int roll_NUM) {
		this.roll_NUM = roll_NUM;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}
	

}
