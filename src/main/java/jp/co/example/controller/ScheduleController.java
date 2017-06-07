package jp.co.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import enums.*;
import jp.co.example.entity.Trainings;
import jp.co.example.entity.Users;
import jp.co.example.form.*;
import jp.co.example.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import util.Util;

/**
 * スケジュールコントローラ
 *
 * @author Yukihiro Yoshida
 *
 */
@Controller
@Slf4j
public class ScheduleController {

	private final String redirectKeyDate = "date";

	@Autowired
	ScheduleService ss;

	/**
	 * スケジュールへ遷移する際に情報を取得しjspに反映させる
	 *
	 * @param session
	 * @param model
	 * @param date
	 * @return
	 */
	@RequestMapping("/schedule")
	public String schedule(HttpSession session, Model model, @RequestParam("date") String date) {
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());
		log.info(LogEnum.METHOD_PARAM.getLogValue() + date);

		// 飛びなおすために日付を記憶
		session.setAttribute(ScopeKey.SCHEDULE_DATE.getScopeKey(), date);

		Users user = (Users) session.getAttribute(ScopeKey.LOGINUSER.getScopeKey());
		Trainings room = (Trainings) session.getAttribute(ScopeKey.LOGINROOM.getScopeKey());

		// テストデータ
		//ルート
		user = new Users(1, "pass", "山田 太郎", 1, 0);
		//生徒
		//user = new Users(12, "test", "内田 初美", 2, 3);
		room = new Trainings(2, "Java研修", 2, "");
		session.setAttribute(ScopeKey.LOGINUSER.getScopeKey(), user);
		session.setAttribute(ScopeKey.LOGINROOM.getScopeKey(), room);
		log.info(room.toString());

		// ユーザー情報を確認(未完成)
		log.info(LogEnum.IF.getLogValue() + "user == null");
		if (user == null) {
			log.info(LogEnum.TRUE.getLogValue());

			// ログインへ戻る処理を書く
		} else {
			log.info(LogEnum.FALSE.getLogValue());
		}

		// 部屋情報を確認(未完成)
		log.info(LogEnum.IF.getLogValue() + "room == null");
		if (room == null) {
			log.info(LogEnum.TRUE.getLogValue());

			// インデックスへ戻る処理を書く
		} else {
			log.info(LogEnum.FALSE.getLogValue());
		}

		// スケジュールを取得
		List<ScheduleForm> list = ss.getSchedule(user.getUserId(), date);
		model.addAttribute("list", list);

		boolean projectorAuthority;

