package jp.co.example.myTest;

import jp.co.example.entity.*;


public interface MyUsersDao {
	Users loginUser(int id, String pass);
	Users getUser(int id);
}
