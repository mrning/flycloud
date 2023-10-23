package com.lqjk.third.beans;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum ScenesType {
	METRO("地铁出行","01"),
	QUESTION("答题有礼","02"),
	TAXI("出租车","03"),
    SIGN_IN("签到","04"),
	BUS("公交出行","05"),
	ACT("活动","06"),
	GREEN_TRAVEL("绿色出行周","07"),
	STEP("行走", "08"),
	STEP_LUCKY("小福星", "09"),
	SHARE_TASK("分享", "10"),
    INVITE("邀请助力","11"),
    WALK("步行","12"),//十成联动
    TEN_CITY("冰雪运动","13"),//十成联动
	TREE("植树护绿","14"),
	SAKURA("樱花节", "15"),
	GREEN_TRAVEL2("世界地球日", "20"),
	MARATHON("守护地球", "21"),
	USER_MISSION("臻美新人", "34"),
	ANNIVERSARY_1("628周年庆", "101"),
	HELLO_BIKE("骑行出行", "300"),
	METRO_LC_001("低碳有礼", "29"),
	DONGTOU_COOPERATION("建设者Online", "112"),
	WATERCOLOR_FESTIVAL("国际水彩艺术季", "116"),
	ICE_SPORTS("冰雪运动", "117"),
	NEW_USER_01("新手任务", "303"),
	ZSJ_2023("植树节", "304");


	private String typeName;
	private String code;


	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
