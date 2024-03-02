package oop_nft_project.tweet;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class GetTweets2 {
    public static void main(String[] args) throws InterruptedException {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-private");
        
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe"); //Config môi trường để web chạy
        //Ở đây là xài firefox lên muốn lấy bắt buộc mọi người phải có browser này hạn chế dùng google do giữa driver và browser phiên bản không tương thích
        WebDriver driver = new FirefoxDriver(options); //Đưa config vào cho web ở đây là không hiện lên hay là Minimize        
        
        driver.get("https://twitter.com/i/flow/login");  //Đưa url vào web để dẫn đến trang ở đây truyền trực tiếp nhưng thường hãy tách biến ra và config cứng lại
        
        Thread.sleep(5000); // Đợi 5s để web ổn định có thể lấy dễ hơn
        
        WebElement usernameInput = driver.findElement(  // Tìm đến phần tử có thẻ input và có 2 thuộc tính autocomplete và name
            By.cssSelector("input[autocomplete='username'][name='text']"));
        usernameInput.sendKeys("nvquyet2904@gmail.com"); //Set cho phần tử đó có giá trị là tài khoản đăng nhập
        usernameInput.sendKeys(Keys.ENTER);     //Ấn Enter để chuyển trang tùy vào trang có thể là ấn Enter có thể là button ấn nhưng 90% là enter
        
        Thread.sleep(5000);
        
        // Nếu báo đăng nhập bất thường
        try {
            WebElement authen = driver.findElement(By.cssSelector("input[data-testid='ocfEnterTextTextInput'][value='']"));
            authen.sendKeys("daika_quyet");
            authen.sendKeys(Keys.ENTER);

            Thread.sleep(5000);
        }
        catch(Exception e) {
            System.out.println("Không bị đăng nhập bất thường");
        }

        WebElement password = driver.findElement(By.cssSelector("input[autocomplete='current-password'][name='password']"));
        password.sendKeys("quyetdaika2803");
        password.sendKeys(Keys.ENTER);
        
        Thread.sleep(5000); // Đợi 5s để đăng nhập
        
        driver.navigate().to("https://twitter.com/search?f=live&q=(%23NFT%20OR%20%23NFTCommunity%20OR%20%23NFTdrop%20OR%20%23OpenSeaNFT)%20min_replies%3A1%20min_faves%3A500%20min_retweets%3A0%20lang%3Aen%20-filter%3Areplies&src=typed_query");
        Thread.sleep(5000); //Đợi 5s để navigate ổn định
        
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        long pageHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight"
            + ", document.body.offsetHeight, document.documentElement.clientHeight,"
            + " document.documentElement.scrollHeight,"
            + " document.documentElement.offsetHeight )");
        int steps = 2;
        long delayBetweenStepsInMillis = 1000;
        long scrollStep = pageHeight / steps;
        for (int i = 0; i < steps; i++) {
            long yOffset = i * scrollStep;
            jsExecutor.executeScript("window.scrollTo(0, " + yOffset + ")");
            Thread.sleep(delayBetweenStepsInMillis);
        }
        
        String html = (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
        System.out.println(html);
        driver.quit();
    }
}
/*

*/
