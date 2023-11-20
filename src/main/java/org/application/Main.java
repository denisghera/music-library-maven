package org.application;

public class Main {
    public static void main(String[] args) {

        InputDevice id = new InputDevice(System.in);
        OutputDevice od = new OutputDevice(System.out);

        if(args.length < 1) od.print("No valid option selected!");
        else if (args[0].equals("start")) {
            if (args.length < 3) od.print("No account specified!");
            else {
                Application app = new Application(id, od);
                app.startApplication(args[1], args[2]);
            }
        }
        else if (args[0].equals("test")) {
            od.print("Testing 123!");
        }
    }
}