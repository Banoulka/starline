package MVCs.StartScene;

import Abstracts.Controller;
import Abstracts.Model;
import Base.SceneManager;
import Base.Scenes.PlayScene;
import MVCs.PlayerData.M_PlayerData;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class C_StartScene extends Controller {

    protected V_StartScene view;

    public C_StartScene(Pane root) {
        view = new V_StartScene(root);

        // Set the start button event handler and pass it the handle method
        // Also set the enter key for the input box as a shortcut
        view.getStartButton().setOnMouseReleased(actionEvent -> handleStartButton());
        view.getNameInput().setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER && view.getNameInput().isFocused())
                handleStartButton();
        });
    }

    @Override
    public Model model() {
        return null;
    }

    @Override
    public V_StartScene view() {
        return view;
    }

    public void handleStartButton() {
        System.out.println("C_StartScene :: Handle Start Button");

        String newName = view.getNameInput().getText();

        if (newName.isBlank()) {
            // Send error to screen
            view.toggleError(true);
        } else {
            // Continue to new screen and send name
            M_PlayerData.getInstance().setPlayerName(view.getNameInput().getText());

            // Switch the scene to the play scene
            SceneManager.setCurrScene(PlayScene.get());
        }

    }
}
