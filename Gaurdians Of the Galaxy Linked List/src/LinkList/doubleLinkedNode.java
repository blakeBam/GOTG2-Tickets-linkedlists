//Blake Bambico
//bcb140130
//CS 2336.002
package linklist;

public class doubleLinkedNode extends baseNode {
  //members
  protected doubleLinkedNode next;
  protected doubleLinkedNode prev;

  //default constructor
  public doubleLinkedNode()
  {
    super();
    next = null;
    prev = null;
  }

  //partially overlaoded constructor
  public doubleLinkedNode(int s, int r)
  {
    super(s,r);
    next = null;
    prev = null;
  }

  //fully overloaded constructor
  public doubleLinkedNode(int s, int r, doubleLinkedNode n, doubleLinkedNode p)
  {
    super(s,r);
    next = n;
    prev = p;
  }

  //accessors
  public doubleLinkedNode getNext() {return next;}
  public doubleLinkedNode getPrev() {return prev;}

  //mutators
  public void setNext(doubleLinkedNode n) {next = n;}
  public void setPrev(doubleLinkedNode p) {prev = p;}
}
