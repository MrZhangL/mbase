package com.tomcode.agent.health;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZJUHealthReport {




    private static final String REGISTER_PAGE_URL = "https://healthreport.zju.edu.cn/ncov/wap/default/index";
    private static final String REGISTER_POST_URL = "https://healthreport.zju.edu.cn/ncov/wap/default/save";
    private static final String LOGIN_POST_URL = "https://zjuam.zju.edu.cn/cas/login?service=https%3A%2F%2Fhealthreport.zju.edu.cn%2Fa_zju%2Fapi%2Fsso%2Findex%3Fredirect%3Dhttps%253A%252F%252Fhealthreport.zju.edu.cn%252Fncov%252Fwap%252Fdefault%252Findex%26from%3Dwap";
    public static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

    private static final String STR_IN_LOGIN_PAGE = "统一身份认证平台";
    private static final String STR_IN_REGISTER_PAGE = "每日上报";

    private static Pattern executionPattern = Pattern.compile("(?<=<input type=\"hidden\" name=\"execution\" value=\").*(?=\")");
    private static Pattern oldInfoPattern = Pattern.compile("(?<=oldInfo: ).*(?=,\n)");
    private static Pattern idPattern = Pattern.compile("(?<=var def = ).*(?=;\n)");
    private static Pattern namePattern = Pattern.compile("(?<=realname: \").*(?=\",\n)");
    private static Pattern numberPattern = Pattern.compile("(?<=number: ').*(?=',\n)");

    private OkHttpClient client;
    User user;


    public ZJUHealthReport(User user) {

        client = getClientWithCookie();
        this.user = user;
    }


    public void registerAndNotify() {
        if(register()) {
            // TODO：发送邮件提醒打卡成功
        } else {
            // TODO：发送邮件提醒打卡失败
        }
    }

    public boolean register() {
        try {
            TransferPage ts;
            if(!(ts = toReportPage()).isSuccess()) {
                return false;
            }

            // 登录成功，打卡
            return registerHealth(ts.getHtml());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private OkHttpClient getClientWithCookie() {
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            private HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
            private boolean first = true;
            @Override
            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {

                List<Cookie> orDefault = cookieStore.getOrDefault(httpUrl.host(), new ArrayList<>());
                list.forEach((c)->{
                    boolean flag = false;
                    for (Cookie cookie : orDefault) {
                        if(cookie.name().equals(c.name())) {
                            flag = true;
                            break;
                        }
                    }

                    if(!flag)
                        orDefault.add(c);
                });

                if(first) {
                    Cookie cookie1 = new Cookie.Builder()
                            .name("Hm_lpvt_48b682d4885d22a90111e46b972e3268")
                            .value("1596904293").domain("healthreport.zju.edu.cn").build();
                    Cookie cookie2 = new Cookie.Builder()
                            .name("Hm_lvt_48b682d4885d22a90111e46b972e3268")
                            .value("1596904236,1596904269,1596904293").domain("healthreport.zju.edu.cn").build();

                    orDefault.add(cookie1);
                    orDefault.add(cookie2);
                    first = false;
                }
                cookieStore.put(httpUrl.host(), orDefault);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                List<Cookie> cookies = new ArrayList();
                for(String key : cookieStore.keySet()) {
                    List<Cookie> cookiess = cookieStore.get(key);
                    for(Cookie cookie : cookiess) {
                        cookies.add(cookie);
                    }
                }

                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        }).build();

        return client;
    }

    private TransferPage toReportPage() throws IOException {
        Request toReportPage = new Request.Builder().url(REGISTER_PAGE_URL).build();
        Response res = client.newCall(toReportPage).execute();

        String body = res.body().string();
        if(body.contains(STR_IN_LOGIN_PAGE)) {
            // 需要登录
            return login(body);
        }

        return new TransferPage(body.contains(STR_IN_REGISTER_PAGE), body);
    }

    private TransferPage login(String pageHtml) throws IOException {
        // 提取execution
        String match1 = "<input type=\"hidden\" name=\"execution\" value=\"";
        String match2 = "<input type=\"hidden\" name=\"_eventId\" value=\"submit\" />";
        int index = pageHtml.indexOf(match1);
        index += match1.length();
        int index2 = pageHtml.indexOf(match2);

        if(index >= index2 || index == -1 || index2 == -1) {
            return TransferPage.Fail(null);
        }

        String execution = pageHtml.substring(index, index2).split("\"")[0];

        // Form表单登录
        FormBody form = new FormBody.Builder().add("username", user.getUsername())
                .add("password", user.getPassword())
                .add("authcode","")
                .add("execution", execution)
                .add("_eventId", "submit")
                .build();
        Request login = new Request.Builder()
                .url(LOGIN_POST_URL)
                .post(form)
                .build();

        Response res = client.newCall(login).execute();
        String body = res.body().string();
        //System.out.println(body);

        return new TransferPage(body.contains(STR_IN_REGISTER_PAGE), body);
    }

    public static void main(String[] args) throws IOException {
        /*OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            private HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                cookieStore.put(httpUrl.host(), list);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                List<Cookie> cookies = cookieStore.get(httpUrl.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        }).build();

        String txt = "{\"sfymqjczrj\":\"0\",\"zjdfgj\":\"\",\"sfyrjjh\":\"0\",\"cfgj\":\"\",\"tjgj\":\"\",\"nrjrq\":\"0\",\"rjka\":\"\",\"jnmddsheng\":\"\",\"jnmddshi\":\"\",\"jnmddqu\":\"\",\"jnmddxiangxi\":\"\",\"rjjtfs\":\"\",\"rjjtfs1\":\"\",\"rjjtgjbc\":\"\",\"jnjtfs\":\"\",\"jnjtfs1\":\"\",\"jnjtgjbc\":\"\",\"sfqrxxss\":\"1\",\"sfqtyyqjwdg\":\"0\",\"sffrqjwdg\":\"0\",\"jrdqtlqk\":\"[\\\"0\\\"]\",\"sfhsjc\":\"0\",\"tw\":\"0\",\"sfcxtz\":\"0\",\"sfjcbh\":\"0\",\"sfcxzysx\":\"0\",\"qksm\":\"\",\"sfyyjc\":\"0\",\"jcjgqr\":\"0\",\"remark\":\"\",\"address\":\"\\u6d59\\u6c5f\\u7701\\u676d\\u5dde\\u5e02\\u6c5f\\u5e72\\u533a\\u5f6d\\u57e0\\u8857\\u9053\\u65b0\\u98ce\\u8def21\\u53f7\\u676d\\u5dde\\u4e1c\\u7ad9\",\"geo_api_info\":\"{\\\"type\\\":\\\"complete\\\",\\\"position\\\":{\\\"Q\\\":30.288862575955,\\\"R\\\":120.21112630208398,\\\"lng\\\":120.211126,\\\"lat\\\":30.288863},\\\"location_type\\\":\\\"html5\\\",\\\"message\\\":\\\"Get geolocation success.Convert Success.Get address success.\\\",\\\"accuracy\\\":65,\\\"isConverted\\\":true,\\\"status\\\":1,\\\"addressComponent\\\":{\\\"citycode\\\":\\\"0571\\\",\\\"adcode\\\":\\\"330104\\\",\\\"businessAreas\\\":[{\\\"name\\\":\\\"\\u57ce\\u4e1c\\\",\\\"id\\\":\\\"330104\\\",\\\"location\\\":{\\\"Q\\\":30.271987,\\\"R\\\":120.20027199999998,\\\"lng\\\":120.200272,\\\"lat\\\":30.271987}},{\\\"name\\\":\\\"\\u5f6d\\u57e0\\\",\\\"id\\\":\\\"330104\\\",\\\"location\\\":{\\\"Q\\\":30.285965,\\\"R\\\":120.234238,\\\"lng\\\":120.234238,\\\"lat\\\":30.285965}}],\\\"neighborhoodType\\\":\\\"\\\",\\\"neighborhood\\\":\\\"\\\",\\\"building\\\":\\\"\\\",\\\"buildingType\\\":\\\"\\\",\\\"street\\\":\\\"\\u65b0\\u98ce\\u8def\\\",\\\"streetNumber\\\":\\\"21\\u53f7\\\",\\\"country\\\":\\\"\\u4e2d\\u56fd\\\",\\\"province\\\":\\\"\\u6d59\\u6c5f\\u7701\\\",\\\"city\\\":\\\"\\u676d\\u5dde\\u5e02\\\",\\\"district\\\":\\\"\\u6c5f\\u5e72\\u533a\\\",\\\"township\\\":\\\"\\u5f6d\\u57e0\\u8857\\u9053\\\"},\\\"formattedAddress\\\":\\\"\\u6d59\\u6c5f\\u7701\\u676d\\u5dde\\u5e02\\u6c5f\\u5e72\\u533a\\u5f6d\\u57e0\\u8857\\u9053\\u65b0\\u98ce\\u8def21\\u53f7\\u676d\\u5dde\\u4e1c\\u7ad9\\\",\\\"roads\\\":[],\\\"crosses\\\":[],\\\"pois\\\":[],\\\"info\\\":\\\"SUCCESS\\\"}\",\"area\":\"\\u6d59\\u6c5f\\u7701 \\u676d\\u5dde\\u5e02 \\u6c5f\\u5e72\\u533a\",\"province\":\"\\u6d59\\u6c5f\\u7701\",\"city\":\"\\u676d\\u5dde\\u5e02\",\"sfzx\":\"0\",\"sfjcwhry\":\"0\",\"sfjchbry\":\"0\",\"sfcyglq\":\"0\",\"gllx\":\"\",\"glksrq\":\"\",\"jcbhlx\":\"\",\"jcbhrq\":\"\",\"bztcyy\":\"1\",\"sftjhb\":\"0\",\"sftjwh\":\"0\",\"sfjcqz\":\"\",\"jcqzrq\":\"\",\"jrsfqzys\":\"\",\"jrsfqzfy\":\"\",\"sfyqjzgc\":\"\",\"sfsqhzjkk\":1,\"sqhzjkkys\":\"1\",\"gwszgzcs\":\"\",\"szgj\":\"\",\"fxyy\":\"\",\"jcjg\":\"\",\"date\":\"20200807\",\"uid\":\"54576\",\"created\":1596801901,\"szsqsfybl\":\"0\",\"sfygtjzzfj\":0,\"gtjzzfjsj\":\"\",\"ismoved\":\"1\",\"created_uid\":0,\"id\":11404780}";

        Map<String, String> map = JSON.parseObject(txt, Map.class);

        System.out.println(map.get("sfymqjczrj"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.format(new Date()));*/

        User user = new User("21925094", "042013", "a535665407@qq.com");
        ZJUHealthReport report = new ZJUHealthReport(user);
        System.out.println(report.register());


        /*Matcher oldInfoMatcher = oldInfoPattern.matcher(html);
        Matcher idMatcher = idPattern.matcher(html);
        Matcher nameMatcher = namePattern.matcher(html);
        Matcher numberMatcher = numberPattern.matcher(html);

        if(!oldInfoMatcher.find()) {
            System.out.println(false);
        }

        if(!idMatcher.find()) {
            System.out.println(false);
        }

        if(!nameMatcher.find()) {
            System.out.println(false);
        }

        if(!numberMatcher.find()) {
            System.out.println(false);
        }*/

    }

    private String getRquestJson(String html) {
        Matcher oldInfoMatcher = oldInfoPattern.matcher(html);
        Matcher idMatcher = idPattern.matcher(html);
        Matcher nameMatcher = namePattern.matcher(html);
        Matcher numberMatcher = numberPattern.matcher(html);

        if(!oldInfoMatcher.find() || !idMatcher.find() || !nameMatcher.find() || !numberMatcher.find()) {
            return null;
        }

        String oldInfoJson = oldInfoMatcher.group(0);
        Map<String, Object> oldInfo = JSON.parseObject(oldInfoJson, Map.class);

        String defJson = idMatcher.group(0);
        Map<String, String> def = JSON.parseObject(defJson, Map.class);

        String name = nameMatcher.group(0);
        String number = numberMatcher.group(0);

        oldInfo.put("id", def.get("id"));
        oldInfo.put("name", name);
        oldInfo.put("number", number);
        oldInfo.put("created", System.currentTimeMillis()/1000);

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        oldInfo.put("date", dateFormat.format(new Date()));
        //oldInfo.put("jrdqtlqk", Arrays.asList(0));
        oldInfo.put("jrdqtlqk[]", 0);
        oldInfo.put("jrdqjcqk[]", 0);
        oldInfo.put("sfsqhzjkk", 1);
        oldInfo.put("sqhzjkkys", 1);
        oldInfo.put("sfqrxxss", 1);
        oldInfo.put("jcqzrq", "");
        oldInfo.put("gwszdd", "");
        oldInfo.put("szgjcs", "");
        /*oldInfo.put("ismoved", 0);
        oldInfo.put("sfymqjczrj", 0);
        oldInfo.put("szsqsfybl", 0);
        oldInfo.put("bztcyy", "1");
        oldInfo.put("geo_api_info", "{\"type\":\"complete\",\"info\":\"SUCCESS\",\"status\":1,\"VDa\":\"jsonp_935001_\",\"position\":{\"Q\":30.29673,\"R\":120.08594,\"lng\":120.08594,\"lat\":30.29673},\"message\":\"Get ipLocation success.Get address success.\",\"location_type\":\"ip\",\"accuracy\":null,\"isConverted\":true,\"addressComponent\":{\"citycode\":\"0571\",\"adcode\":\"330106\",\"businessAreas\":[{\"name\":\"西溪\",\"id\":\"330106\",\"location\":{\"Q\":30.271791,\"R\":120.09102999999999,\"lng\":120.09103,\"lat\":30.271791}},{\"name\":\"文新\",\"id\":\"330106\",\"location\":{\"Q\":30.28428,\"R\":120.10033900000002,\"lng\":120.100339,\"lat\":30.28428}}],\"neighborhoodType\":\"\",\"neighborhood\":\"\",\"building\":\"\",\"buildingType\":\"\",\"street\":\"余杭塘路\",\"streetNumber\":\"879号\",\"country\":\"中国\",\"province\":\"浙江省\",\"city\":\"杭州市\",\"district\":\"西湖区\",\"township\":\"三墩镇\"},\"formattedAddress\":\"浙江省杭州市西湖区三墩镇遵义西路浙江大学紫金港校区\",\"roads\":[],\"crosses\":[],\"pois\":[]}");
        oldInfo.put("address", "浙江省杭州市西湖区三墩镇遵义西路浙江大学紫金港校区");
        //oldInfo.put("id", 11404780);
        oldInfo.remove("created_uid");*/

        return JSON.toJSON(oldInfo).toString();
    }

    private String getRquestJson() {
        //String txt = "{\"sfymqjczrj\":\"0\",\"zjdfgj\":\"\",\"sfyrjjh\":\"0\",\"cfgj\":\"\",\"tjgj\":\"\",\"nrjrq\":\"0\",\"rjka\":\"\",\"jnmddsheng\":\"\",\"jnmddshi\":\"\",\"jnmddqu\":\"\",\"jnmddxiangxi\":\"\",\"rjjtfs\":\"\",\"rjjtfs1\":\"\",\"rjjtgjbc\":\"\",\"jnjtfs\":\"\",\"jnjtfs1\":\"\",\"jnjtgjbc\":\"\",\"sfqrxxss\":\"1\",\"sfqtyyqjwdg\":\"0\",\"sffrqjwdg\":\"0\",\"jrdqtlqk\":[\"0\"],\"sfhsjc\":\"0\",\"tw\":\"0\",\"sfcxtz\":\"0\",\"sfjcbh\":\"0\",\"sfcxzysx\":\"0\",\"qksm\":\"\",\"sfyyjc\":\"0\",\"jcjgqr\":\"0\",\"remark\":\"\",\"address\":\"浙江省宁波市余姚市凤山街道安山路191号科创中心2期\",\"geo_api_info\":{\"type\":\"complete\",\"info\":\"SUCCESS\",\"status\":\"1\",\"VDa\":\"jsonp_156747_\",\"position\":{\"Q\":30.07166,\"R\":121.18378000000001,\"lng\":121.18378,\"lat\":30.07166},\"message\":\"Get ipLocation success.Get address success.\",\"location_type\":\"ip\",\"accuracy\":null,\"isConverted\":true,\"addressComponent\":{\"citycode\":\"0574\",\"adcode\":\"330281\",\"businessAreas\":[],\"neighborhoodType\":\"\",\"neighborhood\":\"\",\"building\":\"\",\"buildingType\":\"\",\"street\":\"安山路\",\"streetNumber\":\"191号\",\"country\":\"中国\",\"province\":\"浙江省\",\"city\":\"宁波市\",\"district\":\"余姚市\",\"township\":\"凤山街道\"},\"formattedAddress\":\"浙江省宁波市余姚市凤山街道安山路191号科创中心2期\",\"roads\":[],\"crosses\":[],\"pois\":[]},\"area\":\"浙江省 宁波市 余姚市\",\"province\":\"浙江省\",\"city\":\"宁波市\",\"sfzx\":\"0\",\"sfjcwhry\":\"0\",\"sfjchbry\":\"0\",\"sfcyglq\":\"0\",\"gllx\":\"\",\"glksrq\":\"\",\"jcbhlx\":\"\",\"jcbhrq\":\"\",\"bztcyy\":\"\",\"sftjhb\":\"0\",\"sftjwh\":\"0\",\"sfjcqz\":\"\",\"jcqzrq\":\"\",\"jrsfqzys\":\"\",\"jrsfqzfy\":\"\",\"sfyqjzgc\":\"\",\"sfsqhzjkk\":\"1\",\"sqhzjkkys\":\"1\",\"gwszgzcs\":\"\",\"szgj\":\"\",\"fxyy\":\"\",\"jcjg\":\"\",\"date\":\"20200721\",\"uid\":\"54576\",\"created\":\"1595303594\",\"szsqsfybl\":\"0\",\"sfygtjzzfj\":\"0\",\"gtjzzfjsj\":\"\",\"id\":\"10558148\",\"gwszdd\":\"\",\"szgjcs\":\"\",\"ismoved\":\"0\"}";
        String txt = "{\"sfymqjczrj\":\"0\",\"zjdfgj\":\"\",\"sfyrjjh\":\"0\",\"cfgj\":\"\",\"tjgj\":\"\",\"nrjrq\":\"0\",\"rjka\":\"\",\"jnmddsheng\":\"\",\"jnmddshi\":\"\",\"jnmddqu\":\"\",\"jnmddxiangxi\":\"\",\"rjjtfs\":\"\",\"rjjtfs1\":\"\",\"rjjtgjbc\":\"\",\"jnjtfs\":\"\",\"jnjtfs1\":\"\",\"jnjtgjbc\":\"\",\"sfqrxxss\":\"1\",\"sfqtyyqjwdg\":\"0\",\"sffrqjwdg\":\"0\",\"jrdqtlqk\":\"[\\\"0\\\"]\",\"sfhsjc\":\"0\",\"tw\":\"0\",\"sfcxtz\":\"0\",\"sfjcbh\":\"0\",\"sfcxzysx\":\"0\",\"qksm\":\"\",\"sfyyjc\":\"0\",\"jcjgqr\":\"0\",\"remark\":\"\",\"address\":\"\\u6d59\\u6c5f\\u7701\\u5b81\\u6ce2\\u5e02\\u4f59\\u59da\\u5e02\\u51e4\\u5c71\\u8857\\u9053\\u5b89\\u5c71\\u8def191\\u53f7\\u79d1\\u521b\\u4e2d\\u5fc32\\u671f\",\"geo_api_info\":\"{\\\"type\\\":\\\"complete\\\",\\\"info\\\":\\\"SUCCESS\\\",\\\"status\\\":1,\\\"VDa\\\":\\\"jsonp_665426_\\\",\\\"position\\\":{\\\"Q\\\":30.07166,\\\"R\\\":121.18378000000001,\\\"lng\\\":121.18378,\\\"lat\\\":30.07166},\\\"message\\\":\\\"Get ipLocation success.Get address success.\\\",\\\"location_type\\\":\\\"ip\\\",\\\"accuracy\\\":null,\\\"isConverted\\\":true,\\\"addressComponent\\\":{\\\"citycode\\\":\\\"0574\\\",\\\"adcode\\\":\\\"330281\\\",\\\"businessAreas\\\":[],\\\"neighborhoodType\\\":\\\"\\\",\\\"neighborhood\\\":\\\"\\\",\\\"building\\\":\\\"\\\",\\\"buildingType\\\":\\\"\\\",\\\"street\\\":\\\"\\u5b89\\u5c71\\u8def\\\",\\\"streetNumber\\\":\\\"191\\u53f7\\\",\\\"country\\\":\\\"\\u4e2d\\u56fd\\\",\\\"province\\\":\\\"\\u6d59\\u6c5f\\u7701\\\",\\\"city\\\":\\\"\\u5b81\\u6ce2\\u5e02\\\",\\\"district\\\":\\\"\\u4f59\\u59da\\u5e02\\\",\\\"township\\\":\\\"\\u51e4\\u5c71\\u8857\\u9053\\\"},\\\"formattedAddress\\\":\\\"\\u6d59\\u6c5f\\u7701\\u5b81\\u6ce2\\u5e02\\u4f59\\u59da\\u5e02\\u51e4\\u5c71\\u8857\\u9053\\u5b89\\u5c71\\u8def191\\u53f7\\u79d1\\u521b\\u4e2d\\u5fc32\\u671f\\\",\\\"roads\\\":[],\\\"crosses\\\":[],\\\"pois\\\":[]}\",\"area\":\"\\u6d59\\u6c5f\\u7701 \\u5b81\\u6ce2\\u5e02 \\u4f59\\u59da\\u5e02\",\"province\":\"\\u6d59\\u6c5f\\u7701\",\"city\":\"\\u5b81\\u6ce2\\u5e02\",\"sfzx\":\"0\",\"sfjcwhry\":\"0\",\"sfjchbry\":\"0\",\"sfcyglq\":\"0\",\"gllx\":\"\",\"glksrq\":\"\",\"jcbhlx\":\"\",\"jcbhrq\":\"\",\"ismoved\":\"0\",\"bztcyy\":\"\",\"sftjhb\":\"0\",\"sftjwh\":\"0\",\"sfjcqz\":\"0\",\"jcqzrq\":\"\",\"jrsfqzys\":\"0\",\"jrsfqzfy\":\"0\",\"sfyqjzgc\":\"0\",\"sfsqhzjkk\":1,\"sqhzjkkys\":\"1\",\"gwszgzcs\":\"\",\"szgj\":\"\",\"fxyy\":\"\",\"jcjg\":\"\",\"created_uid\":0,\"date\":\"20200721\",\"uid\":\"54576\",\"created\":1595303594,\"id\":10558148}";
        // 修改日期
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        txt.replace("20200721", String.valueOf( Integer.parseInt(format.format(calendar.getTime()))));

        return txt;
    }

    private boolean registerHealth(String html) throws IOException {
        String json = getRquestJson(html);
        RequestBody body = RequestBody.create(JSON_TYPE, json);

        Request req = new Request.Builder()
                .url(REGISTER_POST_URL)
                .addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                //.addHeader("origin", "https://healthreport.zju.edu.cn")
                //.addHeader("referer", "https://healthreport.zju.edu.cn/ncov/wap/default/index?chInfo=ch_share__chsub_CopyLink")
                //.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.38 Safari/537.36")
                .post(body)
                .build();

        Response response = client.newCall(req).execute();
        System.out.println(response.body().string());
        return false;
    }

    private FormBody getFormBody() {
        String txt = "sfymqjczrj=0&zjdfgj=&sfyrjjh=0&cfgj=&tjgj=&nrjrq=0&rjka=&jnmddsheng=&jnmddshi=&jnmddqu=&jnmddxiangxi=&rjjtfs=&rjjtfs1=&rjjtgjbc=&jnjtfs=&jnjtfs1=&jnjtgjbc=&sfqrxxss=1&sfqtyyqjwdg=0&sffrqjwdg=0&jrdqtlqk%5B%5D=0&sfhsjc=0&tw=0&sfcxtz=0&sfjcbh=0&sfcxzysx=0&qksm=&sfyyjc=0&jcjgqr=0&remark=&address=浙江省宁波市余姚市凤山街道安山路191号科创中心2期&geo_api_info={\"type\":\"complete\",\"info\":\"SUCCESS\",\"status\":1,\"VDa\":\"jsonp_156747_\",\"position\":{\"Q\":30.07166,\"R\":121.18378000000001,\"lng\":121.18378,\"lat\":30.07166},\"message\":\"Get ipLocation success.Get address success.\",\"location_type\":\"ip\",\"accuracy\":null,\"isConverted\":true,\"addressComponent\":{\"citycode\":\"0574\",\"adcode\":\"330281\",\"businessAreas\":[],\"neighborhoodType\":\"\",\"neighborhood\":\"\",\"building\":\"\",\"buildingType\":\"\",\"street\":\"安山路\",\"streetNumber\":\"191号\",\"country\":\"中国\",\"province\":\"浙江省\",\"city\":\"宁波市\",\"district\":\"余姚市\",\"township\":\"凤山街道\"},\"formattedAddress\":\"浙江省宁波市余姚市凤山街道安山路191号科创中心2期\",\"roads\":[],\"crosses\":[],\"pois\":[]}&area=浙江省 宁波市 余姚市&province=浙江省&city=宁波市&sfzx=0&sfjcwhry=0&sfjchbry=0&sfcyglq=0&gllx=&glksrq=&jcbhlx=&jcbhrq=&bztcyy=&sftjhb=0&sftjwh=0&sfjcqz=&jcqzrq=&jrsfqzys=&jrsfqzfy=&sfyqjzgc=&sfsqhzjkk=1&sqhzjkkys=1&gwszgzcs=&szgj=&fxyy=&jcjg=&date=20200721&uid=54576&created=1595303594&szsqsfybl=0&sfygtjzzfj=0&gtjzzfjsj=&id=10558148&gwszdd=&szgjcs=&ismoved=0";
        String[] items = txt.split("&");
        Map<String, String> form = new HashMap<>();
        for(String item : items) {
            String[] words = item.split("=");
            if(words.length == 2) {
                form.put(words[0], words[1]);
            } else {
                form.put(words[0], "");
            }
        }

        // 修改日期
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        form.put("date", format.format(calendar.getTime()));


        FormBody.Builder builder = new FormBody.Builder();
        for(String k : form.keySet()) {
            builder.add(k, form.get(k));
        }

        return builder.build();
    }

}
