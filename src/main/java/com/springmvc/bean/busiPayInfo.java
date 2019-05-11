package com.springmvc.bean;

import java.math.BigDecimal;
import java.util.Date;

public class busiPayInfo {
    private Integer id;

    private String custNo;

    private String custName;

    private String productLimitNo;

    private String productCode;

    private BigDecimal busiAmt;

    private String repayMethod;

    private Integer term;

    private String termOptCode;

    private String termUnit;

    private String payAccount;

    private String payAccountType;

    private String payAccName;

    private String payeeInnerCardFlag;

    private String payeeAcctThNbr;

    private String payeeAcctThName;

    private String bankName;

    private String bankCode;

    private String elctAcctno;

    private String purpose;

    private BigDecimal loanRate;

    private String approveStatus;

    private String ifApprove;

    private String payDate;

    private String applyDate;

    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo == null ? null : custNo.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getProductLimitNo() {
        return productLimitNo;
    }

    public void setProductLimitNo(String productLimitNo) {
        this.productLimitNo = productLimitNo == null ? null : productLimitNo.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public BigDecimal getBusiAmt() {
        return busiAmt;
    }

    public void setBusiAmt(BigDecimal busiAmt) {
        this.busiAmt = busiAmt;
    }

    public String getRepayMethod() {
        return repayMethod;
    }

    public void setRepayMethod(String repayMethod) {
        this.repayMethod = repayMethod == null ? null : repayMethod.trim();
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getTermOptCode() {
        return termOptCode;
    }

    public void setTermOptCode(String termOptCode) {
        this.termOptCode = termOptCode == null ? null : termOptCode.trim();
    }

    public String getTermUnit() {
        return termUnit;
    }

    public void setTermUnit(String termUnit) {
        this.termUnit = termUnit == null ? null : termUnit.trim();
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount == null ? null : payAccount.trim();
    }

    public String getPayAccountType() {
        return payAccountType;
    }

    public void setPayAccountType(String payAccountType) {
        this.payAccountType = payAccountType == null ? null : payAccountType.trim();
    }

    public String getPayAccName() {
        return payAccName;
    }

    public void setPayAccName(String payAccName) {
        this.payAccName = payAccName == null ? null : payAccName.trim();
    }

    public String getPayeeInnerCardFlag() {
        return payeeInnerCardFlag;
    }

    public void setPayeeInnerCardFlag(String payeeInnerCardFlag) {
        this.payeeInnerCardFlag = payeeInnerCardFlag == null ? null : payeeInnerCardFlag.trim();
    }

    public String getPayeeAcctThNbr() {
        return payeeAcctThNbr;
    }

    public void setPayeeAcctThNbr(String payeeAcctThNbr) {
        this.payeeAcctThNbr = payeeAcctThNbr == null ? null : payeeAcctThNbr.trim();
    }

    public String getPayeeAcctThName() {
        return payeeAcctThName;
    }

    public void setPayeeAcctThName(String payeeAcctThName) {
        this.payeeAcctThName = payeeAcctThName == null ? null : payeeAcctThName.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getElctAcctno() {
        return elctAcctno;
    }

    public void setElctAcctno(String elctAcctno) {
        this.elctAcctno = elctAcctno == null ? null : elctAcctno.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus == null ? null : approveStatus.trim();
    }

    public String getIfApprove() {
        return ifApprove;
    }

    public void setIfApprove(String ifApprove) {
        this.ifApprove = ifApprove == null ? null : ifApprove.trim();
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate == null ? null : payDate.trim();
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate == null ? null : applyDate.trim();
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}