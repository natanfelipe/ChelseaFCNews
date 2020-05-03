package com.br.natanfelipe.chelseafcnews

import com.br.natanfelipe.chelseafcnews.model.Articles
import com.br.natanfelipe.chelseafcnews.model.Sources

class ArticleResponseErrorMock {
    val source = Sources(null, "Firstpost.com")
    val author = ""
    val title = "Premier League: Former Chelsea, England goalkeeper Peter Bonetti passes away at age of 78"
    val description = ""
    val url = "https://www.firstpost.com/sports/premier-league-former-chelsea-england-goalkeeper-peter-bonetti-passes-away-at-age-of-78-8252851.html"
    val urlToImage = "https://images.firstpost.com/wp-content/uploads/2018/11/Chelsea-Football-Club-social-Reuters.jpg"
    val publishedAt = "2020-04-12T15:14:57Z"
    val content = "Former Chelsea and England goalkeeper Peter Bonetti has died at the age of 78 after a long battle with illness, the Premier League club announced on Sunday.\\r\\nNicknamed The Cat for his quick reflexes in goal, Bonetti made 729 appearances for the London club in… [+1621 chars]"
    val article = Articles(source, author, title, description, url, urlToImage, publishedAt, content)
}