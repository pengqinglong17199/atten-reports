package com.quanroon.atten.reports.entity.example;

import java.util.ArrayList;
import java.util.List;

public class UpPlatmeAuthExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    public UpPlatmeAuthExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
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
     * This method corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
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

        public Criteria andPlatmeAuthIdIsNull() {
            addCriterion("platme_auth_id is null");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdIsNotNull() {
            addCriterion("platme_auth_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdEqualTo(String value) {
            addCriterion("platme_auth_id =", value, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdNotEqualTo(String value) {
            addCriterion("platme_auth_id <>", value, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdGreaterThan(String value) {
            addCriterion("platme_auth_id >", value, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdGreaterThanOrEqualTo(String value) {
            addCriterion("platme_auth_id >=", value, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdLessThan(String value) {
            addCriterion("platme_auth_id <", value, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdLessThanOrEqualTo(String value) {
            addCriterion("platme_auth_id <=", value, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdLike(String value) {
            addCriterion("platme_auth_id like", value, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdNotLike(String value) {
            addCriterion("platme_auth_id not like", value, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdIn(List<String> values) {
            addCriterion("platme_auth_id in", values, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdNotIn(List<String> values) {
            addCriterion("platme_auth_id not in", values, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdBetween(String value1, String value2) {
            addCriterion("platme_auth_id between", value1, value2, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthIdNotBetween(String value1, String value2) {
            addCriterion("platme_auth_id not between", value1, value2, "platmeAuthId");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyIsNull() {
            addCriterion("platme_auth_key is null");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyIsNotNull() {
            addCriterion("platme_auth_key is not null");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyEqualTo(String value) {
            addCriterion("platme_auth_key =", value, "platmeAuthKey");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyNotEqualTo(String value) {
            addCriterion("platme_auth_key <>", value, "platmeAuthKey");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyGreaterThan(String value) {
            addCriterion("platme_auth_key >", value, "platmeAuthKey");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyGreaterThanOrEqualTo(String value) {
            addCriterion("platme_auth_key >=", value, "platmeAuthKey");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyLessThan(String value) {
            addCriterion("platme_auth_key <", value, "platmeAuthKey");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyLessThanOrEqualTo(String value) {
            addCriterion("platme_auth_key <=", value, "platmeAuthKey");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyLike(String value) {
            addCriterion("platme_auth_key like", value, "platmeAuthKey");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyNotLike(String value) {
            addCriterion("platme_auth_key not like", value, "platmeAuthKey");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyIn(List<String> values) {
            addCriterion("platme_auth_key in", values, "platmeAuthKey");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyNotIn(List<String> values) {
            addCriterion("platme_auth_key not in", values, "platmeAuthKey");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyBetween(String value1, String value2) {
            addCriterion("platme_auth_key between", value1, value2, "platmeAuthKey");
            return (Criteria) this;
        }

        public Criteria andPlatmeAuthKeyNotBetween(String value1, String value2) {
            addCriterion("platme_auth_key not between", value1, value2, "platmeAuthKey");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table up_platme_auth
     *
     * @mbggenerated do_not_delete_during_merge Mon Jun 29 16:26:17 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table up_platme_auth
     *
     * @mbggenerated Mon Jun 29 16:26:17 CST 2020
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