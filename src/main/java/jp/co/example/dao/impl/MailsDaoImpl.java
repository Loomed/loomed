package jp.co.example.dao.impl;

import org.springframework.stereotype.Repository;

import jp.co.example.dao.MailsDao;
import jp.co.example.entity.Mails;
import jp.co.example.entity.Users;

@Repository
public class MailsDaoImpl implements MailsDao {
	@Override
	public Mails findByIdAndTitleAndContents(Integer mailID, String mailTitle, String mailContents) {
		return null;


	}

	@Override
	public int getNewMails(Users user) {

		return 0;
	}
}

