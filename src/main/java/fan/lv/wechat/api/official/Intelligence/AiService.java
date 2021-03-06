package fan.lv.wechat.api.official.Intelligence;

import fan.lv.wechat.entity.official.intelligence.*;
import fan.lv.wechat.entity.result.WxResult;

/**
 * 智能接口
 *
 * @author lv_fan2008
 */
public interface AiService {

    /**
     * 发送语义理解请求
     *
     * @param wxSemanticParam 语义理解请求参数
     * @return 返回相关语义的结果
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Intelligent_Interface/Natural_Language_Processing.html" target="_blank">微信官方接口文档</a>
     * @see <a href="https://open.weixin.qq.com/zh_CN/htmledition/res/assets/smart_lang_protocol.pdf" target="_blank">语义理解接口协议文档</a>
     */
    WxSemanticResult search(WxSemanticParam wxSemanticParam);

    /**
     * 微信AI开放接口——提交语音
     *
     * @param format   文件格式 （只支持mp3，16k，单声道，最大1M）
     * @param voiceId  语音唯一标识
     * @param filePath 语音文件路径
     * @param lang     语言，zh_CN 或 en_US
     * @return 返回结果
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Intelligent_Interface/AI_Open_API.html" target="_blank">微信官方接口文档</a>
     */
    WxResult aiSubmitVoiceForText(String format, String voiceId, String filePath, String lang);

    /**
     * 微信AI开放接口——获取语音识别结果
     * 请注意，添加完文件之后10s内调用这个接口
     *
     * @param voiceId 语音唯一标识
     * @param lang    语言，zh_CN 或 en_US
     * @return 语音识别结果
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Intelligent_Interface/AI_Open_API.html" target="_blank">微信官方接口文档</a>
     */
    WxAiQueryVoiceTextResult aiQueryVoiceForText(String voiceId, String lang);

    /**
     * 微信AI开放接口——微信翻译
     *
     * @param content      需要翻译的内容
     * @param fromLanguage 源语言，zh_CN 或 en_US
     * @param toLanguage   目标语言，zh_CN 或 en_US
     * @return 微信翻译结果
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Intelligent_Interface/AI_Open_API.html" target="_blank">微信官方接口文档</a>
     */
    WxAiTranslateResult aiTranslateVoice(String content, String fromLanguage, String toLanguage);
}
