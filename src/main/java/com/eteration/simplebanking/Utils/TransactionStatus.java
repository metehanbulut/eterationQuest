package com.eteration.simplebanking.Utils;

import lombok.Data;

@Data
public class TransactionStatus {

    private String status;

    private String approvalCode;

    private String errorMessage;


    public TransactionStatus(boolean isSuccessful,boolean isDebit, String prmMsg)
    {
        if(isSuccessful)
        {
            status="OK";
            approvalCode=ProjectUtils.generateApprovalCode(isDebit);
        }
        else {
            status="ERR";
            approvalCode=null;
            errorMessage=prmMsg;
        }
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getStatus() {
        return status;
    }
}
