package fan.lv.wechat.api.open;

import fan.lv.wechat.api.kernel.Client;
import fan.lv.wechat.api.kernel.container.impl.ContainerImpl;
import fan.lv.wechat.api.official.Intelligence.AiService;
import fan.lv.wechat.api.official.Intelligence.impl.AiServiceImpl;
import fan.lv.wechat.api.official.account.AccountService;
import fan.lv.wechat.api.official.account.impl.AccountServiceImpl;
import fan.lv.wechat.api.official.base.BaseService;
import fan.lv.wechat.api.official.base.impl.BaseServiceImpl;
import fan.lv.wechat.api.official.comment.CommentService;
import fan.lv.wechat.api.official.comment.impl.CommentServiceImpl;
import fan.lv.wechat.api.official.customer.CustomerService;
import fan.lv.wechat.api.official.customer.impl.CustomerServiceImpl;
import fan.lv.wechat.api.official.jssdk.JsSdkService;
import fan.lv.wechat.api.official.jssdk.imp.JsSdkServiceImpl;
import fan.lv.wechat.api.official.menu.MenuService;
import fan.lv.wechat.api.official.menu.impl.MenuServiceImpl;
import fan.lv.wechat.api.official.message.MassSendService;
import fan.lv.wechat.api.official.message.TemplateService;
import fan.lv.wechat.api.official.message.impl.MassSendServiceImpl;
import fan.lv.wechat.api.official.message.impl.TemplateServiceImpl;
import fan.lv.wechat.api.official.server.ServerService;
import fan.lv.wechat.api.official.server.impl.ServerServiceImpl;
import fan.lv.wechat.api.official.sns.SnsService;
import fan.lv.wechat.api.official.statics.*;
import fan.lv.wechat.api.official.statics.impl.*;
import fan.lv.wechat.api.official.user.UserService;
import fan.lv.wechat.api.official.user.UserTagService;
import fan.lv.wechat.api.official.user.impl.UserServiceImpl;
import fan.lv.wechat.api.official.user.impl.UserTagServiceImpl;
import fan.lv.wechat.api.open.service.ofiicial.OfficialFastRegisterMpService;
import fan.lv.wechat.api.open.service.ofiicial.MpLinkService;
import fan.lv.wechat.api.open.service.authorizer.OpenAccountService;
import fan.lv.wechat.api.open.service.ofiicial.impl.OfficialFastRegisterMpServiceImpl;
import fan.lv.wechat.api.open.service.open.OpenPlatformService;
import fan.lv.wechat.api.open.service.authorizer.impl.AuthorizerClientImpl;
import fan.lv.wechat.api.open.service.ofiicial.impl.SnsServiceImpl;
import fan.lv.wechat.api.open.service.ofiicial.impl.MpLinkServiceImpl;
import fan.lv.wechat.api.open.service.authorizer.impl.OpenAccountServiceImpl;
import fan.lv.wechat.entity.open.config.OpenPlatformConfig;

/**
 * 公众号服务
 *
 * @author lv_fan2008
 */
public class OpenOfficialApp extends ContainerImpl {

    /**
     * @param config 公众号配置
     */
    public OpenOfficialApp(OpenPlatformService open, Client openClient, OpenPlatformConfig config, String appId) {
        Client client = new AuthorizerClientImpl(open, config, appId);
        this.bind(OpenPlatformConfig.class, () -> config);
        this.bind(Client.class, () -> client);
        this.bind(AccountService.class, () -> new AccountServiceImpl(client));
        this.bind(BaseService.class, () -> new BaseServiceImpl(client, appId));
        this.bind(CommentService.class, () -> new CommentServiceImpl(client));
        this.bind(CustomerService.class, () -> new CustomerServiceImpl(client));
        this.bind(AiService.class, () -> new AiServiceImpl(client));
        this.bind(JsSdkService.class, () -> new JsSdkServiceImpl(appId, client, config.getCache()));
        this.bind(MenuService.class, () -> new MenuServiceImpl(client));
        this.bind(MassSendService.class, () -> new MassSendServiceImpl(client));
        this.bind(TemplateService.class, () -> new TemplateServiceImpl(client));
        this.bind(ServerService.class, () -> new ServerServiceImpl(config.getAesKey(), config.getToken(), config.getComponentAppId()));
        this.bind(SnsService.class, () -> new SnsServiceImpl(openClient, config, appId));
        this.bind(ArticleStaticService.class, () -> new ArticleStaticServiceImpl(client));
        this.bind(PublisherAdStaticService.class, () -> new PublisherAdStaticServiceImpl(client));
        this.bind(UserStaticService.class, () -> new UserStaticServiceImpl(client));
        this.bind(MessageStaticService.class, () -> new MessageStaticServiceImpl(client));
        this.bind(InterfaceStaticService.class, () -> new InterfaceStaticServiceImpl(client));
        this.bind(UserService.class, () -> new UserServiceImpl(client));
        this.bind(UserTagService.class, () -> new UserTagServiceImpl(client));
        this.bind(OpenAccountService.class, () -> new OpenAccountServiceImpl(appId, client));
        this.bind(ServerService.class, () -> new AuthorizerClientImpl(open, config, appId));
        this.bind(MpLinkService.class, () -> new MpLinkServiceImpl(client));
        this.bind(OfficialFastRegisterMpService.class, () -> new OfficialFastRegisterMpServiceImpl(client, config, appId));
    }
}
