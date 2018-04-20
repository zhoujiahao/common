package cn.zhou.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期格式化类
 *
 * @author qingzhou
 */
public class DateUtil {

    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";

    public static final String DEFAULT_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String ASSIGN_FORMAT_DATE_TIME = "yyyy/MM/dd HH:mm";

    public static final String ASSIGN_FORMAT_DATE = "yyyy/MM/dd";

    public static final String DEFAULT_FORMAT_TIME = "HH:mm:ss";

    public static final String FORMAT_TIME_MONTH = "yyyy-MM";

    public static final String FORMAT_TIME_HOUR = "yyyy-MM-dd HH";

    public static final String FORMAT_DATE_HOUR = "yyyyMMddHH";

    public static final String FORMAT_DATE_DAY = "yyyyMMdd";

    public static void main(String[] args) {
        System.out.println(formatDate(new Date(), "yyyy-MM-dd"));
    }

    /**
     * 锁对象
     */
    private static final Object LOCK_OBJ = new Object();

    /**
     * 存放不同的日期模板格式的sdf的Map
     */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     *
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

        if (tl == null) {
            synchronized (LOCK_OBJ) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }

    // =========================================================
    // format date/time methods
    // =========================================================
    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_FORMAT_DATE);
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, DEFAULT_FORMAT_DATE_TIME);
    }

    public static String formatTime(Date date) {
        return formatDate(date, DEFAULT_FORMAT_TIME);
    }

    public static String formatMonthDate(Date date) {
        return formatDate(date, FORMAT_TIME_MONTH);
    }

    public static String formatMonthTime(long monthTime) {
        Date date = new Date(monthTime);
        return formatDate(date, FORMAT_TIME_MONTH);
    }

    public static String formatHourTime(long hourTime) {
        Date date = new Date(hourTime);
        return formatDate(date, FORMAT_TIME_HOUR);
    }

    public static String formatHourDate(long time) {
        Date date = new Date(time);
        return formatDate(date, FORMAT_DATE_HOUR);
    }

    public static String formatDayDate(long time) {
        Date date = new Date(time);
        return formatDate(date, FORMAT_DATE_DAY);
    }

    /**
     * getDateBegin 2016年6月17日 下午8:00:05
     *
     * @param date
     * @return TODO：获取某天的起始时间
     */
    public static Date getDateBegining(Date date) {
        String startStr = DateUtil.formatDate(date, DateUtil.DEFAULT_FORMAT_DATE);
        Date startDateTime = DateUtil.parseDateTime(startStr + " 00:00:00");
        return startDateTime;
    }

    /**
     * getDateEnding 2016年6月17日 下午8:01:16
     *
     * @param date
     * @return TODO：获取某天的结尾时间
     */
    public static Date getDateEnding(Date date) {
        String endStr = DateUtil.formatDate(date, DateUtil.DEFAULT_FORMAT_DATE);
        Date endDateTime = DateUtil.parseDateTime(endStr + " 23:59:59");
        return endDateTime;
    }

    /**
     * Format the date object with specified pattern.
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        if (pattern == null) {
            pattern = DEFAULT_FORMAT_DATE_TIME;
        }
        return getSdf(pattern).format(date);
    }

    // =========================================================
    // parse date/time methods
    // =========================================================
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, DEFAULT_FORMAT_DATE);
    }

    public static Date parseDateTime(String dateTimeStr) {
        return parseDate(dateTimeStr, DEFAULT_FORMAT_DATE_TIME);
    }

    public static Date parseHourDate(String dateStr) {
        return parseDate(dateStr, FORMAT_DATE_HOUR);
    }

    public static Date parseMonthDateTime(String monthDateStr) {
        return parseDate(monthDateStr, FORMAT_TIME_MONTH);
    }

    public static Date parseDate(String dateTimeStr, String pattern) {
        if (dateTimeStr == null || dateTimeStr.length() == 0) {
            return null;
        }

        if (pattern == null) {
            pattern = DEFAULT_FORMAT_DATE_TIME;
        }

        try {
            return getSdf(pattern).parse(dateTimeStr);
        } catch (Exception ex) {
            return null;
        }
    }

    public static long parseDayTime(String str) {
        return parseDate(str, FORMAT_DATE_DAY).getTime();
    }

    public static String getMonthValue(int month) {
        String result = "";
        if (month == 1) {
            result = "January";
        } else if (month == 2) {
            result = "February";
        } else if (month == 3) {
            result = "March";
        } else if (month == 4) {
            result = "April";
        } else if (month == 5) {
            result = "May";
        } else if (month == 6) {
            result = "June";
        } else if (month == 7) {
            result = "July";
        } else if (month == 8) {
            result = "August";
        } else if (month == 9) {
            result = "September";
        } else if (month == 10) {
            result = "October";
        } else if (month == 11) {
            result = "November";
        } else if (month == 12) {
            result = "December";
        }

        return result;
    }

    /**
     * getBeforeDate 2016年6月14日 上午9:25:07
     *
     * @param currentDate
     * @return TODO：获取前一天日期
     */
    public static Date getBeforeDate(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date preDate = calendar.getTime();
        return preDate;
    }

    public static Date getAfterMonthDate(Date currentDate, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, num);
        Date preDate = calendar.getTime();
        return preDate;
    }

    public static String formatDateLongSeconds(long beginTime) {
        return formatDateLongSeconds(beginTime, DEFAULT_FORMAT_DATE);
    }

    public static String formatDateLongSeconds(long beginTime, String pattern) {
        Date date = new Date(beginTime * 1000);
        return formatDate(date, pattern);
    }

    /**
     * 前一天开始时间时间戳
     *
     * @param date
     * @return
     */
    public static long getLastDayBeginTimestamp(Date date) {
        return DateUtil.getBeforeDate(date).getTime();
    }

    /**
     * 该毫秒数所在小时的开始毫秒数
     *
     * @param time
     * @return
     */
    public static long getHourBegin(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTimeInMillis();
    }
}