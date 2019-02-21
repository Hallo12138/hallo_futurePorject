import com.icbc.api.*;
import com.icbc.api.request.*;
import com.icbc.api.request.SettlementAccountBalanceQueryRequestV1.*;
import com.icbc.api.response.*;
import com.icbc.api.request.SettlementAccountDetailQueryRequestV1.*;
import com.icbc.api.request.QrcodeGenerateRequestV2.QrcodeGenerateRequestV2Biz;
import java.util.Date;

public class Communication {//通讯单元，负责所有和工行API的访问
    String APPID_sandbox = "IICAMP0000000003";
    String public_key_sandbox = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwFgHD4kzEVPdOj03ctKM7KV+16bWZ5BMNgvEeuEQwfQYkRVwI9HFOGkwNTMn5hiJXHnlXYCX+zp5r6R52MY0O7BsTCLT7aHaxsANsvI9ABGx3OaTVlPB59M6GPbJh0uXvio0m1r/lTW3Z60RU6Q3oid/rNhP3CiNgg0W6O3AGqwIDAQAB";
    String private_key_sandbox = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXxiVvxBE5SCIhxJ65OkRzFOB2VF9QpM5vgttAgIJHLV7o47HWeCiOEBnIw4qjtFQXnDAEZL7vFM/RW7XnWdHnpPDIRHtqAfFL6lkBvVR4fit1pquqHq7IWTj1OU0Sv3q55aiY7Mtkr4r6RwXQcM+wlTBGXzFLL8O18mVih7ARa1hD3uUPoPdRdPDoLVdm9SOTD/1PPB18pyHzCLYE8swDT4i47hnZgeFoDm2aQ11rVoe8q2Pvi2zc4GuqT+YWJMFP4r3kWd3vFGga0Q0Sww9rYPH8oV9gJ268ntTATV+Lq5m03KH99w+1QdwTwFtAQbTmzTgUYjAKFHSofoyHtlg5AgMBAAECggEAbZJ94Iy6qpIrIhnjRXKNjE/cJVGQZpY0+0iLpauhYmWdCaGo+F9TWAzDsK+LXdiIhWsbN3DMb6qkxk7hqp6r7gKkWtEmEEhKvjX4AEuBumPMWGn1sSHpqXbQqimuPQTEQ4VMDOuXg59ARKQVzIBFUQTED2oWGmPndWoES/gN4uRttv6p4I86OyL7xWasdXlUpmCAF1ZrFsXGOeJ2RMf3NzBaWhNBYL7BhxYSEelOrtwm137srM99PyqZyeLbQWprWHOTy1Yd2adwZCZgh3cdttZxen1pxjUR4En4kSzzTKO9KZnM0WgCo63Yg99x95qXB/FG2W/8YkSwRTnqdyB9iQKBgQDqXT89sp/lpM4wDhhYdEgm8r/awl0e1NE0K6EfconlZYKFec1CQ5oGTGaSH63qMRwK9cVzgqvRuCa/2xGVIkJZwtWWCrFqe8zMHbDxj2GAL7i+B1q8cr8WyL+RcpOsaR/gmMEb3+LML5VkqcLxbkVpTlF+ty7qOiR9YTH2uCYZAwKBgQClyQk+VfQZz2GZpOdUpuqzPNOOsElYKcM5Y6vvT5N2C/mcHflQlu7YUSXM6ddWgbsypFjUBVABvKLEnsVvlhvL8k0vIMa2OBdH89549MeWXVJCIQWOOm3fcGTWL5+Cc7jDMucf8mZao2AnSJ3954RWZzvaM/HkKMZNCbbJjX5/EwKBgQDeV26q+u7FcBAI+SZRrKfTA5POV3z+xgpfg1DNw91dG2uzc7WuuvHCLfNVh+Z3+vVO1JHpnzugsCNa+tt4b9Mg0z3MpgJhMmg98aEv2sY2VY/gAJwtknMFi5hqxZeiBu2uJ111M3c4HaCOqpt+bw489IdOPz2sC1MY+//pwBSsvwKBgAgfaHeIaJRXJ9YKUVdBcmBjy43DL2TFbw5BxuvLv1LaVVmD0nJTYtJwb2Sx1F4lrxFVIhEJTZk4L9VoXg4Qkb4VWhjEB1hoOSPKXA0bGShR3DTnP0lvrXB0JkykOrF+j//oX5CeJMuuE4j5Mtx82mwNVM5lGyXtpYGixzTq3NbHAoGAT538j1K+tAejuTQYtnqcqIYizxkduF/2fg9oRVogFhmz5xB/giHzpKzBtjgqfColNKRPodNye9LWCIHjmVO0I/raOg1kSvkK4avfLW9D0ljDTmSRdjkm52Qpmk62eoFCR9jNO3wU6ooHQ/jgzG4vdxS8ZBG2b40BE3bXvPC8oKY=";
    DefaultIcbcClient sandbox_client = new DefaultIcbcClient(APPID_sandbox, private_key_sandbox, public_key_sandbox);


