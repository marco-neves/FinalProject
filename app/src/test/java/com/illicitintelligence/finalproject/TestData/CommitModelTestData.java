package com.illicitintelligence.finalproject.TestData;

import com.illicitintelligence.finalproject.model.Author;
import com.illicitintelligence.finalproject.model.Committer;

public class CommitModelTestData {
    String commitMessage = "initial commit";
    String commitUrl = "github.com/commits/1";
    Committer committer = new Committer();
    Author author = new Author();

    public Author getAuthor() {
        author.setName("Monique Berry");
        author.setEmail("moniqueberry88@gmail.com");
        author.setDate("01/30/20");
        return author;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public String getCommitUrl() {
        return commitUrl;
    }

    public Committer getCommitter() {
        committer.setName("Joel-Jacob");
        committer.setEmail("joeljacob@gmail.com");
        committer.setDate("02/02/20");
        return committer;
    }
}
