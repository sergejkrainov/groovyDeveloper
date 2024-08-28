package atm

class StartATM {

    static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ATM atm = new ATM();
        boolean wasQuit = false;

        System.out.println("""ATM started, write your command:
        put X Y Z, where X - amount, Y - count, Z - currency;
        get X Y, where X - summary amount to get, Y - currency;
        dump - get count and amount ATM;
        state - output summary amount;
        quit - to quit from application;
        You can put only 1, 3, 5, 10, 25, 50, 100, 500, 1000, 5000;
        You can put currency: Rur,USD,Euro""");

        while (!wasQuit) {
            def command = input.next();
            try {
                switch (command) {
                    case "put":
                        println(atm.put(input.nextInt(), input.nextInt(), input.next()));
                        break;
                    case "get":
                        println(atm.get(input.nextInt(), input.next()));
                        break;
                    case "dump":
                        atm.dump()
                        break;
                    case "state":
                        atm.state();
                        break;
                    case "quit":
                        wasQuit = true;
                        break;
                    default:
                        System.out.println("Invalid command");
                        input.nextLine();
                }
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
