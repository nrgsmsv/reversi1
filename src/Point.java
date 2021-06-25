
public class Point implements Comparable<Object> {
	
	private int x;
	private int y;
	

	public Point(int x , int y){
		
		this.x = x;
		this.y = y;
	}	

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public boolean isEqual(Point p){
		if(this.x == p.getX() && this.y== p.getY()) {
			return true;
		}
		return false;
	}
	@Override
	public String toString(){
		return "("+ (this.getX()) + "," + (this.getY()) + ") ";
	}

	public boolean valid_point() {
		if (this.getX() < 0 || this.getY() < 0){
			return false;
		}
		return true;
	}
	@Override
	public int compareTo(Object other){
		if(other instanceof Point){
			Point p1 = (Point) other;
			if(this.x == p1.getX() && this.y == p1.getY()){
				return 0;
			} else {
				return -1;
			}
		} else {
			return -2;
		}
	}

}
