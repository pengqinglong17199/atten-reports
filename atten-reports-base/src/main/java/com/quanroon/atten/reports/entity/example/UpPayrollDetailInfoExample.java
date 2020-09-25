package com.quanroon.atten.reports.entity.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpPayrollDetailInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    public UpPayrollDetailInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSalaryIdIsNull() {
            addCriterion("salary_id is null");
            return (Criteria) this;
        }

        public Criteria andSalaryIdIsNotNull() {
            addCriterion("salary_id is not null");
            return (Criteria) this;
        }

        public Criteria andSalaryIdEqualTo(Integer value) {
            addCriterion("salary_id =", value, "salaryId");
            return (Criteria) this;
        }

        public Criteria andSalaryIdNotEqualTo(Integer value) {
            addCriterion("salary_id <>", value, "salaryId");
            return (Criteria) this;
        }

        public Criteria andSalaryIdGreaterThan(Integer value) {
            addCriterion("salary_id >", value, "salaryId");
            return (Criteria) this;
        }

        public Criteria andSalaryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("salary_id >=", value, "salaryId");
            return (Criteria) this;
        }

        public Criteria andSalaryIdLessThan(Integer value) {
            addCriterion("salary_id <", value, "salaryId");
            return (Criteria) this;
        }

        public Criteria andSalaryIdLessThanOrEqualTo(Integer value) {
            addCriterion("salary_id <=", value, "salaryId");
            return (Criteria) this;
        }

        public Criteria andSalaryIdIn(List<Integer> values) {
            addCriterion("salary_id in", values, "salaryId");
            return (Criteria) this;
        }

        public Criteria andSalaryIdNotIn(List<Integer> values) {
            addCriterion("salary_id not in", values, "salaryId");
            return (Criteria) this;
        }

        public Criteria andSalaryIdBetween(Integer value1, Integer value2) {
            addCriterion("salary_id between", value1, value2, "salaryId");
            return (Criteria) this;
        }

        public Criteria andSalaryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("salary_id not between", value1, value2, "salaryId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdIsNull() {
            addCriterion("worker_id is null");
            return (Criteria) this;
        }

        public Criteria andWorkerIdIsNotNull() {
            addCriterion("worker_id is not null");
            return (Criteria) this;
        }

        public Criteria andWorkerIdEqualTo(Integer value) {
            addCriterion("worker_id =", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdNotEqualTo(Integer value) {
            addCriterion("worker_id <>", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdGreaterThan(Integer value) {
            addCriterion("worker_id >", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("worker_id >=", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdLessThan(Integer value) {
            addCriterion("worker_id <", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdLessThanOrEqualTo(Integer value) {
            addCriterion("worker_id <=", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdIn(List<Integer> values) {
            addCriterion("worker_id in", values, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdNotIn(List<Integer> values) {
            addCriterion("worker_id not in", values, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdBetween(Integer value1, Integer value2) {
            addCriterion("worker_id between", value1, value2, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("worker_id not between", value1, value2, "workerId");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysIsNull() {
            addCriterion("attendance_days is null");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysIsNotNull() {
            addCriterion("attendance_days is not null");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysEqualTo(Integer value) {
            addCriterion("attendance_days =", value, "attendanceDays");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysNotEqualTo(Integer value) {
            addCriterion("attendance_days <>", value, "attendanceDays");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysGreaterThan(Integer value) {
            addCriterion("attendance_days >", value, "attendanceDays");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("attendance_days >=", value, "attendanceDays");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysLessThan(Integer value) {
            addCriterion("attendance_days <", value, "attendanceDays");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysLessThanOrEqualTo(Integer value) {
            addCriterion("attendance_days <=", value, "attendanceDays");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysIn(List<Integer> values) {
            addCriterion("attendance_days in", values, "attendanceDays");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysNotIn(List<Integer> values) {
            addCriterion("attendance_days not in", values, "attendanceDays");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysBetween(Integer value1, Integer value2) {
            addCriterion("attendance_days between", value1, value2, "attendanceDays");
            return (Criteria) this;
        }

        public Criteria andAttendanceDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("attendance_days not between", value1, value2, "attendanceDays");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyIsNull() {
            addCriterion("payable_money is null");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyIsNotNull() {
            addCriterion("payable_money is not null");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyEqualTo(BigDecimal value) {
            addCriterion("payable_money =", value, "payableMoney");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyNotEqualTo(BigDecimal value) {
            addCriterion("payable_money <>", value, "payableMoney");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyGreaterThan(BigDecimal value) {
            addCriterion("payable_money >", value, "payableMoney");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("payable_money >=", value, "payableMoney");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyLessThan(BigDecimal value) {
            addCriterion("payable_money <", value, "payableMoney");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("payable_money <=", value, "payableMoney");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyIn(List<BigDecimal> values) {
            addCriterion("payable_money in", values, "payableMoney");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyNotIn(List<BigDecimal> values) {
            addCriterion("payable_money not in", values, "payableMoney");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("payable_money between", value1, value2, "payableMoney");
            return (Criteria) this;
        }

        public Criteria andPayableMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("payable_money not between", value1, value2, "payableMoney");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyIsNull() {
            addCriterion("paid_money is null");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyIsNotNull() {
            addCriterion("paid_money is not null");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyEqualTo(BigDecimal value) {
            addCriterion("paid_money =", value, "paidMoney");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyNotEqualTo(BigDecimal value) {
            addCriterion("paid_money <>", value, "paidMoney");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyGreaterThan(BigDecimal value) {
            addCriterion("paid_money >", value, "paidMoney");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("paid_money >=", value, "paidMoney");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyLessThan(BigDecimal value) {
            addCriterion("paid_money <", value, "paidMoney");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("paid_money <=", value, "paidMoney");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyIn(List<BigDecimal> values) {
            addCriterion("paid_money in", values, "paidMoney");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyNotIn(List<BigDecimal> values) {
            addCriterion("paid_money not in", values, "paidMoney");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("paid_money between", value1, value2, "paidMoney");
            return (Criteria) this;
        }

        public Criteria andPaidMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("paid_money not between", value1, value2, "paidMoney");
            return (Criteria) this;
        }

        public Criteria andCheckMarkIsNull() {
            addCriterion("check_mark is null");
            return (Criteria) this;
        }

        public Criteria andCheckMarkIsNotNull() {
            addCriterion("check_mark is not null");
            return (Criteria) this;
        }

        public Criteria andCheckMarkEqualTo(String value) {
            addCriterion("check_mark =", value, "checkMark");
            return (Criteria) this;
        }

        public Criteria andCheckMarkNotEqualTo(String value) {
            addCriterion("check_mark <>", value, "checkMark");
            return (Criteria) this;
        }

        public Criteria andCheckMarkGreaterThan(String value) {
            addCriterion("check_mark >", value, "checkMark");
            return (Criteria) this;
        }

        public Criteria andCheckMarkGreaterThanOrEqualTo(String value) {
            addCriterion("check_mark >=", value, "checkMark");
            return (Criteria) this;
        }

        public Criteria andCheckMarkLessThan(String value) {
            addCriterion("check_mark <", value, "checkMark");
            return (Criteria) this;
        }

        public Criteria andCheckMarkLessThanOrEqualTo(String value) {
            addCriterion("check_mark <=", value, "checkMark");
            return (Criteria) this;
        }

        public Criteria andCheckMarkLike(String value) {
            addCriterion("check_mark like", value, "checkMark");
            return (Criteria) this;
        }

        public Criteria andCheckMarkNotLike(String value) {
            addCriterion("check_mark not like", value, "checkMark");
            return (Criteria) this;
        }

        public Criteria andCheckMarkIn(List<String> values) {
            addCriterion("check_mark in", values, "checkMark");
            return (Criteria) this;
        }

        public Criteria andCheckMarkNotIn(List<String> values) {
            addCriterion("check_mark not in", values, "checkMark");
            return (Criteria) this;
        }

        public Criteria andCheckMarkBetween(String value1, String value2) {
            addCriterion("check_mark between", value1, value2, "checkMark");
            return (Criteria) this;
        }

        public Criteria andCheckMarkNotBetween(String value1, String value2) {
            addCriterion("check_mark not between", value1, value2, "checkMark");
            return (Criteria) this;
        }

        public Criteria andIsReissueIsNull() {
            addCriterion("is_reissue is null");
            return (Criteria) this;
        }

        public Criteria andIsReissueIsNotNull() {
            addCriterion("is_reissue is not null");
            return (Criteria) this;
        }

        public Criteria andIsReissueEqualTo(String value) {
            addCriterion("is_reissue =", value, "isReissue");
            return (Criteria) this;
        }

        public Criteria andIsReissueNotEqualTo(String value) {
            addCriterion("is_reissue <>", value, "isReissue");
            return (Criteria) this;
        }

        public Criteria andIsReissueGreaterThan(String value) {
            addCriterion("is_reissue >", value, "isReissue");
            return (Criteria) this;
        }

        public Criteria andIsReissueGreaterThanOrEqualTo(String value) {
            addCriterion("is_reissue >=", value, "isReissue");
            return (Criteria) this;
        }

        public Criteria andIsReissueLessThan(String value) {
            addCriterion("is_reissue <", value, "isReissue");
            return (Criteria) this;
        }

        public Criteria andIsReissueLessThanOrEqualTo(String value) {
            addCriterion("is_reissue <=", value, "isReissue");
            return (Criteria) this;
        }

        public Criteria andIsReissueLike(String value) {
            addCriterion("is_reissue like", value, "isReissue");
            return (Criteria) this;
        }

        public Criteria andIsReissueNotLike(String value) {
            addCriterion("is_reissue not like", value, "isReissue");
            return (Criteria) this;
        }

        public Criteria andIsReissueIn(List<String> values) {
            addCriterion("is_reissue in", values, "isReissue");
            return (Criteria) this;
        }

        public Criteria andIsReissueNotIn(List<String> values) {
            addCriterion("is_reissue not in", values, "isReissue");
            return (Criteria) this;
        }

        public Criteria andIsReissueBetween(String value1, String value2) {
            addCriterion("is_reissue between", value1, value2, "isReissue");
            return (Criteria) this;
        }

        public Criteria andIsReissueNotBetween(String value1, String value2) {
            addCriterion("is_reissue not between", value1, value2, "isReissue");
            return (Criteria) this;
        }

        public Criteria andReissueMonthIsNull() {
            addCriterion("reissue_month is null");
            return (Criteria) this;
        }

        public Criteria andReissueMonthIsNotNull() {
            addCriterion("reissue_month is not null");
            return (Criteria) this;
        }

        public Criteria andReissueMonthEqualTo(Date value) {
            addCriterion("reissue_month =", value, "reissueMonth");
            return (Criteria) this;
        }

        public Criteria andReissueMonthNotEqualTo(Date value) {
            addCriterion("reissue_month <>", value, "reissueMonth");
            return (Criteria) this;
        }

        public Criteria andReissueMonthGreaterThan(Date value) {
            addCriterion("reissue_month >", value, "reissueMonth");
            return (Criteria) this;
        }

        public Criteria andReissueMonthGreaterThanOrEqualTo(Date value) {
            addCriterion("reissue_month >=", value, "reissueMonth");
            return (Criteria) this;
        }

        public Criteria andReissueMonthLessThan(Date value) {
            addCriterion("reissue_month <", value, "reissueMonth");
            return (Criteria) this;
        }

        public Criteria andReissueMonthLessThanOrEqualTo(Date value) {
            addCriterion("reissue_month <=", value, "reissueMonth");
            return (Criteria) this;
        }

        public Criteria andReissueMonthIn(List<Date> values) {
            addCriterion("reissue_month in", values, "reissueMonth");
            return (Criteria) this;
        }

        public Criteria andReissueMonthNotIn(List<Date> values) {
            addCriterion("reissue_month not in", values, "reissueMonth");
            return (Criteria) this;
        }

        public Criteria andReissueMonthBetween(Date value1, Date value2) {
            addCriterion("reissue_month between", value1, value2, "reissueMonth");
            return (Criteria) this;
        }

        public Criteria andReissueMonthNotBetween(Date value1, Date value2) {
            addCriterion("reissue_month not between", value1, value2, "reissueMonth");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated do_not_delete_during_merge Mon Jun 29 15:04:37 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table up_payroll_detail_info
     *
     * @mbggenerated Mon Jun 29 15:04:37 CST 2020
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}