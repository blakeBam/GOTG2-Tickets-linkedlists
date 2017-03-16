//Blake Bambico
//bcb140130
//CS 2336.002
import linklist.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;

public class Main {
  public static void main(String[] args) throws FileNotFoundException{
    //create all the file objects
    File A1file = new File("A1.txt");
    File A2file = new File("A2.txt");
    File A3file = new File("A3.txt");

    //the legendary universal scanner
    Scanner input;

    //intialize the linked list into an array so the open and reserved can be easily accessed
    linkedList[] A1 = {new linkedList(), new linkedList()};
    //also keep track of the number of rows and seats in each row in an array
    int[] A1dim = new int[2];
    //good habits
    if(A1file.exists())
    {
      input = new Scanner(A1file);
      //read in the text file and store the appropriate lists
      setLists(A1, A1dim, input);
    }

    //same as before
    linkedList[] A2 = {new linkedList(), new linkedList()};
    int[] A2dim = new int[2];
    if(A2file.exists())
    {
      input = new Scanner(A2file);
      setLists(A2, A2dim, input);
    }

    //same as before
    linkedList[] A3 = {new linkedList(), new linkedList()};
    int[] A3dim = new int[2];
    if(A3file.exists())
    {
      input = new Scanner(A3file);
      setLists(A3, A3dim, input);
    }

    //store everything in an array for ease of use in the menu
    linkedList[][] all = {A1, A2, A3};
    int[][] allDim = {A1dim, A2dim, A3dim};
    int choice = 0, audi = 0, row = 0, seat = 0, num = 0;
    input = new Scanner(System.in);

    //menu
    while(choice != 3)
    {
      //display the main menu for the user
      System.out.println("Welcome to Guardians of the Galaxy 2!");
      System.out.println("Please choose and option below:");
      System.out.println("1. Reserve Seats");
      System.out.println("2. View Auditorium");
      System.out.println("3. Exit");

      //validate the user input if its not an int and not in the range then throw
      //an exception and ask the user to enter a valid input
      try
      {
        choice = input.nextInt();
        if(choice < 1 || choice > 3) {throw new InputMismatchException();}
      }
      catch (InputMismatchException e)
      {
        System.out.println("Error only enter an integer from 1 to 3\n");
        input.nextLine();
        continue;
      }

      //prompt the user for their choice of auditorium and display it
      if(choice != 3) {audi = validateAudi(all, allDim, input);}

      if(choice == 1)
      {
        //get a valid row and seat choice
        row = getValidChoice("Enter the row you would like: ", input, allDim[audi][0]);
        seat = getValidChoice("Enter the starting seat number: ", input, allDim[audi][1]);
        boolean check = false;
        //get a volid number of seats to reserve
        while(!check)
        {
          System.out.print("Enter the number of tickets you would like: ");
          try
          {
            num = input.nextInt();
            if(num < 0) {throw new InputMismatchException();}
            check = true;
          }
          catch(InputMismatchException e)
          {
            System.out.println("Error enter a nonnegative integer");
            input.nextLine();
          }
        }

        //check if the users seats are available
        check = checkAvailable(row, seat, num, all[audi]);

        //display a confirmation message if they are
        if(check) {System.out.println("You're seats are available and have been reserved");}

        //attempt to find the best available seats in the auditorium
        else
        {
          //find the midpoint of the auditorium
          double[] midpoint = {(allDim[audi][0] + 1)/2.0, (allDim[audi][1] + 1)/2.0};
          //call a function to handle the best available seat
          bestAvailable(all[audi], input, num, midpoint);
        }
      }
    }

    //array to hold the totals
    int[] totals = new int[2];
    for(int i = 0; i < totals.length; i++)
    {
      totals[i] = all[0][i].length() + all[1][i].length() + all[2][i].length();
    }
    //neatly display the final report back to the user
    //shows them the number of reserved and open seats as well as the sales for each
    //auditorium and a final row that has the totals of each column
    System.out.println("   Labels      Reserved      Open        Sales");
    for(int i = 0; i < all.length; i++)
    {
      System.out.print("Auditorium " + (i + 1) + ":    ");
      System.out.printf(" %-12d%-11d$%-12d\n", all[i][1].length(), all[i][0].length(), all[i][1].length() * 7);
    }
    System.out.print("Totals:          ");
    System.out.printf(" %-12d%-11d$%-12d\n", totals[1], totals[0], totals[1] * 7);


    //todo add print back to the files
  }

