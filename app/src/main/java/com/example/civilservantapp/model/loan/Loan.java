package com.example.civilservantapp.model.loan;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("all")
public class Loan {
    @SerializedName("message")
    private  String message;
    private  Object data;
    @SerializedName("paid_month")
    private  int paidMonth;

    @SerializedName("deposited_amt")
    private  int depositedAmt;
    @SerializedName("loanamt")
    private  double loanamt;
    @SerializedName("stamp")
    private  double stamp;
    @SerializedName("totloan")
    private  double totloan;

    public Loan(String message, Object data, int paidMonth, int depositedAmt, double loanamt,
                 double stamp, double totloan) {

        this.message = message;
        this.data = data;
        this.paidMonth = paidMonth;
        this.depositedAmt = depositedAmt;
        this.loanamt = loanamt;
        this.stamp = stamp;
        this.totloan = totloan;
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

    public int getDepositedAmt() {
        return depositedAmt;
    }

    public double getLoanamt() {
        return loanamt;
    }

    public double getStamp() {
        return stamp;
    }

    public double getTotloan() {
        return totloan;
    }

    public static class Data {
        @SerializedName("id")
        private int id;
        @SerializedName("policy_id")
        private  String policyId;
        @SerializedName("insurance_amount")
        private  int insuranceAmount;
        @SerializedName("name")
        private  String name;
        @SerializedName("age")
        private String age;
        @SerializedName("nrc")
        private  String nrc;
        @SerializedName("personal_no")
        private  String personalNo;
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
        @SerializedName("army_status")
        private final String army;

        public Data(int id, String policyId, int insuranceAmount, String name, String nrc,
                    String personalNo, int insurancePeroid, String startDate, String endDate,
                    String monthlyDate, String dueDate, int amount, int bankFee, int easypayFee,
                    int shFee, int paymentStatus,String army) {
            this.id = id;
            this.policyId = policyId;
            this.insuranceAmount = insuranceAmount;
            this.name = name;
            this.nrc = nrc;
            this.army = army;
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

        public String getAge() {
            return age;
        }

        public String getPolicyId() {
            return policyId;
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

        public Object getPersonalNo() {
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

        public String getArmy() {
            return army;
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
