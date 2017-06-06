package jp.co.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import enums.JspPage;
import enums.LogEnum;
import lombok.extern.slf4j.Slf4j;
import util.Util;

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


	// 氏名←コメント解除するとエラー出る
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
