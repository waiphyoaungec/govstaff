package com.example.civilservantapp.model.fullyear;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("all")
public class FullModel {
    @SerializedName("message")
    private final String message;
    @SerializedName("data")
    private final Object data;
    @SerializedName("paid_month")
    private final int paidMonth;
    @SerializedName("deposited_amt")
    private final double depositedAmt;
    @SerializedName("claim_amount")
    private final double claimAmount;

    public FullModel(String message, Object data, int paidMonth, double depositedAmt, double claimAmount) {
        this.message = message;
        this.data = data;
        this.paidMonth = paidMonth;
        this.depositedAmt = depositedAmt;
        this.claimAmount = claimAmount;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public int getPaidMonth() {
        return paidMonth;
    }

    public double getDepositedAmt() {
        return depositedAmt;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public static class Data {
        @SerializedName("id")
        private final int id;
        @SerializedName("policy_id")
        private final String policyId;
        @SerializedName("age")
        private final int age;
        @SerializedName("insurance_amount")
        private final int insuranceAmount;
        @SerializedName("name")
        private final String name;
        @SerializedName("nrc")
        private final String nrc;
        @SerializedName("personal_no")
        private final String personalNo;
        @SerializedName("insurance_peroid")
        private final int insurancePeroid;
        @SerializedName("start_date")
        private final String startDate;
        @SerializedName("end_date")
        private final String endDate;
        @SerializedName("monthly_date")
        private final String monthlyDate;
        @SerializedName("due_date")
        private final String dueDate;
        @SerializedName("amount")
        private final int amount;
        @SerializedName("bank_fee")
        private final int bankFee;
        @SerializedName("easypay_fee")
        private final int easypayFee;
        @SerializedName("sh_fee")
        private final int shFee;
        @SerializedName("payment_status")
        private final int paymentStatus;

        public Data(int id, String policyId, int age, int insuranceAmount, String name, String nrc,
                    String personalNo, int insurancePeroid, String startDate, String endDate,
                    String monthlyDate, String dueDate, int amount, int bankFee, int easypayFee,
                    int shFee, int paymentStatus) {
            this.id = id;
            this.policyId = policyId;
            this.age = age;
            this.insuranceAmount = insuranceAmount;
            this.name = name;
            this.nrc = nrc;
            this.personalNo = personalNo;
            this.insurancePeroid = insurancePeroid;
            this.startDate = startDate;
            this.endDate = endDate;
            this.monthlyDate = monthlyDate;
            this.dueDate = dueDate;
            this.amount = amount;
            this.bankFee = bankFee;
            this.easypayFee = easypayFee;
            this.shFee = shFee;
            this.paymentStatus = paymentStatus;
        }

        public int getId() {
            return id;
        }

        public String getPolicyId() {
            return policyId;
        }

        public int getAge() {
            return age;
        }

        public int getInsuranceAmount() {
            return insuranceAmount;
        }

        public String getName() {
            return name;
        }

        public String getNrc() {
            return nrc;
        }

        public String getPersonalNo() {
            return personalNo;
        }

        public int getInsurancePeroid() {
            return insurancePeroid;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getMonthlyDate() {
            return monthlyDate;
        }

        public String getDueDate() {
            return dueDate;
        }

        public int getAmount() {
            return amount;
        }

        public int getBankFee() {
            return bankFee;
        }

        public int getEasypayFee() {
            return easypayFee;
        }

        public int getShFee() {
            return shFee;
        }

        public int getPaymentStatus() {
            return paymentStatus;
        }
    }
}
