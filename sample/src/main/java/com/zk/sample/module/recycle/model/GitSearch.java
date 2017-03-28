package com.zk.sample.module.recycle.model;

import java.util.List;

/**
 * ================================================
 * Created by zhaokai on 2017/3/27.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class GitSearch {

    /**
     * total_count : 1
     * incomplete_results : false
     * items : [{"login":"zhaokai1033","id":14001437,"avatar_url":"https://avatars1.githubusercontent.com/u/14001437?v=3","gravatar_id":"","url":"https://api.github.com/users/zhaokai1033","html_url":"https://github.com/zhaokai1033","followers_url":"https://api.github.com/users/zhaokai1033/followers","following_url":"https://api.github.com/users/zhaokai1033/following{/other_user}","gists_url":"https://api.github.com/users/zhaokai1033/gists{/gist_id}","starred_url":"https://api.github.com/users/zhaokai1033/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/zhaokai1033/subscriptions","organizations_url":"https://api.github.com/users/zhaokai1033/orgs","repos_url":"https://api.github.com/users/zhaokai1033/repos","events_url":"https://api.github.com/users/zhaokai1033/events{/privacy}","received_events_url":"https://api.github.com/users/zhaokai1033/received_events","type":"User","site_admin":false,"score":20.975086}]
     */

    private int total_count;
    private boolean incomplete_results;
    private List<GitUser> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<GitUser> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "GitSearch{" +
                "items=" + items +
                '}';
    }

    public void setItems(List<GitUser> items) {
        this.items = items;

    }
}
