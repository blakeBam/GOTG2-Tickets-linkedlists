//Blake Bambico
//bcb140130
//CS 2336.002
package linklist;

public class linkedList {
  //members
  protected doubleLinkedNode head;
  protected doubleLinkedNode tail;
  //member to keep track of the total length of the list
  private int length;

  //default constructor
  public linkedList()
  {
    head = null;
    tail = null;
    length = 0;
  }

  //overloaded constructor with one pointer
  public linkedList(doubleLinkedNode h)
  {
    //tail will be null no matter what but see if h is null as well
    tail = null;
    if(h != null)
    {
      setNullHead(h);
    }
    else {head = h;}
  }

  //overloaded constructor with two pointers
  public linkedList(doubleLinkedNode h, doubleLinkedNode t)
  {
    //start with both head and tail as null then amend them if they aren't null
    this();
    if(h != null && t != null)
    {
      head = h;
      tail = t;
      head.setNext(tail);
      tail.setPrev(head);
      head.setPrev(null);
      tail.setNext(null);
      length = 2;
    }
    else if(h != null && t == null) {setNullHead(h);}
    else if(h == null && t != null) {setNullHead(t);}
  }

  //accessors
  public doubleLinkedNode getHead() {return head;}
  public doubleLinkedNode getTail() {return tail;}

  //special cases in each of the mutators where the head or the tail is already null
  //several functions use these so private just for their use
  private void setNullHead(doubleLinkedNode h)
  {
    head = h;
    length = 1;
    head.setNext(null);
    head.setPrev(null);
  }
  private void setNullTail(doubleLinkedNode t)
  {
    tail = t;
    length++;
    tail.setPrev(head);
    head.setNext(tail);
    tail.setNext(null);
  }

  //replacd the current head with a new head
  //in every one of these methods is a check for if the pointer passed in is null
  public void setHead(doubleLinkedNode h)
  {
    if(head == null)
    {
      setNullHead(h);
    }
    else
    {
      doubleLinkedNode temp = head;
      head = h;
      head.setNext(temp.getNext());
      head.setPrev(null);
    }
  }

  //replace the current tail with a new tail
  public void setTail(doubleLinkedNode t)
  {
    if(head == null)
    {
      setNullHead(t);
    }
    else if(tail == null)
    {
      setNullTail(t);
    }
    else
    {
      doubleLinkedNode temp = tail;
      tail = t;
      tail.setPrev(temp.getPrev());
      tail.setNext(null);
    }
  }

  //add one to the beginning of the list and make it head
  public void addBegin(doubleLinkedNode h)
  {
    if(head == null)
    {
      setNullHead(h);
    }
    else
    {
      head.setPrev(h);
      h.setNext(head);
      h.setPrev(null);
      head = h;
      length++;
    }
  }

  //add one to the end of the list and make it tail
  public void addEnd(doubleLinkedNode t)
  {
    if(head == null)
    {
      setNullHead(t);
    }
    else if(tail == null)
    {
      setNullTail(t);
    }
    else
    {
      tail.setNext(t);
      t.setPrev(tail);
      t.setNext(null);
      tail = t;
      length++;
    }
  }

  //get a node from a particular place
  public doubleLinkedNode getNode(int place)
  {
    if(place < 1 || place > length) {return null;}
    doubleLinkedNode temp = head;
    for(int i = 1; i < place; i++) {temp = temp.getNext();}
    return temp;
  }

  //delete a node from the linked list
  public void deleteNode(int place)
  {
    //check to make sure the place is in the list
    if (place < 1 || place > length) {return;}

    //create a temp node to loop through the list until we find the node
    doubleLinkedNode temp = head;
    for(int i = 1; i < place; i++) {temp = temp.getNext();}

    //if the node is not tail set the next nodes previous as the current nodes previous
    if(temp.getNext() != null) {(temp.getNext()).setPrev(temp.getPrev());}
    //if the node is not head set the previous nodes next as the current nodes next
    if(temp.getPrev() != null) {(temp.getPrev()).setNext(temp.getNext());}
    //replace our head and tail if necessary
    if(temp.getNext() == null && temp.getPrev() != null) {tail = temp.getPrev();}
    else if (temp.getNext() != null && temp.getPrev() == null) {head = temp.getNext();}
    //if we have just head set it to null
    if(temp.getNext() == null && temp.getPrev() == null) {head = null;}
    length--;
  }

  //method for deleting a node by passing in a node
  public void deleteNode(doubleLinkedNode del)
  {
    doubleLinkedNode temp = head;
    //attempt to find the node in the list then call the delete method at the right place
    for(int i = 1; i <= length; i++)
    {
      if(temp.getSeat() == del.getSeat() && temp.getRow() == del.getRow())
      {
        deleteNode(i);
	      return;
      }
      temp = temp.getNext();
    }
  }

  //combine the previous two into one function for ease needed
  public doubleLinkedNode getDeleteNode(int place)
  {
    doubleLinkedNode temp = getNode(place);
    deleteNode(place);
    return  temp;
  }

  //method to insert a node at a particular place
  public void insertNode(int place, doubleLinkedNode add)
  {
    //make sure the place is valid and the node is not null
    if(place < 1 || place > length + 1 || add == null) {return;}

    //check for if the place is the beginning of the list or end of it
    if(place == 1) {addBegin(add);}
    else if(place == length + 1) {addEnd(add);}

    //loop to the right place then insert the new node
    else
    {
      doubleLinkedNode temp = head;
      for(int i = 1; i < place; i++) {temp = temp.getNext();}
      add.setNext(temp);
      add.setPrev(temp.getPrev());
      (add.getPrev()).setNext(add);
      temp.setPrev(add);
    }
    length++;
  }

  //method for getting the number of nodes in the list
  public int length() {return length;}

  //method for adding a node based on its row and seat number
  public void addNode(doubleLinkedNode add)
  {
    //test if the list is empty and set the node as head if need be
    if(head == null) {setNullHead(add);}
    else
    {
      //see if we need to set the new node as head
      if(head.getRow() > add.getRow() || (head.getRow() == add.getRow() && head.getSeat() > add.getSeat()))
      {
        addBegin(add);
      }
      //test if the list is only head and add if neccesary
      else if(tail == null) {setNullTail(add);}
      else
      {
        doubleLinkedNode temp = head.getNext();
        boolean check = false;

        //loop for testing where the new node will insert to
        for(int i = 1; i < length && !check; i++)
        {
          //test to find the node that would be next after the node we are trying to add
          if(temp.getRow() > add.getRow() || (temp.getRow() == add.getRow() && temp.getSeat() > add.getSeat()))
          {
            //add the node
            check = true;
            add.setNext(temp);
            add.setPrev(temp.getPrev());
            (add.getPrev()).setNext(add);
            temp.setPrev(add);
            length++;
          }
          temp = temp.getNext();
        }
        //if we don't add the node then add it to the end of the list
        if(!check) {addEnd(add);}
      }
    }
  }
}
