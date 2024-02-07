public class Ui {

    private Parser parser;
    private String welcomeMessage = "-----------------------------------\n"
            + "Hello! I'm Cro!\n"
            + "What can I do for you?\n"
            + "-----------------------------------\n";
    private Cro myCro;

    public Ui(Cro cro) {
        System.out.println(welcomeMessage);
        parser = new Parser();
        myCro = cro;
    }

    public boolean reading() {
        return parser.handleInput(myCro);
    }
}
