package com.mailer.robot;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.script.ScriptException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class MailerKeyword {
    public static WebDriver driver;

    private static final String tableName = "contactTable";
    private static final String nameID = "firstName";
    private static final String lastNameID = "lastName";
    private static final String emailID = "email";
    private static final String addButton = "addContactButton";
    private static final String contactButton = "contactButton";

    public void hello(String name) {
        System.out.println("Hello " + name);
    }

    public String getHello(String name) {
        return ("Hello " + name);
    }

    public void openBrowser(String url) throws MalformedURLException {
//        DesiredCapabilities capability = DesiredCapabilities.chrome();
//        driver = new RemoteWebDriver(new URL("http://192.168.88.10:4444/wd/hub"), capability);

        driver = new ChromeDriver();
        driver.get(url);
    }

    public void clickContractButton() {
        driver.findElement(By.id(contactButton)).click();
    }

    public void setNameField(String firstName) throws AWTException, NoSuchMethodException, ScriptException, IOException {
        WebElement ele = driver.findElement(By.id(nameID));
        ele.clear();
        ele.sendKeys(firstName);
    }

    public void setLastNameField(String lastName) throws AWTException, NoSuchMethodException, ScriptException, IOException {
        WebElement ele = driver.findElement(By.id(lastNameID));
        ele.clear();
        ele.sendKeys(lastName);
    }

    public void setEmailField(String email) throws AWTException, NoSuchMethodException, ScriptException, IOException {
        WebElement ele = driver.findElement(By.id(emailID));
        ele.clear();
        ele.sendKeys(email);
    }

    private void writeString(String s) throws AWTException {
        Robot robot = new Robot();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }
            robot.keyPress(Character.toUpperCase(c));
            robot.keyRelease(Character.toUpperCase(c));

            if (Character.isUpperCase(c)) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        }
    }

    public void clickAddButton()
    {
        driver.findElement(By.id(addButton)).click();
    }

    public boolean isContractExistInTable(String firstName, String lastName, String email)
    {
        List<WebElement> table = driver.findElements(By.xpath("//table[@id='" + tableName + "']/tbody/tr"));

        for (WebElement trElement : table) {
            List<WebElement> tdElements = trElement.findElements(By.xpath("td"));
            if (tdElements.get(0).getText().equalsIgnoreCase(firstName) &&
                    tdElements.get(1).getText().equalsIgnoreCase(lastName) &&
                    tdElements.get(2).getText().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public int countAllContacts() {
        int rowCount = driver.findElements(By.xpath("//table[@id='" + tableName + "']/tbody/tr")).size();
        return rowCount;
    }

    public String getNameField() {
        return driver.findElement(By.id(nameID)).getAttribute("value");
    }

    public String getLastNameField() {
        return driver.findElement(By.id(lastNameID)).getAttribute("value");
    }

    public String getEmailField() {
        return driver.findElement(By.id(emailID)).getAttribute("value");
    }

    public String getButtonState() {
        return driver.findElement(By.id(addButton)).isEnabled() ? "Enabled" : "Disabled";
    }

    public void javaScript(String id) throws IOException, ScriptException, NoSuchMethodException {

        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver)
                    .executeScript("document.getElementById("+ "\""+ id +"\"" + ").focus()");
        }
    }

    public String getPopupMessage(){

        String text = driver.switchTo().alert().getText();
        return text;
    }

    public void closeBrowser() {
        driver.quit();
    }

}

