package com.zjuse.bankingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("rate")
public class Rate {
    @JsonProperty("demandrate")
    private BigDecimal demand_rate;
    @JsonProperty("3mrate")
    private BigDecimal _3month_rate;
    @JsonProperty("6mrate")
    private BigDecimal _6month_rate;
    @JsonProperty("1yrate")
    private BigDecimal _1year_rate;
    @JsonProperty("2yrate")
    private BigDecimal _2year_rate;
    @JsonProperty("3yrate")
    private BigDecimal _3year_rate;
    @JsonProperty("5yrate")
    private BigDecimal _5year_rate;

    public BigDecimal getDemand_rate() {
        return demand_rate;}
    public void setDemand_rate(BigDecimal demand_rate) {this.demand_rate = demand_rate;}
    public BigDecimal get_3month_rate() {return _3month_rate;}
    public void set_3month_rate(BigDecimal _3month_rate) {this._3month_rate = _3month_rate;}
    public BigDecimal get_6month_rate() {return _6month_rate;}
    public void set_6month_rate(BigDecimal _6month_rate) {this._6month_rate = _6month_rate;}
    public BigDecimal get_1year_rate() {return _1year_rate;}
    public void set_1year_rate(BigDecimal _1year_rate) {this._1year_rate = _1year_rate;}
    public BigDecimal get_2year_rate() {return _2year_rate;}
    public void set_2year_rate(BigDecimal _2year_rate) {this._2year_rate = _2year_rate;}
    public BigDecimal get_3year_rate() {return _3year_rate;}
    public void set_3year_rate(BigDecimal _3year_rate) {this._3year_rate = _3year_rate;}
    public BigDecimal get_5year_rate() {return _5year_rate;}
    public void set_5year_rate(BigDecimal _5year_rate) {this._5year_rate = _5year_rate;}
}
