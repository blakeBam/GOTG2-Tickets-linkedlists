//Blake Bambico
//bcb140130
//CS 2336.002
package linklist;

public abstract class baseNode {
  //members
  protected int row;
  protected int seat;

  //default constructor
  public baseNode()
  {
    row = 0;
    seat = 0;
  }
  
  //overloaded constructor
  public baseNode(int r, int s)
  {
    row = r;
    seat = s;
  }

  //accessors
  public int getSeat() {return seat;}
  public int getRow() {return row;}

  //mutators
  public void setSeat(int s) {seat = s;}
  public void setRow(int r) {row = r;}
}
