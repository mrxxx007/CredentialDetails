package CredentialDetails.data;

import java.io.Serializable;

public class TableContentVo implements Serializable {
    static final long serialVersionUID = 1L;

    public static final transient String[] TABLE_COLUMNS = {"Title", "URL", "Login", "Password", "Comments"};

    private String title;
    private String url;
    private String login;
    private String password;
    private String comment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