    String APPID_2 = "IICAMP0000000802";
    String public_key_2 ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn3jYSuHsmYXZuKqtYPu6OEqAPVXVHYz6OoBkwsbX24A9vcBss/6/bGBY09P46BVNmGvPyhGZ35YTwjBk7rJ5p4x+ZOdoQe/pluDk0u0kpYf+6DkteIkOtqNl2ju3bnjpHRgs+kRR/d8KLRJvgAiMk8gVp44VOMUw+Uns1NdSVibL0+pwKDbFg7rONU1bm/kxy5ytA5LKGNmZ1YQQ7TSdDVbnKcE5jLNFPu+bKWV2CQ5ErDnbTyW1ZGBJ1pjow/kRNjsPat3fHkzBV8wmH4zG0XyQnJYDI1qKWKsEg65aGNXvKQMh8sTweVpj4dIqXY1ZbiXBsNc/CSUt423jpRnygQIDAQAB";
    String private_key_2 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCfeNhK4eyZhdm4qq1g+7o4SoA9VdUdjPo6gGTCxtfbgD29wGyz/r9sYFjT0/joFU2Ya8/KEZnflhPCMGTusnmnjH5k52hB7+mW4OTS7SSlh/7oOS14iQ62o2XaO7dueOkdGCz6RFH93wotEm+ACIyTyBWnjhU4xTD5SezU11JWJsvT6nAoNsWDus41TVub+THLnK0DksoY2ZnVhBDtNJ0NVucpwTmMs0U+75spZXYJDkSsOdtPJbVkYEnWmOjD+RE2Ow9q3d8eTMFXzCYfjMbRfJCclgMjWopYqwSDrloY1e8pAyHyxPB5WmPh0ipdjVluJcGw1z8JJS3jbeOlGfKBAgMBAAECggEASwidHH64yHOGQbFs6pkGwb2sIS/lmoXepvAKeOr+XbU1V7bWbsqKXv/mS6UNFIYRgIoZtsYZVG5U1aXXl8g6HW+peOi40W2Exw0CHYyfFZOriLB+iJxUY/L11X0LjdsuNBZP82F6Aq2TKCnodKGRI2edaL0dlxyRMnpqCKtXflj8FtYQoB5vv1fY68wjRg7ZE/4O047d/QdyWiOYBnGPXSkOmJZ9+O36p6nJI5RBbK1Uj8vs1kqUdvk2Uf0t00s8XXJb0XqD4bKRvDS9y7z7uZAmuBZfI+q/UChsCbsXxuUfyaTgvPZyThB9yvlwm06GAAeT1xyxbp+2sDElqaiWEQKBgQDZ/ad63Xmu8LxQlh7AZ3YOyEjfZME1uPVQ1m1nLOnpIdUdRPouaw3foBtaoDypKSQWh2ORTHkQMCqc/RD1m1X1+p44xl2Jf7Mz5AIRk2r273ZjquvD4/D3GAOTm9EYk5ovcqIwbsVdMIKSDMyRu7TgEZ7PWa2eraFoxM6BgP2XKwKBgQC7Rx4RkSWxvXhtldeCcm1tCwns44uH/ScSSjBQmqdwBTTZDlhGFJADtbSqKK3BrQRv/GLA4GAcgdX+DL3qDktfs7QB0HMBvAjvyoqJb3NXjKXFRE9/BWppa22XV7dA5/x/qh6S/ZRR3FRZwbOCee/Xc5fzP3sdOZmTo/yuqX0HAwKBgQCJXdA2Gm5dkRH57j9+mzPH6OoS69jc06qywAy84Y6c/FbhVOAadBeP/cJ+/056gpRdJf/WRcxcKR4RQ192heZLVRxzRn0W8kOoxVuQW8chyJBgCgr7rB3id115nMzMoeadU61ku8jfCnydMDeQ9inqsO/UVfyUa3G1JZ/D99bVdQKBgD+TXPnJtGoqT/+jpo16Gvuxi6UHeg6oOeNTnCNA3SaCHfaxj/X6dQlNXg+Fz7a37ZG2tC+V/AqxaNvdp3sVbzEF6E3VXbVxB/efYnhVhWprLLtS/Um5pO40sfd2p5c5KdaNAeQUgY1oppnxz+azTmBttV+2ZSz+qMweF2GT2AXRAoGAV3aXPWj531nfbe+LJO8f4Ds+IZUzlI3HN8IDJeXcPUIo7ghXxW7Ka91pd6cM2k2xXqZgJH19AA8TScfgiJzsE9c3xz1UioOwT2Vhc5u4LJNPvj+jlNSbmBeoFeW8UI2h6CjDdBbkIQxASfo16+atZYVgQ9vL7NDPmNPVd3CFs/E=";

