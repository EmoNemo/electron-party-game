//public void NewStructure(){ //reads atoms.txt file and sends relevant information back to main class ElectronParty

import java.io.*; //for file I/O
import java.util.Scanner;

public class NewStructure{

        int randnum;
        int hofPos;
        static int[] hof = {0,11,13,15,16,9,14};   //**  Outof Boundary Exception on row 17:  int[] hof = {1,12,14,16,17,10,15};

        String[][] arr = new String [17][4];
        int hofE;

        public void NewStructure(){
        }

        public void generateNew(){ //reads the text file of possible Lewis structures, then makes new problem

            File x = new File("atoms.txt"); //contains list of atoms, starting electrons, needed electrons and image name
            Scanner y = null;
            String z;
            try{
                    y = new Scanner(x);
            }catch(FileNotFoundException e)
            {
                System.out.println("Can't open file");
                System.exit(1); //terminate if error in file
            }

            // read all contents of file to String array arr
            for(int r=0; r<arr.length; r++){
                for (int c=0; c<=arr[0].length-1; c++){   //** <= length -1 or < length
                    if(y.hasNext()){
                        z=y.next();
                        arr[r][c]=z;
                    }
                }
            }

            randnumElement(); //generates a new problem
        }

        //** used for position the picture by random
        public void randnumElement()
        {
            randnum=(int)(Math.random()*7)-1; // numbers between 0 to 6 for the 6 hofbrincl molecules

            hofPos = hof[randnum];     // randnum pick one row
        }

        public int hofE(){
                hofE = Integer.parseInt(arr[hofPos][1]);  //column 2, not column 3 of this row
                return hofE;

        } 

        public String hofImage(){
            String imageFile = arr[hofPos][3];   // imageFileName
            return imageFile; //set to hostpicture in ElectronParty class
        }

        public int correctHof(){ //returns number of electrons the element needs, used for checkCorrect() method in other class
            int  hofOctet = Integer.parseInt(arr[hofPos][2]);
            return hofOctet;
        }
}
