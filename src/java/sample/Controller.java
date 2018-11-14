package sample;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Controller {

    ////////////////////    ////////////////////

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField urlField;

    @FXML
    private Button goToButton;

    @FXML
    private Label appStatus;

    @FXML
    private Button startSearchButton;

    @FXML
    private TextField searchElemField;

    @FXML
    private ChoiceBox<String> chouseIdentifir;

    @FXML
    private Label searchResult;

    @FXML
    private TextField optionalSearchElemField;

    @FXML
    private Button setThatElemSourceButton;

    @FXML
    private Button setSourceWebPageButton;

    @FXML
    private Label elemCount;

    @FXML
    private ChoiceBox<Integer> numberElemCheckBox;

    @FXML
    private Button clickOnElem;

    @FXML
    private TextField insertTextField;

    @FXML
    private Button insertTextButton;

    @FXML
    private Button clearButton;

    @FXML
    private Label getTextResultField;

    @FXML
    private Label getClassNameResultField;

    @FXML
    private Label getValueResultField;

    @FXML
    private Label getNameResultField;

    @FXML
    private Button getAttributeButton;

    @FXML
    private TextField attributeField;

    @FXML
    private Label getAttributeResultField;

    @FXML
    private Button closeBrowser;

    @FXML
    private Label clickCountLabel;

    ////////////////////    ////////////////////

    private WebActions webActions;
    private WebDriver driver;
    private WebElement searchSource;
    private List<WebElement> webElements;
    private Integer clickCount;

    //  String URL = "http://dev.agroclub-coordinator.spider.ru/auth/sign-in";

    @FXML
    void initialize() {
        clickCount = 0;

        chouseIdentifir.setItems(FXCollections.observableArrayList("id", "XPath", "className", "tag", "name"));


        goToButton.setOnAction(event -> {
            appStatus.setText("WAIT");
            clickCountInc();
            try {
                webActions = new WebActions();
                driver = webActions.getDriver();
                driver.get(urlField.getText());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            appStatus.setText("READY");

        });

        startSearchButton.setOnAction(event -> {
            clickCountInc();
            searchElem();
        });

        clickOnElem.setOnAction(event -> {
            appStatus.setText("WAIT");
            clickCountInc();
            if (searchResult.getText().equals("Present")) {


                webActions.waitToBeClicable(webElements.get(numberElemCheckBox.getValue())).click();
            }
            appStatus.setText("READY");

        });

        getAttributeButton.setOnAction(event -> {
            appStatus.setText("WAIT");
            clickCountInc();
            if (searchResult.getText().equals("Present")) {
                getAttributeResultField.setText(webActions.waitToBeClicable(webElements.get(numberElemCheckBox.getValue())).getAttribute(attributeField.getText()));
            }
            appStatus.setText("READY");

        });

        setThatElemSourceButton.setOnAction(event -> {
            clickCountInc();
            appStatus.setText("WAIT");
            if (searchResult.getText().equals("Present")) {
                searchSource = webElements.get(numberElemCheckBox.getValue());
                searchElem();
            }
            appStatus.setText("READY");

        });

        setSourceWebPageButton.setOnAction(event -> {
            clickCountInc();
            searchSource = null;
            searchElem();
        });

        insertTextButton.setOnAction(event -> {
            appStatus.setText("WAIT");
            clickCountInc();
            if (searchResult.getText().equals("Present")) {
                webActions.waitToBeClicable(webElements.get(numberElemCheckBox.getValue())).sendKeys(insertTextField.getText());
            }
            appStatus.setText("READY");

        });

        clearButton.setOnAction(event -> {
            appStatus.setText("WAIT");
            clickCountInc();
            if (searchResult.getText().equals("Present")) {
                webActions.clearField(webActions.waitToBeClicable(webElements.get(numberElemCheckBox.getValue())));
            }
            appStatus.setText("READY");

        });

        closeBrowser.setOnAction(event -> {
            appStatus.setText("WAIT");
            clickCountInc();
            driver.close();
            appStatus.setText("READY");

        });

        numberElemCheckBox.setOnAction(event -> {
            appStatus.setText("WAIT");
            try {


                getName();
                getValue();
                getClassName();
                getText();
            }catch (Exception e){
                System.out.println("error: "+ e.toString());
            }
            appStatus.setText("READY");

        });
    }

    void clickCountInc() {
        clickCount++;
        clickCountLabel.setText(clickCount.toString());
    }

    void setTextOnSearchResult(String val) {
        if (val.equals("NULL")) {
            searchResult.setText("NULL");
            searchResult.setStyle("-fx-background-color: #D03D3D;");

        } else {
            searchResult.setText("Present");
            searchResult.setStyle("-fx-background-color: #84D484;");
        }
    }

    void searchElem() {
        appStatus.setText("WAIT");
        clickCountInc();
        String findProperty = chouseIdentifir.getValue();
        String findValue = searchElemField.getText();
        if (!findValue.equals("")) {
            By locator = null;
            switch (findProperty) {
                case "id": {
                    locator = By.id(findValue);
                    break;
                }

                case "XPath": {
                    locator = By.xpath(findValue);
                    break;
                }

                case "className": {
                    locator = By.className(findValue);
                    break;
                }

                case "tag": {
                    locator = By.tagName(findValue);
                    break;
                }

                case "name": {
                    locator = By.name(findValue);
                    break;
                }
            }
            if (searchSource != null)
                webElements = searchSource.findElements(locator);
            else
                webElements = driver.findElements(locator);

            if (webElements.size() == 0) {
                setTextOnSearchResult("NULL");

            } else {
                setTextOnSearchResult("Present");


                elemCount.setText(String.valueOf(webElements.size()));

                Integer[] webElementsItems = new Integer[webElements.size()];
                for (int i = 0; i < webElements.size(); i++)
                    webElementsItems[i] = i;

                numberElemCheckBox.setItems(FXCollections.observableArrayList(webElementsItems));
                numberElemCheckBox.setValue(0);
            }
            appStatus.setText("READY");
        }
    }

    void getText() {
        if (searchResult.getText().equals("Present")) {
            getTextResultField.setText(webActions.waitToBeClicable(webElements.get(numberElemCheckBox.getValue())).getText());
        }
    }

    void getClassName() {
        if (searchResult.getText().equals("Present")) {
            getClassNameResultField.setText(webElements.get(numberElemCheckBox.getValue()).getAttribute("class"));
        }
    }

    void getName() {
        if (searchResult.getText().equals("Present")) {
            getNameResultField.setText(webElements.get(numberElemCheckBox.getValue()).getAttribute("name"));
        }
    }

    void getValue() {
        if (searchResult.getText().equals("Present")) {
            getValueResultField.setText(webElements.get(numberElemCheckBox.getValue()).getAttribute("value"));
        }
    }
}
