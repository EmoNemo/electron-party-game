/* Main class for Educational game
 * How to play: select from the drop down menu the number of electrons
that should be traded to complete an octet.
 * connects to NewStructure class which reads from atoms.txt and sends
information about images and electrons
*/
import java.awt.*; //import libraries for panels and components
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.JOptionPane;

class ElectronParty extends JFrame implements ActionListener{
                // global variables for all classes (nested)
        static boolean visible = false; //set Visible for instructions
        static boolean expVisible = false; //set Visible for explanations

        static NewStructure instance;
        static int hostElectrons; //user generated # electrons shared
        static int sideElectrons;//any atom that's not the host
       // static int correctCounter;
        static boolean correct;
        static int correctCounter;

        static Image backgrnd =
Toolkit.getDefaultToolkit().getImage("background.jpeg"); //see devianArt in bibliography
        static Image instructions = Toolkit.getDefaultToolkit().getImage("instructions.gif"); //made with GIMP software
        static String hostpicture = ""; //strings with all the names of the images I need
        static String sidepicture = ""; //allows for different host & side atoms
        static Image hostatom = Toolkit.getDefaultToolkit().getImage(hostpicture);
        static Image otheratom = Toolkit.getDefaultToolkit().getImage(sidepicture);

        static ElectronParty mainpanel;
        static AtomOneP atom1;
        JButton done = new JButton("Submit");
        JButton hints = new JButton("Hints");

        public ElectronParty(){
                super("Electron Party!"); //make background (JFrame)

                //**  moved from the main
                instance = new NewStructure();  //** instance becomes global variable
                instance.generateNew();
                displayAnElement();

                atom1 = new AtomOneP();
                setContentPane(atom1); //adding other panel on top

                setSize(1000,500);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setLocation(0,0);
                setResizable(true);
                setVisible(visible);

                add(done); // button if the user has completed lewis structure, start checkCorrect method
                done.addActionListener(this);
                add(hints);
                hints.addActionListener(this);


        }


        public static void main(String [] args){
            Welcome w = new Welcome(); //starts with instructions JFrame, welcome calls main panel

        }

        public void actionPerformed(ActionEvent e){
            if (e.getSource()== done)
                checkCorrect(); //precondition: user finishes structure
                                //postcondition: correct boolean set to true/false
            else if (e.getSource()== hints){
                expVisible=true;
                Concept c = new Concept();
                hints.addActionListener(c);
            }
        }

        // need to review checkCorrect because the Done button is pressed
        public static void checkCorrect(){
            // check that all atoms have full octet
            int cHof = instance.correctHof() - instance.hofE() ; //cHof = number of electrons in full octet

            if (hostElectrons==cHof && sideElectrons==cHof){ //both host and side must fulfill octet rule
                correct = true;
                correctCounter++;
                JOptionPane.showMessageDialog(null,  "Correct! Your Score is "+correctCounter+"", "Game Score", JOptionPane.PLAIN_MESSAGE);    //** add this counter box for display
                instance.generateNew(); //next 3 lines display new problem, calls methods again
                displayAnElement();
                atom1.repaint();
            }
            else {
                correct = false;
                correctCounter--;
                JOptionPane.showMessageDialog(null,  "Wrong! Your Score is "+correctCounter+"\n How many electrons does the host atom have? Hint: what family on the periodic table did it come from? \n How many does it need? Trade electrons with the guests to fill him or her up.", "Game Score", JOptionPane.PLAIN_MESSAGE);    //** add this counter box for display
                JOptionPane.showMessageDialog(null,  "Wrong! Your Score is "+correctCounter+"\n How many electrons does the host atom have? Hint: what family on the periodic table did it come from? \n How many does it need? Trade electrons with the guests to fill him or her up.", "Game Score", JOptionPane.PLAIN_MESSAGE);    //** add this counter box for display
                }
            }

        public static void displayAnElement(){

                sideElectrons= 0 ;
                hostElectrons= 0;
                hostpicture = instance.hofImage();
                sidepicture = instance.hofImage();
                if ( hostpicture != "" )
                    hostatom =
Toolkit.getDefaultToolkit().getImage(hostpicture);
//** hostImage

                if ( sidepicture != "" )
                    otheratom =
Toolkit.getDefaultToolkit().getImage(sidepicture);
//** hostImage
        }


static class Welcome extends JFrame implements ActionListener { //ActionListener for MenuItem
    JButton start = new JButton("start");
        public Welcome(){
                super("Welcome"); //make background (JFrame)
                WelcomeP instruct = new WelcomeP();
                // use instance of class for JFrame in main class
                setContentPane(instruct); //adding other panel on top
                setSize(1000,500);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setLocation(0,0);
                setResizable(true);
                setVisible(true);
                add(start);
               start.addActionListener(this);
        }

        public void actionPerformed(ActionEvent e){
        //if start is selected, load electron party frame
            if (e.getSource()== start){
                visible=true;
                mainpanel = new ElectronParty();
            }
        }

        class WelcomeP extends JPanel {
            public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        g.drawImage(instructions,0,0,this); //instructions=text on an image file
             }

         }
}

static class Concept implements ActionListener{ //ActionListener for MenuItem

        static String conceptText = "Tips on how to throw a good electron
party: \n Molecules bond so that each atom has a full valence
(outermost) shell. single atoms don't have enough valence
electrons: \n Group 1 has 1, Group 2 has 2, etc. but need 8 to be
'happy' (except Hydrogen which only needs 2) \n 4 steps to making
a Lewis structure: \n 1. Count all the electrons atoms start with
(total appetizers brought to the party) \n 2. Count the number of
electrons appetizers needed for each atom to be full, without
sharing \n 3. (Answer of step 2 - answer of step 1) / 2 \n 4.
Decide which atoms share which electrons."; //"insert hint here,
such as how many electrons needed to complete the bond? ";  //** a
little bit of hint
        public void actionPerformed(ActionEvent e){
            JOptionPane.showMessageDialog(null, conceptText, "Concept
Explanation", JOptionPane.PLAIN_MESSAGE); //see second
citation in bibliography
        }

    }


static class AtomOneP extends JPanel implements ActionListener{ //ActionListener for MenuItem
 JMenuBar hostBar = new JMenuBar();
 JMenu hostMenu = new JMenu("exchange electrons!");
 JMenuItem share=new JMenuItem("share 1 more electron.");
 JMenuItem unshare=new JMenuItem("undo share 1 more electron.");

        public AtomOneP(){ //Panel for
            add(hostBar);
            hostBar.add(hostMenu);
            hostMenu.add(share);
            share.addActionListener(this);
            hostMenu.add(unshare);
            unshare.addActionListener(this);
        }

        public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(backgrnd,0,0,this);
                g.drawImage(hostatom,100, 250,this);
                g.drawImage(otheratom,500,250,this);
        }

        public void actionPerformed(ActionEvent e){
                if (e.getSource()==share){ //to increase electons in octed
                    hostElectrons++;
                    sideElectrons++;
                } else if ( e.getSource()== unshare ){   // used in case gamer makes a mistake
                    if ( hostElectrons >0 ) hostElectrons--;
                    if ( sideElectrons >0 ) sideElectrons--;
                    //cannot have a negative number of electrons
                }
                 JOptionPane.showMessageDialog(null, "Shared"+hostElectrons+" Electrons.", "Electron Counter",JOptionPane.PLAIN_MESSAGE);    // lets user know that they have shared/unshared
        }
     }
}