    DefaultIcbcClient special_client = new DefaultIcbcClient(APPID_2,private_key_2,public_key_sandbox);

    String special = "https://gw-api-iicamp.dccnet.com.cn";
    String sandbox = "https://apisandbox.dccnet.com.cn";
    String look_up_balance(String card_num) throws IcbcApiException {
        Date date = new Date();
        String time = date.toLocaleString();
        SettlementAccountBalanceQueryRequestV1 request = new SettlementAccountBalanceQueryRequestV1();
        request.setServiceUrl(sandbox+"/api/mybank/enterprise/account/qaccbal/V1");
        SettlementAccountBalanceQueryRequestV1Biz bizContent = new SettlementAccountBalanceQueryRequestV1Biz();
        bizContent.setCorpNo("0000000003");
        bizContent.setTrxAccDate(transfer_date(time.split(" ")[0], "Date"));
        bizContent.setTrxAccTime(transfer_date(time.split(" ")[1], "Time"));
        bizContent.setCorpDate(transfer_date(time.split(" ")[0], "Date"));//日期
        bizContent.setMediumId(card_num);//卡号
        bizContent.setCcy(1); //币种
        //bizContent.setSecretKey(private_key);
        request.setBizContent(bizContent);
        SettlementAccountBalanceQueryResponseV1 response = sandbox_client.execute(request, "look_up");
        if (response.isSuccess() && response.getReturnCode() == 0) {
            //成功
            System.out.println(response.getAccountBalance());//
            System.out.println(response.getHoldBalance());//

            String result = new String("查询时间：");
            result += time + "\n";
            result += "余额：";
            if(response.getAccountBalance()==null) {
                result += "0";
            }
            else {
                result += response.getAccountBalance();
            }
            result += "\n冻结不可用金额：";
            if(response.getHoldBalance()==null) {
                result +="0";
            }
            else {
                result += response.getHoldBalance();
            }
            System.out.println(result);
            return result += "\n";
        } else {
            //失败
            System.err.println(response.getReturnCode());
            System.err.println(response.getReturnMsg());
            return "error";
        }
    }