  //method for reading in the auditorium from the file and storing into the lists
  public static void setLists(linkedList[] audi, int[] dim, Scanner input)
  {
    while(input.hasNext())
    {
      //read in each line from the file and store its length in seat length
      String line = input.next();
      dim[1] = line.length();
      for(int i = 0; i < line.length(); i++)
      {
        //loop through eack seat and create a new node for each
        doubleLinkedNode temp = new doubleLinkedNode(dim[0] + 1, i + 1);
        //depending on if it is open or not add it to the appropriate list
      	if(line.charAt(i) == '.')
      	{
      	  audi[1].addEnd(temp);
      	}
      	else
      	{
      	  audi[0].addEnd(temp);
      	}
      }
      //increase the row size each iteration
      dim[0]++;
    }
  }

  //method for validating the auditorium choice and displaying it to the user
  public static int validateAudi(linkedList[][] all, int[][] allDim, Scanner input)
  {
    //flag and loop to validate user input
    int choice = 0;
    boolean check = false;
    while(!check)
    {
      //display the submenu
      System.out.println("Select an auditorium:");
      System.out.println("1. Auditorium 1");
      System.out.println("2. Auditorium 2");
      System.out.println("3. Auditorium 3");

      //make sure the input is within the appropriate range
      try
      {
        choice = input.nextInt();
        if(choice < 1 || choice > 3) {throw new InputMismatchException();}
        check = true;
      }
      catch(InputMismatchException e)
      {
        System.out.println("Error only enter an integer from 1 to 3\n");
        input.nextLine();
      }
    }

    //display the requested auditorium
    System.out.println();
    printAuditorium(all[choice - 1], allDim[choice - 1]);
    System.out.println();

    return choice - 1;
  }

  //simple method to get a valid input from the user and return it back to main
  public static int getValidChoice(String display, Scanner input, int length)
  {
    int choice = 0;
    boolean check = false;
    //loop to validate the user input
    while(!check)
    {
      //display the approptiate message for the user
      System.out.print(display);
      try
      {
        choice = input.nextInt();
        if(choice < 1 || choice > length) {throw new InputMismatchException();}
        check = true;
      }
      catch(InputMismatchException e)
      {
        System.out.println("Error enter an integer from 1 to " + length);
        input.nextLine();
      }
    }
    return choice;
  }

  //method for checking if the users choice of seats is available
  //if it is the book it for them
  public static boolean checkAvailable(int row, int seat, int num, linkedList[] audi)
  {
    doubleLinkedNode temp = audi[0].getHead();
    boolean check = false;
    //keep track of where in the open list we are in case we need to move from it
    int place = 1;

    //stay in the loop while we haven't found the spot and not passed the row
    while(temp != null && !check && temp.getRow() <= row)
    {
      //test for if we find the starting row and seat
      if(temp.getRow() == row && temp.getSeat() == seat)
      {
        check = true;
        //continously loop to check if consecutive seats are available in the row
        for(int i = seat; i < seat + num && check; i++)
        {
          if(temp == null || temp.getRow() != row || temp.getSeat() != i) {check = false;}
	        else {temp = temp.getNext();}
        }

        //if the seats are available then reserve them
        if(check)
        {
          for(int i = 0; i < num; i++)
          {
            //get the appropriate node  and delete it from the open and add it to the reserved
            temp = audi[0].getDeleteNode(place);
            audi[1].addNode(temp);
          }
        }
      }
      //make sure not to call a function on null
      if(temp != null) {temp = temp.getNext();}
      //increment our place holder that is used for moving nodes
      place++;
    }
    return check;
  }

