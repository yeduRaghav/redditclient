# A simple Reddit Client

### Features :
---------
**1. MainScreen :**

Shows list of posts from https://www.reddit.com/r/Kotlin/
  The list contains title, thumbnail and username.
  
  A loading animation is seen when the data is being fetched.
  If loading fails, an error view is shown with a retry button



**2. DetailScreen :**

Click a post from the main screen to load the detail page.

The collapsable toolbar shows the title of the post.

If a thumbnail exists the image is shown, similarly if article body exists, it is shown below the image.


### Testing :
---------
Since r/Kotlin usually might not have thumbnails or rich article body, this might not be the best way to test the features.

However, https://www.reddit.com/r/PS4/ is a great way to test

To load a different subreddit :

Change RedditApi.SUB_REDDIT_KOTLIN = "Kotlin" TO RedditApi.SUB_REDDIT_KOTLIN = "PS4" or anyother subreddit of your choice.

Note : r/Pics won't help, since the Api response for those posts do not return a url for thumbanail property. 





### In defence of the dependencies :
-------------------

**Markwon :** Reddit returns the article as mardown and Markwon does a great job of rendering Markdown in TextViews.

**Material:1.2.0-alpha05 :** Due to the fact that the title of the post needs to be displayed on the Navigation bar, Collapsable Toolbar made sense.
However only way to show multiple lines of text in Collapsable Toolbar is to :
 1. Implement lot of custom code from stackoverflow, that I cannot defend.
 2. Use a custom library just for the collapsable Toolbar.
 3. Update material version to 1.2.0-alpha05, that gives what is necessary with one line <code> app:maxLines="" </code> 

According to google, alpha version is stable, just prone to api changes.
Therefore, option 3 seemed sensible for the problem at hand. 
