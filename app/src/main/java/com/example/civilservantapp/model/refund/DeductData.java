
package com.example.civilservantapp.model.refund;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DeductData {

    @SerializedName("contract_copy")
    private Long mContractCopy;
    @SerializedName("contract_fines")
    private Long mContractFines;
    @SerializedName("loan")
    private Long mLoan;
    @SerializedName("loan_interest")
    private Long mLoanInterest;
    @SerializedName("lost_contract_stamp")
    private Long mLostContractStamp;
    @SerializedName("persistent_interest")
    private Long mPersistentInterest;
    @SerializedName("premium")
    private Long mPremium;
    @SerializedName("total_deduct_amount")
    private Long mTotalDeductAmount;

    public Long getContractCopy() {
        return mContractCopy;
    }

    public Long getContractFines() {
        return mContractFines;
    }

    public Long getLoan() {
        return mLoan;
    }

    public Long getLoanInterest() {
        return mLoanInterest;
    }

    public Long getLostContractStamp() {
        return mLostContractStamp;
    }

    public Long getPersistentInterest() {
        return mPersistentInterest;
    }

    public Long getPremium() {
        return mPremium;
    }

    public Long getTotalDeductAmount() {
        return mTotalDeductAmount;
    }

    public static class Builder {

        private Long mContractCopy;
        private Long mContractFines;
        private Long mLoan;
        private Long mLoanInterest;
        private Long mLostContractStamp;
        private Long mPersistentInterest;
        private Long mPremium;
        private Long mTotalDeductAmount;

        public DeductData.Builder withContractCopy(Long contractCopy) {
            mContractCopy = contractCopy;
            return this;
        }

        public DeductData.Builder withContractFines(Long contractFines) {
            mContractFines = contractFines;
            return this;
        }

        public DeductData.Builder withLoan(Long loan) {
            mLoan = loan;
            return this;
        }

        public DeductData.Builder withLoanInterest(Long loanInterest) {
            mLoanInterest = loanInterest;
            return this;
        }

        public DeductData.Builder withLostContractStamp(Long lostContractStamp) {
            mLostContractStamp = lostContractStamp;
            return this;
        }

        public DeductData.Builder withPersistentInterest(Long persistentInterest) {
            mPersistentInterest = persistentInterest;
            return this;
        }

        public DeductData.Builder withPremium(Long premium) {
            mPremium = premium;
            return this;
        }

        public DeductData.Builder withTotalDeductAmount(Long totalDeductAmount) {
            mTotalDeductAmount = totalDeductAmount;
            return this;
        }

        public DeductData build() {
            DeductData deductData = new DeductData();
            deductData.mContractCopy = mContractCopy;
            deductData.mContractFines = mContractFines;
            deductData.mLoan = mLoan;
            deductData.mLoanInterest = mLoanInterest;
            deductData.mLostContractStamp = mLostContractStamp;
            deductData.mPersistentInterest = mPersistentInterest;
            deductData.mPremium = mPremium;
            deductData.mTotalDeductAmount = mTotalDeductAmount;
            return deductData;
        }

    }

}
