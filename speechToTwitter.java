package twitterPost;  
import twitter4j.Status; 
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.TwitterException;

//must download all of the imports to work
public class PostToTwitter {
	
	public static String catchtwitter(String hashtag) throws InterruptedException, TwitterException {
	// you must have your own twitter app and obtain the consumer key/secret as well as the access token/secret
    Twitter twitter = new TwitterFactory().getInstance();
    // Twitter Consumer key & Consumer Secret
    twitter.setOAuthConsumer("Consumer key", "Consumer secret");
    // Twitter Access token & Access token Secret
    twitter.setOAuthAccessToken(new AccessToken("access token",
    		"access secret"));

      try {
    	  int i = 1;
    	  Query query = new Query(hashtag);
    	  QueryResult search = twitter.search(query);
    	    for (Status s : search.getTweets().subList(0, 3)) {
    	        System.out.println(i + " : "+ s.getUser().getName()+ "------" +s.getText() + "\n");
    	        i++;
    	    }

      }

      catch (TwitterException te) {
        System.out.println("Couldn't connect: " + te);
      } 
      JSONObject jsonObject = new JSONObject(twitter.getRateLimitStatus().get("/followers/ids"));
      try {
          int remaining = Integer.parseInt(jsonObject.get("remaining").toString());
          if (remaining == 0)
          {
              int secondsUntilReset = Integer.parseInt(jsonObject.get("secondsUntilReset").toString());
              System.out.println("Waiting for seconds: ".concat(jsonObject.get("secondsUntilReset").toString()));
              Thread.sleep((( secondsUntilReset ) * 1000));
          }
      } catch (JSONException e) {
          e.printStackTrace();
      }
	return hashtag; 
      
    }
	public static void main(String args[]) throws InterruptedException, TwitterException {
		System.out.println(catchtwitter("#ninja"));
	}
    }
  
