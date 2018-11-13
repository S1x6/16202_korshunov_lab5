package client;

import model.Model;
import view.ConsoleGui;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: .jar (baseUrl)");
            return;
        }
        String baseUrl = args[0];
        try {
            new URI(baseUrl);
        } catch (URISyntaxException e) {
            System.out.println("Incorrect URL syntax");
            return;
        }
        Model.getInstance().setGui(new ConsoleGui());
        new Thread(new Client(baseUrl)).start();
    }

}