		// プロジェクタ参照権限があるかチェック
		log.info(LogEnum.IF + "AutorityCheck");
		if (user.getAuthority().equals(Authority.ROOT.getAuthority())) {
			log.info("ROOT");
			// ログインユーザがルートの場合全てのプロジェクタ権限あり
			projectorAuthority = true;
		} else if (user.getAuthority().equals(Authority.CHARGE.getAuthority())) {
			log.info("CHARGE");
			// ログインユーザが担当者の場合全てのプロジェクタ権限なし
			projectorAuthority = false;
		} else {
			log.info("LECTURER or STUDENT");

			projectorAuthority = ss.isProjectorAuthority(user.getUserId(), room.getTrainingId());
		}
		log.info(LogEnum.IF_PARAM.getLogValue() + projectorAuthority);
		// プロジェクタ権限を格納
		model.addAttribute("projectorAuthority", projectorAuthority);

		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return JspPage.SCHEDULE.getPageName();
	}

	/**
	 * スケジュールの変更を行う際に使うコントローラ
	 *
	 * @param redirectAttributes
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/scheduleUpdate", method = RequestMethod.POST)
	public String scheduleUpdate(RedirectAttributes redirectAttributes, HttpSession session,
			@RequestParam("scheduleIdChange") String scheduleId, @RequestParam("hourChange") String hour,
			@RequestParam("minuteChange") String minute, @RequestParam("contentChange") String content,
			@RequestParam("important") String important) {
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());
		log.info(LogEnum.METHOD_PARAM.getLogValue() + scheduleId + hour + minute + content + important);

		// セッションから今参照している日付を取得
		String date = (String) session.getAttribute(ScopeKey.SCHEDULE_DATE.getScopeKey());

		ss.scheduleApdate(scheduleId, date, hour, minute, content, important);

		redirectAttributes.addAttribute(redirectKeyDate, session.getAttribute(ScopeKey.SCHEDULE_DATE.getScopeKey()));

		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return RedirectController.SCHEDULE.getRedirectName();
	}

	/**
	 * スケジュールの削除を行う際に通るコントローラ
	 *
	 * @param redirectAttributes
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/scheduleDelete", method = RequestMethod.POST)
	public String scheduleDelete(RedirectAttributes redirectAttributes, HttpSession session,
			@RequestParam("scheduleIdDelete") String scheduleId) {
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());

		ss.scheduleDelete(scheduleId);

		redirectAttributes.addAttribute(redirectKeyDate, session.getAttribute(ScopeKey.SCHEDULE_DATE.getScopeKey()));

		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return RedirectController.SCHEDULE.getRedirectName();
	}

	/**
	 * スケジュールの追加を行う際に通るコントローラ
	 *
	 * @param redirectAttributes
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/scheduleInsert", method = RequestMethod.POST)
	public String scheduleInsert(RedirectAttributes redirectAttributes, HttpSession session,
			@RequestParam("hour") String hour, @RequestParam("minute") String minute,
			@RequestParam("content") String content, @RequestParam("important") String important) {
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());

		Users user = (Users) session.getAttribute(ScopeKey.LOGINUSER.getScopeKey());

		// ユーザー情報を確認(未完成)
		log.info(LogEnum.IF.getLogValue() + "user == null");
		if (user == null) {
			log.info(LogEnum.TRUE.getLogValue());

			// ログインへ戻る処理を書く
		} else {
			log.info(LogEnum.FALSE.getLogValue());
		}

		// セッションから今参照している日付を取得
		String date = (String) session.getAttribute(ScopeKey.SCHEDULE_DATE.getScopeKey());

		ss.scheduleInsert(user.getUserId(), date, hour, minute, content, important);

		redirectAttributes.addAttribute(redirectKeyDate, session.getAttribute(ScopeKey.SCHEDULE_DATE.getScopeKey()));

		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return RedirectController.SCHEDULE.getRedirectName();
	}

	/**
	 * プロジェクタの予約をする際に通るコントローラ
	 *
	 * @param redirectAttributes
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/projectorReserve", method = RequestMethod.POST)
	public String projectorReserve(RedirectAttributes redirectAttributes, HttpSession session,
			@RequestParam("userId") String userId,
			@RequestParam("number") String number,
			@RequestParam("time") String time,
			@RequestParam("content") String content) {
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());
		log.info(userId + number + time + content);

		Integer trainingId = ((Trainings)session.getAttribute(ScopeKey.LOGINROOM.getScopeKey())).getTrainingId();
		String date = (String) session.getAttribute(ScopeKey.SCHEDULE_DATE.getScopeKey());

		ss.projectorReserve(trainingId, number, userId, date, time, content);

		redirectAttributes.addAttribute(redirectKeyDate, session.getAttribute(ScopeKey.SCHEDULE_DATE.getScopeKey()));

		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return RedirectController.SCHEDULE.getRedirectName();
	}

	/**
	 * プロジェクタの予約解除をする際に通るコントローラ
	 *
	 * @param redirectAttributes
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/projectorReserveRelease", method = RequestMethod.POST)
	public String projectorReserveRelease(RedirectAttributes redirectAttributes, HttpSession session,
			@RequestParam("projectorId") String projectorId) {
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());
		log.info(projectorId);

		ss.projectorReserveRelease(projectorId);
		redirectAttributes.addAttribute(redirectKeyDate, session.getAttribute(ScopeKey.SCHEDULE_DATE.getScopeKey()));

		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return RedirectController.SCHEDULE.getRedirectName();
	}

	/**
	 * プロジェクタ予約を取得するときに通るAjax用コントローラ
	 *
	 * @param session
	 * @param time
	 * @param date
	 * @return List<ProjectorForm>の形のjson型
	 */
	@RequestMapping(value = "projectorJson")
	@ResponseBody
	public List<ProjectorForm> projector(HttpSession session, @RequestParam("time") String time,
			@RequestParam("date") String date) {
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());
		log.info(LogEnum.METHOD_PARAM.getLogValue() + date);
		log.info(LogEnum.METHOD_PARAM.getLogValue() + time);

		// timeに値が入っていなければ空リストを返す
		if (time == null || "".equals(time)) {
			log.info(Util.getMethodName() + LogEnum.END.getLogValue());
			return new ArrayList<>();
		}

		// sessionLOGINLOOMは一時的
		// session.removeAttribute(ScopeKey.LOGINROOM.getScopeKey());
		Users user = (Users) session.getAttribute(ScopeKey.LOGINUSER.getScopeKey());
		Trainings room = (Trainings) session.getAttribute(ScopeKey.LOGINROOM.getScopeKey());

		// テストデータ
		// user = new Users(1, "pass", "山田 太郎", 1, 0);
		// room = new Trainings(2, "Java研修", 2, "");

		// ユーザー情報を確認(未完成)
		log.info(LogEnum.IF.getLogValue() + "user == null");
		if (user == null) {
			log.info(LogEnum.TRUE.getLogValue());

			// ログインへ戻る処理を書く
		} else {
			log.info(LogEnum.FALSE.getLogValue());
		}

		// 部屋情報を確認(未完成)
		log.info(LogEnum.IF.getLogValue() + "room == null");
		if (room == null) {
			log.info(LogEnum.TRUE.getLogValue());

			// インデックスへ戻る処理を書く
		} else {
			log.info(LogEnum.FALSE.getLogValue());
		}

		List<ProjectorForm> list = ss.getProjectorJson(room.getTrainingId(), date, time);

		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return list;
	}

	/**
	 * プロジェクタ予約で名前を取得するときに通るAjax用コントローラ
	 *
	 * @param session
	 * @param time
	 * @param date
	 * @return List<ReserveUserNameForm>の形のjson型
	 */
	@RequestMapping(value = "reserveNameJson")
	@ResponseBody
	public List<ReserveUserNameForm> projector(HttpSession session) {
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());

		Integer trainingId = ((Trainings) session.getAttribute(ScopeKey.LOGINROOM.getScopeKey())).getTrainingId();
		Integer userId = ((Users) session.getAttribute(ScopeKey.LOGINUSER.getScopeKey())).getUserId();

		List<ReserveUserNameForm> list = ss.getReserveUserNameJson(userId, trainingId);

		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return list;
	}
}
