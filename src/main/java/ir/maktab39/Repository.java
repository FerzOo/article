package ir.maktab39;

import ir.maktab39.entities.Article;
import ir.maktab39.entities.Category;
import ir.maktab39.entities.User;
import ir.maktab39.exceptions.NotFoundException;
import ir.maktab39.exceptions.UniqueConstraintException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {

    private static volatile Repository instance;

    public static Repository getInstance() {
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    instance = new Repository();
                }
            }
        }
        return instance;
    }

    private Connection connection = null;

    private Repository() {
    }

    public void startConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/test?"
                        + "user=root&password=mysql123"
        );
    }

    public void insertArticle(Article article) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into article values(null,?,?,?,?,?,?,?)")) {
            statement.setString(1, article.getTitle());
            statement.setString(2, article.getBrief());
            statement.setString(3, article.getContent());
            Date sqlDate = new Date(article.getCreateDate()
                    .getTime());
            statement.setDate(4, sqlDate);
            statement.setBoolean(5, article.isPublished());
            statement.setLong(6, article.getAuthor().getId());
            statement.setLong(7, article.getCategory().getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating article failed, no rows affected.");
            }
        }
    }

    public List<Article> getUserArticles(User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from article join category on" +
                        " article.categoryId = category.id and article.userId=?")) {
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            List<Article> articleList = new ArrayList<>();
            while (resultSet.next()) {
                Article article = new Article();
                article.setAuthor(null);
                article.setBrief(resultSet.getString("brief"));
                Category category = new Category();
                category.setTitle(resultSet.getString(10));
                article.setCategory(category);
                article.setContent(resultSet.getString("content"));
                article.setCreateDate(resultSet.getDate("createDate"));
                article.setId(resultSet.getLong(1));
                article.setPublished(resultSet.getBoolean("isPublished"));
                article.setTitle(resultSet.getString(2));
                articleList.add(article);
            }
            return articleList;
        }
    }

    public List<Article> getArticles() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from article join category on" +
                        " article.categoryId = category.id" +
                        " join user on article.userId = user.id" +
                        " where article.isPublished=true")) {
            ResultSet resultSet = statement.executeQuery();
            List<Article> articleList = new ArrayList<>();
            while (resultSet.next()) {
                Article article = new Article();
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                article.setAuthor(user);
                article.setBrief(resultSet.getString("brief"));
                Category category = new Category();
                category.setTitle(resultSet.getString(10));
                article.setCategory(category);
                article.setContent(resultSet.getString("content"));
                article.setCreateDate(resultSet.getDate("createDate"));
                article.setId(resultSet.getLong(1));
                article.setPublished(resultSet.getBoolean("isPublished"));
                article.setTitle(resultSet.getString(2));
                articleList.add(article);
            }
            return articleList;
        }
    }

    public User getUser(String username, String password) throws SQLException, NotFoundException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from user where username=? and password=?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                throw new NotFoundException("user");
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("username"));
            user.setNationalCode(resultSet.getString("nationalCode"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
    }

    public void registerUser(User user) throws SQLException, UniqueConstraintException {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into user values(null,?,?,?,?)")) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getNationalCode());
            Date sqlDate = new Date(user.getBirthday()
                    .getTime());
            statement.setDate(3, sqlDate);
            statement.setString(4, user.getNationalCode());
            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new UniqueConstraintException(e);
        }
    }

    public Map<Long, Category> getCategories() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from category")) {
            ResultSet resultSet = statement.executeQuery();
            Map<Long, Category> map = new HashMap<>();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setDescription(
                        resultSet.getString("description"));
                category.setTitle(resultSet.getString("title"));
                map.put(category.getId(), category);
            }
            return map;
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            ErrorHandler.showMessage(e);
        }
    }

    public void changePassword(String password) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update user set user.password=?")) {
            statement.setString(1, password);
            statement.executeUpdate();
        }
    }

    public void insertCategory(Category category) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into category values(null,?,?)")) {
            statement.setString(1, category.getTitle());
            statement.setString(2, category.getDescription());
            statement.executeUpdate();
        }
    }

    public void updateArticle(Article article) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update article set title=? AND " +
                        "brief=? AND content=? AND isPublished=?" +
                        " WHERE article.id=?")) {
            statement.setString(1, article.getTitle());
            statement.setString(2, article.getBrief());
            statement.setString(3, article.getContent());
            statement.setBoolean(4, article.isPublished());
            statement.setLong(5, article.getId());
            statement.executeUpdate();
        }
    }
}