    String transfer_date(String date, String type) {
        String res = new String();
        if (type.equals("Date")) {
            res += date.split("年")[0];
            res += date.substring(date.indexOf("年") + 1, date.indexOf("月"));
            res += date.substring(date.indexOf("月") + 1, date.indexOf("日"));
            return res;
        } else if (type.equals("Time")) {
            res += date.substring(date.indexOf("午") + 1, date.indexOf(":"));
            res += date.split(":")[1] + date.split(":")[2];
            return res;
        }
        return null;
    }

    String look_up_record(String card_num, String from, String to) throws IcbcApiException {
        Date date = new Date();
        String time = date.toLocaleString();
        SettlementAccountDetailQueryRequestV1 request = new SettlementAccountDetailQueryRequestV1();
        request.setServiceUrl(sandbox+"/api/mybank/enterprise/trade/qhisd/V1");
        SettlementAccountDetailQueryRequestV1Biz bizContent = new SettlementAccountDetailQueryRequestV1Biz();
        bizContent.setCorpNo("0000000003"); //合作方机构编号
        bizContent.setTrxAccDate(transfer_date(time.split(" ")[0], "Date"));
        bizContent.setTrxAccTime(transfer_date(time.split(" ")[1], "Time"));
        bizContent.setCorpDate(transfer_date(time.split(" ")[0], "Date"));//日期
        bizContent.setOutServiceCode("1"); //外部服务代码
        bizContent.setMediumId(card_num); //工行联名卡号
        bizContent.setCcy(1); //币种
        bizContent.setBeginDate(from); //开始日期
        bizContent.setEndDate(to); //结束日期
        bizContent.setQueryMode(1); //查询方式1-首次查询2-上一页3-下一页
        bizContent.setPage(1); //页码（首次查询送1，下一页加1，上一页减1）
        bizContent.setPnBusidate("2017-08-23"); //上次入账日期（翻页时必输，查询上页送上页查询结果第一条记录的入账日期，查询下页送最近一次查询结果最后一条记录的入账日期）
        bizContent.setPnRowRecord("12344566"); //翻页条件记录（翻页时必输，查询上页送上页查询结果第一条记录的翻页条件，查询下页送最近一次查询结果最后一条记录的翻页条件）
        //bizContent.setSecretKey("ASDQWEQDZCSDFAWWQDA");
        //bizContent.setMediumIdHash("SDFDFHTEWTGDFWADADAFSDGSESEFD");
        request.setBizContent(bizContent);
        SettlementAccountDetailQueryResponseV1 response = sandbox_client.execute(request, "record");
        if (response.isSuccess() && response.getReturnCode() == 0) {
            // 业务成功处理
            String result = new String("查询时间：");
            result += time + "\n";
            result += "订单记录数：" + response.getRecordNum();//订单记录数
            if(response.getOrderDetail()==null) {
                result+="选择时间段没有交易记录\n";
            }
            else {
                result += "明细：" + response.getOrderDetail();//订单明细
            }
            System.out.println(result);
            return result;
        } else {
            // 失败
            System.out.println("error");
            return "error";
        }
    }
    String generate_QR_code() throws IcbcApiException {
        Date date = new Date();
        String time = date.toLocaleString();

        QrcodeGenerateRequestV2 request = new QrcodeGenerateRequestV2();
        request.setServiceUrl(sandbox+"/api/qrcode/V2/generate");
        QrcodeGenerateRequestV2Biz bizContent = new QrcodeGenerateRequestV2Biz();
        bizContent.setMerId("020002040095");
        bizContent.setStoreCode("02000015087");
        bizContent.setOutTradeNo("ZHL777O15002039");
        bizContent.setOrderAmt("7370");
        bizContent.setTradeDate(transfer_date(time.split(" ")[0], "Date"));//日期);
        bizContent.setTradeTime(transfer_date(time.split(" ")[1],"Time"));
        bizContent.setPayExpire("1200");
        bizContent.setTporderCreateIp("127.0.0.1");
        bizContent.setNotifyFlag("1");
        request.setBizContent(bizContent);
        QrcodeGenerateResponseV2 response = new QrcodeGenerateResponseV2();
        response = sandbox_client.execute(request, "msgId");
        if (response.isSuccess()) {
            // 业务成功处理
            System.out.println("ReturnCode:"+response.getReturnCode());
            System.out.println("response:" + response);
            return response.getQrcode();
        } else {
            // 失败
            System.out.println("ReturnCode:" + response.getReturnCode());
            System.out.println("ReturnMsg:" + response.getReturnMsg());
            return "0";
        }
    }
    String generate_pay_QRcode(String pay_num, String amount) throws IcbcApiException {
        Date date = new Date();
        String time = date.toLocaleString();
        QrcodePayRequestV2 request = new QrcodePayRequestV2();
        request.setServiceUrl(sandbox+"/api/qrcode/V2/pay");
        QrcodePayRequestV2.QrcodePayRequestV2Biz bizContent = new QrcodePayRequestV2.QrcodePayRequestV2Biz();
        bizContent.setQrCode(pay_num);
        bizContent.setMerId("020002040095");
        bizContent.setOutTradeNo("ZHL777O15002039");
        bizContent.setOrderAmt(amount);
        bizContent.setTradeDate(transfer_date(time.split(" ")[0], "Date"));
        bizContent.setTradeTime(transfer_date(time.split(" ")[1], "Time"));
        request.setBizContent(bizContent);
        QrcodePayResponseV2 response;
        response = sandbox_client.execute(request, "msgId");
        if (response.isSuccess()) {
            // 业务成功处理
            System.out.println("ReturnCode:"+response.getReturnCode());
            System.out.println("response:" + response);
            if(response.getReturnCode()==0) {
                return "支付成功";
            }
        } else {
            // 失败
            System.out.println("ReturnCode:"+response.getReturnCode());
            System.out.println("ReturnMsg:"+response.getReturnMsg());
            return "支付失败";
        }
        return "0";
    }
    void password_free(String num) throws IcbcApiException {//小额免密API
        /*UiIcbcClient client = new UiIcbcClient(APPID,IcbcConstants.SIGN_TYPE_RSA2,private_key, IcbcConstants.CHARSET_UTF8);
        B2cPassfreeAgreementSignRequestV1 request = new B2cPassfreeAgreementSignRequestV1();
        request.setServiceUrl("https://gw-api-iicamp.dccnet.com.cn/ui/b2c/passfree/agreement/sign/V1");
        B2cPassfreeAgreementSignRequestV1.B2cPassfreeAgreementSignRequestV1Biz bizContent = new B2cPassfreeAgreementSignRequestV1.B2cPassfreeAgreementSignRequestV1Biz();
        bizContent.setExternalId("123456789005266238");
        bizContent.setSignValidityPeriod("12m");
        request.setBizContent(bizContent);
        String result = client.buildPostForm(request);
        System.out.println(result);*/
        UiIcbcClient client = new UiIcbcClient(APPID_2, IcbcConstants.SIGN_TYPE_RSA2,private_key_2, IcbcConstants.CHARSET_UTF8);
        B2cPassfreeAgreementSignRequestV1 request = new B2cPassfreeAgreementSignRequestV1();
        request.setServiceUrl(special+"/ui/b2c/passfree/agreement/sign/V1");
        B2cPassfreeAgreementSignRequestV1.B2cPassfreeAgreementSignRequestV1Biz bizContent = new B2cPassfreeAgreementSignRequestV1.B2cPassfreeAgreementSignRequestV1Biz();
        bizContent.setExternalId("123456789005266238");
        bizContent.setSignValidityPeriod("12m");

        request.setBizContent(bizContent);
        String result = client.buildPostForm(request);
        System.out.println(result);
    }
    String addFace(String img_base64,String id) throws IcbcApiException {//人脸注册
        //String img_base64 = ImgBase64.GetImageStr(file);
        DefaultIcbcClient client = new DefaultIcbcClient(APPID_2, private_key_2, public_key_sandbox);
        BasFaceFacesaddRequestV1 request=new BasFaceFacesaddRequestV1();
        request.setServiceUrl(special+"/api/bas/face/facesadd/V1");
        BasFaceFacesaddRequestV1.BasFaceFacesaddRequestV1Biz bizContent=new BasFaceFacesaddRequestV1.BasFaceFacesaddRequestV1Biz();
        bizContent.setImg1(img_base64);
        bizContent.setId(id);
        bizContent.setBaseFlag(0);
        bizContent.setChannel("TEST");
        bizContent.setTrCode("1");
        bizContent.setAppName("F-TEST");
        bizContent.setAppInfo("nihao");
        bizContent.setPostMethod("0");
        bizContent.setRetentionTime("2017-01-02 11:03:20");
        request.setBizContent(bizContent);
        BasFaceFacesaddResponseV1 responseV1 = new BasFaceFacesaddResponseV1();
        responseV1 = client.execute(request,"123");
        if(responseV1.getReturnCode()==0) {
            System.out.println("人脸注册成功");
            return responseV1.getFaceId();
        }
        return "error";
    }
    String addFaceGroup(String img_base64,String id) throws IcbcApiException {//人脸注册并分库API
        //String img_base64 = ImgBase64.GetImageStr(file);
        DefaultIcbcClient client = new DefaultIcbcClient(APPID_2, private_key_2, public_key_sandbox);
        BasFaceFacesaddandgroupingRequestV1 request=new BasFaceFacesaddandgroupingRequestV1();
        request.setServiceUrl(special+"/api/bas/face/facesaddandgrouping/V1");
        BasFaceFacesaddandgroupingRequestV1.BasFaceFacesaddandgroupingRequestV1Biz bizContent=new BasFaceFacesaddandgroupingRequestV1.BasFaceFacesaddandgroupingRequestV1Biz();
        bizContent.setImg1(img_base64);
        bizContent.setId(id);
        bizContent.setBaseFlag(0);
        bizContent.setChannel("TEST");
        bizContent.setTrCode("BAS20180317121314328421313");
        bizContent.setAppName("F-TEST");
        bizContent.setAppInfo("工银e办公");
        bizContent.setPostMethod("0");
        bizContent.setBranchId("OA-F-SSTS");
        bizContent.setBranchName("F-SSTS");
        bizContent.setType(0);
        bizContent.setRetentionTime("2017-01-02 11:03:20");
        request.setBizContent(bizContent);
        BasFaceFacesaddandgroupingResponseV1 responseV1 = new BasFaceFacesaddandgroupingResponseV1();
        responseV1 = client.execute(request,"123");
        if(responseV1.getReturnCode()==0) {
            System.out.println("人脸分库注册成功");
            return responseV1.getFaceId();
        }
        return "error";
    }
    boolean verifyFace(String img_base64,String id) throws IcbcApiException {//人脸一对一验证api
        //String img_base64 = ImgBase64.GetImageStr(file);
        DefaultIcbcClient client = new DefaultIcbcClient(APPID_2, private_key_2, public_key_sandbox);
        BasFacePersoncheckRequestV1 request=new BasFacePersoncheckRequestV1();
        request.setServiceUrl(special+"/api/bas/face/personcheck/V1");
        BasFacePersoncheckRequestV1.BasFacePersoncheckRequestV1Biz bizContent=new BasFacePersoncheckRequestV1.BasFacePersoncheckRequestV1Biz();
        bizContent.setImg1(img_base64);
        bizContent.setThreshold("0.01");
        bizContent.setId(id);
        bizContent.setChannel("TEST");
        bizContent.setTrCode("2");
        bizContent.setAppName("F-TEST");
        bizContent.setAppInfo("nihao");
        bizContent.setPostMethod("0");
        request.setBizContent(bizContent);
        BasFacePersoncheckResponseV1 responseV1 = new BasFacePersoncheckResponseV1();
        responseV1 = client.execute(request,"123");

        double sim_result = responseV1.getSim();
        if(sim_result>95) {
            System.out.println("success");
            return true;
        }
        else {
            System.out.println("fail");
            return false;
        }
    }

