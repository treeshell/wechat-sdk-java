package fan.lv.wechat.api.kernel.impl;

import fan.lv.wechat.api.kernel.Client;
import fan.lv.wechat.entity.result.WxResult;
import fan.lv.wechat.util.HttpUtils;
import fan.lv.wechat.util.JsonUtil;
import fan.lv.wechat.util.RequestOptions;
import fan.lv.wechat.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lv_fan2008
 */
@Slf4j
abstract public class BaseClient implements Client {

    /**
     * 下载文件名字匹配
     */
    static final Pattern FILE_NAME_PATTERN = Pattern.compile("filename=[\"'](.*?)[\"']");

    /**
     * 校验结果是否合法，不合法则抛出异常
     *
     * @param result 结果字符串
     * @throws Exception 异常
     */
    protected void verifyResult(String result) throws Exception {
    }


    /**
     * 转换结果
     *
     * @param result     接收的字符串
     * @param resultType 结果类型
     * @param <T>        模板类型
     * @return 返回结果
     */
    protected <T extends WxResult> T convertResult(String result, Class<T> resultType) {
        return result.trim().startsWith("{") ? JsonUtil.parseJson(result, resultType)
                : XmlUtil.parseXml(result, resultType);
    }

    @Override
    public <T extends WxResult> T request(String uri, RequestOptions httpOptions, Class<T> resultType) {
        String url = uri.contains("://") ? uri : getBaseUrl() + uri;
        log.debug("request: url: {}, httpOptions: {}", url, httpOptions.toString());

        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpUtils.httpRequest(url, httpOptions);
        } catch (Exception exception) {
            return errorResult(-1, exception.getMessage(), resultType);
        }

        int statusOk = 200;
        if (httpResponse.getStatusLine().getStatusCode() != statusOk) {
            log.error("getStatusCode() != statusOk httpResponse: {}", httpResponse.toString());
            return errorResult(-1, httpResponse.getStatusLine().toString(), resultType);
        }

        // 如果不是json类型，则为原始数据流
        if (isRawStream(httpResponse)) {
            T wxResult = rawResult(resultType, httpResponse);
            log.debug("rawResult: {}", JsonUtil.toJson(wxResult));
            return wxResult;
        }

        String result = null;
        T wxResult;
        try {
            result = EntityUtils.toString(httpResponse.getEntity());
            verifyResult(result);
            wxResult = convertResult(result, resultType);
        } catch (Exception e) {
            return errorResult(-1, e.getMessage(), resultType);
        }
        log.debug("origin result: {}", result);
        log.debug("result: {}", JsonUtil.toJson(wxResult));
        return wxResult;
    }

    private boolean isRawStream(HttpResponse httpResponse) {
        if (getFileName(httpResponse) != null) {
            return true;
        }
        Header header = httpResponse.getEntity().getContentType();
        String contentType = header != null ? header.getValue() : null;
        return contentType != null && !contentType.contains("application/json") && !contentType.contains("application/xml")
                && !contentType.contains("text/plain");
    }

    /**
     * 获取http响应中的文件名字
     *
     * @param httpResponse http响应
     * @return 文件名字
     */
    private String getFileName(HttpResponse httpResponse) {
        Header[] headers = httpResponse.getHeaders("Content-disposition");
        if (headers == null) {
            return null;
        }
        for (Header header : headers) {
            String value = header.getValue();
            Matcher matcher = FILE_NAME_PATTERN.matcher(value);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    /**
     * 原生结果
     *
     * @param resultType   结果类型
     * @param httpResponse http相应
     * @param <T>          模板类型
     * @return 原生结果
     */
    protected <T extends WxResult> T rawResult(Class<T> resultType, HttpResponse httpResponse) {
        T result;
        try {
            Header header = httpResponse.getEntity().getContentType();
            result = resultType.getDeclaredConstructor().newInstance();
            result.setIsRawStream(true);
            result.setFilename(getFileName(httpResponse));
            result.setContentType(header != null ? header.getValue() : null);
            result.setContent(httpResponse.getEntity().getContent());
            result.setLength(httpResponse.getEntity().getContentLength());
            return result;
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    /**
     * 原生结果
     *
     * @param errorCode  错误码
     * @param resultType 返回类型
     * @param <T>        模板类型
     * @return 原生结果
     */
    protected <T extends WxResult> T errorResult(int errorCode, String errorMessage, Class<T> resultType) {
        T result;
        try {
            result = resultType.getDeclaredConstructor().newInstance();
            result.setErrorCode(errorCode);
            result.setErrorMessage(errorMessage);
            return result;
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}