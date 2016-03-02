package cn.edu.tju.scs.po;

import cn.edu.tju.scs.po.base.BaseMessage;

import java.util.List;

/**
 * 图文消息
 * Created by jack on 2016/2/29.
 */
public class NewsMessage extends BaseMessage{

    private int ArticleCount;
    private List<News> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<News> getArticles() {
        return Articles;
    }

    public void setArticles(List<News> articles) {
        Articles = articles;
    }
}
