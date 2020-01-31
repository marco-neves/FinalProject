package com.illicitintelligence.finalproject;

import com.illicitintelligence.finalproject.TestData.AuthorModelData;
import com.illicitintelligence.finalproject.TestData.CommitModelTestData;
import com.illicitintelligence.finalproject.TestData.CommitterModelTestData;
import com.illicitintelligence.finalproject.model.Author;
import com.illicitintelligence.finalproject.model.Commit;
import com.illicitintelligence.finalproject.model.Committer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GitHubUnitTest {

    private Author author;

    private Committer committer;

    private Commit commit;


    public CommitModelTestData commitModelTestData(){
        return new CommitModelTestData();
    }
    public AuthorModelData authorModelTestObject() {
        return new AuthorModelData();
    }

    public CommitterModelTestData committerModelTestObject() {
        return new CommitterModelTestData();
    }


    public boolean validateAuthor() {
        author = new Author();
        if (author != null) {
            return true;
        }
        return false;
    }

    public boolean validateCommitter() {
        committer = new Committer();

        if (committer != null) {
            return true;
        }
        return false;
    }

    public boolean validateCommit() {
        commit = new Commit();
        if (commit != null) {
            return true;
        }
        return false;
    }

    public Author authorFields() {
        author.setName("Monique Berry");
        author.setEmail("moniqueberry88@gmail.com");
        author.setDate("01/30/20");
        return author;
    }


    public Committer committerFieldMethods() {
        committer.setName("Joel-Jacob");
        committer.setEmail("joeljacob@gmail.com");
        committer.setDate("02/02/20");
        return committer;
    }

    public Commit commitFieldsTest() {
        Author author = new Author();
        Committer committer = new Committer();
        commit.setAuthor(author);
        commit.setCommitter(committer);
        commit.setMessage("initial commit");
        commit.setUrl("github.com/commits/1");
        return commit;
    }


    @Test
    public void AuthorTest() {
        Assert.assertTrue(validateAuthor());
        Assert.assertEquals(authorFields().getName(), authorModelTestObject().getAuthorName());
        Assert.assertEquals(authorFields().getEmail(), authorModelTestObject().getAuthorEmail());
        Assert.assertEquals(authorFields().getDate(), authorModelTestObject().getAuthorDate());

    }

    @Test
    public void committerTest() {
        Assert.assertTrue(validateCommitter());
        Assert.assertEquals(committerFieldMethods().getName(), committerModelTestObject().getCommiterName());
        Assert.assertEquals(committerFieldMethods().getEmail(), committerModelTestObject().getCommiterEmail());
        Assert.assertEquals(committerFieldMethods().getDate(), committerModelTestObject().getCommitDate());
    }

    @Test
    public void commitTest() {
        Assert.assertTrue(validateCommit());
        Assert.assertEquals(commitFieldsTest().getMessage(), commitModelTestData().getCommitMessage());
        Assert.assertEquals(commitFieldsTest().getUrl(), commitModelTestData().getCommitUrl());

    }
}
