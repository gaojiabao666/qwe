/**
 * www.meditrusthealth.com Copyright © MediTrust Health 2017
 */

package com.xsqwe.admin.enums;



/**
 * <p>
 * </p>
 *
 * @author herendong
 * @version 1.0.0
 * @date 2018年6月29日 上午10:08:16
 */
public enum EpCoreAdminEnum implements FastEnum {

    ORDER_NOT_EXISTS("5X2000", "订单不存在"),

    ORDER_COMMODY_NOT_EXISTS("5X2001", "药品不存在"),

    NO_SEARCH_KEY_EXISTS("5X2002", "查询条件不存在"),

    USER_NOT_EXISTS("5X2010", "用户不存在"),

    OBECCT_IS_BLANK("5X2019", "参数对象为null"),

    ORDER_STATUS_EXCEPTION("4x1151", "订单状态异常"),

    USER_STATUS_EXCEPTION("4x1152", "用户状态异常"),

    USER_STATUS_NOT_MESSAGE("4x1153", "当前状态不允许推送"),

    USER_STATUS_NOT_CANCEL("4x1154", "当前状态不允许取消"),

    HOSPITAL_YES_EXIST("4X1155", "该医院名称已存在"),

    HOSPITAL_NOT_SET("4X1156", "该订单医院未填写"),

    HOSPITAL_NOT_EXIST("4X1157", "该医院不存在"),

    UPLOAD_FILE_FAIL("4X1158", "上传sftp文件失败"),

    CPMPRESS_FILE_FAIL("4X1159", "压缩文件失败"),

    REQ_IS_EXIST("4X1160", "该订单正在操作中！请稍后查看"),

    INSURANCE_APPLY_USER_LIMIT("4X1161", "出单人数少于三人"),

    AN_SHENG_POLICY_IS_ERROR("4X1162", "上期投保保单还未生成，请稍后重试！"),

    INSURANCE_RECEIVE_IS_ERROR("4X1163", "报销数据错误"),

    ADDRESS_INFO_EXCEPION("4X1164", "地址信息异常"),

    POLICY_IS_NOT_EXIST("4X1165", "请先完成出单"),

    AN_SHENG_IS_ERROR("4X1166", "安盛请求失败，请联系客服"),

    FILE_DATA_NOT_EXIST("4X1167", "文件不存在"),

    BAR_CODE_NOT_EXIST("4X1168", "药监码不存在"),

    INSURANCE_GROUP_NOT_EXITST("4X1169", "入组订单不存在"),

    INSURANCE_RECEIVE_NOT_EXITST("4X1170", "报销订单不存在"),

    DATA_NOT_EXIST("4X1171", "数据不存在"),

    CREDIT_ACCOUNT_ABNORMALITY("4X1172", "账户额度异常"),

    CAPITAL_CHANNEL_EXCEPTION("4X1173", "资金通道异常"),

    REPAYMENT_BILL_NOT_EXIST("4X1174", "分期账单不存在"),

    REPAY_SPLIT_ERROR("4X1175", "拆单期数不符合要求，请更改期数"),

    NOT_EXIST_FINANCE_APPLICATION("4X1176", "金融订单不存在"),

    NOT_EXIST_USER_REPAYMENT("4X1177", "用户还款计划不存在"),

    NOT_SIGN_MAIN_CONTRACT("4X1178", "用户未签署主合同"),

    FINANCE_ACCOUNT_NOT_EXIST("4X1179", "个人渠道账户不存在"),

    YUNXIN_CREATE_MAIN_CONTRACT("4X1180", "云信创建主合同失败"),

    YUNXIN_QUERY_MAIN_CONTRACT_FAIL("4X1181", "云信查询无结果"),

    CONTRACT_NUMBER_NOT_EXIST("4X1182", "合同号不存在"),

    NOT_EXIST_CONTRACT("4X1183", "合同不存在"),

    NOT_EXIST_PHARMACY_BILL("4X1184", "药店账单不存在"),

    YUNXIN_EXIST_SUB_CONTRACT("4X1185", "云信子合同已创建,请勿重复创建"),

    NOT_EXIST_MAIN_CONTRACT("4X1186", "主合同未创建"),

    NOT_EXIST_PHARMACY_BANK_INFO("4X1187", "该药店确少银行卡信息"),

    NOT_EXIST_PHARMACY_BANK_REGION("4X1188", "银行归属地不存在"),

    YUNXIN_BANK_CODE_CONVERT_FILE("4X1189", "云信银行Code转换失败"),

    YUNXIN_CREATE_SUB_CONTRACT("4X1190", "云信创建子合同失败"),

    CREDIT_ACCOUNT_NOT_EXIST("4X1191", "账户信息不存在"),

    ADDRESS_NOT_FOUND("4X1192", "地址信息不存在"),

    YUNXIN_NOT_EXIST_SUB_CONTRACT("4X1193", "子合同未创建"),

    SEAL_PDF_COPY_EXCEPTION("4X1194", "模板拷贝错误"),

