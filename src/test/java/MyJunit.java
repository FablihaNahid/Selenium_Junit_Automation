import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyJunit {

    WebDriver driver;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver(); //browser launch
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @DisplayName("Check Website Title") //test title
    @Test
    public void visitWebsite() {
        driver.get("https://demoqa.com/"); //test steps
        String titleActualResult = driver.getTitle(); //actual result
        String titleExpectedResult = "DEMOQA"; //expected result
        Assertions.assertEquals(titleExpectedResult, titleActualResult); //test status
    }

    @Test
    public void submitForm() {
        driver.get("https://demoqa.com/text-box");
        driver.findElement(By.id("userName")).sendKeys("Test User");
        driver.findElement(By.cssSelector("[type=email]")).sendKeys("testuser@test.com");
        List<WebElement> elements = driver.findElements(By.className("form-control"));
        elements.get(2).sendKeys("Gulshan");
        elements.get(3).sendKeys("Dhaka");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        //List<WebElement> btnElement= driver.findElements(By.tagName("button"));
        //btnElement.get(1).click();
        driver.findElements(By.tagName("button")).get(1).click();

        List<WebElement> txtElements = driver.findElements(By.tagName("p"));
        String usernameActual = txtElements.get(0).getText();
        String emailActual = txtElements.get(1).getText();
        String currentAddrActual = txtElements.get(2).getText();
        String permaAddrActual = txtElements.get(3).getText();

        Assertions.assertTrue(usernameActual.contains("Test User"));
        //Assertions.assertEquals(usernameActual,"Name:Test User");
        Assertions.assertTrue(emailActual.contains("testuser@test.com"));
        Assertions.assertTrue(currentAddrActual.contains("Gulshan"));
        Assertions.assertTrue(permaAddrActual.contains("Dhaka"));
    }

    @Test
    public void doubleClick(){
        driver.get("https://demoqa.com/buttons");
        Actions actions= new Actions(driver);
        List<WebElement> buttons=driver.findElements(By.tagName("button"));
        actions.doubleClick(buttons.get(1)).perform();
        actions.contextClick(buttons.get(2)).perform();
        actions.click(buttons.get(3 )).perform();
        //actions.contextClick(driver.findElement(By.id("rightClickbtn"))).perform(); //rightclick er jnne contextclick
    }

    @Test
    public void handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        //driver.findElement(By.id("alertButton")).click();
        //Thread.sleep(3000); //alert ashar por 3sec wait korbe
        //driver.switchTo().alert().accept();

        //driver.findElement(By.id("timerAlertButton")).click();
        //Thread.sleep(6000); //alert ashar por 3sec wait korbe
        //driver.switchTo().alert().accept(); // alert ashar por OK button e click korar jnne

        driver.findElement(By.id("timerAlertButton")).click();
        Thread.sleep(6000); //alert ashar por 3sec wait korbe
        driver.switchTo().alert().accept(); // alert ashar por OK button e click korar jnne
        //driver.switchTo().alert().dismiss(); // cancel korte chaile
    }

    @Test
    public void setDate(){
        driver.get("https://demoqa.com/date-picker");
        WebElement txtDate= driver.findElement(By.id("datePickerMonthYearInput"));
        //Actions actions=new Actions(driver);
        //actions.click(txtDate).sendKeys(Keys.CONTROL+"a").sendKeys(Keys.BACK_SPACE).sendKeys("07/01/2025").sendKeys(Keys.ENTER).perform();

        txtDate.click();
        txtDate.sendKeys(Keys.CONTROL+"a");
        txtDate.sendKeys(Keys.BACK_SPACE);
        txtDate.sendKeys("07/01/2025");
        txtDate.sendKeys(Keys.ENTER);
    }

    @Test
    public void selectDropdown() throws InterruptedException {
        driver.get("https://demoqa.com/select-menu");
        //Select select= new Select(driver.findElement(By.id("oldSelectMenu")));
        //select.selectByVisibleText("Green");
        List<WebElement> dropdown=driver.findElements(By.className("css-yk16xz-control"));
        dropdown.get(1).click();
        Thread.sleep(1000);
        Actions actions=new Actions(driver);
        for(int i=0; i<2;i++){
            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        }

        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }

    @Test
    public void mouseHover(){
        driver.get("https://www.aiub.edu/");
        Actions actions= new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//a[contains(text(),\"About\")]"))).perform();

    }

    @Test
    public void handleMultipleTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(3000);
        ArrayList<String> arrayList= new ArrayList<>(driver.getWindowHandles());
        System.out.println(arrayList);
        driver.switchTo().window(arrayList.get(1));
        String heading=driver.findElement(By.id("sampleHeading")).getText();
        System.out.println(heading);
        driver.close();
        driver.switchTo().window(arrayList.get(0));
    }

    @Test
    public void handlemultipleWindow(){
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("windowButton")).click();
        String parentWindow=driver.getWindowHandle();
        Set<String> allWindow=driver.getWindowHandles();

        Iterator<String> iterator=allWindow.iterator();
        while (iterator.hasNext()){
            String childWindow=iterator.next();
            if(!parentWindow.equalsIgnoreCase(childWindow)){
                driver.switchTo().window(childWindow);
                String text=driver.findElement(By.id("sampleHeading")).getText();
                System.out.println(text);
            }
        }
        driver.close(); //child window k close kore dilam
        driver.switchTo().window(parentWindow); //trpr abr main window te back korbo
    }

    @Test
    public void scrapData(){
        driver.get("https://demoqa.com/webtables");
        WebElement table= driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows=table.findElements(By.cssSelector("[role=rowgroup]"));
        for(WebElement row: allRows){
            List <WebElement> cells= row.findElements(By.cssSelector("[role=gridcell]"));
            for (WebElement cell: cells){
                System.out.println(cell.getText());
            }
        }
    }

    @Test
    public void handleIframe(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame1");
        String text= driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();
    }

    @Test
    public void uploadFile(){
        driver.get("https://demoqa.com/upload-download");
        //driver.findElement(By.id("uploadFile")).sendKeys("D://Thankyou.png"); //eita amr pc te so access kora jabe na
        driver.findElement(By.id("uploadFile")).sendKeys(System.getProperty("user.dir")+"/src/test/resources/Thankyou.png");

    }

    @AfterAll
    public void teardown() {
        driver.quit();
    }
}
