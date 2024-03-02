package oop_nft_project.tweet;
import com.google.gson.Gson;
import java.io.FileWriter;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.util.ArrayList;
import java.util.List;

public class GetTweets {
    public static void main(String[] args) throws Exception {
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
        
        // Tìm kiếm các tweet chứa hashtag như #NFT OR #NFTCommunity OR #NFTdrop OR #OpenSeaNFT
        // trong năm 2023
        driver.navigate().to("https://twitter.com/search?q=(%23NFT%20OR%20%23NFTCommunity%20OR%20%23NFTdrop%20OR%20%23OpenSeaNFT)%20min_replies%3A1%20min_faves%3A200%20min_retweets%3A1%20lang%3Aen%20-filter%3Areplies&src=typed_query&f=live");
        Thread.sleep(5000); //Đợi 5s để navigate ổn định
        
        List<Tweet> tweets = new ArrayList<>();
        
        int limit = 20;
        int current = 0;
        while(current < limit) {
            // Tìm các phần tử tweet hiện tại  
            
            List<WebElement> currentTweets = driver.findElements(By.cssSelector("article[role=article]"));
            
            for (WebElement tweet : currentTweets) {
                // Lấy nội dung tweet
                
                // Lấy thời gian
                // Kiểm tra có phải quảng cáo không
                String time = "";
                try {
                    WebElement timeElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[1]/div/div[1]/div/div/div[2]/div/div[3]/a/time"));
                    time = timeElement.getAttribute("datetime");
                    System.out.println("time: " + time);
                }
                catch(NoSuchElementException e) {
                    System.out.println("Đây là quảng cáo nên không có thời gian");
                    continue;
                }
                
                // Kiểm tra nếu bài viết quá dài, sẽ có nút Show more
                try {
                    WebElement showmoreElement = tweet.findElement(By.cssSelector("data-testid[tweet-text-show-more-link]"));
                    continue; // Bỏ qua những bài như vậy
                }
                catch(NoSuchElementException e) {
                    System.out.println("Không có nút Show More");
                }
                
                // Lấy thông tin người đăng
                WebElement nameElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[1]/div/div[1]/div/div/div[1]/div/a/div/div[1]/span/span"));
                String name = nameElement.getText(); 
                System.out.println("name: " + name);

                WebElement userElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[1]/div/div[1]/div/div/div[2]/div/div[1]/a/div/span"));
                String user = userElement.getText();
                System.out.println("user: " + user);
                
                // Lấy nội dung toàn bộ bài viết
                WebElement contentElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[2]"));
                String content = contentElement.getText();
                System.out.println("content: " + content);
                
                // Lấy hashtags
                List<WebElement> hashtagElement = contentElement.findElements(By.cssSelector("a[href^='/hashtag/']"));
                List<String> hashtags = new ArrayList<>();
                for(WebElement x : hashtagElement){
                    hashtags.add(x.getText());
                }
                System.out.print("Hashtags: ");
                for(String x : hashtags) {
                    System.out.print(x + ", ");
                }
                System.out.println("");
                
                
                // Lấy số lượt comment, retweet, like, view
                // Cách lấy bài viết có media sẽ khác bài không có media
                String comment = "", retweet = "", like = "", view = "";
                try {
                    WebElement commentElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[4]/div/div/div[1]/div/div/div[2]/span/span/span"));
                    comment = commentElement.getText();
                    System.out.println("comment: " + comment);

                    WebElement retweetElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[4]/div/div/div[2]/div/div/div[2]/span/span/span"));
                    retweet  = retweetElement.getText();
                    System.out.println("retweet: " + retweet);

                    WebElement likeElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[4]/div/div/div[3]/div/div/div[2]/span/span/span"));
                    like  = likeElement.getText();
                    System.out.println("like: " + like);

                    WebElement viewElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[4]/div/div/div[4]/a/div/div[2]/span/span/span"));
                    view  = viewElement.getText();
                    System.out.println("view: " + view);
                }
                catch(NoSuchElementException e){
                    WebElement commentElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[3]/div/div/div[1]/div/div/div[2]/span/span/span"));
                    comment = commentElement.getText();
                    System.out.println("comment: " + comment);

                    WebElement retweetElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[3]/div/div/div[2]/div/div/div[2]/span/span/span"));
                    retweet  = retweetElement.getText();
                    System.out.println("retweet: " + retweet);

                    WebElement likeElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[3]/div/div/div[3]/div/div/div[2]/span/span/span"));
                    like  = likeElement.getText();
                    System.out.println("like: " + like);

                    WebElement viewElement = tweet.findElement(By.xpath("div/div/div[2]/div[2]/div[3]/div/div/div[4]/a/div/div[2]/span/span/span"));
                    view  = viewElement.getText();
                    System.out.println("view: " + view);
                }

                System.out.println("------------------------------------------");
    //          Thêm vào danh sách để xử lý, lưu trữ
                tweets.add(new Tweet(name, user, time, content, hashtags, comment, retweet, like, view));
                current++;
            }
            
            // Nếu đủ số lượng rồi thì thoát 
            if(current >= limit) break;
                
            // Scroll xuống để tải thêm
            JavascriptExecutor js = (JavascriptExecutor) driver; 
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            // Đợi loading
            Thread.sleep(3000);  
        }
        driver.quit(); //Tắt trình duyệt cho đỡ tốn tài nguyên
        
        System.out.println("Số tweet đã lấy: " + current);
        // Lưu vào file json
        Gson gson = new Gson();
        String json = gson.toJson(tweets);
        FileWriter writer = new FileWriter("./json/tweets.json"); 
        writer.write(json);
        writer.close();
    }
}