    SEAL_IMAGE_NOTEXIST_EXCEPTION("4X1195", "用户签名图片不存在"),

    CHECK_DESC_IS_NULL("4X1196", "请录入审核不通过原因"),

    SEAL_CONTRACT_NOT_EXIST("4X1197", "合同模板不存在或路径错误"),

    YUNXIN_PAYMENT_QUERY_NO_REPONSE("4X1198", "云信放款查询无结果"),

    YUNXIN_PAYMENT_FAIL("4X1199", "云信放款失败"),

    USER_DATA_NOT_EXISTS("4X1200", "用户数据不存在"),

    PHARMACY_BILL_EXCEPTION("4X1201", "药房账单状态异常"),

    YUNXIN_PAYMENT_HAS_NO_REPONSE("4X1202", "放款云信无结果"),

    YUNXIN_QUERY_REPAYMENT_NO_REPONSE("4X1203", "云信查询还款计划无结果"),

    YUNXIN_REPAYMENT_NOT_EXIST("4X1204", "云信未查询到还款计划"),

    YUNXIN_REPAYMENT_DEDUCT_FIAL("4X1205", "云信扣款失败"),

    YUNXIN_REPAYMENT_SYSTEM_NOT_IDENTICAL("4X1206", "云信还款计划和系统不一致"),

    SEAL_PDF_IO_EXCEPTION("4X1207", "文件IO流读写错误"),

    REPAYMENT_STATUS_IS_NOT_DEDUCTED("4X1208", "状态不为代扣款"),

    NOT_EXIST_USER_BANK_CARD("4X1209", "用户银行卡信息不存在"),

    CREDIT_ACCOUNT_DEAL_ABNORMALITY("4X1210", "账户流水异常"),

    ACCOUNT_IDFRONTFILE_NOT_EXIST("4X1211", "身份证正面文件不存在"),

    ACCOUNT_IDBACKFILE_NOT_EXIST("4X1212", "身份证反面文件不存在"),

    ACCOUNT_IDHANDFILE_NOT_EXIST("4X1213", "手持身份证文件不存在"),

    ACCOUNT_IDFRONTFILE_PATH_NOT_EXIST("4X1214", "身份证正面文件不存在或路径错误"),

    ACCOUNT_IDBACKFILE_PATH_NOT_EXIST("4X1215", "身份证反面文件不存在或路径错误"),

    ACCOUNT_IDHANDFILE_PATH_NOT_EXIST("4X1216", "手持身份证文件不存在或路径错误"),

    ACCOUNT_SUBCONTRACT_PATH_NOT_EXIST("4X1217", "子合同文件不存在或路径错误"),

    ACCOUNT_WITHHOLD_PATH_NOT_EXIST("4X1218", "代扣协议文件不存在或路径错误"),

    FINANCE_ORDER_CONTRACTNUM_NOT_EXIST("4X1219", "订单合同号不存在"),

    PATIENT_SUPPORT_SERVICE_NOT_EXIST("4X1220", "患者支持服务协议不存在"),

    PAYMENT_CONTRACT_NOT_EXIST("4X1221", "划款指示合同不存在"),

    SERVICE_CONTRACT_NOT_EXIST("4X1222", "分期合同不存在"),

    PATIENT_SQWTS_SERVICE_NOT_EXIST("4X1223", "患者授权委托书不存在"),

    HDXD_APPLE_NOT_EXIST("4X1224", "华东小贷申请人表已创建,请勿重复创建"),

    SERVICE_FILE_NOT_EXIST("4X1225", "服务协议不存在"),

    AUTH_FILE_NOT_EXIST("4X1226", "投保授权确认书不存在"),

    NOTICE_FILE_NOT_EXIST("4X1227", "投保须知不存在"),

    ANRONG_CONTRACT_NOT_EXIST("4X1228", "安融合同不存在"),

    BILL_RECONCILIATION_NOT_EXIST("4X1229", "对账订单不存在"),

    PHONENO_ACCOUNT_ERROR("4X1230", "账户不存在"),

    DETECTION_CODE_NOT_EXIST("4X1231", "请输入正确的核销码"),

    DETECTION_ORDER_NOT_EXIST("4X1229", "基因检测订单不存在"),

    MX_SERIAL_NUMBER_EXIST("4X1232", "该镁信财务反馈流水单号已存在,请勿重复创建"),

    PAYMENT_METHOD_IS_WRONG("4X1233", "支付方式错误"),

    INVOICE_TO_REPEAT("4X1234", "发票重复"),

    INVOICE_TO_NUll("4X1235", "发票编号为空");



    private String code;
    private String message;

    private EpCoreAdminEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static EpCoreAdminEnum getEnumByCode(String code) {
        for (EpCoreAdminEnum epCoreAdminEnum : EpCoreAdminEnum.values()) {
            if (epCoreAdminEnum.getCode().equals(code)) {
                return epCoreAdminEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("EpCoreAdminEnum name = %s code = %s message = %s", this.name(), code, message);
    }
}
