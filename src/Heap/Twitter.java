package Heap;

import StringProblems.Codec;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Twitter {
    private Map<Integer, List<Tweet>> userTweets = new HashMap<>();
    private Map<Integer, Set<Integer>> followers;
    HashMap<Integer, Set<Integer>> users = new HashMap<>();
    private int timestamp = 0;

    public Twitter() {

    }

    public void postTweet(int userId, int tweetId) {
        userTweets.putIfAbsent(userId, new ArrayList<>());
        userTweets.get(userId).add(new Tweet(tweetId, userId, timestamp++));
    }

    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> followers = new HashSet<>(users.getOrDefault(userId, new HashSet<>()));
        followers.add(userId);
        PriorityQueue<Tweet> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.time));
        for (Integer follower : followers) {
            List<Tweet> tweets = userTweets.get(follower);
            if (tweets != null) {
                for (Tweet tweet : tweets) {
                    queue.add(tweet);
                    if (queue.size() > 10) {
                        queue.poll();
                    }
                }
            }
        }
        List<Integer> newsFeed = new ArrayList<>();
        while (!queue.isEmpty()) {
            newsFeed.add(queue.poll().id);
        }
        Collections.reverse(newsFeed);
        return newsFeed;
    }

    public void follow(int followerId, int followeeId) {
        users.putIfAbsent(followerId, new HashSet<>());
        users.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        users.putIfAbsent(followerId, new HashSet<>());
        users.get(followerId).remove(followeeId);
    }
}

class Tweet {
    int id;
    int userId;
    int time;

    public Tweet(int id, int userId, int time) {
        this.id = id;
        this.userId = userId;
        this.time = time;

    }
}

