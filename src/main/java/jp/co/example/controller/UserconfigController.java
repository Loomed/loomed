package jp.co.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import enums.JspPage;
import enums.LogEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import util.Util;

@Getter
@Slf4j
public class UserconfigController {

	//登録
	@RequestMapping(value = "/resister")
	public String getResistration() {
	log.info(Util.getMethodName() + LogEnum.START.getLogValue());
	log.info(Util.getMethodName() + LogEnum.END.getLogValue());
	return JspPage.USERCONFIG.getPageName();
}

	//氏名
	@RequestMapping(value = "/resister")
	public String getName() {
	log.info(Util.getMethodName() + LogEnum.START.getLogValue());
	log.info(Util.getMethodName() + LogEnum.END.getLogValue());
	return JspPage.USERINFO.getPageName();
	}

	//変更
	@RequestMapping(value = "/resister")
	public String getuserChange() {
	log.info(Util.getMethodName() + LogEnum.START.getLogValue());
	log.info(Util.getMethodName() + LogEnum.END.getLogValue());
	return JspPage.USERCHANGE.getPageName();
	}

	//削除
	@RequestMapping(value = "/resister")
	public String getDelete() {
	log.info(Util.getMethodName() + LogEnum.START.getLogValue());
	log.info(Util.getMethodName() + LogEnum.END.getLogValue());
	return JspPage.USERCONFIG.getPageName();
}

}

