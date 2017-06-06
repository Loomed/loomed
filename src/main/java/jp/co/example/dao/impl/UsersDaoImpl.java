package jp.co.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import enums.LogEnum;
import jp.co.example.dao.UsersDao;
import jp.co.example.entity.Users;
import lombok.extern.slf4j.Slf4j;
import util.Util;

@Slf4j
@Repository
public class UsersDaoImpl implements UsersDao {
	private static final String SQL_SELECT_ID_AND_PASS = "SELECT * FROM users WHERE user_id = ? AND password = ?";
	private static final String SQL_SELECT_ID = "SELECT * FROM users WHERE user_id = ?";
	private static final String UPDATE ="UPDATE users SET password = ?, user_name = ?, company_id = ?, authority = ? WHERE user_id = ?";
	private static final String SQL_MEMBER_SELECT_COMP ="SELECT * FROM users WHERE company_id = ?";
	private static final String SQL_MEMBER_SELECT_ROOM ="SELECT * FROM maps WHERE training_id = ?";

	@Autowired
 	private JdbcTemplate jdbcTemplate;

	@Override
	public Users findByIdAndPass(Integer userId, String password) {
		Users users = null;
		try {
			users = jdbcTemplate.queryForObject(SQL_SELECT_ID_AND_PASS, new BeanPropertyRowMapper<Users>(Users.class),
					userId, password);
		} catch (DataAccessException e) {
			users = null;
		}

		return users;
	}

	@Override
	public int update(Integer userId, String password, String userName, Integer companyId, Integer authority) {
		return jdbcTemplate.update(UPDATE, password, userName, companyId, authority, userId);
	}

	@Override
	public Users findById(Integer userId) {
		// TODO 自動生成されたメソッド・スタブ
		Users users = null;
		try {
			users = jdbcTemplate.queryForObject(SQL_SELECT_ID, new BeanPropertyRowMapper<Users>(Users.class),
					userId);
		} catch (DataAccessException e) {
			users = null;
		}

		return users;
	}

	@Override
	public List<Users> FindCompMember(int comId){
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());
		List<Users> member = null;
		try {
			member = jdbcTemplate.query(SQL_MEMBER_SELECT_COMP, new BeanPropertyRowMapper<Users>(Users.class),
					comId);
		} catch (DataAccessException e) {
			member = null;
		}
		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return member;
	}

	@Override
	public List<Users> FindRoomMember(int roomId){
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());
		List<Users> member = null;
		try {
			member = jdbcTemplate.query(SQL_MEMBER_SELECT_ROOM, new BeanPropertyRowMapper<Users>(Users.class),
					roomId);
		} catch (DataAccessException e) {
			member = null;
		}
		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return member;
	}

}
