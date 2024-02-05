package gr.hua.dit.ds.group60.dao;

import gr.hua.dit.ds.group60.entity.User;

public interface UserDAO {
    public User getUserByEmail(String email);
}
