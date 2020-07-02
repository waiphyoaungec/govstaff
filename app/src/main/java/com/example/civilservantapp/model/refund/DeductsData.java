
package com.example.civilservantapp.model.refund;

import com.google.gson.annotations.SerializedName;

public class DeductsData {

    @SerializedName("deduct_data")
    private DeductData mDeductData;

    public DeductData getDeductData() {
        return mDeductData;
    }

    public static class Builder {

        private DeductData mDeductData;

        public DeductsData.Builder withDeductData(DeductData deductData) {
            mDeductData = deductData;
            return this;
        }

        public DeductsData build() {
            DeductsData deductsData = new DeductsData();
            deductsData.mDeductData = mDeductData;
            return deductsData;
        }

    }

}
