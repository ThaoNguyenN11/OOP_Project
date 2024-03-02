package oop_nft_project.tweet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Tweet {
    private String name, user, content;
    private List<String> hashtags = new ArrayList<>();
    private LocalDateTime createdAt;
    private int comment, retweet, like, view;

    public Tweet() {
    }

    public Tweet(String name, String user, String time, String content, List<String> hashtags, String comment, String retweet, String like, String view) {
        this.name = name;
        this.user = user;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    
        // Chuyển từ String sang LocalDateTime
        this.createdAt = LocalDateTime.parse(time, formatter);
        
        this.content = content;
        
        this.hashtags = hashtags;
        
        if(comment.charAt(comment.length() - 1) == 'K') {
            Double qty = Double.valueOf(comment.substring(0, comment.length() - 1));
            this.comment = (int)(qty * 1000);
        }
        else this.comment = Integer.parseInt(comment);
        
        // Dữ liệu nhận về có thể < 1000(235) hoặc > 1000 (23.5K)
        if(retweet.charAt(retweet.length() - 1) == 'K') {
        Double qty = Double.valueOf(retweet.substring(0, retweet.length() - 1));
        this.retweet = (int)(qty * 1000);
        }
        else this.retweet = Integer.parseInt(retweet);

        if(like.charAt(like.length() - 1) == 'K') {
            Double qty = Double.valueOf(like.substring(0, like.length() - 1));
            this.like = (int)(qty * 1000);
        }
        else this.like = Integer.parseInt(like);

        if(view.charAt(view.length() - 1) == 'K') {
            Double qty = Double.valueOf(view.substring(0, view.length() - 1));
            this.view = (int)(qty * 1000);
        }
        else this.view = Integer.parseInt(view);
    }   

    @Override
    public String toString() {
        return "Tweet{" + "name=" + name + ", user=" + user + ", content=" + content + ", hashtags=" + hashtags + ", createdAt=" + createdAt + ", comment=" + comment + ", retweet=" + retweet + ", like=" + like + ", view=" + view + '}';
    } 
}
/*

*/
