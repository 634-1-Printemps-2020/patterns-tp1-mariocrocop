package metier;


public class Point {
    private int x, y; //Variables
    public Point(int x, int y) { this.x=x; this.y=y; } //Constructeur
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean equals(Object obj) { return this.x==((Point)obj).x && this.y==((Point)obj).y; }
    public String toString() { return "<"+x+";"+y+">"; }
    public int hashCode() {return x * 31 + y;}
}