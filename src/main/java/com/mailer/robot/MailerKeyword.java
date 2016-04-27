package com.mailer.robot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class MailerKeyword {
    //public static final String ROBOT_LIBRARY_SCOPE = "GLOBAL";
    public static WebDriver driver;

    private static final String tableName = "nameInfo";
    private static final String nameID = "#firstName";
    private static final String lastNameID = "#lastName";
    private static final String emailID = "#email";
    private static final String addButton = "#button";
    private static final String contactButton = "#contactButton";

    public void hello(String name) {
        System.out.println("Hello " + name);
    }

    public String getHello(String name) {
        return ("Hello " + name);
    }

    public void openBrowser(String url)
    {
        driver = new FirefoxDriver();
        driver.get(url);
    }

    public void clickContractButton() {
        driver.findElement(By.id(contactButton)).click();
    }

    public void setNameField(String firstName) {
        driver.findElement(By.id(nameID)).sendKeys(firstName);
    }

    public void setLastNameField(String lastName) {
        driver.findElement(By.id(lastNameID)).sendKeys(lastName);
    }

    public void setEmailField(String email) {
        driver.findElement(By.id(emailID)).sendKeys(email);
    }

    public void clickAddButton()
    {
        driver.findElement(By.id(addButton)).click();
    }

    public boolean isContractExistInTable(String firstName, String lastName, String email) {
        List<WebElement> table = driver.findElements(By.xpath("//table[@id='" + tableName + "']/tbody/tr"));

        for (WebElement trElement : table) {
            List<WebElement> tdElements = trElement.findElements(By.xpath("td"));
            if (tdElements.get(0).getAttribute("value").equalsIgnoreCase(firstName) &&
                    tdElements.get(1).getAttribute("value").equalsIgnoreCase(lastName) &&
                    tdElements.get(2).getAttribute("value").equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public int countAllContacts() {
        int headerRow = 1;
        int rowCount = driver.findElements(By.xpath("//table[@id='" + tableName + "']/tbody/tr")).size();
        return rowCount - headerRow;
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

    public String getButtonState(){
        return driver.findElement(By.id(addButton)).isEnabled() ? "Enabled" : "Disabled";
    }

    public void closeBrowser() {
        driver.quit();
    }
}

