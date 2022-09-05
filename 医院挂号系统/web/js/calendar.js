/**
 * 要求格式yyyy-MM
 * @param {Object} currentMonth 当前月份
 */
function getCalendar(currentMonth) {
    if (currentMonth.indexOf("-") < 0) {
        return;
    }
    var calendar = [];
    var myDate = currentMonth.split("-")[1] * 1;
    computeDate(currentMonth).forEach((item, index) => {
        var iscurrentmonth = false;
        if (myDate == item.split("-")[1] * 1) {
            iscurrentmonth = true;
        }
        calendar.push({
            "week": new Date(item).getDay() == 0 ? 7 : new Date(item).getDay(),
            "date": item,
            "iscurrentmonth": iscurrentmonth
        });
    });
    return calendar;
}

/**
 * 要求格式yyyy-MM
 * @param {Object} currentMonth 当前月份
 */
function computeDate(currentMonth) {
    if (currentMonth.indexOf("-") < 0) {
        return;
    }
    var myDate = currentMonth.split("-");
    // 上个月的需要显示的天数
    var preMonthDate = [];
    // 当月需要显示的天数
    var currentMonthDate = [];
    // 下个月需要显示的天数
    var nextMonthDate = [];
    // 显示的总格数
    var total = 6 * 7;
    var currentMonthDays = getMonthDays(myDate[0], myDate[1]);

    // 当前月1号是周几
    var week = (new Date(currentMonth + '-01').getDay() == 0 ? 7 : new Date(currentMonth + '-01').getDay()) - 1;
    // 存储上月要显示的日期
    if (week > 0) {
        var dateTime = new Date(currentMonth + "-01");
        for (var i = 0; i < week; i++) {
            dateTime = dateTime.setDate(dateTime.getDate() - 1);
            dateTime = new Date(dateTime);
            preMonthDate.push(getNowFormatDate(dateTime));
        }
        preMonthDate.reverse();
    }
    //console.info(preMonthDate);

    // 存储当月要显示的日期
    var currentTime = new Date(currentMonth + "-01");
    currentMonthDate.push(getNowFormatDate(currentTime));
    for (var i = 0; i < currentMonthDays - 1; i++) {
        currentTime = currentTime.setDate(currentTime.getDate() + 1);
        currentTime = new Date(currentTime);
        currentMonthDate.push(getNowFormatDate(currentTime));
    }
    //console.info(currentMonthDate);

    // 存储下个月的日期
    var nextDays = (total - currentMonthDays - week);
    if (nextDays > 0) {
        var nextYear = myDate[0];
        var nextMonth = myDate[1];
        if (myDate[1] >= 12) {
            nextMonth = 1;
            nextYear++;
        } else {
            nextMonth++;
        }
        var nextTime = new Date(nextYear + "-" + nextMonth + "-01");

        nextMonthDate.push(getNowFormatDate(nextTime));
        for (var i = 0; i < nextDays - 1; i++) {
            nextTime = nextTime.setDate(nextTime.getDate() + 1);
            nextTime = new Date(nextTime);
            nextMonthDate.push(getNowFormatDate(nextTime));
        }
    }
    //console.info(nextMonthDate);

    // 合并数据
    return preMonthDate.concat(currentMonthDate, nextMonthDate);
}

/**
 * 获取当月天数
 * @param {Object} year 年份
 * @param {Object} month 月份
 */
function getMonthDays(year, month) {
    return new Date(year, month, 0).getDate();
}

/**
 * 返回格式 yyyy-MM-dd
 * @param {Object} date
 */
function getNowFormatDate(date) {
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}