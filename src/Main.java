import model.Model;
import swing.GameView;
import swing.controler.SwingController;

public class Main {

    private static Model model;
    private static GameView view;
    private static SwingController controller;

    public static void main(String[] args) {
        model = new Model();
        view = new GameView(model);
        controller = new SwingController(model, view);
        model.setUiDelegate(view);
        view.setController(controller);
        view.start();
    }

}
