package MVCs.ViewScene;

import Abstracts.CelestialBody;
import Abstracts.PlanetData;
import Abstracts.View;
import Base.Interfaces.IRunAfter;
import Base.PlanetData.EarthData;
import Base.StarFactory;
import Base.Utility.Coord;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class V_ViewScene extends View implements IRunAfter {

    private Pane pane;

    private C_ViewScene controller;
    private CelestialBody viewing;

    public V_ViewScene(Pane root, C_ViewScene controller) {
        super(root);
        this.pane = root;
        this.controller = controller;
        this.viewing = ((M_ViewScene) controller.model()).getCurrViewing();

        Image image = new Image("/Resources/Min/BACKGROUND_NO_STAR-min.png", 1920, 1080, false, true);
        BackgroundSize backgroundSize = new BackgroundSize(105, 105, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );
        Background bg = new Background(backgroundImage);

        // New pane for layout and size
        pane = new Pane();
        pane.setBackground(bg);

        ((BorderPane) root).setCenter(pane);
    }

    @Override
    public void updateView() {

    }

    @Override
    public void runAfter() {
        setupPlanet();
        setupStars();
        setupUI();
    }

    private void setupStars() {
        // Creating the star background with different settings
        Pane backgroundLayer = new Pane();
        backgroundLayer.setPrefSize(pane.getWidth(), pane.getHeight());

        StarFactory starFactory = new StarFactory(
                backgroundLayer.getPrefWidth(),
                backgroundLayer.getPrefHeight(),
                StarFactory.StarType.RANDOM);
        starFactory.setNoOfStars(150);
        backgroundLayer.getChildren().addAll(starFactory.buildStars());

        pane.getChildren().add(backgroundLayer);
    }

    private void setupPlanet() {
        viewing.setPosition(new Coord(pane.getWidth() / 6, pane.getHeight() / 2));
        viewing.update();
        viewing.startAnimation();
        pane.getChildren().add(viewing);
    }

    private void setupUI() {
        Pane UIPane = new Pane();
        UIPane.setPrefSize(pane.getWidth() / 3, pane.getHeight() - 300);
        UIPane.setLayoutX(pane.getWidth() - UIPane.getPrefWidth() - 150);
        UIPane.setLayoutY(pane.getHeight() - UIPane.getPrefHeight() - 150);
        UIPane.setStyle("-fx-background-color: rgba(50,52,49,0.76)");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 15, 15, 15));
        vBox.setPrefSize(UIPane.getPrefWidth(), UIPane.getPrefHeight());
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.setSpacing(20);

        PlanetData planetData = viewing.getPlanetData();
        PlanetData earthData = new EarthData();

        Label title = new Label();
        title.setText(viewing.getName());
        title.getStyleClass().addAll("title", "margin-bottom");

        Label gravity = new Label();
        gravity.setText("Gravity Pull: " + planetData.getGravity());

        Label age = new Label();
        age.setText("Age: " + planetData.getAge());

        Label size = new Label();
        size.setText("Size: " + planetData.getSize());

        Label compareTo = new Label();
        compareTo.setText("------------- Compare To Earth -------------");
        compareTo.setPadding(new Insets(50, 50, 50, 50));


        HBox ageBox = new HBox();
        HBox sizeBox = new HBox();
        HBox gravityBox = new HBox();

        ageBox.setAlignment(Pos.BASELINE_CENTER);
        sizeBox.setAlignment(Pos.BASELINE_CENTER);
        gravityBox.setAlignment(Pos.BASELINE_CENTER);

        Label ageCompare = new Label();
        ageCompare.setText("Age: " + earthData.getAge());
        ageBox.getChildren().add(ageCompare);

        Label sizeCompare = new Label();
        sizeCompare.setText("Size: " + earthData.getSize());
        sizeBox.getChildren().add(sizeCompare);

        Label gravityCompare = new Label();
        gravityCompare.setText("Gravity: " + earthData.getGravity());
        gravityBox.getChildren().add(gravityCompare);

        Label upArrow = new Label();
        upArrow.setText("\uD83E\uDC45");
        upArrow.setStyle("-fx-text-fill: green");
        upArrow.setPadding(new Insets(0, 0, 0, 50));

        Label downArrow = new Label();
        downArrow.setText("\uD83E\uDC47");
        downArrow.setStyle("-fx-text-fill: red");
        downArrow.setPadding(new Insets(0, 0, 0, 50));

        if (planetData.getAgeNumber() != earthData.getAgeNumber())
        ageBox.getChildren().add(
                planetData.getAgeNumber() > earthData.getAgeNumber() ? downArrow : upArrow
        );

        upArrow = new Label();
        upArrow.setText("\uD83E\uDC45");
        upArrow.setStyle("-fx-text-fill: green");
        upArrow.setPadding(new Insets(0, 0, 0, 50));

        downArrow = new Label();
        downArrow.setText("\uD83E\uDC47");
        downArrow.setStyle("-fx-text-fill: red");
        downArrow.setPadding(new Insets(0, 0, 0, 50));

        if (planetData.getGravityNumber() != earthData.getGravityNumber())
        gravityBox.getChildren().add(
                planetData.getGravityNumber() > earthData.getGravityNumber() ? downArrow : upArrow
        );

        upArrow = new Label();
        upArrow.setText("\uD83E\uDC45");
        upArrow.setStyle("-fx-text-fill: green");
        upArrow.setPadding(new Insets(0, 0, 0, 50));

        downArrow = new Label();
        downArrow.setText("\uD83E\uDC47");
        downArrow.setStyle("-fx-text-fill: red");
        downArrow.setPadding(new Insets(0, 0, 0, 50));

        if (planetData.getSizeNumber() != earthData.getSizeNumber())
            sizeBox.getChildren().add(
                    planetData.getSizeNumber() > earthData.getSizeNumber() ? downArrow : upArrow
            );

        vBox.getChildren().addAll(title, age, gravity, size, compareTo, ageBox, gravityBox, sizeBox);

        Button backButton = new Button();
        backButton.setText("< Back To Planets");
        backButton.getStyleClass().add("action-btn");
        backButton.setLayoutX(20);
        backButton.setLayoutY(UIPane.getPrefHeight() - 50);
        backButton.setOnMousePressed(actionEvent -> controller.backToPlay());

        UIPane.getChildren().addAll(vBox, backButton);

        pane.getChildren().add(UIPane);
    }

    public Pane getPane() {
        return pane;
    }
}
