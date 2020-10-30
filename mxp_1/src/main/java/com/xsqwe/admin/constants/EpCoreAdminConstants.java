package com.xsqwe.admin.constants;

/**
 * <p>
 * </p>
 *
 * @author huaping.zhang
 * @version 1.0.0
 * @date 2019年3月20日 下午6:46:17
 */
public interface EpCoreAdminConstants {
    /**
     * 订单是否删除 Y:删除
     */
    String ORDER_IS_DELETE = "Y";
    /**
     * 订单是否删除 N:未删除
     */
    String ORDER_IS_NOT_DELETE = "N";

    String POLICY_TYPE = "1";//生成保单

    /**
     * 项目渠道来源 ADMIN
     */
    String CHANNEL = "ADMIN";
    String IMAGE_TYPE = ".jpg";

    int REDIS_EXPIRE_SECOND = 10;

    String POLICY_STATUS_1 = "01";//成功
    String POLICY_STATUS_2 = "02";//退保

    String MEDICAL_RESULT = "乳腺癌";
    //DIR
    String PATIENT_BASIC_DIR = "PatientBasicDir";
    String INSURANCE_GROUP_DIR = "insuranceGroupDir";
    String IDENTIFICATION = "identification";

    String APPLICANT_FILE_DATA_DIR = "ApplicantFileDataDir";
    String CERTIFICATE = "certificate";
    String PRODUCT_TYPE_13 = "13";

    String POLICY = "policyNo";
    String POLICY_2 = "policyNo2";
    String TRANS_BEGIN_DATE = "transBeginDate";
    String TRANS_END_DATE = "transEndDate";

    String[] ORDER_FIELD_NAME_lIST = new String[]{"invoiceFile", "idCardFile"};


    // 用户状态 1:未审核 2.审核通过 3.审核不通过 4.已支付 5.已满额 6.已退款
    String COSENTYX_USER_STATUS_1 = "1";
    String COSENTYX_USER_STATUS_2 = "2";
    String COSENTYX_USER_STATUS_3 = "3";
    String COSENTYX_USER_STATUS_4 = "4";
    String COSENTYX_USER_STATUS_5 = "5";
    String COSENTYX_USER_STATUS_6 = "6";

    //优惠券订单支付状态 00-未支付 01-支付成功 02-支付失败 03-已退款
    String COUPON_PAY_STATUS_00 = "00";
    String COUPON_PAY_STATUS_01 = "01";
    String COUPON_PAY_STATUS_02 = "02";
    String COUPON_PAY_STATUS_03 = "03";

    // 订单类型 10-送药上门 20-到店购药 30-金融订单
    String ORDER_TYPE_1 = "10";
    String ORDER_TYPE_2 = "20";
    String ORDER_TYPE_3 = "30";

    String INSURANCE_APPLY_UNPASS = "INSURANCE_APPLY_UNPASS";
    String INSURANCE_RECEIVE_UNPASS = "INSURANCE_RECEIVE_UNPASS";
    String INSURANCE_APPLY_SUCCESS = "INSURANCE_APPLY_SUCCESS";
    String POSTOPERATIVE_MATERIALS_UNPASS = "POSTOPERATIVE_MATERIALS_UNPASS"; // 未通过
    String POSTOPERATIVE_MATERIALS_PASS = "POSTOPERATIVE_MATERIALS_PASS"; // 未通过
    String BINDING_BANK_CARD_SUBMIT = "BINDING_BANK_CARD_SUBMIT"; // 绑定银行卡审核中
    String BINDING_BANK_CARD_PASS = "BINDING_BANK_CARD_PASS"; // 绑定银行卡审核通过

    //分期申请状态
    String FINANCE_APPLY_ORDER_STATUS_18 = "18";
    String FINANCE_APPLY_ORDER_STATUS_20 = "20";

    // 产品字段父top 0
    String PRODUCT_COLUMN_TOP_PARENT = "0";

    // 是否
    String YESNO_Y = "Y";
    String YESNO_N = "N";

    // 产品状态
    String PRODUCT_ENABLED_TO_BE_ISSUED = "1";
    String PRODUCT_ENABLED_IN_THE_ISSUED = "2";
    String PRODUCT_ENABLED_ISSUED_TERMINATION = "3";

    String APPLICANT_CONTRACT_DIR = "UserSignImage";// 签名图片
    String CLAIM_SIGN_DIR = "paymentSignFile";
    String INSURANCE_CLAIM_CONTRACT = "insuranceClaimContract";
    String PAYMENT_SIGN = "paymentSign.jpg";
    String USER_SIGN_ORIGINAL_NAME = "eSealSignFile.jpg";
    String COUPON_APPLY_CONTRACT = "couponApplyContract";
    String INVOICE_FILE = "invoiceFile";//发票
    String ID_CARD_FILE = "idCardFile";//身份证
    String DIAGNOSIS__FILE = "diagnosisFile";//诊断证明
    String BANK_FILR = "bankFile";//银行卡
    String PRESCRIPTION_FILR = "prescriptionFile";//处方照片

    String MX_SERIAL_NUMBER = "MxSerialNumber";//镁信财务反馈流水单号
    String INSURANCE_STATUS_25 = "25";
    String INSURANCE_STATUS_26 = "26";
    String COUPON_07 = "07"; // 第7张优惠券

    /**
     * 客服消息推送
     */
    String SPT_INSTALLMENT_SUCCESS = "SPT_INSTALLMENT_SUCCESS";  //分期成功
    String SPT_INSTALLMENT_SIGN = "SPT_INSTALLMENT_SIGN";  //需补件或重新签名
    String SPT_INSTALLMENT_FAIL = "SPT_INSTALLMENT_FAIL";  //分期失败

    String SPT_WX_PAY_SUCCESS = "SPT_WX_PAY_SUCCESS";  //直付-线上
    String SPT_OFFLINE_PAY_SUCCESS = "SPT_OFFLINE_PAY_SUCCESS";  //直付-线下-成功
    String SPT_OFFLINE_PAY_FAIL = "SPT_OFFLINE_PAY_FAIL";  //直付-线下-失败

    /**
     * 分期账单是否删除
     */
    String REPAYMENT_IS_DELETE = "Y";
    String REPAYMENT_IS_NOT_DELETE = "N";

    String APPLICANT_COUPON_AUDITDIR = "couponAuditDir"; //发票

    //亲属关系
    String USER_SPT_INSURANCE_RELATION_1 = "父母";
    String USER_SPT_INSURANCE_RELATION_2 = "配偶";
    String USER_SPT_INSURANCE_RELATION_3 = "子女";
    String USER_SPT_INSURANCE_RELATION_4 = "其他";

    String RELATION_1 = "USER_SPT_INSURANCE_RELATION_1";
    String RELATION_2 = "USER_SPT_INSURANCE_RELATION_2";
    String RELATION_3 = "USER_SPT_INSURANCE_RELATION_3";
    String RELATION_4 = "USER_SPT_INSURANCE_RELATION_4";
}
