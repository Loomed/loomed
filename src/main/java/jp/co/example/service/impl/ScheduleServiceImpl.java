package jp.co.example.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import enums.LogEnum;
import jp.co.example.dao.ProjectorsDao;
import jp.co.example.dao.SchedulesDao;
import jp.co.example.entity.Projectors;
import jp.co.example.entity.Schedules;
import jp.co.example.form.ProjectorForm;
import jp.co.example.form.ScheduleForm;
import jp.co.example.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import util.Util;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {
	private static final SimpleDateFormat SDF_DATETIME = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	private static final SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm");
	private static final String SELECT_ALL = "all";

	@Autowired
	SchedulesDao sd;

	@Autowired
	ProjectorsDao pd;

	@Override
	public List<ScheduleForm> getSchedule(Integer userId, String date) {
		// TODO 自動生成されたメソッド・スタブ
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());

		try {
			Timestamp dateMin = new Timestamp(SDF_DATETIME.parse(date + " 0:0:0").getTime());
			Timestamp dateMax = new Timestamp(SDF_DATETIME.parse(date + " 23:59:59").getTime());
			List<Schedules> list = sd.selectScheduleWhereUserIdAndDate(userId, dateMin, dateMax);

			List<ScheduleForm> listForm = new ArrayList<>();
			Timestamp ts = null;
			// jsp用にtimestampをStringに変換
			for (int i = 0; i < list.size(); i++) {
				ts = list.get(i).getUploadDatetime();
				listForm.add(new ScheduleForm(SDF_TIME.format(ts), list.get(i).getScheduleContents(),
						list.get(i).isImportant()));
			}

			log.info(Util.getMethodName() + LogEnum.END.getLogValue());
			return listForm;
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			log.info(Util.getMethodName() + LogEnum.EXCEPTION.getLogValue());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProjectorForm> getProjectorJson(Integer trainingId, String date, String time) {
		// TODO 自動生成されたメソッド・スタブ
		log.info(Util.getMethodName() + LogEnum.START.getLogValue());

		List<Projectors> list = new ArrayList<>();
		try {
			log.info(LogEnum.IF.getLogValue() + "SELECT_ALL.equals(time)");
			if (SELECT_ALL.equals(time)) {
				// 全時間帯のプロジェクタを参照する場合
				log.info(LogEnum.TRUE.getLogValue());

				Timestamp dateMin = new Timestamp(SDF_DATETIME.parse(date + " 0:0:0").getTime());
				Timestamp dateMax = new Timestamp(SDF_DATETIME.parse(date + " 23:59:59").getTime());
				list = pd.selectProjectorsWhereTrainingIdAndDate(trainingId, dateMin, dateMax);
			} else {
				log.info(LogEnum.FALSE.getLogValue());

				Timestamp dateTime = new Timestamp(SDF_DATETIME.parse(date + " " + time).getTime());
				list = pd.selectProjectorsWhereTrainingIdAndDateTime(trainingId, dateTime);
			}

			List<ProjectorForm> listForm = new ArrayList<>();
			Timestamp ts = null;
			// jsp用にtimestampをStringに変換
			for (int i = 0; i < list.size(); i++) {
				ts = list.get(i).getReserveTime();
				//listForm.add(new ProjectorForm(SDF_TIME.format(ts), );
			}
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		log.info(Util.getMethodName() + LogEnum.END.getLogValue());
		return null;
	}

}