    String face_login(String img1_base64) throws IcbcApiException {//人脸一对多验证
        DefaultIcbcClient client = new DefaultIcbcClient(APPID_2, private_key_2, public_key_sandbox);
        BasFacePersonbranchsearchRequestV1 request = new BasFacePersonbranchsearchRequestV1();
        request.setServiceUrl(special+"/api/bas/face/personbranchsearch/V1");
        BasFacePersonbranchsearchRequestV1.BasFacePersonbranchsearchRequestV1Biz bizContent = new BasFacePersonbranchsearchRequestV1.BasFacePersonbranchsearchRequestV1Biz();
        bizContent.setImg1(img1_base64);
        bizContent.setChannel("TEST");
        bizContent.setBranchId("OA-F-SSTS");
        bizContent.setTrCode("BAS20180317121314328421313");
        bizContent.setAppName("F-TEST");
        bizContent.setAppInfo("工银e办公");
        bizContent.setPostMethod("0");
        request.setBizContent(bizContent);
        BasFacePersonbranchsearchResponseV1 response;
        response = client.execute(request,"1234");
        if(response.getUserInfos()==null) {
            System.out.println("无匹配项目,非法用户");
            return "error";
        }
        else {
            System.out.println(response.getUserInfos());
            return response.getUserInfos().get(0).toString();
        }
    }
    double compareFace(String img1_base64, String img2_base64) throws IcbcApiException {//人脸相似度比较
        //String img2_base64 = ImgBase64.GetImageStr(file2);
        //String img1_base64 = ImgBase64.GetImageStr(file1);
        DefaultIcbcClient client = new DefaultIcbcClient(APPID_2, private_key_2, public_key_sandbox);
        BasFaceFacescompareRequestV1 request=new BasFaceFacescompareRequestV1();
        request.setServiceUrl(special+"/api/bas/face/facescompare/V1");
        BasFaceFacescompareRequestV1.BasFaceFacescompareRequestV1Biz bizContent=new BasFaceFacescompareRequestV1.BasFaceFacescompareRequestV1Biz();
        bizContent.setImg1(img1_base64);
        bizContent.setImg2(img2_base64);
        bizContent.setChannel("TEST");
        bizContent.setTrCode("2");
        bizContent.setAppName("F-TEST");
        bizContent.setAppInfo("nihao");
        bizContent.setPostMethod("0");
        request.setBizContent(bizContent);
        BasFaceFacescompareResponseV1 responseV1 = new BasFaceFacescompareResponseV1();
        responseV1 = client.execute(request,"123");
        return responseV1.getSim();
    }
    public static void main(String[] args) throws IcbcApiException {
        Communication communication = new Communication();
        //communication.addFace(ImgBase64.GetImageStr("face.jpeg"),"100007");
        //communication.addFace(ImgBase64.GetImageStr("test.jpeg"),"100008");
        //communication.addFaceGroup(ImgBase64.GetImageStr("test.jpeg"),"100008");
        /*if(communication.verifyFace(ImgBase64.GetImageStr("face.jpeg"))) {
            System.out.println("yes");
        }
        if(communication.verifyFace(ImgBase64.GetImageStr("test.jpeg"))) {
            System.out.println("yes");
        }
        System.out.println(communication.compareFace(ImgBase64.GetImageStr("face.jpeg"),ImgBase64.GetImageStr("face.jpeg")));
        System.out.println(communication.compareFace(ImgBase64.GetImageStr("face.jpeg"),ImgBase64.GetImageStr("test.jpeg")));
        //communication.look_up_balance("6212880200000000139");*/
        //communication.face_login(ImgBase64.GetImageStr("test.jpeg"));
        //communication.look_up_record("6212880200000000139","180102","181103");
        communication.verifyFace(ImgBase64.GetImageStr("test.jpeg"),"100007");
    }
}
