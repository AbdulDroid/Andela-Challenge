package com.andelachallenge.developerprofiles;

import java.util.List;

/**
 * Created by Abdulkarim on 3/10/2017.
 */

public class Developer {
    public class Items{
        public String login;
        public int id;
        public String avatar_id;
        public String gravatar_id;
        public String url;
        public String html_url;
        public String followers_url;
        public String following_url;
        public String gists_url;
        public String starred_url;
        public String subscriptions_url;
        public String organizations_url;
        public String repos_url;
        public String events_url;
        public String received_events_url;
        public String type;
        public boolean site_admin;
        public double score;
    }

    public class Developer{
        public int total_count;
        public boolean incomplete_results;
        public List<Items>items;
    }
}
