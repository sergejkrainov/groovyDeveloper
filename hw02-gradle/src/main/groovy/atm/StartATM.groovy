package atm

class StartATM {

    static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ATM atm = new ATM();
        boolean wasQuit = false;

        System.out.println("""ATM started, write your command:
        put X Y, where X - amount, Y - count;
        get X , where X - summary amount to get;
        dump - get count and amount ATM;
        state - output summary amount;
        quit - to quit from application;
        You can put only 1, 3, 5, 10, 25, 50, 100, 500, 1000, 5000""");

        while (!wasQuit) {
            def command = input.next();
            try {
                switch (command) {
                    case "put":
                        System.out.println(atm.put(input.nextInt(), input.nextInt()));
                        break;
                    case "get":
                        System.out.println(atm.get(input.nextInt()));
                        break;
                    case "dump":
                        System.out.println(atm.dump());
                        break;
                    case "state":
                        System.out.println(atm.state());
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
