package jp.co.example.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import enums.*;
import lombok.extern.slf4j.*;
import util.*;

@Slf4j
@Controller
public class UserconfigController {
//実行時エラー出るので注意

	// //登録
	@RequestMapping(value = "/userconfig")
	public String getResistration() {
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());
		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return JspPage.USERCONFIG.getPageName();
	}

<<<<<<< HEAD
	// 氏名←コメント解除するとエラー出る
=======
//	// 氏名
>>>>>>> 9bbf5f2acb340bb9e0da93c2c022913be0f89620
//	@RequestMapping(value = "/userinfo")
//	public String getName() {
//		log.info(Util.getMethodName() + LogEnum.START.getLogValue());
//		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
//		return JspPage.USERINFO.getPageName();
//	}

	// //変更←コメント解除するとエラー出る
//	@RequestMapping(value = "/userchange")
//	public String getuserChange() {
//		log.info(Util.getMethodName() + LogEnum.START.getLogValue());
//		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
//		return JspPage.USERCHANGE.getPageName();
//	}

	// //削除
	@RequestMapping(value = "/userdelete")
	public String getDelete() {
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());
		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return JspPage.USERCONFIG.getPageName();
	}

}
