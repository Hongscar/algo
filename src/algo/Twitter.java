package algo;

import java.util.LinkedList;
import java.util.List;
import java.util.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 9:06 2019/11/28
 */

class Twitter {
    class Tweet_order {
        private int userId;
        private int tweetId;
        Tweet_order(int userId, int tweetId) {
            this.userId = userId;
            this.tweetId = tweetId;
        }
        public int getUserId() {
            return userId;
        }
        public int getTweetId() {
            return tweetId;
        }
    }
    Map<Integer, List<Integer>> follows;
    Map<Integer, List<Integer>> tweets;
    LinkedList<Tweet_order> order;      // record all the tweets order, actually we should build a heap to save these.

    /** Initialize your data structure here. */
    public Twitter() {
        follows = new HashMap<>();
        tweets = new HashMap<>();
        order = new LinkedList<>();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        List<Integer> tweet = tweets.getOrDefault(userId, new ArrayList<>());
        tweet.add(tweetId);
        tweets.put(userId, new ArrayList<>(tweet));
        order.addFirst(new Tweet_order(userId, tweetId));
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();
        LinkedList<Tweet_order> tempOrder = new LinkedList<>(order);
        List<Integer> follow = follows.get(userId);
        int size = tempOrder.size();
        int i = 0;
        while (res.size() < 10 && tempOrder.size() != 0) {
            Tweet_order tw = tempOrder.removeFirst();
            if ((follow != null && follow.contains(tw.getUserId())) || userId == tw.getUserId())
                res.add(tw.getTweetId());
            i++;
        }
        return res;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        List<Integer> follow = follows.getOrDefault(followerId, new ArrayList<>());
        follow.add(followeeId);
        follows.put(followerId, new ArrayList<>(follow));
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        List<Integer> follow = follows.getOrDefault(followerId, new ArrayList<>());
        follow.remove((Integer)followeeId);
        follows.put(followerId, new ArrayList<>(follow));
    }

    public static void main(String[] args) {
        Twitter twitter = new Twitter();

// 用户1发送了一条新推文 (用户id = 1, 推文id = 5).
        twitter.postTweet(1, 5);

// 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
        System.out.println(twitter.getNewsFeed(1));

// 用户1关注了用户2.
        twitter.follow(1, 2);

// 用户2发送了一个新推文 (推文id = 6).
        twitter.postTweet(2, 6);

// 用户1的获取推文应当返回一个列表，其中包含两个推文，id分别为 -> [6, 5].
// 推文id6应当在推文id5之前，因为它是在5之后发送的.
        System.out.println(twitter.getNewsFeed(1));

// 用户1取消关注了用户2.
        twitter.unfollow(1, 2);

// 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
// 因为用户1已经不再关注用户2.
        System.out.println(twitter.getNewsFeed(1));
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */