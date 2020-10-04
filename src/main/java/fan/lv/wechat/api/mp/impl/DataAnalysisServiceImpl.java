package fan.lv.wechat.api.mp.impl;

import com.google.common.collect.ImmutableMap;
import fan.lv.wechat.api.kernel.Client;
import fan.lv.wechat.api.mp.DataAnalysisService;
import fan.lv.wechat.entity.mp.datacube.*;

/**
 * @author lv_fan2008
 */
public class DataAnalysisServiceImpl implements DataAnalysisService {
    /**
     * 请求客户端
     */
    protected Client client;


    /**
     * @param client 请求客户端
     */
    public DataAnalysisServiceImpl(Client client) {
        this.client = client;
    }

    @Override
    public WxDailyRetainInfoResult getDailyRetainInfo(String beginDate, String endDate) {
        return client.postJson("/datacube/getweanalysisappiddailyretaininfo",
                ImmutableMap.of("begin_date", beginDate, "end_date", endDate),
                WxDailyRetainInfoResult.class);
    }

    @Override
    public WxDailySummaryTrendResult getDailySummaryTrend(String beginDate, String endDate) {
        return client.postJson("/datacube/getweanalysisappiddailysummarytrend",
                ImmutableMap.of("begin_date", beginDate, "end_date", endDate),
                WxDailySummaryTrendResult.class);
    }

    @Override
    public WxDailyVisitTrendResult getDailyVisitTrend(String beginDate, String endDate) {
        return client.postJson("/datacube/getweanalysisappiddailyvisittrend",
                ImmutableMap.of("begin_date", beginDate, "end_date", endDate),
                WxDailyVisitTrendResult.class);
    }

    @Override
    public WxMonthlyVisitTrendResult getMonthlyVisitTrend(String beginDate, String endDate) {
        return client.postJson("/datacube/getweanalysisappidmonthlyvisittrend",
                ImmutableMap.of("begin_date", beginDate, "end_date", endDate),
                WxMonthlyVisitTrendResult.class);
    }

    @Override
    public WxWeeklyVisitTrendResult getWeeklyVisitTrend(String beginDate, String endDate) {
        return client.postJson("/datacube/getweanalysisappidweeklyvisittrend",
                ImmutableMap.of("begin_date", beginDate, "end_date", endDate),
                WxWeeklyVisitTrendResult.class);
    }

    @Override
    public WxUserPortraitResult getUserPortrait(String beginDate, String endDate) {
        return client.postJson("/datacube/getweanalysisappiduserportrait",
                ImmutableMap.of("begin_date", beginDate, "end_date", endDate),
                WxUserPortraitResult.class);
    }

    @Override
    public WxVisitDistributionResult getVisitDistribution(String beginDate, String endDate) {
        return client.postJson("/datacube/getweanalysisappidvisitdistribution",
                ImmutableMap.of("begin_date", beginDate, "end_date", endDate),
                WxVisitDistributionResult.class);
    }

    @Override
    public WxVisitPageResult getVisitPage(String beginDate, String endDate) {
        return client.postJson("/datacube/getweanalysisappidvisitpage",
                ImmutableMap.of("begin_date", beginDate, "end_date", endDate),
                WxVisitPageResult.class);
    }
}
