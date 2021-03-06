package com.quanroon.atten.reports.entity.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpWorkerExpExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    public UpWorkerExpExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
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
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
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

        public Criteria andCertificateKindIsNull() {
            addCriterion("certificate_kind is null");
            return (Criteria) this;
        }

        public Criteria andCertificateKindIsNotNull() {
            addCriterion("certificate_kind is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateKindEqualTo(String value) {
            addCriterion("certificate_kind =", value, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateKindNotEqualTo(String value) {
            addCriterion("certificate_kind <>", value, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateKindGreaterThan(String value) {
            addCriterion("certificate_kind >", value, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateKindGreaterThanOrEqualTo(String value) {
            addCriterion("certificate_kind >=", value, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateKindLessThan(String value) {
            addCriterion("certificate_kind <", value, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateKindLessThanOrEqualTo(String value) {
            addCriterion("certificate_kind <=", value, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateKindLike(String value) {
            addCriterion("certificate_kind like", value, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateKindNotLike(String value) {
            addCriterion("certificate_kind not like", value, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateKindIn(List<String> values) {
            addCriterion("certificate_kind in", values, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateKindNotIn(List<String> values) {
            addCriterion("certificate_kind not in", values, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateKindBetween(String value1, String value2) {
            addCriterion("certificate_kind between", value1, value2, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateKindNotBetween(String value1, String value2) {
            addCriterion("certificate_kind not between", value1, value2, "certificateKind");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeIsNull() {
            addCriterion("certificate_type is null");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeIsNotNull() {
            addCriterion("certificate_type is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeEqualTo(String value) {
            addCriterion("certificate_type =", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeNotEqualTo(String value) {
            addCriterion("certificate_type <>", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeGreaterThan(String value) {
            addCriterion("certificate_type >", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeGreaterThanOrEqualTo(String value) {
            addCriterion("certificate_type >=", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeLessThan(String value) {
            addCriterion("certificate_type <", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeLessThanOrEqualTo(String value) {
            addCriterion("certificate_type <=", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeLike(String value) {
            addCriterion("certificate_type like", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeNotLike(String value) {
            addCriterion("certificate_type not like", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeIn(List<String> values) {
            addCriterion("certificate_type in", values, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeNotIn(List<String> values) {
            addCriterion("certificate_type not in", values, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeBetween(String value1, String value2) {
            addCriterion("certificate_type between", value1, value2, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeNotBetween(String value1, String value2) {
            addCriterion("certificate_type not between", value1, value2, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeIsNull() {
            addCriterion("certificate_grade is null");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeIsNotNull() {
            addCriterion("certificate_grade is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeEqualTo(String value) {
            addCriterion("certificate_grade =", value, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeNotEqualTo(String value) {
            addCriterion("certificate_grade <>", value, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeGreaterThan(String value) {
            addCriterion("certificate_grade >", value, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeGreaterThanOrEqualTo(String value) {
            addCriterion("certificate_grade >=", value, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeLessThan(String value) {
            addCriterion("certificate_grade <", value, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeLessThanOrEqualTo(String value) {
            addCriterion("certificate_grade <=", value, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeLike(String value) {
            addCriterion("certificate_grade like", value, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeNotLike(String value) {
            addCriterion("certificate_grade not like", value, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeIn(List<String> values) {
            addCriterion("certificate_grade in", values, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeNotIn(List<String> values) {
            addCriterion("certificate_grade not in", values, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeBetween(String value1, String value2) {
            addCriterion("certificate_grade between", value1, value2, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateGradeNotBetween(String value1, String value2) {
            addCriterion("certificate_grade not between", value1, value2, "certificateGrade");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberIsNull() {
            addCriterion("certificate_number is null");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberIsNotNull() {
            addCriterion("certificate_number is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberEqualTo(String value) {
            addCriterion("certificate_number =", value, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberNotEqualTo(String value) {
            addCriterion("certificate_number <>", value, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberGreaterThan(String value) {
            addCriterion("certificate_number >", value, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberGreaterThanOrEqualTo(String value) {
            addCriterion("certificate_number >=", value, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberLessThan(String value) {
            addCriterion("certificate_number <", value, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberLessThanOrEqualTo(String value) {
            addCriterion("certificate_number <=", value, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberLike(String value) {
            addCriterion("certificate_number like", value, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberNotLike(String value) {
            addCriterion("certificate_number not like", value, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberIn(List<String> values) {
            addCriterion("certificate_number in", values, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberNotIn(List<String> values) {
            addCriterion("certificate_number not in", values, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberBetween(String value1, String value2) {
            addCriterion("certificate_number between", value1, value2, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNumberNotBetween(String value1, String value2) {
            addCriterion("certificate_number not between", value1, value2, "certificateNumber");
            return (Criteria) this;
        }

        public Criteria andCertificateNameIsNull() {
            addCriterion("certificate_name is null");
            return (Criteria) this;
        }

        public Criteria andCertificateNameIsNotNull() {
            addCriterion("certificate_name is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateNameEqualTo(String value) {
            addCriterion("certificate_name =", value, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateNameNotEqualTo(String value) {
            addCriterion("certificate_name <>", value, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateNameGreaterThan(String value) {
            addCriterion("certificate_name >", value, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateNameGreaterThanOrEqualTo(String value) {
            addCriterion("certificate_name >=", value, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateNameLessThan(String value) {
            addCriterion("certificate_name <", value, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateNameLessThanOrEqualTo(String value) {
            addCriterion("certificate_name <=", value, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateNameLike(String value) {
            addCriterion("certificate_name like", value, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateNameNotLike(String value) {
            addCriterion("certificate_name not like", value, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateNameIn(List<String> values) {
            addCriterion("certificate_name in", values, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateNameNotIn(List<String> values) {
            addCriterion("certificate_name not in", values, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateNameBetween(String value1, String value2) {
            addCriterion("certificate_name between", value1, value2, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateNameNotBetween(String value1, String value2) {
            addCriterion("certificate_name not between", value1, value2, "certificateName");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateIsNull() {
            addCriterion("certificate_start_date is null");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateIsNotNull() {
            addCriterion("certificate_start_date is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateEqualTo(Date value) {
            addCriterion("certificate_start_date =", value, "certificateStartDate");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateNotEqualTo(Date value) {
            addCriterion("certificate_start_date <>", value, "certificateStartDate");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateGreaterThan(Date value) {
            addCriterion("certificate_start_date >", value, "certificateStartDate");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateGreaterThanOrEqualTo(Date value) {
            addCriterion("certificate_start_date >=", value, "certificateStartDate");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateLessThan(Date value) {
            addCriterion("certificate_start_date <", value, "certificateStartDate");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateLessThanOrEqualTo(Date value) {
            addCriterion("certificate_start_date <=", value, "certificateStartDate");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateIn(List<Date> values) {
            addCriterion("certificate_start_date in", values, "certificateStartDate");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateNotIn(List<Date> values) {
            addCriterion("certificate_start_date not in", values, "certificateStartDate");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateBetween(Date value1, Date value2) {
            addCriterion("certificate_start_date between", value1, value2, "certificateStartDate");
            return (Criteria) this;
        }

        public Criteria andCertificateStartDateNotBetween(Date value1, Date value2) {
            addCriterion("certificate_start_date not between", value1, value2, "certificateStartDate");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateIsNull() {
            addCriterion("certificate_end_date is null");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateIsNotNull() {
            addCriterion("certificate_end_date is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateEqualTo(Date value) {
            addCriterion("certificate_end_date =", value, "certificateEndDate");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateNotEqualTo(Date value) {
            addCriterion("certificate_end_date <>", value, "certificateEndDate");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateGreaterThan(Date value) {
            addCriterion("certificate_end_date >", value, "certificateEndDate");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateGreaterThanOrEqualTo(Date value) {
            addCriterion("certificate_end_date >=", value, "certificateEndDate");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateLessThan(Date value) {
            addCriterion("certificate_end_date <", value, "certificateEndDate");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateLessThanOrEqualTo(Date value) {
            addCriterion("certificate_end_date <=", value, "certificateEndDate");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateIn(List<Date> values) {
            addCriterion("certificate_end_date in", values, "certificateEndDate");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateNotIn(List<Date> values) {
            addCriterion("certificate_end_date not in", values, "certificateEndDate");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateBetween(Date value1, Date value2) {
            addCriterion("certificate_end_date between", value1, value2, "certificateEndDate");
            return (Criteria) this;
        }

        public Criteria andCertificateEndDateNotBetween(Date value1, Date value2) {
            addCriterion("certificate_end_date not between", value1, value2, "certificateEndDate");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingIsNull() {
            addCriterion("certificate_issuing is null");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingIsNotNull() {
            addCriterion("certificate_issuing is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingEqualTo(String value) {
            addCriterion("certificate_issuing =", value, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingNotEqualTo(String value) {
            addCriterion("certificate_issuing <>", value, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingGreaterThan(String value) {
            addCriterion("certificate_issuing >", value, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingGreaterThanOrEqualTo(String value) {
            addCriterion("certificate_issuing >=", value, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingLessThan(String value) {
            addCriterion("certificate_issuing <", value, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingLessThanOrEqualTo(String value) {
            addCriterion("certificate_issuing <=", value, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingLike(String value) {
            addCriterion("certificate_issuing like", value, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingNotLike(String value) {
            addCriterion("certificate_issuing not like", value, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingIn(List<String> values) {
            addCriterion("certificate_issuing in", values, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingNotIn(List<String> values) {
            addCriterion("certificate_issuing not in", values, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingBetween(String value1, String value2) {
            addCriterion("certificate_issuing between", value1, value2, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateIssuingNotBetween(String value1, String value2) {
            addCriterion("certificate_issuing not between", value1, value2, "certificateIssuing");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusIsNull() {
            addCriterion("certificate_status is null");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusIsNotNull() {
            addCriterion("certificate_status is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusEqualTo(String value) {
            addCriterion("certificate_status =", value, "certificateStatus");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusNotEqualTo(String value) {
            addCriterion("certificate_status <>", value, "certificateStatus");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusGreaterThan(String value) {
            addCriterion("certificate_status >", value, "certificateStatus");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusGreaterThanOrEqualTo(String value) {
            addCriterion("certificate_status >=", value, "certificateStatus");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusLessThan(String value) {
            addCriterion("certificate_status <", value, "certificateStatus");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusLessThanOrEqualTo(String value) {
            addCriterion("certificate_status <=", value, "certificateStatus");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusLike(String value) {
            addCriterion("certificate_status like", value, "certificateStatus");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusNotLike(String value) {
            addCriterion("certificate_status not like", value, "certificateStatus");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusIn(List<String> values) {
            addCriterion("certificate_status in", values, "certificateStatus");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusNotIn(List<String> values) {
            addCriterion("certificate_status not in", values, "certificateStatus");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusBetween(String value1, String value2) {
            addCriterion("certificate_status between", value1, value2, "certificateStatus");
            return (Criteria) this;
        }

        public Criteria andCertificateStatusNotBetween(String value1, String value2) {
            addCriterion("certificate_status not between", value1, value2, "certificateStatus");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table up_worker_exp
     *
     * @mbggenerated do_not_delete_during_merge Mon Jun 29 15:18:45 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
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