  //method for finding the best available seat in the auditorium
  //may come back with a more efficent algorithm but works for now
  public static void bestAvailable(linkedList[] audi, Scanner input, int num, double[] midpoint)
  {
    //nodes to store during the loop
    doubleLinkedNode temp = audi[0].getHead(), cur, best = null;
    //keeps track of the distance of the left and right side of the seats
    int[] leftPoint = {0,0}, rightPoint = {0,0};
    //intialize the distance to be off the auditorium
    double[] distance = {calcDistance(midpoint, leftPoint),calcDistance(midpoint, rightPoint)};
    while(temp != null)
    {
      //create a flag to test if we have a match
      boolean check = true;
      cur = temp;
      //loop through the list only the requested seat size, check if there are any
      //consecutive seats on the same row
      for(int i = temp.getSeat(); i < num + temp.getSeat() && check; i++)
      {
        //if a seat is a consecutive go on to the next seat
        if(cur != null && cur.getSeat() == i && cur.getRow() == temp.getRow()) {cur = cur.getNext();}
        //leave if not
        else {check = false;}
      }
      if(check)
      {
        //since our seats meet our criteria create the appropriate left and right point arrays
        leftPoint[0] = temp.getRow();
	      leftPoint[1] = temp.getSeat();
        rightPoint[0] = temp.getRow();
        rightPoint[1] = temp.getSeat() + num - 1;
        //test if the distances are smaller than what we had before put in a loop for continous testing
	      while(distance[0] + distance[1] > calcDistance(midpoint, leftPoint) + calcDistance(midpoint, rightPoint))
        {
          //since it is smaller store it as the new point and mark the node
          distance[0] = calcDistance(midpoint, leftPoint);
          distance[1] = calcDistance(midpoint, rightPoint);
          best = temp;
          //another test to see if the next node after our test would give us a better result
          //saves a lot of computation time so we dont recheck nodes
      	  if(cur != null && cur.getRow() == temp.getRow() && cur.getSeat() == temp.getSeat() + num)
      	  {
      	    temp = temp.getNext();
      	    cur = cur.getNext();
      	    leftPoint[0] = temp.getRow();
      	    leftPoint[1] = temp.getSeat();
      	    rightPoint[0] = temp.getRow();
      	    rightPoint[1] = temp.getSeat() + num - 1;
      	  }
        }
      }
      //move temp however far we have tested to speed up the calculation
      temp = cur;
    }

    if(best != null)
    {
      //display to the user depending on the number of seats requested that we have
      //found seats for them and indicate where the seats are
      System.out.print("We have found " + num);
      if(num > 1)  {System.out.print(" open seats starting ");}
      else {System.out.print(" open seat ");}
      System.out.print("on row " + best.getRow() + " seat " + best.getSeat());
      if(num > 1) {System.out.println(" and ending on seat " + (best.getSeat() + num - 1));}
      else {System.out.println();}
      //ask the user if they want to reserve and validate the input
      System.out.print("Would you like to reserve?(Y/N): ");
      while (!input.hasNext("[YN]"))
      {
        System.out.println("Error only enter Y for yes or N for no");
        input.next();
        System.out.println("Would you like to reserve?(Y/N): ");
      }
      String choice = input.next();
      if(choice.equals("Y"))
      {
        //if yes then move the nodes from the open to the reserved linked list
        for(int i = 0; i < num; i++)
        {
          temp = best.getNext();
          audi[0].deleteNode(best);
          audi[1].addNode(best);
          best = temp;
        }
        System.out.println("Your seats have been reserved!\n");
      }
      //tell them it hasn't been reserved
      else
      {
        System.out.println("No seats have been reserved\n");
      }
    }
    //tell them no seats meet their criteria
    else {System.out.println("No available seats were found in the auditorium");}
  }

  //method for calculating the distance between 2 points
  public static double calcDistance(double[] midpoint, int[] point)
  {
    return Math.sqrt(Math.pow((midpoint[0] - point[0]),2) + Math.pow((midpoint[1] - point[1]),2));
  }

  //method for printing the auditorium onto the screen for the user
  public static void printAuditorium(linkedList[] audi, int dimension[])
  {
    //properly display the top row
    System.out.print("  ");
    for(int i = 0; i < dimension[1]; i++) {System.out.print((i+1) % 10);}
    System.out.println();
    //nodes to loop through the list with
    doubleLinkedNode curOpen = audi[0].getHead(), curClosed = audi[1].getHead();
    for(int i = 0; i < dimension[0]; i++)
    {
      //show row number
      System.out.print((i+1) + " ");
      for(int j = 0; j < dimension[1]; j++)
      {
        //depending on the current seat check whether it is open or reserved and print it
        //then move to the next item in the appropriate list
        if(curOpen != null && curOpen.getRow() == i + 1 && curOpen.getSeat() == j + 1)
      	{
      	  System.out.print('#');
      	  curOpen = curOpen.getNext();
      	}
      	else if( curClosed != null && curClosed.getRow() == i + 1 && curClosed.getSeat() == j + 1)
      	{
      	  System.out.print('.');
      	  curClosed = curClosed.getNext();
      	}
      	else {System.out.print('N');}
      }
      System.out.println();
    }
  }
}
