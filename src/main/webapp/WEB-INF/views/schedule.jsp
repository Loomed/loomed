<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>スケジュール</title>

<%@ include file="common/head.jsp"%>

<!-- 研修教室管理と同じ -->
<style>
.mycontainer {
	margin-top: 75px;
}

.navbar-brand {
	background: url("images/logo_alpha.png") no-repeat left center;
	background-size: contain;
	height: 60px;
	width: 180px;
}

.navbar-header {
	margin-left: 3px;
	width: 100%;
}

.navbar-btn-modify {
	margin-top: 0;
	margin-bottom: 0;
	margin-right: 30px;
	margin-left: 30px;
}

/*データベースのimportantがtrueだった場合 */
.importanttrue {
	color: RED;
}

td {
	word-wrap: break-word;
}
</style>
</head>

<body>

	<%@ include file="common/header.jsp"%>

	<!-- ログインユーザの権限を保持 -->
	<input type="hidden" id="loginUserName"
		value="${fn:escapeXml(loginuser.userName) }" />
	<input type="hidden" id="loginUserAuthority"
		value="${fn:escapeXml(loginuser.authority) }" />
	<input type="hidden" id="loginRoom"
		value="${fn:escapeXml(loginroom.trainingId) }" />
	<input type="hidden" id="loginUserId"
		value="${fn:escapeXml(loginuser.userId) }" />

	<div class="container mycontainer">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">スケジュール</h3>
					</div>
					<div class="panel-body">
						<!-- スケジュールあるなしで分岐 -->
						<c:choose>
							<c:when test="${not empty list}">
								<div class="panel panel-default">
									<table class="table" style="table-layout: fixed; width: 100%;">
										<thead>
											<tr>
												<th class="col-xs-1">時間</th>
												<th class="col-xs-7">内容</th>
												<th class="col-xs-1">変更</th>
												<th class="col-xs-1">削除</th>
											</tr>
										</thead>
										<tbody id="scheduleBody">
											<c:forEach var="list" items="${list}" varStatus="status">
												<input type="hidden" id="scheduleId${status.index}"
													value="${fn:escapeXml(list.scheduleId) }" />
												<tr>
													<td id="timeScheduleTable${status.index}"
														class="important${fn:escapeXml(list.important) }">${fn:escapeXml(list.time) }</td>
													<td id="contentScheduleTable${status.index}"
														class="important${fn:escapeXml(list.important) }">${fn:escapeXml(list.content) }</td>
													<td><button id="change:${status.index}"
															class="btn btn-primary change">変更</button></td>
													<td><button id="delete:${status.index}"
															class="btn btn-danger delete">削除</button></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</c:when>
							<c:otherwise>
								<h4 style="text-align: center;">予定はありません</h4>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container mycontainer">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">スケジュール追加</h3>
					</div>
					<form action="scheduleInsert" method="post">
						<div class="panel-body">
							<div class="panel panel-default">
								<table class="table">
									<thead>
										<tr>
											<th class="col-xs-3">時間</th>
											<th class="col-xs-9">内容</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="number" name="hour" size="2" min="0"
												max="23" required style="text-align: right;">時<input
												type="number" name="minute" size="2" min="0" max="59" required
												style="text-align: right;">分</td>
											<td>
												<!--<input type="text" name="#" placeholder="ここに内容を入れて下さい。" required>-->
												<textarea name="content" class="col-xs-12"
													placeholder="ここに内容を入力してください" required></textarea>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<label for="normal"><input type="radio" id="normal"
								name="important" value="normal" checked />通常</label> <label
								for="important"><input type="radio" id="important"
								name="important" value="important" />重要</label> <br> <br> <input
								type="submit" class="btn btn-primary" name="" value="追加">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="container mycontainer">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">プロジェクタ予約状況</h3>
						<!-- プロジェクタの権限がある場合セレクトボックス表示 -->
						<c:if test="${fn:escapeXml(projectorAuthority) }">
							<br> 閲覧したい時間 <select id="selectTime" name="selectTime">
								<option></option>
								<option value="9:00">9:00</option>
								<option value="9:30">9:30</option>
								<option value="10:00">10:00</option>
								<option value="10:30">10:30</option>
								<option value="11:00">11:00</option>
								<option value="11:30">11:30</option>
								<option value="12:00">12:00</option>
								<option value="12:30">12:30</option>
								<option value="13:00">13:00</option>
								<option value="13:30">13:30</option>
								<option value="14:00">14:00</option>
								<option value="14:30">14:30</option>
								<option value="15:00">15:00</option>
								<option value="15:30">15:30</option>
								<option value="16:00">16:00</option>
								<option value="16:30">16:30</option>
								<option value="17:00">17:00</option>
								<option value="17:30">17:30</option>
								<option value="18:00">18:00</option>
								<option value="18:30">18:30</option>
								<option value="19:00">19:00</option>
								<option value="19:30">19:30</option>
								<option value="0:00">All</option>
							</select>
						</c:if>
					</div>
					<div class="panel-body">

						<!-- プロジェクタの権限がある場合セレクトボックス表示 -->
						<c:choose>
							<c:when test="${fn:escapeXml(projectorAuthority) }">
								<div class="panel panel-default">
									<table class="table">
										<thead>
											<tr>
												<th>予約時間</th>
												<th>プロジェクタ</th>
												<th>予約ユーザ</th>
												<th>予約</th>
											</tr>
										</thead>
										<tbody id="projectorBody">
										</tbody>
									</table>
								</div>
							</c:when>
							<c:when test="${!fn:escapeXml(projectorAuthority) }">
								<h4 style="text-align: center;">権限がありません</h4>
							</c:when>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!--  スケジュール削除モーダル -->
	<div class="modal fade" id="configDeleteModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="scheduleDelete" method="post">
					<div class="modal-header modal-header-modify">
						<button type="button" class="close" data-dismiss="modal">
							<span>×</span>
						</button>
						<h4 class="modal-title">削除確認</h4>
					</div>
					<input id="scheduleIdDeleteModal" name="scheduleIdDelete"
						type="hidden" value="" />
					<div class="modal-body">
						この内容のスケジュールを削除しますか？<br> <br>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">時間</h3>
							</div>
							<div class="panel-body">
								<div id="timeDeleteModal"></div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">内容</h3>
							</div>
							<div class="panel-body">
								<textarea id="contentDeleteModal" class="col-xs-10" readonly></textarea>
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">キャンセル</button>
						<input type="submit" class="btn btn-danger" value="削除">
					</div>
				</form>
			</div>
		</div>
	</div>

	<!--  プロジェクタ予約解除モーダル -->
	<div class="modal fade" id="reserveReleaseModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="projectorReserveRelease" method="post">
					<div class="modal-header modal-header-modify">
						<button type="button" class="close" data-dismiss="modal">
							<span>×</span>
						</button>
						<h4 class="modal-title">予約解除確認</h4>
					</div>

					<div class="modal-body">
						この内容の予約を解除しますか？<br> <br>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">時間</h3>
							</div>
							<div class="panel-body">
								<div id="releaseTimeModal"></div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">プロジェクタ</h3>
							</div>
							<div class="panel-body">
								<div id="releaseProjectorModal"></div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">予約ユーザ</h3>
							</div>
							<div class="panel-body">
								<div id="releaseReserveUserModal"></div>
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">キャンセル</button>
						<input type="submit" class="btn btn-danger" value="解除">
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- スケジュール変更モーダル -->
	<div class="modal fade" id="configChangeModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="scheduleUpdate" method="post">
					<div class="modal-header modal-header-modify">
						<button type="button" class="close" data-dismiss="modal">
							<span>×</span>
						</button>
						<h4 class="modal-title">変更内容</h4>
					</div>
					<input id="scheduleIdChangeModal" name="scheduleIdChange"
						type="hidden" value="" />
					<div class="modal-body">
						変更する項目を編集してください
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">時間</h3>
							</div>
							<div class="panel-body">
								<input id="hourChangeModal" name="hourChange" type="number"
									name="#" size="2" min="0" max="23" required
									style="text-align: right;">時 <input
									id="minuteChangeModal" name="minuteChange" type="number"
									name="#" size="2" min="0" max="59" required
									style="text-align: right;">分
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">内容</h3>
							</div>
							<div class="panel-body">
								<!--<input id="contentChangeModal" size="70" type="text" value="" required>-->
								<textarea id="contentChangeModal" name="contentChange"
									class="col-xs-10" required></textarea>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">重要</h3>
							</div>
							<div class="panel-body">
								<label for="normalModal"><input type="radio"
									id="normalModal" name="important" value="normal" />通常</label> <label
									for="importantModal"><input type="radio"
									id="importantModal" name="important" value="important" />重要</label>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">キャンセル</button>
						<input type="submit" id="changeModalCommit"
							class="btn btn-primary" value="変更">
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- 予約確認モーダル -->
	<div class="modal fade" id="configReserveModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="projectorReserve" method="post">
					<div class="modal-header modal-header-modify">
						<button type="button" class="close" data-dismiss="modal">
							<span>×</span>
						</button>
						<h4 class="modal-title">予約確認</h4>
					</div>
					<div class="modal-body">
						項目を入力し、予約を完了してください<br> <br>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">予約ユーザ</h3>
							</div>
							<div class="panel-body">
								<div id="reserveUserModal"></div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">予約内容</h3>
							</div>
							<div class="panel-body">
								<div id="timeModal"></div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">予約目的</h3>
							</div>
							<div class="panel-body">
								<textarea class="col-xs-10"></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">キャンセル</button>
						<input type="submit" class="btn btn-primary" value="予約">
					</div>
				</form>
			</div>
		</div>
	</div>


	<script type="text/javascript" src="/js/schedule.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>

